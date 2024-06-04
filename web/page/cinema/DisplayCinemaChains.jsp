<%-- 
    Document   : DisplayCinema
    Created on : May 25, 2024, 7:30:43 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Cinema Chains</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <form id="displayCinemaChainsForm">
            <c:set var="cinemaChains" value="${requestScope.cinemaChains}"></c:set>
                <nav>
                    <ul class="menu">
                        <li class="menu-item">Cinema
                            <!-- <ul class="submenu"> -->
                        <c:forEach var="cinemaChain" items="${cinemaChains}">
                        <li class="submenu-item" id="cinemaChain" name="cinemaChain" onclick="callServlet('displayCinemaChainsForm', '/movie/displaycinemachains', 'POST')">${cinemaChain.getName()}</li>
                        </c:forEach>
                    </li>
                </ul>
            </nav>
        </form>
    </body>

    <script src="javascript/style.js">
//        const cinemaChain = document.getElementsByName("cinemaChain");
//        cinemaChain.addEventListener("click", () => {
//            callServlet('displayMovieForm', '/movie/handlecinema', 'POST');
//        });
    </script>
</html>

