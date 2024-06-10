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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Room;
import util.RouterJSP;

@WebServlet(name = "CreateRoomServlet", urlPatterns = {"/roomAdmin/createRoom"})
public class CreateRoomServlet extends HttpServlet {

    RouterJSP router = new RouterJSP();

    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.roomDAO = new RoomDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CreateRoomServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet CreateRoomServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateRoomServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(router.Add_Room).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Handling the form submission to create a new room
        int cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        int length = Integer.parseInt(request.getParameter("length"));
        int width = Integer.parseInt(request.getParameter("width"));
        String status = request.getParameter("status");

        Room room = new Room();
        room.setCinemaID(cinemaID);
        room.setName(name);
        room.setType(type);
        room.setCapacity(capacity);
        room.setLength(length);
        room.setWidth(width);
        room.setStatus(status);

        roomDAO.createRoom(room);

        response.sendRedirect(request.getContextPath() + "/roomAdmin/rooms?cinemaID=" + cinemaID);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
