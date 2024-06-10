<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@ page import="model.MovieWithStatus" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@page import="model.Movie" %>
<%@page import="DAO.MovieDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Movie List</title>
        <!--Include necessary CSS and JavaScript files only once--> 
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/movie-style.css">
        <style>
            /* Xác định màu chữ mặc định và hiệu ứng chuyển màu */
            .movie-title a {
                text-decoration: none; /* Không có gạch chân */
                color: #262626; /* Màu chữ mặc định */
                transition: color 0.3s ease; /* Hiệu ứng chuyển màu */
            }

            /* Hover effect cho phần tử a */
            .movie-title a:hover {
                color: #4dbf00; /* Đổi màu khi hover */
                text-decoration: none; /* Đảm bảo không có gạch chân khi hover */
            }

            /* Hover effect cho phần tử h5 bên trong phần tử a */
            .movie-title a:hover h5 {
                color: #4dbf00; /* Đổi màu chữ của h5 khi hover link */
                text-decoration: none; /* Đảm bảo không có gạch chân khi hover */
            }

        </style>
    </head>
    <body class="BodyMovie">
        <%
            String status = request.getParameter("status");
            if (status == null || (!status.equals("Showing") && !status.equals("Coming"))) {
                status = "Showing"; // Default status
            }

            MovieDAO movieDAO = new MovieDAO(getServletContext());
            List<MovieWithStatus> movies = movieDAO.getMoviesByStatus(status);
            Map<Integer, List<String>> movieGenresMap = movieDAO.getAllMovieGenres();

            String uniqueId = java.util.UUID.randomUUID().toString();

            request.setAttribute("movies", movies);
            request.setAttribute("movieGenresMap", movieGenresMap);
            request.setAttribute("status", status);
            request.setAttribute("uniqueId", uniqueId);
        %>

        <div class="container">
            <h2 class="text-center"><c:out value="${status}"/></h2>
            <div id="movieSlider_${uniqueId}" class="slider">
                <c:forEach var="movie" items="${movies}">
                    <div class="movie-card">
                        <a href="${pageContext.request.contextPath}/HandleDisplayMovieInfo?movieID=${movie.movieID}">
                            <img src="${movie.imageURL}" alt="${movie.title}" class="card-img-top">
                        </a>
                        <div class="movie-info">
                            <div class="movie-title">
                                <a href="${pageContext.request.contextPath}/HandleDisplayMovieInfo?movieID=${movie.movieID}" class="movie-title-link">
                                    <h5>${movie.title}</h5>
                                </a>
                            </div>
                            <div class="movie-genre">
                                <p><strong>Genres:</strong> 
                                    <c:forEach var="genre" items="${movieGenresMap[movie.movieID]}">
                                        <c:out value="${genre}"/>
                                        <c:if test="${!genre.equals(movieGenresMap[movie.movieID].get(movieGenresMap[movie.movieID].size() - 1))}">
                                            ,
                                        </c:if>
                                    </c:forEach>
                                </p>
                            </div>
                            <div class="movie-rating">
                                <p><strong>⭐ </strong> ${movie.rating}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>

        <script>
            $(document).ready(function () {
                $('#movieSlider_${uniqueId}').slick({
                    slidesToShow: 4,
                    slidesToScroll: 1,
                    autoplay: true,
                    autoplaySpeed: 2000,
                    responsive: [
                        {
                            breakpoint: 1024,
                            settings: {
                                slidesToShow: 3,
                                slidesToScroll: 1,
                            }
                        },
                        {
                            breakpoint: 600,
                            settings: {
                                slidesToShow: 2,
                                slidesToScroll: 1,
                            }
                        },
                        {
                            breakpoint: 480,
                            settings: {
                                slidesToShow: 1,
                                slidesToScroll: 1,
                            }
                        }
                    ]
                });
            });
        </script>
    </body>
</html>
