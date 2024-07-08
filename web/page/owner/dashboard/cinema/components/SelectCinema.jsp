<%@page import="java.util.List"%>
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
                margin: 2% auto;
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
                max-height: 60vh;
                overflow-y: scroll;
            }

            .modal-footer-box-select {
                display: flex;
                flex-direction: row-reverse;

            }

            /* Specific Styles for Movie Selection */
            .cinema-list {
                display: flex;
                gap: 10px;
                justify-content: center;
                align-items: center;
                flex-wrap: nowrap;
                flex-direction: column;
            }
            .cinema-form-item{
                width: 80%;

            }
            .cinema {
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                cursor: pointer;
                text-align: center;
            }

            .cinema img {
                max-width: 100%;
                height: auto;
                display: block;
                margin: 0 auto 10px;
            }

            .cinema.selected {
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
            .img-select-cinema{
                width: 60px;
                height:
                    60px;
                border-radius: 10px;
                box-shadow: 1px, 6px 3px 8px rgba(0,0,0,0,2);
            }

        </style>
    </head>

    <%

        String month = (String) request.getAttribute("month");
        String year = (String) request.getAttribute("year");
        List<Integer> listCinemaID = (List<Integer>) request.getAttribute("listCinemaID");

        // Check if month is null or empty
        if (month == null || month.isEmpty()) {

            Calendar now = Calendar.getInstance();
            int currentMonth = now.get(Calendar.MONTH) + 1;
            month = String.valueOf(currentMonth);
        }

        // Check if year is null or empty
        if (year == null || year.isEmpty()) {

            Calendar now = Calendar.getInstance();
            int currentYear = now.get(Calendar.YEAR);
            year = String.valueOf(currentYear);
        }

    %>

    <body>
        <!-- Button to open the modal -->
        <!-- Button to open the modal -->
        <div class="container mt-5">
            <button class="btn btn-success" onclick="openMovieModal()">Select Cinema</button>
            <div id="selected-cinema" class="mt-3"></div>

            <form id="cinema-form" action="<%= RouterURL.OWNER_CINEMAS_STATISTIC%>" method="POST" class="mt-3">
                <input type="hidden" name="month" id="month-select" />
                <input type="hidden" name="year" id="year-select" />
                <input type="hidden" name="selectedCinemaIDs" id="selected-cinema-ids">
                <button class="btn btn-primary" type="submit">Analyze</button>
            </form>

            <div class="form-group mt-3 w-25">
                <label for="month-select-cinema">Select Month:</label>
                <select class="custom-select" <% if (listCinemaID == null || listCinemaID.isEmpty()) { %> 
                        disabled 
                        <% } %> id="month-select-cinema" onchange="submitFormOnSelectChange()"></select>
            </div>

            <div class="form-group mt-3 w-25">
                <label for="year-select-cinema">Select Year:</label>
                <select class="custom-select" <% if (listCinemaID == null || listCinemaID.isEmpty()) { %> 
                        disabled 
                        <% }%> id="year-select-cinema" onchange="submitFormOnSelectChange()"></select>
            </div>
        </div>
        <!-- The Modal -->
        <div id="cinemaModal" class="container-modal-select">
            <div class="modal-content-box-select">
                <div class="modal-header-box-select">
                    <h2 >Select a Cinema</h2>
                    <span class="close" onclick="closeMovieModal()"><i class="fa-solid fa-circle-xmark"></i></span>
                </div>
                <div class="modal-body-box-select ">
                    <div class="cinema-list">
                        <c:forEach var="cinema" items="${cinemas}">
                            <form class="cinema-form-item cinema-form-${cinema.cinemaID}" action="<%= RouterURL.OWNER_CINEMAS_STATISTIC%>" method="POST">

                                <input type="hidden" name="cinemaID" value="${cinema.cinemaID}" />

                                <input type="hidden" name="cinemaTitle" value="${cinema.getAddress()}" />
                                <input type="hidden" name="month" />
                                <input type="hidden" name="year" />

                                <div class="cinema" type="button" onclick="toggleCinemaSelection('${cinema.cinemaID}', '${cinema.getAddress()}', '${cinema.getAvatar()}')">
                                    <img src="${cinema.getAvatar()}" alt="${cinema.getAddress()}" width="70" height="70"/>
                                    <span>${cinema.getAddress()}</span>
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
            let selectedCinemas = [];

            function openMovieModal() {
                document.getElementById('cinemaModal').style.display = 'block';
            }

            function closeMovieModal() {
                document.getElementById('cinemaModal').style.display = 'none';
                displaySelectedCinemas();
                updateHiddenInputs();
            }

            function toggleCinemaSelection(cinemaID, cinemaAddress, cinemaAvatar) {
                console.log({cinemaID, cinemaAddress, cinemaAvatar})
                const cinemaIndex = selectedCinemas.findIndex(cinema => cinema.cinemaID === cinemaID);
                if (cinemaIndex > -1) {
                    selectedCinemas.splice(cinemaIndex, 1);
                } else {
                    selectedCinemas.push({cinemaID, cinemaAddress, cinemaAvatar});
                }
                closeMovieModal();

            }

            function updateHiddenInputs() {
                const selectedCinemaIDs = selectedCinemas.map(cinema => cinema.cinemaID).join(',');
                document.getElementById('selected-cinema-ids').value = selectedCinemaIDs;
            }
            function displaySelectedCinemas() {

                const selectedCinemaDiv = document.getElementById('selected-cinema');
                selectedCinemaDiv.innerHTML = '';
                selectedCinemas.forEach(cinema => {
                    const cinemaElement = document.createElement('div');
                    cinemaElement.style.marginBottom = "10px";

                    const imgElement = document.createElement('img');
                    imgElement.className = "img-select-cinema";
                    imgElement.src = cinema.cinemaAvatar;
                    imgElement.alt = cinema.cinemaAddress;
                    imgElement.width = 50;
                    imgElement.height = 75;

                    const spanElement = document.createElement('span');
                    spanElement.textContent = cinema.cinemaAddress;

                    cinemaElement.appendChild(imgElement);
                    cinemaElement.appendChild(spanElement);

                    selectedCinemaDiv.appendChild(cinemaElement);
                });
                checkSelectedCinema();
            }

            function submitFormOnSelectChange() {

                var month = document.getElementById("month-select-cinema").value;
                var year = document.getElementById("year-select-cinema").value;

                return submitMovieForm(null);
            }

            function submitMovieForm(cinemaID) {

                // Get the form for the specified cinemaID
                var form = document.querySelector(".cinema-form-" + cinemaID);

                // Set the month and year values in the form
                form.querySelector('input[name="month"]').value = month;
                form.querySelector('input[name="year"]').value = year;

                console.log("Form Movie ID:", form.querySelector('input[name="cinemaID"]').value);
                console.log("Form Movie Title:", form.querySelector('input[name="cinemaTitle"]').value);
                console.log("Form Month:", form.querySelector('input[name="month"]').value);
                console.log("Form Year:", form.querySelector('input[name="year"]').value);

                // Submit the form
                //form.submit();

            }

            window.onclick = function (event) {
                var modal = document.getElementById("cinemaModal");
                if (event.target == modal) {
                    closeMovieModal();
                }
            };

            function populateMonthDropdown() {
                const monthSelect = document.getElementById("month-select-cinema");
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
                const yearSelect = document.getElementById("year-select-cinema");
                const currentYear = new Date().getFullYear();
                const years = Array.from({length: 3}, (_, i) => currentYear - i);

                const currentYearSelect = <%= year%>;

                yearSelect.innerHTML = years.map(year =>
                    '<option value="' + year + '"' + (year === currentYearSelect.toString() ? ' selected' : '') + '>' + year + '</option>'
                ).join('');
            }
            window.onload = () => {
                populateMonthDropdown();
                populateYearDropdown();
            };

            function checkSelectedCinema() {
                const selectedCinemaDiv = document.getElementById('selected-cinema');
                const monthSelect = document.getElementById('month-select-cinema');
                const yearSelect = document.getElementById('year-select-cinema');

                if (selectedCinemaDiv.innerHTML.trim() !== '') {
                    monthSelect.disabled = false;
                    yearSelect.disabled = false;
                } else {
                    monthSelect.disabled = true;
                    yearSelect.disabled = true;
                }
            }
        </script>
    </body>
</html>
