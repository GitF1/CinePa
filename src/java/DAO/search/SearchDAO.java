/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.search;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import model.Movie;

/**
 *
 * @author PC
 */
public class SearchDAO extends SQLServerConnect {

    private static final double SIMILARITY_THRESHOLD = 0.7;

    public SearchDAO(ServletContext context) throws Exception {
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

    public List<String> getGenresFromMovieID(int movieID) throws SQLException {
        List<String> genres = new ArrayList<>();
        String sqlQueryGenres = "select  Genre from Movie join MovieInGenre on Movie.MovieID = MovieInGenre.MovieID where Movie.MovieID = " + movieID;
        ResultSet genresRs = getResultSet(sqlQueryGenres);
        while (genresRs.next()) {
            genres.add(genresRs.getString("Genre"));
        }
        return genres;
    }

    public List<Movie> searchMovies(String input) throws SQLException {
        List<Movie> movies = new ArrayList<>();

//        String sqlQuery = "SELECT * FROM Movie "
//                + "WHERE (Title COLLATE Latin1_General_CI_AI LIKE N'%' + ? + '%' "
//                + "OR SOUNDEX(Title) = SOUNDEX(?)) ";
        String sqlQuery = "SELECT * FROM Movie "
                + "WHERE Title COLLATE Latin1_General_CI_AI LIKE N'%' + ? + '%' ";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {

            // Set input parameter for case-insensitive search
            pstmt.setString(1, input);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if any results are found
                if (!rs.next()) {
                    // No results found, attempt soundex search
                    sqlQuery = "SELECT * FROM Movie "
                            + "WHERE SOUNDEX(Title) = SOUNDEX(?) "
                            + "AND status = 'SHOWING' ";

                    try (PreparedStatement pstmtSoundex = connection.prepareStatement(sqlQuery)) {
                        // Set input parameter for soundex search
                        pstmtSoundex.setString(1, input);

                        try (ResultSet rsSoundex = pstmtSoundex.executeQuery()) {
                            // Process results from soundex query
                            while (rsSoundex.next()) {
                                List<String> genres = getGenresFromMovieID(rsSoundex.getInt("MovieID"));
                                Movie movie = new Movie(
                                        rsSoundex.getInt("MovieID"),
                                        rsSoundex.getString("Title"),
                                        rsSoundex.getString("Synopsis"),
                                        rsSoundex.getString("DatePublished"),
                                        rsSoundex.getString("ImageURL"),
                                        rsSoundex.getFloat("Rating"),
                                        rsSoundex.getString("Status"),
                                        rsSoundex.getString("Country"),
                                        genres
                                );
                                movies.add(movie);
                            }
                        }
                    }
                } else {
                    // Process results from case-insensitive search
                    do {
                        List<String> genres = getGenresFromMovieID(rs.getInt("MovieID"));
                        Movie movie = new Movie(
                                rs.getInt("MovieID"),
                                rs.getString("Title"),
                                rs.getString("Synopsis"),
                                rs.getString("DatePublished"),
                                rs.getString("ImageURL"),
                                rs.getFloat("Rating"),
                                rs.getString("Status"),
                                rs.getString("Country"),
                                genres
                        );
                        movies.add(movie);
                    } while (rs.next());
                }
            }
        }

        return movies;

    }

