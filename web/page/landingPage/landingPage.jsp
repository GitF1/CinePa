<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>

        <!-- link font icon :  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!-- link boostrap :  -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

        
        
        <style>
            iframe {
                width: 100%;
                height: 250px;
            }
        </style>


    </head>
    <body>

        <jsp:include page="header.jsp" />

        <jsp:include page="banner.jsp" />

        <jsp:include page="../movie/Index.jsp" />

        <jsp:include page="mostReview.jsp" />


        <jsp:include page="question.jsp" />









    </body>
</html>
