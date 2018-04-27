<%@ page import="com.netcracker.config.Constant" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="general.adminPanel"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap-select/1.4.2/bootstrap-select.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap-select/1.4.2/bootstrap-select.min.js"></script>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        <%@include file="/resources/js/admin-js.js" %>
        <%@include file="/resources/js/notify.js" %>
        <%@include file="/resources/js/login-js.js"%>
    </script>
    <script type="text/javascript">
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
        $(document).ready(function(){
            if (${usersTab != null}){
                if (${usersTab.equals("success")}){
                    $.notify(getNotificationString('user_created'), "success");
                }
                if (${usersTab.equals("fail")}){
                    $.notify(getErrorString('user_not_created'), "error");
                }
                $('.tab-pane').removeClass('active in');
                $('.tab-pane').addClass('fade');
                $('#usersTab').removeClass('fade');
                $('#usersTab').addClass('active in');
            }
        });
    </script>
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback(drawPieChart);

        function drawPieChart() {
            var data = google.visualization.arrayToDataTable([
                ['Name', 'Quantity'],
                <c:forEach items="${ pieDataMap}" var="item">
                ['${item.key}', ${item.value}],
                </c:forEach>
            ]);

            var options = {
                title: 'Product order statistics',
                width: 900,
                height: 500,
                pieHole: 0.4
            };

            var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
            chart.draw(data, options);
        }

        google.charts.setOnLoadCallback(drawMultSeries);

        function drawMultSeries() {
            var data = google.visualization.arrayToDataTable([
                ['Day of the week', 'Revenue'],
                ['${weekDays[1]}', ${coreChartDataMap.get(weekDays[1])}],
                ['${weekDays[2]}', ${coreChartDataMap.get(weekDays[2])}],
                ['${weekDays[3]}', ${coreChartDataMap.get(weekDays[3])}],
                ['${weekDays[4]}', ${coreChartDataMap.get(weekDays[4])}],
                ['${weekDays[5]}', ${coreChartDataMap.get(weekDays[5])}],
                ['${weekDays[6]}', ${coreChartDataMap.get(weekDays[6])}],
                ['${weekDays[7]}', ${coreChartDataMap.get(weekDays[7])}],
            ]);

            var options = {
                title: 'Revenue by day of the week',
                chartArea: {width: '50%'},
                hAxis: {
                    title: 'Revenue',
                    minValue: 0
                },
                vAxis: {
                    title: 'Day of the week'
                }
            };

            var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }

        google.charts.load('current', {'packages':['line']});
        google.charts.setOnLoadCallback(drawLineChartMaterial);

        function drawLineChartMaterial() {

            var data = google.visualization.arrayToDataTable([
                ['Day', 'Revenue'],
                <c:forEach items="${revenuePerDayMap}" var="item">
                ['${item.key}', ${item.value}],
                </c:forEach>
            ]);

            var options = {
                chart: {
                    title: 'Revenue for the last 10 days',
                   /* subtitle: 'in millions of dollars (USD)'*/
                },
//                width: 700,
//                height: 350
            };

            var chart = new google.charts.Line(document.getElementById('linechart_material'));
            chart.draw(data, google.charts.Line.convertOptions(options));
        }
    </script>


</head>

<body>
<jsp:include page="navbar.jsp"/>

<%--
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
                                &lt;%&ndash;<td><spring:message code="users.role.${user.role}"/></td>&ndash;%&gt;
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
--%>

