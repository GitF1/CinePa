/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner.dashboard;

import DAO.owner.StatisticOwnerCinemaDAO;
import DAO.owner.StatisticOwnerDAO;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.Cinema;
import model.owner.dashboard.ChartModel;
import util.ChartUtil;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author PC
 */
@WebServlet(name = "OwnerDashboardCinemaStatisticServlet", urlPatterns = {"/owner/dashboard/statistic/cinemas"})
public class OwnerDashboardCinemaStatisticServlet extends HttpServlet {

    StatisticOwnerDAO dao;
    StatisticOwnerCinemaDAO daoCinema;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {

            dao = new StatisticOwnerDAO(getServletContext());
            daoCinema = new StatisticOwnerCinemaDAO(getServletContext());

        } catch (Exception ex) {
            Logger.getLogger(OwnerDashBoardServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            Integer userID = (Integer) session.getAttribute("userID");
            String role = (String) session.getAttribute("role");
            List<Cinema> listCinema = (List<Cinema>) session.getAttribute("listCinemaDashBoard");
            System.out.println("userID" + userID + "role" + role);

//            if (userID == null || role == null || !role.equals(util.Role.OWNER)) {
//                response.sendRedirect(RouterURL.LOGIN);
//                return;
//            }
            Integer cinemaChainID = dao.getCinemaChainOfUser(userID);

            if (cinemaChainID == null) {
                response.sendRedirect(RouterURL.ERORPAGE);

            }
            session.setAttribute("cinemaChainID", cinemaChainID);

            String selectedCinemaIDs = request.getParameter("selectedCinemaIDs");

            List<Integer> cinemaIDList;
            if (selectedCinemaIDs == null || selectedCinemaIDs.isEmpty()) {
                // Initialize an empty list if selectedCinemaIDs is null or empty
                cinemaIDList = new ArrayList<>();
            } else {
                // Split the comma-separated string into an array
                String[] cinemaIDArray = selectedCinemaIDs.split(",");

                // Convert the array into a List of Integers
                cinemaIDList = Arrays.stream(cinemaIDArray)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }

            List<Cinema> cinemas = new ArrayList();

            if (listCinema == null || listCinema.isEmpty()) {
                cinemas = daoCinema.getListCinemaByCinemaChain(cinemaChainID, 20, 0);
                session.setAttribute("listCinemaDashBoard", cinemas);
            } else {
                cinemas = listCinema;
            }

            request.setAttribute("cinemas", cinemas);

            String monthParam = request.getParameter("month");
            String yearParam = request.getParameter("year");
            System.out.println("month" + monthParam + "  year:" + yearParam);

            Integer month = null;
            Integer year = null;

            if (monthParam != null && !monthParam.isEmpty()) {
                month = Integer.valueOf(monthParam);
            }
            if (yearParam != null && !yearParam.isEmpty()) {
                year = Integer.valueOf(yearParam);
            }

            List<ChartModel> charts = daoCinema.getListRevenueOfCinemasSelect(cinemaChainID, cinemaIDList, month, year);

            System.out.println("list data" + charts.toString());
            List<Integer> labels = ChartUtil.getListDateInMonth(month, year);

            request.setAttribute("month",  monthParam);
            request.setAttribute("year",  yearParam);
            request.setAttribute("labels", labels);
            request.setAttribute("cinemas", cinemas);
            request.setAttribute("listCombineChart", charts);
            request.setAttribute("listCinemaID", cinemaIDList);

            request.getRequestDispatcher(RouterJSP.OWNER_STATISTIC_CINEMA_PAGE).forward(request, response);

        } catch (Exception ex) {
            Logger.getLogger(OwnerDashboardCinemaStatisticServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
