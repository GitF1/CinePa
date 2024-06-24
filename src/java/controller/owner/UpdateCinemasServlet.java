/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.CinemasDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import util.FileUploader;
import util.RouterJSP;

@WebServlet(name = "UpdateCinemasServlet", urlPatterns = {"/owner/updateCinema"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 10, // 10 MB
    maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UpdateCinemasServlet extends HttpServlet {

    
    RouterJSP router = new RouterJSP();
    CinemasDAO cinemasDAO;
    FileUploader fileUploader;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.cinemasDAO = new CinemasDAO(getServletContext());
            this.fileUploader = new FileUploader();
        } catch (Exception ex) {
            Logger.getLogger(UpdateCinemasServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet UpdateCinemasServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCinemasServlet at " + request.getContextPath() + "</h1>");
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
        int cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
        try {
            Cinema cinema = cinemasDAO.getCinemaByID(cinemaID);
            request.setAttribute("cinema", cinema);
            request.getRequestDispatcher(router.UPDATE_CINEMA).forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UpdateCinemasServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
            String address = request.getParameter("address");
            String province = request.getParameter("provinceName");
            String district = request.getParameter("districtName");
            String commune = request.getParameter("communeName");

            // Retrieve the existing cinema to get the current avatar
            Cinema existingCinema = cinemasDAO.getCinemaByID(cinemaID);
            String avatar = existingCinema.getAvatar();

            // Handle avatar upload
            Part avatarPart = request.getPart("avatar");
            if (avatarPart != null && avatarPart.getSize() > 0) {
                File tempFile = File.createTempFile("upload_", "_");
                avatarPart.write(tempFile.getAbsolutePath());
                avatar = fileUploader.uploadAndReturnUrl(tempFile, "cinema_" + cinemaID, "cinemas");
            }

            // Update cinema details
            Cinema cinema = new Cinema(cinemaID, address, province, district, commune, avatar);
            cinemasDAO.updateCinema(cinema);

            int cinemaChainID = cinemasDAO.getCinemaChainIDByCinemaID(cinemaID);
            response.sendRedirect("cinemas?cinemaChainID=" + cinemaChainID);
        } catch (Exception ex) {
            Logger.getLogger(UpdateCinemasServlet.class.getName()).log(Level.SEVERE, null, ex);
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
