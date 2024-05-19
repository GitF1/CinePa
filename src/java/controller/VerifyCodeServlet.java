package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;
import service.UserServiceImpl;

@WebServlet(name = "VerifyCodeServlet", urlPatterns = {"/VerifyCode"})
public class VerifyCodeServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VerifyCodeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyCodeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Boolean timeExpired = (Boolean) session.getAttribute("timeExpired");
        if (timeExpired == null || !timeExpired) {
            session.setAttribute("startTime", Instant.now());
            session.setAttribute("attempts", 0);
            session.setAttribute("timeExpired", false);
        }
        request.getRequestDispatcher("verify.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            request.setAttribute("error", "Tài khoản đăng ký không thành công. Vui lòng đăng ký lại.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        String code = request.getParameter("authcode");

        Instant startTime = (Instant) session.getAttribute("startTime");
        if (startTime == null) {
            startTime = Instant.now();
            session.setAttribute("startTime", startTime);
        }

        Integer attempts = (Integer) session.getAttribute("attempts");
        if (attempts == null) {
            attempts = 0;
            session.setAttribute("attempts", attempts);
        }

        // Kiểm tra thời gian 30 giây
        Instant endTime = startTime.plusSeconds(60);
        if (Instant.now().isAfter(endTime)) {
            request.setAttribute("error", "Thời gian nhập mã kích hoạt đã hết.");
            user.setStatus(0);
            userService.updatestatus(user);
            session.setAttribute("timeExpired", true);
            session.invalidate(); // Hủy phiên để chắc chắn không có thời gian nào khác được tính toán
            request.getRequestDispatcher("verify.jsp").forward(request, response);
            return;
        } else {
            Duration remainingTime = Duration.between(Instant.now(), endTime);
            request.setAttribute("remainingTime", remainingTime.getSeconds());
        }

        // Kiểm tra mã kích hoạt
        if (code.equals(user.getCode())) {
            user.setStatus(1);
            userService.updatestatus(user);
            session.invalidate(); // Hủy phiên để chắc chắn không có thời gian nào khác được tính toán
            request.setAttribute("error", "Kích hoạt tài khoản thành công!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            if (attempts < 2) { // Chỉ tăng attempts nếu chưa đạt ngưỡng 3 lần
                attempts++;
                session.setAttribute("attempts", attempts);
            } else { // Nếu đã nhập sai 3 lần, khóa tài khoản
                user.setStatus(0);
                userService.updatestatus(user);
                request.setAttribute("error", "Bạn đã nhập sai quá 3 lần. Tài khoản của bạn đã bị khóa.");
                request.getRequestDispatcher("verify.jsp").forward(request, response);
                return;
            }
            request.setAttribute("error", "Sai mã kích hoạt, vui lòng kiểm tra lại");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
            return;
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
