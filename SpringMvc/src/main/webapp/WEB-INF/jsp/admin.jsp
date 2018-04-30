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
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/webjars/bootstrap-select/1.4.2/bootstrap-select.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/bootstrap-select/1.4.2/bootstrap-select.min.js"></script>
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
                title: getLocStrings('product_order_statistics'),
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
                [getLocStrings('day_of_the_week'), getLocStrings('revenue')],
                ['${weekDays[1]}', ${coreChartDataMap.get(weekDays[1])}],
                ['${weekDays[2]}', ${coreChartDataMap.get(weekDays[2])}],
                ['${weekDays[3]}', ${coreChartDataMap.get(weekDays[3])}],
                ['${weekDays[4]}', ${coreChartDataMap.get(weekDays[4])}],
                ['${weekDays[5]}', ${coreChartDataMap.get(weekDays[5])}],
                ['${weekDays[6]}', ${coreChartDataMap.get(weekDays[6])}],
                ['${weekDays[7]}', ${coreChartDataMap.get(weekDays[7])}]
            ]);

            var options = {
                title: getLocStrings('revenue_day'),
                chartArea: {width: '50%'},
                hAxis: {
                    title: getLocStrings('revenue'),
                    minValue: 0
                },
                vAxis: {
                    title: getLocStrings('day_of_the_week')
                }
            };

            var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }

        google.charts.load('current', {'packages': ['line']});
        google.charts.setOnLoadCallback(drawLineChartMaterial);

        function drawLineChartMaterial() {

            var data = google.visualization.arrayToDataTable([
                [getLocStrings('day'), getLocStrings('revenue')],
                <c:forEach items="${revenuePerDayMap}" var="item">
                ['${item.key}', ${item.value}],
                </c:forEach>
            ]);

            var options = {
                chart: {
                    title: getLocStrings('revenue_last_days')
                }
            };
            var chart = new google.charts.Line(document.getElementById('linechart_material'));
            chart.draw(data, google.charts.Line.convertOptions(options));
        }
    </script>
