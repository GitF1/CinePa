<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Movie List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/movie/FilterMovie-style.css">

    </head>
    <body>
        <jsp:include page="../landingPage/Header.jsp" />

        <jsp:include page="../landingPage/Banner.jsp" />

        <div class="container">
            <!-- Filter Form -->
            <form method="get" action="${pageContext.request.contextPath}/filter-movies">
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <h2 class="text-center">Tìm kiếm phim trên CinePa</h2>
                    </div>
                    <div class="form-group col-md-8">
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label for="genre"></label>
                                <select id="genre" name="genre" class="form-control" onchange="this.form.submit()">
                                    <option value="" selected disabled>Thể loại</option>
                                    <option value="">Tất cả</option>
                                    <c:forEach var="genre" items="${allGenres}">
                                        <option value="${genre}" <c:if test="${param.genre == genre}">selected</c:if>>${genre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="country"></label>
                                <select id="country" name="country" class="form-control" onchange="this.form.submit()">
                                    <option value="" selected disabled>Quốc gia</option>
                                    <option value="">Tất cả</option>
                                    <c:forEach var="country" items="${allCountries}">
                                        <option value="${country}" <c:if test="${param.country == country}">selected</c:if>>${country}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="year"></label>
                                <select id="year" name="year" class="form-control" onchange="this.form.submit()">
                                    <option value="" selected disabled>Năm</option>
                                    <option value="">Tất cả</option>
                                    <c:forEach var="year" items="${allYears}">
                                        <option value="${year}" <c:if test="${param.year == year}">selected</c:if>>${year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <!-- Movie Grid -->
            <div class="row">
                <c:forEach var="movie" items="${movies}" varStatus="status">
                    <div class="col-md-3 mb-4">
                        <div class="card">
                            <a href="${pageContext.request.contextPath}/HandleDisplayMovieInfo?movieID=${movie.movieID}">
                                <img src="${movie.imageURL}" alt="${movie.title}" class="card-img-top">
                            </a>             
                            <div class="card-body">
                                <h5 class="card-title">${movie.title}</h5>
                                <p class="card-text"><strong>Genres:</strong>
                                    <c:forEach var="genre" items="${movieGenresMap[movie.movieID]}">
                                        <c:out value="${genre}"/>
                                        <c:if test="${!genre.equals(movieGenresMap[movie.movieID].get(movieGenresMap[movie.movieID].size() - 1))}">
                                            ,
                                        </c:if>
                                    </c:forEach>
                                </p>
                                <p class="card-text"><strong>⭐ </strong> ${movie.rating}</p>
                            </div>
                        </div>
                    </div>
                    <c:if test="${status.count % 4 == 0 && !status.last}">
                    </div>
                    <div class="row">
                    </c:if>
                </c:forEach>
            </div>

            <!-- Pagination -->
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item <c:if test="${currentPage == 1}">disabled</c:if>">
                        <a class="page-link" href="?genre=${param.genre}&country=${param.country}&year=${param.year}&page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item <c:if test="${i == currentPage}">active</c:if>">
                            <a class="page-link" href="?genre=${param.genre}&country=${param.country}&year=${param.year}&page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item <c:if test="${currentPage == totalPages}">disabled</c:if>">
                        <a class="page-link" href="?genre=${param.genre}&country=${param.country}&year=${param.year}&page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>

        </div>

        <jsp:include page="../home/Footer.jsp" />

    </body>
</html>
