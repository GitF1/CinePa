package controller.auth;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author FPTSHOP
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    RouterJSP route = new RouterJSP();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // tao hoac lay session co san : 
        HttpSession session = request.getSession();

        session.invalidate();
        // quay ve trang chu : 
        response.sendRedirect(RouterURL.HOMEPAGE);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
