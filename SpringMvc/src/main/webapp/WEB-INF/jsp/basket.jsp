<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="general.basket"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/basket-style.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="webjars/bootstrap-form-helpers/2.3.0/js/bootstrap-formhelpers-phone.js"></script>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
    <script type="text/javascript">
        <%@include file="/resources/js/basket-js.js" %>
    </script>
    <script type="text/javascript">
        ymaps.ready(getUserAddresses);

        function getUserAddresses() {
            <c:forEach items="${userAddresses}" var="address">
            getAddressByCoordinates(${address.latitude}, ${address.longitude});
            </c:forEach>
        }
    </script>
    <meta name="interkassa-verification" content="547819c7eeea789997a6745efe6349e5"/>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container">
    <div class="row">
        <div class="well">
            <h1 class="text-center"><spring:message code="basket.currentOrder"/></h1>
            <div class="list-group">
                <form action="/checkout" method="get">
                    <ul>
                        <c:forEach items="${basketItems}" var="item">
                            <li class="list-group-item">
                                <div class="media col-md-2">
                                    <figure class="pull-left">
                                        <img class="media-object img-rounded img-responsive" src="${item.productImage}">
                                    </figure>
                                </div>
                                <div class="col-md-5">
                                    <p><h4 class="list-group-item-heading"> ${item.productName} </h4></p>
                                        <%--<p class="list-group-item-text"> ${item.productDescription} </p>--%>
                                    <p>
                                    <h4> ${item.productCost} ₽ </h4>
                                    </p>
                                </div>
                                <div class="col-md-2 text-center">
                                    <div class="info">
                                        <h4>
                                            <div class="number">
                                                <span class="minus" item-id="${item.productId}"
                                                      item-cost="${item.productCost}">-</span>
                                                <input type="text" class="quantity" id="count" name="count"
                                                       value="${item.productQuantity}" size="5"/>
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
                                    <span aria-hidden="true" class="remove-item" item-id="${item.productId}"
                                          item-cost="${item.productCost}" item-quantity="${item.productQuantity}">&times;</span>
                                </div>
                            </li>
                        </c:forEach>
                        <li class="list-group-item list-group-address">
                            <div class="col-md-4">
                                <spring:message code="enter.address" var="placeholder"/>
                                <input type='text' id='input-address' class="form-control" class="dropdown-toggle"
                                       data-toggle="dropdown" placeholder='${placeholder}'>
                                <input type='hidden' id="input-address-latitude" name="input-address-latitude">
                                <input type='hidden' id="input-address-longitude" name="input-address-longitude">
                                <div class="list-group">

                                    <ul class="ul-my-addresses">
                                    </ul>

                                </div>
                                <label id="address-valid"></label>
                            </div>
                            <div class="col-md-4">
                                <input type='text' id='input-phone' name="input-phone" value="${userPhone}"
                                       class="form-control input-medium bfh-phone" data-format="+7 (ddd) ddd-dddd">
                            </div>
                        </li>
                        <li class="list-group-item list-group-total-order">
                            <div class="col-md-6">
                                <h3><spring:message code="orders.totalOrderCost"/>: <span
                                        class="total-order-cost">${totalOrder}</span> ₽</h3>
                                <p>
                                    <spring:message code="basket.checkout" var="placeholder"/>
                                    <input type="submit" class="btn btn-primary to-order-btn" disabled="disabled"
                                           value='${placeholder}'>
                                </p>


                            </div>
                        </li>
                    </ul>
                </form>
            <%--    <form id="payment" name="payment" method="post" action="https://sci.interkassa.com/" enctype="utf-8">
                    <input type="hidden" name="ik_co_id" value="5a7b3a4b3c1eaf856c8b4567"/>
                    <input type="hidden" name="ik_pm_no" value="ID_4233"/>
                    <input type="hidden" name="ik_am" value="100.00"/>
                    <input type="hidden" name="ik_cur" value="RUB"/>
                    <input type="hidden" name="ik_desc" value="Event Description"/>
                    <input type="hidden" name="ik_exp" value="2018-02-16"/>
                    <input type="submit" value="Pay">
                </form>--%>
                <form method="POST" action="https://money.yandex.ru/quickpay/confirm.xml">
                    <input type="hidden" name="receiver" value="410016165222018">
                    <input type="hidden" name="formcomment" value="ProjectUNC">
                    <input type="hidden" name="short-dest" value="Оплата заказа">
                    <input type="hidden" name="label" value="$order_id">
                    <input type="hidden" name="quickpay-form" value="donate">
                    <input type="hidden" name="targets" value="транзакция {order_id}">
                    <input type="hidden" name="sum" value="${totalOrder}" data-type="number">
                    <%--  <input type="hidden" name="comment" value="Хотелось бы дистанционного управления.">--%>
                    <input type="hidden" name="need-fio" value="true">
                    <input type="hidden" name="need-email" value="true">
                    <input type="hidden" name="need-phone" value="false">
                    <input type="hidden" name="need-address" value="false">
                    <label><input type="radio" name="paymentType" value="PC">Яндекс.Деньгами</label>
                    <label><input type="radio" name="paymentType" value="AC">Банковской картой</label>
                    <input type="submit" value="Оплатить">
                </form>
                <p>К сумме добавится комиссия платежной системы (+0.5% Яндекс, +2% банковская карта)</p>

                <form method="POST" action="https://merchant.webmoney.ru/lmi/payment_utf.asp" accept-charset="utf-8">
                <p>
                        <input type="hidden" name="LMI_PAYEE_PURSE" value="R648518292195">
                        <input type="hidden" name="LMI_PAYMENT_AMOUNT" value="${totalOrder}">
                        <input type="hidden" name="LMI_PAYMENT_NO" value="1531">
                        <input type="hidden" name="LMI_PAYMENT_DESC" value="test">
                        <input type="hidden" name="LANG" value="ru-RU">
                    </p>
                    <p>
                        <input type="submit" value="submit">
                    </p>
                </form>

            </div>
        </div>
    </div>
</div>
<c:if test="${basketItems == null}">
    <script>disabledInputAddress();</script>
</c:if>
<c:if test="${basketItems.size() == 0}">
    <script>disabledInputAddress();</script>
</c:if>
<script>getUserAddresses();</script>
</body>
</html>
