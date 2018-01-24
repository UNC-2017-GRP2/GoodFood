<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="general.adminPanel"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<h2><spring:message code="general.adminPanel"/></h2>
<div>

    <table class="table">
        <c:forEach items="${orders}" var="order">
            <form action="/my-orders/${order.orderId}" method="post">
                <tr>
                    <th><spring:message code="orders.orderId"/></th>
                    <th><spring:message code="orders.userId"/></th>
                    <th><spring:message code="orders.status"/></th>
                    <td>${order.orderId}</td>
                    <td>${order.userId}</td>
                    <td>${order.status}</td>
                    <td><c:forEach items="${order.orderItems}" var="item">
                        ${item.productName}<br />
                        ${item.productCost}<br />
                    </c:forEach>
                    </td>
                    <td style="text-align: center">${order.orderCost} ${rub}</td>
                    <td><div class="col-xs-offset-4 col-xs-2">
                        <button type="submit" class="btn btn-default">${del}</button>
                    </div></td>
                </tr>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </c:forEach>
    </table>
</div>
</body>
</html>
