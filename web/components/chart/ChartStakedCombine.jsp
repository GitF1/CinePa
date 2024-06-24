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
            String chartType = request.getParameter("type");  // This should be "bar" or "line" for combined charts
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
                },
//                numbers: function (config) {
//                    var cfg = config || {};
//                    var min = cfg.min || 0;
//                    var max = cfg.max || 100;
//                    var from = cfg.from || [];
//                    var count = cfg.count || 8;
//                    var decimals = cfg.decimals || 8;
//                    var continuity = cfg.continuity || 1;
//
//                    var dfactor = Math.pow(10, decimals) || 0;
//                    var data = [];
//                    var i, value;
//
//                    for (i = 0; i < count; ++i) {
//                        value = (from[i] || 0) + min + Math.random() * (max - min);
//                        if (Math.random() <= continuity) {
//                            data.push(Math.round(dfactor * value) / dfactor);
//                        } else {
//                            data.push(null);
//                        }
//                    }
//
//                    return data;
//                },
                CHART_COLORS: {
                    red: 'rgb(255, 99, 132)',
                    blue: 'rgb(54, 162, 235)',
                    green: 'rgb(75, 192, 192)',
                    orange: 'rgb(255, 159, 64)',
                    yellow: 'rgb(255, 205, 86)',
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

            var backgroundColor;

            if (bgColor === "null" && Array.isArray(bgColorArr)) {
                backgroundColor = bgColorArr.map(color => Utils.transparentize(color, 0.5));
            } else if (typeof bgColor === 'string') {
                backgroundColor = Utils.transparentize(bgColor, 0.5);
            } else {
                backgroundColor = Utils.transparentize('rgb(255, 99, 132)', 0.5);
            }

            // Generate dataset configurations from chartDataArr
            var datasets = data.map(function (item, index) {
                const colors = Object.values(Utils.CHART_COLORS);
                let color = colors[index % colors.length];
                return {
                    label: item.lable,
                    data: item.data,
                    borderColor: color,
                    backgroundColor: Utils.transparentize(color, item.type === "line" ? 1 : 0.5),
                    stack: 'combined',
                    type: item.type
                };
            });

            var chartDataConfig = {
                labels: labels,
                datasets: datasets
            };


            var chartOptions = {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: 'Combined Bar and Line Chart'
                    }
                },
                scales: {
                    x: {
                        stacked: true
                    },
                    y: {
                        stacked: true
                    }
                }
            };

            var config = {
                type: chartType || "line",
                data: chartDataConfig,
                options: chartOptions
            };

            // Create and render the chart
            var ctx = document.getElementById(chartId).getContext('2d');
            new Chart(ctx, config);

        </script>
    </body>
</html>
