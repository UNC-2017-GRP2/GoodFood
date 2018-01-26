<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Корзина</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/basket-style.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        <%@include file="/resources/js/basket-js.js" %>
    </script>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container">
    <div class="row">
        <div class="well">
            <h1 class="text-center">Current order</h1>
            <div class="list-group">
                <ul>
                    <c:forEach items="${basketItems}" var="item">
                        <li class="list-group-item">
                            <div class="media col-md-2">
                                <figure class="pull-left">
                                    <img class="media-object img-rounded img-responsive" src="${item.productImage}">
                                </figure>
                            </div>
                            <div class="col-md-5">
                                <p><h4 class="list-group-item-heading"> ${item.productName} </h4></p>
                                <%--<p class="list-group-item-text"> ${item.productDescription} </p>--%>
                                <p>
                                <h4> ${item.productCost} ${rub} </h4>
                                </p>
                            </div>
                            <div class="col-md-2 text-center">
                                <div class="info">
                                    <h4>
                                    <div class="number">
                                        <span class="minus" item-id="${item.productId}" item-cost="${item.productCost}">-</span>
                                        <input type="text" class="quantity" id="count" name="count" value="${item.productQuantity}" size="5"/>
                                        <span class="plus" item-id="${item.productId}" item-cost="${item.productCost}">+</span>
                                    </div>
                                    </h4>
                                </div>
                            </div>
                            <div class="col-md-3 text-left">
                                <h4 class="final-items-cost"><span class="final-item-cost-span">${item.productQuantity*item.productCost}</span> ${rub}</h4>
                                <span aria-hidden="true" class="remove-item" item-id="${item.productId}" item-cost="${item.productCost}" item-quantity="${item.productQuantity}">&times;</span>
                            </div>
                        </li>
                    </c:forEach>
                    <li class="list-group-item" style="min-height: 120px!important;">
                        <div class="media col-md-6">
                            <h3>Total order cost: <span class="total-order-cost">${totalOrder}</span> ${rub}</h3>
                            <p>
                            <form action="/checkout" method="get">
                            <c:choose>
                                <c:when test="${basketItems.size() == 0}">
                                    <input type="submit" class="btn btn-primary toOrder" disabled="disabled" value="To order">
                                </c:when>
                                <c:when test="${basketItems.size() != 0}">
                                    <input type="submit" class="btn btn-primary to-order" value="To order">
                                </c:when>
                            </c:choose>
                            </form>
                            </p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
