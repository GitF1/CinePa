/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import model.Movie;
import model.MovieInGenre;

/**
 *
 * @author VINHNQ
 */
public class MovieDAO extends SQLServerConnect {

    public MovieDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    //
    public List<Movie> getAllMovie() {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT * FROM Movie";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("MovieID"),
                        rs.getString("Title"),
                        rs.getString("Synopsis"),
                        rs.getString("DatePublished"),
                        rs.getString("ImageURL"),
                        rs.getDouble("Rating"),
                        rs.getString("Status"),
                        rs.getString("Country")
                );
                list.add(movie);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    // New method to get all MovieInGenre
    public List<MovieInGenre> getAllMovieInGenre() {
        List<MovieInGenre> list = new ArrayList<>();
        String sql = "SELECT * FROM MovieInGenre";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                MovieInGenre movieInGenre = new MovieInGenre(
                        rs.getInt("MovieID"),
                        rs.getString("Genre")
                );
                list.add(movieInGenre);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<String> getGenresForMovie(int movieID) {
    List<String> genres = new ArrayList<>();
    String sql = "SELECT Genre FROM MovieInGenre WHERE MovieID = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, movieID);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            genres.add(rs.getString("Genre"));
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return genres;
}

    
    // New method to get movies by status
    public List<Movie> getMoviesByStatus(String status) {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE Status = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("MovieID"),
                        rs.getString("Title"),
                        rs.getString("Synopsis"),
                        rs.getString("DatePublished"),
                        rs.getString("ImageURL"),
                        rs.getDouble("Rating"),
                        rs.getString("Status"),
                        rs.getString("Country")
                );
                list.add(movie);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

}
