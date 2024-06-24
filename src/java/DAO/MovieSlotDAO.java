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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import model.MovieSlot;
import model.graph.SalesData;

/**
 *
 * @author duyqu
 */
public class MovieSlotDAO extends SQLServerConnect {

    public MovieSlotDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public boolean checkOverlap(Date start, Date end, int roomID) {
        boolean overlap = false;
        java.sql.Timestamp timestamp = new java.sql.Timestamp(start.getTime());
        String sql = "select * from MovieSlot where ((?>MovieSlot.StartTime and ?<MovieSlot.EndTime) and Cast(? as date) = Cast(MovieSlot.StartTime as date) and MovieSlot.RoomID=?)";
        //in order: end start start room id
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setTimestamp(1, new java.sql.Timestamp(end.getTime()));
            st.setTimestamp(2, new java.sql.Timestamp(start.getTime()));
            st.setTimestamp(3, new java.sql.Timestamp(start.getTime()));
            st.setInt(4, roomID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    overlap = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return overlap;
    }

    public ArrayList<MovieSlot> getDateSchedule(Date date, int roomID) {
        ArrayList<MovieSlot> result = new ArrayList<>();
        String sqlQuery = "select * from MovieSlot where (? = Cast(MovieSlot.StartTime as date)) and MovieSlot.RoomID=?";
        java.sql.Date insertDate = new java.sql.Date(date.getTime());

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setDate(1, insertDate);
            pstmt.setInt(2, roomID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
//                    int movieSlotID, int roomID, int movieID, LocalDateTime startTime, LocalDateTime endTime, String type, float price, float discount, String status
                    result.add(new MovieSlot(
                            1,//placeholder, will not be used
                            rs.getInt("RoomID"),
                            rs.getInt("MovieID"),
                            rs.getTimestamp("StartTime").toLocalDateTime(),
                            rs.getTimestamp("EndTime").toLocalDateTime(),
                            rs.getString("Type"),
                            rs.getFloat("Price"),
                            rs.getFloat("Discount"),
                            rs.getString("status")
                    )
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately in real scenarios
        }
        return result;
    }

}
