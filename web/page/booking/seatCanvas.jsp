<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Seat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seating Arrangement</title>
    <style>
        .screen {
            display: block;
            margin: 0 auto;
            padding: 10px;
            background-color: #fff;
            text-align: center;
            font-weight: bold;
            font-size: 1.2em;
            margin-bottom: 20px;
        }
        .seating-container {
            position: relative;
            width: 100%;
            height: 600px; /* Adjust height as needed */
            overflow: hidden;
        }
        canvas {
            background-color: #f0f0f0;
            display: block;
            margin: 0 auto;
        }
    </style>
</head>
<body>
    <div class="screen">MÀN HÌNH</div>
    <div class="seating-container">
        <canvas id="seatingCanvas" width="1200" height="800"></canvas>
    </div>

    <script>
        
        // Fetch seat data from the request attribute
        const seats = <%= request.getAttribute("seats") %>;

        const canvas = document.getElementById('seatingCanvas');
        const ctx = canvas.getContext('2d');
        let scale = 1;
        
        const seatWidth = 25;
        const seatHeight = 20;

        // Draw the seats on the canvas
        function drawSeats() {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.save();
            ctx.scale(scale, scale);

            seats.forEach(seat => {
                const x = seat.coordinateX * 65;
                const y = seat.coordinateY * 65;
                ctx.fillStyle = '#6a0dad';
                ctx.fillRect(x, y, seatWidth, seatHeight);
                ctx.fillStyle = '#fff';
                ctx.textAlign = 'center';
                ctx.textBaseline = 'middle';
                ctx.fillText(seat.name, x + seatWidth / 2, y + seatHeight / 2);
            });

            ctx.restore();
        }

        // Handle zooming in and out
        canvas.addEventListener('wheel', function(event) {
            event.preventDefault();
            if (event.deltaY < 0) {
                scale *= 1.1;
            } else {
                scale /= 1.1;
            }
            drawSeats();
        });

        // Initial draw
        drawSeats();
    </script>
</body>
</html>
