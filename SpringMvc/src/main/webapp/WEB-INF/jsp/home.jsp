<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Home</title>
</head>
<body>

<c:url value="/logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h1>Добро пожаловать!</h1>
    <a href="javascript:document.getElementById('logout').submit()">Logout</a>
    <table>
        <tr>
            <td>Логин:</td>
            <td>${user.login}</td>
        </tr>
        <tr>
            <td>ФИО:</td>
            <c:choose>
                <c:when test="${user.fio != null}">
                    <td>${user.fio}</td>
                </c:when>
                <c:when test="${user.fio == null}">
                    <td>${nullParameter}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>E-mail:</td>
            <c:choose>
                <c:when test="${user.email != null}">
                    <td>${user.email}</td>
                </c:when>
                <c:when test="${user.email == null}">
                    <td>${nullParameter}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Телефон:</td>
            <c:choose>
                <c:when test="${user.phoneNumber != null}">
                    <td>${user.phoneNumber}</td>
                </c:when>
                <c:when test="${user.phoneNumber == null}">
                    <td>${nullParameter}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Дата рождения:</td>
            <c:choose>
                <c:when test="${user.birthday != null}">
                    <td>${user.birthday}</td>
                </c:when>
                <c:when test="${user.birthday == null}">
                    <td>${nullParameter}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Адрес доставки:</td>
            <c:choose>
                <c:when test="${user.address != null}">
                    <td>${user.address}</td>
                </c:when>
                <c:when test="${user.address == null}">
                    <td>${nullParameter}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Банковская карта:</td>
            <c:choose>
                <c:when test="${user.bankCard != null}">
                    <td>${user.bankCard}</td>
                </c:when>
                <c:when test="${user.bankCard == null}">
                    <td>${nullParameter}</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td><a href="<c:url value='/edit'/>" >Редактировать профиль</a></td>
        </tr>
        <tr>
            <td><a href="<c:url value='/items'/>" >Сделать заказ</a></td>
        </tr>
        <tr>
            <td>
                <a href="<c:url value='/basket'/>" >Перейти в корзину</a>
            </td>
        </tr>
    </table>
</c:if>

</body>
</html>
