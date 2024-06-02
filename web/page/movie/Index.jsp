<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movie List</title>
    <style>
     
        .titlee {
            text-align: center;
            font-size: 28px; /* Kích thước chữ */
            color: #333; /* Màu chữ */
            font-weight: bold; /* Đậm */
            text-transform: uppercase; /* Chuyển đổi thành chữ in hoa */
            letter-spacing: 2px; /* Khoảng cách giữa các ký tự */
            margin: 59px 0;
        }
    </style>
</head>
<body>
    <div>
        <h1 class="titlee">Phim đang chiếu</h1>
        <jsp:include page="/page/movie/MovieListComponents.jsp">
            <jsp:param name="status" value="Showing"/>
        </jsp:include>
    </div>

    <div>
        <h1 class="titlee">Phim sắp chiếu</h1>
        <jsp:include page="/page/movie/MovieListComponents.jsp">
            <jsp:param name="status" value="Coming"/>
        </jsp:include>
    </div>
</body>
</html>