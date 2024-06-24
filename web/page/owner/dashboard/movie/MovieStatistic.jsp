<%-- 
    Document   : Dashboard
    Created on : Jun 19, 2024, 10:11:48 PM
    Author     : PC
--%>

<%@page import="com.google.gson.Gson"%>
<%@page import="util.ChartUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dash Board</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .main-dasboard{
                background-color: #f3f3f3;
                padding:10px 20px;
            }
            .wrapper-chart{
                width: 98% !important;
                padding: 20px;
                border-radius: 6px;
                background-color: #fff;
                font-size: 0.875em;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

            }
        </style>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">

                <nav class="col-md-2 col-lg-2 d-md-block sidebar position-fixed top-0 left-0 bottom-0 ">
                    <jsp:include page=".././components/Navigate.jsp"/>
                </nav>

                <main role="main" class="col-md-10 ml-sm-auto col-lg-10 p-0 m-0 ">
                    <div class="header_dashboard">
                        <jsp:include page="../.././component/Header.jsp"/>
                    </div>
                    <div class="header_dashboard">

                    </div>
                    <div class="main-dasboard ">
                        <jsp:include page="components/SelectMovie.jsp"/>
                        <div class="row">
                            <jsp:include page="components/ChartMovies.jsp"/>
                        </div>
                    </div>
            </div>
        </div>

    </main>
</div>

</div>




</body>

</html>
