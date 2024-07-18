/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner.transaction;

import DAO.transaction.BalanceUpdater;
import DAO.transaction.TransactionDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;

/**
 *
 * @author PC
 */
@WebServlet(name = "WithDrawServlet", urlPatterns = {"/owner/transaction/withdraw"})
public class WithDrawServlet extends HttpServlet {

    BalanceUpdater d;
    TransactionDAO tsDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            d = new BalanceUpdater(getServletContext());
            tsDAO = new TransactionDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(WithDrawServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet WithDrawServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WithDrawServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

            Integer userID = (int) request.getSession().getAttribute("userID");
            String role = (String) request.getSession().getAttribute("role");

            if (userID == null || !util.Role.OWNER.equalsIgnoreCase(role)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to perform this action.");
                return;
            }
            double balance = tsDAO.getBalance(userID);
            double amount = Double.parseDouble(request.getParameter("amount"));

            if (amount < 200000) {
                request.setAttribute("message", "Minimum withdraw amount is 200,000.");
                request.getRequestDispatcher(RouterJSP.OWNER_BUDGET_PAGE).forward(request, response);
                return;
            }
            if (amount < 20000000) {
                request.setAttribute("message", "Maximum withdraw amount is 20,000,000.");
                request.getRequestDispatcher(RouterJSP.OWNER_BUDGET_PAGE).forward(request, response);
                return;
            }
            if (balance < amount) {
                request.setAttribute("message", "Balance Over!");
                request.getRequestDispatcher(RouterJSP.OWNER_BUDGET_PAGE).forward(request, response);
                return;
            }
            
            try {

                d.sendRequestWithDraw(userID, amount);
                request.setAttribute("message", "Withdraw request submitted successfully.");

            } catch (Exception e) {

                e.printStackTrace();
                request.setAttribute("message", "Failed to submit withdraw request.");

            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(RouterJSP.OWNER_COMFIRM_RESULT_PAGE);
            dispatcher.forward(request, response);

        } catch (SQLException ex) {

            Logger.getLogger(WithDrawServlet.class.getName()).log(Level.SEVERE, null, ex);

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
