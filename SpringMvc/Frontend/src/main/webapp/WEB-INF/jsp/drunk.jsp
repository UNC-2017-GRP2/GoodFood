<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Drunk page</title>
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
        <%@include file="/resources/js/drunk-js.js" %>
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
        ymaps.ready(getUserDestAddresses);

        function getUserAddresses() {
            <c:forEach items="${userAddresses}" var="address">
            getAddressByCoordinates(${address.latitude}, ${address.longitude});
            </c:forEach>
        }

        function getUserDestAddresses() {
            <c:forEach items="${userAddresses}" var="address">
            getDestAddressByCoordinates(${address.latitude}, ${address.longitude});
            </c:forEach>
        }

        $(document).ready(function () {
            setPhoneValue('${userPhone}');
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
                            <h1 class="text-center"><spring:message code="general.soberDriverCalling"/></h1>
                            <div class="border text-center"></div>
                            <div class="well">
                                <div class="list-group">
                                    <form action="/drunk_rest" method="GET" id="payment-form">
                                        <ul>
                                            <li class="list-group-item list-group-address">
                                                <div class="col-md-5 text-left">
                                                    <spring:message code="enter.address" var="placeholder"/>
                                                    <textarea type='text' id='input-address'
                                                              name="input-address" class="form-control"
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
                                                <div class="col-md-5 text-center">
                                                    <spring:message code="enter.address" var="placeholder"/>
                                                    <textarea type='text' id='input-dest-address' class="form-control"
                                                              class="dropdown-toggle" data-toggle="dropdown"
                                                              placeholder='${placeholder}'></textarea>
                                                    <input type='hidden' id="input-address-dest-latitude"
                                                           name="input-address-dest-latitude">
                                                    <input type='hidden' id="input-address-dest-longitude"
                                                           name="input-address-dest-longitude">
                                                    <div class="list-group" id="my-address-list">
                                                        <ul class="ul-my-dest-addresses">
                                                        </ul>
                                                    </div>
                                                    <div class="validationMessage" id="address-dest-valid"></div>
                                                </div>
                                                <div class="col-md-3 text-right">
                                                    <input type='text' id='input-phone' name="input-phone"
                                                           value="${userPhone}" class="form-control">
                                                    <div class="validationMessage" id="phone-validation-message">
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="list-group-item list-group-total-order">
                                                <div class="col-md-12 text-right">
                                                    <p>
                                                        <button type="submit" disabled="disabled"
                                                                class="btn btn-primary to-order-btn">
                                                            <span class="glyphicon glyphicon-check"></span>
                                                            <spring:message code="general.soberDriverCalling"/></button>
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
</body>
</html>
