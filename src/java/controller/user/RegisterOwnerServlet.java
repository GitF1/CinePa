package controller.user;

import DAO.RegisterOwnerDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.RouterJSP;

@WebServlet("/RegisterOwnerServlet")

public class RegisterOwnerServlet extends HttpServlet {

    RegisterOwnerDAO registerOwnerDAO;
    RouterJSP route = new RouterJSP();

    @Override
    public void init() throws jakarta.servlet.ServletException {
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
        int id = (int) session.getAttribute("userID");

        // them trang thai Waiting : 
        registerOwnerDAO.addRegisterOwner(id);

        // chuyen den trang thong bao la da yeu cau lam Owner thanh cong : 
        request.getRequestDispatcher("/page/user/WaitingRegisterOwner.jsp").forward(request, response);

        // quay ve trang chu : 
//        request.getRequestDispatcher(route.USER).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
