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
import util.RouterJSP;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CinemaChain;

@WebServlet(name = "ListCinemaChainServlet", urlPatterns = {"/roomAdmin/cinemaChain"})
public class ListCinemaChainServlet extends HttpServlet {

    RouterJSP router = new RouterJSP();
    RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.roomDAO = new RoomDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(ListCinemaChainServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet ListCinemaChainServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListCinemaChainServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        try {
            List<CinemaChain> cinemaChains = roomDAO.getAllCinemaChains();
            request.setAttribute("cinemaChains", cinemaChains);
            request.getRequestDispatcher(router.LIST_CINEMACHAIN).forward(request, response);
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
