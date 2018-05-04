<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="general.basket"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/basket-style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/jquery.inputmask/3.1.0/inputmask/jquery.inputmask.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/jquery.inputmask/3.1.0/inputmask/jquery.inputmask.date.extensions.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/bootstrap-form-helpers/2.3.0/js/bootstrap-formhelpers-phone.js"></script>
    <script type="text/javascript" src="https://api-maps.yandex.ru/2.1/?lang=ru_RU"></script>
    <script src="https://js.stripe.com/v3/"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/notify.js"></script>
    <%--<script src="webjars/noty/2.2.4/jquery.noty.packaged.min.js" type="text/javascript"></script>--%>
    <script type="text/javascript">
        <%@include file="/resources/js/basket-js.js" %>
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
        ymaps.ready(getUserAddresses);

        function getUserAddresses() {
            <c:forEach items="${userAddresses}" var="address">
            getAddressByCoordinates(${address.latitude}, ${address.longitude});
            </c:forEach>
        }

        $(document).ready(function () {
            setPhoneValue('${userPhone}');

            if (${paymentError != null}){
                $.notify(getErrorString('payment_error'), "error");
                //$.notify('${paymentError}', "wrong");
            }
        });
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
                            <h1 class="text-center"><spring:message code="basket.currentOrder"/></h1>
                            <div class="border text-center"></div>
                            <div class="well">
                                <div class="list-group">
                                    <form action="/checkout" method="post" id="payment-form">
                                        <ul>
                                            <c:forEach items="${basketItems}" var="item">
                                                <li class="list-group-item">
                                                    <div class="media col-md-2">
                                                        <figure class="pull-left">
                                                            <img class="media-object img-rounded img-responsive"
                                                                 src="${item.productImage}">
                                                        </figure>
                                                    </div>
                                                    <div class="col-md-5">
                                                        <p><h4
                                                            class="list-group-item-heading"> ${item.productName} </h4></p>
                                                        <p><h4> ${item.productCost} ₽ </h4></p>
                                                    </div>
                                                    <div class="col-md-2 text-center">
                                                        <div class="info">
                                                            <h4>
                                                                <div class="number">
                                                                    <span class="minus" item-id="${item.productId}"
                                                                          item-cost="${item.productCost}">-</span>
                                                                    <input type="text" class="quantity" id="count"
                                                                           name="count" value="${item.productQuantity}"
                                                                           size="5"/>
                                                                    <span class="plus" item-id="${item.productId}"
                                                                          item-cost="${item.productCost}">+</span>
                                                                </div>
                                                            </h4>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3 text-left">
                                                        <h4 class="final-items-cost"><span
                                                                class="final-item-cost-span">${item.productQuantity*item.productCost}</span>
                                                            ₽</h4>
                                                        <span aria-hidden="true" class="remove-item"
                                                              item-id="${item.productId}"
                                                              item-cost="${item.productCost}"
                                                              item-quantity="${item.productQuantity}">&times;</span>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                            <li class="list-group-item list-group-address">
                                                <div class="col-md-5 text-left">
                                                    <spring:message code="enter.address" var="placeholder"/>
                                                    <textarea type='text' id='input-address' class="form-control"
                                                              class="dropdown-toggle" data-toggle="dropdown"
                                                              placeholder='${placeholder}'></textarea>
                                                    <input type='hidden' id="input-address-latitude"
                                                           name="input-address-latitude">
                                                    <input type='hidden' id="input-address-longitude"
                                                           name="input-address-longitude">
                                                    <div class="list-group" id="my-address-list">
                                                        <ul class="ul-my-addresses">
                                                        </ul>
                                                    </div>
                                                    <div class="validationMessage" id="address-valid"></div>
                                                </div>
                                                <div class="col-md-3 text-center">
                                                    <input type='text' id='input-phone' name="input-phone"
                                                           value="${userPhone}" class="form-control">
                                                    <div class="validationMessage" id="phone-validation-message">
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="list-group-item">
                                                <div class="col-md-6">
                                                    <div>
                                                        <div class="form-row text-left">
                                                            <ul class="nav nav-tabs ">
                                                                <li class="active" id="cash-li"><a data-toggle="tab"
                                                                                                   href="#cash-tab"><spring:message code="basket.cash"/></a>
                                                                </li>
                                                                <li><a data-toggle="tab" id="card-li" href="#card-tab"><spring:message code="basket.card"/></a>
                                                                </li>
                                                            </ul>
                                                            <div class="tab-content">
                                                                <div id="cash-tab" class="tab-pane fade in active col-md-6">
                                                                    <label for="change-from" id="change-from-label"><spring:message code="basket.changeFrom"/></label>
                                                                    <div class="input-group">
                                                                        <input id="change-from" type="text" class="form-control text-right">
                                                                        <span class="input-group-addon"><i class="glyphicon glyphicon-ruble"></i></span>
                                                                    </div>
                                                                </div>
                                                                <div id="card-tab" class="tab-pane fade">
                                                                    <div id="card-element">
                                                                        <!-- A Stripe Element will be inserted here. -->
                                                                    </div>
                                                                    <!-- Used to display form errors. -->
                                                                    <div id="card-errors" class="validationMessage"
                                                                         role="alert"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="list-group-item list-group-total-order">
                                                <div class="col-md-12 text-right">
                                                    <h3><spring:message code="orders.totalOrderCost"/>: <span
                                                            class="total-order-cost">${totalOrder}</span> ₽</h3>
                                                    <p>
                                                        <button type="submit" disabled="disabled"
                                                                class="btn btn-primary to-order-btn">
                                                            <span class="glyphicon glyphicon-check"></span>
                                                            <spring:message code="basket.checkout"/></button>
                                                    </p>
                                                </div>
                                            </li>
                                        </ul>
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
</div>

<jsp:include page="footer.jsp"/>

<c:if test="${basketItems == null}">
    <script>disabledInputFieldsForCheckout();</script>
</c:if>
<c:if test="${basketItems.size() == 0}">
    <script>disabledInputFieldsForCheckout();</script>
</c:if>
</body>
</html>
