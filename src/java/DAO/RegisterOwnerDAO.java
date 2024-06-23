package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterOwnerDAO extends SQLServerConnect {

    public RegisterOwnerDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public String checkRegisterOwner(String id) {
        String status = null;
        String sql = "SELECT Status FROM RegisterOwner WHERE UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                status = rs.getString("Status");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return status;
    }

    public void addRegisterOwner(int userID) {
        String sql = "INSERT INTO RegisterOwner (UserID, Status) VALUES (?, ?)";
        try (
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            statement.setString(2, "Waiting");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
}
