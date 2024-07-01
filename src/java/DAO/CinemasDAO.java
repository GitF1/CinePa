/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.SQLServerConnect;
import java.sql.SQLException;
import jakarta.servlet.ServletContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;

/**
 *
 * @author Admin
 */
public class CinemasDAO extends SQLServerConnect {

    private static final Logger LOGGER = Logger.getLogger(RoomDAO.class.getName());

    public CinemasDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public void createCinema(Cinema cinema) {
        String sql = "INSERT INTO Cinema (CinemaChainID, Address, Province, District, Commune, Avatar, Rating) "
                + "VALUES (?, ?, ?, ?, ?, ?, 0)";

        try {

            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, cinema.getCinemaChainID());
            st.setString(2, cinema.getAddress());
            st.setString(3, cinema.getProvince());
            st.setString(4, cinema.getDistrict());
            st.setString(5, cinema.getCommune());
            st.setString(6, cinema.getAvatar());

            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating room", e);
        }
    }

    public List<Cinema> getCinemasByCinemaChainID(int cinemaChainID, Integer limit, Integer offset) {

        if (limit == null || limit < 0) {
            limit = 20;
        }
        if (offset == null || offset < 0) {
            offset = 0;
        }
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT CC.Avatar, C.CinemaID, C.CinemaChainID, C.Address, C.Province, C.District, C.Commune FROM Cinema C JOIN CinemaChain CC ON CC.CinemaChainID = C.CinemaChainID WHERE C.CinemaChainID = ? ORDER BY CinemaID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cinemaChainID);
            st.setInt(2, offset);
            st.setInt(3, limit);
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

    public int getCinemaChainIDByCinemaID(int cinemaID) {
        int cinemaChainID = 0;
        String sql = "SELECT CinemaChainID FROM Cinema WHERE CinemaID = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, cinemaID);
            try (ResultSet resultSet = st.executeQuery()) {
                if (resultSet.next()) {
                    cinemaChainID = resultSet.getInt("CinemaChainID");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching cinema chain ID by cinema ID", e);
        }

        return cinemaChainID;
    }

    public void updateCinema(Cinema cinema) {
        String sql = "UPDATE Cinema SET Address = ?, Province = ?, District = ?, Commune = ?, Avatar = ? WHERE CinemaID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, cinema.getAddress());
            st.setString(2, cinema.getProvince());
            st.setString(3, cinema.getDistrict());
            st.setString(4, cinema.getCommune());
            st.setString(5, cinema.getAvatar());
            st.setInt(6, cinema.getCinemaID());
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating cinema", e);
        }
    }

    public void deleteCinema(int cinemaID) {
        String sql = "DELETE FROM Cinema WHERE CinemaID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting cinema", e);
        }
    }

    public void deleteCinemaAndRooms(int cinemaID) {
        String deleteRoomsQuery = "DELETE FROM Room WHERE CinemaID = ?";
        String deleteCinemaQuery = "DELETE FROM Cinema WHERE CinemaID = ?";

        try (PreparedStatement deleteRoomsStatement = connection.prepareStatement(deleteRoomsQuery); PreparedStatement deleteCinemaStatement = connection.prepareStatement(deleteCinemaQuery)) {

            // Disable auto-commit to start a transaction
            connection.setAutoCommit(false);

            // First, delete all rooms associated with the cinema
            deleteRoomsStatement.setInt(1, cinemaID);
            deleteRoomsStatement.executeUpdate();

            // Now, delete the cinema
            deleteCinemaStatement.setInt(1, cinemaID);
            deleteCinemaStatement.executeUpdate();

            // Commit the transaction
            connection.commit();

        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error rolling back transaction", ex);
            }
            LOGGER.log(Level.SEVERE, "Error deleting cinema and rooms", e);
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.setAutoCommit(true); // Reset auto-commit to true
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error resetting auto-commit or closing connection", ex);
            }
        }
    }

    public Cinema getCinemaByID(int cinemaID) {
        Cinema cinema = null;
        String sql = "SELECT * FROM Cinema WHERE CinemaID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cinemaID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    cinema = new Cinema();
                    cinema.setCinemaID(rs.getInt("CinemaID"));
                    cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
                    cinema.setAddress(rs.getString("Address"));
                    cinema.setProvince(rs.getString("Province"));
                    cinema.setDistrict(rs.getString("District"));
                    cinema.setCommune(rs.getString("Commune"));
                    cinema.setAvatar(rs.getString("Avatar"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching cinema by ID", e);
        }
        return cinema;
    }

}
