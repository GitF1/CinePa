<%@ page import="model.MostReview" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DAO.MostReviewDAO" %><%@ page import="DAO.MostReviewDAO" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>

         <!--link font icon :-->  
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
         <!--link boostrap :-->  
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

        <style>
            iframe {
                width: 100%;
                height: 250px;
            }
        </style>


    </head>

    <body>


        <%

           // tao Servlet Context : 
                ServletContext context = getServletContext();

        
    //        tao list :
        ArrayList<MostReview> listMostReview = new ArrayList<>();

        try {
            // lay list tu database :
            listMostReview = DAO.MostReviewDAO.getInstance().getMostReview(context)  ;
        } catch (Exception ex) {
        }
                request.setAttribute("listMostReview", listMostReview);


        %>

 
       <!-- heading :  -->
        <div class="container">
            <h3 class="text-center my-5 fw-bold " style="color:rgb(216 45 139);">Bình luận  nổi bật</h3>
        </div>

        <!-- review cards :  -->
        <div class="container">
            <div class="row justify-content-center">

                <c:forEach var="mostReview" items="${listMostReview}">
                    <!-- card 1 :  -->
                    <div class="card col-lg-3 col-md-5 mb-5  rounded-4 p-0  mx-2 border border-2">
                        <img class=" rounded-bottom rounded-4" src="${mostReview.imageUrl}" alt="Card image" style="width:100% ;height: 272px ; background-size: cover"
                             data-bs-toggle="modal" data-bs-target="#myModal-${mostReview.movieId}" type="button">
                        <div class="card-body">

                            <!-- 1 user  -->
                            <div class="card-review d-flex justify-content-start ">
                                <!-- avatar :  -->
                                <div class="card-review-avt col-4 rounded-circle mr-5"
                                     style="width: 38px;height: 38px;  background-size: cover; background-image: url('${mostReview.avatarLink}');">

                                </div>
                                <!-- noi dung :  -->
                                <div class="card-review-content col-8 ml-5" style="margin-left: 10px;">
                                    <strong>${mostReview.fullname}</strong>
                                    <p class="fw-lighter" style="font-size: 14px; height: 50px; overflow: auto;">${mostReview.synopsis}</p>
                                </div>

                            </div>


                        </div>
                    </div>


                    <!-- The Modal -->
                    <div class="modal" id="myModal-${mostReview.movieId}">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">

                                <!-- modal header :  -->
                                <div class="modal-header ">
                                    <span class="fw-bold fs-6">${mostReview.title}</span>
                                    <span class="float-end " style="margin-left: auto;">OFFICIAL TRAILER</span>

                                </div>

                                <!-- link youtube :  -->
                                <div>

                                    <iframe src="https://www.youtube.com/embed/sz5TW2LaLPU?si=4V5dEbE4k66BpLb7"
                                            title="YouTube video player" frameborder="0"
                                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                                            referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
                                </div>

                                <!-- Modal body -->
                                <div class="modal-body">
                                    <div class="modal-desc">

                                        <!-- anh movie :  -->
                                        <div class="modal-desc-img"
                                             style="background-image: url('${mostReview.imageUrl}');">

                                        </div>

                                        <div>
                                            <!-- noi dung phim : -->
                                            <p style="height: 50px; overflow: auto;">
                                                ${mostReview.synopsis}
                                            </p>
                                        </div>
                                    </div>

                                </div>

                                <!-- Modal footer -->
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Đóng</button>
                                </div>

                            </div>
                        </div>
                    </div>


                </c:forEach>


            </div>
        </div>


        <!-- link js boostrap :  -->

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </body>
</html>
