/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.booking;

import DAO.CinemaChainDAO;
import DAO.CinemaDAO;
import DAO.MovieDAO;
import DAO.booking.OrderDAO;
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
import model.Order;
import model.Seat;
import model.booking.Bill;
import model.booking.CanteenItemOrder;
import model.movie.MovieInfo;
import util.RouterJSP;
import util.RouterURL;
import util.auth.AuthUtil;

/**
 *
 * @author PC
 */
@WebServlet(name = "BillServlet", urlPatterns = {"/order/view"})
public class BillServlet extends HttpServlet {

    OrderDAO orderDAO;
    MovieDAO movieDAO;
    CinemaDAO cinemaDAO;
    CinemaChainDAO cinemaChainDAO;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            orderDAO = new OrderDAO(getServletContext());
            movieDAO = new MovieDAO(getServletContext());
            cinemaDAO = new CinemaDAO(getServletContext());
            cinemaChainDAO = new CinemaChainDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(BillServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet BillServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BillServlet at " + request.getContextPath() + "</h1>");
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
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        List<Bill> orders = orderDAO.getListOrderTicket(userID);

        request.setAttribute("orders", orders);
        request.setAttribute("viewDetail", false);
        request.getRequestDispatcher(RouterJSP.VIEW_ORDER).forward(request, response);
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
        try {

            String orderID = request.getParameter("orderID");
            String movieID = request.getParameter("movieID");

            System.out.println("orderID" + orderID);

            System.out.println("movieID" + movieID);

            if (orderID == null || movieID == null) {
                System.err.println("null prameter");
                return;
            }
            
           

            List<Seat> seats = orderDAO.getSeatsByOrderID(Integer.parseInt(orderID));
            List<CanteenItemOrder> canteenItems = orderDAO.getCanteenItemsByOrderID(Integer.parseInt(orderID));
            System.out.println("canteen items: " + canteenItems);
            MovieInfo movieInfor = movieDAO.getMovieWithGenresByID(Integer.parseInt(movieID));
            int cinemaID = movieInfor.getCinemaID();
            Cinema cinema = cinemaDAO.getCinemaByID(cinemaID);

            Order order = orderDAO.getOrder(Integer.parseInt(orderID));

            CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByID(cinema.getCinemaChainID());
            System.out.println("order" + order);
            System.out.println("seats" + seats);

            request.setAttribute("canteenItems", canteenItems);
            request.setAttribute("seats", seats);
            request.setAttribute("movieInfor", movieInfor);
            request.setAttribute("cinema", cinema);
            request.setAttribute("cinemaChain", cinemaChain);
            request.setAttribute("order", order);

            request.getRequestDispatcher(RouterJSP.ORDER_DETAIL_PAGE).forward(request, response);

        } catch (Exception ex) {
            Logger.getLogger(BillServlet.class.getName()).log(Level.SEVERE, null, ex);
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
