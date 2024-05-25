/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import controller.UserDAO;
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
import util.Router;

/**
 *
 * @author FPTSHOP
 */
public class HandleDisplayUserInfo extends HttpServlet {
    
    Router router = new Router();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // tao Servlet Context : 
        ServletContext context = getServletContext();

        // lay userID tu session : 
        String id = "4";
        
        User user = new User();
        try {
            user = UserDAO.getInstance().getUserById(id, context);
        } catch (Exception ex) {
            user.setAddress(ex.getMessage());
            Logger.getLogger(HandleDisplayUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        // them doi tuong user vao request : 
        request.setAttribute("user", user);
// chuyen sang display ra bang thong tin user : 
        request.getRequestDispatcher(router.DISPLAY_INFO).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
