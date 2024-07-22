<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Notification Example</title>
        <style>
            #notificationIcon {
                position: relative;
                cursor: pointer;
                color:#6ea9bf;
                z-index:100;

            }
            #notificationCount {
                position: absolute;
                top: -5px;
                left: 15px;
                background-color: red;
                color: white;
                border-radius: 50%;
                padding: 2px 5px;
                font-size: 12px;
            }
            #notificationList {
                display: none;
                position: absolute;
                top: 50px;
                right: 10px;
                background-color: white;
                border: 1px solid #ccc;
                width: 300px;
                max-height: 400px;
                overflow-y: auto;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                max-height: 60vh;
                overflow-y: overlay;
                overflow-x: hidden;
                padding:10px;
                border-radius: 10px;


            }
            .notification {
                padding: 10px;
                border-bottom: 1px solid #eee;
            }
        </style>
    </head>
    <body>
        <header>
            <div id="notificationIcon">
                <img src="https://cdn3.iconfinder.com/data/icons/block/32/bell-1024.png" alt="Notifications" width="30">
                <span id="notificationCount">0</span>
                <div id="notificationList"></div>
            </div>

        </header>


        <script>
            const notificationIcon = document.getElementById('notificationIcon');
            const notificationCount = document.getElementById('notificationCount');
            const notificationList = document.getElementById('notificationList');
            let notifications = [];

            // Toggle notification list visibility
            notificationIcon.addEventListener('click', () => {
                notificationList.style.display = notificationList.style.display === 'none' ? 'block' : 'none';
//                if (notificationList.style.display === 'none') {
//                    notificationCount.textContent = '0';
//                }
            });

            // WebSocket setup
            const socket = new WebSocket("ws://https://cinepa.shop/movie/notifications");

            socket.onmessage = function (event) {
                const data = JSON.parse(event.data);
                notifications.push(data);
                notificationCount.textContent = notifications.length;
                notificationCount.style.display = 'block';
                // Create a notification element
                const notification = document.createElement("div");
                notification.className = "notification";

                // Add image to the notification
                const image = document.createElement("img");
                image.src = data.image;
                image.style.width = "50px";
                image.style.height = "50px";
                image.style.borderRadius = "10px";
                image.style.float = "left";
                image.style.marginRight = "10px";

                // Add title and message to the notification
                const message = document.createElement("p");
                message.textContent = data.message;

                // Add a clickable link to the notification
                const link = document.createElement("a");
                link.href = data.url;
                link.textContent = "View Details";
                link.target = "_blank";

                // Append elements to the notification div
                notification.appendChild(image);
                notification.appendChild(message);
                notification.appendChild(link);
                //



                if (notificationList.childElementCount === 0) {
                    notificationList.appendChild(wrapperDiv);
                } else {
                    notificationList.appendChild(notification);
                    // Remove placeholder image if it exists
                    const placeholder = document.querySelector('.imageNotFoundNotification');
                    if (placeholder) {
                        notificationList.removeChild(placeholder.parentElement); // Remove wrapperDiv
                    }
                }
                //
                // Append the notification to the notifications container

            };

            socket.onopen = function (event) {
                console.log("Connected to WebSocket");
            };

            socket.onclose = function (event) {
                console.log("Disconnected from WebSocket");
            };

            socket.onerror = function (error) {
                console.log("WebSocket error: " + error.message);
            };

            document.addEventListener('DOMContentLoaded', (event) => {
                var notificationList = document.getElementById('notificationList');
                var wrapperDiv = document.createElement("div");
                var notificationImage = document.createElement('img');
                notificationImage.className = "imageNotFoundNotification";
                notificationImage.src = "/movie/assets/images/not-found-2.jpg";
                notificationImage.alt = "No Notifications";
                notificationImage.width = 200;
                notificationImage.height = 200;
                wrapperDiv.appendChild(notificationImage);
                wrapperDiv.style.display = "flex";
                wrapperDiv.style.justifyContent = "center";
                wrapperDiv.style.alignItems = "center";
                if (notificationList.childElementCount === 0) {
                    notificationList.appendChild(wrapperDiv);
                }
            });

            // Event listener for clicks outside the notificationList div
            window.addEventListener('click', function (event) {
                if (event.target !== notificationList && !notificationList.contains(event.target)) {
                    notificationList.style.display === 'none';
                }
            });
        </script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var notificationCount = document.getElementById('notificationCount');

                // Check if notificationCount is '0', then hide it
                if (notificationCount.innerHTML === '0') {
                    notificationCount.style.display = 'none';
                }
            });
        </script>
    </body>
</html>
