/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import DAO.CinemaChainDAO;
import DAO.UserDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;

/**
 *
 * @author ACER
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    RouterJSP route = new RouterJSP();
    UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.userDAO = new UserDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        request.getRequestDispatcher(route.LOGIN).forward(request, response);
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

        String username_email = request.getParameter("username-email");
        String password = request.getParameter("password");
        String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        Boolean ok = null;
        String role = "";
        String username = "";
        try {
            role = userDAO.getUserRole(username_email);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            ok = userDAO.checkLogin(username_email, hash);

        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpSession session = request.getSession();
        if (ok) {
//            Switch case for role
//          Add user to session attribute
            try {
                username = userDAO.getUsername(username_email);

                session.setAttribute("username", username);
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            switch (role) {
                case "user":
                    //TEMP CODE FOR GETTING CHAINS & Username

                    ArrayList<String> cinemaNames = null;
                    try {
                        CinemaChainDAO cc = new CinemaChainDAO(request.getServletContext());
                        cinemaNames = cc.getCinemaChainList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    HttpSession session = request.getSession();
                    session.setAttribute("chains", cinemaNames);
                    //END OF TEMP CODE
                    request.getRequestDispatcher(route.USER).forward(request, response);
                    break;
                case "staff":
                    request.getRequestDispatcher(route.STAFF).forward(request, response);
                    break;
                case "admin":
                    request.getRequestDispatcher(route.ADMIN).forward(request, response);
                    break;
            }//end of switch
            response.sendRedirect("/movie");

        } else {
            request.setAttribute("ok", ok);
            request.getRequestDispatcher(route.LOGIN).forward(request, response);
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
