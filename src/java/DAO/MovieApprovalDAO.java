/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.Movie;
import model.User;
import java.sql.ResultSet;
/**
 *
 * @author ACER
 */
public class MovieApprovalDAO extends MovieDAO {

    public MovieApprovalDAO(ServletContext context) throws Exception {
        super(context);
    }

    public int requestMovie(int userID, Movie movie) throws ParseException {
        String generatedColumns[] = { "RequestMovieID" };
        String sql = "INSERT INTO MovieApproval\n"
                + "           ([UserID]\n"
                + "           ,[Title]\n"
                + "           ,[DatePublished]\n"
                + "           ,[ImageURL]\n"
                + "           ,[Rating]\n"
                + "           ,[Synopsis]\n"
                + "           ,[Country]\n"
                + "           ,[Year]\n"
                + "           ,[Length]\n"
                + "           ,[LinkTrailer]\n"
                + "           ,[MovieStatus])\n"
                + "     VALUES  (?,?,?,?,0,?,?,?,?,?,?)";
        
        try {

            PreparedStatement st = connection.prepareStatement(sql, generatedColumns);
            
            st.setInt(1, userID);
            st.setString(2, movie.getTitle());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date result = df.parse(movie.getDatePublished());
            java.sql.Date sqlDate = new java.sql.Date(result.getTime());
            st.setDate(3, sqlDate);//need to turn into sql date
            st.setString(4, movie.getImageURL());
            st.setString(5, movie.getSynopsis());
            st.setString(6, movie.getCountry());
            st.setInt(7, result.getYear() + 1900);//need to turn into year int
            st.setInt(8, movie.getLength());
            st.setString(9, movie.getTrailerLink());
            st.setString(10, movie.getStatus());

            int affectedRows = st.executeUpdate();
            if(affectedRows > 0) {
                ResultSet rs = st.getGeneratedKeys();
                int newRequestMovieID;
                if(rs.next()) {
                    newRequestMovieID = rs.getInt(1);
                    return newRequestMovieID;
                }
            }
            


        } catch (SQLException e) {
            // Properly handle the exception, log it, or rethrow as a custom exception
            e.printStackTrace(); // Replace with appropriate logging in production code
        }
        return -1;
    }
    
    public void insertMovieApprovalInGenre(int requestMovieID, String[] genres) {
        String sql = "INSERT INTO [dbo].[MovieApprovalInGenre]\n"
                + "           ([RequestMovieID]\n"
                + "           ,[Genre])\n"
                + "     VALUES  (?,?)";
        // Use try-with-resources for automatic resource management
        for (String s : genres) {
            try {

                PreparedStatement st = connection.prepareStatement(sql);

                st.setInt(1, requestMovieID);
                st.setString(2, s);
                st.executeUpdate();

            } catch (SQLException e) {
                // Properly handle the exception, log it, or rethrow as a custom exception
                e.printStackTrace(); // Replace with appropriate logging in production code
            }
        }
    }
}
