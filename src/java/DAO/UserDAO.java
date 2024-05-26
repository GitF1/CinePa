/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.SQLServerConnect;
import java.sql.SQLException;
import jakarta.servlet.ServletContext;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

/**
 *
 * @author Admin
 */
public class UserDAO extends SQLServerConnect {

    public UserDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public ResultSet getResultSet(String sqlQuery) throws SQLException {
        ResultSet rs = null;
        try {
            PreparedStatement per = connection.prepareStatement(sqlQuery);
            rs = per.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public boolean checkLogin(String username_email, String password) throws SQLException {
        String sqlQuery = "SELECT *\n"
                + "FROM [User]\n"
                + "WHERE (Username = '" + username_email + "' " + "OR Email = '" + username_email + "'" + ")\n"
                + "AND Password = '" + password + "'\n"
                + "AND Status = 1";
        System.out.println(sqlQuery);
        ResultSet rs = getResultSet(sqlQuery);
        return rs.next();
    }

    public boolean checkExistEmail(String email) {
        boolean duplicate = false;
        String sql = "SELECT 1 FROM [User] WHERE email = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    duplicate = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return duplicate;
    }

    public boolean updateUserCode(String email, String code) throws SQLException {
        String sql = "update [User] set Code = '" + code + "'" + " where Email = '" + email + "'";
        Statement st = connection.createStatement();
        int kq = st.executeUpdate(sql);
        return kq == 1;
    }

    public String getUserCode(String email) throws SQLException {
        ResultSet rs = getResultSet("select * from [User] where Email = '" + email + "'");
        if (rs.next()) {
            return rs.getString("Code");
        }
        return null;
    }
    
    //DuyND-Get Role By email or username
    public String getUserRole(String username_email) throws SQLException {
        String sqlQuery = "SELECT *\n"
                + "FROM [User]\n"
                + "WHERE (Username = '" + username_email + "' " + "OR Email = '" + username_email + "'" + ")\n"
        
                + "AND Status = 1";
        ResultSet rs = getResultSet(sqlQuery);
        if (rs.next()) {
            return rs.getString("Role");
        }
        return null;
    }
    
//    DuyND - get username by username or email:)
    
    public String getUsername(String username_email) throws SQLException {
        String sqlQuery = "SELECT *\n"
                + "FROM [User]\n"
                + "WHERE (Username = '" + username_email + "' " + "OR Email = '" + username_email + "'" + ")\n"
        
                + "AND Status = 1";
        ResultSet rs = getResultSet(sqlQuery);
        if (rs.next()) {
            return rs.getString("Username");
        }
        return null;
    }

    public boolean updateUserPassword(String id, String password) throws SQLException {
        String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        String sql = "update [User] set Password = '" + hash + "'" + " where id = '" + id + "'";
        Statement st = connection.createStatement();
        int kq = st.executeUpdate(sql);
        return kq == 1;
    }

    public User getUserById(String id) throws Exception {

        User user = null;

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
        user.setFullName(rs.getString("Fullname"));
        user.setBirthday(rs.getDate("Birthday"));
        user.setAddress(rs.getString("Address"));
        user.setProvince(rs.getString("Province"));
        user.setDistrict(rs.getString("District"));
        user.setCommune(rs.getString("Commune"));
        return user;
    }

    public boolean updateUser(User user) throws SQLException {
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
            throw e; // Rethrow SQLException for handling in the calling method
        }
    }

}
