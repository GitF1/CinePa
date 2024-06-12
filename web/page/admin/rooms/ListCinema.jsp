<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Cinema" %> <!-- Import List class -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cinemas</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .cinema-container {
                display: flex;
                align-items: center;
                margin: 15px 0;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 10px;
                transition: all 0.4s linear;
                cursor: pointer;
            }
            .cinema-container:hover {
                background-color: #f0f0f0;
                opacity: 0.8;
            }
            .img-cinema {
                width: 70px;
                height: 70px;
                border-radius: 50%;
                border: 1px solid #eee;
                margin-right: 15px;
                transition: all 0.3s ease;
            }
            .img-cinema:hover {
                opacity: 0.8;
            }
        </style>
        <script>
            function onSelectCinema(cinemaId) {
                window.location.href = 'rooms?cinemaID=' + encodeURIComponent(cinemaId);
            }
        </script>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">CinemaApp</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">About</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Contact</a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container mt-5">
            <h1 class="mb-4 text-center">Cinemas</h1>
            <div class="row">
                <c:forEach var="cinema" items="${cinemas}">
                    <div class="col-lg-4 col-md-6 col-sm-12 mb-4">
                        <div class="cinema-container" onclick="onSelectCinema(${cinema.cinemaID})">
                            <c:choose>
                                <c:when test="${empty cinema.avatar}">
                                    <img class="img-cinema" src="/movie/assets/images/logo_default_theater.jpg" alt="${cinema.address}">
                                </c:when>
                                <c:otherwise>
                                    <img class="img-cinema" src="${cinema.avatar}" alt="${cinema.address}">
                                </c:otherwise>
                            </c:choose>
                            <div>
                                <h5>${cinema.name}</h5>
                                <p>${cinema.address}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <footer class="bg-light text-center text-lg-start mt-5">
            <div class="container p-4">
                <div class="row">
                    <div class="col-lg-6 col-md-12 mb-4 mb-md-0">
                        <h5 class="text-uppercase">CinemaApp</h5>
                        <p>Your one-stop destination for all your cinema needs.</p>
                    </div>
                    <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                        <h5 class="text-uppercase">Links</h5>
                        <ul class="list-unstyled mb-0">
                            <li>
                                <a href="#!" class="text-dark">About</a>
                            </li>
                            <li>
                                <a href="#!" class="text-dark">Contact</a>
                            </li>
                            <li>
                                <a href="#!" class="text-dark">Privacy Policy</a>
                            </li>
                        </ul>
                    </div>
                    <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                        <h5 class="text-uppercase">Follow Us</h5>
                        <ul class="list-unstyled mb-0">
                            <li>
                                <a href="#!" class="text-dark">Facebook</a>
                            </li>
                            <li>
                                <a href="#!" class="text-dark">Twitter</a>
                            </li>
                            <li>
                                <a href="#!" class="text-dark">Instagram</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="text-center p-3 bg-dark text-white">
                © 2024 CinemaApp
            </div>
        </footer>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
