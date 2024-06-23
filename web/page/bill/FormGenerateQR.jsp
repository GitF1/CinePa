<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Generate QR Code</title>
    </head>
    <body>
        <h1>Enter Details to Generate QR Code</h1>
        <form action="/movie/generate/QR" method="get">
            
            <label for="user-id">Order ID:</label>
            <input type="text" id="order-id" name="order-id" required><br><br>
          
            <label for="user-id">User ID:</label>
            <input type="text" id="user-id" name="user-id" required><br><br>
            <input type="submit" value="Generate QR Code">
            
        </form>
    </body>
</html>
