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
                background-color: #f5f5f5;
                border: 1px solid #7ee0fb;
                opacity: 0.6;
                text-align: center;
                line-height: 50px;
                cursor: pointer;
                border-radius: 8px;
                font-size: 0.8em;
                display: flex;
                justify-content: center;
                align-items: center;
                transition: all 0.2s linear;
                -webkit-touch-callout: none; /* iOS Safari */
                -webkit-user-select: none; /* Safari */
                -khtml-user-select: none; /* Konqueror HTML */
                -moz-user-select: none; /* Old versions of Firefox */
                -ms-user-select: none; /* Internet Explorer/Edge */
                user-select: none; /* Non-prefixed version, currently
                                      supported by Chrome, Edge, Opera and Firefox */

            }
            .seat:hover{
                opacity: 1;
                transform:scale(1.1);
            }
            .selected {
                background-color: #6a0dad;
            }

            #seatsContainer {

                position: relative;
                width: 90vw;
                height: 82vh;
                margin: auto;
                transition: all 0.1s linear;
                cursor: grab;

            }
        </style>
        
        
        <script>
            // khoảng cách mặt định ban đầu giữa khung chứa rạp phim 
            const marginWidth = 50;
            const marginHeight = 60;
            const borderRadiusPercent = 20;
            // chiều dài và chiều rộng của ghế ban đầu
            var WidthElement = 50;
            var HeightElement = 45;
            // chiều dài , chiều rộng của khung chứa các seat
            var innerWidth = window.innerWidth - marginWidth * 2;
            var innerHeight = window.innerHeight - marginHeight * 2;
            // tỉ lệ giữa margin giữa các seat
            let percentWidthAndMarginX = 15 / 55;
            let percentHeightAndMarginY = 20 / 50;
            
            // tổng số ghế được hiện thị ở hàng ngang (X) và dọc (Y)
            let numberOfSeatCoordinateX;
            let numberOfSeatCoordinateY;
            // zoom in zoom out khi scroll 
            let zoomScale = 1;
            
            
            // di chuyển khung hình bằng chuột
            let isDragging = false;
            let initialX, initialY;
            let xOffset = 0, yOffset = 0;
            
            
            //
            function createSeats() {
                const container = document.getElementById('seatsContainer');
                
                container.innerHTML = '';
                // khoảng cách giữa các ghế
                const marginX = percentWidthAndMarginX * WidthElement;
                const marginY = percentHeightAndMarginY * HeightElement;

                console.log("width elemen: " + WidthElement + " margin x:" + marginX);
                console.log("height elemen:  " + HeightElement + " margin y:" + marginY);
                // tổng số ghế hàng ngang = width khung hình /(width ghế + khoảng cách X)
                numberOfSeatCoordinateX = Math.floor((innerWidth) / (WidthElement + marginX));
                 // tổng số ghế hàng dọc = height khung hình  /(height dài ghế + khoảng cách Y)
                numberOfSeatCoordinateY = Math.floor((innerHeight) / (HeightElement + marginY));

                console.log("number x: " + numberOfSeatCoordinateX);
                console.log("number y:" + numberOfSeatCoordinateY);
                
                // hiển thị ra các ghế
                for (let y = 1; y <= numberOfSeatCoordinateY; y++) {
                    for (let x = 1; x <= numberOfSeatCoordinateX; x++) {
                        const seat = document.createElement('div');

                        seat.className = 'seat';
                        // set chiều dài rộng seat
                        seat.style.width = WidthElement + 'px';
                        seat.style.height = HeightElement + 'px';
                        
                        // tọa độ của ghế
                        seat.style.left = (x - 1) * (WidthElement + marginX) + 'px';
                        seat.style.top = (y - 1) * (HeightElement + marginY) + 'px';
                        
                        // style thay đổi ghi zoom hoặc thêm ghế
                        seat.style.borderRadius = (WidthElement * borderRadiusPercent / 100) + 'px';
                        seat.style.fontSize = (WidthElement / 3) + 'px';
                        seat.innerHTML = y + ',' + x;
                        
                        // onclick
                        seat.onclick = function () {
                            this.classList.toggle('selected');
                        };

                        container.appendChild(seat);
                    }
                }

            }
            
            // thêm ghế (thêm cả hàng dọc và ngang) -> cần tách 
            function increaSeats() {

                const amountSeats = document.getElementsByClassName("seat");
                console.log("amoutn seats: " + amountSeats.length);
                
                // stop khi tổng số over 1000
                if (amountSeats.length > 1000)
                    return;
                
                // tăng hàng dọc (X) và hàng ngang (Y) lên 1
                numberOfSeatCoordinateX++;
                numberOfSeatCoordinateY++;
                // tính lại chiều dài, rộng ghế 
                WidthElement = Math.floor(innerWidth / (numberOfSeatCoordinateX * (1 + percentWidthAndMarginX)));
                HeightElement = Math.floor(innerHeight / (numberOfSeatCoordinateY * (1 + percentHeightAndMarginY)));

                createSeats();

            }

            // xử lí zoom in zoom out khi scroll
            function zoom(event) {
                event.preventDefault();

                const container = document.getElementById('seatsContainer');
                const zoomIn = event.deltaY < 0;

                zoomScale += zoomIn ? 0.1 : -0.1;

                zoomScale = Math.max(0.2, Math.min(zoomScale, 5));
                container.style.transform = 'scale(' + zoomScale + ')';


            }

            // xủ lí kéo thả khung hình
            function startDrag(event) {
                const container = document.getElementById('seatsContainer');
                isDragging = true;
                initialX = event.clientX;
                initialY = event.clientY;
                xOffset = 0;
                yOffset = 0;
              
            }

            function drag(event) {
                if (!isDragging)
                    return;
                const container = document.getElementById('seatsContainer');
                const dx = event.clientX - initialX;
                const dy = event.clientY - initialY;

                console.log("dx: ", xOffset + dx, "dy:  ", yOffset + dy);
                console.log("inner height: ", innerHeight, "innerWitdth", innerWidth);



                if (Math.abs(xOffset + dx) > innerWidth - 10 || Math.abs(yOffset + dy) > innerHeight) {
                    return;
                }

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

            }
            // handle Load into DOM
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
