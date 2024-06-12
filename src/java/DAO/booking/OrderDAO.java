/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.booking;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import model.booking.Bill;
import model.booking.OrderTicket;
import java.sql.*;
import model.Seat;
import model.booking.CanteenItemOrder;

/**
 *
 * @author PC
 */
public class OrderDAO extends SQLServerConnect {

    public OrderDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<Bill> getListOrderTicket(int userID) {
        List<Bill> listBill = new ArrayList<>();

        // SQL query
        String query
                = "SELECT DISTINCT "
                + "    O.OrderID, "
                + "    O.TimeCreated, "
                + "    MS.MovieSlotID, "
                + "    MS.RoomID,"
                + "    M.MovieID, "
                + "    M.Title AS MovieTitle, "
                + "    MS.StartTime, "
                + "    MS.EndTime "
                + "FROM "
                + "    [Order] O "
                + "JOIN "
                + "    Ticket T ON O.OrderID = T.OrderID "
                + "JOIN "
                + "    MovieSlot MS ON T.MovieSlotID = MS.MovieSlotID "
                + "JOIN "
                + "    Movie M ON MS.MovieID = M.MovieID "
                + "WHERE "
                + "     O.UserID = ?"
                + "     ORDER BY O.TimeCreated DESC";

        // Establish connection, prepare statement, and execute query
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the user ID parameter
            preparedStatement.setInt(1, userID);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Process the result set
                while (resultSet.next()) {

                    int orderId = resultSet.getInt("OrderID");
                    Timestamp timeCreated = resultSet.getTimestamp("TimeCreated");
                    int movieSlotId = resultSet.getInt("MovieSlotID");
                    int movieId = resultSet.getInt("MovieID");
                    int roomID = resultSet.getInt("RoomID");
                    String movieTitle = resultSet.getString("MovieTitle");
                    Timestamp startTime = resultSet.getTimestamp("StartTime");
                    Timestamp endTime = resultSet.getTimestamp("EndTime");

                    Bill bill = new Bill(orderId, timeCreated, roomID, movieSlotId, movieId, movieTitle, startTime, endTime);

                    listBill.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listBill;
    }

    public List<CanteenItemOrder> getCanteenItemsByOrderID(int orderID) {
        
        List<CanteenItemOrder> canteenItems = new ArrayList<>();
        
        String query = "SELECT CIIO.OrderID, CIIO.CanteenItemID, CIIO.Amount, CI.Price, CI.Name, CI.Image "
                + "FROM CanteenItemInOrder CIIO "
                + "JOIN CanteenItem CI ON CIIO.CanteenItemID = CI.CanteenItemID "
                + "WHERE CIIO.OrderID = ? AND CI.Status = 'Available'";

        try (
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, orderID);
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set and create CanteenItemInOrder objects
            while (rs.next()) {

                int canteenItemID = rs.getInt("CanteenItemID");
                int amount = rs.getInt("Amount");
                double price = rs.getDouble("Price");
                String name = rs.getString("Name");
                String image = rs.getString("Image");

                canteenItems.add(new CanteenItemOrder(amount, orderID, canteenItemID, name, price, image));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return canteenItems;
    }

    public List<Seat> getSeatsByOrderID(int orderID) {
        List<Seat> seats = new ArrayList<>();
        String query = "SELECT S.SeatID, S.RoomID, S.Name, S.CoordinateX, S.CoordinateY "
                + "FROM Ticket T "
                + "JOIN Seat S ON T.SeatID = S.SeatID "
                + "WHERE T.OrderID = ?";

        try ( PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, orderID);
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set and create Seat objects
            while (rs.next()) {
                int seatID = rs.getInt("SeatID");
                int roomID = rs.getInt("RoomID");
                String name = rs.getString("Name");
                int coordinateX = rs.getInt("CoordinateX");
                int coordinateY = rs.getInt("CoordinateY");
                
                seats.add(new Seat(seatID, roomID, name, coordinateX, coordinateY));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

}
