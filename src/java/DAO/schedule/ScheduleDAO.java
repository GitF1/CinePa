/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.schedule;

import jakarta.servlet.ServletContext;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import DB.SQLServerConnect;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import model.Cinema;
import model.CinemaChain;
import model.Movie;
import model.MovieSlot;
import model.schedule.CinemaMovieSlot;
import model.schedule.Schedule;
import model.schedule.DateInfo;
import model.schedule.MovieSchedule;
import util.RouterJSP;

/**
 *
 * @author PC
 */
public class ScheduleDAO extends SQLServerConnect {

    RouterJSP route = new RouterJSP();

    public ScheduleDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<String> getListCites() {

        List<String> citiesProvinces = new ArrayList<>();
        citiesProvinces.add("Hà Nội");
        citiesProvinces.add("Đà Nẵng");
        citiesProvinces.add("TP. Hồ Chí Minh ");
        citiesProvinces.add("Hải Phòng");

        return citiesProvinces;
    }

    public List<CinemaChain> getListCinemaChain(int limit, int offset) throws SQLException {
        List<CinemaChain> cinemaChains = new ArrayList<>();

        String sql = "SELECT CinemaChainID, Name, Information, avatar FROM CinemaChain ORDER BY CinemaChainID OFFSET " + offset + " ROWS FETCH NEXT " + limit + " ROWS ONLY";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int cinemaChainID = rs.getInt("CinemaChainID");
                String name = rs.getString("Name");
                String information = rs.getString("Information");
                String avatar = rs.getString("avatar");

                CinemaChain cinemaChain = new CinemaChain(cinemaChainID, name, information, avatar);
                cinemaChains.add(cinemaChain);
            }
        }

        return cinemaChains;
    }

    public List<Cinema> getListCinema(String city, int cinemaChainID, int limit, int offset) throws SQLException {
        List<Cinema> cinemas = new ArrayList<>();

        String sql = "SELECT c.CinemaID, c.CinemaChainID, c.Address, c.Province, c.District, c.Commune, cc.Avatar "
                + "FROM Cinema c "
                + "LEFT JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE c.Province = ? ";

        if (cinemaChainID != 0) {
            sql += "AND c.CinemaChainID = ? ";
        }

        sql += "ORDER BY c.CinemaID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            stmt.setString(parameterIndex++, city);
            if (cinemaChainID != 0) {
                stmt.setInt(parameterIndex++, cinemaChainID);
            }
            stmt.setInt(parameterIndex++, offset);
            stmt.setInt(parameterIndex, limit);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int cinemaID = rs.getInt("CinemaID");
                int cineChainID = rs.getInt("CinemaChainID");
                String address = rs.getString("Address");
                String province = rs.getString("Province");
                String district = rs.getString("District");
                String commune = rs.getString("Commune");
                String avatar = rs.getString("Avatar");
                Cinema cinema = new Cinema(cinemaID, cineChainID, address, province, district, commune, avatar);

                cinemas.add(cinema);
            }
        }

        return cinemas;
    }

    public List<CinemaMovieSlot> getCinemasShowingMovie(int movieID, String dateStr, String city) throws SQLException {

        List<CinemaMovieSlot> cinemaMovieSlots = new ArrayList<>();

        String sql = "SELECT MC.CinemaID, MC.MovieID,CC.CinemaChainID ,CC.Name, C.Province, C.District, C.Commune, C.Address, CC.Name, CC.Avatar "
                + "FROM MovieCinema MC "
                + "JOIN Cinema C ON C.CinemaID = MC.CinemaID "
                + "JOIN CinemaChain CC ON CC.CinemaChainID = C.CinemaChainID "
                + "WHERE MovieID = ? " + (city != null && !city.isEmpty() ? "AND C.Province = ?" : "");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movieID);
            if (city != null && !city.isEmpty()) {
                stmt.setString(2, city);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int cinemaID = rs.getInt("CinemaID");

                CinemaMovieSlot cinema = new CinemaMovieSlot(
                        cinemaID,
                        rs.getInt("CinemaChainID"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Province"),
                        rs.getString("District"),
                        rs.getString("Commune"),
                        rs.getString("Avatar"),
                        new ArrayList<>()
                );
                System.out.println("cinema" + cinema);
                List<MovieSlot> movieSlots = getListMovieSlotByMovieAndCinema(cinemaID, movieID, dateStr);

                if (!movieSlots.isEmpty()) {
                    cinema.setMovieSlots(movieSlots);
                    cinemaMovieSlots.add(cinema);
                }

            }

        }

        return cinemaMovieSlots;
    }

    public List<MovieSlot> getListMovieSlotByMovieAndCinema(int cinemaID, int movieID, String dateStr) {
        List<MovieSlot> movieSlots = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate;

        // Check if dateStr is null or empty, and use today's date if it is
        if (dateStr == null || dateStr.isEmpty()) {
            formattedDate = LocalDate.now().format(dateFormatter);
        } else {
            try {
                LocalDate parsedDate = LocalDate.parse(dateStr, dateFormatter);
                formattedDate = parsedDate.format(dateFormatter);
            } catch (Exception e) {
                // Handle the parsing exception if the date string is not valid
                formattedDate = LocalDate.now().format(dateFormatter);
            }
        }
        String sql = "SELECT * FROM MovieSlot MS "
                + "JOIN Room R ON R.RoomID = MS.RoomID "
                + "WHERE R.CinemaID = ? AND MS.MovieID = ? "
                + "AND CONVERT(DATE, MS.startTime) = ? "
                + "AND MS.startTime > DATEADD(MINUTE, 30, GETDATE())";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, cinemaID);
            stmt.setInt(2, movieID);
            stmt.setString(3, formattedDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MovieSlot movieSlot = new MovieSlot();
                    movieSlot.setMovieSlotID(rs.getInt("MovieSlotID"));
                    movieSlot.setRoomID(rs.getInt("RoomID"));
                    movieSlot.setMovieID(movieID); // Note: use the method parameter
                    movieSlot.setStartTime(rs.getObject("StartTime", LocalDateTime.class));
                    movieSlot.setEndTime(rs.getObject("EndTime", LocalDateTime.class));
                    movieSlot.setType(rs.getString("Type"));
                    movieSlot.setPrice(rs.getFloat("Price"));
                    movieSlot.setDiscount(rs.getFloat("Discount"));
                    movieSlot.setStatus(rs.getString("Status"));

                    movieSlots.add(movieSlot);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieSlots;
    }

    public List<MovieSchedule> getListMovie(int cinemaID, String date) {
        System.out.println("cinemaID : " + cinemaID);
        List<MovieSchedule> movies = new ArrayList<>();
        String sql = "SELECT \n"
                + "    m.MovieID,\n"
                + "    m.Title,\n"
                + "    m.DatePublished,\n"
                + "    m.Country,\n"
                + "    m.Rating,\n"
                + "    m.ImageURL,\n"
                + "    m.Synopsis,\n"
                + "    mc.Status\n"
                + "FROM \n"
                + "    Movie m\n"
                + "JOIN \n"
                + "    MovieCinema mc ON m.MovieID = mc.MovieID\n"
                + "WHERE \n"
                + "    mc.CinemaID = ? AND mc.Status = 'SHOWING';";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cinemaID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    MovieSchedule movie = new MovieSchedule();
                    movie.setMovieID(resultSet.getInt("movieID"));
                    movie.setTitle(resultSet.getString("title"));
                    movie.setSynopsis(resultSet.getString("synopsis"));
                    movie.setDatePublished(resultSet.getString("datePublished"));
                    movie.setImageURL(resultSet.getString("imageURL"));
                    movie.setRating(resultSet.getDouble("rating"));

                    // Get movie slots for this movie
                    int movieID = resultSet.getInt("movieID");
                    List<MovieSlot> movieSlots = getAllMovieSlotsByMovieID(cinemaID, movieID, date);
                    movie.setListMovieSlot(movieSlots);
                    if (!movieSlots.isEmpty()) {
                        movies.add(movie);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;

    }

    // Method to get all movie slots for a given movieID
    public List<MovieSlot> getAllMovieSlotsByMovieID(int cinemaID, int movieID, String date) {

        List<MovieSlot> movieSlots = new ArrayList<>();

        // SQL query to get movie slots for a specific movie, cinema, and date
        String sql = "SELECT ms.MovieSlotID, ms.RoomID, ms.MovieID, ms.StartTime, ms.EndTime, ms.Type, ms.Price, ms.Discount, ms.Status AS MovieSlotStatus, "
                + "r.Name AS RoomName, r.Type AS RoomType, r.Capacity, r.Status AS RoomStatus "
                + "FROM MovieSlot ms "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN MovieCinema mc ON ms.MovieID = mc.MovieID AND mc.CinemaID = r.CinemaID "
                + "WHERE ms.MovieID = ? "
                + "AND mc.CinemaID = ? "
                + "AND CONVERT(DATE, ms.StartTime) = ? "
                + "AND ms.StartTime > DATEADD(MINUTE,10, GETDATE())";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, movieID);
            preparedStatement.setInt(2, cinemaID);
            preparedStatement.setString(3, date);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MovieSlot movieSlot = new MovieSlot();
                    movieSlot.setMovieSlotID(resultSet.getInt("MovieSlotID"));
                    movieSlot.setRoomID(resultSet.getInt("RoomID"));
                    movieSlot.setMovieID(resultSet.getInt("MovieID"));
                    movieSlot.setStartTime(resultSet.getObject("StartTime", LocalDateTime.class));
                    movieSlot.setEndTime(resultSet.getObject("EndTime", LocalDateTime.class));
                    movieSlot.setType(resultSet.getString("Type"));
                    movieSlot.setPrice(resultSet.getFloat("Price"));
                    movieSlot.setDiscount(resultSet.getFloat("Discount"));
                    movieSlots.add(movieSlot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(movieSlots, Comparator.comparing(MovieSlot::getStartTime));

        return movieSlots;
    }

    public Schedule createSchedule(String city, String cinemaID, String branchID, CinemaChain cinema, Cinema branch, List<Cinema> listBranch, List<MovieSchedule> listMovie) {

        Schedule schedule = new Schedule();
        schedule.setCity(city);
        schedule.setCinemaID(cinemaID);
        schedule.setBranchID(branchID);
        schedule.setCinema(cinema);
        schedule.setBranch(branch);
        schedule.setListBranch(listBranch);
        schedule.setListMovie(listMovie);

        return schedule;
    }

    public void handleDoGetComponentSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        HttpSession session = request.getSession();
        Schedule schedule = getOrCreateScheduleFromSession(session);

        // Get the necessary data
        List<String> citiesProvinces = getListCites();
        List<DateInfo> listDateOfWeek = new DateInfo().generateWeek();
        List<CinemaChain> listCinemaChain = getListCinemaChain(8, 0);
        String selectedCity = getSelectedCity(schedule, citiesProvinces);
        int cinemaChainID = getCinemaChainID(schedule);
        String scheduleForDate = getScheduleForDate(schedule, listDateOfWeek);

        // Get list of cinema branches
        List<Cinema> listBranchCinema = getListCinema(selectedCity, cinemaChainID, 6, 0);
        List<MovieSchedule> listMovies = getMoviesForCinemaBranch(schedule, listBranchCinema, scheduleForDate);

        // Update the schedule object
        updateSchedule(schedule, selectedCity, listDateOfWeek, listBranchCinema, listMovies);

        // Set request attributes
        setRequestAttributes(request, citiesProvinces, listDateOfWeek, listCinemaChain, schedule);

        // Store updated schedule in session
        storeScheduleInSession(session, schedule);
    }

    private Schedule getOrCreateScheduleFromSession(HttpSession session) {
        Schedule schedule = getScheduleFromSession(session);
        return (schedule != null) ? schedule : new Schedule();
    }

    private String getSelectedCity(Schedule schedule, List<String> citiesProvinces) {
        if (schedule.getCity() == null) {
            String defaultCity = citiesProvinces.get(0);
            schedule.setCity(defaultCity);
            return defaultCity;
        } else {
            return schedule.getCity();
        }
    }

    private int getCinemaChainID(Schedule schedule) {
        return (schedule.getCinemaID() == null) ? 0 : Integer.parseInt(schedule.getCinemaID());
    }

    private String getScheduleForDate(Schedule schedule, List<DateInfo> listDateOfWeek) {
        return (schedule.getSelectDate() == null) ? listDateOfWeek.get(0).getTime() : schedule.getSelectDate();
    }

    private List<MovieSchedule> getMoviesForCinemaBranch(Schedule schedule, List<Cinema> listBranchCinema, String scheduleForDate) throws SQLException {
        if (listBranchCinema.isEmpty()) {
            return new ArrayList<>();
        } else {
            int cinemaBranchID = getCinemaBranchID(schedule, listBranchCinema);
            return getListMovie(cinemaBranchID, scheduleForDate);
        }
    }

    private int getCinemaBranchID(Schedule schedule, List<Cinema> listBranchCinema) {
        int defaultCinemaBranchID = listBranchCinema.get(0).getCinemaID();
        return (schedule.getBranchID() == null) ? defaultCinemaBranchID : Integer.parseInt(schedule.getBranchID());
    }

    private void updateSchedule(Schedule schedule, String selectedCity, List<DateInfo> listDateOfWeek, List<Cinema> listBranchCinema, List<MovieSchedule> listMovies) {
        schedule.setCity(selectedCity);
        schedule.setListShowDate(listDateOfWeek);
        schedule.setListBranch(listBranchCinema);
        schedule.setListMovie(listMovies);
        if (schedule.getSelectDate() == null) {
            schedule.setSelectDate(listDateOfWeek.get(0).getTime());
        }
    }

    private void setRequestAttributes(HttpServletRequest request, List<String> citiesProvinces, List<DateInfo> listDateOfWeek, List<CinemaChain> listCinemaChain, Schedule schedule) {
        request.setAttribute("citiesProvinces", citiesProvinces);
        request.setAttribute("listDateOfWeek", listDateOfWeek);
        request.setAttribute("cinemaChains", listCinemaChain);
        setAttribute(request, schedule);
    }

    public void handleDoPostComponentSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Schedule schedule = getScheduleFromSession(session);

        String city = request.getParameter("cityProvince");
        String cinemaID = request.getParameter("cinemaID");
        String cinemaChainID = request.getParameter("cinemaChainID");
        String date = request.getParameter("date");

        if (cinemaID != null) {
            schedule.setBranchID(cinemaID);
        }
        if (date != null) {
            schedule.setSelectDate(date);
        }

        if (cinemaChainID != null) {
            schedule.setCinemaID(cinemaChainID);
            schedule.setBranchID(null);
        }

        if (city != null) {
            schedule.setCity(city);
            schedule.setBranchID(null);

        }

        storeScheduleInSession(session, schedule);
        setAttribute(request, schedule);

    }

    private void setAttribute(HttpServletRequest request, Schedule schedule) {

        request.setAttribute("citySelect", schedule.getCity());
        request.setAttribute("listDateOfWeek", new DateInfo().generateWeek());
        request.setAttribute("listCinemaBranch", schedule.getListBranch());
        request.setAttribute("selectCinema", !schedule.getListBranch().isEmpty() ? schedule.getListBranch().get(0) : null);
        request.setAttribute("selectDateOfWeek", schedule.getSelectDate());
        request.setAttribute("listMovie", schedule.getListMovie());
        request.setAttribute("selectDate", schedule.getSelectDate());

    }

    //
    private void storeScheduleInSession(HttpSession session, Schedule schedule) {
        session.setAttribute("schedule", schedule);
    }

    // Example method to retrieve the Schedule object from the session
    private Schedule getScheduleFromSession(HttpSession session) {
        return (Schedule) session.getAttribute("schedule");
    }

