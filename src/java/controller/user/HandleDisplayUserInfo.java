/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import util.RouterJSP;

/**
 *
 * @author FPTSHOP
 */
@WebServlet("/handleDisplayUserInfo")
public class HandleDisplayUserInfo extends HttpServlet {

    RouterJSP router = new RouterJSP();
    DAO.UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userDAO = new DAO.UserDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // tao Servlet Context : 
        ServletContext context = getServletContext();
        HttpSession session = request.getSession();
        // lay userID tu session : 
        String username = (String)session.getAttribute("username");
//        String username = "123";//TEMP TESTING DATA, REMOVE LATER - DuyND

        User user = new User();
        try {
            user = userDAO.getUserByUsername(username);
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
