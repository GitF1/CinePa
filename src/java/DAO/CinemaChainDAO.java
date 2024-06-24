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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CinemaChain;
import model.User;

/**
 *
 * @author Admin
 */
public class CinemaChainDAO extends SQLServerConnect {

    private static final Logger LOGGER = Logger.getLogger(CinemaChainDAO.class.getName());

    //Chỉ để query cinema chain trong navbar
    public CinemaChainDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }
//    public ResultSet getCinemaChainList() throws SQLException{
//        ResultSet rs = null;
//        String sqlQuery = "SELECT *\n"
//                + "FROM CinemaChain\n"
//                ;
//        try {
//            PreparedStatement per = connection.prepareStatement(sqlQuery);
//            rs = per.executeQuery();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return rs;
//    }

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

    // của duy sửa lại bên dưới
    public ArrayList<String> getCinemaChainList() throws SQLException {
        ArrayList<String> cinemaNames = new ArrayList<>();
        String sqlQuery = "SELECT Name FROM CinemaChain";

        ResultSet rs = getResultSet(sqlQuery);
        while (rs.next()) {
            cinemaNames.add(rs.getString("Name"));
        }

        return cinemaNames;
    }

    public ArrayList<CinemaChain> getCinemaChainListItem() throws SQLException {
        ArrayList<CinemaChain> cinemaChains = new ArrayList<>();
        String sqlQuery = "SELECT CinemaChainID, Name FROM CinemaChain";

        ResultSet rs = getResultSet(sqlQuery);
        while (rs.next()) {
            CinemaChain chain = new CinemaChain();
            chain.setCinemaChainID(rs.getInt("CinemaChainID"));
            chain.setName(rs.getString("Name"));
            cinemaChains.add(chain);
        }

        return cinemaChains;
    }

    public CinemaChain getCinemaChainByName(String name) {
        CinemaChain cinemaChain = null;
        String query = "SELECT * FROM CinemaChain WHERE Name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cinemaChain = new CinemaChain();
                cinemaChain.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinemaChain.setName(rs.getString("Name"));
                cinemaChain.setInformation(rs.getString("Information"));
                cinemaChain.setAvatar(rs.getString("Avatar"));
                cinemaChain.setBanner(rs.getString("Banner"));  // Add this line
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaChain;
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
                cinemaChain.setBanner(rs.getString("Banner"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cinemaChain;
    }

    public CinemaChain getCinemaChainByID(int cinemaChainID) {
        CinemaChain cinemaChain = new CinemaChain();
        String query = "SELECT CinemaChainID, Name, Information, Avatar FROM CinemaChain WHERE CinemaChainID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, cinemaChainID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                String information = rs.getString("Information");
                String avatar = rs.getString("Avatar");
                String banner = rs.getString("Banner");

                cinemaChain = new CinemaChain(cinemaChainID, name, information, avatar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaChain;

    }

    public void updateCinemaChain(CinemaChain cinemaChain) {
        String sql = "UPDATE CinemaChain SET Name = ?, Information = ?, Avatar = ?, Banner = ? WHERE CinemaChainID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, cinemaChain.getName());
            st.setString(2, cinemaChain.getInformation());
            st.setString(3, cinemaChain.getAvatar());
            st.setString(4, cinemaChain.getBanner());  // Add this line
            st.setInt(5, cinemaChain.getCinemaChainID());
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating room", e);
        }
    }

    public void createCinemaChain(CinemaChain cinemaChain, int userId) {

        String sqlCinemaChain = "INSERT INTO CinemaChain (Name, Information, Avatar, Banner) VALUES (?, ?, ?, ?)";
        String sqlManagesChain = "INSERT INTO ManagesChain (UserID, CinemaChainID) VALUES (?, ?)";
        
        try {
            connection.setAutoCommit(false);
            PreparedStatement st1 = connection.prepareStatement(sqlCinemaChain, PreparedStatement.RETURN_GENERATED_KEYS);
            st1.setString(1, cinemaChain.getName());
            st1.setString(2, cinemaChain.getInformation());
            st1.setString(3, cinemaChain.getAvatar());
            st1.setString(4, cinemaChain.getBanner());  // Add this line
            st1.executeUpdate();
            ResultSet generatedKeys = st1.getGeneratedKeys();
            int cinemaChainId = 0;
            if (generatedKeys.next()) {
                cinemaChainId = generatedKeys.getInt(1);
            }
            PreparedStatement st2 = connection.prepareStatement(sqlManagesChain);
            st2.setInt(1, userId);
            st2.setInt(2, cinemaChainId);
            st2.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
