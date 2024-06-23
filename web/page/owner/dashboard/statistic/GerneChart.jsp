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
        ChartModel chart = dao.countGenresInCinemaChain(cinemaChainID);
       
    %>

    <body>
        <jsp:include page="../../../.././components/chart/Chart.jsp">
            <jsp:param name="id" value="chartDoughtNout"/>
            <jsp:param name="label" value="Gernes"/>
            <jsp:param name="data" value="<%= new Gson().toJson(chart.getData())%>"/>
            <jsp:param name="labels" value="<%= new Gson().toJson(chart.getLables())%>"/>
            <jsp:param name="type" value="doughnut"/>
            <jsp:param name="bg-color-arr" value="<%= new Gson().toJson(new ChartUtil().getBestChartColorsDogount(chart.getSize()))%>"/>
        </jsp:include>
    </body>
</html>
