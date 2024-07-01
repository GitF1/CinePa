package controller.user;

import DAO.RegisterOwnerDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ChangeRegisterStatusServlet", urlPatterns = {"/ChangeRegisterStatusServlet"})
public class ChangeRegisterStatusServlet extends HttpServlet {

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

        // lay id va type : 
        String id = request.getParameter("id");
        String status = request.getParameter("type");

        if (status.equalsIgnoreCase("Reject")) {
            registerOwnerDAO.changeRegisterStatus(id, status);
        }

        if (status.equalsIgnoreCase("Accept")) {
            registerOwnerDAO.changeRegisterStatus(id, status);
            // chinh role thanh Owner : 
            registerOwnerDAO.changeRoleToOwner(id);
        }
        
        // chuyen ve servler GetWaitingServlet : 
          request.getRequestDispatcher("/GetWaitingServlet").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
