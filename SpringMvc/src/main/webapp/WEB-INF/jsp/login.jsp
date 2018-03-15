<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="general.projectName"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login-style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery.inputmask/3.1.0/inputmask/jquery.inputmask.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery.inputmask/3.1.0/inputmask/jquery.inputmask.date.extensions.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login-js.js"></script>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="blog-section paddingTB60 bg-grey ">
    <div class="container form-container">
        <div class="row row-rotate">
            <form class="form-signin mg-btm" action="<c:url value='/login' />" method="post" id="loginForm">
                <h3 class="heading-desc"><spring:message code="general.signInForm"></spring:message></h3>
                <div class="social-box">
                    <div class="row mg-btm">
                        <div class="col-md-12">
                            <a href="#" class="btn btn-primary btn-block">
                                <i class="icon-facebook"></i>    Login with Facebook
                            </a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <a href="#" class="btn btn-info btn-block" >
                                <i class="icon-twitter"></i>    Login with Twitter
                            </a>
                        </div>
                    </div>
                </div>
                <div class="main">
                    <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12">
                        <c:if test="${not empty error}">
                            <script>showErrorMessage();</script>
                            ${error}
                        </c:if>
                    </div>
                    <spring:message code="enter.username" var="placeholder"/>
                    <input id="login-username" type="text" class="form-control" name="username" value="" placeholder='${placeholder}'>
                    <spring:message code="enter.password" var="placeholder"/>
                    <input id="login-password" type="password" class="form-control" name="password" placeholder='${placeholder}'>
                    <div class="checkbox">
                        <label>
                            <%--<input id="login-remember" type="checkbox" name="remember-me" value="1"> <span class="label-text"><spring:message code="profile.rememberMe"/></span>--%>
                            <input id="login-remember" type="checkbox" name="remember-me" value="1"><spring:message code="profile.rememberMe"/>
                        </label>
                    </div>
                    <span class="clearfix"></span>
                </div>
                <div class="login-footer">
                    <div class="row">
                        <div class="col-xs-6 col-md-6">
                            <div class="left-section">
                                <%--<a href="">Forgot your password?</a>--%>
                                <a href="#" id="sign-up-reference"><spring:message code="profile.signUp"/></a>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-6 pull-right">
                            <button type="submit" class="btn btn-large btn-danger pull-right"><spring:message code="profile.signIn"/></button>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

            <form:form action="${pageContext.request.contextPath}/registration" method="POST" modelAttribute="userForm" class="form-signup mg-btm"
                       id="registrationForm" role="form">
                <h3 class="heading-desc"><spring:message code="general.signUpForm"></spring:message></h3>
                <div class="main">
                    <spring:message code="enter.fullname" var="placeholder"/>
                    <form:input type='text' id='fio' path="fio" class="form-control" placeholder='${placeholder}'></form:input>
                            <div class="alert alert-danger col-sm-12 validation-message" id="fio-validation-message">
                            </div>

                    <spring:message code="enter.username" var="placeholder"/>
                    <form:input type='text' id='login' path="login" class="form-control" placeholder='${placeholder}'></form:input>
                    <div class="alert alert-danger col-sm-12 validation-message" id="login-validation-message">
                    </div>

                    <spring:message code="enter.email" var="placeholder"/>
                    <form:input type='text' id='email' path="email" class="form-control" placeholder='${placeholder}'></form:input>
                    <div class="alert alert-danger col-sm-12 validation-message" id="email-validation-message">
                    </div>

                    <spring:message code="enter.password" var="placeholder"/>
                    <form:input type='password' id='passwordHash' path="passwordHash" class="form-control" placeholder='${placeholder}'></form:input>
                    <div class="alert alert-danger col-sm-12 validation-message" id="password-validation-message">
                    </div>

                    <spring:message code="users.confirmPassword" var="placeholder"/>
                    <form:input type='password' id='confirmPassword' path="confirmPassword" class="form-control" placeholder='${placeholder}'></form:input>
                    <div class="alert alert-danger col-sm-12 validation-message" id="confirmPassword-validation-message">
                    </div>
                    <span class="clearfix"></span>
                </div>
                <div class="login-footer">
                    <div class="row">
                        <div class="col-xs-6 col-md-6">
                            <div class="left-section">
                                <a href="#" id="sign-in-reference"><spring:message code="profile.signIn"/></a>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-6 pull-right">
                            <button type="submit" class="btn btn-large btn-danger pull-right" id="btn-signUp" disabled="disabled"><spring:message code="profile.signUp"/></button>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form:form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script>${flag}</script>
</body>
</html>
