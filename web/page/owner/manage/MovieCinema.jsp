<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý phim cho rạp chiếu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }
        h1, h2, h3 {
            text-align: center;
            color: #333;
        }
        form {
            margin: 20px 0;
            text-align: center;
        }
        select, input[type="submit"] {
            padding: 10px;
            margin: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
    </style>
    <script>
        function confirmDelete(event) {
            var confirmed = confirm("Bạn có chắc chắn muốn xóa phim này?");
            if (!confirmed) {
                event.preventDefault();
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
            <select name="movieID">
                <c:forEach var="movie" items="${movies}">
                    <option value="${movie.movieID}">${movie.title}</option>
                </c:forEach>
            </select>
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
                                <td>${movie.title}</td>
                                <td>${movie.country}</td>
                            </c:if>
                        </c:forEach>
                        <td>${movieCinema.status}</td>
                        <td>
                            <form action="deleteMovieCinema" method="post" style="display:inline;" onsubmit="confirmDelete(event)">
                                <input type="hidden" name="cinemaID" value="${movieCinema.cinemaID}">
                                <input type="hidden" name="movieID" value="${movieCinema.movieID}">
                                <input type="submit" value="Delete">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
