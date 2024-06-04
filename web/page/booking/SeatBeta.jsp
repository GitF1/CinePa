<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Seat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            .seating {
                position: relative;
                width: 1000px;
                height: 520px;
                overflow: hidden;
                transition: all 0.2s linear;
            }
            .seat {
                background-color: #6a0dad;
                color: white;
                padding: 15px;
                text-align: center;
                border-radius: 8px;
                cursor: pointer;
                position: absolute;
                height: 20px;
                width: 25px;
            }
         
        </style>
    </head>
    <body>

        <div class="screen">MÀN HÌNH</div>

        <div id="seatingContainer" class="seating">
            <%
                ArrayList<Seat> seats = (ArrayList<Seat>) request.getAttribute("seats");
                for (Seat seat : seats) {
                    int x = seat.getX();
                    int y = seat.getY();
                    int id = seat.getSeatID();
            %>
            <div onclick="toggleComboBox('<%= id %>')" class="seat" style= "left: <%= x * 65 %>px; top: <%= y *70 %>px;">
                <%= seat.getName() %>
                <%= x %>, <%= y %>

            </div>
            <%
                }
            %>

            <!--            <button onclick="toggleComboBox('1')">Show Combo</button>-->



        </div>
        <jsp:include page="./Canteen.jsp" />

        <script>

            let seatingContainer = document.getElementById('seatingContainer');
            let scale = 1;

            // Function to apply scaling transformation
            function applyScale() {
                seatingContainer.style.transform = `scale(${scale})`;
                seatingContainer.style.transformOrigin = '0 0';
            }

            seatingContainer.addEventListener('wheel', function (event) {
                console.log(event);
                event.preventDefault();
                if (event.deltaY < 0) {
                    scale *= 1.1;
                } else {
                    scale /= 1.1;
                }
                applyScale();
            });


        </script>
     

    </body>

</html>
