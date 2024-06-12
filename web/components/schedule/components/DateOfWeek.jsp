
<%@ page import="model.schedule.DateInfo" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <style>
            .list-date_schedule{
                display: flex;
            }
            .date_item{
                border: 1px solid #ccc;
                border-radius: 8px;
                margin-right: 15px;
                cursor: pointer;
                width: 100px;
                text-align:center;

            }
            .date_item:hover{
                opacity: 0.9;
               
            }

            .date{
                padding: 12px 16px;
                font-size: 1.2em;
                font-weight: 600;
                margin-bottom: 5px;
                background-color: #ccc;
                border-top-right-radius: 8px;
                border-top-left-radius: 8px;
            }
            .date-of_week {
                padding: 5px 16px;
                color:#8e8c8c;
                border-bottom-right-radius: 8px;
                border-bottom-left-radius: 8px;
            }
            .select-date .date-of_week{
                color:#d82d8b;
                font-weight: 600;
            }
            .select-date .date{
                background-color: #d82d8b;
                color:#fff;
            }
        </style>
    </head>
    <body>
        <div class="list-date_schedule"> 
            <c:forEach items="${listDateOfWeek}" var="day">
                <div class="date_item ${selectDate == day.getTime() ? "select-date":""}" onclick="onSelectDate('${day.getTime()}', this)">
                    <div class="date">
                        ${day.getDate()}
                    </div>
                    <div class="date-of_week">
                        ${day.getDayOfWeek()}
                    </div>
                </div>
            </c:forEach>
        </div>

        <div id="selected-date"></div>

    </body>
    <script>

        function onSelectDate(date, element) {

            // Send the selected date to the server
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "schedule", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var responseUrl = xhr.responseText;
                    window.location.href = responseUrl;
                }
            };
            xhr.send("date=" + encodeURIComponent(date));

        }

    </script>
</html>
