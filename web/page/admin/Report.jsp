<%-- 
    Document   : Report
    Created on : Jun 11, 2024, 9:03:32 AM
    Author     : duyqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="model.graph.SalesData" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report</title>
    </head>
    <body>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src=" https://cdn.jsdelivr.net/npm/fullcalendar@6.1.14/index.global.min.js "></script>
        <div>
            <canvas id="weeklySalesChart"></canvas>
        </div>

        <div>
            <canvas id="monthlySalesChart"></canvas>
        </div>
        <div id='calendar'></div>
        <script>
//            First Graph
            var dateArr = [];
            var valueArr = [];
            <c:forEach var="dataPoint" items='${requestScope["sales7Day"]}'>

            dateArr.push(`<c:out value="${dataPoint.getDate()}"/>`);
            valueArr.push(<c:out value="${dataPoint.getValueSold()}"/>);
            </c:forEach>
            dateArr.forEach(function (value) {
                console.log(value);
            });
            valueArr.forEach(function (value) {
                console.log(value);
            });
            const ctx = document.getElementById('weeklySalesChart');
            new Chart(ctx, {

                type: 'line',
                data: {
                    labels: dateArr,
                    datasets: [{
                            label: 'Sales',
                            data: valueArr,
                            borderColor: 'rgba(75, 192, 192, 1)',
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            fill: false,
                            tension: 0.1
                        }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            position: 'top',
                        },
                        title: {    
                            display: true,
                            text: 'Weekly Sales Line Chart'
                        }
                    }
                },
            });
        </script>
        <script>
//            Second Graph
            dateMonthArr = [];
            chains = [];
            temp = [];
            valueArr2d = [];
            numOfArr = 0;

            var lineChartData;
//            monthly label processing
            <c:forEach var="dataPoint" items='${requestScope["month"]}'>
            dateMonthArr.push(`<c:out value="${dataPoint}"/>`);
            </c:forEach>
//                iterate thru input
            <c:forEach items='${requestScope["monthlyChain"]}' var="entry">
            chains.push(`<c:out value="${entry.key}"/>`);
                <c:forEach items='${entry.value}' var="dailyTotal">
            temp.push(`<c:out value="${dailyTotal.getValueSold()}"/>`);

                </c:forEach>
            valueArr2d.push(temp);
            temp = [];
            numOfArr++;
            </c:forEach>
            <c:forEach items='${valueArr2d}' var="x">

            console.log(`<c:out value="${x}"/>`);
            </c:forEach>
            for (i in chains) {
                console.log(chains[i]);
            }
            for (i in valueArr2d) {
                for (o in valueArr2d[i]) {
                    console.log(valueArr2d[i][o]);
                }
                console.log(chains[i]);
            }
            console.log(numOfArr);

            const datasets = valueArr2d.map((datasetValues, index) => {
                return {
                    label: chains[index],
                    data: datasetValues,
                    fill: false, // Set to true if you want to fill the area under the line
                    borderColor: getRandomColor(),
                    tension: 0.1
                };
            });
            function getRandomColor() {
                const letters = '0123456789ABCDEF';
                let color = '#';
                for (let i = 0; i < 6; i++) {
                    color += letters[Math.floor(Math.random() * 16)];
                }
                return color;
            }


            const config = {
                type: 'line',
                data: {
                    labels: dateMonthArr,
                    datasets: datasets
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            display: true,
                            title: {
                                display: true,
                                text: 'Month'
                            }
                        },
                        y: {
                            display: true,
                            title: {
                                display: true,
                                text: 'Value'
                            }
                        }
                    }
                }
            };
            const ctx2 = document.getElementById('monthlySalesChart').getContext('2d');
            const myChart = new Chart(ctx2, config);


        </script>
        <!--Calender js-->
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var calendarEl = document.getElementById('calendar');

                var calendar = new FullCalendar.Calendar(calendarEl, {
                    timeZone: 'UTC',
                    initialView: 'timeGridFourDay',
                    headerToolbar: {
                        left: 'prev,next',
                        center: 'title',
                        right: 'timeGridDay,timeGridFourDay'
                    },
                    views: {
                        timeGridFourDay: {
                            type: 'timeGrid',
                            duration: {days: 4},
                            buttonText: '4 day'
                        }
                    },
                    events: 'https://fullcalendar.io/api/demo-feeds/events.json'
                });

                calendar.render();
            });
        </script>

        <div>
            <canvas id="myChart"></canvas>
        </div>

        <script src=" https://cdn.jsdelivr.net/npm/chart.js@4.4.3/dist/chart.umd.min.js "></script>
    </body>
</html>
