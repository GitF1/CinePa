/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.payment;

import DAO.booking.BookingDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.booking.OrderTicket;
import service.VnPayService;
import util.RouterJSP;


/**
 *
 * @author PC
 */
@WebServlet(name = "VnPayServlet", urlPatterns = {"/payment/vnpay"})
public class VnPayServlet extends HttpServlet {

    VnPayService vnpay;
    BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        vnpay = new VnPayService();
        try {
            bookingDAO = new BookingDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(VnPayServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet VnPayServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VnPayServlet at " + request.getContextPath() + "</h1>");
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
        
        HttpSession session = request.getSession();
        
        OrderTicket order = (OrderTicket) session.getAttribute("order");
        System.out.println("order: "+ order);
        // set properties forword jsp
        request.setAttribute("totalPriceTicket", order.getTotalPriceTicket());
        request.setAttribute("totalPriceCanteen", order.getTotalPriceCanteen());
        request.setAttribute("movieName", order.getMovieName());
        request.setAttribute("slotMovie", order.getSlotMovie());
        request.setAttribute("date", order.getDate());
        request.setAttribute("canteenOrders", order.getCanteenItemOrders());

        request.getRequestDispatcher(RouterJSP.VN_PAY_PAYMENT_HOMEPAGE).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param req
     * @param res
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        OrderTicket order = (OrderTicket) session.getAttribute("order");
        bookingDAO.bookTicketMovieSlot(order.getUserID(), order.getMovieSlotID(), order.getSeatsID(), order.getCanteenItemOrders(), req, res);
     
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
