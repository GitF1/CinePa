<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.CinemaChain" %> <!-- Import List class -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Select Cinema Chain</title>
        <style>
            .wrapper-cinema__chain_list{
                display: flex;
                margin: 20px 10px;
            }
            .cinema-chain {
                display: flex;
                margin-right: 20px;
                flex-direction: column;
                justify-content: center;
                align-items: center;
            }

            .img-item_theater {
                width: 70px;
                height: 70px;
                border-radius: 10px;
                border: 1px solid #eee;
                cursor: pointer;
                transition: all 0.2s linear;
                overflow: hidden;

            }

            .img-item_theater:hover{
                opacity: 0.8;
                scale:1.1;
            }

            .cinema-name{
                max-width: 70px;
                color:#737373;
                font-size: .75em;
                white-space: nowrap;
                overflow:hidden;
                text-overflow: ellipsis;
                margin-top:5px;
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
                        var responseUrl = xhr.responseText;
                        window.location.href = responseUrl;
                    }
                };
                xhr.send("cinemaChainID=" + encodeURIComponent(cinemaChainId));
            }

        </script>
    </head>
    <body>
        <div class="wrapper-cinema__chain_list">
            <div class="cinema-chain" onclick="onSelectCinemaChain(0)">
                <img class="img-item_theater" src="/movie/assets/images/logo_default_theater.jpg" alt="${cinemaChain.getName()}" >
                <div class="cinema-name">
                    Tất cả
                </div>

            </div>
            <c:forEach items="${cinemaChains}" var="cinemaChain">
                <div class="cinema-chain" onclick="onSelectCinemaChain(${cinemaChain.getCinemaChainID()})">
                    <c:choose>
                        <c:when test="${empty cinemaChain.getAvatar()}">
                            <img class="img-item_theater" src="/movie/assets/images/logo_default_theater.jpg" alt="${cinemaChain.getName()}" >
                        </c:when>
                        <c:otherwise>
                            <img class="img-item_theater" src="${cinemaChain.getAvatar()}" alt="${cinemaChain.getName()}" >
                        </c:otherwise>
                    </c:choose> 
                    <div class="cinema-name">
                        ${cinemaChain.getName()}
                    </div>

                </div>
            </c:forEach>
        </div>

    </body>
</html>
