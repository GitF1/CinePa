/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.SendEmail;
import service.UserService;
import service.UserServiceImpl;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        if (password.matches(passwordPattern)) {
            if (!password.equals(confirmPassword)) {
                alertMsg = "Mật khẩu và xác nhận mật khẩu không khớp!";
                request.setAttribute("error", alertMsg);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            if (userService.checkExistEmail(email)) {
                alertMsg = "Email đã tồn tại!";
                request.setAttribute("error", alertMsg);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else if (userService.checkExistUsername(username)) {
                alertMsg = "Tài khoản đã tồn tại!";
                request.setAttribute("error", alertMsg);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                SendEmail sm = new SendEmail();
                String code = sm.getRanDom();
                User user = new User(fullName, username, email, code);
                boolean test = sm.sendEmail(user);
                if (test) {
                    HttpSession session = request.getSession();
                    session.setAttribute("account", user);
                    boolean isSuccess = userService.register(fullName, username, email, password, code);
                    if (isSuccess) {
                        response.sendRedirect(request.getContextPath() + "/VerifyCode");
                    } else {
                        alertMsg = "Lỗi hệ thống!";
                        request.setAttribute("error", alertMsg);
                        request.getRequestDispatcher("register.jsp").forward(request, response);
                    }
                } else {
                    alertMsg = "Lỗi khi gửi mail!";
                    request.setAttribute("error", alertMsg);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            }
        } else {
            request.setAttribute("error", "Mật khẩu phải chứa ít nhất một số và một kí tự chữ cái, và có ít nhất 8 kí tự.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
