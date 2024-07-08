<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Order</title>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #F3F4F6;
                margin: 0;
                padding: 0;
            }
            .container {
                display: flex;
                height: 100vh;
            }
            .sidebar {
                background-color: #FF6B6B;
                width: 20%;
                padding: 20px;
                color: white;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }
            .sidebar .profile {
                display: flex;
                align-items: center;
                margin-bottom: 40px;
            }
            .sidebar .profile img {
                border-radius: 50%;
                width: 50px;
                height: 50px;
                margin-right: 10px;
            }
            .sidebar .nav {
                list-style: none;
                padding: 0;
            }
            .sidebar .nav li {
                margin-bottom: 20px;
            }
            .sidebar .nav li a {
                color: white;
                text-decoration: none;
                font-size: 18px;
            }
            .sidebar .logout {
                color: white;
                text-decoration: none;
                font-size: 18px;
            }
            .content {
                width: 80%;
                padding: 40px;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .billing {
                background-color: #FFFFFF;
                padding: 20px;
                border-radius: 10px;
                width: 100%;
            }
            .billing-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .billing-header h2 {
                margin: 0;
            }
            .order-history {
                margin-top: 20px;
                max-height: 70vh;
                overflow-y: scroll;
                width: 80vw;
            }
            .order-history table {
                width: 100%;
                border-collapse: collapse;
            }
            .order-history table th, .order-history table td {
                padding: 15px;
                text-align: left;
            }
            .order-history table th {
                background-color: #F3F4F6;
            }
            .order-history table tr:nth-child(even) {
                background-color: #F9FAFB;
            }
            .order-history .load-more {
                color: #FF6B6B;
                text-decoration: none;
                display: block;
                margin-top: 10px;
                font-size: 14px;
            }
            .list-order__history_tr{
                max-height: 50vh;
                overflow-y: scroll;
            }
            .payment-method {
                margin-top: 40px;
            }
            .payment-method .card {
                display: flex;
                align-items: center;
                margin-top: 10px;
            }
            .payment-method .card img {
                margin-right: 10px;
                height:40px;

            }
            .subscription-info {
                background-color: #FF6B6B;
                color: white;
                padding: 20px;
                text-align: center;
                border-radius: 10px;
            }
            .subscription-info button {
                background-color: white;
                color: #FF6B6B;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
            }
            .btn-view_detail{
                border-radius: 5px;
                padding: 10px 20px;
                border: 1px solid #eee;
                cursor:pointer;
            }
        </style>
    </head>

    <body>
        
        <jsp:include page=".././landingPage/Header.jsp" />
        
        <div class="container">
            <div class="content">
                <div class="billing">
                    <div class="billing-header">
                    </div>
                    <div class="order-history">

                        <table>
                            <tr>
                                <th>Ngày Đặt Lịch</th>
                                <th>Tên Phim</th>
                                <th>Thời gian bắt đầu</th>
                                <th>Thời gian kết thúc</th>
                                <th></th>
                            </tr>
                            <div class="list-order__history_tr">
                                <c:forEach var="order" items="${orders}">
                                    <tr >
                                        <td>
                                            <fmt:formatDate value="${order.timeCreated}" pattern="dd/MM/yyyy" />
                                        </td>
                                        <td><c:out value="${order.movieTitle}"/></td>
                                        <td>
                                            <fmt:formatDate value="${order.startTime}" pattern="HH:mm - dd/MM" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${order.endTime}" pattern="HH:mm - dd/MM" />
                                        </td>
                                        <td>
                                            <form action="/movie/order/view" method="post">
                                                <input type="hidden" name="orderID" value="${order.orderID}"/>
                                                <input type="hidden" name="roomID" value="${order.roomID}"/>
                                                <input type="hidden" name="movieSlotID" value="${order.movieSlotID}"/>
                                                <input type="hidden" name="movieID" value="${order.movieID}"/>
                                                <button type="submit" class="btn-view_detail">Chi tiết</button>
                                            </form>

                                        </td>
                                    </tr>
                                </c:forEach>
                                <div>


                                    </table>
                                    <a href="#" class="load-more">Thêm</a>
                                </div>
                                <div class="payment-method">
                                    <h3>Phương thức thanh toán</h3>
                                    <div class="card">
                                        <img src="https://cdn.haitrieu.com/wp-content/uploads/2022/10/Icon-VNPAY-QR-635x496.png" alt="Visa">
                                        <p>VN Pay</p>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>
                <c:if test="${viewDetail}">
                    <jsp:include page="BoxOrderDetail.jsp" />
                </c:if>
                </body>
                <script>

            //        function handleOnViewDetailOrder(orderID, roomID, movieSlotID, movieID, url, method) {
            //
            //            // Example implementation for the handleOnViewDetailOrder function
            //            let params = new URLSearchParams();
            //
            //            params.append('orderID', orderID);
            //            params.append('movieSlotID', movieSlotID);
            //            params.append('movieID', movieID);
            //
            //            fetch(url, {
            //                method: method,
            //                headers: {
            //                    'Content-Type': 'application/x-www-form-urlencoded'
            //                },
            //                body: params.toString()
            //            })
            //                    .then(response => response.json())
            //                    .then(data => {
            //                        // Handle the response data
            //                        displayOrderDetails(data);
            //                    })
            //                    .catch(error => {
            //                        console.error('Error:', error);
            //
            //                    });
            //
            //
            //
            //
            //        }

                </script>
                </html>
