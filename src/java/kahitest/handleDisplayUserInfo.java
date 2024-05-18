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
public class handleDisplayUserInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // tao Servlet Context : 
        ServletContext context = getServletContext();

        // lay userID tu session : 
        String id = "3";
        User user = null;
        try {
            user = UserDAO.getInstance().getUserById(id, context);
        } catch (Exception ex) {
            Logger.getLogger(handleDisplayUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        // them doi tuong user vao request : 
        request.setAttribute("user", user);
// chuyen sang display ra bang thong tin user : 
        request.getRequestDispatcher("displayUserInfo.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
