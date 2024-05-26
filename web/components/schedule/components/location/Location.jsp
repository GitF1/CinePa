
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--<jsp:useBean id="ScheduleDAO" class="DAO.schedule.ScheduleDAO.java" scope="page" />--%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Select City or Province</title>
        <link rel="stylesheet" type="text/css" href="/movie/styles/schedule/location/location.css">
    </head>
    <body>

        

        <select name="cityProvince" id="cityProvince" onchange="fetchCityProvinceDetails()">
            <c:forEach var="city" items="${citiesProvinces}">
                <option value="${city}" ${city == citySelect ? 'selected' : ''}>${city}</option>
            </c:forEach>
        </select>
        
        <script>

            function fetchCityProvinceDetails() {
                var cityProvince = document.getElementById("cityProvince").value;
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "schedule", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                xhr.send("cityProvince=" + encodeURIComponent(cityProvince));
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        // Handle the server response
                        window.location.href = "/movie/schedule";
                    }
                }
            }
        </script>
    </body>
</html>
