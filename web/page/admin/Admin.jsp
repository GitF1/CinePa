<%-- 
    Document   : Admin
    Created on : May 19, 2024, 8:49:26 PM
    Author     : duyqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!Admin</h1>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/page/admin/CreateMovieForm.jsp">Create Movie</a>
        <a class="dropdown-item" href="UpdateMovieServlet">Edit Movie</a>
        <a class="dropdown-item" href="${pageContext.request.contextPath}/page/admin/Test.jsp">Create Movie</a>
        <a class="dropdown-item" href="OverviewGraphServlet">Overview</a>
    </body>
</html>
