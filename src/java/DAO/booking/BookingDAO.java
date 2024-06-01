/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.booking;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.util.List;
import model.Seat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import model.CanteenItem;
import model.booking.CanteenItemOrder;

/**
 *
 * @author PC
 */
public class BookingDAO extends SQLServerConnect {

    public BookingDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<Seat> getListSeatByRoomID(int roomID) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Seat> seats = new ArrayList<>();

        try {

            String sql = "SELECT Name,  CoordinateX, CoordinateY FROM Seat WHERE RoomID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, roomID);
            rs = stmt.executeQuery();

            // Add each seat to the list
            while (rs.next()) {
                String name = rs.getString("Name");
                int coordinateX = rs.getInt("CoordinateX");
                int coordinateY = rs.getInt("CoordinateY");
                seats.add(new Seat(name, roomID, coordinateX, coordinateY));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return seats;
    }

    public List<CanteenItem> getListAllCanteenItem() {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<CanteenItem> listCanteenItem = new ArrayList<>();

        try {

            String sql = "SELECT * FROM CanteenItem";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Add each seat to the list
            while (rs.next()) {

                int id = rs.getInt("CanteenItemID");
                String name = rs.getString("Name");
                float price = rs.getInt("Price");
                String image = rs.getString("Image");
                listCanteenItem.add(new CanteenItem(id, name, price, image));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listCanteenItem;
    }

    public boolean bookTicketMovieSlot(int userID, int movieSlotID, List<Integer> seatIDs, List<CanteenItemOrder> canteenOrderItems) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            // Disable auto-commit to handle transactions manually
            connection.setAutoCommit(false);

            // Check if the seats are already booked of this movie slot
            String checkSeatsSQL = "SELECT SeatID FROM Ticket WHERE MovieSlotID = ? AND SeatID IN ("
                    + String.join(",", seatIDs.stream().map(id -> "?").toArray(String[]::new))
                    + ") FOR UPDATE";
            pstmt = connection.prepareStatement(checkSeatsSQL);
            pstmt.setInt(1, movieSlotID);
            
            for (int i = 0; i < seatIDs.size(); i++) {
                pstmt.setInt(i + 2, seatIDs.get(i));
            }
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // If any seat is already booked, return false
                return false;
            }

            // Insert a new order
            String insertOrderSQL = "INSERT INTO [Order] (UserID, TimeCreated, Status) VALUES (?, NOW(), 'PENDING')";
            pstmt = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, userID);
            pstmt.executeUpdate();

            // Retrieve the generated OrderID
            rs = pstmt.getGeneratedKeys();
            int orderID = 0;
            if (rs.next()) {
                orderID = rs.getInt(1);
            }

            // Insert tickets
            String insertTicketSQL = "INSERT INTO Ticket (MovieSlotID, OrderID, SeatID, Status) VALUES (?, ?, ?, 'BOOKED')";
            pstmt = connection.prepareStatement(insertTicketSQL);
            for (int seatID : seatIDs) {
                pstmt.setInt(1, movieSlotID);
                pstmt.setInt(2, orderID);
                pstmt.setInt(3, seatID);
                pstmt.addBatch();
            }
            
            pstmt.executeBatch();

            // Insert canteen items in order if user select
            if (canteenOrderItems != null && !canteenOrderItems.isEmpty()) {
                String insertCanteenItemSQL = "INSERT INTO CanteenItemInOrder (OrderID, CanteenItemID, Amount, Price) VALUES (?, ?, ?, ?)";
                pstmt = connection.prepareStatement(insertCanteenItemSQL);
                for (CanteenItemOrder item : canteenOrderItems) {

                    pstmt.setInt(1, orderID);
                    pstmt.setInt(2, item.getItemID());
                    pstmt.setInt(3, item.getAmount());
                    pstmt.setDouble(4, item.getPrice());

                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }   

            // Commit the transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // Rollback the transaction in case of error
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
