/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import controller.user.UpdateUserInfo;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.movie.MovieInfo;
import util.RouterJSP;

/**
 *
 * @author duyqu
 */
@WebServlet(name = "PerformUpdateMovieServlet", urlPatterns = {"/PerformUpdateMovieServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class PerformUpdateMovieServlet extends HttpServlet {
    
    RouterJSP route = new RouterJSP();
    private Cloudinary cloudinary;
//    DAO.UserDAO userDAO;
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
            movieDAO = new DAO.MovieDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PerformUpdateMovieServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PerformUpdateMovieServlet at " + request.getContextPath() + "</h1>");
            out.println(request.getParameter("movieID"));
//            out.println(request.getParameter("movieID"));
            out.println(request.getParameter("title"));
            out.println(request.getParameter("synopsis"));
            out.println(request.getParameter("datePublished"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(request.getParameter("datePublished"));
            ServletContext context = getServletContext();
            HttpSession session = request.getSession();
            String movieFileName = ((String) request.getParameter("title")).concat(request.getParameter("datePublished"));
            out.println(movieFileName);
            String uploadedImageUrl = "";
            Part filePart = request.getPart("file");
            String fileName = filePart.getSubmittedFileName();
            if (fileName.equals("")) {
                out.println(true);
            } else {
                out.println(false);
                File tempFile = File.createTempFile("upload_", "_" + fileName);
                filePart.write(tempFile.getAbsolutePath());
                try {
                    // Upload the file to Cloudinary
                    Map<String, Object> uploadParams = ObjectUtils.asMap(
                            "use_filename", true,
                            "unique_filename", false,
                            "overwrite", true,
                            "public_id", movieFileName, // Set the desired name for the uploaded image
                            "folder", "movie" // Specify the nested folder path
                    );
                    
                    Map uploadResult = cloudinary.uploader().upload(tempFile, uploadParams);
                    
                    uploadedImageUrl = (String) uploadResult.get("secure_url");
//            System.out.println("image url from cloud: " + uploadedImageUrl);

//            boolean isUpdate = userDAO.updateAvatarByUserID(userID, uploadedImageUrl);
//            System.out.println("is update" + isUpdate);
                    // Print the uploaded image URL
//            response.getWriter().println("Uploaded Image URL: " + uploadedImageUrl);
                    out.println(uploadedImageUrl);
                    
                } catch (Exception e) {
                    
                    e.printStackTrace(out);
                } finally {
                    // Clean up the temporary file
                    if (tempFile.exists()) {
                        tempFile.delete();
                    }
                }
            }
            if (uploadedImageUrl.equals("")) {
                uploadedImageUrl = movieDAO.getImageUrlByID(Integer.parseInt(request.getParameter("movieID")));
            }
//            int movieID, int cinemaID, String title, Date datePublished, float rating, String imageURL,
//                 String synopsis, String country, int year, int length, String linkTrailer, List<String> genres
            List<String> genres = new ArrayList<>();
            for (String s : request.getParameterValues("genres")) {
                genres.add(s);
            }
            
            MovieInfo mi = new MovieInfo(
                    Integer.parseInt(request.getParameter("movieID")),
                    0,
                    request.getParameter("title"),
                    parsed,
                    0,
                    uploadedImageUrl,
                    request.getParameter("synopsis"),
                    request.getParameter("country"),
                    parsed.getYear() + 1900,
                    Integer.parseInt(request.getParameter("duration")),
                    request.getParameter("urlInput"),
                    genres
            );
            movieDAO.updateMovieByObject(mi, request.getParameter("status"),request.getParameterValues("genres"));
            
            
            out.println("</body>");
            out.println("</html>");
            response.sendRedirect("UpdateMovieServlet");
        }
//        movieDAO.deleteAllGenresOfMovieByID(Integer.parseInt(request.getParameter("movieID")));
//        movieDAO.insertMovieGenreByID(Integer.parseInt(request.getParameter("movieID")), request.getParameterValues("genres"));
//        response.sendRedirect("UpdateMovieServlet");
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
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PerformUpdateMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PerformUpdateMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PerformUpdateMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PerformUpdateMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
