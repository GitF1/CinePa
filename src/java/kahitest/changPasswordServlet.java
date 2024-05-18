/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package kahitest;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author FPTSHOP
 */
public class changPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // lay du lieu tu Request : 
        String currentPass = request.getParameter("current-password");
        String newPass = request.getParameter("new-password");

        // lay userid tu session : 
        String id = "3";

        // tao Servlet Context : 
        ServletContext context = getServletContext();

        User user = null;
        try {

            user = UserDAO.getInstance().getUserById(id, context);
        } catch (Exception ex) {
            Logger.getLogger(changPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // check pass co dung khong , neu dung thi update password :
        if (user.getPassword().equalsIgnoreCase(currentPass)) {
            UserDAO.getInstance().updateUserPassword(id, newPass, context);
        }

        // chuyen qua cho thang display  ra thong tin user : 
        request.getRequestDispatcher("handleDisplayUserInfo").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
