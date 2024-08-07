
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="model.graph.SalesData" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Page</title>
        <!-- link bootstrap :  -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!-- link font-icon :   -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        
        <style>
        .notification-button {
            border: none;
            background-color: white;
            position: relative;
        }

        .notification-popup {
            display: none;
            position: absolute;
            top: 60px;
            right: 30px;
            width: 300px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 10px;
            z-index: 1000;
        }

        .notification-popup ul {
            list-style: none;
            margin: 0;
            padding: 0;
        }

        .notification-popup ul li {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        .notification-popup ul li:last-child {
            border-bottom: none;
        }
        
        .notification-item {
            cursor: pointer;
        }
        </style>
    </head>

    <body>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <div class="container-fluid">
            <!-- row heading :  -->


            <!-- row chinh :  -->
            <div class="row justify-content-around">

                <!-- phan side bar :  -->
                <ul class="col-md-2 " style="background-color: #4e73df; height: 100%; list-style-type: none;">
                    <a href="#" class="d-flex justify-content-center align-items-center py-3">
                        <!-- icon :  -->
                        <div>
                            <i class="fa fa-video-camera text-white" style="font-size: 30px;" aria-hidden="true"></i>
                        </div>
                        <div class="mx-3 text-white">CINEPA
                        </div>
                    </a>
                    <hr class="my-0  py-0 text-white">

                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="/movie/ManageBanUserServlet?type=user" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span> Quản lí người dùng </span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="/movie/ManageBanUserServlet?type=Owner" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-user-circle-o " style="font-size : 20px" aria-hidden="true"></i>
                            <span> Quản lí chủ rạp </span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center my-2">
                        <a href="${pageContext.request.contextPath}/UpdateMovieServlet" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-pencil-square" aria-hidden="true"></i>

                            <span>Edit movies</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="${pageContext.request.contextPath}/page/admin/CreateMovieForm.jsp" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-book" aria-hidden="true"></i>

                            <span>New Movie</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <li class="text-white my-2 py-3 text-center">
                        <a href="${pageContext.request.contextPath}/OverviewGraphServlet" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-book" aria-hidden="true"></i>
                            <span>Report</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="OverviewGraphServlet" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span>overview</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->


                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="/movie/GetWaitingServlet" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span>Duyệt chủ rạp </span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->

                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="#" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                </ul>

                <!-- phan noi dung chinh :  -->
                <div class="sidemain col-md-10 " style="height: 300px;">
                    <!-- phan nav :  -->
                    <nav class="d-flex justify-content-start mb-4 mt-2 shadow align-items-center">
                        <div class="input-group d-flex mr-auto ml-md-3 my-2 my-md-0 mw-100" style="width: 30%;">
                            <input type="text" class="form-control bg-light border-0 small " placeholder="Search for...">
                        </div>
                        <div class="input-group-append">
                            <button class="btn btn-primary">
                                <i class="fa fa-search" aria-hidden="true"></i>
                            </button>
                        </div>
                        <!-- div -->
                        <div class=" d-flex justify-content-evenly align-items-center " style="margin-left: auto; width: 30%; ">
                            <i class="fa fa-search" aria-hidden="true"></i>
                            <button class="notification-button" onclick="toggleNotifications()">
                                <i class="fa fa-bell" aria-hidden="true"></i>
                            </button>
                            <div class="notification-popup" id="notificationPopup">
                                <ul>
                                    <li class="notification-item">
                                        <c:set var="noPendingRequests" value="${requestScope.noPendingRequests}"></c:set>
                                        <form id="movieApprovalForm">
                                            <button onclick="approveMovie()" style="border: none; background-color: white;">${noPendingRequests} chủ rạp gửi yêu cầu tạo phim</button>
                                        </form>
                                    </li>  
                                </ul>
                            </div>
                            <i class="fa fa-commenting-o" aria-hidden="true"></i>
                            <i class="fa fa-bars" aria-hidden="true"></i>

                            <span>Admin page</span>
                            <div  class="rounded-circle d-inline-block" style="width: 50px; height: 50px; background-size: contain; background-image: url('https://th.bing.com/th/id/OIP.2dM29GTjbggyBjika5-jwAAAAA?rs=1&pid=ImgDetMain');"></div>
                        </div>

                    </nav>
                    <!-- phan doanh thu :  -->
                    <div class="row justify-content-around mb-5">
                        <div class="col-md-2 d-flex align-items-center justify-content-between border-start border-5 shadow border-primary">
                            <div style="border-radius: 10px;">
                                <p class="m-2 fw-bold fs-5 text-primary">Doanh thu </p>
                                <p>${doanhThu} VND </p>
                            </div>
                            <i class="fa fa-credit-card-alt text-primary" style="font-size: 24px;" aria-hidden="true"></i>
                        </div>
                        <div class="col-md-2 d-flex align-items-center justify-content-between border-start border-5 shadow border-warning">
                            <div style="border-radius: 10px;">
                                <p class="m-2 fw-bold fs-5 text-warning">Nguời dùng </p>
                                <p>${tongUser} User</p>
                            </div>
                            <i class=" fa fa-user-o text-warning " style="font-size: 24px;" aria-hidden="true"></i>
                        </div>
                        <div class="col-md-2 d-flex align-items-center justify-content-between border-start border-5 shadow border-danger">
                            <div style="border-radius: 10px;">
                                <p class="m-2 fw-bold fs-5 text-danger">Tổng phim</p>
                                <p>${tongPhim} phim</p>
                            </div>
                            <i class="fa fa-film text-danger " style="font-size: 24px;" aria-hidden="true"></i>
                        </div>
                        <div class="col-md-2 d-flex align-items-center justify-content-between border-start border-5 shadow border-info">
                            <div style="border-radius: 10px;">
                                <p class="m-2 fw-bold fs-5 text-info">Tổng review</p>
                                <p>${tongReview} review </p>
                            </div>
                            <i class="fa fa-commenting-o text-info" style="font-size: 24px;" aria-hidden="true"></i>
                        </div>

                    </div>

                    <!-- phan image them vo :  -->
                    <div>
                        <canvas id="weeklySalesChart"></canvas>
                    </div>
                    <script>
                        var dateArr = [];
                        var valueArr = [];
                        <c:forEach var="dataPoint" items='${requestScope["sales7Day"]}'>
                        dateArr.push(`<c:out value="${dataPoint.getDate()}"/>`);
                        valueArr.push(<c:out value="${dataPoint.getValueSold()}"/>);

                        </c:forEach>
                        dateArr.forEach(function (value) {
                            console.log(value);
                        });
                        valueArr.forEach(function (value) {
                            console.log(value);
                        });
                        const ctx = document.getElementById('weeklySalesChart');

                        new Chart(ctx, {

                            type: 'line',
                            data: {
                                labels: dateArr,
                                datasets: [{
                                        label: 'Sales',
                                        data: valueArr,
                                        borderColor: 'rgba(75, 192, 192, 1)',
                                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                                        fill: false,
                                        tension: 0.1
                                    }]
                            },
                            options: {
                                responsive: true,
                                plugins: {
                                    legend: {
                                        position: 'top',
                                    },
                                    title: {
                                        display: true,
                                        text: 'Weekly Sales Line Chart'
                                    }
                                }
                            },
                        });
                        
                        function toggleNotifications() {
                            const popup = document.getElementById('notificationPopup');
                            if (popup.style.display === 'block') {
                                popup.style.display = 'none';
                            } else {
                                popup.style.display = 'block';
                            }
                        }
                        
                        function approveMovie() {
                            callServlet('movieApprovalForm', '/movie/admin/approvemovie', 'GET');
                        }

                        // Close the notification popup when clicking outside
                        window.onclick = function(event) {
                            const popup = document.getElementById('notificationPopup');
                            if (!event.target.closest('.notification-button') && !event.target.closest('.notification-popup') && popup.style.display === 'block') {
                                popup.style.display = 'none';
                            }
                        };
                        
                        function callServlet(id, url, methodType) {
                            document.getElementById(id).action = url;
                            document.getElementById(id).method = methodType;
                            document.getElementById(id).submit();
                        }

                    </script>
                    <div class="row mb-5" style="background-image: url('https://github.com/vankhai-coder/Javascript-exercise-practice/blob/master/Hook/imageCinepa/a2.png?raw=true'); height: 300px; background-size: contain;">
                    </div>
                    <div class="row " style="background-image: url('https://github.com/vankhai-coder/Javascript-exercise-practice/blob/master/Hook/imageCinepa/a1.png?raw=true'); height: 300px; background-size: contain;">
                    </div>
                </div>
            </div>
        </div>


        <script src=" https://cdn.jsdelivr.net/npm/chart.js@4.4.3/dist/chart.umd.min.js "></script>
    </body>
</html>
