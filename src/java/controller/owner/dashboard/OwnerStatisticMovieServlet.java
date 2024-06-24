/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner.dashboard;

import DAO.owner.StatisticOwnerDAO;
import DAO.owner.StatisticOwnerMovieDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
import model.Movie;
import model.owner.dashboard.ChartModel;
import util.ChartUtil;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author PC
 */
@WebServlet(name = "OwnerStatisticMovieServlet", urlPatterns = {"/owner/dashboard/statistic/movies"})
public class OwnerStatisticMovieServlet extends HttpServlet {

    StatisticOwnerMovieDAO daoMovie;
    StatisticOwnerDAO dao;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {

            dao = new StatisticOwnerDAO(getServletContext());
            daoMovie = new StatisticOwnerMovieDAO(getServletContext());

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
            /* TODO output your page here. You may use following sample code. */

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

            if (userID == null || role == null || role != util.Role.OWNER) {
                //response.sendRedirect(RouterURL.LOGIN);
                userID = 10;
            }
            Integer cinemaChainID = dao.getCinemaChainOfUser(userID);
            if (cinemaChainID == null) {
                //response.sendRedirect(RouterURL.ERORPAGE);
                cinemaChainID = 1;
            }
            session.setAttribute("cinemaChainID", cinemaChainID);

            String movieIDParam = request.getParameter("movieID");
            String movieTitle = request.getParameter("movieTitle");

            String monthParam = request.getParameter("month");
            String yearParam = request.getParameter("year");

            System.out.println("movieID" + movieIDParam + "month: " + monthParam + "year:" + yearParam);
            
            
            List<Movie> movies = daoMovie.getListMoviebyCinemaChainID(cinemaChainID);
            //
            request.setAttribute("movies", movies);
            Integer movieID = null;
            Integer month = null;
            Integer year = null;
            try {
                if (movieIDParam != null) {
                    movieID = Integer.valueOf(movieIDParam);
                }
                if (monthParam != null) {
                    month = Integer.valueOf(monthParam);
                }
                if (yearParam != null) {
                    year = Integer.valueOf(yearParam);
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                response.sendRedirect(RouterURL.ERORPAGE);

            }

            ChartModel chartRevenue = daoMovie.getRevenueByDateInMonthOfMovie(cinemaChainID, movieID, month, year);
            ChartModel chartOrderTicket = daoMovie.getOrderTicketByDateInMonthOfMovie(cinemaChainID, movieID, month, year);
            //
            chartRevenue.setLable("Revenue");
            chartRevenue.setType("bar");
            chartOrderTicket.setLable("Ticket Sale");
            chartOrderTicket.setType("line");
            //
            List<ChartModel> listCombined = new ArrayList(2);
            //
            listCombined.add(chartRevenue);
            listCombined.add(chartOrderTicket);
            //
            List<Integer> labels = ChartUtil.getListDateInMonth(month, year);
            //

            request.setAttribute("listCombineChart", listCombined);
            request.setAttribute("labels", labels);
            request.setAttribute("movieTitle", movieTitle == null ? "Tất cả" : movieTitle);
            //

            //
            request.getRequestDispatcher(RouterJSP.OWNER_STATISTIC_MOVIE_PAGE).forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(OwnerStatisticMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect(RouterURL.ERORPAGE);

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
