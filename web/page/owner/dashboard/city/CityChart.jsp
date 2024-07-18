<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.google.gson.Gson"%>
<%@page import="util.ChartUtil"%>
<%@page import="model.owner.dashboard.ChartModel"%>
<%@ page import="java.util.List" %>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dash Board</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .main-dasboard{
                background-color: #f3f3f3;
                padding:10px 20px;
            }
            .wrapper-chart{
                width: 98% !important;
                padding: 20px;
                border-radius: 6px;
                background-color: #fff;
                font-size: 0.875em;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

            }
        </style>
    </head>

    <body>

        <%
      // Lấy chartModel từ thuộc tính của request
      ChartModel chartModel = (ChartModel) request.getAttribute("chartModel");
      List<String> labels = chartModel.getLables();
      List<Double> data = chartModel.getData();

      // Chuyển đổi danh sách labels và data sang JSON để truyền vào JavaScript
      String labelsJson = new Gson().toJson(labels);
      String dataJson = new Gson().toJson(data);
        %>

        <div class="container-fluid">
            <div class="row">

                <nav class="col-md-2 col-lg-2 d-md-block sidebar position-fixed top-0 left-0 bottom-0 ">
                    <jsp:include page="../components/Navigate.jsp"/>
                </nav>

                <main role="main" class="col-md-10 ml-sm-auto col-lg-10 p-0 m-0 ">
                    <div class="header_dashboard">
                        <jsp:include page="../../component/Header.jsp"/>
                    </div>
                    <div class="header_dashboard">

                        <form action="GetDataCityServlet" method="post" class="ml-5">
                            <label for="citySelect">Chọn Tỉnh:</label>
                            <select id="citySelect" name="city">
                                <c:forEach var="city" items="${listCity}">
                                    <c:choose>
                                        <c:when test="${city eq defaultCity}">
                                            <option value="${city}" selected>${city}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${city}">${city}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>



                            </select>
                            <br><br>

                            <label for="monthSelect">Chọn Tháng:</label>
                            <select id="monthSelect" name="month">
                                <c:forEach var="month" begin="1" end="12">
                                    <c:choose>
                                        <c:when test="${month eq defaultMonth}">
                                            <option value="${month}" selected>${month}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${month}">${month}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <br><br>

                            <label for="yearSelecxxt">Chọn Năm:</label>
                            <select id="yearSelect" name="year">
                                <c:forEach var="year" items="${[2024, 2023, 2022]}">
                                    <c:choose>
                                        <c:when test="${year eq defaultYear}">
                                            <option value="${year}" selected>${year}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${year}">${year}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <br><br>

                            <input type="hidden" name="listCity" value="<c:forEach var='city' items='${listCity}'><c:out value='${city}' /><c:if test='${not loop.last}'>,</c:if></c:forEach>" />

                                    <button type="submit">Submit</button>

                                </form>

                            </div>

                            <div  class="mb-5">
                        <%
                                
                        %>

                        <jsp:include page="../../../../components/chart/Chart.jsp" >
                            <jsp:param name="id" value="chartOrderTicketMovie"/>
                            <jsp:param name="label" value="Revenue Sales"/>
                            <jsp:param name="type" value="line"/>
                            <jsp:param name="data" value="<%= new Gson().toJson(data) %>"/>
                            <jsp:param name="labels" value="<%= new Gson().toJson(labels) %>"/>
                            <jsp:param name="bg-color" value="rgb(255, 159, 64)"/>
                        </jsp:include>
                    </div>
                </main>

            </div>
        </div>

    </body>

</html>
