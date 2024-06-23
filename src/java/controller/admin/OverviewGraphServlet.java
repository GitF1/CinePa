/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import controller.user.UpdateUserInfo;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
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
import model.graph.SalesData;
import util.RouterJSP;

/**
 *
 * @author duyqu
 */
@WebServlet(name = "OverviewGraphServlet", urlPatterns = {"/OverviewGraphServlet"})
public class OverviewGraphServlet extends HttpServlet {

    RouterJSP route = new RouterJSP();
    DAO.GraphDAO graphDAO;

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
            out.println("<title>Servlet OverviewGraphServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OverviewGraphServlet at " + request.getContextPath() + "</h1>");
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
    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();

        try {
            graphDAO = new DAO.GraphDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //        processRequest(request, response);
            graphDAO.getTotalSalesValueLast7Days();
            for (SalesData sd : graphDAO.getTotalSalesValueLast7Days()) {
                System.out.println(sd);
            }
            request.setAttribute("sales7Day", graphDAO.getTotalSalesValueLast7Days());
            request.getRequestDispatcher(route.SALES_REPORT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(OverviewGraphServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            //        processRequest(request, response);
            graphDAO.getTotalSalesValueLast7Days();
            for (SalesData sd : graphDAO.getTotalSalesValueLast7Days()) {
                System.out.println(sd);
            }
            request.setAttribute("sales7Day", graphDAO.getTotalSalesValueLast7Days());
            request.getRequestDispatcher(route.ADMIN).forward(request, response);
            
        } catch (SQLException ex) {
            Logger.getLogger(OverviewGraphServlet.class.getName()).log(Level.SEVERE, null, ex);
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
