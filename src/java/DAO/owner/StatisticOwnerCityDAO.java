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
import java.util.stream.Collectors;
import model.owner.dashboard.ChartModel;

public class StatisticOwnerCityDAO extends SQLServerConnect {

    private ServletContext context;

    public StatisticOwnerCityDAO(ServletContext context) throws Exception {
        super();
        connect(context);
        this.context = context;
    }

    public ArrayList<String> getProvinceByUserId(int id) {
        ArrayList<String> provinces = new ArrayList<>();

        String query = "SELECT DISTINCT c.Province "
                + "FROM [User] u "
                + "JOIN ManagesChain MC ON u.UserID = MC.UserID "
                + "JOIN CinemaChain CC ON MC.CinemaChainID = CC.CinemaChainID "
                + "JOIN Cinema c ON CC.CinemaChainID = c.CinemaChainID "
                + "WHERE u.UserID = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String province = resultSet.getString("Province");
                provinces.add(province);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return provinces;
    }

    public double getRevenueByDay(String day, String month, String year, String userId, String province) {
        System.out.println("thong tin truyen ham 2 : " + day + " " + month + " " + year + " " + userId + " " + province);
        double totalRevenue = 0.0;
        
        String query = "SELECT SUM(ms.Price) AS TotalRevenue "
                + "FROM [User] u "
                + "JOIN ManagesChain MC ON MC.UserID = u.UserID "
                + "JOIN CinemaChain cc ON MC.CinemaChainID = cc.CinemaChainID "
                + "JOIN Cinema c ON cc.CinemaChainID = c.CinemaChainID "
                + "JOIN Room r ON c.CinemaID = r.CinemaID "
                + "JOIN MovieSlot ms ON r.RoomID = ms.RoomID "
                + "JOIN Ticket t ON ms.MovieSlotID = t.MovieSlotID "
                + "JOIN [Order] o ON t.OrderID = o.OrderID "
                + "WHERE u.UserID = ? "
                + "AND DAY(o.TimeCreated) = ? "
                + "AND MONTH(o.TimeCreated) = ? "
                + "AND YEAR(o.TimeCreated) = ? "
                + "AND c.Province = ? "
                + "GROUP BY c.Province";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, day);
            preparedStatement.setString(3, month);
            preparedStatement.setString(4, year);
            preparedStatement.setNString(5, province); // Using setNString for NVARCHAR

            // Log the prepared statement
            System.out.println("Prepared Statement: " + preparedStatement.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble("TotalRevenue");
            }

        } catch (SQLException e) {
            System.err.println("loi trong ham thu 2 : result la : " + totalRevenue);

        }

        return totalRevenue;
    }

    public static List<String> getDayInMonth(String month, String year) {
        List<String> daysOfMonth = new ArrayList<>();

        // Parse month and year to integers
        int monthValue = Integer.parseInt(month);
        int yearValue = Integer.parseInt(year);

        // Get the number of days in the specified month and year
        YearMonth yearMonthObject = YearMonth.of(yearValue, monthValue);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        // Generate the list of days as strings
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(yearValue, monthValue, day);
            daysOfMonth.add(String.valueOf(date.getDayOfMonth()));
        }

        return daysOfMonth;
    }

    public ChartModel getChartModelCity(String city, String month, String year, String userId) {

        System.out.println("thong tin truyen theo : " + city + month + year + userId);

        ChartModel chartModel = new ChartModel();

        List<String> labels = getDayInMonth(month, year);
        System.out.println("labels trong ham lay ngay : " + labels);
        chartModel.setLables(labels);

        System.out.println("list labels : " + labels);

        List<Double> data = new ArrayList<>();

        for (String day : labels) {
            double revenue = getRevenueByDay(day, month, year, userId, city);
            System.out.println("revenue : " + revenue);
            data.add(revenue);
        }

        chartModel.setData(data);

//        chartModel.setType("bar");
        return chartModel;
    }

}
