<%-- 
    Document   : CinemaDetail
    Created on : Jun 22, 2024, 3:54:31 PM
    Author     : VINHNQ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .h1, h1 {
                text-align: center;
                padding-top: 20px;
                padding-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="../landingPage/Header.jsp" />
        
        <jsp:include page="Banner.jsp" />
        

        <div>
            <h1 class="titlee">Phim đang chiếu</h1>
            <jsp:include page="/page/movie/MovieListComponents.jsp">
                <jsp:param name="status" value="Showing"/>
            </jsp:include>
        </div>

        <jsp:include page="../landingPage/Question.jsp" />
        
        <jsp:include page="../home/Footer.jsp" />

        <!--phan Quang VInh :--> 
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>

    </body>
</html>
