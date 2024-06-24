/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.CinemaChainDAO;
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
import model.CinemaChain;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "CreateCinemaChainServlet", urlPatterns = {"/owner/createCinemaChain"})
public class CreateCinemaChainServlet extends HttpServlet {

    private RouterJSP router = new RouterJSP();
    private CinemaChainDAO cinemaChainDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.cinemaChainDAO = new CinemaChainDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CreateCinemaChainServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet CreateCinemaChainServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateCinemaChainServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByUserId(userID);

        if (cinemaChain != null) {
            request.setAttribute("error", "You have already created a CinemaChain.");
            request.getRequestDispatcher(router.HOME_OWNER).forward(request, response);
        } else {
            request.getRequestDispatcher(router.CREATE_CINEMACHAIN).forward(request, response);
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
        
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        String name = request.getParameter("name");
        String information = request.getParameter("information");
        String avatar = request.getParameter("avatar");

        CinemaChain existingChain = cinemaChainDAO.getCinemaChainByUserId(userID);

        if (existingChain == null) {
            CinemaChain cinemaChain = new CinemaChain();
            cinemaChain.setName(name);
            cinemaChain.setInformation(information);
            cinemaChain.setAvatar(avatar);
            cinemaChainDAO.createCinemaChain(cinemaChain, userID);

            response.sendRedirect(RouterURL.OWNER_PAGE);
        } else {
            request.setAttribute("error", "You already have a CinemaChain.");
            request.getRequestDispatcher(router.CREATE_CINEMACHAIN).forward(request, response);
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