//    // Implement methods to retrieve cinema, branch, listBranch, and listMovie
//    private CinemaChain getNewCinema(String cinemaID) {
//        // Retrieve cinema by ID from the database or another source
//        return new CinemaChain(); // Placeholder implementation
//    }
//
//    private Cinema getNewBranch(String branchID) {
//        // Retrieve branch by ID from the database or another source
//        return new Cinema(); // Placeholder implementation
//    }
//
//    private List<Cinema> getNewListBranch(String cinemaID) {
//        // Retrieve list of branches by cinema ID from the database or another source
//        return new ArrayList<>(); // Placeholder implementation
//    }
//
//    private List<Movie> getNewListMovie(String cinemaID) {
//        // Retrieve list of movies by cinema ID from the database or another source
//        return new ArrayList<>(); // Placeholder implementation
//    }
    //Functions for crud movieslot - DuyND
    public boolean checkOverlap(Date start, Date end, int roomID) {
        boolean overlap = false;
        java.sql.Timestamp timestamp = new java.sql.Timestamp(start.getTime());
        String sql = "select * from MovieSlot where ((?>MovieSlot.StartTime and ?<MovieSlot.EndTime) and Cast(? as date) = Cast(MovieSlot.StartTime as date) and MovieSlot.RoomID=?)";
        //in order: end start start room id
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setTimestamp(1, new java.sql.Timestamp(end.getTime()));
            st.setTimestamp(2, new java.sql.Timestamp(start.getTime()));
            st.setTimestamp(3, new java.sql.Timestamp(start.getTime()));
            st.setInt(4, roomID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    overlap = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return overlap;
    }

    public boolean checkOverlapExceptID(Date start, Date end, int roomID, int movieSlotID) {//exclude the inputted slot to check while editing - DuyND
        boolean overlap = false;
        java.sql.Timestamp timestamp = new java.sql.Timestamp(start.getTime());
        String sql = "select * from MovieSlot where ((?>MovieSlot.StartTime and ?<MovieSlot.EndTime) and Cast(? as date) = Cast(MovieSlot.StartTime as date) and MovieSlot.RoomID=? and MovieSlotID!=?)";
        //in order: end start start room id
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setTimestamp(1, new java.sql.Timestamp(end.getTime()));
            st.setTimestamp(2, new java.sql.Timestamp(start.getTime()));
            st.setTimestamp(3, new java.sql.Timestamp(start.getTime()));
            st.setInt(4, roomID);
            st.setInt(4, movieSlotID);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    overlap = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return overlap;
    }

    public boolean updateMovieSlot(MovieSlot movieSlot) throws SQLException {
        String sql = "UPDATE [MovieSlot] SET  RoomID=?, MovieID=?, StartTime=?, EndTime=?, Type=?, Price=?, Discount=?, status=? WHERE MovieSlotID=?";
        //remove avatarlink, username, password, email, method only for update profile --DuyND
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieSlot.getRoomID());
            pstmt.setInt(2, movieSlot.getMovieID());
            java.sql.Date startTime = new java.sql.Date((Date.from(movieSlot.getStartTime().atZone(ZoneId.systemDefault()).toInstant())).getTime());
            java.sql.Date endTime = new java.sql.Date((Date.from(movieSlot.getEndTime().atZone(ZoneId.systemDefault()).toInstant())).getTime());
            java.sql.Timestamp timestampStart = new java.sql.Timestamp(startTime.getTime());
            java.sql.Timestamp timestampEnd = new java.sql.Timestamp(endTime.getTime());
            pstmt.setTimestamp(3, timestampStart);
            pstmt.setTimestamp(4, timestampEnd);
            pstmt.setString(5, movieSlot.getType());
            pstmt.setFloat(6, movieSlot.getPrice());
            pstmt.setFloat(7, movieSlot.getDiscount());
            pstmt.setString(8, movieSlot.getStatus());
            pstmt.setInt(9, movieSlot.getMovieSlotID());

            int updated = pstmt.executeUpdate();
            return updated > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e; // Rethrow SQLException for handling in the calling method
        }
    }

    public ArrayList<MovieSlot> getDateSchedule(Date date, int roomID) {
        ArrayList<MovieSlot> result = new ArrayList<>();
        String sqlQuery = "select * from MovieSlot where (? = Cast(MovieSlot.StartTime as date)) and MovieSlot.RoomID=?  order by StartTime";
        java.sql.Date insertDate = new java.sql.Date(date.getTime());

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setDate(1, insertDate);
            pstmt.setInt(2, roomID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
//                    int movieSlotID, int roomID, int movieID, LocalDateTime startTime, LocalDateTime endTime, String type, float price, float discount, String status
                    result.add(new MovieSlot(
                            rs.getInt("MovieSlotID"),//placeholder, will not be used
                            rs.getInt("RoomID"),
                            rs.getInt("MovieID"),
                            rs.getTimestamp("StartTime").toLocalDateTime(),
                            rs.getTimestamp("EndTime").toLocalDateTime(),
                            rs.getString("Type"),
                            rs.getFloat("Price"),
                            rs.getFloat("Discount"),
                            rs.getString("status")
                    )
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace(); // Handle exceptions appropriately in real scenarios
        }
        return result;
    }

    public void insertMovieSlot(MovieSlot movieSlot) throws ParseException {//insert movieslot - DuyND
        String sql = "INSERT INTO [dbo].[MovieSlot]\n"
                + "           ([RoomID]\n"
                + "           ,[MovieID]\n"
                + "           ,[StartTime]\n"
                + "           ,[EndTime]\n"
                + "           ,[Type]\n"
                + "           ,[Price]\n"
                + "           ,[Discount]\n"
                + "           ,[Status])\n"
                + "     VALUES  (?,?,?,?,?,?,?,?)";
        // Use try-with-resources for automatic resource management
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, movieSlot.getRoomID());
            st.setInt(2, movieSlot.getMovieID());
            java.sql.Date startTime = new java.sql.Date((Date.from(movieSlot.getStartTime().atZone(ZoneId.systemDefault()).toInstant())).getTime());
            java.sql.Date endTime = new java.sql.Date((Date.from(movieSlot.getEndTime().atZone(ZoneId.systemDefault()).toInstant())).getTime());
            java.sql.Timestamp timestampStart = new java.sql.Timestamp(startTime.getTime());
            java.sql.Timestamp timestampEnd = new java.sql.Timestamp(endTime.getTime());
            st.setTimestamp(3, timestampStart);
            st.setTimestamp(4, timestampEnd);
            st.setString(5, movieSlot.getType());
            st.setFloat(6, movieSlot.getPrice());
            st.setFloat(7, movieSlot.getDiscount());
            st.setString(8, movieSlot.getStatus());

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Properly handle the exception, log it, or rethrow as a custom exception
            e.printStackTrace(); // Replace with appropriate logging in production code
        }

    }

    public void deleteMovieSlotByID(int id) {
        String sql = "DELETE FROM [dbo].[MovieSlot] WHERE MovieSlotID=?\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.execute();
        } catch (SQLException e) {
            // Properly handle the exception, log it, or rethrow as a custom exception
            System.out.println(e.getMessage());
            e.printStackTrace(); // Replace with appropriate logging in production code
        }

    }

    private void handleErrorGetDate(HttpServletRequest request) {
    }
}
