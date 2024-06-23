<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Room</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 20px;
            padding: 20px;
        }

        h1 {
            background-color: #4e2c72;
            color: white;
            padding: 10px;
            margin: -20px -20px 20px;
            text-align: center;
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
        }

        .form-group label {
            font-weight: bold;
            margin-bottom: 5px;
            display: block;
        }

        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .btn-primary {
            background-color: #4e2c72;
            border-color: #4e2c72;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            display: inline-block;
            text-align: center;
            font-size: 16px;
        }

        .btn-primary:hover {
            background-color: #3a1d54;
        }

        .btn-cancel {
            background-color: #dc3545;
            border-color: #dc3545;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            display: inline-block;
            text-align: center;
            font-size: 16px;
            margin-left: 10px;
        }

        .btn-cancel:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mt-5 mb-4 text-center">Update Room</h1>
        <form action="updateRoom" method="post">
            <input type="hidden" name="roomID" value="${room.roomID}">
            <input type="hidden" name="cinemaID" value="${room.cinemaID}">
            <div class="form-group">
                <label>Name:</label>
                <input type="text" class="form-control" name="name" value="${room.name}" required>
            </div>
            <div class="form-group">
                <label>Type:</label>
                <input type="text" class="form-control" name="type" value="${room.type}" required>
            </div>
            <div class="form-group">
                <label>Capacity:</label>
                <input type="number" class="form-control" name="capacity" value="${room.capacity}" required>
            </div>
            <div class="form-group">
                <label>Length:</label>
                <input type="number" class="form-control" name="length" value="${room.length}" required>
            </div>
            <div class="form-group">
                <label>Width:</label>
                <input type="number" class="form-control" name="width" value="${room.width}" required>
            </div>
            <div class="form-group">
                <label>Status:</label>
                <select class="form-control" name="status" required>
                    <option value="Available" <c:if test="${room.status eq 'Available'}"></c:if>Available</option>
                    <option value="Unavailable" <c:if test="${room.status eq 'Unavailable'}"></c:if>Unavailable</option>
                </select>
            </div>
            <div class="form-row">
                <div class="col-md-6">
                    <button type="submit" class="btn btn-primary btn-block">Update</button>
                </div>
                <div class="col-md-6">
                    <a href="javascript:history.go(-1);" class="btn btn-cancel btn-block">Cancel</a>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
