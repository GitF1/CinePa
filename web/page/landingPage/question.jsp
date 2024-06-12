
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
  
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    </head>
    <body>
      
        <!-- phần bạn hỏi Cinepa trả lời :  -->
        <div class="container my-5">
            <div class="row justify-content-center">
                <!-- hoi -->
                <div class="col-sm-12 col-md-5 mb-sm-3" style=" color: #d82d8b;">
                    <h3 class="fs-3 fw-bold text-center">Bạn hỏi, CinePa trả lời</h3>
                </div>

                <!-- tra loi :  -->
                <div class="col-sm-12 col-md-5">

                    <div id="accordion">
                        <div class="card">
                            <div class="card-header">
                                <a class="btn fw-bold" data-bs-toggle="collapse" href="#collapseOne">
                                    Lợi ích của việc mua vé xem phim trên CinePa ?
                                </a>
                            </div>
                            <div id="collapseOne" class="collapse show" data-bs-parent="#accordion">
                                <div class="card-body">
                                    Nhanh chóng, trực quan không cần ra mua vé trực tiếp tại rạp. Tiết kiệm thời gian và tiện lợi. Có nhiều chương trình khuyến mãi với giá vé vô cùng hấp dẫn. 
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-header">
                                <a class="collapsed btn fw-bold" data-bs-toggle="collapse" href="#collapseTwo">
                                    Lợi ích của việc mua vé xem phim trên CinePa ?

                                </a>
                            </div>
                            <div id="collapseTwo" class="collapse" data-bs-parent="#accordion">
                                <div class="card-body">
                                    Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-header">
                                <a class="collapsed btn fw-bold" data-bs-toggle="collapse" href="#collapseThree">
                                    Có thể mua vé xem phim kèm bắp nước hay không?
                                </a>
                            </div>
                            <div id="collapseThree" class="collapse" data-bs-parent="#accordion">
                                <div class="card-body">
                                    Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>


        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </body>
</html>
