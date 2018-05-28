<%@ page import="com.netcracker.config.Constant" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="general.currentOrders"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/my-orders-style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/current-orders-style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/grey-button-style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/current-orders-js.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/notify.js"></script>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
    <script type="text/javascript">
        <%--<%@include file="/resources/js/sober-list-js.js" %>--%>
        if ('${pageContext.response.locale}' == 'uk') {
            <%@include file="/resources/js/strings-uk.js" %>
        }
        if ('${pageContext.response.locale}' == 'ru') {
            <%@include file="/resources/js/strings-ru.js" %>
        }
        if ('${pageContext.response.locale}' == 'en') {
            <%@include file="/resources/js/strings-en.js" %>
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            var ordersPoints = [];
            ymaps.ready(getOrderAddresses);
            function getOrderAddresses() {
                <c:forEach items="${orders}" var="order">
                getAddressByCoordinates('${order.orderId}',${order.orderAddress.latitude}, ${order.orderAddress.longitude});
                ordersPoints.push([${order.orderAddress.latitude}, ${order.orderAddress.longitude}]);
                </c:forEach>
                createRoute(ordersPoints);
            }
        });
        /*ymaps.ready(function () {
            createRoute(ordersPoints);
        });*/
    </script>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="blog-section paddingTB60 bg-grey">
    <div class="container">
        <div class="row text-center">
            <div class="col-md-12">
                <div class="site-heading">
                    <div class="container">
                        <div class="row">
                            <h1 class="text-center"><spring:message code="general.currentOrders"/></h1>
                            <div class="border text-center"></div>
                            <div class="content">
                                <c:forEach items="${orders}" var="order">
                                    <div class="container-order text-center sticker-left sticker-info"
                                         data-sticker="${order.status}">
                                        <div class="text-center order-info">
                                            <div class="row text-left">
                                                <div class="col-sm-5"><spring:message code="orders.orderId"/></div>
                                                <div class="col-sm-7">${order.orderId}</div>
                                            </div>

                                            <div class="row text-left">
                                                <fmt:parseDate value="${ order.orderCreationDate }"
                                                               pattern="yyyy-MM-dd'T'HH:mm"
                                                               var="parsedDateTime"
                                                               type="both"/>
                                                <div class="col-sm-5"><spring:message code="orders.orderProcessed"/></div>
                                                <div class="col-sm-7"><fmt:formatDate pattern="dd.MM.yyyy   HH:mm"
                                                                                      value="${ parsedDateTime }"/></div>
                                            </div>

                                            <div class="row text-left">
                                                <div class="col-sm-5"><spring:message code="orders.timeSinceCreation"/></div>
                                                <div class="col-sm-7">${order.orderCreationDate.until(now, chr)}</div>
                                            </div>

                                            <div class="row text-left">
                                                <div class="col-sm-5"><spring:message code="users.username"/></div>
                                                <div class="col-sm-7" id="user${order.orderId}">
                                                    <script>getUserName('${order.orderId}', '${order.userId}');</script>
                                                </div>
                                            </div>

                                            <div class="row text-left">
                                                <div class="col-sm-5"><spring:message code="orders.deliveryTo"/></div>
                                                <div class="col-sm-7" id = "address${order.orderId}"></div>
                                            </div>

                                            <div class="row text-left">
                                                <div class="col-sm-5"><spring:message code="orders.phone"/></div>
                                                <div class="col-sm-7">${order.orderPhone}</div>
                                            </div>

                                            <div class="row text-left">
                                                <div class="col-sm-5"><spring:message code="orders.paymentType"/></div>
                                                <div class="col-sm-7">${order.paymentType}
                                                    <c:if test="${order.paymentType eq Constant.PAYMENT_TYPE_CASH}">
                                                        <c:if test="${order.changeFrom != null}">
                                                            (<spring:message code="basket.changeFrom"/> ${order.changeFrom}₽)
                                                        </c:if>
                                                    </c:if>
                                                </div>
                                            </div>

                                            <div class="row text-left">
                                                <div class="col-sm-5"><spring:message code="orders.paymentState"/></div>
                                                <c:if test="${order.paid eq true}">
                                                    <div class="col-sm-7"><spring:message code="orders.paid"></spring:message></div>
                                                </c:if>
                                                <c:if test="${order.paid eq false}">
                                                    <div class="col-sm-7"><spring:message code="orders.notPaid"></spring:message></div>
                                                </c:if>
                                            </div>

                                            <ul class="details text-left">
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
                                                    <c:if test="${role.equals('ROLE_COURIER')}"> <%--&&
                                                    (order.status.equals('Linked with courier')
                                                    || order.status.equals('Принят курьером')
                                                    || order.status.equals('Прийнято кур`єром'))}"--%>
                                                        <form action="/current-orders/markAsDeliv/${order.orderId}"
                                                              method="post">
                                                            <button type="submit"
                                                                    class="btn grey-button grey-btn-success">
                                                                <spring:message
                                                                        code="orders.delivered"/></button>
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                   value="${_csrf.token}"/>
                                                        </form>
                                                    </c:if>
                                                </div>
                                                <div class="col-sm-6 text-center">
                                                    <c:if test="${role.equals('ROLE_COURIER')}">
                                <%--&& (order.status.equals('Linked with courier') || order.status.equals('Created'))}">--%>
                                                        <form action="/current-orders/remove/${order.orderId}"
                                                              method="post">
                                                            <button type="submit"
                                                                    class="btn grey-button grey-btn-danger">
                                                                <spring:message code="orders.drop"/></button>
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
                            <div class="content map-content">
                                <div id="map"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="set-point-modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document" style="min-width: 900px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><strong><spring:message code="admin.setPoint"/></strong></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="reset-values">&times;</span>
                </button>
            </div>
            <div class="modal-body text-center" >
                <div id="map-for-set-point" style="height: 500px;width: 700px; margin: auto;">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="createRouteWithoutGeolocation();"><spring:message
                        code="general.ok"></spring:message></button>
            </div>
        </div>
    </div>
</div>


<jsp:include page="footer.jsp"/>

</body>
</html>
