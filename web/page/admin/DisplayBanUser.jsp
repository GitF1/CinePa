<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center my-4">

                <form action="SearchBanServlet" class="col-3">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Nhập tên..." name="str">
                        <button class="btn btn-success" type="submit">Tìm</button>

                        <input  name="type" value="${type}" type="hidden">

                    </div>
                </form>

            </div>
            <div class="row text-center justify-content-center mt-5">
                <h2>Tài khoản bị khóa </h2>
                <table class="table table-striped" style="width: 50%;">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Username</th>
                            <th scope="col">Fullname</th>
                            <th scope="col">Email</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${listBan}">
                            <tr>
                                <td>${user.userID}</td>
                                <td>${user.username}</td>
                                <td>${user.fullName}</td>
                                <td>${user.email}</td>
                                <td>
                                    <a class="btn btn-primary" href="BanUserServlet?type=${type}&userID=${user.userID}&isBanned=0">Mở khóa tài khoản</a>
                                </td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="row text-center justify-content-center mt-5">
                <h2>Tài khoản hoạt động </h2>
                <table class="table table-striped" style="width: 50%;">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Username</th>
                            <th scope="col">Fullname</th>
                            <th scope="col">Email</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${listUnBan}">
                            <tr>
                                <td>${user.userID}</td>
                                <td>${user.username}</td>
                                <td>${user.fullName}</td>
                                <td>${user.email}</td>
                                <td>
                                    <a class="btn btn-danger" href="BanUserServlet?type=${type}&userID=${user.userID}&isBanned=1">Khóa tài khoản</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </body>
</html>
