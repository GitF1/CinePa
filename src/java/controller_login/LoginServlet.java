/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_login;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Router;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author ACER
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    Router route = new Router();
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
        ResultSet rs;
        Boolean ok;

        try {
            rs = userDAO.checkLogin(username_email, hash);
            ok = rs.next();
            if (ok) {
                User user = new User(rs.getInt("UserID"), rs.getString("AvatarLink"), rs.getString("Role"), rs.getString("Username"), rs.getString("Bio"), rs.getString("Email"), rs.getString("Fullname"), rs.getDate("Birthday"), rs.getString("Address"), rs.getBoolean("isBanned"), rs.getInt("LevelPremiumID"), rs.getDouble("AccountBalance"), rs.getInt("BonusPoint"), rs.getString("Province"), rs.getString("District"), rs.getString("Commune"));
                System.out.println(user);
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                
//                List<String> locations = new ArrayList<>();
//                ResultSet rs2 = userDAO.getData("Cinema", "distinct Province", null);
//                while(rs2.next()) {
//                    locations.add(rs.getString("Province"));
//                }
//                session.setAttribute("locations", locations);
                
                response.sendRedirect(route.HOMEPAGE);
            } else {
                request.setAttribute("ok", ok);
                request.getRequestDispatcher(route.LOGIN).forward(request, response);
            }
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
