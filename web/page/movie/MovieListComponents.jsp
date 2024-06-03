<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--<%@ page import="DAO.MovieDAOQV" %>--%>
<%--<%@ page import="DAO.MovieDAO" %>--%>



<%@ page import="model.MovieWithStatus" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    String status = request.getParameter("status");
    if (status == null || (!status.equals("Showing") && !status.equals("Coming"))) {
        status = "Showing"; // Default status
    }

       // tao Servlet Context : (khai tao)
                ServletContext context = getServletContext();
//// cua Quang Vinh :     
//    MovieDAOQV movieDAO = new MovieDAOQV(getServletContext());
    
    
    
//    List<MovieWithStatus> movies = movieDAO.getMoviesByStatus(status); the : 
    List<MovieWithStatus> movies = DAO.MovieDAO.getInstance().getMoviesByStatus(status , context ) ; 

    
    
    
//    Map<Integer, List<String>> movieGenresMap = movieDAO.getAllMovieGenres(); the : 
    Map<Integer, List<String>> movieGenresMap = DAO.MovieDAO.getInstance().getAllMovieGenres(context) ; 


    String uniqueId = java.util.UUID.randomUUID().toString();

    request.setAttribute("movies", movies);
    request.setAttribute("movieGenresMap", movieGenresMap);
    request.setAttribute("status", status);
    request.setAttribute("uniqueId", uniqueId);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Movie List</title>
        <!-- Include necessary CSS and JavaScript files only once -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/movie-style.css">
    </head>
    <body>
        <div class="container">
            <h2 class="text-center"><c:out value="${status}"/></h2>
            <div id="movieSlider_${uniqueId}" class="slider">
                <c:forEach var="movie" items="${movies}">


                    <a  class="the-a"  href="${pageContext.request.contextPath}/HandleDisplayMovieInfo?movieID=${movie.movieID}">


                        <div class="movie-card">
                            <img src="${movie.imageURL}" alt="${movie.title}" class="card-img-top">
                            <div class="movie-info">
                                <div class="movie-title">
                                    <h5>${movie.title}</h5>
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

                    </a>



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