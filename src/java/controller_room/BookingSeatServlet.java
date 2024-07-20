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
@WebServlet(name = "BookingSeatServlet", urlPatterns = {"/user/booking/seat"})
public class BookingSeatServlet extends HttpServlet {

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

        checkUserID(request, response);
        String movieSlotIDStr = (String) request.getParameter("movieSlotID");

        if (movieSlotIDStr == null) {
            response.sendRedirect(RouterURL.HOMEPAGE);
        }

        int movieSlotID = Integer.parseInt(movieSlotIDStr);


        try {

            UserDAO userDAO = new UserDAO(request.getServletContext());
            BookingDAO bookingDAO = new BookingDAO(getServletContext());
            List<CanteenItem> canteenItems = bookingDAO.getListAllCanteenItem();

            MovieSlot movieSlot = userDAO.queryMovieSlots(movieSlotID);

            Movie movie = userDAO.queryMovie(movieSlotID);
            Room room = userDAO.queryRoom(movieSlotID);

            List<Seat> seats = userDAO.querySeatsInRoom(movieSlotID);
            
            int maxWidth = 0, maxLength = 0;

            for (Seat seat : seats) {
                maxWidth = Math.max(maxWidth, seat.getY());
                maxLength = Math.max(maxLength, seat.getX());
            }

            request.setAttribute("movieSlotID", movieSlotID);
            request.setAttribute("movieSlot", movieSlot);
            request.setAttribute("movie", movie);
            request.setAttribute("room", room);
            request.setAttribute("seats", seats);
            request.setAttribute("maxWidth", maxWidth);
            request.setAttribute("maxLength", maxLength);
            request.setAttribute("canteenItems", canteenItems);

            // init session for order ticket
            
            OrderTicket order = new OrderTicket();
            
            order.setRoomName(room.getName());
            order.setRoomType(room.getType());
            order.setMovieName(movie.getTitle());
            order.setSlotMovie(movieSlot.getFormattedStartTime() + "~" + movieSlot.getFormattedEndTime());
            order.setDate(movieSlot.getFormattedDate());
            //
            HttpSession session = request.getSession();
            
            session.setAttribute("order", order);
            //
        } catch (Exception ex) {
            Logger.getLogger(BookingSeatServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher(RouterJSP.BOOKING_SEAT).forward(request, response);

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
        response.setContentType("text/html");
        HttpSession session = request.getSession();

        Integer userID = checkUserID(request, response);

        // check user ID
        if (userID == null || userID == 0) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        String movieSlotIDParam = request.getParameter("movieSlotID");

        String totalCostCanteenStr = request.getParameter("totalPriceCanteenItem");

        String totalCostTicketStr = request.getParameter("totalPriceTicket");

        double totalCostCanteen = -1;
        double totalCostTicket = -1;

        if (movieSlotIDParam == null || movieSlotIDParam.isEmpty()) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        int movieSlotID = -1;

        try {
            totalCostCanteen = Double.parseDouble(totalCostCanteenStr);
            totalCostTicket = Double.parseDouble(totalCostTicketStr);
            movieSlotID = Integer.parseInt(movieSlotIDParam);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        List<Integer> seatIDs = getListSeatIDs(request);

        List<CanteenItemOrder> canteenOrders = getListCanteenOrder(request);

        PrintWriter out = response.getWriter();

        OrderTicket order = (OrderTicket) session.getAttribute("order");
    
        if (order == null) {
            order = new OrderTicket();
        }

        order.setMovieSlotID(movieSlotID);
        order.setUserID(userID);
        order.setSeatsID(seatIDs);
        order.setCanteenItemOrders(canteenOrders);
        order.setTotalPriceCanteen(totalCostCanteen);
        order.setTotalPriceTicket(totalCostTicket);
       

        //
        response.sendRedirect(RouterURL.PAYMENT_VNPAY);

        // handle paying before add into database
    }

    //--------------------------------------------------------------//
    private List<Integer> getListSeatIDs(HttpServletRequest request) {
        List<Integer> seatIDs = new ArrayList<>();

        for (int i = 1; i <= 8; ++i) {
            String seatID = request.getParameter("selectedSeat" + i);

            if (seatID == null || seatID.isEmpty()) {
                continue;
            }
            try {
                int seatIDInt = Integer.parseInt(seatID);
                seatIDs.add(seatIDInt);
            } catch (NumberFormatException e) {
                continue; // Exit the method if seatID is not a number
            }

        }
        return seatIDs;
    }

    private List<CanteenItemOrder> getListCanteenOrder(HttpServletRequest request) {
        List<CanteenItemOrder> canteenOrders = new ArrayList<>();

        // Iterate over all parameters
        request.getParameterMap().forEach((key, value) -> {
            if (key.startsWith("quantity_")) {
                try {
                    int id = Integer.parseInt(key.substring(9)); // Extract item ID from parameter name
                    int amount = Integer.parseInt(value[0]);
                    if (amount > 0) {
                        canteenOrders.add(new CanteenItemOrder(amount, id));
                    }
                } catch (NumberFormatException e) {
                    // Handle potential number format exception
                }
            }
        });
        return canteenOrders;
    }

    public Integer checkUserID(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy userID từ session
        Integer userID = (Integer) request.getSession().getAttribute("userID");

        // Kiểm tra userID
        if (userID == null || userID == 0) {
            // Nếu không có userID hoặc nó bằng 0, chuyển hướng đến trang đăng nhập
            response.sendRedirect(RouterURL.LOGIN);
            return null;
        }

        return userID;
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