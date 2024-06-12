/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.CinemasDAO;
import DAO.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import util.RouterJSP;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "CreateCinemasOwnerServlet", urlPatterns = {"/owner/createCinema"})
public class CreateCinemasOwnerServlet extends HttpServlet {

    RouterJSP router = new RouterJSP();
    CinemasDAO cinemasDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.cinemasDAO = new CinemasDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CreateCinemasOwnerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet CreateCinemasOwnerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateCinemasOwnerServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(router.CREATE_CINEMA).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = request.getParameter("address");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String commune = request.getParameter("commune");
        String avatar = request.getParameter("avatar");
        int cinemaChainID = Integer.parseInt(request.getParameter("cinemaChainID"));

        Cinema cinema = new Cinema();
        cinema.setAddress(address);
        cinema.setProvince(province);
        cinema.setDistrict(district);
        cinema.setCommune(commune);
        cinema.setAvatar(avatar);
        cinema.setCinemaChainID(cinemaChainID);

        try {
            cinemasDAO.createCinema(cinema);
            response.sendRedirect(request.getContextPath() + "/owner/cinemaChain");
        } catch (Exception e) {
            throw new ServletException("Failed to create cinema", e);
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
