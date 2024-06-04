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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Cinema;
import model.CinemaChain;
import model.Movie;
import model.MovieSlot;
import model.Room;
import model.Seat;

/**
 *
 * @author Admin
 */

public class UserDAO extends SQLServerConnect {

    public UserDAO(ServletContext context) throws Exception {
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

    public ResultSet checkLogin(String username_email, String password) throws SQLException {
        String sqlQuery = "SELECT *\n"
                + "FROM [User]\n"
                + "WHERE (Username = '" + username_email + "' " + "OR Email = '" + username_email + "'" + ")\n"
                + "AND Password = '" + password + "'\n"
                + "AND Status = 1";
        System.out.println(sqlQuery);
        ResultSet rs = getResultSet(sqlQuery);
        return rs;
    }

    public boolean checkExistEmail(String email) {
        boolean duplicate = false;
        String sql = "SELECT 1 FROM [User] WHERE email = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    duplicate = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return duplicate;
    }

    public boolean updateUserCode(String email, String code) throws SQLException {
        String sql = "update [User] set Code = '" + code + "'" + " where Email = '" + email + "'";
        Statement st = connection.createStatement();
        int kq = st.executeUpdate(sql);
        return kq == 1;
    }

    public String getUserCode(String email) throws SQLException {
        ResultSet rs = getResultSet("select * from [User] where Email = '" + email + "'");
        if (rs.next()) {
            return rs.getString("Code");
        }
        return null;
    }

    public boolean updateUserPassword(String email, String password) throws SQLException {
        String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        String sql = "update [User] set Password = '" + hash + "'" + " where Email = '" + email + "'";
        Statement st = connection.createStatement();
        int kq = st.executeUpdate(sql);
        return kq == 1;
    }
    
    //Query cinema
    
    public ResultSet getData(String table, String atr, Map<String, String> conditions) throws SQLException {
        String sqlQuery;
        if(conditions == null) sqlQuery = "select " + atr + " from [" + table + "]";
        else {
            sqlQuery = "select " + atr + " from [" + table + "] where ";
            for(Map.Entry<String, String> entry : conditions.entrySet()) {
                sqlQuery += entry.getKey() + " = '" + entry.getValue() + "'" + "\nand ";
            }
            sqlQuery += "1 = 1";
        }
        ResultSet rs = getResultSet(sqlQuery);
        return rs;
    }
    
    public List<CinemaChain> queryCinemaChains(Map<String, String> map) throws SQLException {
        List<CinemaChain> cinemas = new ArrayList<>();
        ResultSet rs = getData("CinemaChain", "*", map);
        while(rs.next()) {
            CinemaChain cinemaChain = new CinemaChain(rs.getInt("CinemaChainID"), rs.getString("Name"), rs.getString("Information"), rs.getString("Avatar"));
            cinemas.add(cinemaChain);
        }
        return cinemas;
    }
    
    public List<Cinema> queryCinemas(Map<String, String> map) throws SQLException {
        List<Cinema> cinemas = new ArrayList<>();
        ResultSet rs = getData("Cinema", "*", map);
        while(rs.next()) {
            Cinema cinema = new Cinema(rs.getInt("CinemaID"), rs.getInt("CinemaChainID"), rs.getString("Address"), rs.getString("Province"), rs.getString("District"), rs.getString("Commune"));
            cinemas.add(cinema);
        }
        return cinemas;
    }
    
    public List<Cinema> queryCinemasByLoc(String province) throws SQLException {
        String sqlQuery = "select * from Cinema\n" +
                            "join CinemaChain\n" +
                            "on CinemaChain.CinemaChainID = Cinema.CinemaChainID\n" +
                            "where Province = '" + province + "'";
        List<Cinema> cinemas = new ArrayList<>();
        ResultSet rs = getResultSet(sqlQuery);
        while(rs.next()) {
            Cinema cinema = new Cinema(rs.getInt("CinemaID"), rs.getInt("CinemaChainID"), rs.getString("Name"), rs.getString("Address"), rs.getString("Province"), rs.getString("District"), rs.getString("Commune"));
            cinemas.add(cinema);
        }
        return cinemas;
    }
    
    public List<Cinema> specQueryCinemas(Map<String, String> conditions) throws SQLException {
        List<Cinema> cinemas = new ArrayList<>();
        String sqlQuery = "select * from Cinema\n" +
                            "join CinemaChain\n" +
                            "on CinemaChain.CinemaChainID = Cinema.CinemaChainID\n";
        if(conditions != null && !conditions.isEmpty()) {
            sqlQuery += "where ";
            for(Map.Entry<String, String> entry : conditions.entrySet()) {
                if(entry.getValue() != null) sqlQuery += entry.getKey() + " like '%" + entry.getValue() + "%'" + "\nand ";
            }
            sqlQuery += "1 = 1";
        }
        System.out.println(sqlQuery);
        ResultSet rs = getResultSet(sqlQuery);
        while(rs.next()) {
            Cinema cinema = new Cinema(rs.getInt("CinemaID"), rs.getInt("CinemaChainID"), rs.getString("Name"), rs.getString("Address"), rs.getString("Province"), rs.getString("District"), rs.getString("Commune"));
            cinemas.add(cinema);
        }
        return cinemas;
    }
    
    //Query cinema
    
    //Search movies
    public List<Movie> searchMovies(String input) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sqlQuery = "select * from Movie where Title like '%" + input + "%'";
        ResultSet rs = getResultSet(sqlQuery);
        while(rs.next()) {
            Movie movie = new Movie(rs.getInt("MovieID"), rs.getString("Title"), rs.getString("Synopsis"), rs.getDate("DatePublished"), rs.getString("ImageURL"), rs.getFloat("Rating"), rs.getString("Country"), rs.getString("Status"));
            movies.add(movie);
        }
        return movies;
    }
    
