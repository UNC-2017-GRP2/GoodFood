<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Project</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>

<jsp:include page="navbar.jsp" />

<body>
<h2>My orders</h2>
<div>

    <table class="table">
        <c:forEach items="${orders}" var="order">

                <tr>
                    <td>Order ${order.orderId}</td>
                    <td>${order.userId}</td>
                    <td>${order.status}</td>
                    <td><c:forEach items="${order.orderItems}" var="item">
                        ${item.productName}<br />
                        ${item.productCost}<br />
                    </c:forEach>
            </td>
                    <td style="text-align: center">${order.orderCost} ${rub}</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.status.equals('Created') || order.status.equals('Linked with courier')}">
                                <form action="/my-orders/remove/${order.orderId}" method="post">
                                    <button type="submit" class="btn btn-default">${remove}</button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                </form>
                            </c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>
                        <br />
                        <c:choose>
                            <c:when test="${role.equals('ROLE_COURIER') && order.status.equals('Linked with courier')}">
                                <form action="/my-orders/markAsDeliv/${order.orderId}" method="post">
                                    <button type="submit" class="btn btn-default"><spring:message code="orders.delivered"/></button>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                </form>
                            </c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>
                    </td>
                </tr>


        </c:forEach>
    </table>
</div>
<div>
    <a href="<c:url value='/home'/>" >Перейти в профиль</a>
</div>

</body>
</html>
