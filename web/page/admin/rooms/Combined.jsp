<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.CinemaChain, model.Cinema" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cinema Chains and Cinemas</title>
        <style>
            .cinema-chain, .cinema_container {
                display: inline-block;
                margin-right: 10px;
            }
            .img-item_theater, .img_cinema {
                width: 90px;
                height: 90px;
                border-radius: 8px;
                border: 1px solid #eee;
                cursor: pointer;
                transition: all 0.2s linear;
            }
            .img-item_theater:hover, .img_cinema:hover {
                opacity: 0.8;
                transform: scale(1.1);
            }
            .cinema_container {
                display: flex;
                margin: 15px 0;
                align-items: center;
                border: 1px solid #ccc;
                padding: 10px;
                border-radius: 10px;
                cursor: pointer;
                transition: all 0.4s linear;
            }
            .cinema_container:hover {
                opacity: 0.8;
                background-color: #f0f0f0;
            }
            .btn-primary {
                color: #fff;
                background-color: #dcb760;
                border-color: #ffc107;
            }
        </style>
        <script>
            function onSelectCinemaChain(cinemaChainId) {
                window.location.href = 'combined?cinemaChainID=' + encodeURIComponent(cinemaChainId);
            }

            function onSelectCinema(cinemaId) {
                window.location.href = 'rooms?cinemaID=' + encodeURIComponent(cinemaId);
            }
        </script>
    </head>
    <body>
        <h1>Cinema Chains and Cinemas</h1>
        <div>
            <c:choose>
                <c:when test="${empty param.cinemaChainID}">
                    <h2>Cinema Chains</h2>
                    <c:forEach var="cinemaChain" items="${cinemaChains}">
                        <div class="cinema-chain" onclick="onSelectCinemaChain(${cinemaChain.cinemaChainID})">
                            <c:choose>
                                <c:when test="${empty cinemaChain.avatar}">
                                    <img class="img-item_theater" src="/movie/assets/images/logo_default_theater.jpg" alt="${cinemaChain.name}">
                                </c:when>
                                <c:otherwise>
                                    <img class="img-item_theater" src="${cinemaChain.avatar}" alt="${cinemaChain.name}">
                                </c:otherwise>
                            </c:choose>
                            <br>
                            ${cinemaChain.name}
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2>Cinemas</h2>
                    <div class="container-cinema_main">
                        <c:forEach var="cinema" items="${cinemas}">
                            <div class="cinema_container" onclick="onSelectCinema(${cinema.cinemaID})">
                                <c:choose>
                                    <c:when test="${empty cinema.avatar}">
                                        <img class="img_cinema" src="/movie/assets/images/logo_default_theater.jpg" alt="${cinema.address}">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="img_cinema" src="${cinema.avatar}" alt="${cinema.address}">
                                    </c:otherwise>
                                </c:choose>
                                <div>
                                    ${cinema.address}
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
