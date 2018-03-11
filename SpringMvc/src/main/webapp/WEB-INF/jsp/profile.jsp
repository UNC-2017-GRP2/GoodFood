<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><spring:message code="general.profile"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/profile-style.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/jquery-ui/1.9.2/js/jquery-ui-1.9.2.custom.min.js"></script>
    <script type="text/javascript" src="webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>
    <script type="text/javascript" src="webjars/jquery.inputmask/3.1.0/inputmask/jquery.inputmask.js"></script>
    <script type="text/javascript" src="webjars/jquery.inputmask/3.1.0/inputmask/jquery.inputmask.date.extensions.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap-form-helpers/2.3.0/js/bootstrap-formhelpers-phone.js"></script>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>

    <script type="text/javascript">
        <%@include file="/resources/js/profile-js.js" %>
    </script>
    <script type="text/javascript">
        ymaps.ready(getUserAddresses);
        function getUserAddresses() {
            <c:forEach items="${userAddresses}" var="address">
            getAddressByCoordinates(${address.latitude}, ${address.longitude});
            </c:forEach>
        }

    </script>
</head>
<body>

<jsp:include page="navbar.jsp"/>

<div class="blog-section paddingTB60 bg-grey ">
    <div class="container">
        <div class="row text-center">
            <div class="col-md-12">
                <div class="site-heading">
                    <div class="container">
                        <div class="row">
                            <h1 class="text-center"><spring:message code="general.profile"/></h1>
                            <div class="border text-center"></div>
                            <div class="content">
                                <div class="container container-prof text-center">
                                    <div class="avatar-flip">
                                        <img src="https://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg" height="150" width="150">
                                        <img src="http://i1112.photobucket.com/albums/k497/animalsbeingdicks/abd-3-12-2015.gif~original">
                                    </div>
                                    <h2>${user.login}<i class="glyphicon glyphicon-edit" data-toggle="modal"
                                                        data-target="#formEditProfileModal"></i></h2>
                                    <div class="text-center">
                                        <ul class="details text-left" id="user-data-list">
                                            <li><p><span class="glyphicon glyphicon-user one"></span>${user.fio}</p></li>
                                            <li><p><span class="glyphicon glyphicon-earphone one"></span>${user.phoneNumber}</p></li>
                                            <li><p><span class="glyphicon glyphicon-envelope one"></span>${user.email}</p></li>
                                            <fmt:parseDate value="${user.birthday}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                                            <li><p><span class="glyphicon glyphicon-calendar one"></span><fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDate }" /></p></li>
                                            <hr>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>




<div class="modal fade" id="formEditProfileModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"><spring:message code="profile.editProfile"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="resetNewAddress">&times;</span>
                </button>
            </div>
            <div class="well" style="height: auto!important;">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#editProfile" data-toggle="tab"><spring:message code="profile.basicInfo"/></a></li>
                    <li><a href="#editPassword" data-toggle="tab"><spring:message code="users.password"/></a></li>
                    <li><a href="#editAddress" data-toggle="tab"><spring:message code="users.addresses"/></a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane active in" id="editProfile">
                        <form:form action="/edit" method="POST" modelAttribute="userForUpdate" class="form-horizontal"
                                   id="editProfileForm" role="form">
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="login" class="col-xs-4 control-label"><spring:message code="users.username"/>:</label>
                                    <div class="col-xs-6">
                                        <form:input type='text' id='login' path="login"
                                                    class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="login"></form:errors>
                                </div>
                                <div class="form-group">
                                    <label for="fio" class="col-xs-4 control-label"><spring:message code="users.fullname"/>:</label>
                                    <div class="col-xs-6">
                                        <form:input type='text' id='fio' path="fio" class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="fio"></form:errors>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-xs-4 control-label"><spring:message code="users.email"/>:</label>
                                    <div class="col-xs-6">
                                        <form:input type='text' id='email' path="email"
                                                    class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="email"></form:errors>
                                </div>
                                <div class="form-group">
                                    <label for="phoneNumber1" class="col-xs-4 control-label"><spring:message code="users.phoneNumber"/>:</label>
                                    <div class="col-xs-6">
                                        <form:input type='text' id='phoneNumber1' path="phoneNumber"
                                                    class="form-control input-medium bfh-phone" data-format="+7 (ddd) ddd-dddd"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="phoneNumber"></form:errors>
                                </div>

                                <div class="form-group">
                                    <label for="birth" class="col-xs-4 control-label"><spring:message code="users.birthday"/>:</label>
                                    <spring:message code="enter.birthday" var="placeholder"/>
                                    <div class="col-xs-6">
                                        <form:input type='text' id='birth' path="birthday"
                                                    class="form-control" placeholder='${placeholder}'></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="birthday"></form:errors>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary" id="btn-save-user-data"><spring:message code="profile.save"/></button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="general.cancel"/></button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                    <div class="tab-pane fade" id="editPassword">
                        <form:form action="/editPassword" method="POST" class="form-horizontal" id="editPasswordForm"
                                   role="form" modelAttribute="userForUpdate">
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="oldPassword" class="col-xs-4 control-label"><spring:message code="profile.oldPassword"/>:</label>
                                    <div class="col-xs-6">
                                        <input type='password' id='oldPassword' name="oldPassword" class="form-control">
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <label class="validationMessage errorOldPassword"></label>
                                    <c:choose>
                                        <c:when test="${errorOldPassword}">
                                            <spring:message code="errors.oldPassIsntCorrect" var="placeholder"/>
                                            <script>putValueToErrorPasswordLabel('${placeholder}');</script>
                                        </c:when>
                                        <c:otherwise>
                                            <script>putValueToErrorPasswordLabel("");</script>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="form-group">
                                    <label for="passwordHash" class="col-xs-4 control-label"><spring:message code="profile.newPassword"/>:</label>
                                    <div class="col-xs-6">
                                        <form:input type='password' id='passwordHash' path="passwordHash"
                                                    class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="passwordHash"></form:errors>
                                </div>
                                <div class="form-group">
                                    <label for="confirmPassword" class="col-xs-4 control-label"><spring:message code="users.confirmPassword"/>:</label>
                                    <div class="col-xs-6">
                                        <form:input type='password' id='confirmPassword' path="confirmPassword"
                                                    class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="col-xs-offset-4 col-xs-8 validationMessage">
                                    <form:errors path="confirmPassword"></form:errors>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary"><spring:message code="profile.save"/></button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="general.cancel"/></button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                    <div class="tab-pane fade" id="editAddress">
                        <form:form action="/editAddresses" method="GET" class="form-horizontal" id="editAddressForm"
                                   role="form">
                            <div class="modal-body">
                                <div class="list-group">
                                    <ul id="user-addresses-for-edit">
                                        <li class="forNewAddress"></li>
                                    </ul>

                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <spring:message code="enter.address" var="placeholder"/>
                                        <input type='text' id='input-address' class="form-control"
                                               placeholder='${placeholder}'>
                                    </div>
                                    <div class="col-xs-2">
                                        <button type="button" class="btn btn-default" onclick="addAddress();"><spring:message code="profile.addAddress"/></button>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-6">
                                        <label id="addressValid"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary"><spring:message code="profile.save"/></button>
                                <button type="button" class="btn btn-secondary resetNewAddress" data-dismiss="modal"><spring:message code="general.cancel"/></button>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <script>${flag}</script>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script>getUserAddresses();</script>
</body>
</html>
