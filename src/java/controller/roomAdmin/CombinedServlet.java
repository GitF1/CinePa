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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import model.CinemaChain;
import util.RouterJSP;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "CombinedServlet", urlPatterns = {"/roomAdmin/combined"})
public class CombinedServlet extends HttpServlet {

    private RoomDAO roomDAO;
    private RouterJSP router = new RouterJSP();

    @Override
    public void init() throws ServletException {
        try {
            this.roomDAO = new RoomDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CombinedServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet CombinedServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CombinedServlet at " + request.getContextPath() + "</h1>");
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
            List<CinemaChain> cinemaChains = roomDAO.getAllCinemaChains();
            request.setAttribute("cinemaChains", cinemaChains);

            String cinemaChainID = request.getParameter("cinemaChainID");
            if (cinemaChainID != null) {
                int id = Integer.parseInt(cinemaChainID);
                List<Cinema> cinemas = roomDAO.getCinemasByCinemaChainID(id);
                request.setAttribute("cinemas", cinemas);
                request.setAttribute("selectedCinemaChainID", id);
            }

            request.getRequestDispatcher(router.Combined_Page).forward(request, response);
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
