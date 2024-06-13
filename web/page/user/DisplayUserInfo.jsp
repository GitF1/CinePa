<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/user/UserInfoCss/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/user/UserInfoCss/displayUserInfoCss.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/user/UserInfoCss/style.css"/>
        <title>Display User Page</title>

        <style>
            .user-avatar {
                width: 78px;
                height: 78px;
                background-image: url(https://th.bing.com/th/id/OIP.j_NOgofx2PhT1iPFXWbe0QHaNK?w=187&h=333&c=7&r=0&o=5&dpr=1.3&pid=1.7);
                margin: auto;
                border-radius: 50%;
                background-size: cover;
                margin-bottom: 31px;
            }
        </style>
    </head>

    <body>
        <script src="https://esgoo.net/scripts/jquery.js"></script>

        <jsp:include page="../landingPage/Header.jsp" />

        <div class="d-lg-flex half">
            <div class="bg order-1 order-md-2" style="background-image: url('${pageContext.request.contextPath}/page/user/BackGroundImage/bg_1.jpg');"></div>

            <div class="contents order-2 order-md-1">

                <div class="container">
                    <div class="row align-items-center justify-content-center">
                        <div class="col-md-7 py-3">
                            <div class="user-info">

                                <div class="user-avatar" style="background-image: url('${user.getAvatarLink()}') " >
        <!--                            <img src="${user.getAvatarLink()}" alt="User Avatar">-->
                                </div>

                                <div class="container">
                                    <form method="post" action="/movie/user/upload/avatar" enctype="multipart/form-data">
                                        <input type="file" name="file" />
                                        <input type="submit" value="Upload" />
                                    </form>
                                </div>
                            </div>
                            <h3 class="text-center my-5">View User's Information</h3>

                            <form action="/movie/user/information/update" method="post">
                                <div class="hide">
                                    <input type="text" id="username" name="userId" value="${user.getUserID()}" >
                                </div>

                                <div class="row ">
                                    <div class="col-md-6 hide">
                                        <div class="form-group first">
                                            <label for="fname">Username</label>
                                            <input type="text" class="form-control" id="fname" name="username" readonly value="${user.getUsername()}" >
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="fname">Full Name</label>
                                            <input type="text" class="form-control" id="fname" name="fullname" value="${user.getFullName()}">
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Email</label>
                                            <input type="text" class="form-control" id="lname" name="email" readonly value="${user.getEmail()}" >
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Birthday</label>
                                            <input type="date" class="form-control" id="lname" name="birthday" value="${user.getBirthday()}">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Province</label>
                                            <select class="form-control css_select" id="tinh" name="province" title="Chọn Tỉnh Thành">
                                                <option value="${user.getProvince()}">${user.getProvince()}</option>
                                            </select>
                                            <!-- Hidden field to store selected province name -->
                                            <input type="hidden" id="provinceName" name="provinceName" value="${user.getProvince()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="fname">Address</label>
                                            <input type="text" class="form-control" id="fname" name="address" value="${user.getAddress()}">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">District</label>
                                            <select class="form-control css_select" id="quan" name="district" title="Chọn Quận Huyện">
                                                <option value="${user.getDistrict()}">${user.getDistrict()}</option>
                                            </select>
                                            <!-- Hidden field to store selected district name -->
                                            <input type="hidden" id="districtName" name="districtName" value="${user.getDistrict()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 me-auto">
                                        <div class="form-group first">
                                            <label for="lname">Commune</label>
                                            <select class="form-control css_select" id="phuong" name="commune" title="Chọn Phường Xã">
                                                <option value="${user.getCommune()}">${user.getCommune()}</option>
                                            </select>
                                            <!-- Hidden field to store selected commune name -->
                                            <input type="hidden" id="communeName" name="communeName" value="${user.getCommune()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="row"></div>
                                <div class="d-flex mb-2  align-items-center">
                                    <div class="d-flex align-items-center"></div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <button class="btn px-5 btn-primary " type="submit"> Update now </button>
                                    </div>
                                </div>
                            </form>

                            <div class="row justify-content-end">

                                <div class="col-3">
                                    <form action="${pageContext.request.contextPath}/page/user/ChangePassword.jsp" class="text-center">
                                        <button class="btn p-1 btn-warning cpw ms-auto">Change Password </button>
                                    </form>
                                </div>

                                <div class="col-3">
                                   <form action="${pageContext.request.contextPath}"  class="text-center">
                                    <button class="btn  btn-warning cpw">Return </button>
                                </form>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                // Fetch provinces
                $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                    if (data_tinh.error == 0) {
                        $.each(data_tinh.data, function (key_tinh, val_tinh) {
                            $("#tinh").append('<option value="' + val_tinh.id + '">' + val_tinh.full_name + '</option>');
                        });

                        // Set the default selected province text
                        var defaultProvince = '${user.getProvince()}';
                        if (defaultProvince) {
                            $("#tinh").val(defaultProvince);
                        }

                        // On province change
                        $("#tinh").change(function () {
                            var idtinh = $(this).val();
                            var selectedProvince = $("#tinh option:selected").text();
                            $("#provinceName").val(selectedProvince); // Update hidden field with selected name

                            // Fetch districts
                            $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                                if (data_quan.error == 0) {
                                    $("#quan").html('<option value="0">Quận Huyện</option>');
                                    $("#phuong").html('<option value="0">Phường Xã</option>');
                                    $.each(data_quan.data, function (key_quan, val_quan) {
                                        $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                                    });

                                    // Set the default selected district text
                                    var defaultDistrict = '${user.getDistrict()}';
                                    if (defaultDistrict) {
                                        $("#quan").val(defaultDistrict);
                                    }

                                    // On district change
                                    $("#quan").change(function () {
                                        var idquan = $(this).val();
                                        var selectedDistrict = $("#quan option:selected").text();
                                        $("#districtName").val(selectedDistrict); // Update hidden field with selected name

                                        // Fetch wards
                                        $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                                            if (data_phuong.error == 0) {
                                                $("#phuong").html('<option value="0">Phường Xã</option>');
                                                $.each(data_phuong.data, function (key_phuong, val_phuong) {
                                                    $("#phuong").append('<option value="' + val_phuong.id + '">' + val_phuong.full_name + '</option>');
                                                });

                                                // Set the default selected ward text
                                                var defaultWard = '${user.getCommune()}';
                                                if (defaultWard) {
                                                    $("#phuong").val(defaultWard);
                                                }

                                                // On ward change
                                                $("#phuong").change(function () {
                                                    var selectedWard = $("#phuong option:selected").text();
                                                    $("#communeName").val(selectedWard); // Update hidden field with selected name
                                                });
                                            }
                                        });
                                    });
                                }
                            });
                        });
                    }
                });
            });
        </script>

    </body>
</html>
