<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Movie List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/movie-style.css">
</head>
<body>
    <div class="container">
        <h2 class="text-center">Movie List</h2>

        <!-- Filter Form -->
        <form method="get" action="${pageContext.request.contextPath}/filter-movies">
            <div class="form-group">
                <label for="genre">Filter by Genre:</label>
                <select id="genre" name="genre" class="form-control">
                    <option value="">All</option>
                    <c:forEach var="genre" items="${movieGenresMap}">
                        <option value="${genre.key}" <c:if test="${param.genre == genre.key}">selected</c:if>>${genre.key}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Filter</button>
        </form>

        <!-- Movie Grid -->
        <div class="row">
            <c:forEach var="movie" items="${movies}" varStatus="status">
                <div class="col-md-3 mb-4">
                    <div class="card">
                        <img src="${movie.imageURL}" alt="${movie.title}" class="card-img-top">
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
                    </div><div class="row">
                </c:if>
            </c:forEach>
        </div>

        <!-- Pagination -->
        <c:if test="${totalPages > 1}">
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item <c:if test='${i == currentPage}'>active</c:if>'">
                            <a class="page-link" href="${pageContext.request.contextPath}/filter-movies?page=${i}&genre=${param.genre}">
                                ${i}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>
