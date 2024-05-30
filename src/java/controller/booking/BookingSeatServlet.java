/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.booking;

import DAO.booking.BookingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Seat;
import model.CanteenItem;

/**
 *
 * @author PC
 */
@WebServlet(name = "BookingSeatServlet", urlPatterns = {"/select/seat"})
public class BookingSeatServlet extends HttpServlet {

    BookingDAO bookingDAO;

    @Override
    public void init()
            throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            bookingDAO = new BookingDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(BookingSeatServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

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

        int roomID = Integer.parseInt(request.getParameter("roomID"));

        List<Seat> seats = bookingDAO.getListSeatByRoomID(roomID);
        List<CanteenItem> canteenItems = bookingDAO.getListAllCanteenItem();
        System.out.println("list seat: " + seats);
        request.setAttribute("seats", seats);

//        Gson gson = new GsonBuilder().create();
//        String jsonSeats = gson.toJson(seats);
        // Set JSON as a request attribute
        
        request.setAttribute("seats", seats);
        request.setAttribute("canteenItems", canteenItems);
        request.getRequestDispatcher("/page/booking/Seat.jsp").forward(request, response);
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
