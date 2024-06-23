package controller.admin;

import DAO.admin.AdminDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "BanUserServlet", urlPatterns = {"/BanUserServlet"})
public class BanUserServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        lay du lieu : 
        String type = request.getParameter("type");
        String userID = request.getParameter("userID");
        String isBanned = request.getParameter("isBanned");
        // tao lop AdminDAO ; 
        ServletContext context = getServletContext();
        AdminDAO adminDAO = null;
        try {
            adminDAO = new AdminDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(ManageBanUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //        Ban or UnBan user :
            adminDAO.banUser(userID, isBanned);
        } catch (SQLException ex) {
            Logger.getLogger(BanUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
//        set Attribute : 
        request.setAttribute(type, type);
        //  chuyen sang cho DisplayBanUser.jsp : 
        request.getRequestDispatcher("/ManageBanUserServlet").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
}
