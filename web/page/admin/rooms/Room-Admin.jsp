<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Room" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Rooms</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .action-links a {
                margin-right: 10px;
            }
            .btn-success {
                color: #fff;
                background-color: #246c9f;
                border-color: #f8f9fa;
            }
        </style>

    </head>
    <body>
        <h1 class="text-center mt-3">Rooms</h1>
        <div class="container mt-4">
       
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Capacity</th>
                        <th>Size</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="room" items="${rooms}">
                        <tr>
                            <td>${room.name}</td>
                            <td>${room.type}</td>
                            <td>${room.capacity}</td>
                            <td>${room.length}m x ${room.width}m</td>
                            <td>${room.status}</td>
                            
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
