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

@WebServlet(name = "SearchBanServlet", urlPatterns = {"/SearchBanServlet"})
public class SearchBanServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //        get type : 
        String type = request.getParameter("type");
        // get str : 
        String str = request.getParameter("str");
        // tao lop AdminDAO ; 
        ServletContext context = getServletContext();
        AdminDAO adminDAO = null;
        try {
            adminDAO = new AdminDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(ManageBanUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // lay list chua 'str' : 
        ArrayList<User> list = null;

        try {
            list = adminDAO.searchBanUsers(type, str);
        } catch (SQLException ex) {
            Logger.getLogger(SearchBanServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // set attribute : 
        request.setAttribute("list", list);
        request.setAttribute("str", str);
        request.setAttribute("type", type);
        // chuyen sang cho DisplaySearchBan.jsp : 
        request.getRequestDispatcher("/page/admin/DisplaySearchBan.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
