<%-- 
    Document   : DisplayCinemaDetail
    Created on : May 25, 2024, 11:39:28 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cinema Details</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            /* Custom CSS for additional styling */
            .details-container {
                max-width: 800px;
                margin: 50px auto;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                background-color: #ffffff;
            }
            .cinema-header {
                text-align: center;
                margin-bottom: 20px;
            }
            .cinema-header h1 {
                font-size: 2em;
                margin-bottom: 0.5em;
            }
            .cinema-header p {
                color: #6c757d;
            }
            .cinema-details {
                margin-top: 20px;
            }
            .cinema-details dt {
                font-weight: bold;
            }
            .cinema-details dd {
                margin-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="details-container">
                <div class="cinema-header">
                    <h1>${cinema.name}</h1>
                    <p>${cinema.address}, ${cinema.province}, ${cinema.district}, ${cinema.commune}</p>
                </div>
                <div class="cinema-details">
                    <dl class="row">
                        <dt class="col-sm-3">Name:</dt>
                        <dd class="col-sm-9">${cinema.name}</dd>
                        
                        <dt class="col-sm-3">Address:</dt>
                        <dd class="col-sm-9">${cinema.address}</dd>
                        
                        <dt class="col-sm-3">Province:</dt>
                        <dd class="col-sm-9">${cinema.province}</dd>
                        
                        <dt class="col-sm-3">District:</dt>
                        <dd class="col-sm-9">${cinema.district}</dd>
                        
                        <dt class="col-sm-3">Commune:</dt>
                        <dd class="col-sm-9">${cinema.commune}</dd>
                        
                        <!-- Add more fields as needed -->
                    </dl>
                </div>
            </div>
        </div>
    </body>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</html>
