<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h1>Редактирование данных</h1>
    <form:form action="/edit" method="POST" modelAttribute="user" >
    <table>
        <tr>
            <td>Логин:</td>
            <td><form:input type='text' id='login' path="login"></form:input></td>
        </tr>
        <tr>
            <td>ФИО:</td>
            <td><form:input type='text' id='fio' path="fio" ></form:input></td>
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
            <td>Адрес доставки:</td>
            <td><form:input type='text' id='address' path="address"></form:input></td>

        </tr>
        <tr>
            <td>Банковская карта:</td>
            <td><form:input type='text' id='bankCard' path="bankCard"></form:input></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Подтвердить" /></td>
            <td><a href="<c:url value='/home'/>" >Отмена</a></td>
        </tr>
    </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    </form:form>
</c:if>
</body>
</html>
