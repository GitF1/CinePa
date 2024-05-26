/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.schedule;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.schedule.ScheduleDAO;
import com.google.gson.JsonObject;
import jakarta.servlet.RequestDispatcher;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;

/**
 *
 * @author PC
 */
@WebServlet(name = "ScheduleMovieServlet", urlPatterns = {"/schedule"})
public class ScheduleMovieServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    ScheduleDAO scheduleDAO;
    RouterJSP route = new RouterJSP();

    @Override
    public void init()
            throws ServletException {
        super.init();
        try {
            scheduleDAO = new ScheduleDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(ScheduleMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            scheduleDAO.handleDoGetComponentSchedule(request, response);

            request.getRequestDispatcher(route.SCHEDULE_MOIVE).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
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

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        scheduleDAO.handleDoPostComponentSchedule(request, response);

        String ajaxHeader = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(ajaxHeader)) {
            // This is an AJAX request, send back a response

            response.getWriter().write("" + request.getAttribute("date"));
            JsonObject jsonObject = new JsonObject();
        } else {
            // This is a regular form submission, forward to the JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher(route.SCHEDULE_MOIVE);
            dispatcher.forward(request, response);
        }

    }

}
