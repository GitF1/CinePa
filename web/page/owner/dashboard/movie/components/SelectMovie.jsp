<%@page import="java.util.Calendar"%>
<%@page import="util.RouterURL"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
      

        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <style>
            @keyframes goToTop {
                from {
                    transform: translateY(-100%);
                    opacity: 0;
                }
                to {
                    transform: translateY(0);
                    opacity: 1;
                }
            }

            /* General Modal Styles */
            .container-modal-select {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgb(0, 0, 0);
                background-color: rgba(0, 0, 0, 0.4);
            }

            .modal-content-box-select {
                background-color: #fefefe;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 600px;
                border-radius: 10px;
                transform: translateY(-100%);
                opacity: 0;
                animation: goToTop 0.5s forwards;
            }

            .modal-header-box-select {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 1px solid #ddd;
                padding-bottom: 10px;
            }

            .modal-body-box-select {
                margin: 20px 0;
            }

            .modal-footer-box-select {
                display: flex;
                flex-direction: row-reverse;
            }

            /* Specific Styles for Movie Selection */
            .movie-list {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                justify-content: center;
            }

            .movie {
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                cursor: pointer;
                text-align: center;
            }

            .movie img {
                max-width: 100%;
                height: auto;
                display: block;
                margin: 0 auto 10px;
            }

            .movie.selected {
                border-color: #ff69b4;
                color: #ff69b4;
            }

            .close {
                color: #dadada;
                float: right;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer;
            }

            .close:hover,
            .close:focus {
                color: #ccc;
                text-decoration: none;
                cursor: pointer;
            }

            .button-close {
                padding: 10px 20px;
                margin: 20px 10px;
                background-color: #ff69b4;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
            }
        </style>
    </head>

    <%

        String title = (String) request.getAttribute("movieTitle");
        String month = (String) request.getAttribute("month");
        String year = (String) request.getAttribute("year");
        Integer movieID = (Integer) request.getAttribute("movieID");

        // Check if month is null or empty
        if (month == null || month.isEmpty()) {
            // Get current month (1-based index, January is 1)
            Calendar now = Calendar.getInstance();
            int currentMonth = now.get(Calendar.MONTH) + 1; // Adding 1 because Calendar.MONTH is zero-based
            month = String.valueOf(currentMonth);
        }

// Check if year is null or empty
        if (year == null || year.isEmpty()) {
            // Get current year
            Calendar now = Calendar.getInstance();
            int currentYear = now.get(Calendar.YEAR);
            year = String.valueOf(currentYear);
        }

    %>

    <body>
        <!-- Button to open the modal -->
        <!-- Button to open the modal -->
        <button onclick="openMovieModal()"> <%= title%> </button>
        <div id="selected-movie"></div>
        <div>
            <label for="month-select">Select Month:</label>
            <select  <% if (movieID == null) { %> 
                disabled 
                <% }%>  id="month-select" onchange="submitFormOnSelectChange()"></select>
        </div>
        <div>
            <label for="year-select">Select Year:</label>
            <select <% if (movieID == null ) { %> 
                disabled 
                <% }%> id="year-select" onchange="submitFormOnSelectChange()"></select>
        </div>
        <!-- The Modal -->
        <div id="movieModal" class="container-modal-select">
            <div class="modal-content-box-select">
                <div class="modal-header-box-select">
                    <h2>Select a Movie</h2>
                    <span class="close" onclick="closeMovieModal()"><i class="fa-solid fa-circle-xmark"></i></span>
                </div>
                <div class="modal-body-box-select">
                    <div class="movie-list">
                        <c:forEach var="movie" items="${movies}">
                            <form class="movie-form-${movie.movieID}" action="<%= RouterURL.OWNER_MOVIES_STATISTIC%>" method="POST">

                                <input type="hidden" name="movieID" value="${movie.getMovieID()}" />
                                <input type="hidden" name="movieTitle" value="${movie.getTitle()}" />
                                <input type="hidden" name="month" />
                                <input type="hidden" name="year" />

                                <div class="movie" type="button" onclick="submitMovieForm('${movie.movieID}')">
                                    <img src="${movie.imageURL}" alt="${movie.title}" width="50" height="75"/>
                                    <span>${movie.title}</span>
                                </div>

                            </form>
                        </c:forEach>
                    </div>

                </div>
                <div class="modal-footer-box-select">
                    <button class="button-close" onclick="closeMovieModal()">Close</button>
                </div>
            </div>
        </div>

        <script>


            function openMovieModal() {
                populateMonthDropdown();
                populateYearDropdown();
                document.getElementById("movieModal").style.display = "block";
            }

            function closeMovieModal() {
                document.getElementById("movieModal").style.display = "none";
            }

            function submitFormOnSelectChange() {
                const movieID = <%= movieID%>;
                
                if (movieID === null) {
                    alert("Choose One Movie!");
                    return;
                }

                return submitMovieForm(movieID);
            }

            function submitMovieForm(movieID) {

                var month = document.getElementById("month-select").value;
                var year = document.getElementById("year-select").value;


                // Get the form for the specified movieID
                var form = document.querySelector(".movie-form-" + movieID);

                // Set the month and year values in the form
                form.querySelector('input[name="month"]').value = month;
                form.querySelector('input[name="year"]').value = year;

                console.log("Form Movie ID:", form.querySelector('input[name="movieID"]').value);
                console.log("Form Movie Title:", form.querySelector('input[name="movieTitle"]').value);
                console.log("Form Month:", form.querySelector('input[name="month"]').value);
                console.log("Form Year:", form.querySelector('input[name="year"]').value);

                // Submit the form
                form.submit();

            }

            window.onclick = function (event) {
                var modal = document.getElementById("movieModal");
                if (event.target == modal) {
                    closeMovieModal();
                }
            };

            function populateMonthDropdown() {
                const monthSelect = document.getElementById("month-select");
                const months = [
                    {value: "1", name: "January"},
                    {value: "2", name: "February"},
                    {value: "3", name: "March"},
                    {value: "4", name: "April"},
                    {value: "5", name: "May"},
                    {value: "6", name: "June"},
                    {value: "7", name: "July"},
                    {value: "8", name: "August"},
                    {value: "9", name: "September"},
                    {value: "10", name: "October"},
                    {value: "11", name: "November"},
                    {value: "12", name: "December"}
                ];

                const currentMonth = new Date().getMonth() + 1;
                const currentMonthValue = '<%= month%>';
                monthSelect.innerHTML = months.map(month =>
                    '<option value="' + month.value + '"' + (month.value === currentMonthValue ? ' selected' : '') + '>' + month.name + '</option>'
                ).join('');
            }

            function populateYearDropdown() {
                const yearSelect = document.getElementById("year-select");
                const currentYear = new Date().getFullYear();
                const years = Array.from({length: 3}, (_, i) => currentYear - i);

                const currentYearSelect = <%= year %>;

                yearSelect.innerHTML = years.map(year =>
                    '<option value="' + year + '"' + (year === currentYearSelect.toString() ? ' selected' : '') + '>' + year + '</option>'
                ).join('');
            }
            window.onload = () => {
                populateMonthDropdown();
                populateYearDropdown();
            };
        </script>
    </body>
</html>
