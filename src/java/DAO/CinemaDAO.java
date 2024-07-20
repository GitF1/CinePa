/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.*;
import model.Cinema;

/**
 *
 * @author PC
 */
public class CinemaDAO extends SQLServerConnect {

    public CinemaDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public Cinema getCinemaByID(int cinemaID) {

        Cinema cinema = new Cinema();
        String query = "SELECT CinemaID, CinemaChainID, Address, Province, District, Commune FROM Cinema WHERE CinemaID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, cinemaID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                
                int cinemaChainID = rs.getInt("CinemaChainID");
                String address = rs.getString("Address");
                String province = rs.getString("Province");
                String district = rs.getString("District");
                String commune = rs.getString("Commune");

                cinema.setAddress(address);
                cinema.setCinemaChainID(cinemaChainID);
                cinema.setProvince(province);
                cinema.setDistrict(district);
                cinema.setCommune(commune);
            }
            
            return cinema;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
