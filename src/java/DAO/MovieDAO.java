package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Date;
import java.sql.SQLException;
import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Movie;
import model.MovieInGenre;
import model.Review;
//import model.User;
import model.movie.MovieInfo;

/**
 *
 * @author VINHNQ
 */
public class MovieDAO extends SQLServerConnect {

    public MovieDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public MovieInfo getMovieWithGenresByID(int movieID, ServletContext context) throws Exception {

        DB.SQLServerConnect dbConnect = new SQLServerConnect();

        java.sql.Connection connection = dbConnect.connect(context);

        String movieSql = "SELECT * FROM Movie WHERE MovieID = ?";
        String genreSql = "SELECT Genre FROM MovieInGenre WHERE MovieID = ?";
        MovieInfo movie = null;

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
                movie = new MovieInfo(movieID, cinemaID, title, datePublished, rating, imageURL, synopsis, country, year, length, linkTrailer, null);

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

    public ArrayList<MovieInfo> getAvailableMovies(ServletContext context) throws Exception {

        DB.SQLServerConnect dbConnect = new SQLServerConnect();

        java.sql.Connection connection = dbConnect.connect(context);

        ArrayList<MovieInfo> availableMovies = new ArrayList<>();
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
                    MovieInfo movie = getMovieWithGenresByID(movieID, context);
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

    public ArrayList<Review> getReviewsByMovieID(int movieID, ServletContext context) throws Exception {

        DB.SQLServerConnect dbConnect = new SQLServerConnect();

        java.sql.Connection connection = dbConnect.connect(context);

        ArrayList<Review> reviews = new ArrayList<>();
        String sql = "SELECT Review.*, [User].AvatarLink, [User].Username "
                + "FROM Review "
                + "JOIN [User] ON Review.UserID = [User].UserID "
                + "WHERE Review.MovieID = ?";

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

    public void insertMovie(Movie movie) throws ParseException {//insert movie - DuyND
        String sql = "INSERT INTO [dbo].[Movie]\n"
                + "           ([Title]\n"
                + "           ,[DatePublished]\n"
                + "           ,[ImageURL]\n"
                + "           ,[Synopsis]\n"
                + "           ,[Country]\n"
                + "           ,[Year]\n"
                + "           ,[Length]\n"
                + "           ,[LinkTrailer]\n"
                + "           ,[Status])\n"
                + "     VALUES  (?,?,?,?,?,?,?,?,?)";
        // Use try-with-resources for automatic resource management
        try {

            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, movie.getTitle());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date result = df.parse(movie.getDatePublished());
            java.sql.Date sqlDate = new java.sql.Date(result.getTime());
            st.setDate(2, sqlDate);//need to turn into sql date
            st.setString(3, movie.getImageURL());
            st.setString(4, movie.getSynopsis());
            st.setString(5, movie.getCountry());
            st.setInt(6, result.getYear() + 1900);//need to turn into year int
            st.setInt(7, movie.getLength());
            st.setString(8, movie.getTrailerLink());
            st.setString(9, movie.getStatus());

            st.executeUpdate();

        } catch (SQLException e) {
            // Properly handle the exception, log it, or rethrow as a custom exception
            e.printStackTrace(); // Replace with appropriate logging in production code
        }
    }

    public int getMovieID(String movieTitle) throws SQLException {

        int id = 0;
        String sqlQuery = "SELECT MovieID FROM [Movie] WHERE Title = ? ";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {

            pstmt.setString(1, movieTitle);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("MovieID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately in real scenarios
        }
        return id;

    }

    public void insertMovieGenre(Movie movie, String[] genres) {//insert movie genres - DuyND
        String sql = "INSERT INTO [dbo].[MovieInGenre]\n"
                + "           ([MovieID]\n"
                + "           ,[Genre])\n"
                + "     VALUES  (?,?)";
        // Use try-with-resources for automatic resource management
        for (String s : genres) {
            try {

                PreparedStatement st = connection.prepareStatement(sql);

                st.setInt(1, getMovieID(movie.getTitle()));
                st.setString(2, s);
                st.executeUpdate();

            } catch (SQLException e) {
                // Properly handle the exception, log it, or rethrow as a custom exception
                e.printStackTrace(); // Replace with appropriate logging in production code
            }
        }
        
    }

}
