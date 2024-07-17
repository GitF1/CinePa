/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.confirm;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import service.EmailService;
import java.sql.*;
import util.Status;

/**
 *
 * @author PC
 */
public class ConfirmDAO extends SQLServerConnect {

    public ConfirmDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public boolean sendInFormationCofirm(HttpServletRequest request, HttpServletResponse response, Integer orderId, String qrCodeUrl, String code) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Integer userId = (int) session.getAttribute("userID");
        String email = (String) session.getAttribute("email");

        // Validate parameters
        if (orderId == null || userId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User Forbbiden!");
            return false;
        }

        try {

            boolean isSuccess = new EmailService().sendEmailActiveOrder(email, code, qrCodeUrl);
            //

            if (isSuccess) {
                System.out.println("send confirm sucess! ");
                return true;

            }
            //
            response.setContentType("application/json");
            response.getWriter().write("{\"qrCodeUrl\":\"" + qrCodeUrl + "\"}");

        } catch (Exception e) {
            throw new ServletException(e);
        }
        return false;
    }

    public boolean validateAndCheckInOrder(int orderID, int userID, String code) {

        String validateOrderSQL = "SELECT OrderID FROM [Order] o WHERE OrderID = ? AND UserID = ? AND Code = ? AND o.Status = ?";
        String updateOrderStatusSQL = "UPDATE [Order] SET Status = ? WHERE OrderID = ?";

        try (PreparedStatement validateStmt = connection.prepareStatement(validateOrderSQL); PreparedStatement updateStmt = connection.prepareStatement(updateOrderStatusSQL)) {

            // Set parameters for validation query
            validateStmt.setInt(1, orderID);
            validateStmt.setInt(2, userID);
            validateStmt.setString(3, code);
            validateStmt.setString(4, Status.STATUS_ORDER_BOOKED);

            ResultSet rs = validateStmt.executeQuery();

            // Check if the order exists and matches the criteria
            if (rs.next()) {
                // Set parameters for update query
                updateStmt.setString(1, Status.STATUS_ORDER_CHECKED_IN);
                updateStmt.setInt(2, orderID);
                int rowsAffected = updateStmt.executeUpdate();

                // Return true if the update was successful
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception or rethrow as needed
        }
        // Return false if the validation or update failed
        return false;
    }

    public boolean isValidQRCodeOrder(int orderID, int userID, String code) {

        String validateOrderSQL = "SELECT OrderID FROM [Order] o WHERE OrderID = ? AND UserID = ? AND Code = ? AND o.Status = ?";

        try (PreparedStatement validateStmt = connection.prepareStatement(validateOrderSQL)) {

            // Set parameters for validation query
            validateStmt.setInt(1, orderID);
            validateStmt.setInt(2, userID);
            validateStmt.setString(3, code);
            validateStmt.setString(4, Status.STATUS_ORDER_BOOKED);

            try (ResultSet rs = validateStmt.executeQuery();) {
                return rs.next(); // Returns true if a record is found, false otherwise
            }
            // Set parameters for update query

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception or rethrow as needed
        }
        // Return false if the validation or update failed
        return false;
    }

}
