<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Project</title>
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

<div>
    <table class="menu">
        <tr>
            <td class="menu-td">
                <div class="card">
                    <a href="<c:url value='/home?value=Pizza'/>">
                        <img class="card-img-top" src="/resources/img/category/pizza_main.png">
                        <div class="card-block">
                            <h5 class="text-bold">Pizza</h5>
                        </div>
                    </a>
                </div>
            </td>
            <td class="menu-td">
                <div class="card">
                    <a href="<c:url value='/home?value=Sushi'/>">
                        <img class="card-img-top" src="/resources/img/category/sushi_main.png">
                        <div class="card-block">
                            <h5 class="text-bold">Sushi</h5>
                        </div>
                    </a>
                </div>
            </td>
            <td class="menu-td">
                <div class="card">
                    <a href="<c:url value='/home?value=Burgers'/>">
                        <img class="card-img-top" src="/resources/img/category/burger_main.png">
                        <div class="card-block">
                            <h5 class="text-bold">Burgers</h5>
                        </div>
                    </a>
                </div>
            </td>
            <td class="menu-td">
                <div class="card">
                    <a href="<c:url value='/home?value=Salads'/>">
                        <img class="card-img-top" src="/resources/img/category/salad_main.jpg">
                        <div class="card-block">
                            <h5 class="text-bold">Salads</h5>
                        </div>
                    </a>
                </div>
            </td>
            <td class="menu-td">
                <div class="card">
                    <a href="<c:url value='/home?value=Snacks'/>">
                        <img class="card-img-top" src="/resources/img/category/snack_main.png">
                        <div class="card-block">
                            <h5 class="text-bold">Snacks</h5>
                        </div>
                    </a>
                </div>
            </td>
            <td class="menu-td">
                <div class="card">
                    <a href="<c:url value='/home?value=Dessert'/>">
                        <img class="card-img-top" src="/resources/img/category/dessert_main.png">
                        <div class="card-block">
                            <h5 class="text-bold">Dessert</h5>
                        </div>
                    </a>
                </div>
            </td>
            <td class="menu-td">
                <div class="card">
                    <a href="<c:url value='/home?value=Beverages'/>">
                        <img class="card-img-top" src="/resources/img/category/beverage_main.png">
                        <div class="card-block">
                            <h5 class="text-bold">Beverages</h5>
                        </div>
                    </a>
                </div>
            </td>
        </tr>
    </table>
</div>

<div style="text-align: center;">
    <p><h1>${value}</h1></p>
</div>

<div class="content">
    <c:forEach items="${items}" var="item">
        <div class="block">
            <form action="/items/${item.productId}" method="post">
                <div class="top">
                    <ul>
                        <li><a href="#"><i class="fa fa-star-o" aria-hidden="true"></i></a></li>
                        <li><span class="converse">${item.productName}</span></li>
                    </ul>
                </div>

                <div class="middle">
                    <img src="${item.productImage}"/>
                </div>

                <div class="bottom">
                    <div class="heading">${item.productDescription}</div>
                    <div class="price">${item.productCost} ${rub}</div>
                    <div class="info">Quantity</div>
                    <div class="style">
                        <button type="submit" class="btn btn-primary">${add}</button>
                    </div>
                </div>
            </form>
        </div>
    </c:forEach>
</div>


</body>

</html>
