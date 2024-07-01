package controller.owner.dashboard;

import DAO.owner.StatisticOwnerCityDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.owner.dashboard.ChartModel;

@WebServlet(name = "GetDataCityServlet", urlPatterns = {"/GetDataCityServlet"})
public class GetDataCityServlet extends HttpServlet {

    StatisticOwnerCityDAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dao = new StatisticOwnerCityDAO(getServletContext());
        } catch (Exception ex) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String defaultCity = request.getParameter("city");
        String defaultMonth = request.getParameter("month");
        String defaultYear = request.getParameter("year");
        String listCityString = request.getParameter("listCity");
        ArrayList<String> listCity = new ArrayList<>(Arrays.asList(listCityString.split(",")));

        request.setAttribute("listCity", listCity);

        request.setAttribute("defaultCity", defaultCity);
        request.setAttribute("defaultMonth", defaultMonth);
        request.setAttribute("defaultYear", defaultYear);

        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        // neu khong co thi mac dinh userid = 1 ; 
        if (userID == null) {
            System.out.println("LOI USER ID LA NULL!!!!");
        }

        ChartModel chartModel = new ChartModel();
        chartModel = dao.getChartModelCity(defaultCity, defaultMonth, defaultYear, userID + "");  // Initialize labels with months from 1 to 12
        request.setAttribute("chartModel", chartModel);
        System.out.println("ádfasdfas");

        // chuyen sang cho CityChart.jsp : 
        request.getRequestDispatcher("/page/owner/dashboard/city/CityChart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
