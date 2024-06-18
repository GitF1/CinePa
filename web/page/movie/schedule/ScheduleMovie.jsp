    



<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.LocalDate"%>
<%@page import="model.schedule.DateInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ page import="model.CinemaChain" %>
<%@ page import="model.Cinema" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="com.google.gson.Gson" %>

<%@page import="model.schedule.Schedule"%>
<%@page import="DAO.schedule.ScheduleDAO"%>
<%@page import="model.schedule.CinemaMovieSlot"%>
<%@page import="model.MovieSlot"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%
    // Fetch movieID from request parameter
    int movieID = Integer.parseInt(request.getParameter("movieID"));
    String date = request.getParameter("date");

    ScheduleDAO scheduleDAO = new ScheduleDAO(getServletContext());
    Schedule schedule = (Schedule) session.getAttribute("schedule");
    String citySelected;

    List<String> cities = scheduleDAO.getListCites();
    if (schedule == null) {
        citySelected = cities.get(0);
    } else {
        citySelected = schedule.getCity();
        if (citySelected == null || citySelected.isEmpty()) {
            citySelected = cities.get(0);
        }
    }

    List<CinemaMovieSlot> cinemaMovieSlots = scheduleDAO.getCinemasShowingMovie(movieID, date, citySelected);

    // Generate week dates
    List<DateInfo> week = new DateInfo().generateWeek();

