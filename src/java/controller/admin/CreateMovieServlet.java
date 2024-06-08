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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;

/**
 *
 * @author duyqu
 */
@WebServlet(name = "CreateMovieServlet", urlPatterns = {"/CreateMovieServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class CreateMovieServlet extends HttpServlet {

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
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateMovieServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateMovieServlet at " + request.getContextPath() + "</h1>");
            out.println(request.getParameter("title"));
            out.println(request.getParameter("synopsis"));
            out.println(request.getParameter("datePublished"));
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Date parsed = format.parse(request.getParameter("datePublished"));
//            String dateString=format.format(parsed ).replace("-","");
//            out.println(dateString);
//cloudinary bit bobs
            ServletContext context = getServletContext();
            HttpSession session = request.getSession();
            String movieFileName = ((String) request.getParameter("title")).concat(request.getParameter("datePublished"));
            out.println(movieFileName);
            String uploadedImageUrl = "";
//            int userID = (int) session.getAttribute("userID");
//            System.out.println("userID: " + userID);
            Part filePart = request.getPart("file");
            String fileName = filePart.getSubmittedFileName();
            //broken somewhere above here

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
            out.println("");
            out.println(request.getParameter("country"));
            for (String s : request.getParameterValues("genres")) {//check null here
                out.println(s);
            }
            out.println(request.getParameter("duration"));
            out.println(request.getParameter("urlInput"));
            out.println(request.getParameter("status"));
            out.println("</body>");
            out.println("</html>");
//int movieID, String title, String synopsis, String datePublished, String imageURL, double rating, String status, String country, int length, String trailerLink
            Movie newMovie = new Movie(
                    1,
                    (String) request.getParameter("title"),
                    (String) request.getParameter("synopsis"),
                    (String) request.getParameter("datePublished"),
                    uploadedImageUrl,
                    0,
                    (String) request.getParameter("status"),
                    (String) request.getParameter("country"),
                    Integer.parseInt(request.getParameter("duration")),
                    (String) request.getParameter("urlInput")
            );
            out.println(newMovie);
            movieDAO.insertMovie(newMovie);
            movieDAO.insertMovieGenre(newMovie, request.getParameterValues("genres"));
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
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(CreateMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException ex) {
            Logger.getLogger(CreateMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
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
