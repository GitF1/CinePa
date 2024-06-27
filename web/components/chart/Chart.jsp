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
        <!-- Include Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@kurkle/color@0.1.9"></script>
        <script src="https://cdn.jsdelivr.net/npm/chartjs-color"></script>
        <script src="https://cdn.jsdelivr.net/npm/chartjs-color-string"></script>
        <script src="https://cdn.jsdelivr.net/npm/@kurkle/color@0.1.9/dist/color.umd.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/moment@2.29.1"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4"></script>
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
            String bgColorArrParam = request.getParameter("bg-color-arr");
        %>

        <div style=" margin:auto;">
            <canvas width="<%= width%>px !important"  height="<%= height%>px !important" id="<%= chartId%>"></canvas>
        </div>

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
            var bgColorArr = JSON.parse('<%= bgColorArrParam%>');

            console.log("bgColorArr:", bgColorArr);
            var backgroundColor;

            if (bgColor === "null" && Array.isArray(bgColorArr)) {
                // Use first color for outer ring and second color for inner ring
                backgroundColor = bgColorArr.map(color => Utils.transparentize(color, 0.5));

            } else if (typeof bgColor === 'string') {
                // Treat as a single color for both outer and inner rings
                backgroundColor = Utils.transparentize(bgColor, 0.5);
            } else {
                // Fallback to a default color or handle error case
                backgroundColor = Utils.transparentize('rgb(255, 99, 132)', 0.5);
            }

            // Define the data and configuration
            var chartDataConfig = {
                labels: labels,
                datasets: [{
                        label: chartLabel,
                        data: data,
                        borderColor: bgColor,
                        backgroundColor: backgroundColor,
                        pointStyle: 'circle',
                        pointRadius: 10,
                        pointHoverRadius: 15
                    }]
            };

            var config = {
                type: chartType,
                data: chartDataConfig,
                options: {
                    responsive: true,
                    plugins: {
                        title: {
                            display: true,
                            text: (ctx) => 'Point Style: ' + ctx.chart.data.datasets[0].pointStyle,
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
