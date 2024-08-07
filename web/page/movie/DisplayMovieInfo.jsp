<!DOCTYPE html>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Review" %>
<%@ page import="model.movie.MovieInfo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="page/movie/DisplayMovieInfoCss.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <title>${movie.title}</title>
        
        <style>
            .movie-style_frame iframe{
                width:  100% !important;
            }

            p.active-film-desc {
                font-weight: bold;
                margin: 0;
                transition: color 0.3s, cursor 0.3s;
                cursor: pointer;
                margin-top: 20px;
            }
            p.active-film-desc:hover {
                color: #ff69b4; /* Pink color on hover */
            }

            /* Additional styling to ensure consistency and improve appearance */
            p.active-film-type,
            p.active-film-rate {
                margin: 5px 0;
            }

            p.active-film-btn {
                display: inline-block;
                padding: 4px;
                background-color: #579b41;
                color: white;
                border-radius: 12px;
                font-size: 12px;
            }

            .trailer-button {
                height: 32px; /* Match height and width for a square shape */
                width: 32px;
                border-radius: 50%; /* Circular shape */
                border: 2px solid #ff4c4c; /* Pink border */
                background-color: transparent; /* Transparent background */
                display: flex; /* Flex to center the icon */
                align-items: center; /* Center vertically */
                justify-content: center; /* Center horizontally */
                cursor: pointer; /* Pointer cursor on hover */
                transition: background-color 0.3s, color 0.3s, border-color 0.3s; /* Smooth transition */
                margin-right: 8px; /* Space to the right */
                color: #ff4c4c; /* Text color matches the border */
            }

            .trailer-icon {
                width: 20px; /* Fit within the container */
                height: 20px; /* Fit within the container */
                fill: currentColor; /* Uses the current color of .trailer-button */
            }

            /* Hover effect */
            .trailer-button:hover {
                background-color: #ff4c4c; /* Background color on hover */
                color: white; /* Icon color on hover */
                border-color: white; /* Border color on hover */
            }

            .trailer-button:hover .trailer-icon {
                fill: white; /* Change icon color on hover */
            }

            .content {
                background: radial-gradient(circle, rgb(4 4 4 / 10%) 0%, rgb(35 46 36 / 80%) 0%);
                display: flex;
                align-items: center;
                align-items: flex-start;
            }

            .review-container {
                width: 100%;
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 16px;
                margin: 16px;
                margin-left: 0;
                font-family: Arial, sans-serif;
                /*background-color: white;*/
            }
            .user-info {
                display: flex;
                align-items: center;
            }
            .avatar {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                overflow: hidden;
                margin-right: 16px;
            }
            .avatar img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
            .user-name {
                font-size: 18px;
                font-weight: bold;
            }
            .rating {
                margin-top: 8px;
            }
            .comment {
                margin-top: 16px;
                margin-left: 6px;
                font-size: 14px;
            }

            #movie-rating-div {
                font-size: 16px;
                display: flex;
                align-items: center;
                font-family: Arial, sans-serif;
            }

            #movie-rating-strong {
                font-size: 32px;
            }

            #movie-rating-div img {
                vertical-align: middle;
                margin-right: 5px;
            }

            #total-rating-div {
                margin-top: 10px;
            }

            #write-review-button {
                margin-left: 59%;
                padding: 10px 20px;
                background-color: rgb(216, 45, 139);
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
            }

            /* User muốn viết feedback */
            .ratingMovieContainer {
                margin-top: 20px;
                margin-bottom: 20px;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 100%;
            }

            .rating-container {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }

            .button-star {
                border: none;
                background-color: #f8f8f8;
                cursor: pointer;
                margin: 0 5px;
            }

            .review-container-2 {
                margin-top: 20px;
            }

            textarea {
                width: 100%;
                border-radius: 8px;
                border: 1px solid #ccc;
                font-size: 18px;
                resize: none;
                font-family: 'Times New Roman', Times, serif;
                margin: 0;
            }

            #submitReviewMovieButton {
                padding: 10px 20px;
                background-color: rgb(216, 45, 139);
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
            }

            .submit-container {
                text-align: center; margin-top: 20px;
            }

        </style>
    </head>
    <body>
        <!--  phan noi dung phim :  -->
        <div class="content">
            <div class="container">
                <div class="row pdt50">

                    <div class="content-image col-3">
                        <div class="film-image js-trailer"
                             style="background-image: url('${movie.imageURL}');">

                        </div>
                    </div>
                    <div class="content-desc col-9">
                        <!-- title :  -->
                        <h2 class=" clWhite">${movie.title}</h2>
                        <!-- title 2 :  -->
                        <div class="title-2 clWhite">
                            <span class="clXam">${movie.title}</span>
                            <span class="mg20 clXam">${movie.year != 0 ? movie.year : "N/A"}</span>
                            <span class="clXam">${ movie.length != 0 ? movie.length : "N/A"}</span>
                        </div>
                        <!-- rating :  -->
                        <div class="mgt12">
                            <span class="clWhite">
                                <i>⭐ </i>
                                ${movie.rating}
                            </span>
                        </div>
                        <!-- noi dung :  -->
                        <h6 class="clWhite fw-700">Nội Dung</h6>
                        <div class="content-sumary">
                            ${ movie.synopsis != null ? movie.synopsis : "N/A"}
                        </div>
                        <!-- nhung cai li ti :  -->
                        <div class="liti">
                            <div class="inline-block tliti-date">
                                <div class="  fs-14 clXam">Ngày Chiếu</div>
                                <div class="clWhite fw-700"> ${movie.datePublished}</div>
                            </div>
                            <div class="inline-block tliti-type">
                                <div class=" fs-14 clXam  xxx">Thể Loại</div>
                                <div class="clWhite  fw-700">${movie.getGenresAsString()} </div>

                            </div>
                            <div class="inline-block titi-country">
                                <div class=" fs-14 clXam">Quốc Gia</div>
                                <div class="clWhite  fw-700">${movie.country}</div>

                            </div>
                        </div>

                        <!-- phan xem trailer , xem review :  -->
                        <div class="trailer-part d-flex align-items-center">
                            <span class="clWhite trailer-view js-trailer d-flex align-items-center" style="margin-right: 16px;">
                                <div class="trailer-button h-6 w-6 rounded-full border-2 border-pink-600 text-white/80" style="margin-right: 8px;">
                                    <svg class="trailer-icon" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg" style="width: 24px; height: 24px;">
                                    <g fill="none" fill-rule="evenodd">
                                    <path d="M34.667 24.335c0 .515-.529.885-.529.885l-14.84 9.133c-1.08.704-1.965.182-1.965-1.153V15.467c0-1.338.884-1.856 1.968-1.153L34.14 23.45c-.002 0 .527.37.527.885Z" fill="currentColor" fill-rule="nonzero"></path>
                                    </g>
                                    </svg>
                                </div>

                                Xem trailer
                            </span>

                            <a onclick="scrollToReviewSection();" style="display: inline-block; text-decoration: none; cursor: pointer;">
                                <span class="clWhite">
                                    <i class="fa fa-comments" aria-hidden="true"></i>
                                    Xem review
                                </span>
                            </a>
                            
                            <c:if test="${not empty sessionScope.userID}">
                                <c:set var="isFavoritedMovie" value="${requestScope.isFavoritedMovie}"></c:set>
                                <c:if test="${isFavoritedMovie == false}">
                                    <form id="addToFavoriteForm">
                                    <input type="hidden" name="isAddingToFavorite" value="true"/>
                                    <input type="hidden" id="favoritedAtInput" name="favoritedAt"/>
                                    <a onclick="addToFavorite();" style="display: inline-block; text-decoration: none; cursor: pointer; margin-left: -25px;">
                                        <span class="clWhite">
                                            <img src="assets/images/add-to-favorites.png"></img>
                                            Thêm vào yêu thích
                                        </span>
                                    </a>
                                    </form>
                                </c:if>
                                
                                <c:if test="${isFavoritedMovie == true}">
                                    <form id="deleteFavoriteMovieForm">
                                    <input type="hidden" name="deletedFavouriteMovieInput" value="${movie.movieID}"/>
                                    <input type="hidden" name="isDeletingInMovieInfo" value="true"/>
                                    <a onclick="deleteFavoriteMovie();" style="display: inline-block; text-decoration: none; cursor: pointer; margin-left: -25px;">
                                        <span class="clWhite">
                                            <img src="assets/images/delete-favorite.png"></img>
                                            Hủy yêu thích
                                        </span>
                                    </a>
                                    </form>                                    
                                </c:if>


                                <form id="viewFavouriteMoviesForm">
                                <a onclick="viewFavouriteMovies();" style="display: inline-block; text-decoration: none; cursor: pointer; margin-left: -25px;">
                                    <span class="clWhite">
                                        <img src="assets/images/view-favourite-movies.png"></img>
                                        Xem phim đã yêu thích
                                    </span>
                                </a>
                                </form>
                            </c:if>
                        </div>


                    </div>
                </div>
            </div>
        </div>

        <!--  phan lich chieu( Vinh ) + danh sach phim dang chieu :  -->
        <div class="container ">
            <div class="row">
                <div class="booking-ticket col-8">
                    <!-- Vinh  -->

                    <div class="schedule-movie">
                        <jsp:include page="./schedule/ScheduleMovie.jsp" />
                    </div>


                    <!-- Khai code phan review phim :  -->
                    <!--                    <div id="review">
                                            <h3 class="review-heading">Bình luận từ người xem</h3>
                    
                    <!-- Comment code a Khải về review phim -->
