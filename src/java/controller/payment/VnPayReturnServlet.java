/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.payment;

import DAO.booking.BookingDAO;
import DAO.confirm.ConfirmDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;
import util.Util;
import util.error.Retry;

/**
 *
 * @author PC
 */
@WebServlet(name = "VnPayReturnServlet", urlPatterns = {"/payment/vnpay/return"})
public class VnPayReturnServlet extends HttpServlet {

    BookingDAO bookingDAO;
    ConfirmDAO confirmDAO;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            bookingDAO = new BookingDAO(getServletContext());
            confirmDAO = new ConfirmDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(VnPayReturnServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet VnPayReturnServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VnPayReturnServlet at " + request.getContextPath() + "</h1>");
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

        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        HttpSession session = request.getSession();
        System.out.println("order: " + session.getAttribute("order"));

        if ("00".equals(vnp_ResponseCode)) {
            try {
                // Payment successful, confirm and complete the order
                int orderID = Integer.parseInt(vnp_TxnRef);
                String codeActive = Util.generateActivationCodeOrder();
                boolean isOrderConfirmed = Retry.retryOperation(() -> bookingDAO.confirmOrder(orderID, codeActive), 3);

                System.out.println("Order confirmation result: " + isOrderConfirmed);

                boolean isSend = Retry.retryOperation(() -> confirmDAO.sendInFormationCofirm(request, response, orderID,codeActive), 3);

                if (isOrderConfirmed) {
                    session.removeAttribute("order");
                }

            } catch (Exception ex) {
                Logger.getLogger(VnPayReturnServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            // Payment failed, rollback the order
            int orderID = Integer.parseInt(vnp_TxnRef);
            bookingDAO.rollbackOrder(orderID);
        }

        request.setAttribute("vnp_TxnRef", request.getParameter("vnp_TxnRef"));
        request.setAttribute("vnp_Amount", request.getParameter("vnp_Amount"));
        request.setAttribute("vnp_OrderInfo", request.getParameter("vnp_OrderInfo"));
        request.setAttribute("vnp_ResponseCode", request.getParameter("vnp_ResponseCode"));
        request.setAttribute("vnp_TransactionNo", request.getParameter("vnp_TransactionNo"));
        request.setAttribute("vnp_BankCode", request.getParameter("vnp_BankCode"));
        request.setAttribute("vnp_PayDate", request.getParameter("vnp_PayDate"));
        request.setAttribute("vnp_TransactionStatus", request.getParameter("vnp_TransactionStatus"));

        request.getRequestDispatcher(RouterJSP.RETURN_TRACSACTION_BOOKING_TICKET).forward(request, response);

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
