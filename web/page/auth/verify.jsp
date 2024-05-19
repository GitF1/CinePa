<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Verify Account</title>
</head>
<body>
    <div class="container">
        <br/>
        <br/>
        <br/>
        <span>Hệ thống đã gửi mã kích hoạt đến Email của bạn.</span>
        <span>Xin vui lòng kiểm tra Email để lấy mã kích hoạt tài khoản của bạn.</span>
        <br/>
        <div>
            <form action="verifycode" method="post" class="log-reg-block sky-form">
                <div class="input-group">
                    <label for="authcode">Mã kích hoạt:</label><br>
                    <input type="text" id="authcode" name="authcode" class="form-control margin-top-20" required>
                </div>
                <input type="submit" value="Kích hoạt" class="btn-u btn-u-sea-shop margin-top-20">
            </form>
        </div>
        <% 
            String error = (String)request.getAttribute("error");
            if (error != null) {
                out.println("<p>" + error + "</p>");
            }
        %>
        <br/>
        <br/>
        <br/>
    </div>
</body>
</html>
