<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="general.currentOrders"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/current-orders-style.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<jsp:include page="navbar.jsp"/>

<body>
<div class="container main-container">
    <div class="row">
        <div class="well  orders-group">
            <h1 class="text-center"><spring:message code="general.currentOrders"/></h1>
            <div class="list-group">
                <c:forEach items="${orders}" var="order">
                <fmt:parseDate value="${ order.orderCreationDate }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <div class="container sub-container">
                    <div class="row">
                        <div class="col-md-12">
                            <c:choose>
                            <c:when test="${order.status.equals('Delivered')}">
                            <div class="panel panel-success">
                                </c:when>
                                <c:when test="${order.status.equals('Created') || order.status.equals('Linked with courier')}">
                                <div class="panel panel-info">
                                    </c:when>
                                    <c:otherwise>
                                    <div class="panel panel-danger">
                                        </c:otherwise>
                                        </c:choose>
                                            <%--<div class="panel panel-default">--%>
                                        <div class="panel-heading">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <h4>${order.status}</h4>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-body">
                                            <div class="box box-info">
                                                <div class="box-body">
                                                        <%--<div class="col-sm-5 col-xs-6 tital ">Статус заказа</div>
                                                        <div class="col-sm-7 col-xs-6 ">${order.status}</div>
                                                        <div class="clearfix"></div>
                                                        <div class="bot-border"></div>--%>

                                                    <div class="col-sm-5 col-xs-6 tital "><spring:message
                                                            code="orders.orderProcessed"/></div>
                                                    <div class="col-sm-7"><fmt:formatDate pattern="dd.MM.yyyy   HH:mm"
                                                                                          value="${ parsedDateTime }"/></div>
                                                    <div class="clearfix"></div>
                                                    <div class="bot-border"></div>

                                                    <div class="col-sm-5 col-xs-6 tital "><spring:message
                                                            code="orders.deliveryTo"/></div>
                                                    <div class="col-sm-7">${order.orderAddress.latitude} ${order.orderAddress.longitude}</div>
                                                    <div class="clearfix"></div>
                                                    <div class="bot-border"></div>

                                                    <c:forEach items="${order.orderItems}" var="item">
                                                        <div class="col-sm-5 col-xs-6 tital">${item.productName}</div>
                                                        <div class="col-sm-4">${item.productQuantity} <spring:message
                                                                code="items.count"/>&times;${item.productCost}₽
                                                        </div>
                                                        <div class="col-sm-3">${item.productCost*item.productQuantity}₽</div>
                                                    </c:forEach>
                                                    <div class="clearfix"></div>
                                                    <div class="bot-border"></div>

                                                    <div class="col-sm-5 col-xs-6 tital "><spring:message
                                                            code="orders.totalOrderCost"/></div>
                                                    <div class="col-sm-4"></div>
                                                    <div class="col-sm-3">${order.orderCost}₽</div>

                                                    <div class="clearfix"></div>
                                                    <div class="bot-border"></div>

                                                    <div class="col-sm-6 col-xs-6 tital "></div>
                                                    <div class="col-sm-3 text-right">
                                                        <c:choose>
                                                            <c:when test="${role.equals('ROLE_COURIER') && order.status.equals('Linked with courier')}">
                                                                <form action="/current-orders/markAsDeliv/${order.orderId}"
                                                                      method="post">
                                                                    <button type="submit" class="btn btn-success">
                                                                        <spring:message
                                                                                code="orders.delivered"/></button>
                                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                                           value="${_csrf.token}"/>
                                                                </form>
                                                            </c:when>
                                                            <c:otherwise></c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <%--<div class="col-sm-1"></div>--%>
                                                    <div class="col-sm-3 text-center">
                                                        <c:choose>
                                                            <c:when test="${role.equals('ROLE_COURIER')
                                && (order.status.equals('Linked with courier') || order.status.equals('Created'))}">
                                                                <form action="/current-orders/remove/${order.orderId}"
                                                                      method="post">
                                                                    <button type="submit" class="btn btn-danger">
                                                                        <spring:message code="orders.drop"/></button>
                                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                                           value="${_csrf.token}"/>
                                                                </form>
                                                            </c:when>
                                                            <c:otherwise></c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
