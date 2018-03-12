<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="general.adminPanel"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        <%@include file="/resources/js/admin-js.js" %>
    </script>
</head>

<body>
<jsp:include page="navbar.jsp"/>

<h2><spring:message code="general.adminPanel"/></h2>

    <div class="well" style="height: auto!important;"><form action="/admin/actualize" method="post">
        <p align="right" ><button type="submit" class="btn btn-default"><spring:message code="admin.actualize"/></button></p>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
        <ul class="nav nav-tabs">
            <li class="active"><a href="#orders" data-toggle="tab"><spring:message code="admin.orders"/></a></li>
            <li><a href="#users" data-toggle="tab"><spring:message code="admin.users"/></a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane active in" id="orders">

            <table class="table">


                <tr>
                    <thead>
                        <tr>
                            <th scope="col"><spring:message code="orders.orderId"/></th>
                            <th scope="col"><spring:message code="general.userId"/></th>
                            <th scope="col"><spring:message code="orders.status"/></th>
                            <th scope="col"><spring:message code="orders.items"/></th>
                            <th scope="col"><spring:message code="orders.orderCreationDate"/></th>
                            <th scope="col"><spring:message code="orders.timeSinceCreation"/></th>
                            <th scope="col"><spring:message code="orders.cost"/></th>
                        </tr>
                    </thead>
<tbody>
<tr>
    <c:forEach items="${orders}" var="order">
    <th scope="row">${order.orderId}</th>
                    <td>${order.userId}</td>
                    <td>${order.status}</td>
                    <td><c:forEach items="${order.orderItems}" var="item">
                        ${item.productName}<br />
                        ${item.productCost} ₽<br />
                    </c:forEach>
                    </td>
        <td>${order.orderCreationDate.toString()}</td>
        <td>${order.orderCreationDate.until(now, chr)}</td>

        <td style="text-align: center">${order.orderCost} ₽</td>
                </tr>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

        </c:forEach>
        </tbody>
    </table>
            </div>
        <div class="tab-pane fade" id="users">
            <table class="table">
                    <tr>
                        <thead>
                        <tr>
                            <th scope="col"><spring:message code="general.userId"/></th>
                            <th scope="col"><spring:message code="users.fullname"/></th>
                            <th scope="col"><spring:message code="users.username"/></th>
                            <th scope="col"><spring:message code="users.phoneNumber"/></th>
                            <th scope="col"><spring:message code="users.birthday"/></th>
                            <th scope="col"><spring:message code="users.email"/></th>
                            <th scope="col"><spring:message code="users.address"/></th>
                            <th scope="col"><spring:message code="users.role"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <c:forEach items="${users}" var="user">
                            <th scope="row">${user.userId}</th>
                            <td>${user.fio}</td>
                                <td>${user.login}</td>
                                <td>${user.phoneNumber}</td>
                                <td>${user.birthday}</td>
                                <td>${user.email}</td>
                                <td><c:forEach items="${user.addresses}" var="item">
                                        ${item}<br />
                                </c:forEach></td>
                                <%--<td><spring:message code="users.role.${user.role}"/></td>--%>
                            <td>
                                <select id="dropdown-${user.userId}">
                                    <option value="ROLE_COURIER"><spring:message code="users.role.ROLE_COURIER"/></option>
                                    <option value="ROLE_ADMIN"><spring:message code="users.role.ROLE_ADMIN"/></option>
                                    <option value="ROLE_USER"><spring:message code="users.role.ROLE_USER"/></option>
                                </select>
                                <c:choose>
                                    <c:when test="${user.role.equals('ROLE_USER')}">
                                        <script>selectOption('dropdown-${user.userId}', 'ROLE_USER');</script>
                                    </c:when>
                                    <c:when test="${user.role.equals('ROLE_COURIER')}">
                                        <script>selectOption('dropdown-${user.userId}', 'ROLE_COURIER');</script>
                                    </c:when>
                                    <c:when test="${user.role.equals('ROLE_ADMIN')}">
                                        <script>selectOption('dropdown-${user.userId}', 'ROLE_ADMIN');</script>
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <button type="button" class="btn btn-default" onclick="dropdownButton('${user.userId}', 'dropdown-${user.userId}');">Change role</button>
                            </td>
                        </tr>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </c:forEach>
                </tbody>
            </table>

        </div>
        </div>
    </div>




</body>
</html>
