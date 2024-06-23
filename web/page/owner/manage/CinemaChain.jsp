<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Manage Cinema Chain</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: Arial, sans-serif;
            }
            .container {
                max-width: 800px;
                margin: auto;
                padding: 20px;
            }
            .card {
                border: none;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                transition: all 0.3s ease;
            }
            .card:hover {
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            }
            .card-header, .card-footer {
                background-color: #f8f9fa;
                border-bottom: 1px solid #dee2e6;
                border-radius: 10px 10px 0 0;
                padding: 10px;
            }
            .cinema-chain-name {
                font-size: 1.5em;
                color: #343a40;
            }
            .img-item_theater {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                border: 3px solid #ddd;
                transition: all 0.3s ease;
            }
            .img-item_theater:hover {
                opacity: 0.8;
                transform: scale(1.05);
            }
            .card-body {
                padding: 20px;
            }
            .card-footer {
                background-color: #f8f9fa;
                border-top: 1px solid #dee2e6;
                border-radius: 0 0 10px 10px;
                padding: 10px;
            }
            .btn-primary, .btn-secondary, .btn-warning {
                color: #fff;
                border-radius: 5px;
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
                font-size: 1.2em;
                color: #6c757d;
                margin-top: 20px;
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
        <div class="container">
            <h1 class="text-center mb-4">Manage Your Cinema Chain</h1>
            <c:choose>
                <c:when test="${not empty cinemaChain}">
                    <div class="card text-center" data-toggle="tooltip" title="${cinemaChain.name}">
                        <div class="card-header">
                            <h2 class="cinema-chain-name">${cinemaChain.name}</h2>
                        </div>
                        <div class="card-body">
                            <img class="img-item_theater mb-3" src="${cinemaChain.avatar == null ? '/movie/assets/images/logo_default_theater.jpg' : cinemaChain.avatar}" alt="${cinemaChain.name}">
                            <p>${cinemaChain.information}</p>
                            <!-- Add more details if needed -->
                        </div>
                        <div class="card-footer">
                            <div class="card-footer">
                                <a href="${pageContext.request.contextPath}/owner/createCinema?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-primary">Create Cinema</a>
                                <a href="${pageContext.request.contextPath}/owner/updateCinemaChain?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-warning">Update </a>
                                <a href="${pageContext.request.contextPath}/owner/cinemas?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-secondary">View Cinemas</a>
                            </div>

                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="no-cinema">
                        <a href="<%= request.getContextPath()%>/owner">Back Home</a>
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
    </body>
</html>
