/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_room;

import DAO.UserDAO;
import DAO.booking.BookingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CanteenItem;
import model.Movie;
import model.MovieSlot;
import model.Room;
import model.Seat;
import model.booking.CanteenItemOrder;
import model.booking.OrderTicket;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author ACER
 */
@WebServlet(name = "ViewSeatsServlet", urlPatterns = {"/owner/viewseats"})
public class ViewSeatsServlet extends HttpServlet {

    BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            bookingDAO = new BookingDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(BookingSeatServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String roomIDStr = request.getParameter("roomID");
        if(roomIDStr == null) {
            response.sendRedirect(RouterURL.HOMEPAGE);
        }
        int roomID = Integer.parseInt(roomIDStr);
        try {
            UserDAO userDAO = new UserDAO(request.getServletContext());

            Room room = userDAO.queryRoomByRoomID(roomID);
            List<Seat> seats = userDAO.queryAllSeatsInRoom(roomID);
            
            int maxWidth = 0, maxLength = 0;

            for (Seat seat : seats) {
                maxWidth = Math.max(maxWidth, seat.getY());
                maxLength = Math.max(maxLength, seat.getX());
            }
            request.setAttribute("room", room);
            request.setAttribute("seats", seats);
            request.setAttribute("maxWidth", maxWidth);
            request.setAttribute("maxLength", maxLength);
            //
        } catch (Exception ex) {
            Logger.getLogger(BookingSeatServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher(RouterJSP.VIEW_SEATS).forward(request, response);

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