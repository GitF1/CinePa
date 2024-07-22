<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ticket Details</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="/movie/page/bill/style/OrderDetail.css">

        <style>
            body {
                background-color: #f2f2f2;
                font-family: 'Arial', sans-serif;
            }
            .card {
                max-width: 700px;
                margin: auto;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }
            .card-header {
                background-color: #eae9ea !important;
                color: #fff;
                text-align: center;
                padding: 15px;
                border-radius: 10px 10px 0 0;
            }
            .card-body {
                padding: 15px;
            }
            .movie-info, .info-section {
                background-color: #ffffff;
                border-radius: 10px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ddd; /* Add border */
            }
            .movie-info .row {
                align-items: center;
            }
            .movie-info img {
                max-height: 120px;
                max-width: 120px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                transition: transform 0.2s ease;
            }
            .movie-info img:hover {
                transform: scale(1.05);
            }
            .movie-info h4 {
                margin: 5px 0;
                color: #333;
                font-weight: 600;
                font-size: 1rem;
            }
            .cinema-info {
                background-color: #fff;
                border-radius: 10px;
                padding: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                text-align: center;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
            }
            .cinema-info h4 {
                margin: 5px 0;
                font-weight: bold;
                color: #333;
            }
            .cinema-info .address {
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .seat {
                background-color: #f8f9fa;
                border-radius: 5px;
                padding: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                margin-bottom: 8px;
                border: 1px solid #ddd; /* Add border */
            }
            .no-canteen-items {
                text-align: center;
                font-style: italic;
                color: #6c757d;
            }
            .qr-code {
                height: 180px;
                width: 180px;
            }
            .item-details {
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
            }
            .canteen-item img {
                max-height: 100px;
                max-width: 100px;
                border-radius: 20px;
                transition: transform 0.2s ease, box-shadow 0.2s ease;
                border: 1px solid #ddd; /* Add border */
                margin-left: 10px
            }
            .canteen-item img:hover {
                transform: scale(1.1);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }

            .row {
                display: flex;
                align-items: stretch; /* Ensure all items have the same height */
            }
            .col-md-6 {
                display: flex;
                flex-direction: column;
            }
            .info-section {
                flex-grow: 1; /* Make the sections grow to fill available space */
            }
            .order-confirm{
                position: absolute;
                top: 15%;

            }
            .order-confirm img {
                height: 500px;
                width: 90%;
            }
            
        </style>
    </head>
    <body>
        <jsp:include page=".././landingPage/Header.jsp" />
        <div class="container mt-5">
            <div class="card">
                <div class="card-header">
                    <h1>Thông tin đặt vé xem phim</h1>
                </div>
                <div class="card-body">
                    <div class="movie-info">
                        <div class="row">
                            <div class="col-md-4 text-center">
                                <img src="${movieInfor.getImageURL()}" alt="Movie Image" class="img-fluid"/>
                            </div>
                            <div class="col-md-8">
                                <h4>Tên phim: ${movieInfor.getTitle()}</h4>
                                <h4>Thể loại: ${movieInfor.getGenres()}</h4>
                                <h4>Rạp chiếu: ${cinemaChain.getName()}</h4>
                                <h4>Địa chỉ: <span class="address">${cinema.getAddress()}, ${cinema.getProvince()}</span></h4>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <!-- Seats Section -->
                        <div class="col-md-6">
                            <div class="info-section">
                                <h2>Số ghế</h2>
                                <div>
                                    <c:forEach var="seat" items="${seats}">
                                        <div class="seat">
                                            ${seat.getName()} (Row: ${seat.getX()}, Seat: ${seat.getY()})
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <!-- Canteen Items Section -->
                        <div class="col-md-6">
                            <div class="info-section">
                                <h2>Thức ăn kèm</h2>
                                <div class="row justify-content-center">
                                    <c:choose>
                                        <c:when test="${empty canteenItems}">
                                            <div class="col-md-12 no-canteen-items">Không có thức ăn kèm</div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="item" items="${canteenItems}">
                                                <div class="col-md-6 text-center canteen-item mb-3">
                                                    <img src="${item.getImage()}" alt="${item.getName()}" class="img-fluid mb-2"/>
                                                    <div class="item-details">
                                                        <span class="item-name">${item.getName()}</span>
                                                        <span class="item-amount">x${item.getAmount()}</span>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- QR Code Section -->
                    <div class="info-section text-center">
                        <h2>Mã đặt vé</h2>
                        <img src="${order.QRCodeURL}" alt="QR Code" class="img-fluid qr-code"/>
                        <h4 class="mt-3">Đưa mã này cho nhân viên soát vé để nhận vé vào rạp</h4>
                    </div>
                    <c:if test="${order.status == 'CHECKED-IN'}">
                        <div class="order-confirm">
                            <img src="https://static.vecteezy.com/system/resources/previews/021/433/008/original/confirmed-rubber-stamp-free-png.png" />
                        </div>
                    </c:if>

                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
