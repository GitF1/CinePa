


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ page import="model.CinemaChain" %>
<%@ page import="model.Cinema" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>

<%@page import="model.schedule.Schedule"%>
<%@page import="DAO.schedule.ScheduleDAO"%>
<%@page import="model.schedule.CinemaMovieSlot"%>
<%@page import="model.MovieSlot"%>

<%

    //HttpSession session = request.getSession();
    Schedule schedule = (Schedule) session.getAttribute("schedule");
    // Fetch cities and cinema chains from the database
    ScheduleDAO scheduleDAO = new ScheduleDAO(getServletContext());
    List<String> cities = scheduleDAO.getListCites();
    List<CinemaChain> cinemaChains = scheduleDAO.getListCinemaChain(10, 0);
    //

    // Get selected city and movie
    String selectedCity = schedule != null && schedule.getCity() != null ? schedule.getCity() : cities.get(0);

    int selectedCinemaChain = request.getParameter("cinemaChainID") != null ? Integer.parseInt(request.getParameter("cinemaChainID")) : 0;
    int movieID = Integer.parseInt(request.getParameter("movieID"));

    // Fetch cinemas in the selected city
    //List<Cinema> cinemas = scheduleDAO.getListCinema(selectedCity, selectedCinemaChain, 10, 0);
    // Prepare a map of cinema slots by type
    
    
    List<CinemaMovieSlot> cinemaMovieSlots = scheduleDAO.getMovieSlotsByMovieID(movieID);
    Map<Integer, Map<String, List<MovieSlot>>> cinemaSlotsMap = new HashMap<>();
    for (CinemaMovieSlot cinema : cinemaMovieSlots) {
        Map<String, List<MovieSlot>> slotsByType = new HashMap<>();
        for (MovieSlot slot : cinema.getMovieSlots()) {
            String type = slot.getType();
            slotsByType.computeIfAbsent(type, k -> new ArrayList<>()).add(slot);
        }
        cinemaSlotsMap.put(cinema.getCinemaID(), slotsByType);
    }

    // Set attributes to be accessed in EL
    request.setAttribute("cinemaMovieSlots", cinemaMovieSlots);
    request.setAttribute("cinemaSlotsMap", cinemaSlotsMap);


%>

<!DOCTYPE html>
<html>
    <head>
        <title>Movie Schedule</title>
        <link rel="stylesheet" href="MovieSchedule.css">


    </style>
</head>
<body>
    <h1>Movie Schedule</h1>

    <form action="/schedule/movie" method="POST">

        <!--            <label for="city">Select City:</label>-->

        <select id="city" name="city">
            <c:forEach var="city" items="${cities}">
                <option value="${city}" ${city == selectedCity ? 'selected' : ''}>${city}</option>
            </c:forEach>
        </select>

        <label for="cinemaChain">Select Cinema Chain:</label>
        <select id="cinemaChain" name="cinemaChainID">
            <option value="0">All</option>
            <c:forEach var="chain" items="${cinemaChains}">
                <option value="${chain.cinemaChainID}" ${chain.cinemaChainID == selectedCinemaChain ? 'selected' : ''}>${chain.name}</option>
            </c:forEach>
        </select>

        <label for="movie">Movie:</label>
        <input type="text" id="movie" name="movie" value="${movie}" required />

    </form>

    <h2>Schedule for "${movie}" in ${selectedCity}</h2>


    <div class="container-movie_schedule">
        <% for (CinemaMovieSlot cinema : cinemaMovieSlots) {%>
        <div class="cinema">
            <div class="cinema-header">
                <img src="<%= cinema.getAvatar()%>" alt="Cinema Avatar" class="cinema-avatar"/>
                <div class="cinema-info">
                    <h3><%= cinema.getAddress()%></h3>
                    <p><%= cinema.getDistrict()%>, <%= cinema.getCommune()%>, <%= cinema.getProvince()%></p>
                    <a href="https://www.google.com/maps/search/?api=1&query=<%= cinema.getAddress()%>" target="_blank">[Bản đồ]</a>
                </div>
            </div>
            <div class="slots">
                <% Map<String, List<MovieSlot>> slotsByType = (Map<String, List<MovieSlot>>) request.getAttribute("cinemaSlotsMap").; %>
                <% for (String type : slotsByType.keySet()) {%>
                <h4><%= type%></h4>
                <div class="slot-buttons">
                    <% for (MovieSlot slot : slotsByType.get(type)) {%>
                    <button class="slot">
                        <%= slot.getStartTime()%> ~ <%= slot.getEndTime()%>
                    </button>
                    <% } %>
                </div>
                <% } %>
            </div>
        </div>
        <% }%>
    </div>

</body>
</html>
