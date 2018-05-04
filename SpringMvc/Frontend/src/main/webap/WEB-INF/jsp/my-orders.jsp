<%@ page import="com.netcracker.config.Constant" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="general.myOrders"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/my-orders-style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/grey-button-style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/my-orders-js.js"></script>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
    <script type="text/javascript">
        ymaps.ready(getOrderAddresses);

        function getOrderAddresses() {
            <c:forEach items="${orders}" var="order">
            getAddressByCoordinates('${order.orderId}', ${order.orderAddress.latitude}, ${order.orderAddress.longitude});
            </c:forEach>
        }
    </script>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="blog-section paddingTB60 bg-grey ">
    <div class="container">
        <div class="row text-center">
            <div class="col-md-12">
                <div class="site-heading">
                    <div class="container">
                        <div class="row">
                            <h1 class="text-center"><spring:message code="general.myOrders"/></h1>
                            <div class="border text-center"></div>
                            <div class="content">
                                <c:forEach items="${orders}" var="order">
                                <c:choose>
                                <c:when test="${order.status.equals('Delivered')}">
                                <div class="container-order text-center sticker-left sticker-success"
                                     data-sticker="${order.status}">
                                    </c:when>
                                    <c:when test="${order.status.equals('Created') || order.status.equals('Linked with courier')}">
                                    <div class="container-order text-center sticker-left sticker-info"
                                         data-sticker="${order.status}">
                                        </c:when>
                                        <c:otherwise>
                                        <div class="container-order text-center sticker-left sticker-danger"
                                             data-sticker="${order.status}">
                                            </c:otherwise>
                                            </c:choose>
                                            <div class="text-center order-info">
                                                <ul class="details text-left">
                                                    <fmt:parseDate value="${ order.orderCreationDate }"
                                                                   pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                                                   type="both"/>
                                                    <li>
                                                        <p>
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                            <fmt:formatDate pattern="dd.MM.yyyy   HH:mm"
                                                                            value="${ parsedDateTime }"/>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <span class="glyphicon glyphicon-home"></span>
                                                            <span id="address${order.orderId}">
                                                            </span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <span class="glyphicon glyphicon-earphone"></span>
                                                            <span>${order.orderPhone}</span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <span class="glyphicon glyphicon-ruble"></span>
                                                            <span>${order.paymentType}</span>
                                                        </p>
                                                    </li>
                                                    <li>
                                                        <p>
                                                            <c:if test="${order.paid eq true}">
                                                                <span class="glyphicon glyphicon-ok"></span>
                                                                <span><spring:message code="orders.paid"></spring:message></span>
                                                            </c:if>
                                                            <c:if test="${order.paid eq false}">
                                                                <span class="glyphicon glyphicon-remove"></span>
                                                                <span><spring:message code="orders.notPaid"></spring:message></span>
                                                            </c:if>
                                                        </p>
                                                    </li>
                                                    <hr>
                                                </ul>

                                                <c:forEach items="${order.orderItems}" var="item">
                                                    <div class="row text-left">
                                                        <div class="col-sm-5">${item.productName}</div>
                                                        <div class="col-sm-4">${item.productQuantity} <spring:message
                                                                code="items.count"/>&times;${item.productCost}₽
                                                        </div>
                                                        <div class="col-sm-3">${item.productCost*item.productQuantity}₽</div>
                                                    </div>
                                                </c:forEach>

                                                <div class="row text-left">
                                                    <div class="col-sm-5"></div>
                                                    <div class="col-sm-4"><spring:message
                                                            code="orders.totalOrderCost"/></div>
                                                    <div class="col-sm-3">${order.orderCost}₽</div>
                                                </div>

                                                <ul class="details text-left">
                                                    <hr>
                                                </ul>

                                                <div class="row text-left">
                                                    <div class="col-sm-6 text-center">
                                                        <c:if test="${role.equals('ROLE_USER')
                                && (order.status.equals('Linked with courier') || order.status.equals('Created'))
                                && (order.orderCreationDate.until(now, chr) > start_exp_time)}">
                                                            <form action="/my-orders/markAsExp/${order.orderId}"
                                                                  method="post">
                                                                <button type="submit"
                                                                        class="btn grey-button grey-btn-danger">
                                                                    <spring:message code="orders.expired"/></button>
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                       value="${_csrf.token}"/>
                                                            </form>
                                                        </c:if>
                                                    </div>
                                                    <div class="col-sm-6 text-center">
                                                        <c:if test="${role.equals('ROLE_USER')
                                && (order.status.equals('Linked with courier') || order.status.equals('Created'))}">
                                                            <form action="/my-orders/remove/${order.orderId}"
                                                                  method="post">
                                                                <button type="submit"
                                                                        class="btn grey-button grey-btn-danger">
                                                                    <spring:message code="orders.cancel"/></button>
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                       value="${_csrf.token}"/>
                                                            </form>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        </c:forEach>
                                    </div>
                                    <c:if test="${orders != null && orders.size() != 0}">
                                        <div class="pagination">
                                            <ul>
                                                <c:choose>
                                                    <c:when test="${page == 1}">
                                                        <a href="<c:url value='/my-orders/${page}'/>">
                                                            <li><</li>
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="<c:url value='/my-orders/${page - 1}'/>">
                                                            <li><</li>
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>

                                                <c:forEach var="i" begin="1" end="${pageCount}">
                                                    <c:choose>
                                                        <c:when test="${i == page}">
                                                            <a class="is-active"
                                                               href="<c:url value='/my-orders/${i}'/>">
                                                                <li>${i}</li>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="<c:url value='/my-orders/${i}'/>">
                                                                <li>${i}</li>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>

                                                <c:choose>
                                                    <c:when test="${page == pageCount}">
                                                        <a href="<c:url value='/my-orders/${page}'/>">
                                                            <li>></li>
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="<c:url value='/my-orders/${page + 1}'/>">
                                                            <li>></li>
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </ul>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
