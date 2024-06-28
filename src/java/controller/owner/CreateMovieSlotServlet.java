/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.schedule.ScheduleDAO;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MovieSlot;
import util.RouterJSP;

/**
 *
 * @author duyqu
 */
@WebServlet(name = "CreateMovieSlotServlet", urlPatterns = {"/CreateMovieSlotServlet"})
public class CreateMovieSlotServlet extends HttpServlet {

    RouterJSP route = new RouterJSP();
    ScheduleDAO scheduleDAO;

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
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(CreateMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        boolean overlap = true;
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet CreateMovieSlotServlet</title>");  
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet CreateMovieSlotServlet at " + request.getContextPath () + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
        System.out.println(request.getParameter("cinema"));
        System.out.println(request.getParameter("room"));
        System.out.println(request.getParameter("movie"));
        System.out.println(request.getParameter("date"));
        System.out.println(request.getParameter("startTime"));
        System.out.println(request.getParameter("endTime"));

        LocalDate date = LocalDate.parse(request.getParameter("date"));
        LocalTime time = LocalTime.parse(request.getParameter("startTime"));

// Combine date and time into LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        dateTime = LocalDateTime.of(LocalDate.parse(request.getParameter("date")), LocalTime.parse(request.getParameter("startTime")));

// Convert LocalDateTime to Date
        Date dateObject = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("output:");
        System.out.println(date);
        System.out.println("datetime" + dateTime);
// Check the result
        System.out.println("dateObject" + dateObject);
        overlap = scheduleDAO.checkOverlap(
                Date.from(LocalDateTime.of(
                        LocalDate.parse(request.getParameter("date")),
                        LocalTime.parse(request.getParameter("startTime"))
                ).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(
                        LocalDate.parse(request.getParameter("date")),
                        LocalTime.parse(request.getParameter("endTime"))
                ).atZone(ZoneId.systemDefault()).toInstant()),
                Integer.parseInt(request.getParameter("room")));

        request.setAttribute("message", "SAMPLE ERROR MESSAGE" + (String) request.getParameter("cinema"));

        if (!overlap) {
            try {
                scheduleDAO.insertMovieSlot(new MovieSlot(
                        Integer.parseInt(request.getParameter("cinema")),
                        Integer.parseInt(request.getParameter("room")),
                        Integer.parseInt(request.getParameter("movie")),
                        LocalDateTime.of(
                                LocalDate.parse(request.getParameter("date")),
                                LocalTime.parse(request.getParameter("startTime"))
                        ),
                        LocalDateTime.of(
                                LocalDate.parse(request.getParameter("date")),
                                LocalTime.parse(request.getParameter("endTime"))
                        ),
                        "TYPE",//TEMPLATE
                        Float.parseFloat(request.getParameter("price")),
                        0,//TEMPLATE
                        "Scheduled"//TEMPLATE
                ));
                System.out.println(new MovieSlot(
                        Integer.parseInt(request.getParameter("cinema")),
                        Integer.parseInt(request.getParameter("room")),
                        Integer.parseInt(request.getParameter("movie")),
                        LocalDateTime.of(
                                LocalDate.parse(request.getParameter("date")),
                                LocalTime.parse(request.getParameter("startTime"))
                        ),
                        LocalDateTime.of(
                                LocalDate.parse(request.getParameter("date")),
                                LocalTime.parse(request.getParameter("endTime"))
                        ),
                        "TYPE",//TEMPLATE
                        Float.parseFloat(request.getParameter("price")),
                        0,//TEMPLATE
                        "Scheduled"//TEMPLATE
                ));
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
                request.setAttribute("message", ex.getMessage());
                Logger.getLogger(CreateMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.setContentType("text/html;charset=UTF-8");
        }else{
            request.setAttribute("message", "OVERLAP");
        }

        request.getRequestDispatcher(route.CREATE_MOVIE_SLOT).forward(request, response);
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
