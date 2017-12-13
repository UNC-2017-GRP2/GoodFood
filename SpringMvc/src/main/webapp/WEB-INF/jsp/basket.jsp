<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Корзина</title>
</head>
<body>
<div>
    <div><h2>Текущий заказ</h2></div>
    <div>
        <table>
            <c:forEach items="${basketItems}" var="item">
                <tr>
                    <td>${item.productName}</td>
                    <td style="text-align: center">${item.productCost} ${rub}</td>
                </tr>
            </c:forEach>
            <tr>
                <td>Общая стоимость заказа:</td>
                <td style="text-align: center">${totalOrder} ${rub}</td>
            </tr>
        </table>
    </div>
</div>
<div>
    <form action="/checkout" method="get">
        <input type="submit" value="Оформить заказ">
    </form>
    <br>
    <a href="<c:url value='/home'/>" >Перейти в профиль</a>
    <br>
    <a href="<c:url value='/items'/>" >Вернуться к заказу</a>
</div>
</body>
</html>
