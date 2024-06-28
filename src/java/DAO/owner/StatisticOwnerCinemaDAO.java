/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.owner;

import DAO.CinemasDAO;
import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import model.Cinema;
import model.owner.dashboard.ChartModel;
import util.ChartUtil;
import util.Time;

/**
 *
 * @author PC
 */
public class StatisticOwnerCinemaDAO extends SQLServerConnect {

    private ServletContext context;

    public StatisticOwnerCinemaDAO(ServletContext context) throws Exception {
        super();
        connect(context);
        this.context = context;

    }

    public List<Cinema> getListCinemaByCinemaChain(int cinemaChainID, int limit, int offset) throws Exception {
        return new CinemasDAO(context).getCinemasByCinemaChainID(cinemaChainID, limit, offset);
    }

    public String getNameCinemaByID(int cinemaID) {

        String queryGetMovie = "SELECT CC.Name, C.Address FROM Cinema C JOIN CinemaChain CC ON CC.CinemaChainID = C.CinemaChainID WHERE CinemaID = ? ";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetMovie)) {

            preparedStatement.setInt(1, cinemaID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String title = resultSet.getString("Name");
                String address = resultSet.getString("Address");

                return title + " " + address;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ChartModel getRevenueByDateInMonthOfCinema(int cinemaChainID, Integer cinemaID, Integer month, Integer year) throws Exception {

        String label = cinemaID != null ? getNameCinemaByID(cinemaID) : "Revenue Cinemas";

        if (cinemaID == null) {
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
                + "    DAY(O.TimeCreated) AS DayOfMonth,\n"
                + "    SUM(MS.Price * (1 - MS.Discount) * T.TotalTickets) AS Revenue\n"
                + "FROM\n"
                + "    (\n"
                + "        SELECT\n"
                + "            T.OrderID,\n"
                + "            MS.MovieSlotID,\n"
                + "            COUNT(*) AS TotalTickets\n"
                + "        FROM\n"
                + "            Ticket T\n"
                + "            JOIN MovieSlot MS ON T.MovieSlotID = MS.MovieSlotID\n"
                + "            JOIN Room R ON MS.RoomID = R.RoomID\n"
                + "            JOIN Cinema C ON R.CinemaID = C.CinemaID\n"
                + "            JOIN CinemaChain CC ON C.CinemaChainID = CC.CinemaChainID\n"
                + "        WHERE\n"
                + "            C.CinemaID = ?\n"
                + "            AND CC.CinemaChainID = ? \n"
                + "        GROUP BY\n"
                + "            T.OrderID,\n"
                + "            MS.MovieSlotID,\n"
                + "            C.CinemaID\n"
                + "    ) AS T\n"
                + "    JOIN [Order] O ON T.OrderID = O.OrderID\n"
                + "    JOIN MovieSlot MS ON T.MovieSlotID = MS.MovieSlotID\n"
                + "    JOIN Room R ON MS.RoomID = R.RoomID\n"
                + "    JOIN Cinema C ON R.CinemaID = C.CinemaID\n"
                + "    JOIN CinemaChain CC ON C.CinemaChainID = CC.CinemaChainID\n"
                + "WHERE\n"
                + "    C.CinemaID = ? \n"
                + "    AND CC.CinemaChainID = ? \n"
                + "    AND YEAR(O.TimeCreated) = ? \n"
                + "    AND MONTH(O.TimeCreated) = ? \n"
                + "GROUP BY\n"
                + "    DAY(O.TimeCreated)\n"
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

            stmt.setInt(1, cinemaID);
            stmt.setInt(2, cinemaChainID);
            stmt.setInt(3, cinemaID);
            stmt.setInt(4, cinemaChainID);
            stmt.setInt(5, year);
            stmt.setInt(6, month);

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

        return new ChartModel(0, labels, data, label, ChartUtil.TYPE_LINE);
    }

    public List<ChartModel> getListRevenueOfCinemasSelect(Integer cinemaChainID, List<Integer> listCinemaID, Integer month, Integer year) throws Exception {

        List<ChartModel> list = new ArrayList<>(listCinemaID.size());
        
        if (month == null) {
            month = new Time().getMonth();
        }
        if (year == null) {
            year = new Time().getYear();
        }

        for (Integer cinemaID : listCinemaID) {

            if (cinemaID == null) {
                continue;
            }

            ChartModel chart = getRevenueByDateInMonthOfCinema(cinemaChainID, cinemaID, month, year);

            if (chart == null) {
                continue;
            }

            list.add(chart);
        }

        return list;
    }

}
