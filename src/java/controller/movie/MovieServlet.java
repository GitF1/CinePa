/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.movie;

import DAO.MovieDAO;
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
import model.Movie;
import model.MovieInGenre;
import util.RouterJSP;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "MovieServlet", urlPatterns = {"/movies"})
public class MovieServlet extends HttpServlet {

    RouterJSP router = new RouterJSP();
    MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.movieDAO = new MovieDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(MovieServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet MovieServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MovieServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = request.getParameter("status");
        if (status == null || (!status.equals("Showing") && !status.equals("Coming"))) {
            status = "Coming"; // Trạng thái mặc định
        }
        try {
            List<Movie> movies = movieDAO.getMoviesByStatus(status);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
