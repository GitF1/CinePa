/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.SendEmail;
import model.User;
import service.UserServiceInteface;
import service.UserServiceImpl;
import util.Router;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "HomeRegister", urlPatterns = {"/register", "/verifycode"})
public class RegisterServlet extends HttpServlet {

    UserServiceInteface userService;
    Router route = new Router();

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

        if (url.contains("register")) {
            getRegister(request, response);
        } else if (url.contains("verifycode")) {
            request.getRequestDispatcher(route.VERIFY).forward(request, response);
        } else {
            homePage(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String url = request.getRequestURL().toString();
            userService = new UserServiceImpl(getServletContext());

            if (url.contains("register")) {
                postRegister(request, response);
            } else if (url.contains("verifycode")) {
                postVerifyCode(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void homePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(route.HOMEPAGE).forward(req, resp);
    }

    protected void getRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(route.REGISTER).forward(req, resp);
    }

    protected void postRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        // Lấy tham số từ form
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String fullName = req.getParameter("fullName");

        String alertMsg = " ";

        if (userService.checkExistEmail(email)) {
            alertMsg = "Email đã tồn tại!";
            req.setAttribute("error", alertMsg);
            req.getRequestDispatcher(route.REGISTER).forward(req, resp);
        } else if (userService.checkExistUsername(username)) {
            alertMsg = "Tài khoản đã tồn tại!";
            req.setAttribute("error", alertMsg);
            req.getRequestDispatcher(route.REGISTER).forward(req, resp);
        } else {
            SendEmail sm = new SendEmail();
            // Lấy mã 6 chữ số
            String code = sm.getRanDom();
            // Tạo người dùng mới
            User user = new User(username, email, fullName, code);
            // Gửi email
            boolean success = sm.sendEmail(user);
            if (success) {
                HttpSession session = req.getSession();
                session.setAttribute("account", user);
                // Thực hiện đăng ký
                String passwordHash = userService.hashPassword(password);
                boolean isSuccess = userService.register(username, passwordHash, email, fullName, code);
                
                if (isSuccess) {
                    resp.sendRedirect(req.getContextPath() + "/verifycode");
                } else {
                    alertMsg = "Lỗi hệ thống!";
                    req.setAttribute("error", alertMsg);
                    req.getRequestDispatcher(route.REGISTER).forward(req, resp);
                }
            } else {
                PrintWriter out = resp.getWriter();
                out.println("Lỗi khi gửi mail!!!!!!!!");
            }
        }
    }

    protected void postVerifyCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("account");
            String code = req.getParameter("authcode");
            if (code.equals(user.getCode())) {
                user.setEmail(user.getEmail());
                user.setStatus(1);
                userService.updatestatus(user);
                out.println("<div class=\"container\"><br/>"
                        + " <br/>"
                        + "Kích hoạt tài khoản thành công! <br/>"
                        + " <br/></div>");
                
                
                //  resp.sendRedirect(req.getContextPath() + "/verifycode");
                
            } else {
                
                String alertMsg = "Code Doesn't Match!";
                req.setAttribute("error", alertMsg);
                req.getRequestDispatcher(route.VERIFY).forward(req, resp);
             
            }
        }
    }

//    protected void getLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Check session
//        HttpSession session = req.getSession(false);
//        if (session != null && session.getAttribute("account") != null) {
//            resp.sendRedirect(req.getContextPath() + "/waiting");
//            return;
//        }
//
//        // Check cookie
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("username")) {
//                    session = req.getSession(true);
//                    session.setAttribute("username", cookie.getValue());
//                    resp.sendRedirect(req.getContextPath() + "/waiting");
//                    return;
//                }
//            }
//        }
//
//        // Forward to login page if no session or cookie is found
//        req.getRequestDispatcher(route.LOGIN).forward(req, resp);
//    }
//
//    //
//    protected void postLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        resp.setCharacterEncoding("UTF-8");
//        req.setCharacterEncoding("UTF-8");
//
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        boolean isRememberMe = false;
//        String remember = req.getParameter("remember");
//
//        if ("on".equals(remember)) {
//            isRememberMe = true;
//        }
//
//        String alertMsg = "";
//        if (username.isEmpty() || password.isEmpty()) {
//            alertMsg = "Tài khoản hoặc mật khẩu không đúng";
//            req.setAttribute("error", alertMsg);
//            req.getRequestDispatcher(route.LOGIN).forward(req, resp);
//            return;
//        }
//
//        User user = userService.login(username, password);
//
//        if (user != null) {
//            if (user.getStatus() == 1) {
//                // Tạo session
//                HttpSession session = req.getSession(true);
//                session.setAttribute("account", user);
//
//                if (isRememberMe) {
//                    saveRememberMe(resp, username);
//                }
//
//                resp.sendRedirect(req.getContextPath() + "/waiting");
//            } else {
//                alertMsg = "Tài khoản đã bị khóa, liên hệ Admin nhé";
//                req.setAttribute("message", alertMsg);
//                req.getRequestDispatcher(route.LOGIN).forward(req, resp);
//            }
//        } else {
//            alertMsg = "Tài khoản hoặc mật khẩu không đúng";
//            req.setAttribute("error", alertMsg);
//            req.getRequestDispatcher(route.LOGIN).forward(req, resp);
//        }
//    }
//
//    protected void getWaiting(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        if (session != null && session.getAttribute("account") != null) {
//            User user = (User) session.getAttribute("account");
//            req.setAttribute("username", user.getUsername());
//
//            String roleId = user.getRole();
//            if ("1".equals(roleId)) {
//                resp.sendRedirect(req.getContextPath() + "/home");
//            } else if ("2".equals(roleId)) {
//                resp.sendRedirect(req.getContextPath() + "/manager/home");
//            } else if ("3".equals(roleId)) {
//                resp.sendRedirect(req.getContextPath() + "/seller/home");
//            } else {
//                resp.sendRedirect(req.getContextPath() + "/home");
//            }
//        } else {
//            resp.sendRedirect(req.getContextPath() + "/login");
//        }
//    }
    private void saveRememberMe(HttpServletResponse resp, String username) {
        Cookie usernameCookie = new Cookie("username", username);
        usernameCookie.setMaxAge(24 * 60 * 60 * 60); // 1 ngày
        resp.addCookie(usernameCookie);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
