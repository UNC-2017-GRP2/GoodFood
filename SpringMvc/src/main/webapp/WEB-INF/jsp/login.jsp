<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<c:if test="${not empty error}"><div>${error}</div></c:if>

<form name='login' action="<c:url value='/login' />" method='POST'>
    <table>
        <tr>
            <td>UserName:</td>
            <td><input type='text' name='username'></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Войти" /></td>
            <td><a href="<c:url value='/registration'/>" >Зарегистрироваться</a></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
</body>
</html>