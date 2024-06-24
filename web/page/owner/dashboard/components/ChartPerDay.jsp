<%@page import="util.ChartUtil"%>
<%@page import="com.google.gson.Gson"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bootstrap Chart Component</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Chart.js -->

        <style>
            .card-custom_chart {
                border-radius: 15px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }
            .card-custom_chart .card-body {
                position: relative;
            }
            .chart-container__perday {
                position: relative;
                height: 150px;
                width: 100%;
            }
            .stats {
                display: flex;
                justify-content: space-between;
                padding-top: 15px;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-4 mb-4">
                    <div class="card-custom_chart" style="background-color: #00BCD4">
                        <div class="card-body">
                           
                            <jsp:include page="../statistic/perdate/RevenueInDateChart.jsp" />
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class=" card-custom_chart " style="background-color: #4BC0C0">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <h6 class="text-white">Visits</h6>
                                <span>9%</span>
                            </div>
                            <div class="chart-container">
                                <%-- Include chart component dynamically --%>
                                <jsp:include page="../../../.././components/chart/ChartHiddenBG.jsp">
                                    <jsp:param name="id" value="visitsChart" />
                                    <jsp:param name="label" value="Visits" />
                                    <jsp:param name="data" value="<%= new Gson().toJson(ChartUtil.numbers(12, 0, 1000, 0, 1))%>" />
                                    <jsp:param name="labels" value="<%= new Gson().toJson(ChartUtil.numbers(12, 1, 12, 0, 1))%>" />
                                    <jsp:param name="type" value="line" />
                                    <jsp:param name="bg-color" value="rgb(255, 255, 255)" />           
                                </jsp:include>
                            </div>
                            <div class="stats text-white">
                                <div>
                                    <h3>3562</h3>
                                    <p>Monthly Visits</p>
                                </div>
                                <div>
                                    <h3>96</h3>
                                    <p>Today Visits</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-4 mb-4">
                    <div class=" card-custom_chart " style="background: linear-gradient(to right, #fe5d70, #fe909d)">
                        <div class="card-body">
                            <jsp:include page="../statistic/perdate/OrderPerDateChart.jsp" />
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->


        <!--        <script>
                    // Chart.js settings
                    var ctxSales = document.getElementById('salesChart').getContext('2d');
                    var salesChart = new Chart(ctxSales, {
                        type: 'line',
                        data: {
                            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                            datasets: [{
                                    data: [120, 150, 180, 200, 220, 240, 300, 320, 340, 360, 380, 400],
                                    backgroundColor: 'rgba(255, 255, 255, 0.2)',
                                    borderColor: 'rgba(255, 255, 255, 1)',
                                    borderWidth: 2,
                                    pointBackgroundColor: 'rgba(255, 255, 255, 1)',
                                    pointBorderColor: 'rgba(255, 255, 255, 1)',
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
                    });
        
                    var ctxVisits = document.getElementById('visitsChart').getContext('2d');
                    var visitsChart = new Chart(ctxVisits, {
                        type: 'line',
                        data: {
                            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                            datasets: [{
                                    data: [80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300],
                                    backgroundColor: 'rgba(255, 255, 255, 0.2)',
                                    borderColor: 'rgba(255, 255, 255, 1)',
                                    borderWidth: 2,
                                    pointBackgroundColor: 'rgba(255, 255, 255, 1)',
                                    pointBorderColor: 'rgba(255, 255, 255, 1)',
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
                    });
                </script>-->
    </body>
</html>
