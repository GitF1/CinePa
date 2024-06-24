<%@page import="java.text.NumberFormat"%>
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
        Integer cinemaChainID = (Integer) session.getAttribute("cinemaChainID");
        StatisticOwnerDAO dao = new StatisticOwnerDAO(getServletContext());
        ChartModel chart = dao.getOrderTicketPerDate(cinemaChainID, null, null, null);
        Double totalOrder = chart.getTotalData();
        Double bestOrder = chart.getMaxData();

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedTotalOrder = (totalOrder != null || totalOrder == 0) ? numberFormat.format(totalOrder) : "N/A";
        String formattedBestOrder = (bestOrder != null || bestOrder == 0) ? numberFormat.format(bestOrder) : "N/A";

    %>
    <body>
        <div class="d-flex justify-content-between align-items-center">
            <h6 class="text-white">Order</h6>
            <span>9%</span>
        </div>
        <div class="chart-container">
            <%-- Include chart component dynamically --%>
            <jsp:include page="../../../../.././components/chart/ChartHiddenBG.jsp">
                <jsp:param name="id" value="orderChartPerDate" />
                <jsp:param name="label" value="Visits" />
                <jsp:param name="data" value="<%= new Gson().toJson(chart.getData())%>"/>
                <jsp:param name="labels" value="<%= new Gson().toJson(chart.getLables())%>"/>
                <jsp:param name="type" value="line" />
                <jsp:param name="bg-color" value="rgb(255, 255, 255)" />           
            </jsp:include>

        </div>
        <div class="stats text-white">
            <div>
                <h3><%= formattedTotalOrder%></h3>
                <p>Total Order</p>
            </div>
            <div>
                <h3><%= formattedBestOrder%></h3>
                <p>Best Order</p>
            </div>
        </div>
    </body>
</html>
