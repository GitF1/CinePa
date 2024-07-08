<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
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
            canvas {
                height: 200px;
                width: 500px;
                -moz-user-select: none;
                -webkit-user-select: none;
                -ms-user-select: none;
            }
        </style>
    </head>
    <body>
        <div style="width: 75%; margin:auto;">
            <canvas  id="myChartMovie"></canvas>
        </div>

        <script>
            // Utility functions
            const Utils = {
                months: function (config) {
                    var cfg = config || {};
                    var count = cfg.count || 12;
                    var section = cfg.section;
                    var values = [];
                    for (var i = 0; i < count; ++i) {
                        values.push(moment().month(i).format('MMMM'));
                    }
                    return values;
                },
                numbers: function (config) {
                    var cfg = config || {};
                    var min = cfg.min || 0;
                    var max = cfg.max || 100;
                    var from = cfg.from || [];
                    var count = cfg.count || 8;
                    var decimals = cfg.decimals || 8;
                    var continuity = cfg.continuity || 1;
                    var dfactor = Math.pow(10, decimals) || 0;
                    var data = [];
                    var i, value;
                    for (i = 0; i < count; ++i) {
                        value = (from[i] || 0) + Math.random() * (max - min) + min;
                        if (Math.random() <= continuity) {
                            data.push(Math.round(dfactor * value) / dfactor);
                        } else {
                            data.push(null);
                        }
                    }
                    return data;
                },
                transparentize: function (color, opacity) {
                    var alpha = opacity === undefined ? 0.5 : 1 - opacity;
                    return Chart.helpers.color(color).alpha(alpha).rgbString();
                },
                CHART_COLORS: {
                    red: 'rgb(255, 99, 132)',
                    orange: 'rgb(255, 159, 64)',
                    yellow: 'rgb(255, 205, 86)',
                    green: 'rgb(75, 192, 192)',
                    blue: 'rgb(54, 162, 235)',
                    purple: 'rgb(153, 102, 255)',
                    grey: 'rgb(201, 203, 207)'
                }
            };

            // Define the data and configuration
            const labels = Utils.months({count: 12});
            const data = {
                labels,
                datasets: [{
                        label: 'Amount',

                        data: [1, 3, 4, 5, 12, 4, 8, 34, 20, 34, 67,45],
                        // data: Utils.numbers({count: 12, min: 0, max: 100}),

                        borderColor: Utils.CHART_COLORS.red,
                        backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red, 0.5),
             
                        pointStyle: 'circle',
                        pointRadius: 10,
                        pointHoverRadius: 15
                    }]
            };

            const config = {
                type: 'bar',
                data: data,
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
            window.onload = function () {
                var ctx = document.getElementById('myChartMovie').getContext('2d');
                new Chart(ctx, config);
            };
        </script>
    </body>
</html>
