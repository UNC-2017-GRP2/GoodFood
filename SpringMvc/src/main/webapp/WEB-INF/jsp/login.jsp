<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/login-style.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript"><%@include file="/resources/js/login-js.js" %></script>
</head>
<body>

<form name='login' action="<c:url value='/login' />" method='POST' class="form-horizontal form-login" id="loginForm" role="form">
    <c:if test="${not empty error}">
        <div class="invalid-data">${error}</div>
    </c:if>
    <div class="form-group top-form-group">
        <label for="inputLogin" class="col-xs-4 control-label">Username:</label>
        <div class="col-xs-6">
            <input type="text" class="form-control" name='username' id="inputLogin" placeholder="Enter username">
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword" class="col-xs-4 control-label">Password:</label>
        <div class="col-xs-6">
            <input type="password" class="form-control" name='password' id="inputPassword" placeholder="Enter password">
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-4 col-xs-4">
            <div class="checkbox">
                <label><input type="checkbox" name="remember-me"> Remember me</label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-4 col-xs-2">
            <button type="submit" class="btn btn-default">Sign In</button>
        </div>
        <div class="col-xs-2">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#formRegistrationModal">Sign Up</button>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<!-- Modal -->
<div class="modal fade" id="formRegistrationModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Registration</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form:form action="/registration" method="POST" modelAttribute="userForm" class="form-horizontal"
                       id="registrationForm" role="form">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="fio" class="col-xs-4 control-label">Full Name:</label>
                        <div class="col-xs-6">
                            <form:input type='text' id='fio' path="fio" class="form-control"
                                        placeholder="Enter Full Name"></form:input>
                        </div>
                    </div>
                    <div class="col-xs-offset-4 col-xs-8 validationMessage">
                        <form:errors path="fio"></form:errors>
                    </div>
                    <div class="form-group">
                        <label for="login" class="col-xs-4 control-label">Username:</label>
                        <div class="col-xs-6">
                            <form:input type='text' id='login' path="login" class="form-control"
                                        placeholder="Enter username"></form:input>
                        </div>
                    </div>
                    <div class="col-xs-offset-4 col-xs-8 validationMessage">
                        <form:errors path="login"></form:errors>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-xs-4 control-label">E-mail:</label>
                        <div class="col-xs-6">
                            <form:input type='text' id='email' path="email" class="form-control"
                                        placeholder="Enter e-mail"></form:input>
                        </div>
                    </div>
                    <div class="col-xs-offset-4 col-xs-8 validationMessage">
                        <form:errors path="email"></form:errors>
                    </div>
                    <div class="form-group">
                        <label for="passwordHash" class="col-xs-4 control-label">Password:</label>
                        <div class="col-xs-6">
                            <form:input type='password' id='passwordHash' path="passwordHash" class="form-control"
                                        placeholder="Enter password"></form:input>
                        </div>
                    </div>
                    <div class="col-xs-offset-4 col-xs-8 validationMessage">
                        <form:errors path="passwordHash"></form:errors>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword" class="col-xs-4 control-label">Confirm password:</label>
                        <div class="col-xs-6">
                            <form:input type='password' id='confirmPassword' path="confirmPassword" class="form-control"
                                        placeholder="Confirm password"></form:input>
                        </div>
                    </div>
                    <div class="col-xs-offset-4 col-xs-8 validationMessage">
                        <form:errors path="confirmPassword"></form:errors>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Sign Up</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form:form>
        </div>
    </div>
</div>
<script>${flag}</script>
</body>
</html>