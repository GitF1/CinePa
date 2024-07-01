/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.owner;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Request;

/**
 *
 * @author ACER
 */
public class OwnerDAO extends SQLServerConnect {
    
    public OwnerDAO(ServletContext context) throws Exception {
        super();
        connect(context);
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
    
    public List<Request> queryMovieRequests(int ownerID) throws SQLException {
        List<Request> requests = new ArrayList<>();
        String sqlQuery = "select * from MovieApproval where userID = " + ownerID;
        ResultSet rs = getResultSet(sqlQuery);
        while(rs.next()) {
            List<String> genres = new ArrayList<>();
            String sqlQueryGenres = "select * from MovieApprovalInGenre where RequestMovieID = " + rs.getInt("RequestMovieID");
            ResultSet genresRs = getResultSet(sqlQueryGenres);
            while(genresRs.next()) {
                genres.add(genresRs.getString("Genre"));
            }
            Request request = new Request(rs.getInt("RequestMovieID"), rs.getInt("UserID"), rs.getString("Title"), rs.getString("Synopsis"), rs.getString("DatePublished"), rs.getString("ImageURL"), rs.getString("Rating"), rs.getString("MovieStatus"), rs.getString("Country"), rs.getInt("Length"), rs.getString("LinkTrailer"), genres, rs.getString("RequestStatus"));
            requests.add(request);
        }
        return requests;
    }
}
