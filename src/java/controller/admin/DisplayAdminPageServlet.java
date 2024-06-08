package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DisplayAdminPageServlet", urlPatterns = {"/DisplayAdminPageServlet"})
public class DisplayAdminPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // lay tong doanh thu : 
        int doanhThu = 109990909;
        // lay tong so user : 
        int tongUser = 230;
        // lay tong bo phim : 
        int tongPhim = 99;
        // lay tong review : 
        int tongReview = 22;

        // setAttribute : 
        request.setAttribute("doanhThu", doanhThu);
        request.setAttribute("tongUser", tongUser);
        request.setAttribute("tongPhim", tongPhim);
        request.setAttribute("tongReview", tongReview);

        //chuyen sang AdminPage.jsp : 
        request.getRequestDispatcher("page/admin/AdminPage.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
