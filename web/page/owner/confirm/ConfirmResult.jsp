<%@page import="util.RouterURL"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Order Processing Result</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f2f2f2;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                text-align: center;
                background-color: #fff;
                border-radius: 8px;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                max-width: 400px;
                width: 100%;
            }
            h1 {
                color: #333;
            }
            .message {
                margin-bottom: 20px;
                font-weight: 600;
            }
            .countdown {
                font-size: 18px;
                color: #555;
            }
            .back-link {
                display: inline-block;
                margin-top: 10px;
                color: #007bff;
                text-decoration: none;
                border-bottom: 1px dotted #007bff;
            }
            .back-link:hover {
                text-decoration: none;
            }
        </style>
        <script type="text/javascript">
            // Function to start the countdown and redirect after timeout
            function startCountdownAndRedirect() {
                var countdown = 30; // Countdown duration in seconds
                var timer = setInterval(function () {
                    countdown--;
                    document.getElementById("countdown").innerHTML = countdown;
                    if (countdown <= 0) {
                        clearInterval(timer);
                        window.location.href = '<%= RouterURL.OWNER_PAGE %>'; // Redirect to home page after countdown
                    }
                }, 1000); // Update countdown every second (1000 ms)
            }

            // Function to stop the countdown if the user interacts
            function stopCountdown() {
                clearInterval(timer); // Clear the timer if it's running
            }

            // Call startCountdownAndRedirect function when the page loads
            window.onload = function () {
                startCountdownAndRedirect();
            };
            
        </script>
    </head>
    <body>
        <div class="container">
            <h1>Confirm Result: </h1>
            <div class="message">${requestScope.message}</div> 
            <p class="countdown">You will be redirected to the home page in <span id="countdown">30</span> seconds.</p>
            <a href="<%= RouterURL.OWNER_PAGE %>" class="back-link">Back</a> 
        </div>
    </body>
</html>