    //Query seats from room
    public Map<Room, List<Seat>> querySeatsFromRoom(int roomID) throws SQLException {
        List<Seat> seats = new ArrayList<>();
        Map<Room, List<Seat>> map = new HashMap<>();
        Room room = null;
        String sqlQuery = "select Room.RoomID, CinemaID, Room.Name as RoomName, Room.Capacity, Status, Length, Width, Type, SeatID, Seat.Name as SeatName, CoordinateX, CoordinateY from Room\n" +
                        "join Seat on Room.RoomID = Seat.RoomID\n" +
                        "where Room.RoomID = " + roomID + "\n" +
                        "order by Seat.Name";
        ResultSet rs = getResultSet(sqlQuery);
        while(rs.next()) {
            Seat seat = new Seat(rs.getInt("SeatID"), rs.getInt("RoomID"), rs.getString("SeatName"), rs.getInt("CoordinateX"), rs.getInt("CoordinateY"));
            seats.add(seat);
            if(room == null) room = new Room(rs.getInt("RoomID"), rs.getInt("CinemaID"), rs.getString("RoomName"), rs.getString("Type"), rs.getInt("capacity"), rs.getInt("length"), rs.getInt("width"), rs.getString("status"));
        }
        map.put(room, seats);
        return map;
    }    
    //Query seats from MovieSlot (list những ghế đã đặt tại movie slot)
    public List<Seat> queryBookedSeats(int movieSlotID) throws SQLException {
        List<Seat> seats = new ArrayList<>();
        String sqlQuery = "select distinct Ticket.SeatID from MovieSlot\n" +
                        "join Ticket on MovieSlot.MovieSlotID = Ticket.MovieSlotID\n" + 
                        "where MovieSlot.MovieSlotID = " + movieSlotID + " and Ticket.Status = 'Booked'";
        ResultSet rs = getResultSet(sqlQuery);
        while(rs.next()) {
            seats.add(new Seat(rs.getInt("SeatID")));
        }
        return seats;
    }
    
