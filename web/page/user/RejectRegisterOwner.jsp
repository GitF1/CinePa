
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

        <h3 class="text-center mt-5">Thông báo về việc từ chối yêu cầu làm chủ rạp</h3>
        <div class="container-fluid">
            <div class="row d-flex justify-content-center">
                <div class="col-sm-12 col-md-8">
                    <div class="card">
                        <ol>
                            <li>Thông tin chưa đầy đủ: Hồ sơ của bạn thiếu một số thông tin quan trọng cần thiết để xét
                                duyệt yêu cầu. Vui lòng cung cấp đầy đủ các tài liệu và thông tin cần thiết.</li>
                            <li>Kinh nghiệm chưa đủ: Chúng tôi nhận thấy rằng kinh nghiệm quản lý rạp của bạn chưa đáp ứng
                                đủ các tiêu chí của chúng tôi. Chúng tôi khuyến khích bạn tích lũy thêm kinh nghiệm và nộp
                                lại yêu cầu trong tương lai.</li>
                            <li>Khả năng tài chính chưa đáp ứng: Sau khi xem xét, chúng tôi nhận thấy khả năng tài chính
                                hiện tại của bạn chưa đủ để đảm bảo hoạt động ổn định của rạp.</li>

                        </ol>
                        <a class="btn btn-primary text-center d-block mx-auto text-white" href="${pageContext.request.contextPath}" >Quay về trang chủ</a>
                    </div>

                </div>
            </div>
        </div>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </body>
</html>
