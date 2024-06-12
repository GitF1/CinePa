<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Cinema" %> <!-- Import List class -->
<%--<%@ page import="model.Schedule.schedule" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Select Cinema Chain</title>
        <style>
            .container-cinema_main{
                margin-top:-15px;
            }
            .cinema_container {
                display: flex;
                margin: 15px 0;
                margin-right: 10px;
                align-items: center;
                border: 1px solid #ccc;
                padding: 8px;
                border-radius: 10px;
                cursor: pointer;
                transition: all 0.4s linear;
            }
            .cinema_container:hover{
                opacity: 0.8;
                background-color: #f0f0f0;
            }
            .cinema_container:hover .img_cinema{
                transform: scale(1.05);
            }
            .img_cinema{
                width: 55px;
                height: 55px;
                border-radius: 50%;
                cursor: pointer;
                border: 1px solid #ccc;
                margin-right: 10px;
                transition: all 0.2s linear;
            }
            .img_cinema:hover{
                opacity: 0.8;

            }

        </style>
        <script>

            function onSelectCinema(cinemaId) {

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "schedule", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var responseUrl = xhr.responseText;
                        window.location.href = responseUrl;
                    }
                };
                xhr.send("cinemaID=" + encodeURIComponent(cinemaId));
            }

        </script>
    </head>
    <body>
        <div class="container-cinema_main">

            <c:forEach items="${listCinemaBranch}" var="cinema">
                <div class="cinema_container" onclick="onSelectCinema('${cinema.getCinemaID()}')">
                    <c:choose>
                        <c:when test="${empty cinema.getAvatar()}">
                            <img class="img_cinema" src="/movie/assets/images/logo_default_theater.jpg" alt="${cinema.getAddress()}" >
                        </c:when>
                        <c:otherwise>
                            <img class="img_cinema" src="${cinema.getAvatar()}" alt="${cinema.getAddress()}">
                        </c:otherwise>
                    </c:choose> 
                    <div>
                        ${cinema.getAddress()}
                    </div>

                </div>
            </c:forEach>
        </div>


    </body>
</html>