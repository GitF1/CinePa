<%-- 
    Document   : Header
    Created on : Jun 13, 2024, 3:55:16 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <style>
            .container-header-owner{

                border-bottom: 1px solid #eee;
                margin-bottom: 20px;
                box-shadow: 1px 1px 20px 1px rgba(0,0,0,0.1);
            }
            .navbar-brand{
                margin-left: 20px;
            }
        </style>
    </head>
    <body>
        <nav class=" container-header-owner navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Owner Management</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath()%>/owner/cinemaChain">Manage Cinema Chain</a>
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
    </body>
</html>
