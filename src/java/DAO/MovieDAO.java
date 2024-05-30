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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    //
    public Map<Integer, List<String>> getAllMovieGenres() {
        Map<Integer, List<String>> movieGenresMap = new HashMap<>();
        String sql = "SELECT MovieID, Genre FROM MovieInGenre";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int movieId = rs.getInt("MovieID");
                String genre = rs.getString("Genre");
                movieGenresMap.computeIfAbsent(movieId, k -> new ArrayList<>()).add(genre);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return movieGenresMap;
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

    //
    public List<Movie> getMoviesByGenre(String genre) {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT m.* FROM Movie m JOIN MovieInGenre mg ON m.MovieID = mg.MovieID WHERE mg.Genre = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, genre);
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

    public List<String> getAllDistinctGenres() {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT DISTINCT Genre FROM MovieInGenre";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                genres.add(rs.getString("Genre"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return genres;
    }
    
    ///
    // Get all countries
    public Set<String> getAllCountries() {
        Set<String> countries = new HashSet<>();
        String sql = "SELECT DISTINCT Country FROM Movie";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                countries.add(rs.getString("Country"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return countries;
    }

    // Get all years
    public Set<Integer> getAllYears() {
        Set<Integer> years = new HashSet<>();
        String sql = "SELECT DISTINCT YEAR(DatePublished) as Year FROM Movie";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                years.add(rs.getInt("Year"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return years;
    }

    // Get movies by country
    public List<Movie> getMoviesByCountry(String country) {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE Country = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, country);
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

    // Get movies by year
    public List<Movie> getMoviesByYear(int year) {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE YEAR(DatePublished) = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
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
    
     // New method to fetch all distinct genres
    public Set<String> getAllGenres() {
        Set<String> genres = new HashSet<>();
        String sql = "SELECT DISTINCT Genre FROM MovieInGenre";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                genres.add(rs.getString("Genre"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return genres;
    }

}
