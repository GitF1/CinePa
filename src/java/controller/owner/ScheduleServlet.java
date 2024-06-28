/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.MovieDAO;
import DAO.schedule.ScheduleDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Collections.list;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Banner;
import model.MovieSlot;
import model.MovieSlot2;
import model.Review;

/**
 *
 * @author duyqu
 */
@WebServlet(name = "ScheduleServlet", urlPatterns = {"/ScheduleServlet"})
public class ScheduleServlet extends HttpServlet {

    ScheduleDAO scheduleDAO;
    MovieDAO movieDAO;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void init(ServletConfig config)
            throws ServletException {
        super.init(config); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            scheduleDAO = new ScheduleDAO(getServletContext());
            movieDAO = new MovieDAO(getServletContext());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ScheduleServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ScheduleServlet at " + request.getContextPath() + "</h1>");
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
        String text = "some text1";
        long valueDateFromClient = Long.parseLong((String) request.getParameter("date"));
//        java.util.Date date = new GregorianCalendar(2024, Calendar.JUNE, 25).getTime();;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date date = new Date(valueDateFromClient);
//        Date date = new GregorianCalendar(2024, Calendar.JUNE, 25).getTime();
        System.out.println(date);

        ArrayList<MovieSlot2> list = new ArrayList<>();
        for (MovieSlot ms : scheduleDAO.getDateSchedule(date, Integer.parseInt(request.getParameter("room")))) {
//            System.out.println(ms);
            MovieSlot2 ms2 = new MovieSlot2(ms);
            try {
                ms2.setTitle(movieDAO.getTitleByID(Integer.parseInt(ms2.getTitle())));
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            list.add(ms2);
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        String json3 = gson.toJson(list);

        try {

            System.out.println(json3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        response.getWriter().write(json3);       // Write response body.
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
//        processRequest(request, response);
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