    //Query seats from MovieSlot (list những ghế có trong room tại slot đó)
    public List<Seat> querySeatsInRoom(int movieSlotID) throws SQLException {
        List<Seat> seats = new ArrayList<>();
        String sqlQuery = "select Seat.SeatID, Seat.RoomID, Seat.Name, CoordinateX, CoordinateY, \n" +
                            "    CASE \n" +
                            "        WHEN BookedSeats.SeatID IS NOT NULL THEN 'Booked'\n" +
                            "        ELSE 'Available'\n" +
                            "    END AS Status\n" +
                            "from MovieSlot\n" +
                            "join Seat on MovieSlot.RoomID = Seat.RoomID\n" +
                            "left join (\n" +
                            "	select SeatID from MovieSlot\n" +
                            "	join Ticket on MovieSlot.MovieSlotID = Ticket.MovieSlotID\n" +
                            "	where Ticket.Status = 'Booked'\n" +
                            ") as BookedSeats on Seat.SeatID = BookedSeats.SeatID\n" +
                            "where MovieSlotID = " + movieSlotID;
                            
        ResultSet rs = getResultSet(sqlQuery);
        while(rs.next()) {
            Seat seat = new Seat(rs.getInt("SeatID"), rs.getInt("RoomID"), rs.getString("Name"), rs.getInt("CoordinateX"), rs.getInt("CoordinateY"), rs.getString("Status"));
            seats.add(seat);
        }
        return seats;
    }
    // Query seats
    
    // Query movie slot
    public MovieSlot queryMovieSlots(int movieSlotID) throws SQLException {
        String sqlQuery = "select * from MovieSlot where MovieSlot.MovieSlotID = " + movieSlotID;
        ResultSet rs = getResultSet(sqlQuery);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, true)
            .optionalEnd()
            .toFormatter();        
        if(rs.next()) return new MovieSlot(movieSlotID, rs.getInt("RoomID"), rs.getInt("MovieID"), LocalDateTime.parse(rs.getString("StartTime"), formatter), LocalDateTime.parse(rs.getString("EndTime"), formatter), rs.getString("Type"), rs.getFloat("Price"), rs.getFloat("Discount"), rs.getString("Status"));
        return null;
    }
    
    // Query movie from movie-slot ID
    public Movie queryMovie(int movieSlotID) throws SQLException {
        String sqlQuery = "select Movie.MovieID, Title, Synopsis, DatePublished, ImageURL, Rating, Country, Movie.Status from MovieSlot\n" +
                            "join Movie on MovieSlot.MovieID = Movie.MovieID\n" + 
                            "where MovieSlot.MovieSlotID = " + movieSlotID;
        ResultSet rs = getResultSet(sqlQuery);
        if(rs.next()) return new Movie(rs.getInt("MovieID"), rs.getString("Title"), rs.getString("Synopsis"), rs.getDate("DatePublished"), rs.getString("ImageURL"), rs.getFloat("Rating"), rs.getString("Country"), rs.getString("Status"));
        return null;
    }
    // Query movie
    
    // Query room from movie-slot ID
    public Room queryRoom(int movieSlotID) throws SQLException {
        String sqlQuery = "select Room.RoomID, CinemaID, Room.Name, Capacity, Room.Status, Length, Width, Room.Type from MovieSlot\n" +
                        "join Room on MovieSlot.RoomID = Room.RoomID\n" + 
                        "where MovieSlot.MovieSlotID = " + movieSlotID;
        ResultSet rs = getResultSet(sqlQuery);
        if(rs.next()) return new Room(rs.getInt("RoomID"), rs.getInt("CinemaID"), rs.getString("Name"), sqlQuery, rs.getInt("Capacity"), rs.getInt("Length"), rs.getInt("Width"), rs.getString("Status"));
        return null;
    }
}
