package controller.user;

import DAO.RegisterOwnerDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.User;

@WebServlet(name = "GetWaitingServlet", urlPatterns = {"/GetWaitingServlet"})
public class GetWaitingServlet extends HttpServlet {

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

        // lay list Waiting : 
        ArrayList<User> list = (ArrayList<User>) registerOwnerDAO.listWaitingUsers();
        
        // set attribute : 
        request.setAttribute("list", list);

        // chuyen sang cho jsp : 
        request.getRequestDispatcher("/page/user/WaitingList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
