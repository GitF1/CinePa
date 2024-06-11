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
     
        <%--<c:set var="message" value='${requestScope["movieList"]}' />--%>
        <table class="table">
            <form action = "UpdateMovieServlet" method="post">
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
                        <th scope="col">Trạng thái</th>
                        <th scope="col">Hành động</th>
                        <th scope="col">Xóa</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="movie" items='${requestScope["movieList"]}'>
                        <tr>
                            <td><c:out value="${movie.getMovieID()}"/></td>
                            <td class="imgdiv"><div ><img class="img" src="<c:out value="${movie.getImageURL()}"/>"/></div></td>
                            <td><c:out value="${movie.getTitle()}"/></td>
                            <td><c:out value="${movie.getDatePublished()}"/></td>
                            <td><c:out value="${movie.getSynopsis()}"/></td>
                            <td><c:out value="${movie.getCountry()}"/></td>
                            <td><c:out value="${movie.getLength()}"/></td>
                            <td><a target="_blank" href="<c:out value="${movie.getTrailerLink()}"/>"> <c:out value="${movie.getTitle()}"/><a/></td>
                            <td><c:out value="${movie.getStatus()}"/></td>
                            <td><button formaction="UpdateMovieServlet" value="<c:out value="${movie.getMovieID()}"/>" name ="movieID" type="submit" class="btn btn-primary">Edit</button></td>
                            <td>
                                <button type="button" class="btn-close" data-bs-toggle="modal" data-bs-target="#exampleModal<c:out value="${movie.getMovieID()}"/>">
                                 
                                </button>
                                </td>

                            <!--modal-->
                    <div class="modal fade" id="exampleModal<c:out value="${movie.getMovieID()}"/>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    Xác nhận xóa phim <c:out value="${movie.getTitle()}"/>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy bỏ</button>
                            
                                    <button formaction="DeleteMovieServlet" value="<c:out value="${movie.getMovieID()}"/>" name ="movieID" type="submit" class="btn btn-danger">Xóa</button>
                                </div>
                            </div>
                        </div>
                    </div>

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
