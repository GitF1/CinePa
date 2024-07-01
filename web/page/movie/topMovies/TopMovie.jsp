<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Top 10 Movies</title>
        <style>

            h1 {
                text-align: center;
                color: #2c2c2c;
                margin-bottom: 30px;
            }

            .movies-list {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 10px; /* Giảm khoảng cách giữa các mục */
            }

            .movie-item {
                background-color: #2c2c2c;
                border-radius: 10px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
                overflow: hidden;
                width: 22%; /* Giảm chiều rộng để làm mục nhỏ hơn */
                display: flex;
                flex-direction: column;
                margin-bottom: 10px;
                max-width: 300px; /* Đảm bảo không vượt quá chiều rộng tối đa */
            }

            .movie-item .movie-content {
                display: flex;
                flex-direction: column;
                padding: 10px; /* Giảm padding để giảm kích thước */
                width: 100%;
            }

            .movie-item img {
                width: 100%;
                height: 180px; /* Đặt chiều cao cố định cho hình ảnh */
                object-fit: cover; /* Đảm bảo hình ảnh vừa vặn trong khung */
            }

            .movie-details {
                display: flex;
                flex-direction: column;
                padding-top: 8px; /* Giảm padding */
            }

            .movie-details h2 {
                margin: 0;
                color: #fff;
                font-size: 0.9em; /* Giảm kích thước font */
            }

            .movie-details p {
                margin: 4px 0;
                color: #bbb;
                font-size: 0.8em; /* Giảm kích thước font */
            }

            .view-more {
                margin-top: 8px;
                text-decoration: none;
                color: #ff6f61;
                font-weight: bold;
                font-size: 0.8em; /* Giảm kích thước font */
            }

            .movie-item .rank {
                background-color: #ff6f61;
                color: #fff;
                font-size: 12px; /* Giảm kích thước font */
                font-weight: bold;
                padding: 5px; /* Giảm padding */
                text-align: center;
                position: absolute;
                top: 10px;
                left: 10px;
                border-radius: 50%;
                width: 25px; /* Giảm kích thước width */
                height: 25px; /* Giảm kích thước height */
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .movie-item .movie-image {
                position: relative;
            }

            .movie-item .movie-content img {
                border-radius: 10px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/page/landingPage/Banner.jsp" />

        
        <h1>Top 12 Movies Popular</h1>
        <div class="movies-list">
            <c:forEach var="movie" items="${topMovies}" varStatus="status">
                <div class="movie-item">
                    <div class="movie-image">
                        <div class="rank">${status.index + 1}</div>
                        <img src="${movie.imageURL}" alt="${movie.title}">
                    </div>
                    <div class="movie-content">
                        <div class="movie-details">
                            <h2>${movie.title}</h2>
                            <p>${movie.synopsis}</p>
                            <p><strong>Rating:</strong> ${movie.rating} ⭐</p>
                            <p><strong>Published Date:</strong> ${movie.datePublished}</p>
                            <p><strong>Country:</strong> ${movie.country}</p>
                            <!-- Nút "View More" với đường dẫn mới -->
                            <a href="${pageContext.request.contextPath}/HandleDisplayMovieInfo?movieID=${movie.movieID}" class="view-more">View More</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
                <jsp:include page="/page/home/Footer.jsp" />

    </body>
</html>
