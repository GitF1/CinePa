/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.owner;

import DAO.CinemaChainDAO;
import DAO.RoomDAO;
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

/**
 *
 * @author VINHNQ
 */
@WebServlet(name="UpdateCinemaChainServlet", urlPatterns={"/owner/updateCinemaChain"})
public class UpdateCinemaChainServlet extends HttpServlet {
   
    private RouterJSP router = new RouterJSP();
    private CinemaChainDAO cinemaChainDAO;
    
      @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.cinemaChainDAO = new CinemaChainDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateCinemaChainServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet UpdateCinemaChainServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCinemaChainServlet at " + request.getContextPath () + "</h1>");
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

        if (cinemaChain == null) {
            request.setAttribute("error", "No Cinema Chain found.");
            response.sendRedirect(request.getContextPath() + "/owner/homeOwner");
        } else {
            request.setAttribute("cinemaChain", cinemaChain);
            request.getRequestDispatcher(router.UPDATE_CINEMACHAIN).forward(request, response);
        }
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String name = request.getParameter("name");
        String information = request.getParameter("information");
        String avatar = request.getParameter("avatar");

        CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByUserId(userID);

        if (cinemaChain != null) {
            cinemaChain.setName(name);
            cinemaChain.setInformation(information);
            cinemaChain.setAvatar(avatar);

            cinemaChainDAO.updateCinemaChain(cinemaChain);

            response.sendRedirect(request.getContextPath() + "/owner/cinemaChain");
        } else {
            request.setAttribute("error", "No Cinema Chain found.");
            request.getRequestDispatcher(router.UPDATE_CINEMACHAIN).forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
