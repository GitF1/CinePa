/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.graph.SalesData;
import model.movie.MovieInfo;

/**
 *
 * @author duyqu
 */
//NOT really accurate name but is here to query general data for graph-DuyND
public class GraphDAO extends SQLServerConnect {

    public GraphDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public ArrayList<SalesData> getTotalSalesValueLast7Days() throws SQLException {
        ArrayList<SalesData> result = new ArrayList<>();
        String sqlQuery = "SELECT \n"
                + "    CAST(ms.StartTime AS DATE) AS Date,\n"
                + "    SUM(ms.Price) AS TotalSales\n"
                + "FROM \n"
                + "    Ticket t\n"
                + "JOIN \n"
                + "    MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID\n"
                + "WHERE \n"
                + "    ms.StartTime >= DATEADD(day, -7, CAST(GETDATE() AS DATE))\n"
                + "GROUP BY \n"
                + "    CAST(ms.StartTime AS DATE)\n"
                + "ORDER BY \n"
                + "    Date;";//group value sold by date, order by date, only select last 7 days
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new SalesData(df.format(rs.getDate("Date")), rs.getDouble("TotalSales")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately in real scenarios
        }
        return result;

    }

}
