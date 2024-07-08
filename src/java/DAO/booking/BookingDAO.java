/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.booking;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Seat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import model.CanteenItem;
import model.booking.CanteenItemOrder;
import service.VnPayService;

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

                Seat newSeat = new Seat();
                newSeat.setName(name);
                newSeat.setRoomID(roomID);
                newSeat.setX(coordinateX);
                newSeat.setY(coordinateY);
                seats.add(newSeat);

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

    public boolean bookTicketMovieSlot(int userID, int movieSlotID, List<Integer> seatIDs, List<CanteenItemOrder> canteenOrderItems, HttpServletRequest req, HttpServletResponse res) throws IOException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection.setAutoCommit(false);

            // Insert a new order as pending
            String insertOrderSQL = "INSERT INTO [Order] (UserID, TimeCreated, Status) VALUES (?, GETDATE(), 'PENDING')";
            pstmt = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, userID);
            pstmt.executeUpdate();

            // Retrieve the generated OrderID
            rs = pstmt.getGeneratedKeys();
            int orderID = 0;
            if (rs.next()) {
                orderID = rs.getInt(1);
            }

            // Insert temporary order details into a temporary table
            for (int seatID : seatIDs) {
                String insertTempSeatsSQL = "INSERT INTO TempOrderSeats (OrderID, MovieSlotID, SeatID) VALUES (?, ?, ?)";
                pstmt = connection.prepareStatement(insertTempSeatsSQL);
                pstmt.setInt(1, orderID);
                pstmt.setInt(2, movieSlotID);
                pstmt.setInt(3, seatID);
                pstmt.executeUpdate();
            }
            
            for (CanteenItemOrder item : canteenOrderItems) {
                String insertTempCanteenItemsSQL = "INSERT INTO TempOrderCanteenItems (OrderID, CanteenItemID, Amount, Price) VALUES (?, ?, ?, ?)";
                pstmt = connection.prepareStatement(insertTempCanteenItemsSQL);
                pstmt.setInt(1, orderID);
                pstmt.setInt(2, item.getItemID());
                pstmt.setInt(3, item.getAmount());
                pstmt.setDouble(4, item.getPrice());
                pstmt.executeUpdate();
            }
            req.setAttribute("orderID", orderID);
            // Initiate VNPay payment process
            new VnPayService().PayMentService(orderID, req, res);

            // Commit transaction
            connection.commit();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        }
    }

    public boolean confirmOrder(int orderID, String codeActive) {
        PreparedStatement pstmt = null;

        try {

            connection.setAutoCommit(false);

            // Update the order status to confirmed
            String updateOrderSQL = "UPDATE [Order] SET Status = 'CONFIRMED', Code = ? WHERE OrderID = ?";
            pstmt = connection.prepareStatement(updateOrderSQL);

            pstmt.setString(1, codeActive);
            pstmt.setInt(2, orderID);
            pstmt.executeUpdate();

            // Retrieve temporary order details from the temporary tables
            String selectTempSeatsSQL = "SELECT MovieSlotID, SeatID FROM TempOrderSeats WHERE OrderID = ?";
            pstmt = connection.prepareStatement(selectTempSeatsSQL);
            pstmt.setInt(1, orderID);
            ResultSet rs = pstmt.executeQuery();

            List<Integer> seatIDs = new ArrayList<>();
            int movieSlotID = 0;
            while (rs.next()) {
                movieSlotID = rs.getInt("MovieSlotID");
                seatIDs.add(rs.getInt("SeatID"));
            }

            String selectTempCanteenItemsSQL = "SELECT CanteenItemID, Amount, Price FROM TempOrderCanteenItems WHERE OrderID = ?";
            pstmt = connection.prepareStatement(selectTempCanteenItemsSQL);
            pstmt.setInt(1, orderID);
            rs = pstmt.executeQuery();

            List<CanteenItemOrder> canteenItems = new ArrayList<>();
            while (rs.next()) {
                int canteenItemID = rs.getInt("CanteenItemID");
                int amount = rs.getInt("Amount");
                double price = rs.getDouble("Price");
                CanteenItemOrder canteenItem = new CanteenItemOrder();
                canteenItem.setItemID(canteenItemID);
                canteenItem.setAmount(amount);
                canteenItem.setPrice(price);
                canteenItems.add(canteenItem);
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

            // Insert canteen items
            if (!canteenItems.isEmpty()) {
                String insertCanteenItemSQL = "INSERT INTO CanteenItemInOrder (OrderID, CanteenItemID, Amount, Price) VALUES (?, ?, ?, ?)";
                pstmt = connection.prepareStatement(insertCanteenItemSQL);
                for (CanteenItemOrder item : canteenItems) {
                    pstmt.setInt(1, orderID);
                    pstmt.setInt(2, item.getItemID());
                    pstmt.setInt(3, item.getAmount());
                    pstmt.setDouble(4, item.getPrice());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            // Commit transaction
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void rollbackOrder(int orderID) {
        PreparedStatement pstmt = null;

        try {
            // Remove temporary order details
            String deleteTempSeatsSQL = "DELETE FROM TempOrderSeats WHERE OrderID = ?";
            pstmt = connection.prepareStatement(deleteTempSeatsSQL);
            pstmt.setInt(1, orderID);
            pstmt.executeUpdate();

            String deleteTempCanteenItemsSQL = "DELETE FROM TempOrderCanteenItems WHERE OrderID = ?";
            pstmt = connection.prepareStatement(deleteTempCanteenItemsSQL);
            pstmt.setInt(1, orderID);
            pstmt.executeUpdate();

            // Update the order status to failed
            String updateOrderSQL = "UPDATE [Order] SET Status = 'FAILED' WHERE OrderID = ?";
            pstmt = connection.prepareStatement(updateOrderSQL);
            pstmt.setInt(1, orderID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
