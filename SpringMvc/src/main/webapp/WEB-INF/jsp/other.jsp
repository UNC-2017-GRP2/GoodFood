<%@ page import="com.netcracker.config.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/create-json-object-js.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/other-js.js"></script>
    <style type="text/css">
        * {
            font-family: "Roboto";
            list-style: none;
            margin: 0;
            padding: 0;
            text-decoration: none;
            letter-spacing: 1px;
            box-sizing: border-box;
        }

        .bg-grey {
            border-top: 1px solid rgba(0, 0, 0, .1);
            border-bottom: 1px solid rgba(0, 0, 0, .1);
            background-color: #ffffff;
        }

        .paddingTB60{
            padding-top:60px;
            padding-bottom:60px;
        }

        /*---------------------------------Content--------------------------------------*/

        .validation-message{
            display: none;
        }


        .form-signup {
            max-width: 400px;
            display:block;
            margin: 0 auto;
            vertical-align: middle;
            background-color: #f7f7f7;
            -moz-box-shadow: 0 0 3px 3px #888;
            -webkit-box-shadow: 0 0 3px 3px #888;
            box-shadow: 0 0 3px 3px #888;
            border-radius:2px;
        }

        #registrationForm{
            width: 400px;
            min-height: 500px;
        }


        .main{
            padding: 38px;
        }

        .social-box a{
            font-weight:bold;
            font-size:18px;
            padding:8px;
        }
        .social-box a i{
            font-weight:bold;
            font-size:20px;
        }
        .heading-desc{
            font-size:20px;
            font-weight:bold;
            padding:38px 38px 0px 38px;

        }
        .form-signin .form-signin-heading, .form-signin .checkbox, .form-signup .form-signup-heading, .form-signup .checkbox {
            margin-bottom: 10px;
        }

        .form-signin .checkbox, .form-signup .checkbox {
            font-weight: normal;
        }
        .form-signin .form-control, .form-signup .form-control {
            position: relative;
            font-size: 16px;
            height: 20px;
            padding: 20px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }
        .form-signin .form-control:focus, .form-signup .form-control:focus {
            z-index: 2;
        }
        .form-signin input[type="text"], .form-signup input[type="text"] {
            margin-bottom: 10px;
            border-radius: 5px;

        }
        .form-signin input[type="password"], .form-signup input[type="password"] {
            margin-bottom: 10px;
            border-radius: 5px;
        }
        .login-footer{
            background:#f0f0f0;
            margin: 0 auto;
            border-top: 1px solid #dadada;
            padding:20px;
        }
        .login-footer .left-section a{
            font-weight:bold;
            color:#8a8a8a;
            line-height:19px;
        }
        .mg-btm{
            margin-bottom:20px;
        }
    </style>
</head>
<body>

<form action="#" method="POST"  class="form-signup mg-btm send-for-json-form"
           id="registrationForm" role="form" object-type-id="<%=Constant.USER_OBJ_TYPE_ID%>">
    <h3 class="heading-desc"><spring:message code="general.signUpForm"></spring:message></h3>
    <div class="main">
        <spring:message code="enter.fullname" var="placeholder"/>
        <input type='text' id='fio' attr-id="<%=Constant.FULL_NAME_ATTR_ID%>"  class="form-control" placeholder='${placeholder}'>
        <div class="alert alert-danger col-sm-12 validation-message" id="fio-validation-message">
        </div>

        <spring:message code="enter.username" var="placeholder"/>
        <input type='text' id='login' attr-id="<%=Constant.USERNAME_ATTR_ID%>" name="true"  class="form-control" placeholder='${placeholder}'>
        <div class="alert alert-danger col-sm-12 validation-message" id="login-validation-message">
        </div>

        <spring:message code="enter.email" var="placeholder"/>
        <input type='text' id='email' attr-id="<%=Constant.EMAIL_ATTR_ID%>" class="form-control" placeholder='${placeholder}'>
        <div class="alert alert-danger col-sm-12 validation-message" id="email-validation-message">
        </div>

        <spring:message code="enter.password" var="placeholder"/>
        <input type='password' id='passwordHash' attr-id="<%=Constant.PASSWORD_HASH_ATTR_ID%>" class="form-control" placeholder='${placeholder}'>
        <div class="alert alert-danger col-sm-12 validation-message" id="password-validation-message">
        </div>

        <spring:message code="users.confirmPassword" var="placeholder"/>
        <input type='password' id='confirmPassword' class="form-control" placeholder='${placeholder}'>
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
                <button type="button" class="btn btn-large btn-danger pull-right send-for-json-btn" id="btn-signUp" onclick="submitJson();"><spring:message code="profile.signUp"/></button>
            </div>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>
