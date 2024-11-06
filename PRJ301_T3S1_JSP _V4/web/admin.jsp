<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="sample.users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Galaxy Admin Page</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #e6f3ff;
            color: #333;
            margin: 0;
            padding: 0;
        }
        .content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
            background-color: #f0f8ff;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        h1, h2 {
            color: #4682b4;
            text-align: center;
        }
        .logout-btn, .delete-btn, input[type="submit"], button {
            background-color: #87cefa;
            color: #fff;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .logout-btn:hover, .delete-btn:hover, input[type="submit"]:hover, button:hover {
            background-color: #4682b4;
        }
        .search-form {
            display: flex;
            justify-content: center;
            margin-bottom: 1rem;
        }
        .search-form input[type="text"] {
            padding: 0.5rem;
            border: 1px solid #b0d4ff;
            border-radius: 5px 0 0 5px;
        }
        .search-form input[type="submit"] {
            border-radius: 0 5px 5px 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 2rem;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid #e6f3ff;
        }
        th {
            background-color: #b0d4ff;
            color: #333;
        }
        tr:nth-child(even) {
            background-color: #f8fbff;
        }
        input[type="text"] {
            width: 100%;
            padding: 0.5rem;
            border: 1px solid #b0d4ff;
            border-radius: 5px;
        }
        .error-message {
            color: #ff6b6b;
            text-align: center;
            margin-top: 1rem;
        }
        .space-background, .nebula {
            display: none;
        }
    </style>
</head>
<body>
    <div class="space-background"></div>
    <div class="nebula"></div>
    <div class="content">
        <h1>Welcome ${sessionScope.LOGIN_USER.fullName}</h1>
        <c:url var="logoutLink" value="MainController">
            <c:param name="action" value="Logout"></c:param>
        </c:url>
        <div style="text-align: center; margin-bottom: 1rem;">
            <a href="${logoutLink}" class="logout-btn">Logout</a>
        </div>
        <section class="user-management">
            <h2>User Management</h2>
            <form action="MainController" method="POST" class="search-form">
                <input type="text" name="search" value="${param.search}" placeholder="Search user"/>
                <input type="submit" name="action" value="Search"/>
            </form>
            <c:if test="${sessionScope.LIST_USER != null}">
                <c:if test="${not empty sessionScope.LIST_USER}">
                    <table class="user-table">
                        <thead>
                            <tr>
                                <th>NO</th>
                                <th>User ID</th>
                                <th>Full Name</th>
                                <th>Role ID</th>
                                <th>Password</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" varStatus="counter" items="${sessionScope.LIST_USER}">
                            <form action="MainController" method="POST">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>
                                        <input type="text" name="userID" value="${user.userID}" readonly/>
                                    </td>
                                    <td>
                                        <input type="text" name="fullName" value="${user.fullName}"/>
                                    </td>
                                    <td>
                                        <input type="text" name="roleID" value="${user.roleID}"/>
                                    </td>
                                    <td>${user.password}</td>
                                    <td>
                                        <input type="submit" name="action" value="Update"/>
                                        <input type="hidden" name="search" value="${param.search}"/>
                                    </td>
                                    <td>
                                        <c:url var="deleteLink" value="MainController">
                                            <c:param name="action" value="Delete"></c:param>
                                            <c:param name="userID" value="${user.userID}"></c:param>
                                            <c:param name="search" value="${param.search}"></c:param>
                                        </c:url>
                                        <a href="${deleteLink}" class="delete-btn">Delete</a>
                                    </td>
                                </tr>
                            </form>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="error-message">${requestScope.ERROR}</div>
                </c:if>
            </c:if>
        </section>
        <section class="product-management">
            <h2>Product Management</h2>
            <c:if test="${sessionScope.LIST_CLOTHES != null}">
                <c:if test="${not empty sessionScope.LIST_CLOTHES}">
                    <table class="product-table">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Product ID</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" varStatus="counter" items="${sessionScope.LIST_CLOTHES}">
                                <form action="MainController" method="POST">
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>
                                            <input type="text" name="productID" value="${product.getId()}" readonly/>
                                        </td>
                                        <td>
                                            <input type="text" value="${product.getName()}" readonly/>
                                        </td>
                                        <td>
                                            <input type="text" name="price" value="${product.getPrice()}"/>
                                        </td>
                                        <td>
                                            <input type="text" name="quantity" value="${product.getQuantity()}"/>
                                        </td>
                                        <td>
                                            <button type="submit" name="action" value="UpdateProduct">Update</button>
                                        </td>
                                    </tr>
                                </form>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </c:if>
        </section>
    </div>
</body>
</html>