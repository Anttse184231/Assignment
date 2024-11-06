<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="sample.shopping.Cart"%>
<%@page import="sample.shopping.ClothesDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Diep Store</title>
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
            background-color: #f0f8ff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #4682b4;
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
            border-bottom: 1px solid #b0d4ff;
        }
        th {
            background-color: #b0d4ff;
            color: #333;
        }
        tr:nth-child(even) {
            background-color: #f8fbff;
        }
        input[type="number"] {
            width: 60px;
            padding: 5px;
            border: 1px solid #b0d4ff;
            border-radius: 4px;
        }
        button, .links a {
            background-color: #87cefa;
            color: #fff;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin: 5px;
            transition: background-color 0.3s ease;
        }
        button:hover, .links a:hover {
            background-color: #4682b4;
        }
        .total {
            font-size: 1.2em;
            font-weight: bold;
            text-align: right;
            margin-top: 20px;
            color: #4682b4;
        }
        .message {
            background-color: #b0d4ff;
            color: #333;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .links {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Your Shopping Cart</h1>      
        <c:if test="${not empty sessionScope.CART}">
        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Remove</th>
                    <th>Edit</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="count" value="1"/>
                <c:set var="total" value="0" scope="page"/>
                <c:forEach var="clothes" items="${sessionScope.CART.cart.values()}">
                    <c:set var="itemTotal" value="${clothes.quantity * clothes.price}" scope="page"/>
                    <c:set var="total" value="${total + itemTotal}" scope="page"/>
                    <form>
                    <tr>
                        <td>${count}</td>
                        <td>${clothes.id}</td>
                        <td>${clothes.name}</td>
                        <td><input type="number" name="quantity" min="1" value="${clothes.quantity}"/></td>
                        <td><fmt:formatNumber value="${clothes.price}" pattern="###0.00"/></td>
                        <td>
                            <a href="MainController?action=Remove&Id=${clothes.id}">Remove</a>
                        </td>
                        <td>
                            <input type="hidden" name="Id" value="${clothes.id}"/>
                            <button type="submit" name="action" value="Edit">Edit</button>
                        </td>
                        <td><fmt:formatNumber value="${itemTotal}" pattern="###0.00"/></td>
                    </tr>
                    </form>
                    <c:set var="count" value="${count + 1}"/>
                </c:forEach>
            </tbody>
        </table>
        <div class="total">Total: <fmt:formatNumber value="${total}" pattern="###0.00"/>$</div>
        </c:if>        
        <c:if test="${not empty requestScope.MESSAGE}">
            <div class="message">${requestScope.MESSAGE}</div>
        </c:if>       
        <div class="links">
            <a href="MainController?action=Check">Check out!</a>
            <a href="MainController?action=Shopping_Page">Add more!</a>
        </div>
    </div>
</body>
</html>