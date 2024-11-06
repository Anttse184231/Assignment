<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #e6f3ff;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(45deg, #b3d9ff, #d9eeff);
            z-index: -1;
        }
        .content {
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }
        h1 {
            color: #4a86e8;
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        input[type="text"], input[type="password"] {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #b3d9ff;
            border-radius: 5px;
            font-size: 16px;
        }
        input[type="submit"], input[type="reset"] {
            background-color: #4a86e8;
            color: white;
            border: none;
            padding: 10px;
            margin-top: 10px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover, input[type="reset"]:hover {
            background-color: #3a76d8;
        }
        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #4a86e8;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        #error {
            color: #ff4d4d;
            text-align: center;
            margin-top: 15px;
        }
        .g-recaptcha {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="background"></div>
    <div class="content">
        <div id="container">
            <h1>Login Information</h1>
            <form action="MainController" method="POST">
                <input type="text" name="userID" placeholder="User ID" required>
                <input type="password" name="password" placeholder="Password" required>
                <div class="g-recaptcha" data-sitekey="6LcPtgQqAAAAALHoRDrFJxUfIPiiTrjbMVqErN3h"></div>
                <input type="submit" name="action" value="Login">
                <input type="reset" name="Reset" value="Reset">
            </form>
            <a href="MainController?action=Create_Page">Create Account</a>
            <div id="error">
                <c:if test="${requestScope.ERROR==null}">
                    <c:set var="error" value="" />
                </c:if>
                ${requestScope.ERROR}
            </div>
        </div>
    </div>
</body>
</html>