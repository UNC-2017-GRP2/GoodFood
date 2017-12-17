<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Заказать</title>
</head>
<body>
<h2>${message} ${username}</h2>
<div>
    <table>
        <c:forEach items="${items}" var="item">
            <form action="/items/${item.productId}" method="post">
                <tr>
                    <td>${item.productName}</td>
                    <td style="text-align: center">${item.productCost} ${rub}</td>
                    <td><input type="submit" value="${add}"></td>
                </tr>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </c:forEach>
    </table>
</div>
<div>
    <a href="<c:url value='/basket'/>" >Перейти в корзину</a>
    <br>
    <a href="<c:url value='/home'/>" >Перейти в профиль</a>
</div>
</body>
</html>
