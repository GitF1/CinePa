/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import controller.auth.VerifyCodeServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.UserServiceInteface;
import util.RouterJSP;
import model.User;
import service.SendEmail;
import service.UserServiceImpl;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    UserServiceInteface userService;
    RouterJSP route = new RouterJSP();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Initialize the UserService instance
            this.userService = new UserServiceImpl(getServletContext());;
        } catch (Exception ex) {
            Logger.getLogger(VerifyCodeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = request.getRequestURL().toString();

        getRegister(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String url = request.getRequestURL().toString();

            if (url.contains("register")) {
                postRegister(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void getRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(route.REGISTER).forward(req, resp);
    }

    protected void postRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");

        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        String alertMsg = " ";

        // IF password doesn't match pattern
        if (!password.matches(passwordPattern)) {
            request.setAttribute("error", "Mật khẩu phải chứa ít nhất một số và một kí tự chữ cái, và có ít nhất 8 kí tự.");
            request.getRequestDispatcher(route.REGISTER).forward(request, response);
            return;
        }
        // If  confirm password doesn't match password
        if (!password.equals(confirmPassword)) {
            alertMsg = "Mật khẩu và xác nhận mật khẩu không khớp!";
            request.setAttribute("error", alertMsg);
            request.getRequestDispatcher(route.REGISTER).forward(request, response);
            return;
        }
        // chek exist email 
        if (userService.checkExistEmail(email)) {
            alertMsg = "Email đã tồn tại!";
            request.setAttribute("error", alertMsg);
            request.getRequestDispatcher(route.REGISTER).forward(request, response);
        }

        // check exsit username
        if (userService.checkExistUsername(username)) {
            alertMsg = "Tài khoản đã tồn tại!";
            request.setAttribute("error", alertMsg);
            request.getRequestDispatcher(route.REGISTER).forward(request, response);
        }

        //--------------------------------------------------------------------//
        // Send Mail Handler 
        SendEmail sm = new SendEmail();
        String code = sm.getRanDom();
        User user = new User(username, email, fullName, code);
        boolean isSuccessSendMail = sm.sendEmail(user);

        // If Send Mail Failed 
        if (!isSuccessSendMail) {
            alertMsg = "Lỗi khi gửi mail!";
            request.setAttribute("error", alertMsg);
            request.getRequestDispatcher(route.REGISTER).forward(request, response);
        }

        // Send Mail Sucessfully
        HttpSession session = request.getSession();
        session.setAttribute("account", user);
        boolean isSuccess = userService.register(fullName, username, email, password, code);

        // handle  verify email code
        if (isSuccess) {
            response.sendRedirect(request.getContextPath() + "/verifycode");
        } else {
            alertMsg = "Lỗi hệ thống!";
            request.setAttribute("error", alertMsg);
            request.getRequestDispatcher(route.REGISTER).forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
