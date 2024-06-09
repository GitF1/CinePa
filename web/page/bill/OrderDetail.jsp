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
        <div class="container">
            <div class="header">
                <h1>Ticket Details</h1>
            </div>
            <div class="content">
                <div class="info-section">
                    <h2>Movie Information</h2>
                    <div class="wrapper-movie_infor">
                        <div class="info-block">
                            <h4>Title</h4>
                            <p>${movieInfor.getTitle()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Cinema Chain</h4>
                            <p>${cinemaChain.getName()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Address</h4>
                            <p>${cinema.getAddress()}</p>
                        </div>
                        <div class="info-block">
                            <h4>Province</h4>
                            <p>${cinema.getProvince()}</p>
                        </div>
                    </div>

                </div>

                <div class="info-section">
                    <h2>Canteen Items</h2>
                    <div class="canteen-items">
                        <c:forEach var="item" items="${canteenItems}">
                            <div class="canteen-item">
                                <img src="${item.getImage()}" alt="${item.getName()}"/>
                                <div class="item-details">
                                    <span class="item-name">${item.getName()}</span>
                                    <span class="item-amount">x${item.getAmount()}</span>
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
                                ${seat.getName()} (Row: ${seat.getX()}, Seat: ${seat.getY()})
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
