<%@page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <%
            // Retrieve parameters from the request
            String chartId = request.getParameter("id");
            String width = request.getParameter("width");
            String height = request.getParameter("height");

        %>
        <meta charset="UTF-8">
        <title>Chart</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Include Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            #<%= chartId%> canvas {
                height: <%= height%>px !important;
                width: <%= width%>px !important;
                -moz-user-select: none;
                -webkit-user-select: none;
                -ms-user-select: none;
            }
        </style>
    </head>
    <body>

        <%
            String chartLabel = request.getParameter("label");
            String chartData = request.getParameter("data");
            String chartLabels = request.getParameter("labels");
            String chartType = request.getParameter("type");
            String bgColor = request.getParameter("bg-color");
 
        %>

        <div style=" margin:auto;">
            <canvas width="<%= width%>px !important"  height="<%= height%>px !important" id="<%= chartId%>"></canvas>
        </div>


        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            var Utils = {
                transparentize: function (color, opacity) {
                    var alpha = opacity === undefined ? 0.5 : 1 - opacity;
                    return Chart.helpers.color(color).alpha(alpha).rgbString();
                }
            };
            // Parse parameters from JSP
            var chartId = "<%= chartId%>";
            var chartLabel = "<%= chartLabel%>";
            var labels = JSON.parse('<%=chartLabels%>');
            var data = JSON.parse('<%=chartData%>');
            var chartType = "<%= chartType%>";
            var bgColor = "<%= bgColor%>";
            

            console.log("bgColorArr:", bgColorArr);
            var backgroundColor;

         

            var config = {
                type: chartType,
                data: {
                    labels,
                    datasets: [{
                            data,
                            backgroundColor: Utils.transparentize(bgColor, 0.9) ,
                            borderColor: Utils.transparentize(bgColor, 0.2),
                            borderWidth: 2,
                            pointBackgroundColor: bgColor,
                            pointBorderColor: bgColor,
                            fill: true,
                        }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        x: {
                            display: false
                        },
                        y: {
                            display: false
                        }
                    },
                    plugins: {
                        legend: {
                            display: false
                        }
                    }
                }
            };

            // Create and render the chart
            var ctx = document.getElementById(chartId).getContext('2d');
            new Chart(ctx, config);

        </script>
    </body>
</html>
