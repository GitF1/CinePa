package controller;

import DAO.MovieDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    MovieDAO movieDAO;
    
    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            movieDAO= new MovieDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(MovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
            List<MovieWithStatus> movies = movieDAO.getMoviesByStatus(status);
            List<MovieInGenre> movieInGenres = movieDAO.getAllMovieInGenre();

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
