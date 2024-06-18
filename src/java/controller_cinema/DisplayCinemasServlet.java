/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_cinema;

import DAO.UserDAO;
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
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Cinema;
import model.CinemaChain;
import util.RouterJSP;


/**
 *
 * @author ACER
 */
@WebServlet(name = "DisplayCinemasServlet", urlPatterns = {"/displaycinemas"})
public class DisplayCinemasServlet extends HttpServlet {
    RouterJSP router = new RouterJSP();
    UserDAO userDAO;

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
            out.println("<title>Servlet DisplayCinemasServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DisplayCinemasServlet at " + request.getContextPath() + "</h1>");
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
        System.out.println("DOGET: " + LocalDateTime.now()) ;
        HttpSession session = request.getSession(false);
        try {
            userDAO = new UserDAO(request.getServletContext());
            List<String> locations;
            List<CinemaChain> cinemaChains;
            
            if(session == null || (List<String>)session.getAttribute("locations") == null) {
                locations = new ArrayList<>();
                ResultSet rs = userDAO.getData("Cinema", "distinct Province", null);
                while(rs.next()) {
                    locations.add(rs.getString("Province"));
                }   
            }
            else locations = (List<String>)session.getAttribute("locations");
            
            if(session == null || (List<CinemaChain>)session.getAttribute("cinemaChains") == null) {
                cinemaChains = new ArrayList<>();
                ResultSet rs = userDAO.getData("CinemaChain", "*" , null);
                while(rs.next()) {
                    CinemaChain cinemaChain = new CinemaChain(rs.getInt("CinemaChainID"), rs.getString("Name"), rs.getString("Information"), rs.getString("Avatar"));
                    cinemaChains.add(cinemaChain);
                }
            }
            else cinemaChains = (List<CinemaChain>)session.getAttribute("cinemaChains");
          
            if(session == null) session = request.getSession(true);
            session.setAttribute("locations", locations);
            session.setAttribute("cinemaChains", cinemaChains);
            System.out.println("Cinema Chains:");
            System.out.println(cinemaChains);

            session.setAttribute("location", "Hanoi");
            session.setAttribute("cinemaSearched", "");
            session.setAttribute("cinemaChainName", "All");
            doPost(request, response);
        } catch (Exception ex) {
            Logger.getLogger(DisplayCinemasServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
//        request.getRequestDispatcher(router.DISPLAY_CINEMAS).forward(request, response);
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
        System.out.println("DOPOST: " + LocalDateTime.now());
        HttpSession session = request.getSession(false);
        try {
            userDAO = new UserDAO(request.getServletContext());
            String location = request.getParameter("location");
            String cinemaSearched = request.getParameter("cinemaSearchedInput");
            String cinemaChainName = request.getParameter("cinemaChainNameInput");
            System.out.println("Request: location = " + location + ", cinemaSearched = " + cinemaSearched + ", cinemaChainName = " + cinemaChainName);
            
            //Update
            if(location != null) session.setAttribute("location", location);
            if(cinemaSearched != null) session.setAttribute("cinemaSearched", cinemaSearched);
            if(cinemaChainName != null) session.setAttribute("cinemaChainName", cinemaChainName);
            
            location = (String)session.getAttribute("location");
            cinemaSearched = (String)session.getAttribute("cinemaSearched");
            cinemaChainName = (String)session.getAttribute("cinemaChainName");
            
            Map<String, String> map = new HashMap<>();
            map.put("Province", location);
            map.put("Name", cinemaSearched);
            if(!cinemaChainName.equals("All")) map.put("Name", cinemaChainName);
            
            List<Cinema> cinemas = userDAO.specQueryCinemas(map);
            System.out.println(cinemas);
            
            request.setAttribute("cinemas", cinemas);
            
//            List<String> locations = (List<String>) session.getAttribute("locations");
//            request.setAttribute("locations", locations);
            
            request.getRequestDispatcher(router.DISPLAY_CINEMAS).forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(DisplayCinemasServlet.class.getName()).log(Level.SEVERE, null, ex);
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
