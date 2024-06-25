package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

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

    public List<User> listWaitingUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.* FROM [User] u JOIN RegisterOwner ro ON u.UserID = ro.UserID WHERE ro.Status = 'Waiting'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("AvatarLink"),
                        rs.getString("Role"),
                        rs.getString("Fullname"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Bio"),
                        rs.getString("Email"),
                        rs.getDate("Birthday"),
                        rs.getString("Address"),
                        rs.getBoolean("IsBanned"),
                        rs.getInt("LevelPremiumID"),
                        rs.getFloat("AccountBalance"),
                        rs.getInt("BonusPoint"),
                        rs.getString("Province"),
                        rs.getString("District"),
                        rs.getString("Commune"),
                        rs.getString("Code"),
                        rs.getInt("Status")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void changeRegisterStatus(String id, String status) {
        String sql = "UPDATE RegisterOwner SET Status = ? WHERE UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Thiết lập các tham số cho PreparedStatement
            statement.setString(1, status);
            statement.setString(2, id);

            // Thực thi truy vấn để cập nhật bản ghi
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Có thể xử lý lỗi ở đây
        }
    }

    public void changeRoleToOwner(String id) {
        String sql = "UPDATE [User] SET Role = 'OWNER' WHERE UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Thiết lập các tham số cho PreparedStatement
            statement.setString(1, id);

            // Thực thi truy vấn để cập nhật bản ghi
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Có thể xử lý lỗi ở đây
        }
    }
}
