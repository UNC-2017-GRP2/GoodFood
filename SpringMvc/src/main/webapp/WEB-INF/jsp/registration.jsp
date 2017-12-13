<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form:form action="/registration" method="POST" modelAttribute="userForm" >
    <table>
        <tr>
            <td>ФИО:</td>
            <td><form:input type='text' id='fio' path="fio"></form:input></td>

        </tr>
        <tr>
            <td>Логин:</td>
            <td><form:input type='text' id='login' path="login"></form:input></td>
        </tr>
        <tr>
            <td>Пароль:</td>
            <td><form:input type='password' id='passwordHash'  path="passwordHash"></form:input></td>
        </tr>
        <tr>
            <td>E-mail:</td>
            <td><form:input type='email' id='email' path="email"></form:input></td>
        </tr>
        <tr>
            <td>Телефон:</td>
            <td><form:input type='text' id='phoneNumber' path="phoneNumber"></form:input></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Зарегистрироваться" /></td>
            <td><a href="<c:url value='/login'/>" >Вход</a></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form:form>
</body>
</html>
