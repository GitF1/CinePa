<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Seat Selection</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">        
        <style>

            .container-body_seat__create{

                /*overflow: hidden;*/

            }

            .seat {
                display: inline-block;
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
                background-color: rgb(216, 45, 139);
            }

            #seatsContainer {
                position: relative;
                width: 90vw;
                height: 82vh;
                margin: auto;
                transition: all 0.1s linear;
                cursor: grab;
                height: 500vh;
                width: 300vw;
            }

            .disabledButton {
                background-color: #cccccc;
                color: #666666;
                border: 1px solid #999999;
                cursor: not-allowed;
                opacity: 0.6;
            }

            .dropdown-toggle.no-caret::after {
                display: none !important;
            }

            .dropdown-item {
                cursor: pointer;
            }
        </style>

    </head>



    <body>
        <h1>Select and Create Seats</h1>
        <form class="container-body_seat__create" id="addSeatsForm">
            <input type="hidden" id="roomID" name="roomID" value="${roomID}"/>
            <div id="seatsContainer"></div>
        </form>

    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
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

        const marginX = percentWidthAndMarginX * WidthElement;
        const marginY = percentHeightAndMarginY * HeightElement;

        const letters = "0ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        const selectedSeatColor = "rgb(216, 45, 139)";
        const unselectedSeatColor = "rgb(245, 245, 245)";
        const disabledSeatColor = "rgb(204, 204, 204)";
        const highlightedColor = "red";

        const container = document.getElementById('seatsContainer');
        const ROW = 'ROW', COL = 'COL';

        // tổng số ghế được hiện thị ở hàng ngang (X) và dọc (Y)
        let numberOfSeatCoordinateX = Math.floor((innerWidth) / (WidthElement + percentWidthAndMarginX * WidthElement)) - 1;
        let numberOfSeatCoordinateY = Math.floor((innerHeight) / (HeightElement + percentHeightAndMarginY * HeightElement)) - 1;
        // zoom in zoom out khi scroll 
        let zoomScale = 1;

        var selectedAvailableSeats = [];
        var selectedDisabledSeats = [];

        console.log("window.innerWidth = " + window.innerWidth + ", window.innerHeight = " + window.innerHeight);


        // di chuyển khung hình bằng chuột
        let isDragging = false;
        let initialX, initialY;
        let xOffset = 0, yOffset = 0;

        function createMyElement(element, className, x, y, innerHTML) {
            element.classList.add(className);
            element.style.width = WidthElement + 'px';
            element.style.height = HeightElement + 'px';
            element.style.left = (x + 0.5) * (WidthElement + marginX) + 'px';
            element.style.top = (y) * (HeightElement + marginY) + 'px';
            element.style.borderRadius = (WidthElement * borderRadiusPercent / 100) + 'px';
            element.style.fontSizeborderRadius = (WidthElement / 3) + 'px';
            element.style.backgroundColor = unselectedSeatColor;
            element.innerHTML = innerHTML;
        }

        function updateMyElement(element, className, innerHTML) {
            element.className = className;
            element.innerHTML = innerHTML;
        }

        function updateCoordinateElement(element, x, y) {
            element.style.left = (x + 0.5) * (WidthElement + marginX) + 'px';
            element.style.top = (y) * (HeightElement + marginY) + 'px';
        }

        function updateSeatName(seat, x, y) {
            seat.innerHTML = letters[y] + x;
        }

        function isSelectedSeat(seat) {
            return seat.style.backgroundColor === selectedSeatColor;
        }

        function isUnselectedSeat(seat) {
            return !isSelectedSeat(seat);
        }

        function insertAvailableSeat(x, y) {
            const newSeat = {
                coordinateX: x,
                coordinateY: y
            };
            selectedAvailableSeats = [...selectedAvailableSeats, newSeat];
        }

        function insertDisabledSeat(x, y) {
            const newSeat = {
                coordinateX: x,
                coordinateY: y
            };
            selectedDisabledSeats = [...selectedDisabledSeats, newSeat];
        }

        function isDisabledSeatSelected(x, y) {
            return selectedDisabledSeats.some(seat => seat.coordinateX === x && seat.coordinateY === y);
        }

        function selectDisabledSeat(seat, x, y) {
            if (seat.style.backgroundColor === disabledSeatColor) {
                seat.style.backgroundColor = unselectedSeatColor;
                const restoreIcon = document.createElement('i');
                restoreIcon.id = "restore_" + seat.id;
                restoreIcon.className = 'fa-solid fa-check';
                restoreIcon.style.fontSize = "x-large";
                seat.appendChild(restoreIcon);
                insertDisabledSeat(x, y);
            } else if (seat.style.backgroundColor === unselectedSeatColor) {
                seat.style.backgroundColor = disabledSeatColor;
                const restoreIcon = document.getElementById("restore_" + seat.id);
                restoreIcon.classList.remove('fa-solid', 'fa-check');
                seat.innerHTML = '';
                selectedDisabledSeats = selectedDisabledSeats.filter((seat) => !(seat.coordinateX === x && seat.coordinateY === y));
            }
        }

        function selectSeat(seat, x, y) {
            const restoreSelectedSeatsButton = document.getElementById('restoreSelectedSeatsButton');
            if (restoreSelectedSeatsButton.getAttribute('isActive') === 'true') {
                console.log("selectSeat: " + seat.id + ', ' + seat.innerHTML);
                if (!isDisabled(seat) && !isDisabledSeatSelected(x, y)) {
                    alert("You can only select seats that are disabled.");
                    return;
                }
                selectDisabledSeat(seat, x, y);
                return;
            }

            if (seat.style.backgroundColor === unselectedSeatColor)
                seat.style.backgroundColor = selectedSeatColor;
            else if (seat.style.backgroundColor === selectedSeatColor)
                seat.style.backgroundColor = unselectedSeatColor;

            let seatBackgroundColor = seat.style.backgroundColor;

            if (seatBackgroundColor === selectedSeatColor) {
                insertAvailableSeat(x, y);
            } else if (seatBackgroundColor === unselectedSeatColor) {
                selectedAvailableSeats = selectedAvailableSeats.filter((seat) => !(seat.coordinateX === x && seat.coordinateY === y));
            }
        }

        function createSeat(x, y) {
            const seat = document.createElement('div');
            seat.id = "seat_" + x + '_' + y;
            createMyElement(seat, 'seat', x, y, x + ',' + y);
            updateSeatName(seat, x, y);

            seat.onclick = function () {
                selectSeat(seat, x, y);
            };

            seat.addEventListener('contextmenu', function (e) {
                e.preventDefault();
                showDropdownMenu(seat, x, y);
            });

            container.appendChild(seat);
        }

        function selectConsecutiveSeatsButton_Template(choice, i, cb1, cb2, cb3) {
            let cnt = 0;
            let num = (choice === ROW) ? numberOfSeatCoordinateX : numberOfSeatCoordinateY;
            let left, right;
            choice === ROW ? right = i : left = i;
            let seat;
            for (let j = 1; j <= num; ++j) {
                choice === ROW ? left = j : right = j;
                seat = document.getElementById("seat_" + left + "_" + right);
                cnt += cb1(seat, left, right);
            }
            if (cnt === num) {
                for (let j = 1; j <= num; ++j) {
                    choice === ROW ? left = j : right = j;
                    seat = document.getElementById("seat_" + left + "_" + right);
                    cb2(seat, left, right);
                }
            } else if (cnt !== num) {
                for (let j = 1; j <= num; ++j) {
                    choice === ROW ? left = j : right = j;
                    seat = document.getElementById("seat_" + left + "_" + right);
                    cb3(seat, left, right);
                }
            }
        }

        function selectConsecutiveSeatsButtonFunc_Restore(choice, i) {
            const cb1 = (seat, x, y) => {
                if (!isDisabled(seat) && !isDisabledSeatSelected(x, y))
                    return 1;
                else if (seat.style.backgroundColor === unselectedSeatColor)
                    return 1;
                return 0;
            };
            const cb2 = (seat, x, y) => {
                if (!isDisabled(seat) && !isDisabledSeatSelected(x, y))
                    return;
                if (seat.style.backgroundColor === unselectedSeatColor)
                    selectDisabledSeat(seat, x, y);
            };
            const cb3 = (seat, x, y) => {
                if (!isDisabled(seat) && !isDisabledSeatSelected(x, y))
                    return;
                if (!isDisabled(seat) || seat.style.backgroundColor === unselectedSeatColor)
                    return;
                selectDisabledSeat(seat, x, y);
            };
            selectConsecutiveSeatsButton_Template(choice, i, cb1, cb2, cb3);
        }

        function selectRowSeatsButtonFunc_Restore(y) {
            selectConsecutiveSeatsButtonFunc_Restore(ROW, y);
        }

        function selectColSeatsButtonFunc_Restore(x) {
            selectConsecutiveSeatsButtonFunc_Restore(COL, x);
        }

        function selectConsecutiveSeatsButtonFunc_Delete(choice, i) {
            const cb1 = (seat, x, y) => {
                if (isDisabled(seat) || isSelectedSeat(seat))
                    return 1;
                return 0;
            };
            const cb2 = (seat, x, y) => {
                selectSeat(seat, x, y);
            };
            const cb3 = (seat, x, y) => {
                if (isSelectedSeat(seat))
                    return;
                selectSeat(seat, x, y);
            };
            selectConsecutiveSeatsButton_Template(choice, i, cb1, cb2, cb3);
        }

        function selectRowSeatsButtonFunc_Delete(y) {
            selectConsecutiveSeatsButtonFunc_Delete(ROW, y);
        }

        function selectColSeatsButtonFunc_Delete(x) {
            selectConsecutiveSeatsButtonFunc_Delete(COL, x);
        }

        function selectColSeatsButtonFunc(x) {
            const restoreSelectedSeatsButton = document.getElementById('restoreSelectedSeatsButton');
            if (restoreSelectedSeatsButton.getAttribute('isActive') === 'true') {
                selectColSeatsButtonFunc_Restore(x);
                return;
            }
            selectColSeatsButtonFunc_Delete(x);
        }

        function selectRowSeatsButtonFunc(y) {
            const restoreSelectedSeatsButton = document.getElementById('restoreSelectedSeatsButton');
            if (restoreSelectedSeatsButton.getAttribute('isActive') === 'true') {
                selectRowSeatsButtonFunc_Restore(y);
                return;
            }
            selectRowSeatsButtonFunc_Delete(y);
        }

        function createSelectRowSeatsButton(y) {
            let selectRowSeatsButton = document.createElement('button');
            createMyElement(selectRowSeatsButton, 'seat', 0, y, '');
            let selectIcon = document.createElement('i');
            selectIcon.classList.add('fa-solid', 'fa-arrow-right');
            selectRowSeatsButton.appendChild(selectIcon);
            selectRowSeatsButton.type = 'button';

            selectRowSeatsButton.onclick = function () {
                selectRowSeatsButtonFunc(y);
            };
            container.appendChild(selectRowSeatsButton);
        }

        function createSelectColSeatsButton(x) {
            let selectColSeatsButton = document.createElement('button');
            createMyElement(selectColSeatsButton, 'seat', x, 0, '');
            let selectIcon = document.createElement('i');
            selectIcon.classList.add('fa-solid', 'fa-arrow-down');
            selectColSeatsButton.appendChild(selectIcon);
            selectColSeatsButton.type = 'button';

            selectColSeatsButton.onclick = function () {
                selectColSeatsButtonFunc(x);
            };
            container.appendChild(selectColSeatsButton);
        }

        function updateDisabledSeatCursor(cursor) {
            for (let x = 1; x <= numberOfSeatCoordinateX; ++x) {
                for (let y = 1; y <= numberOfSeatCoordinateY; ++y) {
                    const seat = document.getElementById("seat_" + x + '_' + y);
                    if (isDisabled(seat))
                        seat.style.cursor = cursor;
                }
            }
        }

        // Tạo sẵn ghế ghi load trang
        function createSeats() {
            console.log("sizeX = " + numberOfSeatCoordinateX + ', sizeY = ' + numberOfSeatCoordinateY);

            container.innerHTML = '';

            // hiển thị ra các ghế
            for (let x = 1; x <= numberOfSeatCoordinateX; x++) {
                for (let y = 1; y <= numberOfSeatCoordinateY; y++) {
                    createSeat(x, y);
                }
            }
            for (let y = 1; y <= numberOfSeatCoordinateY; ++y) {
                createSelectRowSeatsButton(y);
            }

            for (let x = 1; x <= numberOfSeatCoordinateX; ++x) {
                createSelectColSeatsButton(x);
            }

            const addRowSeatsButton = document.createElement('button');
            addRowSeatsButton.id = 'addRowSeatsButton';
            createMyElement(addRowSeatsButton, 'seat', 0, numberOfSeatCoordinateY + 1, '');
            let addIcon = document.createElement('i');
            addIcon.className = 'fa-solid fa-plus';
            addRowSeatsButton.appendChild(addIcon);
            addRowSeatsButton.type = 'button';
            addRowSeatsButton.addEventListener('click', () => {
                window.scrollTo(-0.5 * (WidthElement + marginX), (numberOfSeatCoordinateY + 2) * (HeightElement + marginY) - innerHeight);
                increRowSeats();
            });

            const addColSeatsButton = document.createElement('button');
            addColSeatsButton.id = 'addColSeatsButton';
            createMyElement(addColSeatsButton, 'seat', numberOfSeatCoordinateX + 1, 0, '');
            addIcon = document.createElement('i');
            addIcon.className = 'fa-solid fa-plus';
            addColSeatsButton.appendChild(addIcon);
            addColSeatsButton.type = 'button';
            addColSeatsButton.addEventListener('click', () => {
                window.scrollTo((numberOfSeatCoordinateX + 2) * (WidthElement + marginX) - innerWidth, -1 * (HeightElement + marginY));
                increColumnSeats();
            });

            const saveSeatsButton = document.createElement('button');
            saveSeatsButton.id = 'saveSeatsButton';
            createMyElement(saveSeatsButton, 'seat', numberOfSeatCoordinateX + 1, numberOfSeatCoordinateY + 1, '');
            const createIcon = document.createElement('i');
            createIcon.className = 'fa-regular fa-floppy-disk';
            createIcon.style.fontSize = "x-large";
            saveSeatsButton.appendChild(createIcon);
            saveSeatsButton.onclick = function () {
                const lengthInput = document.createElement('input');
                const widthInput = document.createElement('input');
                lengthInput.name = 'length';
                lengthInput.value = numberOfSeatCoordinateY;
                widthInput.name = 'width';
                widthInput.value = numberOfSeatCoordinateX;
                lengthInput.type = 'hidden';
                widthInput.type = 'hidden';
                container.appendChild(lengthInput);
                container.appendChild(widthInput);

                for (let x = 1; x <= numberOfSeatCoordinateX; ++x) {
                    for (let y = 1; y <= numberOfSeatCoordinateY; ++y) {
                        const seat = document.getElementById("seat_" + x + "_" + y);
                        if (isDisabled(seat))
                            continue;
                        const seatInput = document.createElement('input');
                        seatInput.name = seat.id;
                        seatInput.value = seat.innerHTML;
                        seatInput.type = 'hidden';
                        container.appendChild(seatInput);
                    }
                }
                callServlet('addSeatsForm', '/movie/owner/room/seat/create', 'POST');

            };

            const deleteSelectedSeatsButton = document.createElement('button');
            deleteSelectedSeatsButton.id = 'deleteSelectedSeatsButton';
            createMyElement(deleteSelectedSeatsButton, 'seat', numberOfSeatCoordinateX, numberOfSeatCoordinateY + 1, '');
            const deleteIcon = document.createElement('i');
            deleteIcon.className = 'fa-solid fa-ban';
            deleteIcon.style.fontSize = "x-large";
            deleteSelectedSeatsButton.appendChild(deleteIcon);
            deleteSelectedSeatsButton.type = "button";
            deleteSelectedSeatsButton.onclick = () => {
                for (let i = 0; i < selectedAvailableSeats.length; ++i) {
                    deleteSpecSeat(selectedAvailableSeats[i].coordinateX, selectedAvailableSeats[i].coordinateY);
                }
                selectedAvailableSeats = selectedAvailableSeats.filter(() => false);
            };

            const restoreSelectedSeatsButton = document.createElement('button');
            restoreSelectedSeatsButton.id = 'restoreSelectedSeatsButton';
            createMyElement(restoreSelectedSeatsButton, 'seat', numberOfSeatCoordinateX - 1, numberOfSeatCoordinateY + 1, '');
            const restoreIcon = document.createElement('i');
            restoreIcon.className = 'fa-solid fa-trash-arrow-up';
            restoreIcon.style.fontSize = "x-large";
            restoreSelectedSeatsButton.appendChild(restoreIcon);
            restoreSelectedSeatsButton.type = "button";
            restoreSelectedSeatsButton.setAttribute('isActive', 'false');
            restoreSelectedSeatsButton.onclick = () => {
                if (restoreSelectedSeatsButton.getAttribute('isActive') === 'true') {
                    restoreSelectedSeatsButton.setAttribute('isActive', 'false');
                    for (let i = 0; i < selectedDisabledSeats.length; ++i) {
                        restoreSpecSeat(selectedDisabledSeats[i].coordinateX, selectedDisabledSeats[i].coordinateY);
                    }
                    selectedDisabledSeats = selectedDisabledSeats.filter(() => false);
                    updateDisabledSeatCursor('not-allowed');
                    restoreSelectedSeatsButton.style.transform = 'scale(1.0)';
                    restoreSelectedSeatsButton.style.backgroundColor = unselectedSeatColor;

                } else {
                    restoreSelectedSeatsButton.setAttribute('isActive', 'true');
                    updateDisabledSeatCursor('pointer');
                    restoreSelectedSeatsButton.style.transform = 'scale(1.3)';
                    restoreSelectedSeatsButton.style.backgroundColor = highlightedColor;
                }
            };

            container.appendChild(addRowSeatsButton);
            container.appendChild(addColSeatsButton);
            container.appendChild(saveSeatsButton);
            container.appendChild(deleteSelectedSeatsButton);
            container.appendChild(restoreSelectedSeatsButton);
        }

        function showDropdownMenu(seat, x, y) {
            const existingDropdown = document.getElementById('seatDropdownMenu');
            if (existingDropdown)
                existingDropdown.remove();
            const dropdownMenu = document.createElement('div');
            dropdownMenu.id = 'seatDropdownMenu';
            dropdownMenu.className = 'dropdown-menu show';
            dropdownMenu.style.position = 'absolute';
            dropdownMenu.style.left = (parseFloat(seat.style.left) + 20) + 'px';
            dropdownMenu.style.top = (parseFloat(seat.style.top) - 30) + 'px';

            const disableSeatButton = document.createElement('a');
            disableSeatButton.className = 'dropdown-item';
            disableSeatButton.textContent = 'Disable seat ' + seat.innerHTML;
            disableSeatButton.onclick = () => deleteSpecSeat(x, y);
            dropdownMenu.appendChild(disableSeatButton);

            const disableColumnButton = document.createElement('a');
            disableColumnButton.className = 'dropdown-item';
            disableColumnButton.textContent = 'Disable column';
            disableColumnButton.onclick = () => deleteSpecColumnSeats(x);
            dropdownMenu.appendChild(disableColumnButton);

            const disableRowButton = document.createElement('a');
            disableRowButton.className = 'dropdown-item';
            disableRowButton.textContent = 'Disable row';
            disableRowButton.onclick = () => deleteSpecRowSeats(y);
            dropdownMenu.appendChild(disableRowButton);

            document.body.appendChild(dropdownMenu);
        }

        function handleDropdownAction(action, seat, x, y) {
            const dropdownMenu = document.getElementById('seatDropdownMenu');
            if (dropdownMenu)
                dropdownMenu.remove();

            if (action === 'Change') {
                const newLabel = prompt('Enter new label:', seat.innerHTML);
                if (newLabel !== null) {
                    seat.innerHTML = newLabel;
                    seat.id = "seat_" + x + '_' + y; // Update the ID if needed
                }
            } else if (action === 'Remove') {
                seat.remove();
            }
        }

        // Thêm ghế vào cột dọc sau cùng
        const increColumnSeats = () => {
            const amountSeats = document.getElementsByClassName("seat");
            if (numberOfSeatCoordinateX >= 50) {
                alert("Bạn chỉ có thể thêm tối đa 50 ghế trên hàng ngang");
                return;
            }
            ++numberOfSeatCoordinateX;
            for (let y = 1; y <= numberOfSeatCoordinateY; ++y) {
                createSeat(numberOfSeatCoordinateX, y);
                document.getElementById("seat_" + numberOfSeatCoordinateX + "_" + y).innerHTML = 'Y';
            }
            postDeleteSeat();
            createSelectColSeatsButton(numberOfSeatCoordinateX);
            updateCoordinateElement(document.getElementById('addColSeatsButton'), numberOfSeatCoordinateX + 1, 0);
            postIncreConsecutiveSeats();
        };

        // Thêm ghế vào hàng ngang sau cùng
        const increRowSeats = () => {
            const amountSeats = document.getElementsByClassName("seat");
            console.log("num of seatY = " + numberOfSeatCoordinateY);
            if (numberOfSeatCoordinateY >= 100) {
                alert("Bạn chỉ có thể thêm tối đa 100 ghế trên cột dọc");
                return;
            }
            ++numberOfSeatCoordinateY;
            for (let x = 1; x <= numberOfSeatCoordinateX; ++x) {
                createSeat(x, numberOfSeatCoordinateY);
                document.getElementById("seat_" + x + "_" + numberOfSeatCoordinateY).innerHTML = 'Y';
            }
            postDeleteSeat();
            createSelectRowSeatsButton(numberOfSeatCoordinateY);
            updateCoordinateElement(document.getElementById('addRowSeatsButton'), 0, numberOfSeatCoordinateY + 1);
            postIncreConsecutiveSeats();
        };

        function postIncreConsecutiveSeats() {
            updateCoordinateElement(document.getElementById('saveSeatsButton'), numberOfSeatCoordinateX + 1, numberOfSeatCoordinateY + 1);
            updateCoordinateElement(document.getElementById('deleteSelectedSeatsButton'), numberOfSeatCoordinateX, numberOfSeatCoordinateY + 1);
            updateCoordinateElement(document.getElementById('restoreSelectedSeatsButton'), numberOfSeatCoordinateX - 1, numberOfSeatCoordinateY + 1);
        }

        // Xóa ghế cột dọc sau cùng
        function deleteColumnSeats() {
            --numberOfSeatCoordinateX;
            WidthElement = Math.floor(innerWidth / ((numberOfSeatCoordinateX + 1) * (1 + percentWidthAndMarginX)));
            createSeats();
        }

        // Xóa ghế hàng ngang sau cùng
        function deleteRowSeats() {
            --numberOfSeatCoordinateY;
            HeightElement = Math.floor(innerHeight / ((numberOfSeatCoordinateY + 1) * (1 + percentHeightAndMarginY)));
            createSeats();
        }

        function disableSeat(seat) {
            seat.innerHTML = '';
            seat.style.backgroundColor = "#cccccc";
            seat.style.border = "1px solid #7ee0fb";
            seat.style.cursor = "not-allowed";
            seat.style.opacity = "0.6";
            console.log(seat.style.backgroundColor);
        }

        function ableSeat(seat) {
            console.log("Able seat: " + seat.id);
            seat.innerHTML = 'Y';
        }

        function isDisabled(seat) {
            if (seat.innerHTML === '')
                console.log("isDisabled: " + seat.id);
            return seat.innerHTML === '';
        }

        function postDeleteSeat() {
            let y = 1;

            let deletedRows = 0;
            let deletedSeatsInRow;

            for (; y <= numberOfSeatCoordinateY; ++y) {
                deletedSeatsInRow = 0;
                for (let x = 1; x <= numberOfSeatCoordinateX; ++x) {
                    const seat = document.getElementById("seat_" + x + "_" + y);
                    if (isDisabled(seat)) {
                        ++deletedSeatsInRow;
                        continue;
                    }
                    updateSeatName(seat, x - deletedSeatsInRow, y - deletedRows);
                }
                if (deletedSeatsInRow === numberOfSeatCoordinateX)
                    ++deletedRows;
            }
        }

        // Xóa ghế ở cột dọc cụ thể
        function deleteSpecColumnSeats(x) {
            for (let y = 1; y <= numberOfSeatCoordinateY; ++y) {
                const seat = document.getElementById("seat_" + x + '_' + y);
                deleteSpecSeat(x, y);
            }
        }

        // Xóa ghế ở hàng ngang cụ thể
        function deleteSpecRowSeats(y) {
            for (let x = 1; x <= numberOfSeatCoordinateX; ++x) {
                const seat = document.getElementById("seat_" + x + '_' + y);
                deleteSpecSeat(x, y);
            }
        }

        // Xóa ghế bất kỳ
        function deleteSpecSeat(x, y) {
            const seat = document.getElementById("seat_" + x + '_' + y);
            disableSeat(seat);
            postDeleteSeat();
        }

        function restoreSpecSeat(x, y) {
            const seat = document.getElementById("seat_" + x + '_' + y);
            ableSeat(seat);
            postDeleteSeat();
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

//                zoomScale = Math.max(0.2, Math.min(zoomScale, 5));
            // container.style.transform = 'scale(' + zoomScale + ')';
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



//            if (Math.abs(xOffset + dx) > innerWidth - 10 || Math.abs(yOffset + dy) > innerHeight) {
//                return;
//            }
//
//            function updatePosition() {
//                container.style.transform = 'translate(' + (xOffset + dx) + 'px, ' + (yOffset + dy) + 'px) scale(' + zoomScale + ')';
//            }
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

        document.getElementById('addSeatsForm').onclick = function () {
            const existingDropdown = document.getElementById('seatDropdownMenu');
            if (existingDropdown)
                existingDropdown.remove();
        };

        function callServlet(id, url, methodType) {
            document.getElementById(id).action = url;
            document.getElementById(id).method = methodType;
            document.getElementById(id).submit();
        }

        function handleKeyboardShortcuts(event) {
            console.log(event);
            if (event.ctrlKey && event.shiftKey && event.key === 's') {
                alert('Ctrl + Shift + s was pressed');
                event.preventDefault();
            }
        }

        document.addEventListener('keydown', handleKeyboardShortcuts);

        // handle Load into DOM
        window.onload = () => {
            createSeats();
            const container = document.getElementById('seatsContainer');
            //container.addEventListener('wheel', zoom);
//            container.addEventListener('mousedown', startDrag);
//            container.addEventListener('mousemove', drag);
//            container.addEventListener('mouseup', stopDrag);
//            container.addEventListener('mouseleave', stopDrag);
        };
    </script>
</html>
