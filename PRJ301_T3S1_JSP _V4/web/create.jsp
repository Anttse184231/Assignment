<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="sample.users.UserError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create User</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e6f3ff;
            color: #333;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            background-color: #f0f8ff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #4682b4;
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-top: 10px;
            color: #4682b4;
            font-weight: bold;
        }
        input[type="text"],
        input[type="password"] {
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #b0d4ff;
            border-radius: 4px;
            font-size: 16px;
        }
        input[type="submit"],
        input[type="reset"] {
            background-color: #87cefa;
            color: #fff;
            border: none;
            padding: 10px;
            margin-top: 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover,
        input[type="reset"]:hover {
            background-color: #4682b4;
        }
        .error-message,
        .general-error {
            color: #ff6b6b;
            font-size: 14px;
            margin-top: 5px;
        }
        .general-error {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <c:if test="${requestScope.USER_ERROR == null}">
        <c:set var="userError" value="<%= new sample.users.UserError() %>" />
    </c:if>
    <div class="container">
        <h1>Create User</h1>
        <form action="MainController" method="POST">
            <label for="userID">User ID</label>
            <input type="text" id="userID" name="userID" required>
            <div class="error-message">${requestScope.USER_ERROR.userIDError}</div>        
            <label for="fullname">Full Name</label>
            <input type="text" id="fullname" name="fullname" required>
            <div class="error-message">${requestScope.USER_ERROR.fullNameError}</div>          
            <label for="roleID">Role ID</label>
            <input type="text" id="roleID" name="roleID" value="US" readonly>            
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>            
            <label for="confirm">Confirm Password</label>
            <input type="password" id="confirm" name="confirm" required>
            <div class="error-message">${requestScope.USER_ERROR.confirmError}</div>         
            <input type="submit" name="action" value="Create">
            <input type="reset" value="Reset">
        </form>
        <div class="general-error">${requestScope.USER_ERROR.error}</div>
    </div>
</body>
</html>