<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
    <h2>Register</h2>
    <form action="register" method="post">
        
        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username" required><br>
        
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" required><br>
        
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br>

        <label for="fullName">Full Name:</label><br> <!-- Sửa đổi thuộc tính name thành fullname -->
        <input type="text" id="fullName" name="fullName" required><br> <!-- Sửa đổi thuộc tính name thành fullname -->

        <input type="submit" value="Register">
        <% 
            String error = (String)request.getAttribute("error");
            if (error != null) {
                out.println("<p>" + error + "</p>");
            }
        %>
    </form>
</body>
</html>

