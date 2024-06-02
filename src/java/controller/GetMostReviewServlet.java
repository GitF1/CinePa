package controller;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MostReview;

@WebServlet(name = "GetMostReviewServlet", urlPatterns = {"/GetMostReviewServlet"})
public class GetMostReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
    // tao Servlet Context : 
        ServletContext context = getServletContext();
        
        
    //        tao list : 
        ArrayList<MostReview> listMostReview = new ArrayList<>();

        try {
            // lay list tu database :
            listMostReview = DAO.MostReviewDAO.getInstance().getMostReview(context)  ;
        } catch (Exception ex) {
            Logger.getLogger(GetMostReviewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // them list vao request : 
        request.setAttribute("listMostReview", listMostReview);
        
    // chuyen sang cho mostReview.jsp : 
        request.getRequestDispatcher("page/landingPage/mostReview.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