<div class="container-fluid main-container">
    <div class="col-md-2 sidebar">
        <div class="row">
            <!-- uncomment code for absolute positioning tweek see top comment in css -->
            <div class="absolute-wrapper"></div>
            <!-- Menu -->
            <div class="side-menu">
                <nav class="navbar navbar-default" role="navigation">
                    <!-- Main Menu -->
                    <div class="side-menu-container">
                        <ul class="nav navbar-nav nav-tabs">
                            <li class="active"><a href="#overviewTab" data-toggle="tab"><span
                                    class="glyphicon glyphicon-dashboard"></span> Overview</a></li>
                            <li><a href="#usersTab" data-toggle="tab"><span class="glyphicon glyphicon-user"></span>
                                Users</a></li>
                            <li><a href="#ordersTab" data-toggle="tab"><span
                                    class="glyphicon glyphicon-shopping-cart"></span> Orders</a></li>
                            <li><a href="#itemsTab" data-toggle="tab"><span class="glyphicon glyphicon-cutlery"></span>
                                Food</a></li>
                            <li class="panel panel-default my-dropdown">
                                <a data-toggle="collapse" href="#dropdown-lvl1">
                                    <span class="glyphicon glyphicon-list-alt"></span> Pages <span class="caret"></span>
                                </a>
                                <div id="dropdown-lvl1" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <ul class="nav navbar-nav">
                                            <li><a href="<c:url value='/admin'/>"><spring:message code="general.adminPanel"/></a></li>
                                            <li><a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_PIZZA%>"><spring:message code="general.mainPage"/></a></li>
                                            <li><a href="<c:url value='/my-orders/1'/>"><spring:message code="general.myOrders"/></a></li>
                                            <li><a href="<c:url value='/basket'/>"><spring:message code="general.basket"/></a></li>
                                            <li><a href="<c:url value='/free-orders'/>"><spring:message code="orders.freeOrders"/></a></li>
                                            <li><a href="<c:url value='/current-orders'/>"><spring:message code="orders.currentOrders"/></a></li>
                                            <li><a href="<c:url value='/profile'/>"><spring:message code="general.profile"/></a></li>
                                            <!-- Dropdown level 2 -->
                                            <%--<li class="panel panel-default my-dropdown">
                                                <a data-toggle="collapse" href="#dropdown-lvl2">
                                                    <span class="glyphicon glyphicon-off"></span> Sub Level <span
                                                        class="caret"></span>
                                                </a>
                                                <div id="dropdown-lvl2" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <ul class="nav navbar-nav">
                                                            <li><a href="#">Link</a></li>
                                                            <li><a href="#">Link</a></li>
                                                            <li><a href="#">Link</a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </li>--%>
                                        </ul>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div><!-- /.navbar-collapse -->
                </nav>
            </div>
        </div>
    </div>
    <div class="col-md-10 content tab-content">
        <div class="panel panel-default tab-pane active in" id="overviewTab">
            <div class="panel-heading">
                <h3 class="panel-title">Website Overview</h3>
            </div>
            <div class="panel-body">
                <div class="col-md-3">
                    <div class="well dash-box">
                        <h2><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${users.size()}</h2>
                        <h4>Users</h4>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="well dash-box">
                        <h2><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>${orders.size()}</h2>
                        <h4>Orders</h4>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="well dash-box">
                        <h2><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> 7</h2>
                        <h4>Pages</h4>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="well dash-box">
                        <h2><span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span> ${items.size()}</h2>
                        <h4>Products</h4>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <div id="donutchart" class="chart"></div>
                <div id="chart_div" class="chart"></div>
                <div id="linechart_material" class="chart"></div>
            </div>
        </div>



        <div class="panel panel-default tab-pane fade" id="usersTab">
            <div class="panel-body">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Users</h3>
                    </div>
                    <div class="panel-body">
                        <div class="input-group stylish-input-group input-append">
                            <input type="text" class="form-control" id="dev-table-filter" data-action="filter" data-filters="#dev-table" placeholder="Filter Users" />
                            <span class="input-group-addon">
                                <button type="submit">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </span>
                        </div>
                       <%-- <input type="text" class="form-control" id="dev-table-filter" data-action="filter" data-filters="#dev-table" placeholder="Filter Users" />--%>
                    </div>
                    <table class="table table-hover table-striped" id="dev-table">
                        <thead>
                        <%--<a href="#" class="btn btn-primary btn-xs pull-right"><b>+</b> Add new categories</a>--%>
                        <tr>
                            <th><spring:message code="general.userId"/></th>
                            <th><spring:message code="users.fullname"/></th>
                            <th><spring:message code="users.username"/></th>
                            <th><spring:message code="users.phoneNumber"/></th>
                            <th><spring:message code="users.role"/></th>
                            <th class="text-center">Action</th>
                        </tr>
                        </thead>
                        <c:forEach items="${users}" var="user">
                            <tr class="user-tr"  onclick="getUserInfo('<c:out value="${user.userId}"/>')">
                                <td data-toggle="modal" data-target="#user-info-modal">${user.userId}</td>
                                <td data-toggle="modal" data-target="#user-info-modal">${user.fio}</td>
                                <td data-toggle="modal" data-target="#user-info-modal">${user.login}</td>
                                <td data-toggle="modal" data-target="#user-info-modal">${user.phoneNumber}</td>
                                <td data-toggle="modal" data-target="#user-info-modal">${user.role}</td>
                                <td class="text-center">
                                    <select id="dropdown-${user.userId}" class="select-each-role">
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

                                    <c:choose>
                                        <c:when test="${user.login == pageContext.request.remoteUser}">
                                            <a class='btn btn-info btn-xs' href="#" disabled="disabled">
                                                <span class="glyphicon glyphicon-edit"></span> Change role
                                            </a>
                                            <a href="#" class="btn btn-danger btn-xs" disabled="disabled">
                                                <span class="glyphicon glyphicon-remove"></span> Del
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class='btn btn-info btn-xs' href="#" onclick="dropdownButton('${user.userId}', 'dropdown-${user.userId}');">
                                                <span class="glyphicon glyphicon-edit"></span> Change role
                                            </a>
                                            <a href="#" class="btn btn-danger btn-xs" onclick="removeUser(this, '${user.userId}');">
                                                <span class="glyphicon glyphicon-remove"></span> Del
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">New user</h3>
                    </div>
                    <div class="panel-body">
                        <form:form action="${pageContext.request.contextPath}/createUser" method="POST"
                                   modelAttribute="userForm" class="form-signup mg-btm"
                                   id="registrationForm" role="form">
                            <div class="main">
                                <spring:message code="enter.fullname" var="placeholder"/>
                                <form:input type='text' id='fio' path="fio" class="form-control"
                                            placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="fio-validation-message">
                                </div>

                                <spring:message code="enter.username" var="placeholder"/>
                                <form:input type='text' id='login' path="login" class="form-control"
                                            placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="login-validation-message">
                                </div>

                                <spring:message code="enter.email" var="placeholder"/>
                                <form:input type='text' id='email' path="email" class="form-control"
                                            placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="email-validation-message">
                                </div>

                                <spring:message code="enter.password" var="placeholder"/>
                                <form:input type='password' id='passwordHash' path="passwordHash" class="form-control"
                                            placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="password-validation-message">
                                </div>

                                <spring:message code="users.confirmPassword" var="placeholder"/>
                                <form:input type='password' id='confirmPassword' path="confirmPassword"
                                            class="form-control" placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="confirmPassword-validation-message">
                                </div>
                                <form:select id="user-role"  path="role">
                                    <form:option value="ROLE_COURIER"><spring:message
                                            code="users.role.ROLE_COURIER"/></form:option>
                                    <form:option value="ROLE_ADMIN"><spring:message
                                            code="users.role.ROLE_ADMIN"/></form:option>
                                    <form:option value="ROLE_USER"><spring:message code="users.role.ROLE_USER"/></form:option>
                                </form:select>
                                <button type="submit" class="btn btn-info"
                                        id="btn-signUp" disabled="disabled">Create user
                                </button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

        <div class="panel panel-default tab-pane fade" id="ordersTab">
            <div class="panel-heading">
                Orders
            </div>
            <div class="panel-body">
               order
            </div>
        </div>

        <div class="panel panel-default tab-pane fade" id="itemsTab">
            <div class="panel-heading">
                Items
            </div>
            <div class="panel-body">
                item
            </div>
        </div>
    </div>
    <%--<footer class="pull-left footer">
        <p class="col-md-12">
        <hr class="divider">
        Copyright &COPY; 2015 <a href="http://www.pingpong-labs.com">Gravitano</a>
        </p>
    </footer>--%>
</div>

<div class="modal fade" id="user-info-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">User profile</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="reset-values">&times;</span>
                </button>
            </div>
            <div class="content user-content">
                <div class="container container-prof text-center">
                    <div class="avatar-flip">
                        <img class="img-circle" src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg">
                    </div>
                    <div class="text-center">
                        <ul class="details text-left" id="user-data-list">
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3">User Id</span><span id="data-user-id" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3">Role</span><span id="data-role" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3">Login</span><span id="data-login" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3">Full name</span><span id="data-fio" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3">Phone</span><span id="data-phone" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3">E-mail</span><span id="data-email" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3">Birthday</span><span id="data-birthday" class="col-sm-6 user-value"></span></p></li>
                            <hr>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
