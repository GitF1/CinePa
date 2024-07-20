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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import model.Movie;
import model.MovieWithStatus;
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
        String country = request.getParameter("country");
        String yearParam = request.getParameter("year");
        int year = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : -1;
        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int moviesPerPage = 16;

        List<Movie> movies = movieDAO.getAllMovie();

        if (genre != null && !genre.trim().isEmpty()) {
            movies = movies.stream().filter(movie -> {
                List<String> genres = movieDAO.getGenresForMovie(movie.getMovieID());
                return genres.contains(genre);
            }).collect(Collectors.toList());
        }

        if (country != null && !country.trim().isEmpty()) {
            movies = movies.stream().filter(movie -> movie.getCountry().equals(country)).collect(Collectors.toList());
        }

        if (year != -1) {
            movies = movies.stream().filter(movie -> {
                int movieYear = Integer.parseInt(movie.getDatePublished().split("-")[0]);
                return movieYear == year;
            }).collect(Collectors.toList());
        }

        int totalMovies = movies.size();
        int start = (page - 1) * moviesPerPage;
        int end = Math.min(start + moviesPerPage, totalMovies);
        List<Movie> moviesPage = movies.subList(start, end);

        Map<Integer, List<String>> movieGenresMap = movieDAO.getAllMovieGenres();
        Set<String> allGenres = movieDAO.getAllGenres();
        Set<String> allCountries = movieDAO.getAllCountries();
        List<Integer> allYears = movieDAO.getAllYears().stream().sorted().collect(Collectors.toList());

        request.setAttribute("movies", moviesPage);
        request.setAttribute("movieGenresMap", movieGenresMap);
        request.setAttribute("allGenres", allGenres);
        request.setAttribute("allCountries", allCountries);
        request.setAttribute("allYears", allYears);
        request.setAttribute("genre", genre);
        request.setAttribute("country", country);
        request.setAttribute("year", year);
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
