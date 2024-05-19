/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBContext;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author VINHNQ
 */
public class UserDAOImpl extends DBContext {

 

  public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                User c = new User(rs.getString("fullName"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
   
    public void updatestatus(User user) {
        String sql = "UPDATE [User] SET status = ?, code = ? WHERE email = ?";

        // Use try-with-resources for automatic resource management
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, user.getStatus());
            st.setString(2, user.getCode());
            st.setString(3, user.getEmail());

            st.executeUpdate();
        } catch (SQLException e) {
            // Properly handle the exception, log it, or rethrow as a custom exception
            e.printStackTrace(); // Replace with appropriate logging in production code
        }
    }

    public boolean checkExistUsername(String username) {
        boolean duplicate = false;
        String sql = "SELECT * FROM [User] WHERE username = ?";

        // Use try-with-resources for automatic resource management
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, username);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    duplicate = true;
                }
            }
        } catch (SQLException e) {
            // Properly handle the exception, log it, or rethrow as a custom exception
            e.printStackTrace(); // Replace with appropriate logging in production code
        }

        return duplicate;
    }

    public boolean checkExistEmail(String email) {
        boolean duplicate = false;
        String sql = "SELECT 1 FROM [User] WHERE email = ?";

        // Use try-with-resources for automatic resource management
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    duplicate = true;
                }
            }
        } catch (SQLException e) {
            // Properly handle the exception, log it, or rethrow as a custom exception
            e.printStackTrace(); // Replace with appropriate logging in production code
        }

        return duplicate;
    }

    public void insertregister(User user) {
        String sql = "INSERT INTO [dbo].[User]\n"
                + "           ([Fullname]\n"
                + "           ,[Username]\n"
                + "           ,[Email]\n"
                + "           ,[Password]\n"
                + "           ,[Code]\n"
                + "           ,[Status]\n"
                + "           ,[Role])\n"
                + "     VALUES  (?,?,?,?,?,?,?)";
        // Use try-with-resources for automatic resource management
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, user.getFullName());
            st.setString(2, user.getUsername());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPassword());
            st.setString(5, user.getCode());
            st.setInt(6, user.getStatus());
            st.setString(7, user.getRole());

            st.executeUpdate();
        } catch (SQLException e) {
            // Properly handle the exception, log it, or rethrow as a custom exception
            e.printStackTrace(); // Replace with appropriate logging in production code
        }
    }
    
    

}
