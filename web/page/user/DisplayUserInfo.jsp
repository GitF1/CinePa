
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/user/UserInfoCss/bootstrap.min.css"/>
        <!-- Style -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/user/UserInfoCss/displayUserInfoCss.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/user/UserInfoCss/style.css"/>


        <title>display user Page</title>

    </head>


    <body>


        <div class="d-lg-flex half">
            <div class="bg order-1 order-md-2" style="background-image: url('${pageContext.request.contextPath}/page/user/BackGroundImage/bg_1.jpg');"></div>

            <div class="contents order-2 order-md-1">

                <div class="container">
                    <div class="row align-items-center justify-content-center">
                        <div class="col-md-7 py-5">
                            <div class="user-info">
                                <div class="user-avatar">
                                    <img src="${user.avatarLink}" alt="User Avatar">
                                </div>
                                <div class="container">
                                    <form method="post" action="avatarUploadServlet" enctype="multipart/form-data">
                                        <input type="file" name="file" />
                                        <input type="submit" value="Upload" />
                                    </form>
                                </div>
                                <h3>View User's Information</h3>
                            </div>

                            <p class="mb-4"></p>
                            <form action="${pageContext.request.contextPath}/updateUserInfo" method="post">

                                <div class="hide">
                                    <input type="text" id="username" name="userId" value="${user.userID}"  >
                                </div>

                                <div class="row ">
                                    <div class="col-md-6 hide">
                                        <div class="form-group first">
                                            <label for="fname">Username</label>
                                            <input type="text" class="form-control" id="fname"  name="username" readonly  value="${user.username}" >
                                        </div>
                                    </div>

                                    <div class="col-md-6">

                                        <div class="form-group last mb-3">
                                            <label for="password">Avatar Link </label>
                                            <input type="password" class="form-control" id="password" name="avatarUrl"  value="${user.avatarLink}">
                                        </div>
                                    </div>


                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Email</label>
                                            <input type="text" class="form-control" id="lname" name="email" readonly  value="${user.email}" >
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="fname">Full Name</label>
                                            <input type="text" class="form-control" id="fname"  name="fullname" value="${user.fullName}">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Birthday</label>
                                            <input type="text" class="form-control" id="lname" name="birthday" value="${user.birthday}">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="fname">Address</label>
                                            <input type="text" class="form-control" id="fname" name="address" value="${user.address}">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Province</label>
                                            <input type="text" class="form-control" id="lname" name="province" value="${user.province}">
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">District</label>
                                            <input type="text" class="form-control" id="lname" name="district"  value="${user.district} ">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group first">
                                            <label for="lname">Commune</label>
                                            <input type="text" class="form-control" id="lname" name="commune" value="${user.commune} " >
                                        </div>
                                    </div>
                                </div>
                                <div class="row">


                                </div>

                                <div class="d-flex mb-5 mt-4 align-items-center">
                                    <div class="d-flex align-items-center">

                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-3">
                                        <button class="btn px-5 btn-primary" > Update now </button>
                                    </div>
                                </div>

                            </form>




                            <form action="page/user/ChangePassword.jsp"  > 

                                <button class="btn px-5 btn-primary cpw"  >Change Password </button>
                            </form>
                            <!--button added to return to homepage-DuyND-->
                            <form action="test2.jsp"  > 

                                <button class="btn px-5 btn-primary cpw"  >Return </button>
                            </form>
                            <!--Removed logout from profile, was not working and you don't need to logout from profile - DuyND-->
                        </div>
                    </div>
                </div>
            </div>


        </div>



    </body>



</html>
