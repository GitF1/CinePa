<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Room</title>
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
        <h1 class="mt-5 mb-4 text-center">Create New Room</h1>
        <form action="createRoom" method="post">
            <input type="hidden" name="cinemaID" value="${param.cinemaID}">
            
            <div class="form-group">
                <label for="name">Room Name:</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="type">Room Type:</label>
                <input type="text" class="form-control" id="type" name="type" required>
            </div>
            <div class="form-group">
                <!--<label for="capacity">Capacity:</label>-->
                <input type="hidden" class="form-control" id="capacity" name="capacity" value="0" >
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <!--<label for="length">Length (in meters):</label>-->
                    <input type="hidden" class="form-control" id="length" name="length" value="0" >
                </div>
                <div class="form-group col-md-6">
                    <!--<label  for="width">Width (in meters):</label>-->
                    <input type="hidden" class="form-control" id="width" name="width" value="0">
                </div>
            </div>
            <div class="form-group">
                <label for="status">Status:</label>
                <select class="form-control" id="status" name="status" required>
                    <option value="Available">Available</option>
                    <option value="Unavailable">Unavailable</option>
                </select>
            </div>
            <div class="form-row">
                <div class="col-md-6">
                    <button type="submit" class="btn btn-primary btn-block">Create Room</button>
                </div>
                <div class="col-md-6">
                    <a href="javascript:history.go(-1);" class="btn btn-cancel btn-block">Cancel</a>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
