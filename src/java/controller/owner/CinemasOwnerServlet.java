/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.CinemasDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import util.RouterJSP;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "CinemasOwnerServlet", urlPatterns = {"/owner/cinemas"})
public class CinemasOwnerServlet extends HttpServlet {

    CinemasDAO cinemasDAO;
    RouterJSP router = new RouterJSP();

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.cinemasDAO = new CinemasDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CinemasOwnerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet CinemasOwnerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CinemasOwnerServlet at " + request.getContextPath() + "</h1>");
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
        try {
            int cinemaChainID = Integer.parseInt(request.getParameter("cinemaChainID"));
            List<Cinema> cinemas = cinemasDAO.getCinemasByCinemaChainID(cinemaChainID, 20, 0);

            // Logging for debugging
            System.out.println("Cinemas found: " + cinemas.size());
            for (Cinema cinema : cinemas) {
                System.out.println(cinema.getAddress());
            }

            request.setAttribute("cinemas", cinemas);
            request.getRequestDispatcher(router.CINEMAS).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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
