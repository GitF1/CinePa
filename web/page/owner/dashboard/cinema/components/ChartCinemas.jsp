<%@page import="java.util.List"%>
<%@page import="util.ChartUtil"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="model.owner.dashboard.ChartModel"%>
<%@page import="DAO.owner.StatisticOwnerDAO"%>
<%@page import="jakarta.servlet.http.HttpServlet"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page import="DAO.owner.StatisticOwnerDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    </head>

    <%
        List<ChartModel> listChart = (List<ChartModel>) request.getAttribute("listCombineChart");
        List<Integer> labels = (List<Integer>) request.getAttribute("labels");
    %>

    <body>
        <div class=" wrapper-chart mt-4 col-10 mx-auto mt-4">

            <jsp:include page="../../../../.././components/chart/ChartStakedCombine.jsp" >
                <jsp:param name="id" value="chartRevenuMovie"/>
                <jsp:param name="label" value="Renueve Sales"/>
                <jsp:param name="type" value="bar"/>
                <jsp:param name="data" value="<%= new Gson().toJson(listChart)%>"/>
                <jsp:param name="labels" value="<%= new Gson().toJson(labels)%>"/>
                <jsp:param name="bg-color" value="rgb(75, 192, 192)"/>
            </jsp:include>
        </div>



    </body>
</html>
