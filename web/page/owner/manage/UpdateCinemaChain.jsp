<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Cinema Chain</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 500px;
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }
        .form-group label {
            font-weight: bold;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .btn-cancel {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-cancel:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Update Cinema Chain</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/owner/updateCinemaChain" method="post">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" name="name" id="name" value="${cinemaChain.name}" required>
            </div>
            <div class="form-group">
                <label for="information">Information:</label>
                <textarea class="form-control" name="information" id="information" required>${cinemaChain.information}</textarea>
            </div>
            <div class="form-group">
                <label for="avatar">Avatar URL:</label>
                <input type="text" class="form-control" name="avatar" id="avatar" value="${cinemaChain.avatar}" required>
            </div>
            <button type="submit" class="btn btn-primary mr-2">Update</button>
            <a href="javascript:history.go(-1);" class="btn btn-cancel">Cancel</a>
        </form>
    </div>
</body>
</html>
