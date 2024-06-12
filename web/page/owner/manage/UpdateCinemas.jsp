<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Cinema</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 500px;
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }
        .form-group label {
            font-weight: bold;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .btn-cancel {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-cancel:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center mt-3">Update Cinema</h1>
        <form action="updateCinema" method="post">
            <input type="hidden" name="cinemaID" value="${cinema.cinemaID}">
            <input type="hidden" name="cinemaChainID" value="${cinema.cinemaChainID}">
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" class="form-control" id="address" name="address" value="${cinema.address}" required>
            </div>
            <div class="form-group">
                <label for="province">Province:</label>
                <input type="text" class="form-control" id="province" name="province" value="${cinema.province}" required>
            </div>
            <div class="form-group">
                <label for="district">District:</label>
                <input type="text" class="form-control" id="district" name="district" value="${cinema.district}" required>
            </div>
            <div class="form-group">
                <label for="commune">Commune:</label>
                <input type="text" class="form-control" id="commune" name="commune" value="${cinema.commune}" required>
            </div>
            <div class="form-group">
                <label for="avatar">Avatar URL:</label>
                <input type="text" class="form-control" id="avatar" name="avatar" value="${cinema.avatar}">
            </div>
            <button type="submit" class="btn btn-primary mr-2">Update Cinema</button>
            <a href="javascript:history.go(-1);" class="btn btn-cancel">Cancel</a>
        </form>
    </div>
</body>
</html>
