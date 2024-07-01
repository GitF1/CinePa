
<%@page import="util.RouterURL"%>
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

        <div class="container-fluid">
            <div class="row d-flex justify-content-center">
                <div class="col-sm-12 col-md-6">
                    <div class="card">
                        <ol>
                            <h3 class="text-center my-3">Yêu cầu của bạn đang được xử lí </h3>
                        </ol>
                        <a class="btn btn-primary text-center d-block mx-auto text-white" href="${pageContext.request.contextPath}">Quay về trang chủ</a>
                    </div>

                </div>
            </div>
        </div>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </body>
</html>
