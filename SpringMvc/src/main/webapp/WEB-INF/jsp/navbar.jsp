<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
            <a class="navbar-brand" href="home">ProjectUNC</a>
        </div>

        <ul class="nav navbar-nav navbar-right">
            <!--<li><a href="#">Link</a></li>-->
            <li class="dropdown">
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${user.login} <span class="caret"></span></a>
                </c:if>
                <ul class="dropdown-menu">
                    <li><a href="home">Main page</a></li>
                    <li><a href="profile">Profile</a></li>
                    <li><a href="my-orders">My Orders</a></li>
                    <li><a href="basket">Basket</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="javascript:document.getElementById('logout').submit()">Logout</a></li>
                </ul>
            </li>
        </ul>
    </div>
    </div>
</nav>

</body>
</html>
