
<%@ page import="java.util.List" %>
<%@ page import="model.Movie" %>
<%@ page import="model.MovieSlot" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <style>
            .container-time_schedule{
                display: flex;
                align-items: center;
                margin: 25px 10px;
            }
            .thumb-movie{
                height: 150px;
                width: 130px;
                border-radius: 10px;
                cursor: pointer;
            }

            .list-time_showing{
                display: grid;
                grid-template-columns: repeat(3, minmax(0, 1fr));
                gap: .75rem;
            }
            .time-item{
                border:1px solid #0ea5e9;
                padding: 10px;
                margin: 0 10px;
                border-radius: 8px;
                cursor: pointer;
                color: #0ea5e9;
            }
            .title-movie{
                line-height: 1.25;
                font-weight: 600;
                font-size: 1.2rem;
                text-decoration: none;
                outline: none;
                margin: 10px;
                color: #262626;
            }
            .right-time_schedule{
                margin: 0 45px;
            }
            .strong-start_time{
                font-weight: 600;
            }

        </style>
    </head>
    <body>
        <c:if test="${empty listMovie}">
            <jsp:include page="./error/NotFoundMovie.jsp" />
        </c:if>
        <c:forEach items="${listMovie}" var="movie">
            <div class="container-time_schedule">
                <div class="left-time_schedule">
                    <img class="thumb-movie" src="${movie.imageURL}" alt="${movie.getTitle()} Poster">
                </div>
                <div class="right-time_schedule">
                    <a href="/movie/HandleDisplayMovieInfo?movieID=${movie.getMovieID()}" class="title-movie">${movie.title}</a>
                    
                    <div>${movie.getCountry()}</div>
                   
                    <div class="list-time_showing">
                        <c:forEach items="${movie.listMovieSlot}" var="slot">
                            <div onclick="onSelectSchedule('${slot.getMovieSlotID()}')" class="time-item"><strong class="strong-start_time"> ${slot.startTime.format(DateTimeFormatter.ofPattern('HH:mm'))} ~</strong>  ${slot.endTime.format(DateTimeFormatter.ofPattern('HH:mm'))}</div>
                        </c:forEach>
                    </div>
                    
                </div>

            </div>

        </c:forEach>
    </body>
    <script>


        function onSelectSchedule(movieSlotID) {

            var servletURL = '/movie/user/booking/seat?movieSlotID=' + movieSlotID;

            // Redirect to the servlet
            window.location.href = servletURL;
        }


    </script>
</html>
