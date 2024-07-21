/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.transaction;

import DB.SQLServerConnect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import util.RouterJSP;

/**
 *
 * @author PC
 */
public class BalanceUpdater extends SQLServerConnect {

    private static final String ADMIN_EMAIL = "cinepa.org@gmail.com";

    public BalanceUpdater(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public void sendRequestWithDraw(int userId, double amount) throws SQLException {
        String query = "INSERT INTO Transactions (userID, amount, transaction_type, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setDouble(2, amount);
            ps.setString(3, util.Transaction.TYPE_TRANSACTION_WITHDRAW);
            ps.setString(4, util.Transaction.STATUS_TRANSACTION_PENDING);

            ps.executeUpdate();
        }
    }

    public void withDrawing(int transactionID, int userID, double amount, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            connection.setAutoCommit(false);

            // Check owner's balance
            double ownerBalance = 0;
            ps = connection.prepareStatement("SELECT balance FROM Balances WHERE userID = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                ownerBalance = rs.getDouble("balance");
            }
            rs.close();
            ps.close();

            if (ownerBalance < amount || ownerBalance <= 0) {
                request.setAttribute("message", "Insufficient balance.");
                request.getRequestDispatcher(RouterJSP.OWNER_BUDGET_PAGE).forward(request, response);
                return;
            }

            // Get admin ID
            int adminID = 0;
            ps = connection.prepareStatement("SELECT UserID FROM [User] WHERE Role = 'ADMIN' AND LOWER(Email) = ?");
            ps.setString(1, ADMIN_EMAIL.toLowerCase());
            rs = ps.executeQuery();
            if (rs.next()) {
                adminID = rs.getInt("UserID");
            }
            rs.close();
            ps.close();

            if (adminID == 0) {
                connection.rollback();
                request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
                return;
            }

            // Decrease owner's balance
            ps = connection.prepareStatement("UPDATE Balances SET balance = balance - ? WHERE userID = ?");
            ps.setDouble(1, amount);
            ps.setInt(2, userID);
            ps.executeUpdate();
            ps.close();

            // Decrease admin's balance by  amount
            ps = connection.prepareStatement("UPDATE Balances SET balance = balance - ? WHERE userID = ?");
            ps.setDouble(1, amount);
            ps.setInt(2, adminID);
            ps.executeUpdate();
            ps.close();

            // Store transaction for owner
            ps = connection.prepareStatement("UPDATE  Transactions SET status = ? WHERE userID = ? AND transaction_id = ? AND status = ? ");
            //
            ps.setString(1, util.Transaction.STATUS_TRANSACTION_SUCCESS);
            ps.setInt(2, userID);
            ps.setInt(3, transactionID);
            ps.setString(4, util.Transaction.STATUS_TRANSACTION_PENDING);

            ps.executeUpdate();
            ps.close();

            connection.commit();

            request.setAttribute("message", "Withdraw successful.");
            request.setAttribute("type", "success");
            request.getRequestDispatcher(RouterJSP.OWNER_COMFIRM_RESULT_PAGE).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
                request.setAttribute("message", "Withdraw failed.");
                request.getRequestDispatcher(RouterJSP.OWNER_COMFIRM_RESULT_PAGE).forward(request, response);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
