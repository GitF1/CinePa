/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.owner;

import DB.SQLServerConnect;
import controller.owner.dashboard.OwnerDashBoardServlet;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import model.Movie;
import model.owner.dashboard.ChartModel;

/**
 *
 * @author PC
 */
public class StatisticOwnerMovieDAO extends SQLServerConnect {

    private ServletContext context;

    public StatisticOwnerMovieDAO(ServletContext context) throws Exception {
        super();
        connect(context);
        this.context = context;
    }
    //

    //////////////////////////////////////////// DAO FOR MOVIE STATISTIC ///////////////////////////////////////////
    public String getNameMovieByID(int movieID) {
        String queryGetMovie = "SELECT * FROM Movie WHERE MovieID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetMovie)) {

            preparedStatement.setInt(1, movieID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String title = resultSet.getString("Title");
                return title;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Movie> getListMoviebyCinemaChainID(int cinemaChainID, Integer limit, Integer offset) {
        if (limit == null || limit < 0) {
            limit = 20;
        }
        if (offset == null || offset < 0) {
            offset = 0;
        }

        String query = "SELECT DISTINCT\n"
                + "    m.MovieID,\n"
                + "    m.Title,\n"
                + "    m.ImageURL\n"
                + "FROM \n"
                + "    Movie m\n"
                + "    INNER JOIN MovieCinema mc ON m.MovieID = mc.MovieID\n"
                + "    INNER JOIN Cinema c ON mc.CinemaID = c.CinemaID\n"
                + "    INNER JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID\n"
                + "WHERE \n"
                + "    cc.CinemaChainID = ?\n"
                + "ORDER BY m.MovieID\n" 
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY;";

        List<Movie> movies = new ArrayList();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, cinemaChainID);
            stmt.setInt(2, offset);  // number of rows to skip
            stmt.setInt(3, limit);   // number of rows to return
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Movie movie = new Movie();

                int movieID = rs.getInt("MovieID");
                String title = rs.getString("Title");
                String imageURL = rs.getString("ImageURL");

                movie.setMovieID(movieID);
                movie.setTitle(title);
                movie.setImageURL(imageURL);

                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    /////
    public ChartModel getRevenueByDateInMonthOfMovie(int cinemaChainID, Integer movieID, Integer month, Integer year) throws Exception {

        String label = movieID != null ? getNameMovieByID(movieID) : "Revenue movies";

        if (movieID == null) {
            ChartModel chart = new StatisticOwnerDAO(context).getRevenueByDateInMonth(cinemaChainID, month, year);
            chart.setLable(label);
            return chart;
        }

        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        // Determine the number of days in the specified month and current year
        YearMonth yearMonth = YearMonth.of(year, month);

        int daysInMonth = yearMonth.lengthOfMonth();

        String query = "SELECT\n"
                + "    DATEPART(day, ms.StartTime) AS DayOfMonth,\n"
                + "    SUM(ms.Price) AS Revenue\n"
                + "FROM\n"
                + "    Movie m\n"
                + "    INNER JOIN MovieCinema mc ON m.MovieID = mc.MovieID\n"
                + "    INNER JOIN Cinema c ON mc.CinemaID = c.CinemaID\n"
                + "    INNER JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID\n"
                + "    INNER JOIN Room r ON c.CinemaID = r.CinemaID\n"
                + "    INNER JOIN MovieSlot ms ON r.RoomID = ms.RoomID AND m.MovieID = ms.MovieID\n"
                + "    INNER JOIN Ticket t ON ms.MovieSlotID = t.MovieSlotID\n"
                + "    INNER JOIN [Order] o ON t.OrderID = o.OrderID\n"
                + "WHERE\n"
                + "    cc.CinemaChainID = ?\n"
                + "    AND m.MovieID = ?\n"
                + "    AND MONTH(o.TimeCreated) = ?\n"
                + "    AND YEAR(o.TimeCreated) = ?\n"
                + "GROUP BY\n"
                + "    DATEPART(day, ms.StartTime)\n"
                + "ORDER BY\n"
                + "    DayOfMonth;";

        List<String> labels = new ArrayList(daysInMonth);
        List<Double> data = new ArrayList(daysInMonth);
        // Fill the lists with default values
        for (int i = 0; i < daysInMonth; i++) {
            labels.add((i + 1) + ""); // Label for each day of the month
            data.add(0.0);            // Default count is 0
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, cinemaChainID);
            stmt.setInt(2, movieID);
            stmt.setInt(3, month);
            stmt.setInt(4, year);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int dayOfMonth = rs.getInt("DayOfMonth");
                double revenue = rs.getDouble("Revenue");
                labels.set(dayOfMonth - 1, dayOfMonth + "");
                data.set(dayOfMonth - 1, (double) revenue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ChartModel(0, labels, data, label, "bar");
    }

    public ChartModel getOrderTicketByDateInMonthOfMovie(int cinemaChainID, Integer movieID, Integer month, Integer year) throws Exception {

        String label = movieID != null ? getNameMovieByID(movieID) : "Order sales";

        if (movieID == null) {
            ChartModel chart = new StatisticOwnerDAO(context).getOrderCountsByDateInMonth(month, year, cinemaChainID);
            chart.setLable(label);
            return chart;
        }

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
                + "    DAY(O.TimeCreated) AS DayOfMonth,\n"
                + "    COUNT(t.TicketID) AS Amount\n"
                + "FROM \n"
                + "    Ticket t\n"
                + "	INNER JOIN [Order] O ON O.OrderID = T.OrderID\n"
                + "    INNER JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID\n"
                + "    INNER JOIN Room r ON ms.RoomID = r.RoomID\n"
                + "    INNER JOIN Cinema c ON r.CinemaID = c.CinemaID\n"
                + "    INNER JOIN Movie m ON ms.MovieID = m.MovieID\n"
                + "WHERE \n"
                + "    m.MovieID = ? \n"
                + "    AND c.CinemaChainID =  ? \n"
                + "    AND YEAR(ms.StartTime) = ? \n"
                + "    AND MONTH(ms.StartTime) =  ? \n"
                + "GROUP BY \n"
                + "   DAY(O.TimeCreated)\n"
                + "ORDER BY \n"
                + "    DayOfMonth;";

        List<String> labels = new ArrayList(daysInMonth);
        List<Double> data = new ArrayList(daysInMonth);
        // Fill the lists with default values
        for (int i = 0; i < daysInMonth; i++) {
            labels.add((i + 1) + ""); // Label for each day of the month
            data.add(0.0);            // Default count is 0
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, movieID);
            stmt.setInt(2, cinemaChainID);
            stmt.setInt(3, year);
            stmt.setInt(4, month);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int dayOfMonth = rs.getInt("DayOfMonth");
                double orderCount = rs.getDouble("Amount");
                labels.set(dayOfMonth - 1, dayOfMonth + "");
                data.set(dayOfMonth - 1, (double) orderCount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ChartModel(0, labels, data, label, "bar");
    }
}
