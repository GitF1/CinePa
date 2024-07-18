/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.transaction;

import DB.SQLServerConnect;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;
import util.Role;
import util.RouterURL;

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

        HttpSession session = request.getSession();
        Integer ownerId = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        if (ownerId == null || !role.equals(Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

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

    public double getBalance(int userID) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        double balance = 0;

        try {

            String query = "SELECT balance FROM Balances WHERE userID = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }

        }

        return balance;
    }

    public void updateBalances(int orderID, double totalCashPaid) throws SQLException {

        String GET_CINEMA_CHAIN_ID_QUERY = "SELECT TOP 1 mc.UserID FROM [Order] o\n"
                + "				JOIN Ticket t ON t.OrderID = o.OrderID \n"
                + "				JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID\n"
                + "				JOIN Room r ON ms.RoomID = r.RoomID \n"
                + "				JOIN Cinema c ON r.CinemaID = c.CinemaID\n"
                + "				JOIN ManagesChain mc ON mc.CinemaChainID = c.CinemaChainID\n"
                + "               WHERE o.OrderID= ?";

        String GET_ADMIN_ID_QUERY
                = "SELECT UserID FROM [User] WHERE Role = 'ADMIN' AND LOWER(Email) = ?";

        String UPDATE_BALANCE_QUERY
                = "UPDATE Balances SET balance = balance + ? WHERE userID = ?";

        String ADMIN_EMAIL = "cinepa.org@gmail.com";

        double amountToIncrease = totalCashPaid * 0.95;
        int cinemaChainUserID = 0;
        int adminUserID = 0;

        try (PreparedStatement getCinemaChainIDStmt = connection.prepareStatement(GET_CINEMA_CHAIN_ID_QUERY); PreparedStatement getAdminIDStmt = connection.prepareStatement(GET_ADMIN_ID_QUERY); PreparedStatement updateBalanceStmt = connection.prepareStatement(UPDATE_BALANCE_QUERY)) {

            // Get the CinemaChainID associated with the order
            getCinemaChainIDStmt.setInt(1, orderID);
            try (ResultSet rs = getCinemaChainIDStmt.executeQuery()) {
                if (rs.next()) {
                    cinemaChainUserID = rs.getInt("UserID");
                }
            }

            // Get the Admin ID
            getAdminIDStmt.setString(1, ADMIN_EMAIL.toLowerCase());

            try (ResultSet rs = getAdminIDStmt.executeQuery()) {
                if (rs.next()) {
                    adminUserID = rs.getInt("UserID");
                }
            }

            // Update the balance of the cinema chain owner
            if (cinemaChainUserID != 0) {
                updateBalanceStmt.setDouble(1, amountToIncrease);
                updateBalanceStmt.setInt(2, cinemaChainUserID);
                updateBalanceStmt.executeUpdate();
            }

            // Update the balance of the admin
            if (adminUserID != 0) {
                updateBalanceStmt.setDouble(1, totalCashPaid);
                updateBalanceStmt.setInt(2, adminUserID);
                updateBalanceStmt.executeUpdate();
            }
        }
    }

    public List<Transaction> getTransactionsByUserId(int userId) {

        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT transaction_id, userID, amount, transaction_type, transaction_date, status FROM Transactions WHERE userID = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int transactionId = rs.getInt("transaction_id");
                    double amount = rs.getDouble("amount");
                    String transactionType = rs.getString("transaction_type");
                    Timestamp transactionDate = rs.getTimestamp("transaction_date");
                    String status = rs.getString("status");
                    Transaction transaction = new Transaction(transactionId, userId, amount, transactionType, transactionDate, status);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<Transaction> getTransactionsWithDrawPending(int limit, int offset) {

        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT transaction_id, userID, amount, transaction_type, transaction_date, status "
                + "FROM Transactions "
                + "WHERE transaction_type = ? AND status = ? "
                + "ORDER BY transaction_date DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, util.Transaction.TYPE_TRANSACTION_WITHDRAW);  // Assuming 'WITHDRAW' is your transaction type
            ps.setString(2, util.Transaction.STATUS_TRANSACTION_PENDING);   // Status 'PENDING'
            ps.setInt(3, offset);
            ps.setInt(4, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("userID");
                    int transactionId = rs.getInt("transaction_id");
                    double amount = rs.getDouble("amount");
                    String transactionType = rs.getString("transaction_type");
                    Timestamp transactionDate = rs.getTimestamp("transaction_date");
                    String status = rs.getString("status");
                    Transaction transaction = new Transaction(transactionId, userId, amount, transactionType, transactionDate, status);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

}
