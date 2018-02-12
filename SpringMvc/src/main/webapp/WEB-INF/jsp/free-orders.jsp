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
    <table class="table">


        <tr>
            <thead>
            <tr>
                <th scope="col"><spring:message code="orders.orderId"/></th>
                <th scope="col"><spring:message code="general.userId"/></th>
                <th scope="col"><spring:message code="orders.status"/></th>
                <th scope="col"><spring:message code="orders.items"/></th>
                <th scope="col"><spring:message code="orders.orderCreationDate"/></th>
                <th scope="col"><spring:message code="orders.timeSinceCreation"/></th>
                <th scope="col"><spring:message code="orders.cost"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <c:forEach items="${orders}" var="order">
                <th scope="row">${order.orderId}</th>
                <td>${order.userId}</td>
                <td>${order.status}</td>
                <td><c:forEach items="${order.orderItems}" var="item">
                    ${item.productName}<br />
                    ${item.productCost} ₽<br />
                </c:forEach>
                </td>
                <td>${order.orderCreationDate.toString()}</td>
                <td>${order.orderCreationDate.until(now, chr)}</td>

                <td style="text-align: center">${order.orderCost} ₽</td>
                    <form action="/free-orders/${order.orderId}" method="post">
                            <spring:message code="orders.takeOrder" var="placeholder"/>
                        <td><input type="submit" value="${placeholder}"></td>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </form>
            </tr>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            </c:forEach>
            </tbody>
    </table>
</div>

</body>
</html>
