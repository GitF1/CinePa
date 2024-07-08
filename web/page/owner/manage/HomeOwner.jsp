<%@page import="model.CinemaChain"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Home Page - Cinema Owner</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <style>
            .hide-if-null {
                display: none; /* Ẩn phần tử khi dùng class này */
            }

            .h1, h1 {
                text-align: center;
                padding-top: 20px;
                padding-bottom: 10px;
            }
            p {
                text-align: center;
                margin-top: 0;
                margin-bottom: 1rem;
                font-weight: 200;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Owner Management</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item ${empty cinemaChain ? 'hide-if-null' : ''}">
                        <a class="nav-link" href="${pageContext.request.contextPath}/owner/cinemaChain">Manage Cinema Chain</a>
                    </li>
                    <li class="nav-item">
                    <c:choose>
                        <c:when test="${cinemaChain == null}">
                            <a class="nav-link" href="${pageContext.request.contextPath}/owner/createCinemaChain">Create Cinema Chain</a>
                        </c:when>
                    </c:choose>
                    </li>
                    <li class="nav-item">
                        <!--em không hiểu flow nên để tạm movie slot ở đây- DuyND-->
                        <a class="nav-link" href="<%= request.getContextPath()%>/CreateMovieSlotFormInfoServlet">Create Movie Slot</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/page/owner/createMovie/CreateMovieRequest.jsp">Create Movie Request</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/owner/viewmovierequests">View Movie Request</a>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container">
            <h1>Welcome to the Cinema Management System</h1>
            <p>Use the navigation bar to manage or create your cinema chain.</p>

            <div>
                <h1 class="titlee">Current Movies</h1>
                <jsp:include page="/page/movie/MovieListComponents.jsp">
                    <jsp:param name="status" value="Showing"/>
                </jsp:include>
            </div>

            <div>
                <h1 class="titlee">Upcoming Movies</h1>
                <jsp:include page="/page/movie/MovieListComponents.jsp">
                    <jsp:param name="status" value="Coming"/>
                </jsp:include>
            </div>
        </div>
    </body>
</html>
