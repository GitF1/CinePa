<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ticket Details</title>
        <link rel="stylesheet" href="/movie/page/bill/style/OrderDetail.css">
    </head>
    <body>
        
        <jsp:include page=".././component/Header.jsp" />
        
        <div class="container">
            <div class="header">
                <h1>Order Details</h1>
            </div>
            <div class="content">
                <div class="info-section">
                    <h2>Thông tin phim</h2>
                    <div class="wrapper-movie_infor">
                        <div class="info-block">
                            <h4>Tên</h4>
                            <p>${movieInfor.getTitle()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Xuất Chiếu</h4>
                            <p>${cinema.getProvince()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Ngày</h4>
                            <p>${cinema.getProvince()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Rạp</h4>
                            <p>${cinemaChain.getName()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Địa Chỉ</h4>
                            <p>${cinema.getAddress()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Tỉnh Thành</h4>
                            <p>${cinema.getProvince()}</p>
                        </div>
                    </div>
                </div>

                <div class="info-section">
                    <h2>Thực đơn</h2>
                    <div class="canteen-items">
                        <c:forEach var="item" items="${canteenItems}">
                            <div class="canteen-item">
                                <img src="${item.getImage()}" alt="${item.getName()}"/>
                                <div class="item-details">
                                    <span class="item-name">${item.getName()}</span>
                                    <span class="item-amount">số lượng: ${item.getAmount()}</span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="info-section">
                    <h2>Seats</h2>
                    <div class="seats">
                        <c:forEach var="seat" items="${seats}">
                            <div class="seat">
                                ${seat.getName()} (Hàng: ${seat.getX()}, Ghế ${seat.getY()})
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
