package controller.owner.dashboard;

import DAO.owner.StatisticOwnerCityDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.owner.dashboard.ChartModel;

@WebServlet(name = "OwnerDashboardCityServlet", urlPatterns = {"/OwnerDashboardCityServlet"})
public class OwnerDashboardCityServlet extends HttpServlet {

    StatisticOwnerCityDAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dao = new StatisticOwnerCityDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(OwnerDashBoardServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // lay list cac city theo userID : 
        ArrayList<String> listCity = new ArrayList<>();
        // lay userID : 
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        // neu khong co thi mac dinh userid = 1 ; 
        if (userID == null) {
            System.out.println("LOI USER ID LA NULL!!!!");
        }

        listCity = dao.getProvinceByUserId(userID);
        System.out.println("list city " + listCity);
        request.setAttribute("listCity", listCity);

        // default thang , nam : 
        String defaultMonth = "6";
        String defaultYear = "2024";
        request.setAttribute("defaultYear", defaultYear);
        request.setAttribute("defaultMonth", defaultMonth);

        // tao ChartMOdel : 
        ChartModel chartModel = new ChartModel();
        System.out.println("ch" + chartModel);
        chartModel = dao.getChartModelCity(listCity.get(0), defaultMonth, defaultYear, userID + "");  // Initialize labels with months from 1 to 12
        System.out.println("chart lables : " + chartModel.getLables());
        System.out.println("chart data : " + chartModel.getData());

        request.setAttribute("chartModel", chartModel);

        // chuyen sang cho CityChart.jsp : 
        request.getRequestDispatcher("/page/owner/dashboard/city/CityChart.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
