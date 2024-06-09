<%-- 
    Document   : Test
    Created on : Jun 9, 2024, 4:40:18 PM
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
        <h1>Hello World!</h1>
        <form action="${pageContext.request.contextPath}/TestServlet">
            <input name="title" value="a"/>
            <button type="submit">A</button>
        </form>
    </body>
</html>
