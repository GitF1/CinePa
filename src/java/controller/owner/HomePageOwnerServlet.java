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
import util.RouterJSP;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CinemaChain;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "HomePageOwnerServlet", urlPatterns = {"/owner"})
public class HomePageOwnerServlet extends HttpServlet {

    private RouterJSP router = new RouterJSP();
    private CinemaChainDAO cinemaChainDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.cinemaChainDAO = new CinemaChainDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(HomePageOwnerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet HomePageOwnerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomePageOwnerServlet at " + request.getContextPath() + "</h1>");
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
        request.setAttribute("cinemaChain", cinemaChain);

        request.getRequestDispatcher(router.HOME_OWNER).forward(request, response);
      
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
