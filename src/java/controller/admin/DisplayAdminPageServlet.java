package controller.admin;

import DAO.admin.AdminDAO;
import DAO.GraphDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.graph.SalesData;
import util.RouterJSP;

@WebServlet(name = "DisplayAdminPageServlet", urlPatterns = {"/admin"})
public class DisplayAdminPageServlet extends HttpServlet {

    GraphDAO graphDAO;
    AdminDAO adminDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            graphDAO = new GraphDAO(getServletContext());
            adminDAO = new AdminDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(DisplayAdminPageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // tao lop AdminDAO ;
            ServletContext context = getServletContext();
            AdminDAO adminDAO = null;

            adminDAO = new AdminDAO(context);

            // lay tong so user :
            int tongUser = 0;

            tongUser = adminDAO.getUserCount();

            // lay tong bo phim :
            int tongPhim = 0;

            tongPhim = adminDAO.getFilmCount();

            // lay tong review :
            int tongReview = 0;

            tongReview = adminDAO.getReviewCount();
            double totalAmountTicket = adminDAO.getTotalAmountTicket();
            double totalAmountCanteen = adminDAO.getTotalCanteenOrderPrice();

            System.out.println("total amount ticket: :" + totalAmountTicket + "total amoutn canteen: " + totalAmountCanteen);
            // lay tong doanh thu :
            double doanhThu = totalAmountTicket + totalAmountCanteen;
            String formattedDoanhThu = String.format("%,.2f", doanhThu);
            
            int noPendingRequests = adminDAO.getNumberOfPendingRequests();

            // setAttribute :
            request.setAttribute("doanhThu", formattedDoanhThu);
            request.setAttribute("tongUser", tongUser);
            request.setAttribute("tongPhim", tongPhim);
            request.setAttribute("tongReview", tongReview);
            request.setAttribute("noPendingRequests", noPendingRequests);

            //        processRequest(request, response);
            graphDAO.getTotalSalesValueLast7Days();
            for (SalesData sd : graphDAO.getTotalSalesValueLast7Days()) {
                System.out.println(sd);
            }
            request.setAttribute("sales7Day", graphDAO.getTotalSalesValueLast7Days());

            //chuyen sang AdminPage.jsp :
            request.getRequestDispatcher(RouterJSP.ADMIN_PAGE).forward(request, response);

        } catch (Exception ex) {
            Logger.getLogger(DisplayAdminPageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
