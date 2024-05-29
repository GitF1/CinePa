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
@WebServlet(name="FilterMovieServlet", urlPatterns={"/filter-movies"})
public class FilterMovieServlet extends HttpServlet {
   
    RouterJSP router = new RouterJSP();
    MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.movieDAO = new MovieDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(FilterMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet FilterMovieServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterMovieServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String genre = request.getParameter("genre");
        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int moviesPerPage = 16;

        List<Movie> movies;
        if (genre != null && !genre.trim().isEmpty()) {
            movies = movieDAO.getMoviesByGenre(genre);
        } else {
            movies = movieDAO.getAllMovie();
        }

        int totalMovies = movies.size();
        int start = (page - 1) * moviesPerPage;
        int end = Math.min(start + moviesPerPage, totalMovies);
        List<Movie> moviesPage = movies.subList(start, end);

        Map<Integer, List<String>> movieGenresMap = movieDAO.getAllMovieGenres();

        request.setAttribute("movies", moviesPage);
        request.setAttribute("movieGenresMap", movieGenresMap);
        request.setAttribute("genre", genre);
        request.setAttribute("totalPages", (int) Math.ceil((double) totalMovies / moviesPerPage));
        request.setAttribute("currentPage", page);

            request.getRequestDispatcher(router.MOVIE_Genre).forward(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
