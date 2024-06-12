<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Footer Ví dụ</title>
        <!-- Bootstrap core CSS -->
        <link href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <!-- CSS tùy chỉnh -->
        <style>
            /* Tùy chỉnh footer */
            footer {
                background-color: #1c2331;
                color: white;
                padding: 60px 0;
            }
            /* Biểu tượng mạng xã hội */
            .social-icons a {
                color: white;
                margin-right: 15px;
                font-size: 24px;
            }
            /* Tên công ty */
            .company-name {
                color: white;
                font-weight: bold;
            }
            /* Liên kết footer */
            .footer-links a {
                color: white;
            }
        </style>
    </head>
    <body>

        <!-- Footer -->
        <footer class="text-center text-lg-start">
            <!-- Mạng xã hội -->
            <section class="d-flex justify-content-between p-4" style="background-color: #6351ce;">
                <!-- Trái -->
                <div class="me-5">
                    <span>Kết nối với chúng tôi trên mạng xã hội:</span>
                </div>
                <!-- Phải -->
                <div class="social-icons">
                    <a href="#" class="text-white"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="text-white"><i class="fab fa-twitter"></i></a>
                    <a href="#" class="text-white"><i class="fab fa-google"></i></a>
                    <a href="#" class="text-white"><i class="fab fa-instagram"></i></a>
                    <a href="#" class="text-white"><i class="fab fa-linkedin"></i></a>
                    <a href="#" class="text-white"><i class="fab fa-github"></i></a>
                </div>
            </section>
            <!-- Liên kết -->
            <section>
                <div class="container text-center text-md-start mt-5">
                    <!-- Dòng grid -->
                    <div class="row mt-3">
                        <!-- Cột grid -->
                        <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
                            <h6 class="company-name">CinePa</h6>
                            <hr class="mb-4 mt-0 d-inline-block mx-auto" style="width: 60px; background-color: #7c4dff; height: 2px;">
                            <p>Việc đặt vé xem phim chưa bao giờ đơn giản và dễ dàng như thế, chỉ với vài thao tác trên màn hình bạn đã có thể đặt vé xem bộ phim mình yêu thích mà không phải xếp hàng tại rạp.</p>
                        </div>
                        <!-- Cột grid -->
                        <!-- Cột grid -->
                        <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
                            <h6>Sản phẩm</h6>
                            <hr class="mb-4 mt-0 d-inline-block mx-auto" style="width: 60px; background-color: #7c4dff; height: 2px;">
                            <p><a href="#" class="footer-links">MDBootstrap</a></p>
                            <p><a href="#" class="footer-links">MDWordPress</a></p>
                            <p><a href="#" class="footer-links">BrandFlow</a></p>
                            <p><a href="#" class="footer-links">Bootstrap Angular</a></p>
                        </div>
                        <!-- Cột grid -->
                        <!-- Cột grid -->
                        <div class="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4">
                            <h6>Liên kết hữu ích</h6>
                            <hr class="mb-4 mt-0 d-inline-block mx-auto" style="width: 60px; background-color: #7c4dff; height: 2px;">
                            <p><a href="#" class="footer-links">Tài khoản của bạn</a></p>
                            <p><a href="#" class="footer-links">Trở thành Đối tác Liên kết</a></p>
                            <p><a href="#" class="footer-links">Tính phí vận chuyển</a></p>
                            <p><a href="#" class="footer-links">Trợ giúp</a></p>
                        </div>
                        <!-- Cột grid -->
                        <!-- Cột grid -->
                        <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
                            <h6>Liên hệ</h6>
                            <hr class="mb-4 mt-0 d-inline-block mx-auto" style="width: 60px; background-color: #7c4dff; height: 2px;">
                            <p><i class="fas fa-home me-3"></i> New York, NY 10012, US</p>
                            <p><i class="fas fa-envelope me-3"></i> info@example.com</p>
                            <p><i class="fas fa-phone me-3"></i> + 01 234 567 88</p>
                            <p><i class="fas fa-print me-3"></i> + 01 234 567 89</p>
                        </div>
                        <!-- Cột grid -->
                    </div>
                    <!-- Dòng grid -->
                </div>
            </section>
            <!-- Bản quyền -->
            <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
                © 2020 Bản quyền:
                <a class="text-white" href="https://mdbootstrap.com/">MDBootstrap.com</a>
            </div>
        </footer>
        <!-- Footer -->

        <!-- Bootstrap core JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.1/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.3.3/js/bootstrap.min.js"></script>
        <!-- Font Awesome -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>

    </body>
</html>
