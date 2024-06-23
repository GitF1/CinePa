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
import java.util.HashMap;
import java.util.Map;
import java.sql.CallableStatement;
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
                + "    ms.StartTime >= DATEADD(day, -30, CAST(GETDATE() AS DATE)) AND ms.StartTime<=CAST(GETDATE() AS DATE)\n"
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
    public ArrayList<String> getMonth(){
        System.out.println("MONTH");
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        ArrayList<String> result = new ArrayList<>();
        try (CallableStatement cs = connection.prepareCall("{call dbo.get_date_of_month}")) {
            cs.executeQuery();
            try (ResultSet rs = cs.getResultSet()) {
                while (rs.next()) {
                    result.add(df.format(rs.getDate("Date")));
                    System.out.println(df.format(rs.getDate("Date")));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(); // Handle exceptions appropriately in real scenarios
        }
        return result;
    }
    public HashMap<String, ArrayList> getSalesAndChain() {
        ArrayList<SalesData> result = new ArrayList<>();
        HashMap<String, ArrayList> resultMap = new HashMap<>();
//        String sqlQuery = "select sum(price) as Total,CAST(MovieSlot.StartTime AS DATE) AS Date,CinemaChain.CinemaChainID,CinemaChain.Name\n"
//                + "from\n"
//                + "Ticket \n"
//                + "join\n"
//                + "MovieSlot on Ticket.MovieSlotID=MovieSlot.MovieSlotID\n"
//                + "join \n"
//                + "Room on MovieSlot.RoomID=MovieSlot.RoomID\n"
//                + "join \n"
//                + "Cinema on Cinema.CinemaID=Room.CinemaID\n"
//                + "join \n"
//                + "CinemaChain on CinemaChain.CinemaChainID=Cinema.CinemaChainID\n"
//                + "where CAST(MovieSlot.StartTime AS DATE)>=(DATEADD(Day,-30,GETDATE()))AND MovieSlot.StartTime<=CAST(GETDATE() AS DATE)\n"
//                + "group by CinemaChain.CinemaChainID,CinemaChain.Name,CAST(MovieSlot.StartTime AS DATE)\n"
//                + ";";

        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        try (CallableStatement cs = connection.prepareCall("{call dbo.get_chain_value_monthly}")) {
            cs.executeQuery();
            try (ResultSet rs = cs.getResultSet()) {
                while (rs.next()) {
                    result.add(new SalesData(df.format(rs.getDate("Date")), rs.getDouble("Total"), rs.getString("Name")));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(); // Handle exceptions appropriately in real scenarios
        }
        for (SalesData sd : result) {
            if (resultMap.containsKey(sd.getChain())) {
                resultMap.get(sd.getChain()).add(sd);

            } else {
                resultMap.put(sd.getChain(), new ArrayList<SalesData>());
                resultMap.get(sd.getChain()).add(sd);
            }
        }
        return resultMap;
    }

}
