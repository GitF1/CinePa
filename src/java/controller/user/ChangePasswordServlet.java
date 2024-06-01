/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import DAO.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import java.sql.SQLException;

/**
 *
 * @author FPTSHOP
 */
@WebServlet("/changePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userDAO = new UserDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // lay du lieu tu Request : 
        String currentPass = request.getParameter("current-password");
        String newPass = request.getParameter("new-password");

        // lay userid tu session : 
        String id = "3";



        User user = null;
        try {

            user = userDAO.getUserById(id);
        } catch (Exception ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // check pass co dung khong , neu dung thi update password :
        if (user.getPassword().equalsIgnoreCase(currentPass)) {
            try {
                userDAO.updateUserPassword(id, newPass);
            } catch (SQLException ex) {
                Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
