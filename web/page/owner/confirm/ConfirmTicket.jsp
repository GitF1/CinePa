<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Order Details</title>
    </head>
    <body>

        <h1>Order Details</h1>
        <h2>Order Information</h2>
        
        <p>Time Created: ${orderDetails.timeCreated}</p>

        <h2>Movie Information</h2>

        <p>Title: ${orderDetails.movieTitle}</p>
        <p>Room: ${orderDetails.roomName} (Status: ${orderDetails.roomStatus})</p>
        <p>Status: ${orderDetails.movieStatus}</p>
        <p>Synopsis: ${orderDetails.synopsis}</p>
        <p>Country: ${orderDetails.country}</p>
        <p>Start Time: ${orderDetails.startTime}</p>
        <p>End Time: ${orderDetails.endTime}</p>

        <h2>Cinema Information</h2>
        <p>Cinema: ${orderDetails.cinemaName}</p>
        <p>Address:   ${orderDetails.address},  ${orderDetails.commune}, ${orderDetails.district}, ${orderDetails.province}  </p>
        <h2>Seats</h2>

        <ul>
            <c:forEach var="seat" items="${seats}">
                <li>${seat.name}</li>
            </c:forEach>
        </ul>

        <h2>Canteen Items</h2>
        
        <ul>
            <c:forEach var="item" items="${canteenItems}">
                <li>${item.name} - ${item.amount}</li>
            </c:forEach>
        </ul>

        <form action="/movie/order/confirm" method="post">
            
            <input type="hidden" name="orderID" value="${orderID}">
            <input type="hidden" name="userID" value="${userID}">
            <input type="hidden" name="code" value="${code}">

            <input type="submit" value="Xác Nhận">
        </form>
    </body>
</html>
