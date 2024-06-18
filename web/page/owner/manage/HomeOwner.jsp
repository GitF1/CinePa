<%@page import="model.User"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Home Page - Cinema Owner</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Owner Management</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath()%>/owner/cinemaChain">Manage Cinema Chain</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath()%>/owner/createCinemaChain">Create Cinema Chain</a>
                    </li>
                </ul>
                <!-- Add this part for logout functionality -->
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath()%>/logout">Đăng Xuất</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container">
            <h1>Welcome to the Cinema Management System</h1>
            <p>Use the navigation bar to manage or create your cinema chain.</p>
            
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
        </div>
        
    </body>
</html>
