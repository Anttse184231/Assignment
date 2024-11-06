<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="sample.order.OrderDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Order History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e6f3ff;
            color: #333;
            line-height: 1.6;
        }
        h1 {
            color: #4a86e8;
            text-align: center;
            padding: 20px 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #b3d9ff;
            color: #333;
        }
        tr:nth-child(even) {
            background-color: #f2f9ff;
        }
        button {
            background-color: #4a86e8;
            color: white;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 4px;
        }
        button:hover {
            background-color: #3a76d8;
        }
        .details-table {
            margin-top: 10px;
            background-color: #e6f3ff;
        }
        .details-table th {
            background-color: #99ccff;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 15px;
            background-color: #4a86e8;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        a:hover {
            background-color: #3a76d8;
        }
    </style>
    <script>
        function toggleDetails(orderID) {
            var detailsRow = document.getElementById("details-" + orderID);
            if (detailsRow.style.display === "none" || detailsRow.style.display === "") {
                detailsRow.style.display = "table-row";
            } else {
                detailsRow.style.display = "none";
            }
        }
    </script>
</head>
<body>
<h1>Your Order History</h1>
<c:if test="${not empty sessionScope.USER_ORDER}">
    <table>
        <thead>
            <tr>
                <th>No</th>
                <th>Order ID</th>
                <th>Date</th>
                <th>Total</th>
                <th>View Details</th>
            </tr>
        </thead>
        <tbody>
            <c:set var="count" value="1"/>
            <c:forEach var="order" items="${sessionScope.USER_ORDER}">
                <c:if test="${order.status == 0}">
                    <tr>
                        <td>${count}</td>
                        <td>${order.orderID}</td>
                        <td><fmt:formatDate value="${order.date}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatNumber value="${order.total}" pattern="###0.00"/>$</td>
                        <td>
                            <button type="button" onclick="toggleDetails('${order.orderID}')">View Details</button>
                        </td>
                    </tr>
                    <tr id="details-${order.orderID}" style="display:none;">
                        <td colspan="5">
                            <table class="details-table">
                                <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>Product ID</th>
                                        <th>Product Name</th>
                                        <th>Quantity</th>
                                        <th>Price</th>
                                        <th>Total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="detailCount" value="1" />
                                    <c:forEach var="detail" items="${order.getDetails()}">
                                        <tr>
                                            <td>${detailCount}</td>
                                            <td>${detail.getProductID()}</td>
                                            <td>${detail.getProductName()}</td>
                                            <td>${detail.getQuantity()}</td>
                                            <td><fmt:formatNumber value="${detail.getPrice()}" pattern="###0.00"/></td>
                                            <td><fmt:formatNumber value="${detail.getQuantity() * detail.getPrice()}" pattern="###0.00"/></td>
                                        </tr>
                                        <c:set var="detailCount" value="${detailCount + 1}" />
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <c:set var="count" value="${count + 1}"/>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty sessionScope.USER_ORDER}">
    <h2>No order history available.</h2>
</c:if>
<div>
    <a href="user.jsp">Back</a>
</div>
</body>
</html>