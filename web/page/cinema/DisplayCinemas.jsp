<%-- 
    Document   : DisplayCinema
    Created on : May 25, 2024, 8:24:04 PM
    Author     : ACER
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cinemas</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .form-container {
                max-width: 600px;
                margin: 50px auto;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                background-color: #fff;
            }
            .cinema-list {
                margin-top: 20px;
                padding: 0;
            }
            .cinema-list li {
                list-style: none;
                background-color: #f8f9fa;
                padding: 15px;
                border-radius: 5px;
                margin-bottom: 10px;
                cursor: pointer;
                transition: all 0.3s ease;
            }
            .cinema-list li:hover {
                background-color: #e9ecef;
            }
            .cinema-name {
                font-size: 1.2em;
                font-weight: bold;
                margin-bottom: 5px;
            }
            .cinema-address {
                color: #6c757d;
            }
            .search-bar {
                margin-top: 10px;
                position: relative;
            }
            .search-bar input[type="text"] {
                padding-right: 30px; /* Ensure space for icon */
            }
            .search-icon {
                position: absolute;
                right: 10px;
                top: 50%;
                transform: translateY(-50%);
                cursor: pointer;
            }

            .icon-container {
                display: flex;
                justify-content: space-around;
                margin-top: 20px;
            }
            .icon {
                width: 40px;
                height: 40px;
                cursor: pointer;
                transition: transform 0.3s ease;
            }
            .icon:hover {
                transform: scale(1.1);
            }
            .icon.selected {
                border: 2px solid #007bff;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="form-container">
                <form id="locationForm" action="displaycinemas" method="post">
                    <div class="form-group">
                        <label for="location">Location:</label>
                        <select id="locationSelect" name="location" class="form-control">
                            <c:forEach var="location" items="${locations}">
                                <option value="${location}" <c:if test="${location == sessionScope.location}">selected</c:if>>${location}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>

                <form id="imageForm" action="displaycinemas" method="post">
                    <div class="icon-container">
                        <img src="images/all_avatar.png" class="icon ${(not empty cinemaChainName && cinemaChainName == 'All') ? 'selected' : ''}" onclick="setImageValue('All'); callServlet('imageForm', '/movie/displaycinemas', 'POST');">
                        <c:forEach var="cinemaChain" items="${cinemaChains}">
                            <img src="images/${cinemaChain.getAvatar()}" class="icon ${(not empty cinemaChainName && cinemaChainName == cinemaChain.getName()) ? 'selected' : ''}" onclick="selectIcon(this); setImageValue('${cinemaChain.getName()}'); callServlet('imageForm', '/movie/displaycinemas', 'POST');">
                        </c:forEach>
                        <input type="hidden" id="cinemaChainNameInput" name="cinemaChainNameInput">
                    </div>
                </form>

                <form id="searchCinemaForm" action="displaycinemas" method="post">
                    <div class="search-bar">
                        <input type="text" id="cinemaSearchedInput" name="cinemaSearchedInput" class="form-control" placeholder="Enter cinema's name or address" value="${cinemaSearched}">
                        <span class="search-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16" onclick="callServlet('searchCinemaForm', '/movie/displaycinemas', 'POST')">
                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001a.5.5 0 0 0 .132.13l3.85 3.85a.5.5 0 0 0 .706-.707l-3.85-3.85a.5.5 0 0 0-.132-.131zM2 6.5a4.5 4.5 0 1 1 9 0 4.5 4.5 0 0 1-9 0z"/>
                            </svg>
                        </span>
                    </div>
                </form>

                <form id="cinemaDetailForm">
                    <ul class="cinema-list list-group mt-3">
                        <c:if test="${not empty cinemas}">
                            <c:forEach var="cinema" items="${cinemas}">
                                <li class="list-group-item" onclick="callServlet('cinemaDetailForm', '/movie/displaycinemadetail', 'POST')">
                                    <div class="cinema-name">${cinema.getName()}</div>
                                    <div class="cinema-address">${cinema.getAddress()}, ${cinema.getProvince()}, ${cinema.getDistrict()}, ${cinema.getCommune()}</div>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </form>
            </div>
        </div>

        <script>
            function debounce(cb) {
                let timeout;
                let delay = 800;
                return (...args) => {
                    clearTimeout(timeout);
                    timeout = setTimeout(() => {
                        cb(...args);
                    }, delay);
                };
            }

            function callServlet(id, url, methodType) {
                document.getElementById(id).action = url;
                document.getElementById(id).method = methodType;
                document.getElementById(id).submit();
            }

            document.addEventListener("DOMContentLoaded", function () {
                const cinemaSearchedInput = document.getElementById("cinemaSearchedInput");
                cinemaSearchedInput.focus();
                const length = cinemaSearchedInput.value.length;
                cinemaSearchedInput.setSelectionRange(length, length);

                const locationSelect = document.getElementById("locationSelect");
                locationSelect.addEventListener("change", () => {
                    callServlet('locationForm', '/movie/displaycinemas', 'POST');
                });

                const queryCinemas = debounce(() => {
                    callServlet('searchCinemaForm', '/movie/displaycinemas', 'POST');
                });

                cinemaSearchedInput.addEventListener("input", () => {
                    queryCinemas();
                });
            });

            function selectIcon(icon) {
                const icons = document.querySelectorAll('.icon');
                icons.forEach(i => i.classList.remove('selected'));
                icon.classList.add('selected');
            }

            function setImageValue(value) {
                document.getElementById('cinemaChainNameInput').value = value;
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>

