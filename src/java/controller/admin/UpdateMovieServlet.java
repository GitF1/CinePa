/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import com.cloudinary.Cloudinary;
import controller.user.UpdateUserInfo;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;
import model.movie.MovieInfo;
import util.RouterJSP;

/**
 *
 * @author duyqu
 */
@WebServlet(name = "UpdateMovieServlet", urlPatterns = {"/UpdateMovieServlet"})
public class UpdateMovieServlet extends HttpServlet {

    RouterJSP route = new RouterJSP();
    private Cloudinary cloudinary;
    DAO.UserDAO userDAO;
    DAO.MovieDAO movieDAO;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        String envFilePath = context.getRealPath("/WEB-INF/config/public/.env");
        Dotenv dotenv = Dotenv.configure().directory(envFilePath).load();

        cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));

        try {
            userDAO = new DAO.UserDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            movieDAO = new DAO.MovieDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateMovieServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateMovieServlet at " + request.getContextPath() + "</h1>");
            out.println(request.getParameter("movieID"));
            out.println("</body>");
            out.println("</html>");
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
        List<Movie> movieList = movieDAO.getAllMovieAvailable();
        request.setAttribute("movieList", movieList);
        response.setContentType("text/html;charset=UTF-8");

        request.getRequestDispatcher(route.UPDATE_MOVIE_LIST).forward(request, response);
//        processRequest(request, response);
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
//        processRequest(request, response);
        ServletContext context = request.getServletContext();
        try {
            
            MovieInfo mi = movieDAO.getMovieWithGenresByID(Integer.parseInt(request.getParameter("movieID")));
            request.setAttribute("mi", mi);
            Date date = mi.getDatePublished();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            String dateString = df.format(date);
            request.setAttribute("date", date);
            List<String> genresList= mi.getGenres();
            request.setAttribute("genresList", genresList);
           
            
            
        } catch (Exception ex) {
            Logger.getLogger(UpdateMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher(route.UPDATE_MOVIE).forward(request, response);
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
