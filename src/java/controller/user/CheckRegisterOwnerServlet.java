
package controller.user;

import DAO.RegisterOwnerDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "CheckRegisterOwnerServlet", urlPatterns = {"/CheckRegisterOwnerServlet"})
public class CheckRegisterOwnerServlet extends HttpServlet {

    RegisterOwnerDAO registerOwnerDAO;

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

        // lay Status theo id : 
        String status = registerOwnerDAO.checkRegisterOwner(String.valueOf(id));

        // kiem tra Status la : null , Waiting , Reject : 
        if (status == null) {
            request.getRequestDispatcher("/page/user/RegisterOwner.jsp").forward(request, response);
        }
        // neu la Waiting : 
        if (status.equalsIgnoreCase("Waiting")) {
            // chuyen den trang thong bao la da yeu cau lam Owner thanh cong : 
            request.getRequestDispatcher("/page/user/WaitingRegisterOwner.jsp").forward(request, response);
        }
        // neu la Reject : 
        if(status.equalsIgnoreCase("Reject")){
            // chuyen den trang Reject : 
                        request.getRequestDispatcher("/page/user/RejectRegisterOwner.jsp").forward(request, response);

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
