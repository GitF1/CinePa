<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .card {
                box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
                padding: 20px;
                margin-top: 20px;
            }
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    </head>
    <body>
        <h3 class="text-center mt-5">Quản lí đăng kí trở thành chủ rạp</h3>
        <div class="container">
            <div class="row d-flex justify-content-center text-center">
                <div class="col-sm-12 col-md-12">
                    <div class="card">
                        <c:choose>
                            <c:when test="${empty list}">
                                <p>Hiện tại không có yêu cầu trở thành chủ rạp nào!</p>
                            </c:when>
                            <c:otherwise>
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Email</th>
                                            <th>Birthday</th>
                                            <th>Fullname</th>
                                            <th>Districts</th>
                                            <th>Province</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="user" items="${list}">
                                            <tr>
                                                <td>${user.userID}</td>
                                                <td>${user.email}</td>
                                                <td>${user.birthday}</td>
                                                <td>${user.fullName}</td>
                                                <td>${user.district}</td>
                                                <td>${user.province}</td>
                                                <td>
                                                    <a class="btn btn-primary text-white" href="${pageContext.request.contextPath}/ChangeRegisterStatusServlet?id=${user.userID}&type=Accept">Accept</a>
                                                    <a class="btn btn-warning text-white" href="${pageContext.request.contextPath}/ChangeRegisterStatusServlet?id=${user.userID}&type=Reject">Reject</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:otherwise>
                        </c:choose>
                                <a class="btn btn-primary text-center d-block mx-auto text-white" href="/movie/admin">Quay về trang chủ</a>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </body>
</html>
