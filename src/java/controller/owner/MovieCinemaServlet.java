/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.MovieCinemaDAO;
import DAO.MovieDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Movie;
import model.MovieCinema;
import util.RouterJSP;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "MovieCinemaServlet", urlPatterns = {"/owner/movieCinema"})
public class MovieCinemaServlet extends HttpServlet {
    RouterJSP router = new RouterJSP();

    private MovieCinemaDAO movieCinemaDAO;
    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            movieCinemaDAO = new MovieCinemaDAO(getServletContext());
            movieDAO = new MovieDAO(getServletContext());
        } catch (Exception ex) {
            throw new ServletException(ex);
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
            out.println("<title>Servlet MovieCinemaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MovieCinemaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         int cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
        List<Movie> movies = movieDAO.getAllMovie(); // Ensure this method returns all necessary details
        List<MovieCinema> movieCinemas = movieCinemaDAO.getMoviesByCinemaID(cinemaID);

        request.setAttribute("movies", movies);
        request.setAttribute("movieCinemas", movieCinemas);
        request.setAttribute("cinemaID", cinemaID);
        request.getRequestDispatcher(router.MOVIECINEMA).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
        int movieID = Integer.parseInt(request.getParameter("movieID"));
        String status = request.getParameter("status");

        movieCinemaDAO.addMovieToCinema(movieID, cinemaID, status);

        response.sendRedirect("movieCinema?cinemaID=" + cinemaID);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
