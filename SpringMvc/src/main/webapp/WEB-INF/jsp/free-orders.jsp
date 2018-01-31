<%--
  Created by IntelliJ IDEA.
  User: PC-Administrator
  Date: 17.12.2017
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Free orders</title>
</head>
<body>
<h2>List of orders without a courier</h2>
<div>
    <table>
        <c:forEach items="${orders}" var="order">
            <form action="/free-orders/${order.orderId}" method="post">
                <tr>
                    <td>Order ${order.orderId}</td>
                    <td>${order.userId}</td>
                    <td>${order.orderCreationDate.toString()}</td>
                    <td>${order.status}</td>
                    <td style="text-align: center">${order.orderCost} ${rub}</td>
                    <td><input type="submit" value="${add}"></td>
                </tr>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </c:forEach>
    </table>
</div>
<div>
    <a href="<c:url value='/home'/>" >Перейти в профиль</a>
</div>

</body>
</html>