</head>
<body>
<jsp:include page="navbar.jsp"/>

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
                                    class="glyphicon glyphicon-dashboard"></span><spring:message code="admin.overview"/></a>
                            </li>
                            <li><a href="#usersTab" data-toggle="tab"><span
                                    class="glyphicon glyphicon-user"></span><spring:message code="admin.users"/></a>
                            </li>
                            <li><a href="#ordersTab" data-toggle="tab"><span
                                    class="glyphicon glyphicon-shopping-cart"></span><spring:message
                                    code="admin.orders"/></a></li>
                            <li><a href="#itemsTab" data-toggle="tab"><span
                                    class="glyphicon glyphicon-cutlery"></span><spring:message
                                    code="admin.products"/></a></li>
                            <li class="panel panel-default my-dropdown">
                                <a data-toggle="collapse" href="#dropdown-lvl1">
                                    <span class="glyphicon glyphicon-list-alt"></span><spring:message
                                        code="admin.pages"/><span class="caret"></span>
                                </a>
                                <div id="dropdown-lvl1" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <ul class="nav navbar-nav">
                                            <li><a href="<c:url value='/admin'/>"><spring:message
                                                    code="general.adminPanel"/></a></li>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_PIZZA%>"><spring:message
                                                        code="general.mainPage"/></a></li>
                                            <li><a href="<c:url value='/my-orders/1'/>"><spring:message
                                                    code="general.myOrders"/></a></li>
                                            <li><a href="<c:url value='/basket'/>"><spring:message
                                                    code="general.basket"/></a></li>
                                            <li><a href="<c:url value='/free-orders'/>"><spring:message
                                                    code="orders.freeOrders"/></a></li>
                                            <li><a href="<c:url value='/current-orders'/>"><spring:message
                                                    code="orders.currentOrders"/></a></li>
                                            <li><a href="<c:url value='/profile'/>"><spring:message
                                                    code="general.profile"/></a></li>
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
            <div class="panel-body">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><spring:message code="admin.website_overview"/></h3>
                    </div>
                    <div class="panel-body">
                        <div class="col-md-3">
                            <div class="well dash-box">
                                <h2><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${users.size()}
                                </h2>
                                <h4><spring:message code="admin.users"/></h4>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="well dash-box">
                                <h2><span class="glyphicon glyphicon-shopping-cart"
                                          aria-hidden="true"></span>${orders.size()}</h2>
                                <h4><spring:message code="admin.orders"/></h4>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="well dash-box">
                                <h2><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> 7</h2>
                                <h4><spring:message code="admin.pages"/></h4>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="well dash-box">
                                <h2><span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span> ${items.size()}
                                </h2>
                                <h4><spring:message code="admin.products"/></h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><spring:message code="admin.product_order_statistics"/></h3>
                    </div>
                    <div class="panel-body">
                        <div id="donutchart" class="chart"></div>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><spring:message code="admin.revenue_day"/></h3>
                    </div>
                    <div class="panel-body">
                        <div id="chart_div" class="chart"></div>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><spring:message code="admin.revenue_last_days"/></h3>
                    </div>
                    <div class="panel-body">
                        <div id="linechart_material" class="chart"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="panel panel-default tab-pane fade" id="usersTab">
            <div class="panel-body">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><spring:message code="admin.users"/></h3>
                    </div>
                    <div class="panel-body">
                        <div class="input-group stylish-input-group input-append">
                            <spring:message code="admin.users.filter" var="placeholder"/>
                            <input type="text" class="form-control" id="dev-table-filter" data-action="filter"
                                   data-filters="#dev-table" placeholder="${placeholder}"/>
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
                            <th class="text-center"><spring:message code="admin.users.actions"/></th>
                        </tr>
                        </thead>
                        <c:forEach items="${users}" var="user">
                            <tr class="user-tr" onclick="getUserInfo('<c:out value="${user.userId}"/>');">
                                <td data-toggle="modal" data-target="#user-info-modal">${user.userId}</td>
                                <td data-toggle="modal" data-target="#user-info-modal">${user.fio}</td>
                                <td data-toggle="modal" data-target="#user-info-modal">${user.login}</td>
                                <td data-toggle="modal" data-target="#user-info-modal">${user.phoneNumber}</td>
                                <td data-toggle="modal" data-target="#user-info-modal"
                                    id="user-role-${user.userId}">${user.role}</td>
                                <td class="text-center">
                                    <select id="dropdown-${user.userId}" class="select-each-role">
                                        <option value="ROLE_COURIER"><spring:message
                                                code="users.role.ROLE_COURIER"/></option>
                                        <option value="ROLE_ADMIN"><spring:message
                                                code="users.role.ROLE_ADMIN"/></option>
                                        <option value="ROLE_USER"><spring:message code="users.role.ROLE_USER"/></option>
                                    </select>
                                    <script>selectOption('dropdown-${user.userId}', '${user.role}');</script>
                                    <c:choose>
                                        <c:when test="${user.login == pageContext.request.remoteUser}">
                                            <a class='btn btn-info btn-xs' href="#" disabled="disabled">
                                                <span class="glyphicon glyphicon-edit"></span><spring:message
                                                    code="admin.btn.change_role"/>
                                            </a>
                                            <a href="#" class="btn btn-danger btn-xs" disabled="disabled">
                                                <span class="glyphicon glyphicon-remove"></span><spring:message
                                                    code="admin.btn.del_user"/>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class='btn btn-info btn-xs' href="#"
                                               onclick="changeRole('${user.userId}', 'dropdown-${user.userId}');">
                                                <span class="glyphicon glyphicon-edit"></span><spring:message
                                                    code="admin.btn.change_role"/>
                                            </a>
                                            <a href="#" class="btn btn-danger btn-xs"
                                               onclick="removeUser(this, '${user.userId}');">
                                                <span class="glyphicon glyphicon-remove"></span><spring:message
                                                    code="admin.btn.del_user"/>
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
                        <h3 class="panel-title"><spring:message code="admin.create_user"/></h3>
                    </div>
                    <div class="panel-body">
                        <form:form action="${pageContext.request.contextPath}/createUser" method="POST"
                                   modelAttribute="userForm" class="form-signup mg-btm"
                                   id="createUserForm" role="form">
                            <div class="main">
                                <spring:message code="enter.fullname" var="placeholder"/>
                                <form:input type='text' id='fio' path="fio" name="fio" class="form-control"
                                            placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="fio-validation-message">
                                </div>

                                <spring:message code="enter.username" var="placeholder"/>
                                <form:input type='text' id='login' path="login" name="login" class="form-control"
                                            placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="login-validation-message">
                                </div>

                                <spring:message code="enter.email" var="placeholder"/>
                                <form:input type='text' id='email' path="email" name="email" class="form-control"
                                            placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="email-validation-message">
                                </div>

                                <spring:message code="enter.password" var="placeholder"/>
                                <form:input type='password' id='passwordHash' path="passwordHash" name="passwordHash"
                                            class="form-control"
                                            placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="password-validation-message">
                                </div>

                                <spring:message code="users.confirmPassword" var="placeholder"/>
                                <form:input type='password' id='confirmPassword' name="confirmPassword"
                                            path="confirmPassword"
                                            class="form-control" placeholder='${placeholder}'></form:input>
                                <div class="alert alert-danger col-sm-12 validation-message"
                                     id="confirmPassword-validation-message">
                                </div>
                                <form:select id="user-role" path="role" name="role">
                                    <form:option value="ROLE_COURIER"><spring:message
                                            code="users.role.ROLE_COURIER"/></form:option>
                                    <form:option value="ROLE_ADMIN"><spring:message
                                            code="users.role.ROLE_ADMIN"/></form:option>
                                    <form:option value="ROLE_USER"><spring:message
                                            code="users.role.ROLE_USER"/></form:option>
                                </form:select>
                                <button type="button" class="btn btn-info"
                                        id="btn-signUp" disabled="disabled" onclick="createUser();"><spring:message
                                        code="admin.create_user"/>
                                </button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

        <div class="panel panel-default tab-pane fade" id="ordersTab">
            <div class="panel-body">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><spring:message code="admin.orders"/></h3>
                    </div>
                    <div class="panel-body">
                        <form action="/admin/actualize" method="post">
                            <button type="submit" class="btn btn-info btn-xs pull-right"><spring:message
                                    code="admin.actualize"/></button>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </div>
                    <table class="table table-hover table-striped" id="orders-table">
                        <thead>
                        <tr class="order-head">
                            <th><spring:message code="orders.orderId"/></th>
                            <th><spring:message code="general.userId"/></th>
                            <th><spring:message code="orders.status"/></th>
                            <th><spring:message code="orders.items"/></th>
                            <th><spring:message code="orders.orderCreationDate"/></th>
                            <th><spring:message code="orders.timeSinceCreation"/></th>
                            <th><spring:message code="orders.cost"/></th>
                        </tr>
                        </thead>
                        <c:forEach items="${orders}" var="order">
                            <tr class="order-row" onclick="getOrderInfo('${order.orderId}');">
                                <td>${order.orderId}</td>
                                <td>${order.userId}</td>
                                <td>${order.status}</td>
                                <td><c:forEach items="${order.orderItems}" var="item">
                                    ${item.productName}<br/>
                                    ${item.productCost} ₽<br/>
                                </c:forEach>
                                </td>
                                <td>${order.orderCreationDate.toString()}</td>
                                <td>${order.orderCreationDate.until(now, chr)}</td>
                                <td style="text-align: center">${order.orderCost} ₽</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"></h3>
                    </div>
                    <div class="panel-body">

                    </div>
                </div>
            </div>
        </div>

        <div class="panel panel-default tab-pane fade" id="itemsTab">
            <div class="panel-heading">
                <spring:message code="admin.products"/>
            </div>
            <div class="panel-body">
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
                <h5 class="modal-title" id="exampleModalLongTitle"><spring:message code="profile.userProfile"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="reset-values">&times;</span>
                </button>
            </div>
            <div class="content user-content">
                <div class="container container-prof text-center">
                    <div class="avatar-flip">
                        <img class="img-circle"
                             src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg">
                    </div>
                    <div class="text-center">
                        <ul class="details text-left" id="user-data-list">
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3"><spring:message
                                    code="general.userId"/></span><span
                                    id="data-user-id" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3"><spring:message
                                    code="users.role"/></span><span
                                    id="data-role" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3"><spring:message
                                    code="users.username"/></span><span
                                    id="data-login" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span
                                    class="col-sm-3"><spring:message code="users.fullname"/></span><span id="data-fio"
                                                                                                         class="col-sm-6 user-value"></span>
                            </p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3"><spring:message
                                    code="users.phoneNumber"/></span><span
                                    id="data-phone" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3"><spring:message
                                    code="users.email"/></span><span
                                    id="data-email" class="col-sm-6 user-value"></span></p></li>
                            <li><p class="row"><span class="col-sm-1"></span><span class="col-sm-3"><spring:message
                                    code="users.birthday"/></span><span
                                    id="data-birthday" class="col-sm-6 user-value"></span></p></li>
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
