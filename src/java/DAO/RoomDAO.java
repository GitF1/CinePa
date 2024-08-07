package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Cinema;
import model.CinemaChain;
import model.Room;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

public class RoomDAO extends SQLServerConnect {

    private static final Logger LOGGER = Logger.getLogger(RoomDAO.class.getName());

    public RoomDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Room";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomID(rs.getInt("RoomID"));
                room.setCinemaID(rs.getInt("CinemaID"));
                room.setName(rs.getString("Name"));
                room.setType(rs.getString("Type"));
                room.setCapacity(rs.getInt("Capacity"));
                room.setLength(rs.getInt("Length"));
                room.setWidth(rs.getInt("Width"));
                room.setStatus(rs.getString("Status"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching rooms", e);
        }

        return rooms;
    }

    public Room getRoomById(int roomId) {
        Room room = null;
        String sql = "SELECT * FROM Room WHERE RoomID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roomId);
            try (ResultSet rs = st.executeQuery();) {
                if (rs.next()) {
                    room = new Room();
                    room.setRoomID(rs.getInt("RoomID"));
                    room.setCinemaID(rs.getInt("CinemaID"));
                    room.setName(rs.getString("Name"));
                    room.setType(rs.getString("Type"));
                    room.setCapacity(rs.getInt("Capacity"));
                    room.setLength(rs.getInt("Length"));
                    room.setWidth(rs.getInt("Width"));
                    room.setStatus(rs.getString("Status"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching room by ID", e);
        }

        return room;
    }

    public List<Room> getRoomsByCinemaId(int cinemaID) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Room WHERE CinemaID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cinemaID);
            try (ResultSet rs = st.executeQuery();) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomID(rs.getInt("RoomID"));
                    room.setCinemaID(rs.getInt("CinemaID"));
                    room.setName(rs.getString("Name"));
                    room.setType(rs.getString("Type"));
                    room.setCapacity(rs.getInt("Capacity"));
                    room.setLength(rs.getInt("Length"));
                    room.setWidth(rs.getInt("Width"));
                    room.setStatus(rs.getString("Status"));
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching rooms by cinema ID", e);
        }

        return rooms;
    }

    public List<CinemaChain> getAllCinemaChains() {
        List<CinemaChain> cinemaChains = new ArrayList<>();
        String sql = "SELECT * FROM CinemaChain";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CinemaChain cinemaChain = new CinemaChain();
                cinemaChain.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinemaChain.setName(rs.getString("Name"));
                cinemaChain.setInformation(rs.getString("Information"));
                cinemaChain.setAvatar(rs.getString("Avatar"));
                cinemaChain.setBanner(rs.getString("Banner")); // Added Banner field
                cinemaChains.add(cinemaChain);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching cinema chains", e);
        }

        return cinemaChains;
    }

    public List<Cinema> getCinemasByCinemaChainID(int cinemaChainID) {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM Cinema WHERE CinemaChainID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cinemaChainID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setCinemaID(rs.getInt("CinemaID"));
                cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinema.setAddress(rs.getString("Address"));
                cinema.setProvince(rs.getString("Province"));
                cinema.setDistrict(rs.getString("District"));
                cinema.setCommune(rs.getString("Commune"));
                cinema.setAvatar(rs.getString("Avatar"));
                cinemas.add(cinema);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching cinemas by cinema chain ID", e);
        }

        return cinemas;
    }

    public void createRoom(Room room) {
        String sql = "INSERT INTO Room (CinemaID, Name, Type, Capacity, Length, Width, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, room.getCinemaID());
            st.setString(2, room.getName());
            st.setString(3, room.getType());
            st.setInt(4, room.getCapacity());
            st.setInt(5, room.getLength());
            st.setInt(6, room.getWidth());
            st.setString(7, room.getStatus());
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating room", e);
        }
    }

    public void updateRoom(Room room) {
        String sql = "UPDATE Room SET Name=?, Type=?, Capacity=?, Length=?, Width=?, Status=? WHERE RoomID=?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, room.getName());
            st.setString(2, room.getType());
            st.setInt(3, room.getCapacity());
            st.setInt(4, room.getLength());
            st.setInt(5, room.getWidth());
            st.setString(6, room.getStatus());
            st.setInt(7, room.getRoomID());
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating room", e);
        }
    }

    public void deleteRoomById(int roomId) {
        String sql = "DELETE FROM Room WHERE RoomID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roomId);
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting room by ID", e);
        }
    }
    
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM [User] WHERE UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                // Set other user properties
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public CinemaChain getCinemaChainByUserId(int userId) {
        CinemaChain cinemaChain = null;
        String sql = "SELECT cc.* FROM CinemaChain cc INNER JOIN ManagesChain mc ON cc.CinemaChainID = mc.CinemaChainID WHERE mc.UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cinemaChain = new CinemaChain();
                cinemaChain.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinemaChain.setName(rs.getString("Name"));
                cinemaChain.setInformation(rs.getString("Information"));
                cinemaChain.setAvatar(rs.getString("Avatar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cinemaChain;
    }

}
