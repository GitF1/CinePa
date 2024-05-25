package controller;

import DB.SQLServerConnect;
import java.sql.Connection;
import jakarta.servlet.ServletContext;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import model.User;
import java.sql.SQLException;

public class UserDAO {

    public static UserDAO getInstance() {
        return new UserDAO();
    }

    public User getUserById(String id, ServletContext context) throws Exception {

        User user = null;

        DB.SQLServerConnect dbConnect = new SQLServerConnect();

        java.sql.Connection connection = dbConnect.connect(context);

        String sql = "SELECT * FROM [User] WHERE UserID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        }
        return user;

    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("UserID"));
        user.setAvatarLink(rs.getString("AvatarLink"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setEmail(rs.getString("Email"));
        user.setFullName(rs.getString("FullName"));
        user.setBirthday(rs.getDate("Birthday"));
        user.setAddress(rs.getString("Address"));
        user.setProvince(rs.getString("Province"));
        user.setDistrict(rs.getString("District"));
        user.setCommune(rs.getString("Commune"));
        return user;
    }

    public boolean updateUser(User user, ServletContext context) throws Exception {

        DB.SQLServerConnect dbConnect = new SQLServerConnect();

        try (Connection connection = dbConnect.connect(context)) {

            String sql = "UPDATE [User] SET AvatarLink=?, Username=?, Password=?, Email=?, Fullname=?, Birthday=?, Address=?, Province=?, District=?, Commune=? WHERE UserID=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, user.getAvatarLink());
                pstmt.setString(2, user.getUsername());
                pstmt.setString(3, user.getPassword());
                pstmt.setString(4, user.getEmail());
                pstmt.setString(5, user.getFullName());
                if (user.getBirthday() != null) {
                    pstmt.setDate(6, new java.sql.Date(user.getBirthday().getTime()));
                } else {
                    pstmt.setNull(6, java.sql.Types.DATE);
                }
                pstmt.setString(7, user.getAddress());
                pstmt.setString(8, user.getProvince());
                pstmt.setString(9, user.getDistrict());
                pstmt.setString(10, user.getCommune());
                pstmt.setInt(11, user.getUserID());
                int updated = pstmt.executeUpdate();
                return updated > 0; 

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;

        }
    }
    
   public void updateUserPassword(String id, String newPassword, ServletContext context) {
        
       SQLServerConnect dbConnect = new SQLServerConnect();
        
        try (Connection connection = dbConnect.connect(context)) {

            String sql = "UPDATE [User] SET Password=? WHERE UserID=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
