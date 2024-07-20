/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.UserDAO;
import DAO.owner.OwnerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;
import util.RouterURL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
/**
 *
 * @author PC
 */
@WebServlet(name = "CreateSeatServlet", urlPatterns = {"/owner/room/seat/create"})
public class CreateSeatServlet extends HttpServlet {

    UserDAO userDAO;
    OwnerDAO ownerDAO;

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
    public void init()
            throws ServletException {
        try {
            userDAO = new UserDAO(getServletContext());
            ownerDAO = new OwnerDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CreateSeatServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.init();

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

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
        
        System.out.println("Go doGet of CRUD Seat Servlet");

        String roomID = request.getParameter("roomID");
        String referer = request.getHeader("Referer");
        String isExistingSeatsStr = request.getParameter("isExistingSeats");
        request.setAttribute("roomID", roomID);
        System.out.println("roomID = " + roomID);
        System.out.println("isExistingSeatsStr = " + isExistingSeatsStr);
        
        Boolean isExistingSeats = null;
        
        System.out.println("Previous page URL: " + referer);
        
        try {
            isExistingSeats = ownerDAO.isExistingSeats(Integer.parseInt(roomID));
            System.out.println(request.getRequestURL().toString());
            if(isExistingSeats) {
            if(isExistingSeatsStr != null && isExistingSeatsStr.equals("true")) {
                response.sendRedirect(referer);
                return;
            }
                System.out.println("CinemaID = " + request.getParameter("cinemaID"));
                String path = referer.substring(referer.indexOf("/owner"));
                request.setAttribute("createSeatsMessage", "Đã có ghế ngồi trong phòng, không thể tạo mới!");
//                System.out.println("path = " + path);
//                System.out.println("Test = " + getServletContext().getRealPath("/"));
//                request.getRequestDispatcher(path).forward(request, response);
                response.sendRedirect(referer+"&isExistingSeats=" + "true");
            }
            else request.getRequestDispatcher(RouterJSP.ROOM_CREAT_SEAT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CreateSeatServlet.class.getName()).log(Level.SEVERE, null, ex);
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

        String roomIDStr = request.getParameter("roomID");

        if (roomIDStr == null || request.getParameter("length") == null || request.getParameter("width") == null)  {
            response.sendRedirect(RouterURL.OWNER_PAGE);
        }
        
        int length = Integer.parseInt(request.getParameter("length"));
        int width = Integer.parseInt(request.getParameter("width"));
        int roomID = Integer.parseInt(roomIDStr);

        for (int x = 1; x <= width; ++x) {
            for (int y = 1; y <= length; ++y) {
                String seatName = request.getParameter("seat_" + x + "_" + y);
                if (seatName == null) {
                    continue;
                }
                try {
                    userDAO = new UserDAO(request.getServletContext());
                    userDAO.insertSeats(roomID, seatName, x, y);
                } catch (Exception ex) {
                    Logger.getLogger(CreateSeatServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        request.getRequestDispatcher("/owner").forward(request, response);
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
