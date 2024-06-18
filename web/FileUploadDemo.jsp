<%-- 
    Document   : FileUploadDemo
    Created on : Jun 18, 2024, 9:15:35 AM
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
        <form action="${pageContext.request.contextPath}/FileUploadDemoServlet" method ="post" enctype="multipart/form-data">
            <input type ="file" name="file">
            <button type="submit"/>
        </form>
    </body>
</html>
