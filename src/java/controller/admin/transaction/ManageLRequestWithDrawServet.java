/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin.transaction;

import DAO.transaction.BalanceUpdater;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author PC
 */
@WebServlet(name = "ManageLRequestWithDrawServet", urlPatterns = {"/admin/transaction/manage/withdraw"})
public class ManageLRequestWithDrawServet extends HttpServlet {

    BalanceUpdater dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new BalanceUpdater(getServletContext());

            super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        } catch (Exception ex) {
            Logger.getLogger(ManageLRequestWithDrawServet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet ManageLRequestWithDrawServet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageLRequestWithDrawServet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher(RouterJSP.ADMIN_MANAGE_REQUEST_WITHDRAW).forward(request, response);

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

        String userIDStr = (String) request.getParameter("userId");
        String amountStr = (String) request.getParameter("amount");
        String transationIDStr = (String) request.getParameter("transactionID");

        Integer userID = null;
        Double amount = null;
        Integer transactionID = null;

        try {
            userID = Integer.valueOf(userIDStr);
            amount = Double.valueOf(amountStr);
            transactionID = Integer.valueOf(transationIDStr);

        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.ERORPAGE);
        }

        if (userID == null || amount == null || transactionID == null || amount <= 0) {
            response.sendRedirect(RouterURL.ERORPAGE);
            return;
        }
        System.out.println("userID" + userID + "transationID" + transactionID);
        
        dao.withDrawing(transactionID, userID, amount, request, response);

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
