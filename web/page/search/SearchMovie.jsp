<%-- 
    Document   : TestSearch
    Created on : May 24, 2024, 10:22:04 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <c:set var="movieName" value="${requestScope.movieName}"></c:set>
        
            <form id="searchMovieForm" action="searchmovie" method="post">
                Movie Name: <input type="text" id="movieNameInput" name="movieName" value="${movieName}" >
                <button type="submit">Search</button>
            </form>

    </div>
    <c:set var="movies" value="${requestScope.movies}"></c:set>
    <c:if test="${not empty movies}">
        <div>
            <c:forEach var="movie" items="${movies}">
                <div>
                    <h2>${movie.title}</h2>
                    <!--<img src="${movie.imageURL}" alt="${movie.title} image" />-->
                    <p><strong>Synopsis:</strong> ${movie.synopsis}</p>
                    <p><strong>Release Date:</strong> <fmt:formatDate value="${movie.datePublished}" pattern="yyyy-MM-dd" /></p>
                    <p><strong>Rating:</strong> ${movie.rating}</p>
                    <p><strong>Country:</strong> ${movie.country}</p>
                    <p><strong>Status:</strong> ${movie.status}</p>
                </div>
                <hr/>
            </c:forEach>
        </div>
    </c:if>

    </body>
    <script>
        const movieNameInput = document.getElementById("movieNameInput");
        document.addEventListener("DOMContentLoaded", function() {
            movieNameInput.focus();
            const length = movieNameInput.value.length;
            movieNameInput.setSelectionRange(length, length);
        });

        function debounce(cb) {
            let timeout;
            let delay = 1200;

            return (...args) => {
                clearTimeout(timeout);
                timeout = setTimeout(() => {
                    cb(...args);
                }, delay);
            };
        }

            function callServlet(id, url, methodType) {
                document.getElementById(id).action = url;
                document.getElementById(id).method = methodType;
                document.getElementById(id).submit();
            }

            const queryMovies = debounce(() => {
                callServlet('searchMovieForm', '/movie/searchmovie', 'POST');
            });

            movieNameInput.addEventListener("input", () => {
                queryMovies();
            });

    </script>
    </html>
