<%-- 
    Document   : home
    Created on : May 19, 2024, 2:50:39 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Home Page</h1>
        <div>
            <c:set var="user" value="${sessionScope.user}"></c:set>
            Hello ${user.getFullName()}
        </div>
    </body>
</html>
