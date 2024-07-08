<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <title>Cinema Chain Information</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        .marquee {
            background: url('${cinemaChain.banner}') no-repeat center center;
            background-size: cover;
            color: #fff;
            padding: 50px 0; /* Restore original padding */
            position: relative;
        }
        .marquee::before {
            content: '';
            display: block;
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5); /* Dark overlay for better text readability */
        }
        .marquee-content {
            position: relative;
            z-index: 1;
            margin-top: 200px; /* Adjust margin-top */
        }
        .info-section {
            display: flex; /* Use Flexbox to align items side by side */
            align-items: center;
            justify-content: flex-start; /* Align items to the left */
            text-align: left;
            margin-top: 20px;
            padding-left: 10%; /* Add padding to move the section away from the left edge */
        }
        .info-section img {
            max-height: 130px;
            margin-right: 20px;
            border-radius: 15px;
        }
        .info-section div {
            text-align: left; /* Ensure text is left-aligned */
        }
        .info-section h1 {
            font-size: 2.0rem; /* Increase font size */
            margin: 0;
        }
        .info-section p {
            font-size: 1.2rem;
            margin: 5px 0;
        }
        .stars {
            margin: 10px 0; /* Adjust margin for spacing */
            color: #FFD700; /* Gold color for stars */
        }
    </style>
</head>
<body>
    <div class="marquee">
        <c:if test="${not empty cinemaChain}">
            <div class="container marquee-content">
                <div class="info-section">
                    <img src="${cinemaChain.avatar}" alt="Avatar" class="img-fluid">
                    <div>
                        <h1>${cinemaChain.name}</h1>
                        <div class="stars">
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star-half-alt"></i> <!-- Half star for variety -->
                        </div>
                        <p>${cinemaChain.information}</p>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${empty cinemaChain}">
            <p class="marquee-content">No cinema chain information available.</p>
        </c:if>
    </div>
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>
</body>
</html>
