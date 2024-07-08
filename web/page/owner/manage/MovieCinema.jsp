<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý phim cho rạp chiếu</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f0f2f5;
            }
            .container {
                width: 90%;
                max-width: 1200px;
                margin: 40px auto;
                background: #fff;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            h1, h2, h3 {
                text-align: center;
                color: #333;
                margin: 20px 0;
            }
            form {
                display: flex;
                justify-content: center;
                align-items: center;
                flex-wrap: wrap;
                gap: 10px;
                margin-bottom: 30px;
            }
            select, input[type="submit"], button {
                padding: 10px;
                margin: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer; /* Thêm thuộc tính cursor */
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
                font-size: 16px;
            }
            table, th, td {
                border: 1px solid #ddd;
            }
            th, td {
                padding: 15px;
                text-align: left;
            }
            th {
                background-color: #007bff;
                color: #fff;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            tr:hover {
                background-color: #f1f1f1;
            }
            .button-delete {
                background-color: #dc3545;
                color: white;
                border: none;
                padding: 10px 20px;
                cursor: pointer; /* Thêm thuộc tính cursor */
                border-radius: 5px;
                font-size: 16px;
            }
            .button-delete:hover {
                background-color: #c82333;
            }
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgb(0, 0, 0);
                background-color: rgba(0, 0, 0, 0.4);
            }
            .modal-content {
                background-color: #fefefe;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                border-radius: 8px;
            }
            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer; /* Thêm thuộc tính cursor */
            }
            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }
            @media (max-width: 768px) {
                form {
                    flex-direction: column;
                    gap: 5px;
                }
                select, input[type="submit"], button {
                    width: 100%;
                    margin: 5px 0;
                }
                table, th, td {
                    font-size: 14px;
                    padding: 10px;
                }
            }
            .movie-img {
                width: 50px;
                height: auto;
                border-radius: 5px;
            }
        </style>
        <script>
            function confirmDelete(event) {
                var confirmed = confirm("Bạn có chắc chắn muốn xóa phim này?");
                if (!confirmed) {
                    event.preventDefault();
                }
            }

            function openModal() {
                document.getElementById('movieModal').style.display = 'block';
            }

            function closeModal() {
                document.getElementById('movieModal').style.display = 'none';
            }

            function updateSelectedMovies() {
                var selectedMovies = document.querySelectorAll('input[name="selectedMovies"]:checked');
                var movieTitles = [];
                var movieIDs = [];
                selectedMovies.forEach(function (movie) {
                    movieIDs.push(movie.value);
                    movieTitles.push(movie.dataset.title);
                });
                document.getElementById('selectedMovieIDs').value = movieIDs.join(',');
                document.getElementById('selectedMovieTitles').innerText = movieTitles.join(', ');
            }

            window.onclick = function (event) {
                var modal = document.getElementById('movieModal');
                if (event.target == modal) {
                    modal.style.display = 'none';
                }
            }
        </script>
    </head>
    <body>
        <div class="container">
            <h1>Quản lý phim cho rạp chiếu</h1>
            <h2>Rạp: ${cinemaID}</h2>

            <h3>Chọn phim</h3>
            <form action="movieCinema" method="post">
                <input type="hidden" name="cinemaID" value="${cinemaID}">
                <input type="hidden" id="selectedMovieIDs" name="movieIDs" value="">
                <button type="button" onclick="openModal()">Chọn phim</button>
                <span id="selectedMovieTitles">Không có phim nào được chọn</span>
                <label for="status">Status:</label>
                <select name="status" id="status">
                    <option value="SHOWING">SHOWING</option>
                    <option value="COMING">COMING</option>
                </select>
                <input type="submit" value="Thêm vào rạp">
            </form>

            <h3>Danh sách phim trong rạp</h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Rạp Phim</th>
                        <th>Image</th>
                        <th>Title</th>
                        <th>Country</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="movieCinema" items="${movieCinemas}">
                        <tr>
                            <td>${movieCinema.movieID}</td>
                            <td>${movieCinema.cinemaID}</td>
                            <c:forEach var="movie" items="${movies}">
                                <c:if test="${movie.movieID == movieCinema.movieID}">
                                    <td><img src="${movie.imageURL}" alt="${movie.title}" class="movie-img"></td>
                                    <td>${movie.title}</td>
                                    <td>${movie.country}</td>
                                </c:if>
                            </c:forEach>
                            <td>${movieCinema.status}</td>
                            <td>
                                <form action="deleteMovieCinema" method="post" style="display:inline;" onsubmit="confirmDelete(event)">
                                    <input type="hidden" name="cinemaID" value="${movieCinema.cinemaID}">
                                    <input type="hidden" name="movieID" value="${movieCinema.movieID}">
                                    <input type="submit" value="Delete" class="button-delete">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- The Modal -->
        <div id="movieModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h2>Chọn phim</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Image</th>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Country</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="movie" items="${movies}">
                            <tr>
                                <td><img src="${movie.imageURL}" alt="${movie.title}" class="movie-img"></td>
                                <td>${movie.movieID}</td>
                                <td>${movie.title}</td>
                                <td>${movie.country}</td>
                                <td>
                                    <input type="checkbox" name="selectedMovies" value="${movie.movieID}" data-title="${movie.title}" onclick="updateSelectedMovies()">
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button type="button" onclick="closeModal()">Đóng</button>
            </div>
        </div>
    </body>
</html>
