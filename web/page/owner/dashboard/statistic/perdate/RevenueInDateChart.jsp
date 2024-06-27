<%@page import="util.ChartUtil"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="model.owner.dashboard.ChartModel"%>
<%@page import="DAO.owner.StatisticOwnerDAO"%>
<%@page import="jakarta.servlet.http.HttpServlet"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page import="DAO.owner.StatisticOwnerDAO"%>
<%@ page import="java.text.NumberFormat" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <%
        Integer cinemaChainID = (Integer) session.getAttribute("cinemaChainID");
        StatisticOwnerDAO dao = new StatisticOwnerDAO(getServletContext());
        ChartModel chart = dao.getRevenuePerDate(cinemaChainID, null, null, null);
        Double totalRevenue = chart.getTotalData();
        Double bestTimeSale = chart.getMaxData();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedBestTimeSale = (bestTimeSale != null && bestTimeSale != -1) ? numberFormat.format(bestTimeSale) : "N/A";
    %>
    <body>
        <div class="d-flex justify-content-between align-items-center">
            <h6 class="text-white">Sales Per Day</h6>
            <span>3%</span>
        </div>
        <div class="chart-container__perday">

            <jsp:include page="../../../../.././components/chart/ChartHiddenBG.jsp">
                <jsp:param name="id" value="salesChart" />
                <jsp:param name="label" value="Sales per Day" />
                <jsp:param name="data" value="<%= new Gson().toJson(chart.getData())%>"/>
                <jsp:param name="labels" value="<%= new Gson().toJson(chart.getLables())%>"/>
                <jsp:param name="type" value="line" />
                <jsp:param name="bg-color" value="rgb(255, 255, 255)" />
            </jsp:include>

            <%-- Include chart component dynamically --%>

        </div>
        <div class="stats text-white">
            <div>
                <h3>$ <%= totalRevenue%></h3>
                <p>Total Revenue</p>
            </div>
            <div>
                <h3><%= formattedBestTimeSale%></h3            
                <p>Best Hour </p>
            </div>
        </div>

    </body>
</html>
