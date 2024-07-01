/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.CinemasDAO;
import DAO.MovieDAO;
import DAO.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import model.CinemaChain;
import model.Movie;
import model.Room;
import util.RouterJSP;

/**
 *
 * @author duyqu
 */
@WebServlet(name = "CreateMovieSlotFormInfoServlet", urlPatterns = {"/CreateMovieSlotFormInfoServlet"})
public class CreateMovieSlotFormInfoServlet extends HttpServlet {

    CinemasDAO cinemasDAO;
    RoomDAO roomDAO;
    RouterJSP router = new RouterJSP();
    boolean doPost = false;
    MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.cinemasDAO = new CinemasDAO(getServletContext());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(CreateMovieSlotFormInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.roomDAO = new RoomDAO(getServletContext());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(CreateMovieSlotFormInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.movieDAO = new MovieDAO(getServletContext());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(CreateMovieSlotFormInfoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
        System.out.println(doPost);
        if (request.getParameter("cinema") != null) {
            request.setAttribute("cinema", request.getParameter("cinema"));
        }
        System.out.println("Cinema: " + request.getParameter("cinema"));
        HttpSession session = request.getSession();
//        response.setContentType("text/html;charset=UTF-8");
        Integer userID = (Integer) session.getAttribute("userID");
        System.out.println("UserID:" + userID);
        CinemaChain cinemaChain = roomDAO.getCinemaChainByUserId(userID);
        System.out.println(cinemaChain);
        request.setAttribute("cinemaChain", cinemaChain);
        int cinemaChainID = cinemaChain.getCinemaChainID();
        System.out.println("----------------------------ID:" + cinemaChainID);
        List<Cinema> cinemas = cinemasDAO.getCinemasByCinemaChainID(cinemaChainID);
        request.setAttribute("cinemas", cinemas);

        if (doPost) {
            if (request.getParameter("cinema") != null) {
                List<Room> rooms = roomDAO.getRoomsByCinemaId(Integer.parseInt(request.getParameter("cinema")));
                request.setAttribute("rooms", rooms);
                List<Movie> movies = movieDAO.getMoviesByCinemaId(Integer.parseInt(request.getParameter("cinema")));
                request.setAttribute("movies", movies);
                for (Movie m : movies) {
                    System.out.println("movie");
                    System.out.println(m);
                }
            }
        }
        if (request.getParameter("movieSlotEdit") != null) {
            request.setAttribute("movieSlotEdit", request.getParameter("movieSlotEdit"));
            request.getRequestDispatcher(router.EDIT_MOVIE_SLOT).forward(request, response);
        } else {
            request.getRequestDispatcher(router.CREATE_MOVIE_SLOT).forward(request, response);
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
        processRequest(request, response);
        doPost = false;
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
        doPost = true;
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
