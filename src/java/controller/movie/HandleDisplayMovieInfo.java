package controller.movie;

import DAO.MovieDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.movie.MovieInfo;
import model.Review;
import DAO.schedule.ScheduleDAO;
import java.sql.SQLException;
import util.RouterJSP;

@WebServlet(name = "HandleDisplayMovieInfo", urlPatterns = {"/HandleDisplayMovieInfo"})
public class HandleDisplayMovieInfo extends HttpServlet {

    ScheduleDAO scheduleDAO;
    MovieDAO movieDAO;
    RouterJSP route = new RouterJSP();

    @Override
    public void init()
            throws ServletException {
        super.init();
        try {
            scheduleDAO = new ScheduleDAO(getServletContext());
            movieDAO = new MovieDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // nhan movieID tu Quang Vinh guii qua : 
        String movieID = request.getParameter("movieID");
        if (movieID == null) {
            movieID = "4";
        }

        // tao Servlet Context : 
        ServletContext context = getServletContext();

        //lay Movie : 
        MovieInfo movie = new MovieInfo();
        try {
            movie =  (MovieInfo) movieDAO.getMovieWithGenresByID(Integer.parseInt(movieID));
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        // lay list cac phim dang chieu : 
        ArrayList<MovieInfo> listAvailabelMovies = new ArrayList<>();
        try {
            listAvailabelMovies = movieDAO.getAvailableMovies(context);
        } catch (Exception ex) {

            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        // lay list cac Reviews : 
        ArrayList<Review> listReviews = new ArrayList<>();
        try {
            listReviews = movieDAO.getReviewsByMovieID(Integer.parseInt(movieID), context);
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        // them vao Request : 
        request.setAttribute("movie", movie);
        request.setAttribute("listAvailableMovies", listAvailabelMovies);
        request.setAttribute("listReviews", listReviews);

        try {
            scheduleDAO.handleDoGetComponentSchedule(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        // chuyen qua cho DisplayMovieInfo.jsp : 
        request.getRequestDispatcher("page/movie/DisplayMovieInfo.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        scheduleDAO.handleDoPostComponentSchedule(request, response);
        
        String redirectUrl = "/movie/HandleDisplayMovieInfo";
        response.setContentType("text/plain");
        response.getWriter().write(redirectUrl);
        
        doGet(request, response);
    }

}
