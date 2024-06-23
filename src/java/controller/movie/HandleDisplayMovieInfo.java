package controller.movie;

import DAO.FavouriteMoviesDAO;
import DAO.MovieDAO;
import DAO.ReviewMovieDAO;
import DAO.UserDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.movie.MovieInfo;
import model.Review;
import DAO.schedule.ScheduleDAO;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.User;
import model.schedule.CinemaMovieSlot;
import util.RouterJSP;

@WebServlet(name = "HandleDisplayMovieInfo", urlPatterns = {"/HandleDisplayMovieInfo"})
public class HandleDisplayMovieInfo extends HttpServlet {

    ScheduleDAO scheduleDAO;
    MovieDAO movieDAO;
    UserDAO userDAO;
    ReviewMovieDAO reviewMovieDAO;
    FavouriteMoviesDAO favoriteMoviesDAO;
    RouterJSP route = new RouterJSP();

    @Override
    public void init()
            throws ServletException {
        super.init();
        try {
            scheduleDAO = new ScheduleDAO(getServletContext());
            movieDAO = new MovieDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userDAO = new UserDAO(request.getServletContext());
            // nhan movieID tu Quang Vinh guii qua :
            String movieID = request.getParameter("movieID");
            if (movieID == null) {
                System.err.println("missing movieID!");
                return;
            }

            // tao Servlet Context :
            ServletContext context = getServletContext();

            //lay Movie :
            MovieInfo movie = new MovieInfo();

            movie = (MovieInfo) movieDAO.getMovieWithGenresByID(Integer.parseInt(movieID));

            // lay list cac phim dang chieu :
            ArrayList<MovieInfo> listAvailabelMovies = new ArrayList<>();

            listAvailabelMovies = movieDAO.getAvailableMovies(context);

            // lay list cac Reviews :
            ArrayList<Review> listReviews = new ArrayList<>();

            listReviews = movieDAO.getReviewsByMovieID(Integer.parseInt(movieID), context);
//            List<CinemaMovieSlot> listCinemaMovieSlots = scheduleDAO.getCinemasShowingMovie(Integer.parseInt(movieID), null);
//
//            System.out.println("list cinema movie slot:" + listCinemaMovieSlots);

            
            Map<Review, User> userReviews = new HashMap<>();

            System.out.println(userReviews);
            
            reviewMovieDAO = new ReviewMovieDAO(request.getServletContext());
            favoriteMoviesDAO = new FavouriteMoviesDAO(request.getServletContext());
            HttpSession session = request.getSession();
            System.out.println(session.getAttribute("userID"));
            int userID = -1;
            if(session.getAttribute("userID") != null) userID = (int)session.getAttribute("userID");
            Boolean canReview = false;
            Boolean isFavoritedMovie = null;
            if(userID != -1) {
                canReview = reviewMovieDAO.canReview(userID, Integer.parseInt(movieID));
                isFavoritedMovie = favoriteMoviesDAO.isFavoritedMovie(userID, Integer.parseInt(movieID));
            }
                        
            for(Review review : listReviews) {
                User user = userDAO.getUserById(review.getUserID());
                userReviews.put(review, user);
            }
            
            // them vao Request :
            request.setAttribute("movie", movie);
            request.setAttribute("listAvailableMovies", listAvailabelMovies);
            request.setAttribute("listReviews", listReviews);
            request.setAttribute("userReviews", userReviews);
            request.setAttribute("canReview", canReview);
            request.setAttribute("isFavoritedMovie", isFavoritedMovie);

            request.getRequestDispatcher("page/movie/DisplayMovieInfo.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userID = (int)session.getAttribute("userID");
        int movieID = Integer.parseInt(request.getParameter("movieID"));
        
        boolean isSendingFeedback = request.getParameter("isSendingFeedback") != null && request.getParameter("isSendingFeedback").equals("true");
        boolean isAddingToFavorite = request.getParameter("isAddingToFavorite") != null && request.getParameter("isAddingToFavorite").equals("true");
        
        
        
        if(isAddingToFavorite) {
            String favoritedAt = request.getParameter("favoritedAt");
            try {
                favoriteMoviesDAO = new FavouriteMoviesDAO(request.getServletContext());
                favoriteMoviesDAO.insertFavouriteMovie(userID, movieID, favoritedAt);
                doGet(request, response);
            } catch (Exception ex) {
                Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        
        if(isSendingFeedback) {
            String reviewContent = request.getParameter("reviewText");
            int rating = Integer.parseInt(request.getParameter("starOutput"));
            String timeCreated = request.getParameter("timeCreatedOutput");
            try {
                reviewMovieDAO = new ReviewMovieDAO(request.getServletContext());
                reviewMovieDAO.insertReview(userID, movieID, rating, timeCreated, reviewContent);
                doGet(request, response);
            } catch (Exception ex) {
                Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        scheduleDAO.handleDoPostComponentSchedule(request, response);

        String redirectUrl = "/movie/HandleDisplayMovieInfo";
        response.setContentType("text/plain");
        response.getWriter().write(redirectUrl);
        
        doGet(request, response);
    }
}
