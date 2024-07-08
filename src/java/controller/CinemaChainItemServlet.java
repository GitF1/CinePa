/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CinemaChainDAO;
import DAO.UserDAO;
import controller.auth.LoginServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CinemaChain;
import util.RouterJSP;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "CinemaChainItemServlet", urlPatterns = {"/cinemaItem"})
public class CinemaChainItemServlet extends HttpServlet {

    RouterJSP route = new RouterJSP();
    CinemaChainDAO cinemaChainDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.cinemaChainDAO = new CinemaChainDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CinemaChainItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CinemaChainItemServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CinemaChainItemServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String cinemaName = request.getParameter("cinemaName");
            if (cinemaName != null) {
                CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByName(cinemaName);
                request.setAttribute("cinemaChain", cinemaChain);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher(route.CINEMA_CHAIN_ITEM).forward(request, response);
    }

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
