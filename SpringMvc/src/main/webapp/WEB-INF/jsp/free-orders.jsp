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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="orders.freeOrders"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/basket-style.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap-form-helpers/2.3.0/js/bootstrap-formhelpers-phone.js"></script>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h2><spring:message code="orders.freeOrdersDesc"/></h2>
<div>
    <table>
        <c:forEach items="${orders}" var="order">
            <form action="/free-orders/${order.orderId}" method="post">
                <tr>
                    <td>Order ${order.orderId}</td>
                    <td>${order.userId}</td>
                    <td>${order.orderCreationDate.toString()}</td>
                    <td>${order.status}</td>
                    <td style="text-align: center">${order.orderCost} ₽</td>
                    <spring:message code="orders.takeOrder" var="placeholder"/>
                    <td><input type="submit" value="${placeholder}"></td>
                </tr>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </c:forEach>
    </table>
</div>

</body>
</html>