    //
    public List<Movie> searchMoviesByGenre(String keyword) {

        List<Movie> movies = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            StringBuilder sqlQuery = new StringBuilder("SELECT DISTINCT m.* FROM Movie m ")
                    .append("JOIN MovieInGenre mg ON m.MovieID = mg.MovieID ")
                    .append("WHERE mg.Genre COLLATE Latin1_General_CI_AI IN (");

            List<String> matchedGenres = extractGenres(keyword);

            if (matchedGenres.isEmpty()) {
                return movies; // Return empty list if no genres are matched
            }

            for (int i = 0; i < matchedGenres.size(); i++) {
                sqlQuery.append("?");
                if (i < matchedGenres.size() - 1) {
                    sqlQuery.append(", ");
                }
            }

            sqlQuery.append(")");

            stmt = connection.prepareStatement(sqlQuery.toString());

            for (int i = 0; i < matchedGenres.size(); i++) {
                stmt.setString(i + 1, matchedGenres.get(i));
            }

            rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {

                List<String> genres = getGenresFromMovieID(rs.getInt("MovieID"));

                Movie movie = new Movie(
                        rs.getInt("MovieID"),
                        rs.getString("Title"),
                        rs.getString("Synopsis"),
                        rs.getString("DatePublished"),
                        rs.getString("ImageURL"),
                        rs.getFloat("Rating"),
                        rs.getString("Status"),
                        rs.getString("Country"),
                        genres
                );

                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            // SQL query to get all unique genres
            String sqlQuery = "SELECT DISTINCT Genre FROM MovieInGenre";

            stmt = connection.prepareStatement(sqlQuery);
            rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                genres.add(rs.getString("Genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("list genres" + genres);
        return genres;
    }

    private List<String> extractGenres(String input) {
        // Normalize input to lowercase and remove accents
        input = normalize(input.toLowerCase().trim());

        List<String> knownGenres = getAllGenres();
        List<String> matchedGenres = new ArrayList<>();

        for (String genre : knownGenres) {
            // Normalize genre to lowercase and remove accents
            String normalizedGenre = normalize(genre.toLowerCase());
            if (input.contains(normalizedGenre)) {
                matchedGenres.add(genre);
            }
        }

        return matchedGenres;
    }

    //
    public List<Movie> searchMoviesByAbbreviationAndStatus(String abbreviation ) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String status = "Showing";
        
        // SQL query using the abbreviation function, status, and recent 3 months filter
        String sql = "SELECT MovieID, Title, DatePublished, Rating, ImageURL, Synopsis, Country, Year, Length, LinkTrailer, Status "
                + "FROM Movie "
                + "WHERE dbo.GetTitleAbbreviation(Title) LIKE ? "
                + "AND Status = ? ";
//                + "AND DatePublished >= DATEADD(MONTH, -3, GETDATE())";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set the parameters for the query
            pstmt.setString(1, abbreviation.toUpperCase() + '%');
            pstmt.setString(2, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                //
                while (rs.next()) {

                    List<String> genres = getGenresFromMovieID(rs.getInt("MovieID"));

                    Movie movie = new Movie(
                            rs.getInt("MovieID"),
                            rs.getString("Title"),
                            rs.getString("Synopsis"),
                            rs.getString("DatePublished"),
                            rs.getString("ImageURL"),
                            rs.getFloat("Rating"),
                            rs.getString("Status"),
                            rs.getString("Country"),
                            genres
                    );

                    movies.add(movie);
                }

            }
        }
        return movies;
    }

    /// ALPHA VERSION SEARCHING WIHT FUZZY ALGORITHM 
    public List<Movie> searchMovieV2(String input) throws SQLException {
        List<Movie> movies = new ArrayList<>();

        // Normalize and prepare input for comparison
        String normalizedInput = normalize(input.trim());

        // SQL query for case-insensitive, accent-insensitive search
        String sqlQuery = "SELECT * FROM Movie "
                + "WHERE Title COLLATE Latin1_General_CI_AI LIKE ? ";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {

            // Set input parameter for case-insensitive search
            pstmt.setString(1, "%" + normalizedInput + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("Title");
                    // Normalize and prepare title for comparison
                    String normalizedTitle = normalize(title);

                    // Calculate similarity score  !!!!!!!!!!!!!!!!!!!!!!!!
//                    double similarityScore = calculateSimilarity(normalizedInput, normalizedTitle);
                    // If similarity score meets threshold, add to result
                    List<String> genres = getGenresFromMovieID(rs.getInt("MovieID"));
                    Movie movie = new Movie(
                            rs.getInt("MovieID"),
                            title,
                            rs.getString("Synopsis"),
                            rs.getString("DatePublished"),
                            rs.getString("ImageURL"),
                            rs.getFloat("Rating"),
                            rs.getString("Status"),
                            rs.getString("Country"),
                            genres
                    );
                    movies.add(movie);

                }
            }
        }

        return movies;
    }

    //
    // Method to normalize VN's text (remove accents)
    private String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }

    // Method to calculate Jaro-Winkler similarity
}
