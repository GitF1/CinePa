<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ page
import="java.util.List" %> <%@ page import="model.Review" %> <%@ page
import="model.movie.MovieInfo" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="page/movie/DisplayMovieInfoCss.css" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    />
    <style>
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

      .content {
        background-image: url("${movie.imageURL}");
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        padding: 50px 0;
      }
    </style>
  </head>
  <body>
    <!--  phan noi dung phim :  -->
    <div
      class="content"
      style="background-image: url('assets/images/4477479.jpg')"
    >
      <div class="container">
        <div class="row pdt50">
          <div class="content-image col-3">
            <div
              class="film-image js-trailer"
              style="background-image: url('${movie.imageURL}')"
            ></div>
          </div>
          <div class="content-desc col-9">
            <!-- title :  -->
            <h2 class="clWhite">${movie.title}</h2>
            <!-- title 2 :  -->
            <div class="title-2 clWhite">
              <span class="clXam">${movie.title}</span>
              <span class="mg20 clXam"
                >${movie.year != 0 ? movie.year : "N/A"}</span
              >
              <span class="clXam"
                >${ movie.length != 0 ? movie.length : "N/A"}</span
              >
            </div>
            <!-- rating :  -->
            <div class="mgt12">
              <span class="clWhite">
                <i>⭐</i>
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
                <div class="fs-14 clXam">Ngày Chiếu</div>
                <div class="clWhite fw-700">${movie.datePublished}</div>
              </div>
              <div class="inline-block tliti-type">
                <div class="fs-14 clXam xxx">Thể Loại</div>
                <div class="clWhite fw-700">${genreString}</div>
              </div>
              <div class="inline-block titi-country">
                <div class="fs-14 clXam">Quốc Gia</div>
                <div class="clWhite fw-700">${movie.country}</div>
              </div>
            </div>

            <!-- phan xem trailer , xem review :  -->
            <div class="trailer-part d-flex align-items-center">
              <span
                class="clWhite trailer-view js-trailer d-flex align-items-center"
                style="margin-right: 16px"
              >
                <div
                  class="h-6 w-6 rounded-full border-2 border-pink-600 text-white/80"
                  style="margin-right: 8px"
                >
                  <svg
                    viewBox="0 0 48 48"
                    xmlns="http://www.w3.org/2000/svg"
                    style="width: 24px; height: 24px"
                  >
                    <g fill="none" fill-rule="evenodd">
                      <path
                        d="M34.667 24.335c0 .515-.529.885-.529.885l-14.84 9.133c-1.08.704-1.965.182-1.965-1.153V15.467c0-1.338.884-1.856 1.968-1.153L34.14 23.45c-.002 0 .527.37.527.885Z"
                        fill="currentColor"
                        fill-rule="nonzero"
                      ></path>
                    </g>
                  </svg>
                </div>
                Xem trailer
              </span>

              <a
                href="#review"
                style="display: inline-block; text-decoration: none"
              >
                <span class="clWhite">
                  <i class="fa fa-comments" aria-hidden="true"></i>
                  Xem review
                </span>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--  phan lich chieu( Vinh ) + danh sach phim dang chieu :  -->
    <div class="container">
      <div class="row">
        <div class="booking-ticket col-8">
          <!-- Vinh  -->
          <div class="schedule-movie">
            <jsp:include page="./schedule/ScheduleMovie.jsp" />
          </div>
          <!-- Khai code phan review phim :  -->
          <div id="review">
            <h3 class="review-heading">Bình luận từ người xem</h3>
            <c:choose>
              <c:when test="${not empty listReviewsdd}">
                <c:forEach var="item" items="${listReviews}">
                  <div class="review-part">
                    <div
                      class="review-part-img"
                      style="background-image: url('${item.userAvatarLink}')"
                    ></div>
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
                    <div class="review-part-content">${item.content}</div>
                  </div>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <p>Không có review nào.</p>
              </c:otherwise>
            </c:choose>
          </div>
        </div>

        <!-- Phan danh sach phim dang chieu :  -->
        <div class="list-active-films col-4">
          <h3 class="pdc">Phim đang chiếu</h3>
          <c:if test="${not empty listAvailableMovies}">
            <c:forEach var="item" items="${listAvailableMovies}">
              <div class="active-film row">
                <div class="active-film-img col-4">
                  <a
                    href="HandleDisplayMovieInfo?movieID=${item.getMovieID()}"
                    class="active-film-img-a"
                  >
                    <div
                      class="active-film-img"
                      style="background-image: url('${item.imageURL}')"
                    ></div>
                  </a>
                </div>
                <div class="active-film-desc col-8">
                  <p class="active-film-desc">${item.getTitle()}</p>
                  <p class="active-film-type">Genre: Kinh dị</p>
                  <p class="active-film-rate"><i>⭐ </i>${item.getRating()}</p>
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
          <span class="modal-header-official">OFFICIAL TRAILER #1</span>
          <span class="modal-header-close js-trailer-close">
            <i class="fa fa-window-close" aria-hidden="true"></i>
          </span>
        </div>
        <!-- modal link youtube :  -->
        <div>${movie.linkTrailer}</div>
        <!-- modal desc  -->
        <div class="modal-desc">
          <div
            class="modal-desc-img"
            style="background-image: url('${movie.imageURL}')"
          ></div>
          <div class="modal-desc-content">
            <span class="modal-desc-content-name">${movie.title}</span>
            <span class="modal-desc-content-type"> ${genreString}</span>
            <div class="modal-desc-content-detail">${movie.synopsis}</div>
            <button class="modal-desc-book">Đặt vé</button>
            <button class="modal-desc-close js-trailer-close">Đóng</button>
          </div>
        </div>
      </div>
    </div>

    <script src="page/movie/DisplayMovieInfoJS.js"></script>
  </body>
</html>
