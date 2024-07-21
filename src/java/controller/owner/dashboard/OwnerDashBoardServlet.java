/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner.dashboard;

import DAO.owner.StatisticOwnerDAO;
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
import util.RouterURL;

/**
 *
 * @author PC
 */
@WebServlet(name = "OwnerDashBoardServlet", urlPatterns = {"/owner/dashboard"})
public class OwnerDashBoardServlet extends HttpServlet {

    StatisticOwnerDAO dao;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            dao = new StatisticOwnerDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(OwnerDashBoardServlet.class.getName()).log(Level.SEVERE, null, ex);
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

        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");
        System.out.println("role" + role + "useriD" + userID);
        
        if (userID == null || role == null || !role.equals(util.Role.OWNER)) {
            response.sendRedirect(RouterURL.CREATE_CINEMA_CHAIN);
            return;
        }
        
        Integer cinemaChainID = dao.getCinemaChainOfUser(userID);

        if (cinemaChainID == null) {
            response.sendRedirect(RouterURL.OWNER_PAGE);
            return;
        }

        System.out.println("role " + role + " userID " + userID + " cinemaChainID" + cinemaChainID);
        session.setAttribute("cinemaChainID", cinemaChainID);

        double totalRevenue = dao.getTotalRevenueByChainID(cinemaChainID);

        System.out.println("totalRevue: " + totalRevenue);

        request.getRequestDispatcher(RouterJSP.OWNER_DASHBOARD_PAGE).forward(request, response);
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
