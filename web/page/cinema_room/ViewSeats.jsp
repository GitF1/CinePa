
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="model.Seat" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Seats</title>
        <style>
            #container {
                width: 100vw;
                height: 200vh;
                position: relative;
                background-color: #262626;
            }
            .seatButton {
                position: absolute;
                width: 35px;
                height: 35px;
                font-size: 13px;
                color: white;
                border: none;
                border-radius: 8px;
            }

            #seatInfo {
                width: 100vw;
                height: 12%;
                background-color: #262626;
                position: fixed;
                top: 70%;
            }

            #seatInfo2 {
                width: 100vw;
                height: 30%;
                background-color: white;
                position: fixed;
                top: 85%;
            }

            .price-container {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .price-details {
                display: flex;
                flex-direction: column;
            }
            .price-details div,
            .price-details h3 {
                margin: 0;
            }

            #bookedSeatInfoButton {
                top: 25%;
                left: 35%;
                background-color: rgb(64, 64, 64);
            }

            #bookedSeatInfoString {
                position: absolute;
                top: 35%;
                left: calc(35% + 45px);
                color: white;
            }

            #availableSeatInfoButton {
                top: 25%;
                left: calc(45%);
                background-color: rgb(114, 46, 209);
            }

            #availableSeatInfoString {
                position: absolute;
                top: 35%;
                left: calc(45% + 45px);
                color: white;
            }

            #chosenSeatInfoButton {
                top: 25%;
                left: calc(55%);
                background-color: rgb(216, 45, 139);
            }

            #chosenSeatInfoString {
                position: absolute;
                top: 35%;
                left: calc(55% + 45px);
                color: white;
            }

            .purchaseBtn {
                padding: 15px 30px;
                background-color: #d82d8b;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                font-weight: bold;
                text-transform: uppercase;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }
            
            @media only screen and (max-width: 1024px) {
                .seatButton {
                    width: 25px;
                    height: 25px;
                    font-size: 10px;
                }
            }

        </style>
    </head>
    <body>

        <form id="bookingSeatForm">
            <div id="container">

                <c:set var="room" value="${requestScope.room}"></c:set>
                <c:set var="seats" value="${requestScope.seats}"></c:set>


                <c:forEach var="seat" items="${seats}">
                    <c:set var="seatName" value="${seat.getName()}" />
                    <c:set var="seatNameLength" value="${fn:length(seatName)}" />

                    <button type="button" class="seatButton" id="seatButton_${seat.seatID}" onclick="chooseSeat('${seat.getSeatID()}', '${seat.getName()}');">
                        ${seatName}
                    </button>



                </c:forEach>
                <div id="seatInfo">

                    <button class="seatButton" id="availableSeatInfoButton" type="button"></button>
                    <div id="availableSeatInfoString">Ghế tồn tại</div>       

                </div>

                <div id="seatInfo2">
                    <h2 style="margin-left: 3%; color: red">${room.getName()}</h2>
                    <c:if test="${seats.size() == 0}">
                        <div style="margin-left: 3%">(Bạn chưa tạo ghế cho phòng này)</div>
                    </c:if>
                </div>
            </div>
        </form>

    </body>

    <script>

        const start = 5;

        const sizeX = Math.floor((9 * window.innerWidth / 10) / 40);
        const sizeY = Math.floor((9 * window.innerHeight / 10) / 40);
        
        const roomWidth = ${requestScope.maxWidth}, roomLength = ${requestScope.maxLength};
        const initX = Math.floor((sizeX - roomLength) / 2);
        const initY = 0;
        const distance = 40; // 40px

        const bookedSeatColor = "rgb(64, 64, 64)";
        const availableSeatColor = "rgb(114, 46, 209)", chosenSeatColor = "rgb(216, 45, 139)";

        var selectedSeats = [];

        <c:set var="bookedSeatColor" value="rgb(64, 64, 64)"/>
        <c:set var="availableSeatColor" value="rgb(114, 46, 209)"/>
        <c:set var="chosenSeatColor" value="rgb(216, 45, 139)"/>

        var numberOfSelectedSeat = 0, totalPrice = 0;

        <c:forEach var="seat" items="${seats}">
            seatID = ${seat.getSeatID()};
            backgroundColor = "${seat.getStatus() == 'Available' ? availableSeatColor : bookedSeatColor}";
            cursor = "${seat.getStatus() == 'Available' ? 'pointer' : 'not-allowed'}";
            console.log("background-color: " + backgroundColor + ";");
            document.getElementById("seatButton_" + seatID).style = "top: calc(5vh + " + (${seat.getY()} + initY - 1) * distance + "px);"
                                                                    + "left: calc(5vw + " + (${seat.getX()} + initX - 1) * distance + "px);";
            document.getElementById("seatButton_" + seatID).style.backgroundColor = backgroundColor;  
            document.getElementById("seatButton_" + seatID).style.cursor = cursor;                                             
        </c:forEach>
    </script>

</html>
