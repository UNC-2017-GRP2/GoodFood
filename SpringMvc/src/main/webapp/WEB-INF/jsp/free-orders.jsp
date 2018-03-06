<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="orders.freeOrders"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/free-orders-style.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap-form-helpers/2.3.0/js/bootstrap-formhelpers-phone.js"></script>
    <script type="text/javascript" src = "/resources/js/free-orders-js.js"></script>
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
                            <h1 class="text-center"><spring:message code="orders.freeOrders"/></h1>
                            <div class="border text-center"></div>
                            <div class="well">
                                <div class="list-group">
                                    <c:forEach items="${orders}" var="order">
                                        <fmt:parseDate value="${ order.orderCreationDate }" pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDateTime"
                                                       type="both"/>
                                        <div class="container sub-container">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="panel panel-info">
                                                        <div class="panel-heading">
                                                            <div class="row">
                                                                <div class="col-md-8">
                                                                    <h4>Order ${order.orderId}</h4>
                                                                </div>
                                                                <div class="col-md-4 text-right">
                                                                    <h4>${order.status}</h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="panel-body">
                                                            <div class="box box-info">
                                                                <div class="box-body">
                                                                    <div class="col-sm-5 col-xs-6 tital"><spring:message
                                                                            code="orders.orderProcessed"/></div>
                                                                    <div class="col-sm-7"><fmt:formatDate pattern="dd.MM.yyyy   HH:mm"
                                                                                                          value="${ parsedDateTime }"/></div>
                                                                    <div class="clearfix"></div>
                                                                    <div class="bot-border"></div>

                                                                    <div class="col-sm-5 col-xs-6 tital "><spring:message code="orders.timeSinceCreation"/></div>
                                                                    <div class="col-sm-7">${order.orderCreationDate.until(now, chr)}</div>
                                                                    <div class="clearfix"></div>
                                                                    <div class="bot-border"></div>

                                                                    <div class="col-sm-5 col-xs-6 tital "><spring:message code="users.username"/></div>
                                                                    <div class="col-sm-7 order-username" id="user${order.orderId}">
                                                                        <script>getUserName('${order.orderId}','${order.userId}');</script>
                                                                    </div>
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

                                                                    <div class="col-sm-9 col-xs-6 tital "></div>
                                                                    <div class="col-sm-3 text-right">
                                                                        <form action="/free-orders/${order.orderId}" method="post">
                                                                            <button type="submit" class="btn btn-success"><spring:message code="orders.takeOrder"/></button>
                                                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                                        </form>
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
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>


</body>
</html>
