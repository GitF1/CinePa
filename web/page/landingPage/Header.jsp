<%-- 
    Document   : navbar
    Created on : May 23, 2024, 3:59:44 PM
    Author     : duyqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!--jstl import-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--java import-->
<%@page import ="DAO.CinemaChainDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.servlet.ServletContext" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>

<html>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <style type="text/css">
        @media only screen and (min-width:768px){
            .nav-item.dropdown:hover .dropdown-menu{
                display:block;
            }
            .dropdown>.dropdown-toggle:active {
                /*Without this, clicking will make it sticky*/
                pointer-events: none;
            }
        }

        .borderless-btn {
            background: none;
            border: none;
            color: black;
            font-size: 1.5rem;
            cursor: pointer;
        }

        .borderless-btn:focus {
            outline: none;
            box-shadow: none;
        }

        .custom-modal .modal-dialog {
            position: absolute;
            top: 0;
            left: 25%;
            width: 50vw;
        }

        .custom-modal .modal-content {
            border-radius: 10px;
            padding: 20px;
        }

        .modal-dialog {
            max-width: 50vw !important;
        }

        .movie-image {
            max-width: 100px;
            border-radius: 5px;
            margin-right: 15px;
        }

        .movie-info p, .movie-info span {
            margin: 0;
        }

        .movie-details {
            display: flex;
            align-items: center;
            padding: 15px;
        }
        .movie-info {
            margin-left: 15px;
            display: flex;
            flex-wrap: wrap;
            gap: 5px;
        }

        .genre {
            display: inline-block;
            margin-right: 5px;
            padding: 2px 4px;
            background-color: #f0f0f0;
            border-radius: 3px;
        }

        #movieNameInput {
            width: 100%;
            padding: 10px;
            font-size: 1rem;
            border: 1px solid #ced4da;
            border-radius: 8px;
            outline: none;
            transition: border-color 0.3s ease;
            margin-left: 90%;
        }

        .modal-footer button {
            width: 100px;
            height: 48px;
        }

        #showingButton {
            height: 30px;
            border: 2px solid white;
            border-radius: 10px;
            background-color: red;
            color: white;
            font-size: 16px;
        }

        #movieDetailsContainer {
            cursor: pointer;
        }
        #movieContainerForm{
            max-height: 65vh;
            overflow: overlay;
        }
        #searchButton{
            margin-left:500px;
        }
        .icon-logo-btn{

            font-size: 2em;
        }
        .wrapper-navbar-header{
            display: flex;
            align-items: center;
        }
        .icon-logo_header{
            width: 50px;
            height: 50px;
            border: 1px solid #ccc;
            border-radius: 12px;
            box-shadow: 2px 3px 16px 1px rgba(0, 0, 0, 0.2);

        }
    </style>

    <!--get chains from login servlet-->
    <c:set var="cinemaNames" value="${sessionScope.chains}" />

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="/movie" style="font-style: italic; font-weight: 600">Cinepa</a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="wrapper-navbar-header navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="icon-logo-btn nav-link active" aria-current="page" href="/movie"> <img class="icon-logo_header" src="https://res.cloudinary.com/dsvllb1am/image/upload/v1718269790/sgvvasrlc3tisefkq92j.png"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/movie/schedule">Lịch chiếu</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/movie/filter-movies">Phim chiếu</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Rạp chiếu
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <c:forEach  var="o" items="${cinemaNames}">
                                <form action="<!--servlet here-->">

                                    <li><input class="dropdown-item" type="submit" name="chain" value="<c:out value = "${o}"/>"></li>
                                </form>
                            </c:forEach>

                        </ul>
                    </li>

                </ul>

                <div>
                    <button id="searchButton" class="borderless-btn" onclick="showModal()">
                        <i class="fa-solid fa-magnifying-glass"></i> 
                    </button>

                    <c:set var="movieName" value="${requestScope.movieName}"></c:set>
                    <c:set var="movies" value="${requestScope.movies}"></c:set>
                        <div class="modal fade custom-modal" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <form id="searchMovieForm" action="searchmovie" method="post">
                                            <div class="search-input-container">
                                                <input type="search" id="movieNameInput" name="movieName" class="modal-title" placeholder="Search..." aria-label="Search" value="${movieName}"></input> 
                                        </div>
                                    </form>
                                    <!--<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>-->
                                </div>
                                <c:if test="${not empty movies}">
                                    <form id="movieContainerForm">
                                        <c:forEach var="movie" items="${movies}">
                                            <div class="modal-body movie-details" id="movieDetailsContainer" onclick="displayMovieDetails('${movie.movieID}')">
                                                <img src="${movie.imageURL}" alt="${movie.title} image" class="movie-image" />
                                                <div class="movie-info">
                                                    <b style="font-size: 22px;">${movie.title}</b>
                                                    <c:set var="genresString" value="${fn:replace(movie.genres.toString(), '[', '')}" />
                                                    <c:set var="genresString" value="${fn:replace(genresString, ']', '')}" />
                                                    <p style="font-size: 18px;">${genresString}</p>
                                                    <p style="font-size: 18px;  "><i class="fa-regular fa-star"></i> ${movie.rating}</p>
                                                    <button type="button" id="showingButton"><i class="fas fa-video" style="margin-right: 8px;"></i>ĐANG CHIẾU</button>
                                                </div>
                                            </div>
                                            <hr/>
                                        </c:forEach>
                                        <input type="hidden" id="movieIDInput" name="movieID"></input>
                                    </form>
                                </c:if>
                                <div class="modal-footer">
                                    <button style="background-color: rgb(216, 45, 139)" type="button" class="btn btn-primary" onclick="closeModal();" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>                

                <ul class="navbar-nav ms-auto mb-2 me-lg-5">
                    <c:if test="${not empty sessionScope.username}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <c:out value="${sessionScope.username}" />
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/information">View Profile</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/order/view">View Ordered</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/CheckRegisterOwnerServlet">Register Owner</a></li>

                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Log Out</a></li>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${!not empty sessionScope.username}">
                        <li class="nav-item ">
                            <a class="nav-link " href="${pageContext.request.contextPath}/login" id="" role="button"  >
                                Login
                            </a>

                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <script>

        <c:set var="modalStatus" value="${requestScope.modalStatus}"></c:set>
                                        console.log("modalStatus:", ${modalStatus});
        <c:if test="${not empty modalStatus and modalStatus}">
                                        showModal();
        </c:if>

                                        const movieNameInput = document.getElementById("movieNameInput");
                                        document.addEventListener("DOMContentLoaded", function () {
                                            movieNameInput.focus();
                                            const length = movieNameInput.value.length;
                                            movieNameInput.setSelectionRange(length, length);
                                        });

                                        function closeModal() {
                                            document.getElementById("movieNameInput").innerText = "";

                                            document.getElementById("movieContainerForm").style.display = "none";

                                            //window.location.href = "/movie";

                                        }

                                        function debounce(cb) {
                                            let timeout;
                                            let delay = 1200;

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

                                        function showModal() {
                                            var myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
                                            myModal.show();
                                        }

                                        const queryMovies = debounce(() => {
                                            callServlet('searchMovieForm', '/movie/searchmovie', 'POST');
                                        });



                                        movieNameInput.addEventListener("input", () => {
                                            queryMovies();
                                        });

                                        function displayMovieDetails(movieID) {
                                            document.getElementById('movieIDInput').value = movieID;
                                            callServlet('movieContainerForm', '/movie/HandleDisplayMovieInfo', 'GET');
                                        }

                                        function callServlet(id, url, methodType) {
                                            document.getElementById(id).action = url;
                                            document.getElementById(id).method = methodType;
                                            document.getElementById(id).submit();
                                        }


    </script>
</html>
