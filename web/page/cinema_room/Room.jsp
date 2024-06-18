<%-- 
    Document   : Room
    Created on : May 27, 2024, 3:27:31 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Room</title>
        <style>
            #containerCanvas {
                width: 100%;
                height: 100vh;
                margin: auto;
                border: 1px solid #000;
            }

            #myCanvas {
                width: 100%;
                height: auto;
            }
        </style>
    </head>
    <body>
        <form id="containerCanvas">
            <canvas id="myCanvas"></canvas>
            <c:set var="room" value="${requestScope.room}"></c:set>
            <c:set var="seats" value="${requestScope.seats}"></c:set>
            <input type="hidden" id="seatIDInput" name="seatIDInput"></input>
        </form>
    </body>
    <script>
//        const canvas = document.getElementById('myCanvas');
//        const context = canvas.getContext('2d');
//        
//        const start = 20, sizeX = 27, sizeY = 13, distance = 50, side = 35;
//        const roomWidth = ${requestScope.maxWidth}, roomLength = ${requestScope.maxLength};
//        const addX = Math.floor((sizeX - roomWidth) / 2), addY = Math.floor((sizeY - roomLength) / 2);
//        
//        const initX = start + addX * distance, initY = start + addY * distance;
//        
//        var seats = [];
//        
//        function draw() {
//            canvas.width = containerCanvas.clientWidth;
//            canvas.height = containerCanvas.clientHeight;
//            <c:forEach var="seat" items="${seats}">
//                newSeat = {
//                    seatID: "${seat.getSeatID()}",
//                    seatName: "${seat.getName()}",
//                    coordinateX: initX + (${seat.getY()} - 1) * distance,
//                    coordinateY: initY + (${seat.getX()} - 1) * distance
//                };
//                
//                seats = [...seats, newSeat];
//                drawSeat(${seat.x}, ${seat.y});
//            </c:forEach>
//        }   
//        
//        function drawRectangle(x, y, width, height, color) {
//            context.fillStyle = color;
//            context.fillRect(x, y, width, height);
//        }
//        
//        function drawSeat(x, y) {
//            drawRectangle(initX + (y - 1) * distance, initY + (x - 1) * distance, side, side, 'blueviolet');
//        }
//        draw();
//        window.addEventListener('resize', draw);
//        
//        for(let i = 0; i < seats.length; ++i) console.log(seats[i]);
//        
//        function chooseSeat(userClickX, userClickY, seatX, seatY) {
//            return userClickX >= seatX && userClickX <= seatX + side && userClickY >= seatY && userClickY <= seatY + side;
//        }
//        
//        canvas.addEventListener('click', function(e) {
//            let userClickX = e.offsetX, userClickY = e.offsetY;
//            console.log("User click: " + userClickX + ", " + userClickY);
//            let seatX, seatY;
//            let i = 0;
//            for(; i < seats.length; ++i) {
//                seatX = seats[i].coordinateX, seatY = seats[i].coordinateY;
//                if(chooseSeat(userClickX, userClickY, seatX, seatY)) {
//                    document.getElementById('seatIDInput').value = seats[i].seatID;
//                    callServlet('containerCanvas', '/movie/booking/seat', 'POST');
//                    break;
//                }
//            }
//        });
//        
//        function callServlet(id, url, methodType) {
//            document.getElementById(id).action = url;
//            document.getElementById(id).method = methodType;
//            document.getElementById(id).submit();
//        }
    </script>
</html>