<!--                    <c:choose>
                        <c:when test="${not empty listReviewsdd}">
                            <c:forEach var="item" items="${listReviews}">
                                <div class="review-part">
                                    <div class="review-part-img" style="background-image: url('${item.userAvatarLink}')"></div>
                                    <div class="review-part-detail">
                                        <p class="review-part-name">${item.username}</p>
                                        <span class="review-part-time">${item.timeCreated}</span>
                                        <span class="review-part-cinepa">
                                            <i class="fa fa-check-circle" aria-hidden="true"></i>
                                            Đã mua qua CinePa
                                        </span>
                                    </div>
                                    <p class="review-part-rate">
                                        <i class="fa fa-star pdt0"></i>
                                ${item.rating}
                            </p>
                            <div class="review-part-content">
                                ${item.content}
                            </div>
                        </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p>Không có review nào.</p>
                        </c:otherwise>
                    </c:choose>
                </div>-->

                    <!-- KA code phần render review phim -->
                    <div>
                        <c:set var="userReviews" value="${requestScope.userReviews}"></c:set>
                            <h3 class="review-heading" id="reviewMovieHeader">Bình luận từ người xem</h3>
                            <div id="movie-rating-div">
                                <img id="starImage${star}" src="assets/images/yellow_star_icon.png" alt="white star"/> <strong id="movie-rating-strong">${movie.rating}</strong> <div id="total-rating-div">/10, ${userReviews.size()} đánh giá</div>
                            </div>
                            
                        <c:if test="${empty userReviews}">
                            <p>Không có review nào.</p>
                        </c:if>
                            
                        <form id="ratingMovieContainer" class="ratingMovieContainer" action="/movie/reviewmovie" method="post">
                            <input type="hidden" name="isSendingFeedback" value="true"/>
                        </form>
                         
                        <div class="review-container" style="margin-bottom: 80px;">
                            <c:forEach var="entry" items="${userReviews}">
                                <c:set var="review" value="${entry.key}"></c:set>
                                <c:set var="user" value="${entry.value}"></c:set>
                                <c:if test="${review.userID == userID && review.movieID == movie.movieID}">
                                    <input type="hidden" id="haveCommented"/>
                                    <div class="user-info">
                                        <div class="avatar">
                                            <img src="https://st.depositphotos.com/2001755/3622/i/450/depositphotos_36220949-stock-photo-beautiful-landscape.jpg" alt="Your Avatar">
                                        </div>
                                        <div class="user-name" style="color: red">Tôi</div>
                                    </div>
                                    <div id="movie-rating-div" style="margin-top: 10px; margin-left: 6px;">
                                        <img id="starImage${star}" src="assets/images/yellow_star_icon.png" alt="white star" style="width: 25px;"/> <strong id="total-rating-div">${review.rating}/10</strong>
                                    </div>
                                    <div class="comment">
                                        ${review.content}
                                    </div>
                                </c:if>

                            </c:forEach>
                        </div>
                            
                        <div class="review-container">
                            <c:forEach var="entry" items="${userReviews}">
                                <c:set var="review" value="${entry.key}"></c:set>
                                <c:set var="user" value="${entry.value}"></c:set>
                                <c:if test="${!(review.userID == userID && review.movieID == movie.movieID)}">
                                    <div class="user-info">
                                        <div class="avatar">
                                            <img src="https://st.depositphotos.com/2001755/3622/i/450/depositphotos_36220949-stock-photo-beautiful-landscape.jpg" alt="Your Avatar">
                                        </div>
                                        <div class="user-name">${user.fullName}</div>
                                    </div>
                                    <div id="movie-rating-div" style="margin-top: 10px; margin-left: 6px;">
                                        <img id="starImage${star}" src="assets/images/yellow_star_icon.png" alt="white star" style="width: 25px;"/> <strong id="total-rating-div">${review.rating}/10</strong>
                                    </div>
                                    <div class="comment">
                                        ${review.content}
                                    </div>
                                    <hr>
                                </c:if>

                            </c:forEach>
                        </div>

                        <!--<div>
                        <c:set var="listReviews" value="${requestScope.listReviews}"></c:set>
                        <c:forEach var="review" items="${listReviews}">
                            <div>${review}</div>
                        </c:forEach>
                    </div>-->
                    <c:set var="userID" value="${sessionScope.userID}"></c:set>
                    </div>
                </div>    
                
                <!-- KA xin hết -->

                <!-- Phan danh sach phim dang chieu :  -->
                <div class="list-active-films col-4">
                    <h3 class="pdc">Phim đang chiếu </h3>




                    <c:if test="${not empty listAvailableMovies}">

                        <c:forEach var="item" items="${listAvailableMovies}">
                            <div class="active-film row">
                                <div class="active-film-img col-4">
                                    <a href="HandleDisplayMovieInfo?movieID=${item.getMovieID()}" class="ctive-film-img-a" style="">
                                        <div class="active-film-img" style="background-image: url('${item.imageURL}')"></div>
                                    </a>
                                </div>
                                <div class="active-film-desc col-8">
                                    <p class="active-film-desc">${item.getTitle()}</p>
                                    <p class="active-film-type">${item.getGenresAsString()}</p>
                                    <p class="active-film-rate">
                                        <i>⭐ </i>${item.getRating()}
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>



                </div>
            </div>
        </div>


        <!-- phan modal :  -->
        <div class="modal-t js-modal">

            <div class="trailer-modal-container js-modal-container">

                <!-- modal header :  -->
                <div class="modal-header clWhite">
                    <span class="modal-header-name">${movie.title}</span>
                    <spana class="modal-header-official">OFFICIAL TRAILER #1</spana>
                    <span class="modal-header-close js-trailer-close">
                        <i class="fa fa-window-close" aria-hidden="true"></i>
                    </span>
                </div>


                <!-- modal link youtube :  -->

                <div class="movie-style_frame">
                    ${movie.linkTrailer}              
                </div>
                <!-- modal desc  -->
                <div class="modal-desc">

                    <div class="modal-desc-img"
                         style="background-image: url('${movie.imageURL}');">

                    </div>
                    <div class="modal-desc-content">
                        <span class="modal-desc-content-name">${movie.title}</span>
                        <span class="modal-desc-content-type"> ${genreString}</span>

                        <div class="modal-desc-content-detail">  ${movie.synopsis}</div>
                        <button class="modal-desc-book">Đặt vé</button>
                        <button class="modal-desc-close js-trailer-close">Đóng</button>
                    </div>
                </div>



            </div>
        </div>

        <script src="page/movie/DisplayMovieInfoJS.js"></script>
        <script src="javascript/style.js"></script>
        <script>
            let isWritingReview = false;
            let haveCommented = document.getElementById('haveCommented') === null ? false : true;
            
            const ratingMovieContainer = document.getElementById('ratingMovieContainer');
            const userID = ${userID};
            const movieID = ${movie.movieID};
            const canReview = ${requestScope.canReview};
            console.log(userID + ' ' + movieID + ' ' + canReview);
            if(!(userID === '' || canReview === false || haveCommented === true)) {
                const movieRatingDiv = document.getElementById('movie-rating-div');
                const writeReviewButton = document.createElement('button');
                writeReviewButton.id = 'write-review-button';
                writeReviewButton.onclick = () => goWriteReview();
                writeReviewButton.innerText = 'Viết bình luận';
                movieRatingDiv.appendChild(writeReviewButton);
            }
            function goWriteReview() {
                if(isWritingReview) {
                    isWritingReview = false;
                    ratingMovieContainer.innerHTML = '';
                    return;
                }
                
                isWritingReview = true;
                
                const ratingContainer = document.createElement('div');
                ratingContainer.classList.add('rating-container');
                for(let star = 1; star <= 10; ++star) {
                    const starButton = document.createElement('button');
                    starButton.id = 'starButton' + star;
                    starButton.classList.add('button-star');
                    starButton.type = 'button';
                    starButton.onclick = () => rateMovie(star);
                    
                    const starImage = document.createElement('img');
                    starImage.id = 'starImage' + star;
                    starImage.src = 'assets/images/white_star_icon.png';
                    starImage.alt = 'white star';
                    
                    starButton.appendChild(starImage);
                    ratingContainer.appendChild(starButton);
                }
                
                const reviewContainer = document.createElement('div');
                reviewContainer.classList.add('review-container-2');
                
                const reviewTextArea = document.createElement('textarea');
                reviewTextArea.id = 'reviewTextArea';
                reviewTextArea.name = 'reviewText';
                reviewTextArea.rows = 3;
                reviewTextArea.required = true;
                
                reviewContainer.appendChild(reviewTextArea);
                
                const submitContainer = document.createElement('div');
                submitContainer.classList.add('submit-container');

                const submitButton = document.createElement('button');
                submitButton.id = 'submitReviewMovieButton';
                submitButton.type = 'button';
                submitButton.onclick = () => sendFeedback();
                submitButton.textContent = 'Gửi đánh giá';
                submitContainer.appendChild(submitButton);
                
                const starOutput = document.createElement('input');
                starOutput.type = 'hidden';
                starOutput.id = 'starOutput';
                starOutput.name = 'starOutput';
                
                const timeCreatedOutput = document.createElement('input');
                timeCreatedOutput.type = 'hidden';
                timeCreatedOutput.id = 'timeCreatedOutput';
                timeCreatedOutput.name = 'timeCreatedOutput';

                ratingMovieContainer.appendChild(ratingContainer);
                ratingMovieContainer.appendChild(reviewContainer);
                ratingMovieContainer.appendChild(submitContainer);
                ratingMovieContainer.appendChild(starOutput);
                ratingMovieContainer.appendChild(timeCreatedOutput);
            }

            function rateMovie(star) {
                const starImage = document.getElementById('starImage' + star);
                if (starImage.src.includes("white_star_icon.png")) {
                    for (let i = 1; i <= star; ++i) {
                        document.getElementById('starImage' + i).src = "assets/images/yellow_star_icon.png";
                    }
                } else {
                    for (let i = 1; i <= 10; ++i) {
                        document.getElementById('starImage' + i).src = "assets/images/white_star_icon.png";
                    }
                }
                document.getElementById('starOutput').value = star;
            }

            function voteMovie(star, src) {
                for (let i = 1; i <= star; ++i) {
                    document.getElementById('starButton' + i).innerHTML = '';
                    const starImage = document.createElement('img');
                    starImage.src = src;
                    document.getElementById('starButton' + i).append(starImage);
                }
            }

            function sendFeedback() {
                let review = document.getElementById('reviewTextArea');
                let reviewContent = review.value;
                let starBtn = document.getElementById('starOutput');
                let rating = starBtn.value;
                if (rating === '') {
                    alert("Hãy chọn sao trước khi gửi đánh giá!");
                    return;
                }
                
                if(reviewContent === '') {
                    alert("Hãy viết bình luận trước khi gửi!");
                    return;
                }
                
                let timeCreatedOutput = document.getElementById('timeCreatedOutput');
                timeCreatedOutput.value = getCurrentDateTime();
                
                callServlet('ratingMovieContainer', 'HandleDisplayMovieInfo?movieID=' + movieID, 'POST');
            }
            
            
            function getCurrentDateTime() {
                const now = new Date();

                const year = now.getFullYear();
                const month = String(now.getMonth() + 1).padStart(2, '0'); 
                const day = String(now.getDate()).padStart(2, '0');

                const hours = String(now.getHours()).padStart(2, '0');
                const minutes = String(now.getMinutes()).padStart(2, '0');
                const seconds = String(now.getSeconds()).padStart(2, '0');
                const milliseconds = String(now.getMilliseconds()).padStart(3, '0');

                return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds + ':' + milliseconds;
            }

            function scrollToReviewSection() {
                document.getElementById('reviewMovieHeader').scrollIntoView({
                    behavior: 'smooth'
                });
            }
            
            function addToFavorite() {
                let favoritedAtInput = document.getElementById('favoritedAtInput');
                favoritedAtInput.value = getCurrentDateTime();
                
                callServlet('addToFavoriteForm', 'HandleDisplayMovieInfo?movieID=' + movieID, 'POST');
            }
            
            function deleteFavoriteMovie() {
                callServlet('deleteFavoriteMovieForm', 'myfavouritemovie', 'POST');
            }
            
            function viewFavouriteMovies() {
                callServlet('viewFavouriteMoviesForm', 'myfavouritemovie', 'GET');
            }
        </script>
    </body>
</html>