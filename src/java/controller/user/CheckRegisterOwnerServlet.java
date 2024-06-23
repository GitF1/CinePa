/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import DAO.RegisterOwnerDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FPTSHOP
 */
@WebServlet(name = "CheckRegisterOwnerServlet", urlPatterns = {"/CheckRegisterOwnerServlet"})
public class CheckRegisterOwnerServlet extends HttpServlet {

    RegisterOwnerDAO registerOwnerDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            registerOwnerDAO = new RegisterOwnerDAO((ServletContext) getServletContext());
        } catch (Exception ex) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // lay userID tu session : 
        HttpSession session = (HttpSession) request.getSession();
        String id = (String) session.getAttribute("userID");

        // lay Status theo id : 
        String status = registerOwnerDAO.checkRegisterOwner(id);

        // kiem tra Status la : null , Waiting , Reject : 
        if (status == null) {
            response.sendRedirect(request.getContextPath() + "/RegisterOwnerServlet");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
