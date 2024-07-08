/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author PC
 */
public class TransactionDAO extends SQLServerConnect {

    public TransactionDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public boolean addBalance(int userID, double amount) {
        String selectBalanceSQL = "SELECT balance FROM Balances WHERE userID = ?";
        String updateBalanceSQL = "UPDATE Balances SET balance = balance + ? WHERE userID = ?";
        String insertBalanceSQL = "INSERT INTO Balances (userID, balance) VALUES (?, ?)";

        try {
            connection.setAutoCommit(false);

            // Check if the user already has a balance record
            PreparedStatement selectStmt = connection.prepareStatement(selectBalanceSQL);
            selectStmt.setInt(1, userID);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // If the user has a balance record, update it
                PreparedStatement updateStmt = connection.prepareStatement(updateBalanceSQL);
                updateStmt.setDouble(1, amount);
                updateStmt.setInt(2, userID);
                updateStmt.executeUpdate();
            } else {
                // If the user does not have a balance record, insert a new one
                PreparedStatement insertStmt = connection.prepareStatement(insertBalanceSQL);
                insertStmt.setInt(1, userID);
                insertStmt.setDouble(2, amount);
                insertStmt.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void handleWithdraw(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int ownerId = Integer.parseInt(request.getParameter("ownerID"));
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {

            connection.setAutoCommit(false);

            PreparedStatement ps1 = connection.prepareStatement("SELECT balance FROM Balances WHERE owner_id = ?");

            ps1.setInt(1, ownerId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");

                if (currentBalance >= amount) {
                    PreparedStatement ps2 = connection.prepareStatement("UPDATE Balances SET balance = balance - ? WHERE userID = ?");
                    ps2.setDouble(1, amount);
                    ps2.setInt(2, ownerId);
                    ps2.executeUpdate();

                    PreparedStatement ps3 = connection.prepareStatement("INSERT INTO Transactions (userID, amount, type, date) VALUES (?, ?, 'withdraw', CURRENT_TIMESTAMP)");
                    ps3.setInt(1, ownerId);
                    ps3.setDouble(2, amount);
                    ps3.executeUpdate();

                    connection.commit();
                    request.setAttribute("message", "Withdrawal successful!");
                } else {
                    request.setAttribute("message", "Insufficient balance!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error processing transaction.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("owner-dashboard.jsp");
        dispatcher.forward(request, response);
    }

    public void handleViewBalance(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int ownerId = Integer.parseInt(request.getParameter("ownerId"));

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT balance FROM Balances WHERE userID = ?");
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                request.setAttribute("balance", balance);
            } else {
                request.setAttribute("balance", 0.00);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error fetching balance.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("view-balance.jsp");
        dispatcher.forward(request, response);
    }
}
