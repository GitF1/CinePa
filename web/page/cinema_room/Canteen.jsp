<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.CanteenItem" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Combo Selection</title>
      
        <style>

            .combo-box {
                border: 1px solid #ddd;
                padding: 20px;
                width: 300px;
                background-color: #f9f9f9;
            }
            .combo {
                border-bottom: 1px solid #ddd;
                padding: 10px 0;

            }
            .combo img {
                width: 50px;
                height: auto;
            }
            .combo h3 {
                font-size: 16px;
            }
            .combo input {
                width: 50px;
                text-align: center;
            }
            .total {
                font-size: 18px;
                margin-top: 20px;
                right:10px;
            }
            .submit-btn {
                background-color: #ff4081;
                color: white;
                padding: 10px 20px;
                border: none;
                cursor: pointer;
                font-size: 16px;
                margin: 10x ;
                border-radius: 10px;
            }
            #background_box{
                position: fixed;
                top:0;
                left:0;
                right:0;
                bottom:0;
                background-color: rgba(0,0,0,0.2);
                z-index:999;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .wrapper-box{
                z-index:1000;
                padding: 20px 0px 20px 10px;
                background-color: #fff;
                border-radius: 10px;
                min-width: 450px;
            }
            .layer_box{
                max-height: 390px;
                overflow-y: overlay;
            }
            .btn{
                border: 1px solid #ff4055ad;
                border-radius: 50%;
                font-weight: 600;
                cursor: pointer;
                padding: 0px 5px;
                font-size: 20px;
            }
            .btn-decreament_amount{
                padding: 1px 8px;
            }

        </style>
        <script>
            function updateTotal() {
                let total = 0;
                const combos = document.querySelectorAll('.combo');
                combos.forEach(combo => {
                    const price = parseFloat(combo.getAttribute('data-price'));
                    const quantity = parseInt(combo.querySelector('input').value);
                    total += price * quantity;
                });
                document.getElementById('totalPrice').innerText = total.toFixed(2) + 'đ';
                document.getElementById('hiddenTotalPriceCanteen').value = total.toFixed(2);
            }

            function increment(id) {
                const input = document.getElementById(id);
                input.value = parseInt(input.value) + 1;
                updateTotal();
            }

            function decrement(id) {

                const input = document.getElementById(id);



                if (parseInt(input.value) > 0) {
                    input.value = parseInt(input.value) - 1;
                    updateTotal();
                }

            }
            function enableInputs() {
                const inputs = document.querySelectorAll('.input-amount');
                inputs.forEach(input => {
                    input.disabled = false;
                });
            }

            function callServlet(id, url, methodType) {
                enableInputs();
                document.getElementById(id).action = url;
                document.getElementById(id).method = methodType;
                document.getElementById(id).submit();
            }

            document.addEventListener("mousedown", function (event) {
                const comboBox = document.getElementById("comboBox");
                const background = document.getElementById("wrapper-box-select_canteen");
                const isClickInside = background.contains(event.target);

                if (!isClickInside && comboBox.style.display === "block") {
                    comboBox.style.display = "none";
                }
            });

            document.getElementById("wrapper-box-select_canteen").addEventListener("mousedown", function (event) {
                event.stopPropagation();
            });
        </script>
    </head>
    <body>
        <div id="comboBox" class="combo-box" style="display:none;">
            <div id="background_box" >
                <div id="wrapper-box-select_canteen" class="wrapper-box">
                    <div class="layer_box">

                        <c:forEach var="item" items="${canteenItems}">
                            <div class="combo" data-price="${item.getPrice()}">
                                <h3>${item.getName()} - ${item.getPrice()}đ</h3>
                                <div>
                                    <img src="${item.getImage()}" alt="${item.getName()}">
                                </div>
                                <div>
                                    <button class="btn btn-decreament_amount" type="button" onclick="decrement('quantity_${item.getItemID()}')">-</button>
                                    <input class="input-amount" type="text" id="quantity_${item.getItemID()}" name="quantity_${item.getItemID()}" value="0" min="0" onchange="updateTotal()" disabled="true">
                                    <button class="btn btn-increa_amount" type="button" onclick="increment('quantity_${item.getItemID()}')">+</button>
                                </div>
                            </div>
                        </c:forEach>


                    </div>

                    <div class="total">
                        <span>Tổng cộng: </span><span id="totalPrice">0đ</span>
                        <input type="hidden" id="hiddenTotalPriceCanteen" name="totalPriceCanteenItem" value="0">
                    </div>

                    <button  class="submit-btn" id="purchaseButton" style="margin-left: auto;" onclick="callServlet('bookingSeatForm', '/movie/user/booking/seat', 'POST')">Đặt vé</button>


                </div>

            </div>

        </div>
    </body>
   

</html>
