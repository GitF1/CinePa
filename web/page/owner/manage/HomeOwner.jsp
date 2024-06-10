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
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/owner/cinemaChain">Manage Cinema Chain</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/owner/createCinemaChain">Create Cinema Chain</a>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <h1>Welcome to the Cinema Management System</h1>
        <p>Use the navigation bar to manage or create your cinema chain.</p>
    </div>
</body>
</html>
