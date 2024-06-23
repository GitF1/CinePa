/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.CinemaChainDAO;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.PrintWriter;
import model.CinemaChain;
import util.FileUploader;
import util.RouterJSP;

@WebServlet(name = "CreateCinemaChainServlet", urlPatterns = {"/owner/createCinemaChain"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class CreateCinemaChainServlet extends HttpServlet {

    private RouterJSP router = new RouterJSP();
    private CinemaChainDAO cinemaChainDAO;
    private FileUploader fileUploader;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.cinemaChainDAO = new CinemaChainDAO(getServletContext());
            this.fileUploader = new FileUploader();
        } catch (Exception ex) {
            throw new ServletException(ex);
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
            out.println("<title>Servlet CreateCinemaChainServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateCinemaChainServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByUserId(userID);
        if (cinemaChain != null) {
            request.setAttribute("error", "You already have a CinemaChain.");
            response.sendRedirect(request.getContextPath() + "/owner");
        } else {
            request.getRequestDispatcher(router.CREATE_CINEMACHAIN).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String name = request.getParameter("name");
        String information = request.getParameter("information");

        CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByUserId(userID);
        if (cinemaChain != null) {
            request.setAttribute("error", "You already have a CinemaChain.");
            response.sendRedirect(request.getContextPath() + "/owner");
            return;
        }

        String avatarUrl = "";
        String bannerUrl = "";

        // Handle avatar upload
        Part avatarPart = request.getPart("avatar");
        if (avatarPart != null && avatarPart.getSize() > 0) {
            File avatarFile = File.createTempFile("avatar_", "_" + avatarPart.getSubmittedFileName());
            avatarPart.write(avatarFile.getAbsolutePath());
            avatarUrl = fileUploader.uploadAndReturnUrl(avatarFile, "avatar_" + name, "cinema/avatar");
        }

        // Handle banner upload
        Part bannerPart = request.getPart("banner");
        if (bannerPart != null && bannerPart.getSize() > 0) {
            File bannerFile = File.createTempFile("banner_", "_" + bannerPart.getSubmittedFileName());
            bannerPart.write(bannerFile.getAbsolutePath());
            bannerUrl = fileUploader.uploadAndReturnUrl(bannerFile, "banner_" + name, "cinema/banner");
        }

        cinemaChain = new CinemaChain();
        cinemaChain.setName(name);
        cinemaChain.setInformation(information);
        cinemaChain.setAvatar(avatarUrl);
        cinemaChain.setBanner(bannerUrl);

        cinemaChainDAO.createCinemaChain(cinemaChain, userID);

        response.sendRedirect(request.getContextPath() + "/owner");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
