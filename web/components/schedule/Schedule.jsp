

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Schedule</title>
        <style>
            .body_schedule{
                display: flex;
                margin: 40px 10px;
            }
        </style>
    </head>


    <body>
        <jsp:include page="../.././page/landingPage/Header.jsp" />
        <jsp:include page="./components/Header.jsp" />
        <jsp:include page="./components/ListMovieTheater.jsp" />
        
    <c:choose>
        <c:when test="${empty listCinemaBranch}">
            <jsp:include page="./components/error/NotFoundCinema.jsp" />
        </c:when>
        <c:otherwise>
            <div class="body_schedule">
                <jsp:include page="./components/ListCinema.jsp" />
                <div>
                    <jsp:include page="./components/DateOfWeek.jsp" />
                    <jsp:include page="./components/ListMovie.jsp" />
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>
