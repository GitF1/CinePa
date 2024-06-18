<%-- 
    Document   : Test
    Created on : May 31, 2024, 2:18:33 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Button Positioning</title>
<style>
    #myDiv {
        position: relative;
        width: 100%;
        height: 1000px;
        background-color: #f0f0f0;
        border: 1px solid #ccc;
        padding: 20px;
    }

    #myButton {
        position: absolute;
        left: 85%;
        top: 20%;
    }
    
    #myButton2 {
        position: absolute;
        left: 150px;
        top: 150px;
    }
</style>
</head>
<body>

<div id="myDiv">
    <button id="myButton" onclick="alert('Button clicked!')">Click me</button>
    <button id="myButton2">Click me 2</button>
</div>

</body>
</html>

