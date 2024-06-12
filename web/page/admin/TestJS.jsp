<%-- 
    Document   : TestJS
    Created on : Jun 6, 2024, 10:57:31 AM
    Author     : duyqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="home"></div>
        <button onclick="writeToDiv()">Click me</button>

        <script>
            // JavaScript function to write the variable to the div
            function writeToDiv() {
                var counter = 0;
                counter++;

                // Create the new button element
                var newButton = document.createElement("button");

                // Set the attributes individually
                newButton.type = "button";
                newButton.className = "btn btn-outline-secondary";
                newButton.id = "button-addon" + counter;
                newButton.setAttribute("aria-label", "Close");
                newButton.onclick = function () {
                    removeInputGroup(counter);
                };

                // Set the text content of the button
                newButton.textContent = "x";

                // Append the new button to the div with id "home"
                document.getElementById("home").appendChild(newButton);
            }
            function removeInputGroup(id) {
            var button = document.getElementById("button-addon" + id);
            if (button) {
                button.parentNode.removeChild(button);
            }
        }

            // Call the function to write to the div when the page loads
            //            window.onload = writeToDiv;
        </script>
    </body>
</html>
