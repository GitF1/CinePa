<%@page import="DAO.owner.StatisticOwnerDAO"%>
<%@page import="jakarta.servlet.http.HttpServlet"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Dashboard</title>

        <style>
            .card-custom {
                position: relative;
                margin: 20px 0;
            }
            .card-header-custom {
                font-size: 1rem;
                font-weight: bold;
            }
            .card-body-custom {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .stat {
                font-size: 1rem;
                font-weight: bold;
            }
            .bg-earnings {
                background-color: #FFA07A !important;
                color: white;
            }
            .bg-pageviews {
                background-color: #66CDAA  !important;
                color: white;
            }
            .bg-tasks {
                background-color: #FA8072  !important;
                color: white;
            }
            .bg-downloads {
                background-color: #20B2AA  !important;
                color: white;
            }
            .fas{
                position: absolute;
                bottom: 10px;
                right:10px;
                font-size: 1em;
            }
        </style>

    </head>


    <%

        Integer cinemaChainID = (Integer) session.getAttribute("cinemaChainID");

        StatisticOwnerDAO dao = new StatisticOwnerDAO(getServletContext());
        double totalRevenue = dao.getTotalRevenueByChainID(cinemaChainID);
        int totalCinema = dao.countCinemasByCinemaChainID(cinemaChainID);
        int totalMovie = dao.countMovieByCinemaChainID(cinemaChainID);
        int totalOrderTicket = dao.countTotalOrderTicket(cinemaChainID);
        
     
    %>

    <body>
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="card card-custom bg-earnings">
                        <div class="card-header card-header-custom">$ <%= totalRevenue%></div>
                        <div class="card-body card-body-custom">
                            <div>
                                <p class="stat">All Earnings</p>
                               
                            </div>
                            <div>
                                <i class="fas fa-chart-bar fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card card-custom bg-pageviews">
                        <div class="card-header card-header-custom "> <%=totalMovie%>+ </div>
                        <div class="card-body card-body-custom ">
                            <div>
                                <p class="stat">Total Movie</p>
                             
                            </div>
                            <div>
                                <i class="fas fa-file-alt fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card card-custom bg-tasks">
                        <div class="card-header card-header-custom"><%= totalCinema%></div>
                        <div class="card-body card-body-custom">
                            <div>
                                <p class="stat">Total Cinema</p>
                            </div>
                            <div>
                                <i class="fas fa-calendar-check fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card card-custom bg-downloads">
                        <div class="card-header card-header-custom"><%= totalOrderTicket%></div>
                        <div class="card-body card-body-custom">
                            <div>
                                <p class="stat">Total Order</p>
                                
                            </div>
                            <div>
                                <i class="fas fa-download fa-2x"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- FontAwesome for icons -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"></script>
    </body>
</html>
