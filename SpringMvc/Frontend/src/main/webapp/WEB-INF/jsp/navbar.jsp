<%@ page import="com.netcracker.config.Constant" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/navbar-style.css">
    <script type="text/javascript"><%@include file="/resources/js/navbar-js.js" %></script>
</head>
<body>

<c:url value="/logout" var="logoutUrl"/>
<form id="logout" action="${logoutUrl}" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<nav class="navbar navbar-default navbar-static-top" role="navigation">
<div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_PIZZA%>"><div class="logo"><h1><spring:message code="general.projectName"/></h1></div></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav navbar-right">
            <%--<li><a href="${pageContext.request.contextPath}/basket"><span class="glyphicon glyphicon-cart"></span></a></li>--%>
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal.name != null}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><c:out value="${pageContext.request.remoteUser}"/> <b class="caret"></b></a>
                        <ul class="dropdown-menu">

                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <li>
                                        <a href="<c:url value='/admin'/>"><spring:message code="general.adminPanel"/></a>
                                    </li>
                                </sec:authorize>
                            </c:if>

                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <sec:authorize access="hasAnyRole('ROLE_USER')">
                                    <li><a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_PIZZA%>"><spring:message code="general.mainPage"/></a></li>
                                </sec:authorize>
                            </c:if>

                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_COURIER')">
                                    <li><a href="<c:url value='/my-orders/1'/>"><spring:message code="general.myOrders"/></a></li>
                                </sec:authorize>
                            </c:if>

                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <sec:authorize access="hasAnyRole('ROLE_USER')">
                                    <li><a href="<c:url value='/basket'/>"><spring:message code="general.basket"/></a></li>
                                </sec:authorize>
                            </c:if>

                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <sec:authorize access="hasAnyRole('ROLE_COURIER')">
                                    <li>
                                        <a href="<c:url value='/free-orders'/>"><spring:message code="orders.freeOrders"/></a>
                                    </li>
                                    <li>
                                        <a href="<c:url value='/current-orders'/>"><spring:message code="orders.currentOrders"/></a>
                                    </li>
                                </sec:authorize>
                            </c:if>

                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_COURIER')">
                                    <li><a href="<c:url value='/profile'/>"><spring:message code="general.profile"/></a></li>
                                </sec:authorize>
                            </c:if>
                            <li role="separator" class="divider"></li>
                            <p align="center">
                                <a href="?lang=en"><img src="${pageContext.request.contextPath}/resources/img/flags/United-Kingdom.png" border="1"></a>
                                <a href="?lang=uk"><img src="${pageContext.request.contextPath}/resources/img/flags/Ukraine.png" border="1"></a>
                                <a href="?lang=ru"><img src="${pageContext.request.contextPath}/resources/img/flags/Russia.png" border="1"></a></p>
                            <li role="separator" class="divider"></li>
                            <li><a href="javascript:document.getElementById('logout').submit()"><spring:message
                                    code="navbar.logout"/></a></li>
                        </ul>
                    </li>
                </c:when>
                <c:when test="${pageContext.request.userPrincipal.name == null}">
                    <%--<li><a class="navbar-brand" href="login">Sign In/Sign Up</a></li>--%>
                    <%--<li><a href="login"><span class="glyphicon glyphicon-log-in"></span>Login / Sign Up</a></li>--%>
                    <li>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a  href="<c:url value='/login'/>" ><span class="glyphicon glyphicon-log-in"></span> Login | Sign Up</a></li>
                            <li role="separator" class="divider"></li>
                            <p align="center">
                                <a href="?lang=en"><img src="${pageContext.request.contextPath}/resources/img/flags/United-Kingdom.png" border="1"></a>
                                <a href="?lang=uk"><img src="${pageContext.request.contextPath}/resources/img/flags/Ukraine.png" border="1"></a>
                                <a href="?lang=ru"><img src="${pageContext.request.contextPath}/resources/img/flags/Russia.png" border="5"></a></p>

                        </ul>
                    </li>
                </c:when>
            </c:choose>
        </ul>
    </div><!-- /.navbar-collapse -->
</div><!-- /.container-collapse -->
</nav>

</body>
</html>
