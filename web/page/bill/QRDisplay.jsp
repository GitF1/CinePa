<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>QR</title>
</head>
<body>
    <h1>Scan the QR Code</h1>
    <%
        // Get the parameters from the request
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String itemId = request.getParameter("item-id");
        String userId = request.getParameter("user-id");

        // Generate the URL for the QR code image
        String qrCodeUrl = "generateQRCode?email=" + email + "&phone=" + phone + "&item-id=" + itemId + "&user-id=" + userId;
    %>
    <img src="<%= qrCodeUrl %>" alt="QR Code">
</body>
</html>
