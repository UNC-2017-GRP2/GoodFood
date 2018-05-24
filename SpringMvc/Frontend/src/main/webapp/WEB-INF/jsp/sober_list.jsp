<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Sober list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/my-orders-style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/grey-button-style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://api-maps.yandex.ru/2.1/?lang=ru_RU"></script>
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
        function getAddressByCoordinates(orderId, latitude, longitude){
            var coords = [latitude, longitude];
            ymaps.geocode(coords).then(function(res){
                if (res.geoObjects.get(0) != null){
                    var obj = res.geoObjects.get(0);
                    $("#address"+orderId).text(obj.getAddressLine());
                }
            });
        }

        function getDestAddressByCoordinates(orderId, latitude, longitude){
            var coords = [latitude, longitude];
            ymaps.geocode(coords).then(function(res){
                if (res.geoObjects.get(0) != null){
                    var obj = res.geoObjects.get(0);
                    $("#dest-address"+orderId).text(obj.getAddressLine());
                }
            });
        }

        function getOrderAddresses() {
            <c:forEach items="${entityList}" var="entity">
            var array = "${entity.getParameterByAttrId(3).value}".split(',');
            getAddressByCoordinates('${entity.objectId}', array[0], array[1]);
            array = "${entity.getParameterByAttrId(4).value}".split(',');
            getDestAddressByCoordinates('${entity.objectId}', array[0], array[1]);
            </c:forEach>
        }
        ymaps.ready(getOrderAddresses);

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
                            <h1 class="text-center"><spring:message code="general.soberDriverOrders"/></h1>
                            <div class="border text-center"></div>
                            <div class="content">
                                <table class="table">
                                    <thead>
                                    <%--<a href="#" class="btn btn-primary btn-xs pull-right"><b>+</b> Add new categories</a>--%>
                                    <tr>
                                        <th><spring:message code="users.phoneNumber"/></th>
                                        <th><spring:message code="sober.pos"/></th>
                                        <th><spring:message code="sober.dest"/></th>
                                        <th><spring:message code="orders.orderCreationDate"/></th>
                                        <th><spring:message code="sober.orderEnd"/></th>
                                        <th><spring:message code="orders.status"/></th>
                                    </tr>
                                    </thead>
                                    <c:forEach items="${entityList}" var="entity">
                                        <tr>
                                                <td>${entity.getParameterByAttrId(1).value}</td>
                                                <%--<td>Order ${entity.getParameterByAttrId(2).value}</td>--%>
                                                <td id="address${entity.objectId}">
                                                    </td>
                                        <td id="dest-address${entity.objectId}">
                                        </td>

                                                <td>${entity.getParameterByAttrId(5).value}</td>
                                                <td>${entity.getParameterByAttrId(6).value}</td>
                                                <td>${entity.getParameterByAttrId(7).value}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                    </div>
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
