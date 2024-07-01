<%-- 
    Document   : ListMovieAdmin
    Created on : Jun 9, 2024, 10:17:00 AM
    Author     : duyqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Movie" %>
<%@ page import="java.util.List" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <style>
            .imgdiv {
                width: 5%;

            }
            .img {
                width: 100%;
                height: auto; /* Maintain aspect ratio */
            }

            @media (max-width: 768px) {
                .imgdiv {
                    width: 10%; /* Adjust width for smaller screens */
                }
            }

            @media (max-width: 480px) {
                .imgdiv {
                    width: 20%; /* Adjust width for even smaller screens */
                }
            }
        </style>
    </head>
    <body>
     
        <table class="table">
            <form action = "/movie/approvemovie" method="post">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Hình ảnh</th>
                        <th scope="col">Tiêu đề</th>
                        <th scope="col">Ngày phát hành</th>
                        <th scope="col">Mô tả</th>
                        <th scope="col">Quốc gia</th>
                        <th scope="col">Thời lượng</th>
                        <th scope="col">Trailer Link</th>
                        <th scope="col">Trạng thái phim</th>
                        <th scope="col">Trạng thái yêu cầu</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="allPendingRequests" value="${requestScope.allRequests}"></c:set>
                    <c:forEach var="movie" items="${allRequests}">
                        <tr>
                            <td><c:out value="${movie.requestID}"/></td>
                            <td class="imgdiv"><div ><img class="img" src="<c:out value="${movie.getImageURL()}"/>"/></div></td>
                            <td><c:out value="${movie.getTitle()}"/></td>
                            <td><c:out value="${movie.getDatePublished()}"/></td>
                            <td><c:out value="${movie.getSynopsis()}"/></td>
                            <td><c:out value="${movie.getCountry()}"/></td>
                            <td><c:out value="${movie.getLength()}"/></td>
                            <td><a target="_blank" href="<c:out value="${movie.getTrailerLink()}"/>"> <c:out value="${movie.getTitle()}"/><a/></td>
                            <td><c:out value="${movie.getMovieStatus()}"/></td>
                            <td><c:out value="${movie.getRequestStatus()}"/></td>
                    </tr>

                </c:forEach>

                </tbody>
            </form>
        </table>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <script>
            const myModal = document.getElementById('myModal')
            const myInput = document.getElementById('myInput')

            myModal.addEventListener('shown.bs.modal', () => {
                myInput.focus()
            })

        </script>
    </body>
</html>
