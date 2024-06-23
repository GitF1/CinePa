/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner.order;

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
import util.Role;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author PC
 */
@WebServlet(name = "ConfirmOrderServlet", urlPatterns = {"/order/confirm"})
public class ConfirmOrderServlet extends HttpServlet {

    ConfirmDAO confirmDAO;

    @Override
    public void init() throws ServletException {

        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            confirmDAO = new ConfirmDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(ConfirmOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        System.out.println("role user: " + role);

        if (role == null) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        boolean isValidRole = Role.isRoleValid(role, Role.OWNER);
        System.out.println("role valid: " + isValidRole);

        if (!isValidRole) {
            // Redirect to error page if role is invalid
            response.sendRedirect(RouterURL.ERORPAGE);
            return;
        }

        String orderIdParam = request.getParameter("orderID");
        String userIdParam = request.getParameter("userID");
        String code = request.getParameter("code");

        int orderId = 0;
        int userId = 0;
        boolean validParams = true;

        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            validParams = false;
            e.printStackTrace();
            // Handle invalid orderID
        }

        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            validParams = false;
            e.printStackTrace();
            // Handle invalid userID
        }
        //
        if (!validParams) {
            response.sendRedirect(RouterURL.ERORPAGE);
            return;
        }
        //
        boolean isValid = confirmDAO.validateAndCheckInOrder(orderId, userId, code);
        String resultMessage;
        if (isValid) {
            resultMessage = "Order Confirm successfully.";
        } else {
            resultMessage = "Failed to validate order or update status. Try Again";
        }


        request.setAttribute("message", resultMessage);
        
        request.getRequestDispatcher(RouterJSP.OWNER_COMFIRM_RESULT_PAGE).forward(request, response);
        

      
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
        doGet(request, response);
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
