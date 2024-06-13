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
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;

@WebServlet(name = "DisplayAdminPageServlet", urlPatterns = {"/admin"})
public class DisplayAdminPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

         // tao lop AdminDAO ; 
        ServletContext context = getServletContext();
        AdminDAO adminDAO = null;
        try {
            adminDAO = new AdminDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(ManageBanUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         // lay tong so user : 
         int tongUser = 0  ; 
        try { 
            tongUser = adminDAO.getUserCount() ;
        } catch (SQLException ex) {
            Logger.getLogger(DisplayAdminPageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         // lay tong bo phim : 
          int tongPhim = 0;
          
        try { 
            tongPhim = adminDAO.getFilmCount() ;
        } catch (SQLException ex) {
            Logger.getLogger(DisplayAdminPageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
           // lay tong review : 
        int tongReview = 0;
        try { 
            tongReview = adminDAO.getReviewCount() ;
        } catch (SQLException ex) {
            Logger.getLogger(DisplayAdminPageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        
        // lay tong doanh thu : 
        int doanhThu = 10;
        // lay tong so user : 
//        int tongUser = 20;
        // lay tong bo phim : 
//        int tongPhim = 30;
        // lay tong review : 
//        int tongReview = 40;

        // setAttribute : 
        request.setAttribute("doanhThu", doanhThu);
        request.setAttribute("tongUser", tongUser);
        request.setAttribute("tongPhim", tongPhim);
        request.setAttribute("tongReview", tongReview);

        //chuyen sang AdminPage.jsp : 
        request.getRequestDispatcher(RouterJSP.ADMIN_PAGE).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
