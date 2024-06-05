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
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.booking.CanteenItemOrder;
import model.booking.OrderTicket;
import util.RouterJSP;
import util.RouterURL;
import util.VnPayConfig;

/**
 *
 * @author PC
 */
@WebServlet(name = "BookingTicketServlet", urlPatterns = {"/booking/ticket"})
public class BookingTicketServlet extends HttpServlet {

    BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            bookingDAO = new BookingDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(BookingTicketServlet.class.getName()).log(Level.SEVERE, null, ex);
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

//        boolean parameterMissing = false;
//        Map fields = new HashMap();
//        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
//            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
//            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                fields.put(fieldName, fieldValue);
//            } else {
//                parameterMissing = true; // Set the flag if any parameter is missing
//            }
//        }
//        if (parameterMissing) {
//            response.sendRedirect(RouterURL.BOOKING_SEAT);
//            return;
//        }

//        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
//        if (fields.containsKey("vnp_SecureHashType")) {
//            fields.remove("vnp_SecureHashType");
//        }
//        if (fields.containsKey("vnp_SecureHash")) {
//            fields.remove("vnp_SecureHash");
//        }
//        String signValue = VnPayConfig.hashAllFields(fields);
//       
//        request.setAttribute("vnp_TxnRef", request.getParameter("vnp_TxnRef"));
//        request.setAttribute("vnp_Amount", request.getParameter("vnp_Amount"));
//        request.setAttribute("vnp_OrderInfo", request.getParameter("vnp_OrderInfo"));
//        request.setAttribute("vnp_ResponseCode", request.getParameter("vnp_ResponseCode"));
//        request.setAttribute("vnp_TransactionNo", request.getParameter("vnp_TransactionNo"));
//        request.setAttribute("vnp_BankCode", request.getParameter("vnp_BankCode"));
//        request.setAttribute("vnp_PayDate", request.getParameter("vnp_PayDate"));
//        request.setAttribute("vnp_TransactionStatus", request.getParameter("vnp_TransactionStatus"));
//
//        if (!signValue.equals(vnp_SecureHash)) {
//
//            request.setAttribute("message", "Giao dịch thất bại ");
//
//        } else if (!"00".equals(request.getParameter("vnp_TransactionStatus"))) {
//            request.setAttribute("message", "Giao dịch thất bại ");
//            System.out.print("Giao Dịch Không thành công");
//
//        } else {
//
//            // Add Info Into Database
//            HttpSession session = request.getSession();
//            OrderTicket order = (OrderTicket) session.getAttribute("order");
//
//            if (order == null) {
//                request.setAttribute("message", "Đặt vé thất bại ");
//                System.out.print("Order NULL");
//            } else if (order.getUserID() == 0 || order.getSeatsID() == null || order.getCanteenItemOrders() == null || order.getMovieSlotID() == 0) {
//                request.setAttribute("message", "Đặt vé thất bại ");
//                System.out.print("Missing Param in Order");
//            } else {
//
//                boolean isBooked = bookingDAO.bookTicketMovieSlot(order.getUserID(), order.getMovieSlotID(), order.getSeatsID(), order.getCanteenItemOrders());
//
//                if (!isBooked) {
//                    System.out.print("Đặt vé thất bại");
//                    request.setAttribute("message", "Đặt vé thất bại ");
//                } else {
//                    request.setAttribute("message", "Đặt vé thành công");
//                }
//
//            }
//
//        }
//
//        request.getRequestDispatcher(RouterJSP.RETURN_TRACSACTION_BOOKING_TICKET).forward(request, response);
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

        Integer userID = (Integer) session.getAttribute("userID");
        // check user ID
        
        if (userID == null || userID == 0) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        String movieSlotIDParam = request.getParameter("movieSlotID");

        String totalCostCanteenStr = request.getParameter("totalPriceCanteenItem");

        String totalCostTicketStr = request.getParameter("totalPriceTicket");

        double totalCostCanteen;
        double totalCostTicket;

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
//
        List<CanteenItemOrder> canteenOrders = getListCanteenOrder(request);
        // handle paying before add into database
        // hanle add canteen, seat, ticket , order into database
      
        PrintWriter out = response.getWriter();

//        out.println("user Id:" + userID);
//
//        out.println("movie slot Id:" + movieSlotID);
//        out.println("total price ticket" + totalCostTicketStr);
//        out.println("total price food" + totalCostCanteenStr);

//        if (isBooked) {
//            out.println("booking succes!");
//        } else {
//            out.println(": \"booking fail!\"");
//        }
        out.close();
    }
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
