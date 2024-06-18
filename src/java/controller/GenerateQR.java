/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 *
 * @author PC
 */
@WebServlet(name = "GenerateQR", urlPatterns = {"/generate/QR"})
public class GenerateQR extends HttpServlet {

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
        // Retrieve email, phone, item-id, and user-id from the request
        String orderId = request.getParameter("order-id");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String itemId = request.getParameter("item-id");
        String userId = request.getParameter("user-id");

        // Validate parameters
        if (orderId == null || email == null || phone == null || itemId == null || userId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing email, phone, item-id, or user-id");
            return;
        }

        // Construct the QR code data
        String qrCodeText = "http://localhost:8080/movie/order/view?" + "orderID=" + orderId + "email=" + email + "&phone=" + phone + "&item-id=" + itemId + "&user-id=" + userId;
        int size = 250;
        String fileType = "png";
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size);
            MatrixToImageWriter.writeToStream(bitMatrix, fileType, outputStream);
        } catch (WriterException e) {
            throw new ServletException(e);
        } finally {
            outputStream.close();
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
        processRequest(request, response);
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
