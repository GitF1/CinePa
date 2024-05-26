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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MovieKhai;
import model.Review;

@WebServlet(name = "HandleDisplayMovieInfo", urlPatterns = {"/HandleDisplayMovieInfo"})
public class HandleDisplayMovieInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // nhan movieID tu Quang Vinh guii qua : 
        String movieID = request.getParameter("movieID");
        if (movieID == null) {
            movieID = "4";
        }

        // tao Servlet Context : 
        ServletContext context = getServletContext();

        //lay Movie : 
        MovieKhai movie = new MovieKhai();
        try {
            movie = DAO.MovieDAO.getInstance().getMovieWithGenresByID(Integer.parseInt(movieID), context);
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        // lay list cac phim dang chieu : 
        ArrayList<MovieKhai> listAvailabelMovies = new ArrayList<>();
        try {
            listAvailabelMovies = DAO.MovieDAO.getInstance().getAvailableMovies(context);
        } catch (Exception ex) {

            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        // lay list cac Reviews : 
        ArrayList<Review> listReviews = new ArrayList<>();
        try {
            listReviews = DAO.MovieDAO.getInstance().getReviewsByMovieID(Integer.parseInt(movieID), context);
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        // them vao Request : 
        request.setAttribute("movie", movie);
        request.setAttribute("listAvalableMovies", listAvailabelMovies);
        request.setAttribute("listReviews", listReviews);

        // chuyen qua cho DisplayMovieInfo.jsp : 
        request.getRequestDispatcher("page/movie/DisplayMovieInfo.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
