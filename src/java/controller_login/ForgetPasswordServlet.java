/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller_login;

import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import service.SendEmail;
import util.Router;

/**
 *
 * @author ACER
 */
@WebServlet("/forgetpassword")
public class ForgetPasswordServlet extends HttpServlet {

    Router route = new Router();

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgetPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgetPasswordServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
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


        String startAgain = request.getParameter("start-again");
        if (startAgain != null) {
            request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
            return;
        }

        String email = request.getParameter("email");
        String OTP = request.getParameter("OTP");
        String newPassword = request.getParameter("new-password");
        UserDAO ud;

        System.out.println("newPassword = " + newPassword);
        
        if (newPassword != null) {
            boolean changePasswordOk = false;
            try {
                ud = new UserDAO(request.getServletContext());
                ud.updateUserPassword(email, newPassword);
                changePasswordOk = true;
                request.setAttribute("email", email);
                request.setAttribute("changePasswordOk", changePasswordOk);
                System.out.println("changePasswordOk = " + changePasswordOk);
                if (changePasswordOk)
                request.getRequestDispatcher(route.LOGIN).forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        if (OTP != null) {
            boolean verifyOTPOk = false;
            try {
                ud = new UserDAO(request.getServletContext());
                if (OTP.equals(ud.getUserCode(email))) {
                    verifyOTPOk = true;
                }
                request.setAttribute("email", email);
                request.setAttribute("verifyOTPOk", verifyOTPOk);
                System.out.println("VerifyOTPOK = " + verifyOTPOk);

                request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        boolean isExistedEmail = false;

        try {
            
            ud = new UserDAO(request.getServletContext());

            if (ud.checkExistEmail(email)) {
                isExistedEmail = true;
            }
            request.setAttribute("isExistedEmail", isExistedEmail);

            System.out.println("isExistedEmail = " + isExistedEmail);
            
            if (!isExistedEmail) {
                request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
                return;
            }
            
            SendEmail sendEmail = new SendEmail();
            String code = sendEmail.getRanDom();
            User user = new User();
            
            user.setEmail(email);
            user.setCode(code);
            
            boolean sendEmailOk = sendEmail.sendEmail(user);

            if (sendEmailOk) {
                ud.updateUserCode(email, code);
                request.setAttribute("email", email);
            }

            request.setAttribute("sendEmailOk", sendEmailOk);

            System.out.println("sendEmailOk = " + sendEmailOk);

            request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
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
