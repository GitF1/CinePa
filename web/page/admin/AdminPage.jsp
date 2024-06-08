
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Page</title>
        <!-- link bootstrap :  -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!-- link font-icon :   -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    </head>

    <body>
        <div class="container-fluid">
            <!-- row heading :  -->


            <!-- row chinh :  -->
            <div class="row justify-content-around">

                <!-- phan side bar :  -->
                <ul class="col-md-2 " style="background-color: #4e73df; height: 100%; list-style-type: none;">
                    <a href="#" class="d-flex justify-content-center align-items-center py-3">
                        <!-- icon :  -->
                        <div>
                            <i class="fa fa-video-camera text-white" style="font-size: 30px;" aria-hidden="true"></i>
                        </div>
                        <div class="mx-3 text-white">CINEPA
                        </div>
                    </a>
                    <hr class="my-0  py-0 text-white">

                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="/movie/ManageBanUserServlet?type=user" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span> Quản lí người dùng </span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="/movie/ManageBanUserServlet?type=CinemaOwner" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-user-circle-o " style="font-size : 20px" aria-hidden="true"></i>
                            <span> Quản lí chủ rạp </span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center my-2">
                        <a href="#" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="#" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="#" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->


                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="#" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->

                    <!-- mot cai list ;  -->
                    <li class="text-white my-2 py-3 text-center">
                        <a href="#" style="text-decoration: none;" class="text-white">
                            <i class="fa fa-tachometer" aria-hidden="true"></i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <hr class="my-0  py-0 text-white">
                    <!-- end list :  -->
                </ul>

                <!-- phan noi dung chinh :  -->
                <div class="sidemain col-md-10 " style="height: 300px;">
                    <!-- phan nav :  -->
                    <nav class="d-flex justify-content-start mb-4 mt-2 shadow align-items-center">
                        <div class="input-group d-flex mr-auto ml-md-3 my-2 my-md-0 mw-100" style="width: 30%;">
                            <input type="text" class="form-control bg-light border-0 small " placeholder="Search for...">
                        </div>
                        <div class="input-group-append">
                            <button class="btn btn-primary">
                                <i class="fa fa-search" aria-hidden="true"></i>
                            </button>
                        </div>
                        <!-- div -->
                        <div class=" d-flex justify-content-evenly align-items-center " style="margin-left: auto; width: 30%; ">
                            <i class="fa fa-search" aria-hidden="true"></i>
                            <i class="fa fa-bell" aria-hidden="true"></i>
                            <i class="fa fa-commenting-o" aria-hidden="true"></i>
                            <i class="fa fa-bars" aria-hidden="true"></i>

                            <span>Admin page</span>
                            <div  class="rounded-circle d-inline-block" style="width: 50px; height: 50px; background-size: contain; background-image: url('https://th.bing.com/th/id/OIP.2dM29GTjbggyBjika5-jwAAAAA?rs=1&pid=ImgDetMain');"></div>
                        </div>

                    </nav>
                    <!-- phan doanh thu :  -->
                    <div class="row justify-content-around mb-5">
                        <div class="col-md-2 d-flex align-items-center justify-content-between border-start border-5 shadow border-primary">
                            <div style="border-radius: 10px;">
                                <p class="m-2 fw-bold fs-5 text-primary">Doanh thu </p>
                                <p>${doanhThu} vnd </p>
                            </div>
                            <i class="fa fa-search text-primary" style="font-size: 40px;" aria-hidden="true"></i>
                        </div>
                        <div class="col-md-2 d-flex align-items-center justify-content-between border-start border-5 shadow border-warning">
                            <div style="border-radius: 10px;">
                                <p class="m-2 fw-bold fs-5 text-warning">Nguời dùng </p>
                                <p>${tongUser} ngưởi</p>
                            </div>
                            <i class="fa fa-search text-warning " style="font-size: 40px;" aria-hidden="true"></i>
                        </div>
                        <div class="col-md-2 d-flex align-items-center justify-content-between border-start border-5 shadow border-danger">
                            <div style="border-radius: 10px;">
                                <p class="m-2 fw-bold fs-5 text-danger">Tổng phim</p>
                                <p>${tongPhim} phim</p>
                            </div>
                            <i class="fa fa-search text-danger " style="font-size: 40px;" aria-hidden="true"></i>
                        </div>
                        <div class="col-md-2 d-flex align-items-center justify-content-between border-start border-5 shadow border-info">
                            <div style="border-radius: 10px;">
                                <p class="m-2 fw-bold fs-5 text-info">Tổng review</p>
                                <p>${tongReview} review </p>
                            </div>
                            <i class="fa fa-search text-info" style="font-size: 40px;" aria-hidden="true"></i>
                        </div>

                    </div>

                    <!-- phan image them vo :  -->
                    <div class="row mb-5" style="background-image: url('https://github.com/vankhai-coder/Javascript-exercise-practice/blob/master/Hook/imageCinepa/a2.png?raw=true'); height: 300px; background-size: contain;">
                    </div>
                    <div class="row " style="background-image: url('https://github.com/vankhai-coder/Javascript-exercise-practice/blob/master/Hook/imageCinepa/a1.png?raw=true'); height: 300px; background-size: contain;">
                    </div>
                </div>
            </div>
        </div>



    </body>
</html>
