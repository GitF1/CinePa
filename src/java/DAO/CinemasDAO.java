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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import model.CinemaChain;

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
        String sql = "INSERT INTO Cinema (CinemaChainID, Address, Province, District, Commune, Avatar, Rating) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 0)";
        
        try {
            
            PreparedStatement st = connection.prepareStatement(sql);

            st.setInt(1, cinema.getCinemaChainID());
            st.setString(2, cinema.getAddress());
            st.setString(3, cinema.getProvince());
            st.setString(4, cinema.getDistrict());
            st.setString(5, cinema.getCommune());
            st.setString(6, cinema.getAvatar());

            st.executeUpdate();
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating room", e);
        }
    }
}
