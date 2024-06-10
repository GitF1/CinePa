<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Cinema Chain</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .img-item_theater {
            width: 100px;
            height: 100px;
            margin: auto;
            border-radius: 50%;
            border: 2px solid #ddd;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .img-item_theater:hover {
            opacity: 0.8;
            transform: scale(1.1);
        }
        .cinema-chain-name {
            margin-top: 10px;
            font-weight: bold;
            font-size: 1.1em;
        }
        .tooltip-inner {
            max-width: 200px;
            padding: 5px 10px;
            color: #fff;
            text-align: center;
            background-color: #000;
            border-radius: 5px;
        }
        .btn-primary {
            color: #fff;
            background-color: #dcb760;
            border-color: #ffc107;
        }
        .btn-secondary {
            color: #fff;
            background-color: #6c757d;
            border-color: #6c757d;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Manage Your Cinema Chain</h1>
        <c:choose>
            <c:when test="${not empty cinemaChain}">
                <div class="card text-center" data-toggle="tooltip" title="${cinemaChain.name}">
                    <div class="card-header">
                        <h2 class="cinema-chain-name">${cinemaChain.name}</h2>
                    </div>
                    <div class="card-body">
                        <p>${cinemaChain.information}</p>
                        <img class="img-item_theater" src="${cinemaChain.avatar == null ? '/movie/assets/images/logo_default_theater.jpg' : cinemaChain.avatar}" alt="${cinemaChain.name}">
                        <!-- Add more details if needed -->
                    </div>
                    <div class="card-footer">
                        <a href="${pageContext.request.contextPath}/owner/createCinema?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-primary">Create Cinema</a>
                        <a href="${pageContext.request.contextPath}/roomAdmin/cinemas?cinemaChainID=${cinemaChain.cinemaChainID}" class="btn btn-secondary">View Cinemas</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <p>No Cinema Chain found. <a href="<%= request.getContextPath() %>/owner/createCinemaChain">Create one</a></p>
            </c:otherwise>
        </c:choose>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function(){
            $('[data-toggle="tooltip"]').tooltip(); 
        });
    </script>
</body>
</html>
