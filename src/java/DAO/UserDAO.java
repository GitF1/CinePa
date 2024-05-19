/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.SQLServerConnect;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.ServletContext;
import java.sql.Statement;
import java.util.List;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        String sqlQuery = "SELECT *\n" +
                    "FROM [User]\n" +
                    "WHERE (Username = '" + username_email + "' " +  "OR Email = '" + username_email + "'" + ")\n" +
                    "AND Password = '" + password + "'";
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
        if(rs.next()) return rs.getString("Code");
        return null;
    }
    
    public boolean updateUserPassword(String email, String password) throws SQLException {
        String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        String sql = "update [User] set Password = '" + hash + "'" + " where Email = '" + email + "'";
        Statement st = connection.createStatement();
        int kq = st.executeUpdate(sql);
        return kq == 1;
    }
    
    public static void main(String[] args) {
    }
}
