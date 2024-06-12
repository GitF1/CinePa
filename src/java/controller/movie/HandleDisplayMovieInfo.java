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
import java.util.List;
import model.schedule.CinemaMovieSlot;
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

        try {

            // nhan movieID tu Quang Vinh guii qua :
            String movieID = request.getParameter("movieID");
            if (movieID == null) {
                System.err.println("missing movieID!");
                return;
            }

            // tao Servlet Context :
            ServletContext context = getServletContext();

            //lay Movie :
            MovieInfo movie = new MovieInfo();

            movie = (MovieInfo) movieDAO.getMovieWithGenresByID(Integer.parseInt(movieID));

            // lay list cac phim dang chieu :
            ArrayList<MovieInfo> listAvailabelMovies = new ArrayList<>();

            listAvailabelMovies = movieDAO.getAvailableMovies(context);

            // lay list cac Reviews :
            ArrayList<Review> listReviews = new ArrayList<>();

            listReviews = movieDAO.getReviewsByMovieID(Integer.parseInt(movieID), context);
            //
//            List<CinemaMovieSlot> listCinemaMovieSlots = scheduleDAO.getCinemasShowingMovie(Integer.parseInt(movieID), null);
//
//            System.out.println("list cinema movie slot:" + listCinemaMovieSlots);

            // them vao Request :
            request.setAttribute("movie", movie);
            request.setAttribute("listAvailableMovies", listAvailabelMovies);
            request.setAttribute("listReviews", listReviews);

            request.getRequestDispatcher("page/movie/DisplayMovieInfo.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

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
