/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import model.MovieCinema;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author VINHNQ
 */
public class MovieCinemaDAO extends SQLServerConnect{
    
    public MovieCinemaDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }
    
    public List<MovieCinema> getMoviesByCinemaID(int cinemaID) {
        List<MovieCinema> list = new ArrayList<>();
        String sql = "SELECT * FROM MovieCinema WHERE CinemaID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cinemaID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                MovieCinema movieCinema = new MovieCinema(
                        rs.getInt("MovieID"),
                        rs.getInt("CinemaID"),
                        rs.getString("Status")
                );
                list.add(movieCinema);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addMovieToCinema(int movieID, int cinemaID, String status) {
        String sql = "INSERT INTO MovieCinema (MovieID, CinemaID, Status) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, movieID);
            st.setInt(2, cinemaID);
            st.setString(3, status);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteMovieFromCinema(int movieID, int cinemaID) {
        String sql = "DELETE FROM MovieCinema WHERE MovieID = ? AND CinemaID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, movieID);
            st.setInt(2, cinemaID);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<MovieCinema> getMoreMovies(int cinemaID, int lastMovieID) {
        List<MovieCinema> movies = new ArrayList<>();
        try {
            String sql = "SELECT * FROM MovieCinema WHERE cinemaID = ? AND movieID > ? LIMIT 10";
            try (            PreparedStatement ps = connection.prepareStatement(sql);
) {
                ps.setInt(1, cinemaID);
                ps.setInt(2, lastMovieID);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        MovieCinema movie = new MovieCinema(
                            rs.getInt("movieID"),
                            rs.getInt("cinemaID"),
                            rs.getString("status")
                        );
                        movies.add(movie);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return movies;
    }
}
