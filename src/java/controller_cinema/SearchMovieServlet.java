/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_cinema;

import DAO.UserDAO;
import DAO.search.SearchDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;
import util.RouterJSP;

/**
 *
 * @author ACER
 */
@WebServlet("/searchmovie")
public class SearchMovieServlet extends HttpServlet {

    RouterJSP router = new RouterJSP();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchMovieServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchMovieServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(RouterJSP.LANDING_PAGE).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String movieName = request.getParameter("movieName");
        String voiceStr = request.getParameter("searchInput");

        System.out.println("voiceStr : " + voiceStr);
        try {

            String input = "";
            if (movieName == null) {
                input = voiceStr;
            }

            if (voiceStr == null) {
                input = movieName;
            }

            System.out.println("input searching : " + input);

            SearchDAO dao = new SearchDAO(request.getServletContext());
            List<Movie> movies = new ArrayList<>();
            List<Movie> moviesSearchByTitleMovie = dao.searchMovies(input);
            List<Movie> movieSearchByGenre = dao.searchMoviesByGenre(input);

            movies.addAll(moviesSearchByTitleMovie);
            movies.addAll(movieSearchByGenre);
            if(movies.isEmpty()){
               List<Movie> movieSearchAbbreviation = dao.searchMoviesByAbbreviationAndStatus(input);
               movies.addAll(movieSearchAbbreviation);
               
            }

            request.setAttribute("movieName", input);
            request.setAttribute("movies", movies);
            request.setAttribute("modalStatus", true);

            request.getRequestDispatcher(RouterJSP.LANDING_PAGE).forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SearchMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
