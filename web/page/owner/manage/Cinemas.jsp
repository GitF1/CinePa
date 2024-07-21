<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Cinema" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cinemas</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            .action-links a {
                margin-right: 10px;
            }
            .btn-success {
                color: #fff;
                background-color: #28a745;
                border-color: #28a745;
            }
            .btn-primary, .btn-danger, .btn-info {
                color: #fff;
            }
            img {
                vertical-align: middle;
                border-style: none;
                border-radius: 10px;
            }
            .btn-back {
                margin-bottom: 20px;
            }
        </style>
        <script type="text/javascript">
            function doDelete(cinemaID) {
                if (confirm("Are you sure to delete this cinema with id: " + cinemaID + "?")) {
                    window.location = "deleteCinema?cinemaID=" + cinemaID + "&cinemaChainID=<%= request.getParameter("cinemaChainID") %>";
                }
            }

            function onSelectCinema(cinemaID) {
                window.location.href = 'rooms?cinemaID=' + encodeURIComponent(cinemaID);
            }

            function viewMovies(cinemaID) {
                window.location.href = 'movieCinema?cinemaID=' + encodeURIComponent(cinemaID);
            }

            function goBack() {
                history.back();
            }

            window.onload = function() {
                const urlParams = new URLSearchParams(window.location.search);
                const message = urlParams.get('message');
                if (message) {
                    alert(message);
                }
            }
        </script>
    </head>
    <body>
        <div class="container mt-4">
            <h1 class="text-center mt-3">Cinemas</h1>
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th>Avatar</th>
                        <th>Address</th>
                        <th>Province</th>
                        <th>District</th>
                        <th>Commune</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cinema" items="${cinemas}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${empty cinema.avatar}">
                                        <img src="/movie/assets/images/logo_default_theater.jpg" alt="Default Theater" width="50" height="50">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${cinema.avatar}" alt="${cinema.avatar}" width="50" height="50">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${cinema.address}</td>
                            <td>${cinema.province}</td>
                            <td>${cinema.district}</td>
                            <td>${cinema.commune}</td>
                            <td>
                                <a href="updateCinema?cinemaID=${cinema.cinemaID}" class="btn btn-primary btn-sm">Update</a>
                                <a href="#" onclick="doDelete('${cinema.cinemaID}')" class="btn btn-danger btn-sm">Delete</a>
                                <a href="#" onclick="onSelectCinema('${cinema.cinemaID}')" class="btn btn-info btn-sm">View Rooms</a>
                                <a href="#" onclick="viewMovies('${cinema.cinemaID}')" class="btn btn-success btn-sm">View Movies</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button onclick="goBack()" class="btn btn-secondary btn-back">Back Cinema Chain</button>
        </div>
    </body>
</html>
