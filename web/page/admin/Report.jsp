<%-- 
    Document   : Report
    Created on : Jun 11, 2024, 9:03:32 AM
    Author     : duyqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.graph.SalesData" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>

        </script>
        <div>
            <canvas id="weeklySalesChart"></canvas>
        </div>
        <script>
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
                            text: 'Chart.js Line Chart'
                        }
                    }
                },
            });

        </script>
        <div>
            <canvas id="myChart"></canvas>
        </div>



        <script>
//            const ctx = document.getElementById('myChart');
//
//            new Chart(ctx, {
//
//                type: 'doughnut',
//                data: {
//                    labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
//                    datasets: [{
//                            label: '# of Votes',
//                            data: [12, 19, 3, 5, 2, 3],
//                            borderWidth: 1
//                        }]
//                },
//                options: {
//                    scales: {
//                        y: {
//                            beginAtZero: true
//                        }
//                    }
//                }
//            });

        </script>
        <script src=" https://cdn.jsdelivr.net/npm/chart.js@4.4.3/dist/chart.umd.min.js "></script>
    </body>
</html>
