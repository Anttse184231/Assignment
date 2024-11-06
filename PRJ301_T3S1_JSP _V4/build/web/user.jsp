<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="sample.users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e6f3ff;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            background-color: #ffffff;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
        }
        h2 {
            color: #4a86e8;
            text-align: center;
            margin-bottom: 20px;
        }
        .user-info {
            background-color: #f2f9ff;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .label {
            font-weight: bold;
            color: #4a86e8;
        }
        .red {
            color: #ff4d4d;
        }
        .links {
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
        }
        .links a {
            display: inline-block;
            padding: 10px 15px;
            background-color: #4a86e8;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .links a:hover {
            background-color: #3a76d8;
        }
    </style>
</head>
<body>  
    <div class="container">
        <h2>User Information</h2>
        <div class="user-info">
            <p><span class="label">User ID:</span> ${sessionScope.LOGIN_USER.userID}</p>
            <p><span class="label">Password:</span> <b class="red">${sessionScope.LOGIN_USER.password}</b></p>
            <p><span class="label">Role ID:</span> ${sessionScope.LOGIN_USER.roleID}</p>
            <p><span class="label">Full name:</span> ${sessionScope.LOGIN_USER.fullName}</p>
        </div>
        <div class="links">
            <a href="MainController?action=MainController">Back</a>
            <a href="MainController?action=Shopping_Page">Shopping</a>
            <a href="MainController?action=History">History</a>
        </div>
    </div>
</body>
</html>