%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="/movie/components/schedule/styles/Header.css"/>
        <link rel="stylesheet" href="/movie/page/movie/schedule/styles/MovieSchedule.css"/>
        <style>

        </style>
    </head>
    <body>
        <div class="container-wrapper__movie-schedule">
            <div class="header-movie_schedule">
                <div class="header-title">Lịch chiếu </div>
                <div class="location-selector">
                    <div class="location-button selected" onclick="openModal()">
                        <span class="icon"><i class="fa-solid fa-location-dot"></i></span>
                        <span id="selected-city"><%= citySelected%></span>
                        <span class="icon">  <i class="fa-solid fa-caret-down"></i></span>

                    </div>
                    <div class="location-button">
                        <span class="icon"><i class="fa-solid fa-location-crosshairs"></i></span>
                        <span>Gần bạn</span>
                    </div>
                </div>
            </div>

            <!-- The Modal -->
            <div id="cityModal" class="container-modal-select">
                <div class="modal-content-box-select">
                    <div class="modal-header-box-select">
                        <h2>Chọn địa điểm</h2>
                        <span class="close" onclick="closeModal()"><i class="fa-solid fa-circle-xmark"></i></span>
                    </div>
                    <div class="modal-body-box-select">
                        <div class="city-list">
                            <%
                                for (String city : cities) {
                            %>
                            <div class="city <%= city.equals(citySelected) ? "selected" : ""%>" onclick="selectCity('<%= city%>')"><%= city%></div>
                            <%
                                }
                            %>
                        </div>
                    </div>
                    <div class="modal-footer-box-select">
                        <button class="button-close" onclick="closeModal()">Đóng</button>
                    </div>
                </div>
            </div>


            <div class="date-selector">
                <div class="dates" id="dates-container">
                    <%
                        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
                        for (DateInfo dateInfo : week) {
                            String dateString = dateInfo.getTime();
                            if (dateString.equals(sdfInput.format(new Date()))) {
                    %>
                    <div class="date-box selected" onclick="selectDate('<%= dateString%>', event)">
                        <div class="date__header-movie-schedule"><strong><%= dateInfo.getDate()%></strong></div>
                        <div class="date-of-week__header-movie-schedule"><%= dateInfo.getDayOfWeek()%></div>
                    </div>
                    <%
                    } else {
                    %>
                    <div class="date-box" onclick="selectDate('<%= dateString%>', event)">
                        <div class="date__header-movie-schedule"><strong><%= dateInfo.getDate()%></strong></div>
                        <div class="date-of-week__header-movie-schedule"><%= dateInfo.getDayOfWeek()%></div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
            </div>

            <div id="container-component-scheule-movie" class="container-movie_schedule">

                <% for (CinemaMovieSlot cinema : cinemaMovieSlots) {%>
                <div class="cinema">
                    <div class="cinema-header">
                        <img src="<%= cinema.getAvatar()%>" alt="Cinema Avatar" class="cinema-avatar"/>
                        <div class="cinema-info">
                            <h3><%= cinema.getName()%>  <%= cinema.getAddress()%></h3>
                            <div class="cine-info_adress">
                                <p><%= cinema.getDistrict()%>, <%= cinema.getCommune()%>, <%= cinema.getProvince()%></p>
                                <a href="https://www.google.com/maps/search/?api=1&query=<%= cinema.getAddress()%>,<%= cinema.getCommune()%>,<%= cinema.getDistrict()%>,<%= cinema.getProvince()%>" target="_blank">[Bản đồ]</a>
                            </div>

                        </div>
                    </div>
                    <div class="slots">
                        <%
                            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                            String lastType = "";
                            for (MovieSlot slot : cinema.getMovieSlots()) {
                                String type = slot.getType();
                                if (!type.equals(lastType)) {
                                    if (!lastType.isEmpty()) { %>
                    </div> 
                    <% }
                        lastType = type;%>
                    <h4><%= type%></h4>
                    <div class="slot-buttons">
                        <% }%>
                        <button class="slot" onclick="forwardToServlet('<%= slot.getMovieSlotID()%>')">
                            <strong>
                                <%= slot.getStartTime() != null ? slot.getStartTime().format(timeFormatter) : ""%>
                            </strong>
                            ~
                            <span><%= slot.getEndTime() != null ? slot.getEndTime().format(timeFormatter) : ""%></span>
                        </button>
                        <% }
                            if (!lastType.isEmpty()) { %>
                    </div> 
                    <% }
                    %>
                </div>
            </div>
            <% }%>
        </div>

    </div>



</body>

<script>
    function openModal() {
        document.getElementById("cityModal").style.display = "block";
    }

    function closeModal() {
        document.getElementById("cityModal").style.display = "none";
    }

    function selectCity(city) {
        var selectedCityElement = document.getElementById("selected-city");
        selectedCityElement.innerText = city;

        var cities = document.getElementsByClassName("city");
        for (var i = 0; i < cities.length; i++) {
            cities[i].classList.remove("selected");
        }

        event.target.classList.add("selected");
        closeModal();
    }

    window.onclick = function (event) {
        var modal = document.getElementById("cityModal");
        if (event.target == modal) {
            closeModal();
        }
    }
    function forwardToServlet(movieSlotID) {

        const form = document.createElement('form');
        form.method = 'GET';
        form.action = '/movie/user/booking/seat'; // Replace with your servlet URL

        const movieSlotInput = document.createElement('input');
        movieSlotInput.type = 'hidden';
        movieSlotInput.name = 'movieSlotID';
        movieSlotInput.value = movieSlotID;

        form.appendChild(movieSlotInput);
        document.body.appendChild(form);
        form.submit();
    }

</script>   
<script>
    function selectCity(city) {
//        document.getElementById('selected-city').innerText = city;
//        document.getElementById('cityModal').style.display = 'none';
        // Update the UI to reflect the selected city
        var selectedCityElement = document.getElementById("selected-city");
        selectedCityElement.innerText = city;

        // Remove the "selected" class from all city elements
        var cities = document.getElementsByClassName("city");
        for (var i = 0; i < cities.length; i++) {
            cities[i].classList.remove("selected");
        }

        // Add the "selected" class to the clicked city element
        event.target.classList.add("selected");

        // Close the modal
        document.getElementById('cityModal').style.display = 'none';
        fetchData();
    }

    function selectDate(date, event) {
        document.querySelectorAll('.date-box').forEach(el => el.classList.remove('selected'));
        event.currentTarget.classList.add('selected');

        let dateInput = document.getElementById('date-select');
        if (!dateInput) {
            dateInput = document.createElement('input');
            dateInput.type = 'hidden';
            dateInput.id = 'date-select';
            document.body.appendChild(dateInput);
        }

        dateInput.value = date;

        fetchData();
    }


    function fetchData() {

        const movieID = '<%= movieID%>';
        const date = document.getElementById('date-select') ? document.getElementById('date-select').value : '';
        const city = document.getElementById('selected-city').innerText;

        console.log("movieID: ", movieID, "date select: ", date, "city :", city);

        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'schedule/movie', true);

        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const cinemaMovieSlots = JSON.parse(xhr.responseText);
                console.log("JSON data rs: ", cinemaMovieSlots);

                updateSchedule(cinemaMovieSlots);
            }
        };

        xhr.send("movieID=" + encodeURIComponent(movieID) + "&date=" + encodeURIComponent(date) + "&city=" + encodeURIComponent(city));

    }

    function updateSchedule(cinemaMovieSlots) {
        const scheduleContainer = document.getElementById('container-component-scheule-movie');
        scheduleContainer.innerHTML = '';

        cinemaMovieSlots.forEach(cinema => {
            const cinemaElement = document.createElement('div');
            cinemaElement.classList.add('cinema');

            const cinemaHeader = document.createElement('div');
            cinemaHeader.classList.add('cinema-header');

            const cinemaAvatar = document.createElement('img');
            cinemaAvatar.src = cinema.avatar;
            cinemaAvatar.alt = "Cinema Avatar";
            cinemaAvatar.classList.add('cinema-avatar');
            cinemaHeader.appendChild(cinemaAvatar);

            const cinemaInfo = document.createElement('div');
            cinemaInfo.classList.add('cinema-info');

            const cinemaNameAddress = document.createElement('h3');
            cinemaNameAddress.textContent = cinema.name + ' ' + cinema.address;
            cinemaInfo.appendChild(cinemaNameAddress);

            const cineInfoAdress = document.createElement('div');
            cineInfoAdress.classList.add('cine-info_adress');

            const cinemaLocation = document.createElement('p');
            cinemaLocation.textContent = cinema.district + ', ' + cinema.commune + ', ' + cinema.province;
            cineInfoAdress.appendChild(cinemaLocation);

            const mapLink = document.createElement('a');
            mapLink.href = 'https://www.google.com/maps/search/?api=1&query=' + cinema.address + ',' + cinema.commune + ',' + cinema.district + ',' + cinema.province;
            mapLink.textContent = '[Bản đồ]';
            mapLink.target = '_blank';
            cineInfoAdress.appendChild(mapLink);

            cinemaInfo.appendChild(cineInfoAdress);
            cinemaHeader.appendChild(cinemaInfo);
            cinemaElement.appendChild(cinemaHeader);

            const slotsContainer = document.createElement('div');
            slotsContainer.classList.add('slots');

            let lastType = '';
            let slotButtonsContainer;

            cinema.movieSlots.forEach(slot => {
                const type = slot.type;
                if (type !== lastType) {
                    if (lastType !== '') {
                        slotsContainer.appendChild(slotButtonsContainer);
                    }
                    const slotTypeHeader = document.createElement('h4');
                    slotTypeHeader.textContent = type;
                    slotsContainer.appendChild(slotTypeHeader);

                    slotButtonsContainer = document.createElement('div');
                    slotButtonsContainer.classList.add('slot-buttons');
                    lastType = type;
                }

                const slotButton = document.createElement('button');
                slotButton.classList.add('slot');
                slotButton.onclick = function () {
                    forwardToServlet(slot.movieSlotID);
                };
                slotButton.innerHTML = '<strong>' + slot.startTime + '</strong> ~ <span>' + slot.endTime + '</span>';
                slotButtonsContainer.appendChild(slotButton);
            });

            if (lastType !== '') {
                slotsContainer.appendChild(slotButtonsContainer);
            }

            cinemaElement.appendChild(slotsContainer);
            scheduleContainer.appendChild(cinemaElement);
        });
    }
</script>

</script>

</html>
