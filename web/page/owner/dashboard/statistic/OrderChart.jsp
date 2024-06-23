<%@page import="java.util.Locale"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="util.ChartUtil"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="DAO.owner.StatisticOwnerDAO"%>
<%@page import="jakarta.servlet.http.HttpServlet"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page import="model.owner.dashboard.ChartModel"%>
<%@page import="com.google.gson.Gson"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    
    <%
        Integer month = LocalDate.now().getMonthValue();
        Integer year = ChartUtil.getCurrentYear();
        Integer cinemaChainID = (Integer) session.getAttribute("cinemaChainID");
        StatisticOwnerDAO dao = new StatisticOwnerDAO(getServletContext());
        ChartModel chart = dao.getOrderCountsByDateInMonth(month, year, cinemaChainID);

        String monthName = ChartUtil.getCurrentMonthName();
    %>
    <body>
        <jsp:include page="../../../.././components/chart/Chart.jsp">
            <jsp:param name="id" value="chartLine"/>
            <jsp:param name="label" value="<%=  monthName%>"/>
            <jsp:param name="data" value="<%= new Gson().toJson(chart.getData())%>"/>
            <jsp:param name="labels" value="<%= new Gson().toJson(chart.getLables())%>"/>
            <jsp:param name="type" value="line"/>
            <jsp:param name="bg-color" value="rgb(255, 99, 130)"/>
        </jsp:include>
    </body>
</html>
