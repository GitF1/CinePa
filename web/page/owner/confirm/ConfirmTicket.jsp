<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Order Details</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }

            .container {
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                max-width: 600px;
                width: 100%;
            }

            h1, h2 {
                color: #333;
                text-align: center;
            }

            p {
                color: #666;
                line-height: 1.6;
                margin: 10px 0;
            }

            ul {
                list-style-type: none;
                padding: 0;
            }

            li {
                background-color: #f9f9f9;
                margin: 5px 0;
                padding: 10px;
                border-radius: 4px;
            }

            form {
                text-align: center;
                margin-top: 20px;
            }

            input[type="submit"] {
                background-color: #007BFF;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            .info-section {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">

            <div class="info-section">
                <h2>Thông Tin Đặt Vé</h2>
                <p>Ngày đặt:  ${orderDetails.timeCreated}</p>
            </div>

            <div class="info-section">
                <h2>Thông tin phim</h2>
                <p>Tên Phim:   ${orderDetails.movieTitle}</p>
                <p>Phòng:  ${orderDetails.roomName} (Status: ${orderDetails.roomStatus})</p>
                <p>Trạng thái phòng:  ${orderDetails.movieStatus}</p>
                <p>Quốc gia:  ${orderDetails.country}</p>
                <p>Giờ bắt đầu: ${orderDetails.startTime}</p>
                <p>Giờ kết thúc: ${orderDetails.endTime}</p>
            </div>

            <div class="info-section">
                <h2>Thông tin rạp</h2>
                <p>Tên Rạp: ${orderDetails.cinemaName}</p>
                <p>Địa chỉ: ${orderDetails.address}, ${orderDetails.commune}, ${orderDetails.district}, ${orderDetails.province}</p>
            </div>

            <div class="info-section">
                <h2>Ghế đặt</h2>
                <ul>
                    <c:forEach var="seat" items="${seats}">
                        <li>${seat.name}</li>
                        </c:forEach>
                </ul>
            </div>

            <div class="info-section">
                <h2>Thức ăn kèm theo</h2>
                <ul>
                    <c:forEach var="item" items="${canteenItems}">
                        <li>${item.name} - ${item.amount}</li>
                        </c:forEach>
                </ul>
            </div>

            <form action="/movie/order/confirm" method="post">
                <input type="hidden" name="orderID" value="${orderID}">
                <input type="hidden" name="userID" value="${userID}">
                <input type="hidden" name="code" value="${code}">
                <input type="submit" value="Xác Nhận">
            </form>
        </div>
    </body>
</html>
