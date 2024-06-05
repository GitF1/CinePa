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
import model.User;

/**
 *
 * @author Admin
 */
public class CinemaChainDAO extends SQLServerConnect {

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

    public ArrayList<String> getCinemaChainList() throws SQLException {
        ArrayList<String> cinemaNames = new ArrayList<>();
        String sqlQuery = "SELECT Name FROM CinemaChain";

        ResultSet rs = getResultSet(sqlQuery);
        while (rs.next()) {
            cinemaNames.add(rs.getString("Name"));
        }

        return cinemaNames;
    }

    public static void main(String[] args) {//for testing, delete at will

    }
}
