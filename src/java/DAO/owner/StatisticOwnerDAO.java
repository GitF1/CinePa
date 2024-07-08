/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.owner;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import model.owner.dashboard.ChartModel;
import util.Time;

/**
 *
 * @author PC
 */
public class StatisticOwnerDAO extends SQLServerConnect {

    public StatisticOwnerDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public Integer getCinemaChainOfUser(int userID) {

        String query = "SELECT CinemaChainID FROM ManagesChain WHERE UserID = ?";
        Integer cinemaChainId = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    cinemaChainId = resultSet.getInt("CinemaChainID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinemaChainId;

    }

    public int countCinemasByCinemaChainID(int cinemaChainID) {

        String query = "SELECT COUNT(*) FROM Cinema WHERE CinemaChainID = ?";
        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cinemaChainID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countMovieByCinemaChainID(int cinemaChainID) {

        String query = "SELECT \n"
                + "    COUNT(DISTINCT mc.MovieID) AS TotalMovies\n"
                + "FROM \n"
                + "    CinemaChain cc\n"
                + "JOIN \n"
                + "    Cinema c ON cc.CinemaChainID = c.CinemaChainID\n"
                + "JOIN \n"
                + "    MovieCinema mc ON c.CinemaID = mc.CinemaID\n"
                + "WHERE cc.CinemaChainID =?\n"
                + "\n"
                + "GROUP BY cc.CinemaChainID";

        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cinemaChainID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countTotalOrderTicket(int cinemaChainID) {

        String query = "SELECT COUNT(t.TicketID) AS TotalBookings\n"
                + "FROM Ticket t\n"
                + "JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID\n"
                + "JOIN Room r ON ms.RoomID = r.RoomID\n"
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID\n"
                + "WHERE c.CinemaChainID = ?;";

        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cinemaChainID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public double getTotalRevenueByChainID(int cinemaChainID) {
        double totalRevenue = 0.0;

        // SQL query to calculate total ticket revenue for a specific cinema chain
        String ticketRevenueSql
                = "SELECT SUM(ms.Price * (1 - ms.Discount / 100)) AS TotalRevenue\n"
                + "FROM Ticket t\n"
                + "JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID\n"
                + "JOIN Room r ON ms.RoomID = r.RoomID\n"
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID\n"
                + "WHERE c.CinemaChainID = ? AND t.Status = 'BOOKED'";

        try {

            // Calculate ticket revenue
            try (PreparedStatement pstmt = connection.prepareStatement(ticketRevenueSql)) {
                pstmt.setInt(1, cinemaChainID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        totalRevenue = rs.getDouble(1);
                    }
                }
            }

            System.out.println("total ticket v1" + totalRevenue);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }

    public ChartModel countGenresInCinemaChain(int cinemaChainID) {

        String query = "SELECT mig.Genre, COUNT(*) AS GenreCount\n"
                + "FROM MovieInGenre mig\n"
                + "JOIN (\n"
                + "    SELECT DISTINCT mc.MovieID\n"
                + "    FROM MovieCinema mc\n"
                + "    JOIN Cinema c ON mc.CinemaID = c.CinemaID\n"
                + "    WHERE c.CinemaChainID = ? \n"
                + ") AS MoviesInChain ON mig.MovieID = MoviesInChain.MovieID\n"
                + "GROUP BY  mig.Genre;";

        List<String> lables = new ArrayList();
        List<Double> data = new ArrayList();

        ChartModel chart = new ChartModel();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cinemaChainID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String genre = resultSet.getString("Genre");
                    Integer count = resultSet.getInt("GenreCount");
                    lables.add(genre);
                    data.add((double) count);
                }
                chart.setSize(data.size());
                chart.setData(data);
                chart.setLables(lables);

                return chart;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chart;
    }

    public ChartModel getOrderCountsByDateInMonth(Integer month, Integer year, int cinemaChainID) {

        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        // Determine the number of days in the specified month and current year

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        String query = "SELECT DAY(o.TimeCreated) AS DayOfMonth, COUNT(*) AS OrderCount "
                + "FROM [Order] o "
                + "JOIN Ticket t ON o.OrderID = t.OrderID "
                + "JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "WHERE MONTH(o.TimeCreated) = ? "
                + "AND c.CinemaChainID = ? "
                + "GROUP BY DAY(o.TimeCreated) "
                + "ORDER BY DayOfMonth";

        List<String> labels = new ArrayList(daysInMonth);
        List<Double> data = new ArrayList(daysInMonth);
        // Fill the lists with default values
        for (int i = 0; i < daysInMonth; i++) {
            labels.add((i + 1) + ""); // Label for each day of the month
            data.add(0.0);            // Default count is 0
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, month);
            stmt.setInt(2, cinemaChainID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int dayOfMonth = rs.getInt("DayOfMonth");
                int orderCount = rs.getInt("OrderCount");
                labels.set(dayOfMonth - 1, dayOfMonth + "");
                data.set(dayOfMonth - 1, (double) orderCount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ChartModel(labels.size(), labels, data);
    }

    public ChartModel getRevenueByDateInMonth(int cinemaChainID, Integer month, Integer year) {

        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }

        if (year == null) {
            year = LocalDate.now().getYear();
        }

        // Determine the number of days in the specified month and current year
        YearMonth yearMonth = YearMonth.of(year, month);

        int daysInMonth = yearMonth.lengthOfMonth();

        String query = "SELECT \n"
                + "    DAY(o.TimeCreated) AS DayOfMonth ,\n"
                + "    SUM(ms.Price * (1 - ms.Discount / 100)) AS TotalRevenue\n"
                + "FROM Ticket t\n"
                + "JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID\n"
                + "JOIN Room r ON ms.RoomID = r.RoomID\n"
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID\n"
                + "JOIN [Order] o ON t.OrderID = o.OrderID\n"
                + "WHERE \n"
                + "    c.CinemaChainID = ?\n"
                + "    AND t.Status = 'BOOKED'\n"
                + "    AND MONTH(o.TimeCreated) = ?\n"
                + "    AND YEAR(o.TimeCreated) = ?\n"
                + "GROUP BY \n"
                + "     DAY(o.TimeCreated)\n"
                + "ORDER BY \n"
                + "    DayOfMonth";

        List<String> labels = new ArrayList(daysInMonth);
        List<Double> data = new ArrayList(daysInMonth);
        // Fill the lists with default values
        for (int i = 0; i < daysInMonth; i++) {
            labels.add((i + 1) + ""); // Label for each day of the month
            data.add(0.0);            // Default count is 0
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, cinemaChainID);
            stmt.setInt(2, month);
            stmt.setInt(3, year);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int dayOfMonth = rs.getInt("DayOfMonth");
                double orderCount = rs.getDouble("TotalRevenue");
                labels.set(dayOfMonth - 1, dayOfMonth + "");
                data.set(dayOfMonth - 1, (double) orderCount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ChartModel(labels.size(), labels, data);
    }

    public ChartModel getRevenuePerDate(int cinemaChainID, Integer date, Integer month, Integer year) {

        Time time = new Time();

        if (year == null) {
            year = time.getYear();
        }

        if (month == null) {
            month = time.getMonth();
        }

        if (date == null) {
            date = time.getDay();
        }

        int currentHour = time.getHour();

        String query = "SELECT \n"
                + "    DATEPART(HOUR, o.TimeCreated) AS HourOfDay,\n"
                + "    SUM(ms.Price * (1 - ms.Discount / 100)) AS TotalRevenue\n"
                + "FROM Ticket t\n"
                + "JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID\n"
                + "JOIN Room r ON ms.RoomID = r.RoomID\n"
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID\n"
                + "JOIN [Order] o ON t.OrderID = o.OrderID\n"
                + "WHERE \n"
                + "    c.CinemaChainID = ?\n"
                + "    AND t.Status = 'BOOKED'\n"
                + "    AND YEAR(o.TimeCreated) = ?\n"
                + "    AND MONTH(o.TimeCreated) = ?\n"
                + "    AND DAY(o.TimeCreated) = ?\n"
                + "GROUP BY \n"
                + "    DATEPART(HOUR, o.TimeCreated)\n"
                + "ORDER BY \n"
                + "    HourOfDay;";

        List<String> labels = new ArrayList(currentHour);
        List<Double> data = new ArrayList(currentHour);
        double totalRevenue = 0;
        double maxRevenue = 0;
        Integer bestHourSale = null;
        ChartModel chart = new ChartModel();
        // Fill the lists with default values
        for (int i = 0; i < currentHour; i++) {
            labels.add((i + 1) + "");
            data.add(0.0);
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, cinemaChainID);
            stmt.setInt(2, year);
            stmt.setInt(3, month);
            stmt.setInt(4, date);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int hourOfDate = rs.getInt("HourOfDay");
                double amount = rs.getDouble("TotalRevenue");
                totalRevenue += amount;

                if (amount > maxRevenue) {
                    maxRevenue = amount;
                    bestHourSale = hourOfDate;
                }
                labels.set(hourOfDate - 1, hourOfDate + "");
                data.set(hourOfDate - 1, (double) amount);
            }

            chart.setSize(labels.size());
            chart.setData(data);
            chart.setLables(labels);
            chart.setMaxData(bestHourSale == null ? -1 : bestHourSale);
            chart.setTotalData(totalRevenue);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chart;
    }

    public ChartModel getOrderTicketPerDate(int cinemaChainID, Integer date, Integer month, Integer year) {
        Time time = new Time();

        if (year == null) {
            year = time.getYear();
        }

        if (month == null) {
            month = time.getMonth();
        }

        if (date == null) {
            date = time.getDay();
        }

        int currentHour = time.getHour();

        String query = "SELECT \n"
                + "    DATEPART(HOUR, o.TimeCreated) AS HourOfDay,\n"
                + "    COUNT(o.OrderID) AS AmountOfOrders\n"
                + "FROM Ticket t\n"
                + "JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID\n"
                + "JOIN Room r ON ms.RoomID = r.RoomID\n"
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID\n"
                + "JOIN [Order] o ON t.OrderID = o.OrderID\n"
                + "WHERE \n"
                + "    c.CinemaChainID = ?\n"
                + "    AND t.Status = 'BOOKED'\n"
                + "    AND YEAR(o.TimeCreated) = ?\n"
                + "    AND MONTH(o.TimeCreated) = ?\n"
                + "    AND DAY(o.TimeCreated) = ?\n"
                + "GROUP BY \n"
                + "    DATEPART(HOUR, o.TimeCreated)\n"
                + "ORDER BY \n"
                + "    HourOfDay;";

        List<String> labels = new ArrayList(currentHour);
        List<Double> data = new ArrayList(currentHour);

        double totalOrders = 0;
        int maxOrder = 0;

        ChartModel chart = new ChartModel();
        // Fill the lists with default values
        for (int i = 0; i < currentHour; i++) {
            labels.add((i + 1) + "");
            data.add(0.0);
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, cinemaChainID);
            stmt.setInt(2, year);
            stmt.setInt(3, month);
            stmt.setInt(4, date);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int hourOfDate = rs.getInt("HourOfDay");
                int amount = rs.getInt("AmountOfOrders");
                totalOrders += amount;
                if (amount > maxOrder) {
                    maxOrder = amount;

                }
                labels.set(hourOfDate - 1, hourOfDate + "");
                data.set(hourOfDate - 1, (double) amount);
            }

            chart.setSize(labels.size());
            chart.setData(data);
            chart.setLables(labels);
            chart.setMaxData(maxOrder);
            chart.setTotalData(totalOrders);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chart;
    }

}
