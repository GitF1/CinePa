package controller.admin;

import DAO.AdminDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

@WebServlet(name = "ManageBanUserServlet", urlPatterns = {"/ManageBanUserServlet"})
public class ManageBanUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        get type : 
        String type = request.getParameter("type");

        // tao lop AdminDAO ; 
        ServletContext context = getServletContext();
        AdminDAO adminDAO = null;
        try {
            adminDAO = new AdminDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(ManageBanUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //        lay list BAN :
        ArrayList<User> listBan = null;
        try {
            listBan = adminDAO.getBanAndUnbanUser(type, "1");
        } catch (SQLException ex) {
            Logger.getLogger(ManageBanUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //        lay list UNBAN :
        ArrayList<User> listUnBan = null;
        try {
            listUnBan = adminDAO.getBanAndUnbanUser(type, "0");
        } catch (SQLException ex) {
            Logger.getLogger(ManageBanUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

//        setAttribute : 
        request.setAttribute("type", type);
        request.setAttribute("listUnBan", listUnBan);
        request.setAttribute("listBan", listBan);
//  chuyen sang cho DisplayBanUser.jsp : 
        request.getRequestDispatcher("page/admin/DisplayBanUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
