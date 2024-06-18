<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Cinema</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-container {
            margin: 50px auto;
            max-width: 500px;
        }
    </style>
</head>
<body>
    <div class="container form-container">
        <h2>Create Cinema</h2>
        <form action="${pageContext.request.contextPath}/owner/createCinema" method="post">
            <input type="hidden" name="cinemaChainID" value="${param.cinemaChainID}">
            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" class="form-control" id="address" name="address" required>
            </div>
            <div class="form-group">
                <label for="province">Province</label>
                <input type="text" class="form-control" id="province" name="province" required>
            </div>
            <div class="form-group">
                <label for="district">District</label>
                <input type="text" class="form-control" id="district" name="district" required>
            </div>
            <div class="form-group">
                <label for="commune">Commune</label>
                <input type="text" class="form-control" id="commune" name="commune" required>
            </div>
            <div class="form-group">
                <label for="avatar">Avatar URL</label>
                <input type="text" class="form-control" id="avatar" name="avatar">
            </div>
            <button type="submit" class="btn btn-primary">Create Cinema</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
