package controller;

import DAO.MovieDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.MovieWithStatus;
import model.MovieInGenre;
import util.Router;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "MovieServlet", urlPatterns = {"/movies"})
public class MovieServlet extends HttpServlet {

    Router router = new Router();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        lay context : 
        ServletContext context = getServletContext();

        String status = request.getParameter("status");
        if (status == null || (!status.equals("Showing") && !status.equals("Coming"))) {
            status = "Coming"; // Trạng thái mặc định
        }
        try {
            List<MovieWithStatus> movies = DAO.MovieDAO.getInstance().getMoviesByStatus(status, context);
            List<MovieInGenre> movieInGenres = DAO.MovieDAO.getInstance().getAllMovieInGenre(context);

            // Create a map to store genres by movie ID
            Map<Integer, String> genresMap = new HashMap<>();
            for (MovieInGenre mig : movieInGenres) {
                genresMap.put(mig.getMovieID(), mig.getGenre());
            }

            request.setAttribute("movies", movies);
            request.setAttribute("genresMap", genresMap);
            request.setAttribute("status", status);
            request.getRequestDispatcher(router.MOVIE_LIST).forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error accessing movies", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
