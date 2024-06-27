<%-- 
    Document   : CreateMovieForm
    Created on : Jun 6, 2024, 10:30:14 AM
    Author     : duyqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="DAO.MovieDAO" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <%@ page isELIgnored="false" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />
        <style>
            .fc .fc-button-primary:disabled {
                display: none;
            }
        </style>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src=" https://cdn.jsdelivr.net/npm/fullcalendar@6.1.14/index.global.min.js "></script>
        <script>
            var response;
            var calendar;
            var currDate;
            var calendarEl;
            var currMovie;
            var movieLength;
        </script>
    </head>
    <body>


        <div class="container">
            <button id="somebutton">press here</button>
            <h1 style="color: red;"><c:out value="${requestScope.message}"/></h1>
            <div id="somediv"></div>
            <form action="${pageContext.request.contextPath}/CreateMovieSlotServlet" method="post" >
                <!--implement sau-->
                <label for="cinema" class="form-label">Cinema - Choose 1</label>
                <select class="form-select" aria-label="" id="cinema" name="cinema">
                    <option selected value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                    <option value="4">Four</option>
                </select>
                <label for="room" class="form-label">Room - Choose 1</label>
                <select class="form-select" aria-label="" id="room" name="room">
                    <option selected value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                    <option value="4">Four</option>
                </select>
                <label for="movie" class="form-label">Phim - Choose 1</label>
                <select class="form-select" aria-label="" id="movie" name="movie">
                    <option selected value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                    <option value="4">Four</option>
                </select>
                <!-- datepicker -->
                <label for="date" class="form-label"
                       >Ngày</label
                >
                <input
                    type="date"
                    class="form-control"
                    id="date"
                    name="date"
                    placeholder="Nhập ngày"
                    required

                    />

                <label for="startTime" class="form-label"
                       >Thời gian bắt đầu</label
                >
                <input
                    type="time"
                    class="form-control"
                    id="startTime"
                    name="startTime"
                    placeholder="Nhập thời gian bắt đầu"
                    required
                    />
                <label for="endTime" class="form-label"
                       >Thời gian kết thúc</label
                >

                <input
                    readonly
                    type="time"
                    class="form-control"
                    id="endTime"
                    name="endTime"
                    placeholder="Thời gian kết thức"
                    required
                    />

                <label for="calendar" class="form-label">Lịch trình trong ngày</label>
                <br/>
                <!--CALENDAR-->
                <div class="card" >
                    <div class="card-body">
                        <div id='calendar'></div>
                    </div>
                </div>
                <label for="exampleFormControlInput1" class="form-label">
                    Giá vé</label
                >
                <input
                    type="number" 
                    step="0.01"
                    class="form-control"
                    id="price"
                    name="price"
                    placeholder="Nhập giá vé"
                    required
                    />

                <br/>
                <button type="submit" class="btn btn-danger" onclick="">
                    Tạo suất chiếu</button
                >
            </form>
        </div>
        <!--Popper-->
        <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"
        ></script>
        <!--Bootstrap js-->
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"
        ></script>
        <!--jquery-->
        <script
            src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha384-ZvpUoO/+P0o2QE0Wea1Ygq6hEENenOPJo7FfHRVN9cAo50LjMOhpS7CbRBVxnlNv"
            crossorigin="anonymous"
        ></script>




        <!--Calender js-->
        <script>
            document.getElementById('date').valueAsDate = new Date();
            currDate = document.getElementById('date').valueAsDate.getTime();


            document.addEventListener('DOMContentLoaded', function () {
                calendarEl = document.getElementById('calendar');

                calendar = new FullCalendar.Calendar(calendarEl, {
                    timeZone: 'UTC',
                    initialView: 'timeGridOneDay',
                    validRange: {
                        start: currDate,
                        end: currDate
                    },

                    views: {
                        timeGridOneDay: {
                            type: 'timeGrid',
                            duration: {days: 1},
                            buttonText: '1 day'
                        }
                    },
                    events: response,
                    aspectRatio: 2
                });

                calendar.render();
            });
        </script>
        <!--Update-->
        <script>
            const yourServletURL = "${pageContext.request.contextPath}/ScheduleServlet";
            console.log("dateobject");
            console.log(currDate);
            function updateCalendarCall() {
                currDate = document.getElementById('date').valueAsDate.getTime();
                $.post(yourServletURL,
                        {
                            date: currDate,

                        }, function (data, status) {
//                    alert("Data: " + data + "\nStatus: " + status);
                    response = JSON.parse(data);
                    calendar.removeAllEvents();

                    calendar.addEventSource(response);
                    calendar.refetchEvents();


                });
                calendarEl = document.getElementById('calendar');
                calendar = new FullCalendar.Calendar(calendarEl, {
                    timeZone: 'UTC',
                    initialView: 'timeGridOneDay',
                    validRange: {
                        start: currDate,
                        end: currDate
                    },
                    eventOverlap: true,
                    views: {
                        timeGridOneDay: {
                            type: 'timeGrid',
                            duration: {days: 1},
                            buttonText: '1 day'
                        }
                    },
                    events: response,
                    aspectRatio: 2
                });

                calendar.render();
            }

            function updateLengthCall() {
                const movieLengthServlet = "${pageContext.request.contextPath}/MovieLengthServlet";
                currMovie = document.getElementById('movie').value;
                $.post(movieLengthServlet,
                        {
                            movieID: currMovie,

                        }, function (data, status) {
                    movieLength = JSON.parse(data);
                    var temp = new Date();
                    temp.setUTCHours(0, 0, 0, 0);
                    temp = new Date(temp.getTime() - (movieLength+1) * 60000 + temp.getTimezoneOffset() * 60000);
                    console.log(temp);
                    $.post(yourServletURL,
                            {
                                date: currDate,

                            }, function (data2, status2) {
//                    alert("Data: " + data + "\nStatus: " + status);
                        response = JSON.parse(data2);


                    });
                    console.log(temp.getHours() + ":" + temp.getMinutes());
                    document.getElementById("startTime").max = temp.getHours() + ":" + temp.getMinutes();
                    console.log("--");
                    console.log(document.getElementById("date").value);
                    console.log(document.getElementById("startTime").value);
                    console.log("--");


                    document.getElementById("endTime").valueAsDate = new Date((document.getElementById("startTime").valueAsDate).getTime() + movieLength * 60000);
                    var activity = {
                        "title": "NEW",
                        "start": document.getElementById("date").value + "T" + document.getElementById("startTime").value + ":00.000+0000",
                        "end": document.getElementById("date").value + "T" + document.getElementById("endTime").value + ":00.000+0000",
                        "color": "rgb(220,53,69)"
                    };
                    response.push(activity);
                    console.log(response);
                    calendar.removeAllEvents();

                    calendar.addEventSource(response);
                    calendar.refetchEvents();
//                    alert("Data: " + JSON.parse(data) + "\nStatus: " + status);
//                    response = JSON.parse(data);



                });




            }




            updateCalendarCall();
            document.getElementById('date').addEventListener('change', function () {
                updateCalendarCall();
            });
            document.getElementById('startTime').addEventListener('change', function () {
                updateLengthCall();
            });





        </script>
    </body>
</html>
