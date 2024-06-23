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

        <h1 class="text-center mt-3">Điều khoản và dịch vụ </h1>
        <div class="container-fluid">
            <div class="row d-flex justify-content-center">
                <div class="col-sm-12 col-md-8">
                    <div class="card">
                        <ol>
                            <li>
                                <strong>Đăng ký và xác minh thông tin:</strong> Chủ rạp phải cung cấp thông tin chính xác và
                                đầy đủ khi đăng ký. Mọi thông tin sai lệch có thể dẫn đến việc tài khoản bị hủy bỏ.
                            </li>
                            <li>
                                <strong>Quyền lợi và trách nhiệm:</strong> Chủ rạp có trách nhiệm duy trì hoạt động của rạp
                                phim theo các quy định pháp luật hiện hành và không vi phạm các quy tắc đạo đức xã hội.
                            </li>
                            <li>
                                <strong>Chính sách hoàn tiền:</strong> Chủ rạp phải tuân thủ chính sách hoàn tiền của nền
                                tảng và giải quyết các khiếu nại của khách hàng một cách công bằng và minh bạch.
                            </li>
                            <li>
                                <strong>Bảo mật thông tin:</strong> Chủ rạp phải cam kết bảo mật thông tin khách hàng và
                                không được chia sẻ thông tin này với bên thứ ba mà không có sự đồng ý của khách hàng.
                            </li>
                            <li>
                                <strong>Quản lý và vận hành rạp:</strong> Chủ rạp phải đảm bảo rằng rạp phim hoạt động theo
                                tiêu chuẩn an toàn và chất lượng dịch vụ cao nhất để mang lại trải nghiệm tốt nhất cho khách
                                hàng.
                            </li>
                        </ol>
                        <a class="btn btn-primary text-center d-block mx-auto text-white" href="/movie/NewServlet">Đồng ý và tiếp tục</a>
                    </div>

                </div>
            </div>
        </div>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </body>
</html>
