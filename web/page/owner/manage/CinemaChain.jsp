<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Cinema Chain</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f0f2f5;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            .container {
                max-width: 900px;
                margin: auto;
                padding: 20px;
            }
            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transition: box-shadow 0.3s ease;
            }
            .card:hover {
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            }
            .card-header, .card-footer {
                background-color: #fff;
                border-bottom: 1px solid #e9ecef;
                border-radius: 15px 15px 0 0;
                padding: 15px;
            }
            .cinema-chain-name {
                font-size: 1.75em;
                color: #343a40;
            }
            .img-item_theater {
                width: 180px;
                height: 180px;
                border-radius: 50%;
                border: 5px solid #dee2e6;
                transition: transform 0.3s ease, opacity 0.3s ease;
            }
            .img-item_theater:hover {
                opacity: 0.9;
                transform: scale(1.05);
            }
            .card-body {
                padding: 30px;
            }
            .card-footer {
                background-color: #fff;
                border-top: 1px solid #e9ecef;
                border-radius: 0 0 15px 15px;
                padding: 15px;
            }
            .btn-primary, .btn-secondary, .btn-warning {
                color: #fff;
                border-radius: 8px;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
            }
            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #0056b3;
            }
            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
            }
            .btn-secondary:hover {
                background-color: #5a6268;
                border-color: #545b62;
            }
            .btn-warning {
                background-color: #ffc107;
                border-color: #ffc107;
            }
            .btn-warning:hover {
                background-color: #e0a800;
                border-color: #d39e00;
            }
            .no-cinema {
                text-align: center;
                font-size: 1.3em;
                color: #6c757d;
                margin-top: 30px;
            }
            .no-cinema a {
                color: #007bff;
                text-decoration: none;
                font-weight: bold;
            }
            .no-cinema a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <jsp:include page=".././component/Header.jsp"/>

        <div class="container">
            <h1 class="text-center mb-5">Manage Your Cinema Chain</h1>
            <c:choose>
                <c:when test="${not empty cinemaChain}">
                    <div class="card text-center" data-toggle="tooltip" title="${cinemaChain.name}">
                        <div class="card-header">
                            <h2 class="cinema-chain-name">${cinemaChain.name}</h2>
                        </div>
                        <div class="card-body">
                            <img class="img-item_theater mb-4" src="${cinemaChain.avatar == null ? '/movie/assets/images/logo_default_theater.jpg' : cinemaChain.avatar}" alt="${cinemaChain.name}">
                            <p>${cinemaChain.information}</p>
                        </div>
                        <div class="card-footer">
                            <a href="${pageContext.request.contextPath}/owner/createCinema?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-primary mr-2">Create Cinema</a>
                            <a href="${pageContext.request.contextPath}/owner/updateCinemaChain?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-warning mr-2">Update</a>
                            <a href="${pageContext.request.contextPath}/owner/cinemas?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-secondary">View Cinemas</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="no-cinema">
                        <p>No cinema chain found.</p>
                        <a href="<%= request.getContextPath()%>/owner/dashboard">Back Home</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                $('[data-toggle="tooltip"]').tooltip();
            });
        </script>

        <jsp:include page="/page/home/Footer.jsp" />

    </body>
</html>
