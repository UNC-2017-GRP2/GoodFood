<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: PC-Administrator
  Date: 18.12.2017
  Time: 3:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My orders</title>
</head>
<h2>My orders</h2>
<div>
    <table>
        <c:forEach items="${orders}" var="order">
            <form action="/my-orders/${order.orderId}" method="post">
                <tr>
                    <td>Order ${order.orderId}</td>
                    <td>${order.userId}</td>
                    <td>${order.status}</td>
                    <td style="text-align: center">${order.orderCost} ${rub}</td>
                    <td><input type="submit" value="${del}"></td>
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
