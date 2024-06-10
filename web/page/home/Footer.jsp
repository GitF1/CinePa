<%-- 
    Document   : footer
    Created on : Jun 5, 2024, 9:54:08 PM
    Author     : VINHNQ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Footer Example</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .footer {
            background-color: #000;
            color: #fff;
            padding: 20px 0;
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
        }
        .footer-column {
            flex: 1;
            margin: 0 20px;
            min-width: 200px;
        }
        .footer-column h3 {
            color: #fff;
        }
        .footer a {
            color: #fff;
            text-decoration: none;
        }
        .footer a:hover {
            text-decoration: underline;
        }
        .footer .social-icons a {
            margin: 0 10px;
            display: inline-block;
        }
        .footer .app-links img {
            width: 130px;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <footer class="footer">
        <div class="footer-column">
            <h3>MUA VÉ XEM PHIM</h3>
            <a href="#">Lịch chiếu phim</a><br>
            <a href="#">Rạp chiếu phim</a><br>
            <a href="#">Phim chiếu rạp</a><br>
            <a href="#">Review phim</a><br>
            <a href="#">Top phim</a><br>
            <a href="#">Blog phim</a>
        </div>
        <div class="footer-column">
            <h3>DỊCH VỤ NỔI BẬT</h3>
            <a href="#">Vé xem phim</a><br>
            <a href="#">Bảo hiểm Ô tô</a><br>
            <a href="#">Vé máy bay</a><br>
            <a href="#">Ví nhân ái</a><br>
            <a href="#">Vay nhanh</a>
        </div>
        <div class="footer-column">
            <h3>CHĂM SÓC KHÁCH HÀNG</h3>
            <address>
                Địa chỉ: Lầu 6, Tòa nhà Phú Mỹ Hưng, số 8 Hoàng Văn Thái, khu phố 1, Phường Tân Phú, Quận 7, Thành phố Hồ Chí Minh<br>
                Hotline: 1900 5454 41 (Phí 1.000đ/phút)<br>
                Email: <a href="mailto:hotro@momo.vn">hotro@momo.vn</a><br>
                Tổng đài gọi ra: 028.7306.5555 - 028.9999.5555, các đầu số di động Brandname MoMo<br>
                <a href="#"><img src="huong-dan-tro-giup-tren-ung-dung-momo.png" alt="Hướng dẫn trợ giúp trên ứng dụng MoMo"></a>
            </address>
        </div>
        <div class="footer-column">
            <h3>HỢP TÁC DOANH NGHIỆP</h3>
            Hotline: 1900 636 652 (Phí 1.000đ/phút)<br>
            Email: <a href="mailto:merchant.care@momo.vn">merchant.care@momo.vn</a><br>
            Website: <a href="https://business.momo.vn">business.momo.vn</a><br>
            <a href="#"><img src="dang-ky-hop-tac.png" alt="Đăng ký hợp tác"></a>
        </div>
        <div class="footer-column">
            <h3>KẾT NỐI VỚI CHÚNG TÔI</h3>
            <div class="social-icons">
                <a href="#"><img src="facebook-icon.png" alt="Facebook"></a>
                <a href="#"><img src="linkedin-icon.png" alt="LinkedIn"></a>
                <a href="#"><img src="youtube-icon.png" alt="YouTube"></a>
            </div>
            <h3>TẢI ỨNG DỤNG TRÊN ĐIỆN THOẠI</h3>
            <div class="app-links">
                <a href="#"><img src="app-store-badge.png" alt="App Store"></a><br>
                <a href="#"><img src="google-play-badge.png" alt="Google Play"></a>
            </div>
            <h3>ĐƯỢC CHỨNG NHẬN BỞI</h3>
            <img src="da-dang-ky-bo-cong-thuong.png" alt="Đã đăng ký Bộ Công Thương">
        </div>
    </footer>
</body>
</html>
