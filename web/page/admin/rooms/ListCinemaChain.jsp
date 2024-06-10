<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.CinemaChain" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cinema Chains</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .img-item_theater {
            width: 100px;
            height: 100px;
            margin: auto;
            border-radius: 50%;
            border: 2px solid #ddd;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .img-item_theater:hover {
            opacity: 0.8;
            transform: scale(1.1);
        }
        .cinema-chain-name {
            margin-top: 10px;
            font-weight: bold;
            font-size: 1.1em;
        }
        .tooltip-inner {
            max-width: 200px;
            padding: 5px 10px;
            color: #fff;
            text-align: center;
            background-color: #000;
            border-radius: 5px;
        }
        
          .btn-primary {
                color: #fff;
                background-color: #dcb760;
                border-color: #ffc107;
            }
    </style>
    <script>
        function onSelectCinemaChain(cinemaChainId) {
            window.location.href = 'cinemas?cinemaChainID=' + encodeURIComponent(cinemaChainId);
        }
        $(document).ready(function(){
            $('[data-toggle="tooltip"]').tooltip(); 
        });
    </script>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">CinemaApp</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Contact</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <h1 class="my-4 text-center">Cinema Chains</h1>

        <!-- Search Bar -->
        <div class="row mb-4">
            <div class="col-12">
                <input type="text" class="form-control" id="searchBar" placeholder="Search for cinema chains...">
            </div>
        </div>

        <!-- Cinema Chains -->
        <div class="row">
            <c:forEach var="cinemaChain" items="${cinemaChains}">
                <div class="col-lg-3 col-md-4 col-sm-6 col-12 mb-4">
                    <div class="card text-center" data-toggle="tooltip" title="${cinemaChain.name}">
                        <img class="card-img-top img-item_theater" src="${cinemaChain.avatar == null ? '/movie/assets/images/logo_default_theater.jpg' : cinemaChain.avatar}" alt="${cinemaChain.name}">
                        <div class="card-body">
                            <h5 class="card-title cinema-chain-name">${cinemaChain.name}</h5>
                            <button class="btn btn-primary" onclick="onSelectCinemaChain(${cinemaChain.cinemaChainID})">View Cinemas</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-light text-center text-lg-start mt-4">
        <div class="container p-4">
            <div class="row">
                <div class="col-lg-6 col-md-12 mb-4 mb-md-0">
                    <h5 class="text-uppercase">CinemaApp</h5>
                    <p>Your one-stop destination for all your cinema needs.</p>
                </div>
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase">Links</h5>
                    <ul class="list-unstyled mb-0">
                        <li>
                            <a href="#!" class="text-dark">About</a>
                        </li>
                        <li>
                            <a href="#!" class="text-dark">Contact</a>
                        </li>
                        <li>
                            <a href="#!" class="text-dark">Privacy Policy</a>
                        </li>
                    </ul>
                </div>
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase">Follow Us</h5>
                    <ul class="list-unstyled mb-0">
                        <li>
                            <a href="#!" class="text-dark">Facebook</a>
                        </li>
                        <li>
                            <a href="#!" class="text-dark">Twitter</a>
                        </li>
                        <li>
                            <a href="#!" class="text-dark">Instagram</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="text-center p-3 bg-dark text-white">
            © 2024 CinemaApp
        </div>
    </footer>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function(){
            $('[data-toggle="tooltip"]').tooltip(); 

            $('#searchBar').on('keyup', function() {
                var value = $(this).val().toLowerCase();
                $('.card').filter(function() {
                    $(this).toggle($(this).find('.cinema-chain-name').text().toLowerCase().indexOf(value) > -1)
                });
            });
        });
    </script>
</body>
</html>
