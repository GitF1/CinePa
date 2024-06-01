<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Seat Selection</title>
        <style>

            .container-body_seat__create{

                overflow: hidden;
            }

            .seat {
                position: absolute;
                display: inline-block;
                background-color: #ccc;
                border: 1px solid #333;
                text-align: center;
                line-height: 50px;
                cursor: pointer;
                border-radius: 8px;
                font-size: 0.8em;
                display: flex;
                justify-content: center;
                align-items: center;
                transition: all 0.4s linear;
                -webkit-touch-callout: none; /* iOS Safari */
                -webkit-user-select: none; /* Safari */
                -khtml-user-select: none; /* Konqueror HTML */
                -moz-user-select: none; /* Old versions of Firefox */
                -ms-user-select: none; /* Internet Explorer/Edge */
                user-select: none; /* Non-prefixed version, currently
                                      supported by Chrome, Edge, Opera and Firefox */

            }

            .selected {
                background-color: #6c6;
            }

            #seatsContainer {

                position: relative;
                width: 90vw;
                height: 80vh;
                margin: auto;
                transition: all 0.1s linear;
                cursor: grab;

            }
        </style>
        <script>

            const marginWidth = 50;
            const marginHeight = 60;
            const borderRadiusPercent = 20;

            var WidthElement = 50;
            var HeightElement = 45;

            var innerWidth = window.innerWidth - marginWidth * 2;
            var innerHeight = window.innerHeight - marginHeight * 2;

            let percentWidthAndMarginX = 15 / 55;
            let percentHeightAndMarginY = 20 / 50;

            let numberOfSeatCoordinateX;
            let numberOfSeatCoordinateY;

            let zoomScale = 1;

            let isDragging = false;
            let initialX, initialY;
            let xOffset = 0, yOffset = 0;

            function createSeats() {
                const container = document.getElementById('seatsContainer');
                container.innerHTML = '';

                const marginX = percentWidthAndMarginX * WidthElement;
                const marginY = percentHeightAndMarginY * HeightElement;

                console.log("width elemen: " + WidthElement + " margin x:" + marginX);
                console.log("height elemen:  " + HeightElement + " margin y:" + marginY);

                numberOfSeatCoordinateX = Math.floor((innerWidth) / (WidthElement + marginX));
                numberOfSeatCoordinateY = Math.floor((innerHeight) / (HeightElement + marginY));

                console.log("number x: " + numberOfSeatCoordinateX);
                console.log("number y:" + numberOfSeatCoordinateY);

                for (let y = 1; y <= numberOfSeatCoordinateY; y++) {
                    for (let x = 1; x <= numberOfSeatCoordinateX; x++) {
                        const seat = document.createElement('div');

                        seat.className = 'seat';

                        seat.style.width = WidthElement + 'px';
                        seat.style.height = HeightElement + 'px';

                        seat.style.left = (x - 1) * (WidthElement + marginX) + 'px';
                        seat.style.top = (y - 1) * (HeightElement + marginY) + 'px';

                        seat.style.borderRadius = (WidthElement * borderRadiusPercent / 100) + 'px';
                        seat.style.fontSize = (WidthElement / 3) + 'px';
                        seat.innerHTML = y + ',' + x;

                        seat.onclick = function () {
                            this.classList.toggle('selected');
                        };

                        container.appendChild(seat);
                    }
                }

            }

            function increaSeats() {

                const amountSeats = document.getElementsByClassName("seat");
                console.log("amoutn seats: " + amountSeats.length);
                if (amountSeats.length > 1000)
                    return;

                numberOfSeatCoordinateX++;
                numberOfSeatCoordinateY++;

                WidthElement = Math.floor(innerWidth / (numberOfSeatCoordinateX * (1 + percentWidthAndMarginX)));
                HeightElement = Math.floor(innerHeight / (numberOfSeatCoordinateY * (1 + percentHeightAndMarginY)));

                createSeats();

            }


            function zoom(event) {
                event.preventDefault();

                const container = document.getElementById('seatsContainer');
                const zoomIn = event.deltaY < 0;

                zoomScale += zoomIn ? 0.1 : -0.1;

                zoomScale = Math.max(0.2, Math.min(zoomScale, 5));
                container.style.transform = 'scale(' + zoomScale + ')';


            }


            function startDrag(event) {
                const container = document.getElementById('seatsContainer');
                isDragging = true;
                initialX = event.clientX;
                initialY = event.clientY;
                xOffset = 0;
                yOffset = 0;
                //container.style.cursor = 'grabbing';
            }

            function drag(event) {
                if (!isDragging)
                    return;
                const container = document.getElementById('seatsContainer');
                const dx = event.clientX - initialX;
                const dy = event.clientY - initialY;
                function updatePosition() {
                    container.style.transform = 'translate(' + (xOffset + dx) + 'px, ' + (yOffset + dy) + 'px) scale(' + zoomScale + ')';
                }
                //container.style.transform = 'translate(' + (xOffset + dx) + 'px, ' + (yOffset + dy) + 'px) scale(' + zoomScale + ')';
                requestAnimationFrame(updatePosition);
            }

            function stopDrag() {
                if (!isDragging)
                    return;
                const container = document.getElementById('seatsContainer');
                isDragging = false;
                xOffset += event.clientX - initialX;
                yOffset += event.clientY - initialY;
                //container.style.cursor = 'grab';
            }

            window.onload = () => {

                createSeats();

                const container = document.getElementById('seatsContainer');

                container.addEventListener('wheel', zoom);
                container.addEventListener('mousedown', startDrag);
                container.addEventListener('mousemove', drag);
                container.addEventListener('mouseup', stopDrag);
                container.addEventListener('mouseleave', stopDrag);
            };
        </script>
    </head>



    <body>
        <h1>Select and Create Seats</h1>
        <div class="container-body_seat__create">
            <div id="seatsContainer"></div>
        </div>
        <button onclick="increaSeats()"> More Space</button>
    </body>
</html>
