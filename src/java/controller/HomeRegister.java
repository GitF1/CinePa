///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import model.User;
//import service.SendEmail;
//import service.UserService;
//import service.UserServiceImpl;
//
///**
// *
// * @author VINHNQ
// */
//@WebServlet(name = "HomeRegister", urlPatterns = {"/homeRegister", "/register", "/VerifyCode"})
//public class HomeRegister extends HttpServlet {
//
//    UserService userService = new UserServiceImpl();
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet HomeRegister</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet HomeRegister at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String url = request.getRequestURL().toString();
//
//        if (url.contains("register")) {
//            getRegister(request, response);
//        } else if (url.contains("login")) {
//           // getLogin(request, response);
//        } else if (url.contains("forgotpass")) {
//            request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
//        } else if (url.contains("waiting")) {
//           // getWaiting(request, response);
//        } else if (url.contains("VerifyCode")) {
//            request.getRequestDispatcher("verify.jsp").forward(request, response);
//        } else {
//            homePage(request, response);
//        }
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String url = request.getRequestURL().toString();
//
//        if (url.contains("register")) {
//            postRegister(request, response);
//        } else if (url.contains("login")) {
//           // postLogin(request, response);
//        } else if (url.contains("forgotpass")) {
//            //   postForgotPassWord(request, response);
//        } else if (url.contains("VerifyCode")) {
//            postVerifyCode(request, response);
//        }
//    }
//    
//    protected void homePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("home.jsp").forward(req, resp);
//    }
//
//    protected void getRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("register.jsp").forward(req, resp);
//    }
//
//    protected void postRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        resp.setCharacterEncoding("UTF-8");
//        req.setCharacterEncoding("UTF-8");
//
//        // Lấy tham số từ form
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        String email = req.getParameter("email");
//        String fullName = req.getParameter("fullName");
//
//        String alertMsg = " ";
//
//        if (userService.checkExistEmail(email)) {
//            alertMsg = "Email đã tồn tại!";
//            req.setAttribute("error", alertMsg);
//            req.getRequestDispatcher("register.jsp").forward(req, resp);
//        } else if (userService.checkExistUsername(username)) {
//            alertMsg = "Tài khoản đã tồn tại!";
//            req.setAttribute("error", alertMsg);
//            req.getRequestDispatcher("register.jsp").forward(req, resp);
//        } else {
//            SendEmail sm = new SendEmail();
//            // Lấy mã 6 chữ số
//            String code = sm.getRanDom();
//            // Tạo người dùng mới
//            User user = new User(username, email, fullName, code);
//            // Gửi email
//            boolean test = sm.sendEmail(user);
//            if (test) {
//                HttpSession session = req.getSession();
//                session.setAttribute("account", user);
//                // Thực hiện đăng ký
//                boolean isSuccess = userService.register(username, password, email, fullName, code);
//                if (isSuccess) {
//                    resp.sendRedirect(req.getContextPath() + "/VerifyCode");
//                } else {
//                    alertMsg = "Lỗi hệ thống!";
//                    req.setAttribute("error", alertMsg);
//                    req.getRequestDispatcher("register.jsp").forward(req, resp);
//                }
//            } else {
//                PrintWriter out = resp.getWriter();
//                out.println("Lỗi khi gửi mail!!!!!!!!");
//            }
//        }
//    }
//    
//    protected void postVerifyCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html; charset=UTF-8");
//        try (PrintWriter out = resp.getWriter()) {
//            HttpSession session = req.getSession();
//            User user = (User) session.getAttribute("account");
//            String code = req.getParameter("authcode");
//            if (code.equals(user.getCode())) {
//                user.setEmail(user.getEmail());
//                user.setStatus(1);
//                userService.updatestatus(user);
//                out.println("<div class=\"container\"><br/>"
//                        + " <br/>"
//                        + "Kích hoạt tài khoản thành công! <br/>"
//                        + " <br/></div>");
//            } else {
//                out.println("<div class=\"container\"><br/>"
//                        + " <br/>"
//                        + "Sai mã kích hoạt, vui lòng kiểm tra lại<br/>"
//                        + " <br/></div>");
//            }
//        }
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
