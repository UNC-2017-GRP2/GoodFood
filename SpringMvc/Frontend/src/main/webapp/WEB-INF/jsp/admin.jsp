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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/datatables/1.10.12/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/datatables/1.10.12/css/dataTables.material.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/webjars/bootstrap-select/1.4.2/bootstrap-select.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
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
        $(document).ready(function () {
            pageContext = ${pageContext.request.contextPath};
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
                        <%--<div class="input-group stylish-input-group input-append">
                            <spring:message code="admin.users.filter" var="placeholder"/>
                            <input type="text" class="form-control" id="users-table-filter" data-action="filter"
                                   data-filters="#users-table" placeholder="${placeholder}"/>
                            <span class="input-group-addon">
                                <button type="submit">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </span>
                        </div>--%>
                        <%-- <input type="text" class="form-control" id="users-table-filter" data-action="filter" data-filters="#users-table" placeholder="Filter Users" />--%>
                        <table class="table table-hover table-striped table-bordered" id="users-table">
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
                                            <option value="ROLE_USER"><spring:message
                                                    code="users.role.ROLE_USER"/></option>
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
                        <div style="margin-bottom: 10px;">
                            <form id="actualize-form" action="/admin/actualize" method="post">
                                <button type="submit" class="btn btn-default btn-xs form-control"><spring:message
                                        code="admin.actualize"/></button>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </div>

                        <table class="table table-hover table-striped table-bordered" id="orders-table">
                            <thead>
                            <tr class="order-head">
                                <th><spring:message code="orders.orderId"/></th>
                                <th><spring:message code="general.userId"/></th>
                                <th><spring:message code="orders.status"/></th>
                                <th><spring:message code="orders.items"/></th>
                                <th><spring:message code="orders.orderCreationDate"/></th>
                                <th><spring:message code="orders.timeSinceCreation"/></th>
                                <th><spring:message code="orders.cost"/></th>
                                <th><spring:message code="admin.users.actions"/></th>
                            </tr>
                            </thead>
                            <c:forEach items="${orders}" var="order">
                                <tr class="order-row" onclick="getOrderInfo('<c:out value="${order.orderId}"/>');">
                                    <td data-toggle="modal" data-target="#order-info-modal">${order.orderId}</td>
                                    <td data-toggle="modal" data-target="#order-info-modal">${order.userId}</td>
                                    <td data-toggle="modal" data-target="#order-info-modal">${order.status}</td>
                                    <td data-toggle="modal" data-target="#order-info-modal"><c:forEach items="${order.orderItems}" var="item">
                                        ${item.productName}<br/>
                                        <%--${item.productCost} ₽<br/>--%>
                                    </c:forEach>
                                    </td>
                                    <td data-toggle="modal" data-target="#order-info-modal">${order.orderCreationDate.toString()}</td>
                                    <td id="creation-date-until-td" data-toggle="modal" data-target="#order-info-modal">${order.orderCreationDate.until(now, chr)}</td>
                                    <td id="order-cost-td" data-toggle="modal" data-target="#order-info-modal">${order.orderCost} ₽</td>
                                    <td class="text-center td-for-items-btns">
                                        <a href="#" class="btn btn-danger btn-xs"
                                           onclick="removeOrder(this, '${order.orderId}');">
                                            <span class="glyphicon glyphicon-remove"></span><spring:message
                                                code="admin.item.del"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
                <%--<div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"></h3>
                    </div>
                    <div class="panel-body">

                    </div>
                </div>--%>
            </div>
        </div>

        <div class="panel panel-default tab-pane fade" id="itemsTab">
            <div class="panel-body">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><spring:message code="admin.products"/></h3>
                    </div>
                    <div class="panel-body">
                        <table class="table table-hover table-striped table-bordered" id="items-table">
                            <thead>
                            <tr>
                                <th><spring:message code="admin.item.itemId"/></th>
                                <th><spring:message code="admin.item.image"/></th>
                                <th><spring:message code="admin.item.category"/></th>
                                <th><spring:message code="admin.item.name"/></th>
                                <th><spring:message code="admin.item.description"/></th>
                                <th><spring:message code="admin.item.cost"/></th>
                                <th class="text-center"><spring:message code="admin.users.actions"/></th>
                            </tr>
                            </thead>
                            <c:forEach items="${items}" var="item">
                                <tr class="item-row">
                                    <td>${item.productId}</td>
                                    <td class="media col-md-2">
                                        <img class="media-object img-rounded img-responsive"
                                             src="${pageContext.request.contextPath}${item.productImage}">
                                    </td>
                                    <td>${item.productCategory}</td>
                                    <td>${item.productName}</td>
                                    <td>${item.productDescription}</td>
                                    <td>${item.productCost} ₽</td>
                                    <td class="text-center td-for-items-btns">
                                        <a class="btn btn-info btn-xs btn-for-item" href="#"  data-toggle="modal" data-target="#edit-item-modal" onclick="editItem('${item.productId}');" >
                                            <span class="glyphicon glyphicon-edit"></span><spring:message
                                                code="admin.item.edit"/>
                                        </a><br><br>
                                        <a href="#" class="btn btn-danger btn-xs btn-for-item"
                                           onclick="removeItem(this, '${item.productId}');">
                                            <span class="glyphicon glyphicon-remove"></span><spring:message
                                                code="admin.item.del"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><spring:message code="admin.item.add_items"/></h3>
                    </div>
                    <div class="panel-body">
                        <%--<form id="add-item-form" action="/admin/createItems" method="post" enctype="multipart/form-data">
                            <input type="file" name="file" accept=".xls,.xlsx" />
                            <button type="submit" class="btn btn-default">Upload</button>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>

                        <form method="post" action="/admin/uploadFile" enctype="multipart/form-data">
                            File to upload: <input type="file" id="file" name="file"><br />
                            Name:<br /> <br />
                            <button type="submit" class="btn btn-default">Upl</button> Press here to upload the file!
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>--%>

                            <form method="POST" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data">
                                <input type="file" name="file" /><br/>
                                <input type="submit" value="Submit" />
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                            <%--<img class="media-object img-rounded img-responsive"
                                 src="${pageContext.request.contextPath}/resources/img/mysterion.jpg">--%>

                    </div>
                </div>
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
                            <li>
                                <p class="row">
                                    <span class="col-sm-1"></span>
                                    <span class="col-sm-4"><spring:message code="general.userId"/></span>
                                    <span id="data-user-id" class="col-sm-6 user-value"></span>
                                </p>
                            </li>
                            <li>
                                <p class="row">
                                    <span class="col-sm-1"></span>
                                    <span class="col-sm-4"><spring:message
                                    code="users.role"/></span>
                                    <span id="data-role" class="col-sm-6 user-value"></span>
                                </p>
                            </li>
                            <li>
                                <p class="row">
                                    <span class="col-sm-1"></span>
                                    <span class="col-sm-4"><spring:message code="users.username"/></span>
                                    <span id="data-login" class="col-sm-6 user-value"></span>
                                </p>
                            </li>
                            <li>
                                <p class="row">
                                    <span class="col-sm-1"></span>
                                    <span class="col-sm-4"><spring:message code="users.fullname"/></span>
                                    <span id="data-fio" class="col-sm-6 user-value"></span>
                                </p>
                            </li>
                            <li>
                                <p class="row">
                                    <span class="col-sm-1"></span>
                                    <span class="col-sm-4"><spring:message code="users.phoneNumber"/></span>
                                    <span id="data-phone" class="col-sm-6 user-value"></span>
                                </p>
                            </li>
                            <li>
                                <p class="row">
                                    <span class="col-sm-1"></span>
                                    <span class="col-sm-4"><spring:message code="users.email"/></span>
                                    <span id="data-email" class="col-sm-6 user-value"></span>
                                </p>
                            </li>
                            <li>
                                <p class="row">
                                    <span class="col-sm-1"></span>
                                    <span class="col-sm-4"><spring:message code="users.birthday"/></span>
                                    <span id="data-birthday" class="col-sm-6 user-value"></span>
                                </p>
                            </li>
                            <hr>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="order-info-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><strong><spring:message code="admin.order_info"/></strong></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="reset-values">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container-order text-center">
                    <div class="text-center order-info">
                        <div class="row text-left">
                            <div class="col-sm-12"><strong><spring:message code="orders.order_title"/></strong></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="orders.orderId"/></div>
                            <div class="col-sm-7 order-value" id="order-id"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="orders.status"/></div>
                            <div class="col-sm-7 order-value" id="order-status"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="orders.orderProcessed"/></div>
                            <div class="col-sm-7 order-value" id="order-date"></div>
                        </div>
                        <%--<div class="row text-left">
                            <div class="col-sm-5"><spring:message code="orders.timeSinceCreation"/></div>
                            <div class="col-sm-7" id="order-date-until">${order.orderCreationDate.until(now, chr)}</div>
                        </div>--%>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="orders.deliveryTo"/></div>
                            <div class="col-sm-7 order-value" id = "order-address"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="orders.phone"/></div>
                            <div class="col-sm-7 order-value" id = "order-phone"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="orders.paymentType"/></div>
                            <div class="col-sm-7 order-value" id="order-payment-type"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="orders.paymentState"/></div>
                            <div class="col-sm-7 order-value" id="order-paid"></div>
                        </div>
                        <ul class="details text-left">
                            <hr>
                        </ul>

                        <div class="row text-left">
                            <div class="col-sm-12"><strong><spring:message code="orders.order_items"/></strong></div>
                        </div>
                        <div id="order-items">
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"></div>
                            <div class="col-sm-4"><spring:message
                                    code="orders.totalOrderCost"/></div>
                            <div class="col-sm-3" id="order-cost"></div>
                        </div>
                        <ul class="details text-left">
                            <hr>
                        </ul>
                        <%--USER--%>

                        <div class="row text-left">
                            <div class="col-sm-12"><strong><spring:message code="users.user_info.title"/></strong></div>
                        </div>

                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="general.userId"/></div>
                            <div class="col-sm-7 user-value-order" id="user-id-order"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="users.role"/></div>
                            <div class="col-sm-7 user-value-order" id="user-role-order"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="users.username"/></div>
                            <div class="col-sm-7 user-value-order" id="user-login-order"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="users.fullname"/></div>
                            <div class="col-sm-7 user-value-order" id="user-fio-order"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="users.phoneNumber"/></div>
                            <div class="col-sm-7 user-value-order" id="user-phone-order"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="users.email"/></div>
                            <div class="col-sm-7 user-value-order" id="user-email-order"></div>
                        </div>
                        <div class="row text-left">
                            <div class="col-sm-5"><spring:message code="users.birthday"/></div>
                            <div class="col-sm-7 user-value-order" id="user-birthday-order"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="edit-item-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><strong><spring:message code="admin.editing_item"/></strong></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="reset-values">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container-item text-center">
                    <div class="text-left item-info">
                        <div class="row text-left edit-item-row">
                            <label for="item-id" class="col-xs-4 control-label"><spring:message
                                    code="admin.item.itemId"/>:</label>
                            <div class="col-xs-6">
                                <input type='text' id='item-id' class="form-control" disabled="disabled">
                            </div>
                        </div>

                        <div class="row text-left edit-item-row">
                            <label for="item-category" class="col-xs-4 control-label"><spring:message
                                    code="admin.item.category"/>:</label>
                            <div class="col-xs-6">
                                <select id='item-category' class="form-control">
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category}">${category}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="row text-left edit-item-row">
                            <label for="item-name-en" class="col-xs-4 control-label"><spring:message code="admin.item.name_en"/>:</label>
                            <div class="col-xs-6">
                                <input type="text" id='item-name-en' class="form-control item-param">
                            </div>
                        </div>
                        <div class="row text-left edit-item-row">
                            <label for="item-name-ru" class="col-xs-4 control-label"><spring:message code="admin.item.name_ru"/>:</label>
                            <div class="col-xs-6">
                                <input type="text" id='item-name-ru' class="form-control item-param">
                            </div>
                        </div>
                        <div class="row text-left edit-item-row">
                            <label for="item-name-uk" class="col-xs-4 control-label"><spring:message code="admin.item.name_uk"/>:</label>
                            <div class="col-xs-6">
                                <input type="text" id='item-name-uk' class="form-control item-param">
                            </div>
                        </div>

                        <div class="row text-left edit-item-row">
                            <label for="item-description-en" class="col-xs-4 control-label"><spring:message code="admin.item.description_en"/>:</label>
                            <div class="col-xs-6">
                                <textarea id='item-description-en' class="form-control item-param"></textarea>
                            </div>
                        </div>
                        <div class="row text-left edit-item-row">
                            <label for="item-description-ru" class="col-xs-4 control-label"><spring:message code="admin.item.description_ru"/>:</label>
                            <div class="col-xs-6">
                                <textarea id='item-description-ru' class="form-control item-param"></textarea>
                            </div>
                        </div>
                        <div class="row text-left edit-item-row">
                            <label for="item-description-uk" class="col-xs-4 control-label"><spring:message code="admin.item.description_uk"/>:</label>
                            <div class="col-xs-6">
                                <textarea id='item-description-uk' class="form-control item-param"></textarea>
                            </div>
                        </div>

                        <div class="row text-left edit-item-row">
                            <label for="item-cost" class="col-xs-4 control-label"><spring:message code="admin.item.cost"/>:</label>
                            <div class="col-xs-6">
                                <input type='text' id='item-cost'
                                       class="form-control item-param">
                            </div>
                        </div>

                        <ul class="details text-left">
                            <hr>
                        </ul>

                        <div class="row text-left edit-item-row">
                            <label for="item-image" class="col-xs-4 control-label"><spring:message code="admin.item.image"/>:</label>
                            <div class="media col-xs-6">
                                <img id="item-image" class="media-object img-rounded img-responsive"
                                     src="${pageContext.request.contextPath}">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary"><spring:message code="profile.save"></spring:message></button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
