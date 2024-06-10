<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Room</title>
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
    </style>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Update Room</h2>
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
            <button type="submit" class="btn btn-primary btn-block">Update</button>
        </form>
    </div>
</body>
</html>
