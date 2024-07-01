package controller.movie;

import DAO.MovieDAO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Set;
import model.Movie;
import util.RouterJSP;

/**
 * Servlet implementation class TopMoviesServlet
 */
@WebServlet(name = "TopMoviesServlet", urlPatterns = {"/top-movies"})
public class TopMoviesServlet extends HttpServlet {

    RouterJSP router = new RouterJSP();
    MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.movieDAO = new MovieDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(TopMoviesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TopMoviesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TopMoviesServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try {
            List<Movie> topMovies = movieDAO.getTopRatedMovies();
            request.setAttribute("topMovies", topMovies);
            request.getRequestDispatcher(router.TOP_MOVIES).forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving top rated movies", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet that displays top-rated movies";
    }
}
