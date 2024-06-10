/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.roomAdmin;

import DAO.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import util.RouterJSP;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "ListCinemaServlet", urlPatterns = {"/roomAdmin/cinemas"})
public class ListCinemaServlet extends HttpServlet {

    RoomDAO roomDAO;
    RouterJSP router = new RouterJSP();

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.roomDAO = new RoomDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(ListCinemaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet ListCinemaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListCinemaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        try {
            int cinemaChainID = Integer.parseInt(request.getParameter("cinemaChainID"));
            List<Cinema> cinemas = roomDAO.getCinemasByCinemaChainID(cinemaChainID);
            
            // Logging for debugging
            System.out.println("Cinemas found: " + cinemas.size());
            for (Cinema cinema : cinemas) {
                System.out.println(cinema.getAddress());
            }

            request.setAttribute("cinemas", cinemas);
            request.getRequestDispatcher(router.List_Cinema).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
