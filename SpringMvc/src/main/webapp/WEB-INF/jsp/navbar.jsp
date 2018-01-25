<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<% String username = (String) session.getAttribute("username"); %>
<html>
<body>

<c:url value="/logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="home"><spring:message code="general.projectName"/></a>
        </div>

        <ul class="nav navbar-nav navbar-right">
            <!--<li><a href="#">Link</a></li>-->
            <li class="dropdown">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%= username %> <span class="caret"></span></a>
                    </c:when>
                    <c:when test="${pageContext.request.userPrincipal.name == null}">
                        <a class="navbar-brand" href="login">Sign In/Sign Up</a>
                    </c:when>
                </c:choose>
                <ul class="dropdown-menu">
                    <li><a href="home?value=Pizza"><spring:message code="general.mainPage"/></a></li>
                    <li><a href="profile"><spring:message code="general.profile"/></a></li>
                    <li><a href="my-orders"><spring:message code="general.myOrders"/></a></li>
                    <li><a href="basket"><spring:message code="general.basket"/></a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="javascript:document.getElementById('logout').submit()"><spring:message code="navbar.logout"/></a></li>
                </ul>
            </li>
        </ul>
    </div>
    </div>
</nav>

</body>
</html>
