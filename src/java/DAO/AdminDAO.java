package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.util.ArrayList;
import model.User;

import DB.SQLServerConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;
import jakarta.servlet.ServletContext;

public class AdminDAO extends SQLServerConnect {

    public AdminDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    // Method to get banned and unbanned users
    public ArrayList<User> getBanAndUnbanUser(String type, String isBanned) throws SQLException {
        ArrayList<User> userList = new ArrayList<>();
        ResultSet rs = null;
        String sqlQuery = "SELECT TOP 10 UserID, Username, Fullname, Email FROM [User] WHERE Role = ? AND IsBanned = ?";

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, type);
            ps.setInt(2, Integer.parseInt(isBanned));
            rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String username = rs.getString("Username");
                String fullName = rs.getString("Fullname");
                String email = rs.getString("Email");

                User user = new User();
//                set thong tin : 
                user.setUserID(userID);
                user.setUsername(username);
                user.setFullName(fullName);
                user.setEmail(email);

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return userList;
    }

    public void banUser(String id, String isBanned) throws SQLException {
        String sqlQuery = "UPDATE [User] SET IsBanned = ? WHERE UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            // Convert isBanned to int (0 or 1)
            int isBannedValue = Integer.parseInt(isBanned);

            ps.setInt(1, isBannedValue);
            ps.setString(2, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Search users based on Role and string
    public ArrayList<User> searchBanUsers(String type, String str) throws SQLException {
        ArrayList<User> userList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM [User] WHERE Role = ? AND (Username LIKE ? OR Fullname LIKE ?)";

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, type);
            ps.setString(2, "%" + str.trim() + "%");
            ps.setString(3, "%" + str.trim() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String username = rs.getString("Username");
                String fullName = rs.getString("Fullname");
                String email = rs.getString("Email");
                boolean isBanned = rs.getBoolean("IsBanned");

                User user = new User();
                // set cac truong : 
                user.setUserID(userID);
                user.setUsername(username);
                user.setEmail(email);
                user.setFullName(fullName);
                user.setIsBanned(isBanned);
                
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

}
