package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Movie;
import model.Review;

public class MovieDAO {

    public static MovieDAO getInstance() {
        return new MovieDAO();
    }

    public Movie getMovieWithGenresByID(int movieID, ServletContext context) throws Exception {

        DB.SQLServerConnect dbConnect = new SQLServerConnect();

        java.sql.Connection connection = dbConnect.connect(context);

        String movieSql = "SELECT * FROM Movie WHERE MovieID = ?";
        String genreSql = "SELECT Genre FROM MovieInGenre WHERE MovieID = ?";
        Movie movie = null;

        try {
            // Lấy thông tin cơ bản của bộ phim từ bảng Movie
            PreparedStatement movieStatement = connection.prepareStatement(movieSql);
            movieStatement.setInt(1, movieID);
            ResultSet movieResultSet = movieStatement.executeQuery();

            // Kiểm tra xem bộ phim có tồn tại không
            if (movieResultSet.next()) {
                // Lấy thông tin từ ResultSet để tạo đối tượng Movie
                int cinemaID = movieResultSet.getInt("CinemaID");
                String title = movieResultSet.getString("Title");
                Date datePublished = movieResultSet.getDate("DatePublished");
                float rating = movieResultSet.getFloat("Rating");
                String imageURL = movieResultSet.getString("ImageURL");
                String synopsis = movieResultSet.getString("Synopsis");
                String country = movieResultSet.getString("Country");
                int year = movieResultSet.getInt("Year");
                int length = movieResultSet.getInt("Length");
                String linkTrailer = movieResultSet.getString("LinkTrailer");

                // Khởi tạo đối tượng Movie
                movie = new Movie(movieID, cinemaID, title, datePublished, rating, imageURL, synopsis, country, year, length, linkTrailer, null);

                // Lấy danh sách thể loại của bộ phim từ bảng MovieInGenre
                PreparedStatement genreStatement = connection.prepareStatement(genreSql);
                genreStatement.setInt(1, movieID);
                ResultSet genreResultSet = genreStatement.executeQuery();

                // Khởi tạo danh sách thể loại và thêm vào đối tượng Movie
                List<String> genres = new ArrayList<>();
                while (genreResultSet.next()) {
                    String genre = genreResultSet.getString("Genre");
                    genres.add(genre);
                }
                movie.setGenres(genres);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movie; // Trả về đối tượng Movie
    }

    
    public ArrayList<Movie> getAvailableMovies(ServletContext context) throws Exception {
        
         DB.SQLServerConnect dbConnect = new SQLServerConnect();

        java.sql.Connection connection = dbConnect.connect(context);
        
        
        ArrayList<Movie> availableMovies = new ArrayList<>();
        String sql = "SELECT * FROM MovieCinema WHERE Status = 'Available'";

        try {
            // Tạo một PreparedStatement từ kết nối và truy vấn SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Thực thi truy vấn và lấy kết quả
            ResultSet resultSet = preparedStatement.executeQuery();

            // Lặp qua các kết quả và tạo đối tượng Movie cho mỗi kết quả
            while (resultSet.next()) {
                int movieID = resultSet.getInt("MovieID");
                int cinemaID = resultSet.getInt("CinemaID");
                String status = resultSet.getString("Status");

                // Tạo đối tượng Movie và thêm vào danh sách nếu trạng thái là "available"
                if (status.trim().equalsIgnoreCase("Available")) {
                    Movie movie = getMovieWithGenresByID(movieID, context) ; 
                    if (movie != null) {
                        availableMovies.add(movie);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableMovies; 
    }

     public ArrayList<Review> getReviewsByMovieID(int movieID , ServletContext context) throws Exception {
         
          DB.SQLServerConnect dbConnect = new SQLServerConnect();

        java.sql.Connection connection = dbConnect.connect(context);

         
        ArrayList<Review> reviews = new ArrayList<>();
        String sql = "SELECT Review.*, [User].AvatarLink, [User].Username " +
                     "FROM Review " +
                     "JOIN [User] ON Review.UserID = [User].UserID " +
                     "WHERE Review.MovieID = ?";

        try {
            // Tạo một PreparedStatement từ kết nối và truy vấn SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movieID);

            // Thực thi truy vấn và lấy kết quả
            ResultSet resultSet = preparedStatement.executeQuery();

            // Lặp qua các kết quả và tạo đối tượng Review cho mỗi kết quả
            while (resultSet.next()) {
                int reviewID = resultSet.getInt("ReviewID");
                int userID = resultSet.getInt("UserID");
                int rating = resultSet.getInt("Rating");
                Date timeCreated = resultSet.getTimestamp("TimeCreated");
                String content = resultSet.getString("Content");
                String userAvatarLink = resultSet.getString("AvatarLink");
                String username = resultSet.getString("Username");

                // Tạo đối tượng Review và thêm vào danh sách
                Review review = new Review(reviewID, userID, movieID, rating, timeCreated, content, userAvatarLink, username);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews; // Trả về danh sách các review của bộ phim có movieID tương ứng dưới dạng ArrayList
    }
    
}
