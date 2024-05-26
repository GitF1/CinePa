<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.CinemaChain" %> <!-- Import List class -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Select Cinema Chain</title>
        <style>

            .cinema-chain {
                display: inline-block;
                margin-right: 10px; /* Adjust spacing between cinema chains */
            }

            .img-item_theater {
                width: 90x;
                height: 90px;
                border-radius: 8px;
                border: 1px solid #eee;
                cursor: pointer;
                transform: all 0.2s linear;
                transition: all 0.2s linear;
            }

            .img-item_theater:hover{
                opacity: 0.8;
                scale:1.1;

            }
        </style>
        <script>

            function onSelectCinemaChain(cinemaChainId) {
                // Redirect or perform any action based on the selected cinema chain
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "schedule", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        window.location.href = "/movie/schedule";
                    }
                };
                xhr.send("cinemaChainID=" + encodeURIComponent(cinemaChainId));
            }

        </script>
    </head>
    <body>
        <div>
            <div class="cinema-chain" onclick="onSelectCinemaChain(0)">
                <img class="img-item_theater" src="/movie/assets/images/logo_default_theater.jpg" alt="${cinemaChain.getName()}" >
                <br>
                Tất cả
            </div>
            <c:forEach items="${cinemaChains}" var="cinemaChain">
                <div class="cinema-chain" onclick="onSelectCinemaChain(${cinemaChain.getCinemaChainID()})">
                    <c:choose>
                        <c:when test="${empty cinemaChain.getAvatar()}">
                            <img class="img-item_theater" src="/movie/assets/images/logo_default_theater.jpg" alt="${cinemaChain.getName()}" style="width: 100px; height: 100px;">
                        </c:when>
                        <c:otherwise>
                            <img class="img-item_theater" src="${cinemaChain.getAvatar()}" alt="${cinemaChain.getName()}" style="width: 100px; height: 100px;">
                        </c:otherwise>
                    </c:choose> 
                    <br>
                    ${cinemaChain.getName()}
                </div>
            </c:forEach>
        </div>

    </body>
</html>
