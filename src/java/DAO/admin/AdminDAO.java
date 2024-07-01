package DAO.admin;

import DB.SQLServerConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;
import jakarta.servlet.ServletContext;
import java.util.List;
import model.Request;
import java.sql.CallableStatement;

public class AdminDAO extends SQLServerConnect {

    public AdminDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }
// lay tong so luong user : Khai

    public int getUserCount() throws SQLException {
        String sqlQuery = "SELECT COUNT(*) AS userCount FROM [User]";

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("userCount");
            } else {
                throw new SQLException("Error retrieving user count");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw e;  // Re-throw the exception after logging
        }
    }
    // lay tong so luong phim : Khai

    public int getFilmCount() throws SQLException {
        String sqlQuery = "SELECT COUNT(*) AS MovieCount FROM [Movie]";

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("MovieCount");
            } else {
                throw new SQLException("Error retrieving user count");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw e;  // Re-throw the exception after logging
        }
    }
    // lay tong so luong review : Khai

    public int getReviewCount() throws SQLException {
        String sqlQuery = "SELECT COUNT(*) AS ReviewCount FROM [Review]";

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("ReviewCount");
            } else {
                throw new SQLException("Error retrieving user count");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw e;  // Re-throw the exception after logging
        }
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

    public double getTotalAmountTicket() {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 2. Prepare SQL query to get ticket details with related movie slot price and discount
            String sql = "SELECT t.TicketID, ms.Price, ms.Discount "
                    + "FROM Ticket t "
                    + "INNER JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID";

            stmt = connection.prepareStatement(sql);

            // 3. Execute query
            rs = stmt.executeQuery();

            // 4. Calculate total price
            double totalPrice = 0.0;
            while (rs.next()) {
                double price = rs.getDouble("Price");
                double discount = rs.getDouble("Discount");
                double ticketPrice = price - discount;
                totalPrice += ticketPrice;
            }

            // 5. Output the total price
            return totalPrice;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. Close connections and resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public double getTotalCanteenOrderPrice() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Prepare SQL query to retrieve all canteen orders with details
            String sql = "SELECT ci.Price "
                    + "FROM CanteenItemInOrder cio "
                    + "INNER JOIN CanteenItem ci ON cio.CanteenItemID = ci.CanteenItemID";

            stmt = connection.prepareStatement(sql);

            // Execute query
            rs = stmt.executeQuery();

            // Calculate total price
            double totalPrice = 0;
            while (rs.next()) {
                double price = rs.getDouble("Price");
                totalPrice += price;
            }

            return totalPrice;

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions appropriately
        } finally {
            // Close resources in reverse order of opening
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                // Do not close the connection here, as it might be reused by other methods
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Return 0 or handle appropriately if an error occurs
        return 0;
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
    
    public int getNumberOfPendingRequests() throws SQLException {
        String sqlQuery = "select count(*) as NoPendingRequests from MovieApproval\n" +
                    "where RequestStatus = 'PENDING'";
        ResultSet rs = getResultSet(sqlQuery);
        while(rs.next()) {
            return rs.getInt("NoPendingRequests");
        }
        return 0;
    }
    
    public List<Request> queryAllPendingRequests() throws SQLException {
        List<Request> requests = new ArrayList<>();
        String sqlQuery = "select * from MovieApproval where RequestStatus = 'PENDING'";
        ResultSet rs = getResultSet(sqlQuery);
        while(rs.next()) {
            List<String> genres = new ArrayList<>();
            String sqlQueryGenres = "select * from MovieApprovalInGenre where RequestMovieID = " + rs.getInt("RequestMovieID");
            ResultSet genresRs = getResultSet(sqlQueryGenres);
            while(genresRs.next()) {
                genres.add(genresRs.getString("Genre"));
            }
            Request request = new Request(rs.getInt("RequestMovieID"), rs.getInt("UserID"), rs.getString("Title"), rs.getString("Synopsis"), rs.getString("DatePublished"), rs.getString("ImageURL"), rs.getString("Rating"), rs.getString("MovieStatus"), rs.getString("Country"), rs.getInt("Length"), rs.getString("LinkTrailer"), genres, "PENDING");
            requests.add(request);
        }
        return requests;
    }
    
    public void approveMovieRequest(int requestID) throws SQLException {
        String sql = "{call InsertMovieFromApproval(?)}";
        CallableStatement cs = connection.prepareCall(sql);
        cs.setInt(1, requestID);
        cs.execute();
    }
    
    public void rejectMovieRequest(int requestID) throws SQLException {
        String sql = "{call RejectMovieFromApproval(?)}";
        CallableStatement cs = connection.prepareCall(sql);
        cs.setInt(1, requestID);
        cs.execute();
    }
    
}
