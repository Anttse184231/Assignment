<%@page import="java.util.List"%>
<%@page import="sample.shopping.ClothesDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Diep Store</title>
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
            max-width: 1000px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #4a86e8;
            text-align: center;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #b3d9ff;
        }
        th {
            background-color: #b3d9ff;
            color: #333;
        }
        tr:nth-child(even) {
            background-color: #f2f9ff;
        }
        input[type="number"] {
            width: 60px;
            padding: 5px;
        }
        input[type="submit"] {
            background-color: #4a86e8;
            color: white;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 4px;
        }
        input[type="submit"]:hover {
            background-color: #3a76d8;
        }
        .message {
            background-color: #b3d9ff;
            color: #333;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .links {
            text-align: center;
            margin-top: 20px;
        }
        .links a {
            display: inline-block;
            margin: 0 10px;
            padding: 10px 15px;
            background-color: #4a86e8;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .links a:hover {
            background-color: #3a76d8;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Diep's Store</h1>
        <c:set var="listClothes" value="${sessionScope.LIST_CLOTHES}"/>
        <c:if test="${not empty listClothes}">
            <table>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity in DB</th>
                        <th>Quantity</th>
                        <th>Add</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="clothes" items="${listClothes}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${clothes.id}</td>
                            <td>${clothes.name}</td>
                            <td>${clothes.price}</td>
                            <td>${clothes.quantity}</td>
                            <form action="MainController" method="POST">
                                <input type="hidden" name="productID" value="${clothes.id}">
                                <td><input type="number" name="quantity" min="1" value="1"></td>
                                <td><input type="submit" name="action" value="Add"></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${not empty requestScope.MESSAGE}">
            <div class="message">${requestScope.MESSAGE}</div>
        </c:if>
        <div class="links">
            <a href="MainController?action=View">View Cart</a>
            <a href="user.jsp">Back</a>
        </div>
    </div>
</body>
</html>