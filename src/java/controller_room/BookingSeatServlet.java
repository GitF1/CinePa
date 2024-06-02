/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_room;

import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;
import model.MovieSlot;
import model.Room;
import model.Seat;
import util.Router;

/**
 *
 * @author ACER
 */
@WebServlet(name = "BookingSeatServlet", urlPatterns = {"/booking/seat"})
public class BookingSeatServlet extends HttpServlet {

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
            out.println("<title>Servlet BookingSeatServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingSeatServlet at " + request.getContextPath() + "</h1>");
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
        int movieSlotID = 3;
        try {
            UserDAO userDAO = new UserDAO(request.getServletContext());
            MovieSlot movieSlot = userDAO.queryMovieSlots(movieSlotID);
            Movie movie = userDAO.queryMovie(movieSlotID);
            Room room = userDAO.queryRoom(movieSlotID);
            List<Seat> seats = userDAO.querySeatsInRoom(movieSlotID);
            int maxWidth = 0, maxLength = 0;
            for(Seat seat : seats) {
                System.out.println(seat);
            } // print seat
            
            for(Seat seat : seats) {
                maxWidth = Math.max(maxWidth, seat.getY());
                maxLength = Math.max(maxLength, seat.getX());
            }
            System.out.println(movieSlot);
            request.setAttribute("movieSlot", movieSlot);
            request.setAttribute("movie", movie);
            request.setAttribute("room", room);
            request.setAttribute("seats", seats);
            request.setAttribute("maxWidth", maxWidth);
            request.setAttribute("maxLength", maxLength);
            request.getRequestDispatcher(Router.BOOKING_SEAT).forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(BookingSeatServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        PrintWriter out = response.getWriter();
        List<String> seatIDs = new ArrayList<>();
        for(int i = 1; i <= 8; ++i) {
            String seatID = request.getParameter("selectedSeat" + i);
            if(!seatID.isEmpty()) seatIDs.add(seatID);
        }
        out.println(seatIDs);
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
