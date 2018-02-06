<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="general.projectName"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/home-style.css">
    <script type="text/javascript" src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        <%@include file="/resources/js/home-js.js" %>
    </script>
</head>


<body>

<jsp:include page="navbar.jsp"/>

<%--<c:if test="${notification != null}">
    <script>successNotification();</script>
</c:if>
<div class="alert alert-success" role="alert">
    <strong>Well done!</strong> You successfully read this important alert message.
</div>--%>


<nav class="top-menu">
    <ul class="menu-main">
        <li><a href="<c:url value='/home?value=Pizza'/>">Pizza</a></li>
        <li><a href="<c:url value='/home?value=Sushi'/>">Sushi</a></li>
        <li><a href="<c:url value='/home?value=Burgers'/>">Burgers</a></li>
        <li><a href="<c:url value='/home?value=Salads'/>">Salads</a></li>
        <li><a href="<c:url value='/home?value=Snacks'/>">Snacks</a></li>
        <li><a href="<c:url value='/home?value=Dessert'/>">Dessert</a></li>
        <li><a href="<c:url value='/home?value=Beverages'/>">Beverages</a></li>
    </ul>
</nav>


<div style="text-align: center;">
    <p>
    <h1>${value}</h1></p>
</div>

<c:if test="${items != null}">
    <div class="content">
        <c:forEach items="${items}" var="item">
            <div class="block">
                <form action="/addBasket?id=${item.productId}" method="post">
                    <div class="top">
                        <strong><span class="converse item-name" onclick="openDetails('<c:out value="${item.productName}"/>','<c:out value="${item.productImage}"/>','<c:out value="${item.productDescription}"/>','<c:out value="${item.productCost}"/>','<c:out value="₽"/>');">${item.productName}</span></strong>
                    </div>
                    <div class="middle" >
                        <img src="${item.productImage}" class="item-img" onclick="openDetails('<c:out value="${item.productName}"/>','<c:out value="${item.productImage}"/>','<c:out value="${item.productDescription}"/>','<c:out value="${item.productCost}"/>','<c:out value="₽"/>');"/>
                    </div>
                    <div class="bottom">
                        <strong>
                            <div class="price">${item.productCost} ₽</div>
                        </strong>
                        <div class="info">
                            <div class="number">
                                <span class="minus">-</span>
                                <input type="text" class="quantity" id="count" name="count" value="1" size="5"/>
                                <span class="plus">+</span>
                            </div>
                        </div>

                        <div class="style">
                            <spring:message code="general.addToCart" var="add"/>
                            <c:choose>
                                <c:when test="${pageContext.request.userPrincipal.name != null}">
                                    <button type="submit" class="btn btn-primary">${add}</button>
                                </c:when>
                                <c:when test="${pageContext.request.userPrincipal.name == null}">
                                    <button type="submit" disabled="disabled" class="btn btn-primary">${add}</button>
                                </c:when>
                            </c:choose>

                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </c:forEach>
    </div>
</c:if>
<div class="modal fade" id="itemDetails" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 20vh;">
    <div class="block" style="margin: auto;">
        <div class="top">
            <ul>
                <li><a href="#"><i class="fa fa-star-o" aria-hidden="true"></i></a></li>
                <li id="itemName"><big><span class="converse"></span></big></li>
            </ul>
        </div>
        <div class="middle">
            <img id="itemImage" src=""/>
            <div class="heading style" style="margin-top: 15%;" id="itemDescription"></div>
        </div>
        <div class="bottom">
            <strong><big>
                <div class="price" id="itemCost"></div>
            </big></strong>
        </div>
    </div>
</div>
</body>

</html>
