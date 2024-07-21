/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

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
import model.Room;
import util.RouterJSP;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "UpdateRoomServlet", urlPatterns = {"/owner/updateRoom"})
public class UpdateRoomServlet extends HttpServlet {

    RouterJSP router = new RouterJSP();

    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.roomDAO = new RoomDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateRoomServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet UpdateRoomServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRoomServlet at " + request.getContextPath() + "</h1>");
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
        int roomID = Integer.parseInt(request.getParameter("roomID"));
        Room room = roomDAO.getRoomById(roomID);
        request.setAttribute("room", room);
        request.getRequestDispatcher(router.UPDATE_ROOM).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roomID = Integer.parseInt(request.getParameter("roomID")); // Extract roomID from request
        Room room = roomDAO.getRoomById(roomID); // Get room information from database

        // Update fields of Room object
        room.setName(request.getParameter("name"));
        room.setType(request.getParameter("type"));
//        room.setCapacity(Integer.parseInt(request.getParameter("capacity")));
//        room.setLength(Integer.parseInt(request.getParameter("length")));
//        room.setWidth(Integer.parseInt(request.getParameter("width")));
        room.setStatus(request.getParameter("status"));

        // Call updateRoom method of RoomDAO with the updated Room object
        roomDAO.updateRoom(room);
        response.sendRedirect("rooms?cinemaID=" + request.getParameter("cinemaID"));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
