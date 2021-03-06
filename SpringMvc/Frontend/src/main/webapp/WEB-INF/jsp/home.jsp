<%@ page import="com.netcracker.config.Constant" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title><spring:message code="general.projectName"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home-style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/grey-button-style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home-js.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/notify.js"></script>
    <script type="text/javascript">
        if ('${pageContext.response.locale}' == 'uk') {
            <%@include file="/resources/js/strings-uk.js" %>
        }
        if ('${pageContext.response.locale}' == 'ru') {
            <%@include file="/resources/js/strings-ru.js" %>
        }
        if ('${pageContext.response.locale}' == 'en') {
            <%@include file="/resources/js/strings-en.js" %>
        }
    </script>
</head>
<body>
<c:url value="/logout" var="logoutUrl"/>
<form id="logout" action="${logoutUrl}" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<jsp:include page="navbar.jsp"/>
<div class= "banner-sektion banner-overlay ">
    <div class="container text-center">
        <div class="col-md-12">
            <div class="banner-heading ">
                <h2><spring:message code="general.firstHeader"/></h2>
                <P><spring:message code="general.secondHeader"/></P>
            </div>
        </div>
    </div>
</div>
<div class="blog-section paddingTB60 bg-grey ">
    <div class="container">
        <div class="row text-center">
            <div class="col-md-12">
                <div class="site-heading ">
                    <nav class="top-menu">
                        <ul class="menu-main">
                            <li><a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_PIZZA%>"><spring:message code="item.category.${Constant.CATEGORY_PIZZA}"/></a></li>
                            <li><a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_SUSHI%>"><spring:message code="item.category.${Constant.CATEGORY_SUSHI}"/></a></li>
                            <li><a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_BURGERS%>"><spring:message code="item.category.${Constant.CATEGORY_BURGERS}"/></a></li>
                            <li><a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_SALADS%>"><spring:message code="item.category.${Constant.CATEGORY_SALADS}"/></a></li>
                            <li><a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_SNACKS%>"><spring:message code="item.category.${Constant.CATEGORY_SNACKS}"/></a></li>
                            <li><a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_DESSERT%>"><spring:message code="item.category.${Constant.CATEGORY_DESSERT}"/></a></li>
                            <li><a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_BEVERAGES%>"><spring:message code="item.category.${Constant.CATEGORY_BEVERAGES}"/></a></li>
                        </ul>
                    </nav>
                    <%--<h3>Popular Food This Month In New Delhi</h3>--%>
                </div>
            </div>
        </div>
        <div style="text-align: center;">
            <p><h1><spring:message code="item.category.${value}"/></h1></p>
            <div class="border text-center"></div>
        </div>
        <c:if test="${items != null}">
            <div class="content">
                <c:forEach items="${items}" var="item">
                    <div class="block">
                        <%--<form action="/addBasket?id=${item.productId}" method="post">--%>
                            <div class="top">
                                <strong><span class="converse item-name" onclick="openDetails('<c:out value="${item.productName}"/>','<c:out value="${pageContext.request.contextPath}${item.productImage}"/>','<c:out value="${item.productDescription}"/>','<c:out value="${item.productCost}"/>','<c:out value="₽"/>');">${item.productName}</span></strong>
                            </div>
                            <div class="middle" >
                                <img src="${pageContext.request.contextPath}${item.productImage}" class="item-img" onclick="openDetails('<c:out value="${item.productName}"/>','<c:out value="${pageContext.request.contextPath}${item.productImage}"/>','<c:out value="${item.productDescription}"/>','<c:out value="${item.productCost}"/>','<c:out value="₽"/>');"/>
                            </div>
                            <div class="bottom">
                                <strong>
                                    <div class="price">${item.productCost} ₽</div>
                                </strong>
                                <div class="info">
                                    <div class="number">
                                        <span class="minus">-</span>
                                        <input type="text" class="quantity" id="count-${item.productId}" value="1" size="5"/>
                                        <span class="plus">+</span>
                                    </div>
                                </div>

                                <div class="style">
                                    <c:choose>
                                        <c:when test="${pageContext.request.userPrincipal.name != null}">
                                            <button type="button" onclick="addToCart('${item.productId}', 'count-${item.productId}');" class="btn btn-primary">
                                                    <span class="glyphicon glyphicon-shopping-cart"></span>
                                                <spring:message code="general.addToCart"/></button>
                                        </c:when>
                                        <c:when test="${pageContext.request.userPrincipal.name == null}">
                                            <button type="submit" disabled="disabled" class="btn btn-primary">
                                                <span class="glyphicon glyphicon-shopping-cart"></span>
                                                <spring:message code="general.addToCart"/></button>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <%--</form>--%>
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
        <%--<div class="row ">
            <div class="col-sm-6 col-md-4">
                <div class="blog-box">
                    <div class="blog-box-image">
                        <img src="https://images.pexels.com/photos/128817/pexels-photo-128817.jpeg?w=940&h=650&auto=compress&cs=tinysrgb" class="img-responsive" alt="">
                    </div>
                    <div class="blog-box-content paddingTLR35">
                        <h4>quis porta tellus dictum</h4>
                        <p>Phasellus lorem enim, luctus ut velit eget, convallis egestas eros. </p>


                    </div>
                </div>
            </div> <!-- End Col -->
            <div class="col-sm-6 col-md-4">
                <div class="blog-box">
                    <div class="blog-box-image">
                        <img src="https://images.pexels.com/photos/160846/french-bulldog-summer-smile-joy-160846.jpeg?w=940&h=650&auto=compress&cs=tinysrgb" class="img-responsive" alt="">
                    </div>
                    <div class="blog-box-content paddingTLR35">
                        <h4>quis porta tellus dictum</h4>
                        <p>Phasellus lorem enim, luctus ut velit eget, convallis egestas eros. </p>


                    </div>
                </div>
            </div> <!-- End Col -->
            <div class="col-sm-6 col-md-4">
                <div class="blog-box">
                    <div class="blog-box-image">
                        <img src="https://images.pexels.com/photos/128817/pexels-photo-128817.jpeg?w=940&h=650&auto=compress&cs=tinysrgb" class="img-responsive" alt="">
                    </div>
                    <div class="blog-box-content paddingTLR35">
                        <h4>quis porta tellus dictum</h4>
                        <p>Phasellus lorem enim, luctus ut velit eget, convallis egestas eros. </p>
                    </div>
                </div>
            </div> <!-- End Col -->


        </div>--%>
    </div>
</div>
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <c:if test="${bottle == 1}">
        <a href="${pageContext.request.contextPath}/home?value=<%=Constant.CATEGORY_ALCOHOL%>">
            <img class="btl" src="${pageContext.request.contextPath}/resources/img/beverages/bottle.png" height="30%" width="auto">
        </a>
    </c:if>
    <c:if test="${car == 1}">
        <a href="/drunk"><img class="car" src="${pageContext.request.contextPath}/resources/img/alcohol/Car-icon.png" height="30%" width="auto" class="menu_links"></a>
    </c:if>
</c:if>

<%--<div class="Features-section paddingTB60 bg-dgrey ">
    <div class="container">
        <div class="row text-center ">
            <div class="col-sm-6 col-md-4">
                <div class="feature-box">
                    <div class="tag-container">
                        <div class="tag-inner">
                            <h1>Акция</h1>
                            <ul>
                                <li>Скидка 5%</li>
                                <li>На всю пиццу</li>
                                <li>С 20 по 30 мая</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div> <!-- End Col -->
            <div class="col-sm-6 col-md-4">
                <div class="feature-box">
                    <div class="tag-container">
                        <div class="tag-inner">
                            <h1>Акция</h1>
                            <ul>
                                <li>Скидка 10%</li>
                                <li>На десерты</li>
                                <li>Сегодня до 23:59</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div> <!-- End Col -->
            <div class="col-sm-6 col-md-4">
                <div class="feature-box">
                    <div class="tag-container">
                        <div class="tag-inner">
                            <h1>Акция</h1>
                            <ul>
                                <li>Скидка 15%</li>
                                <li>На бургеры от 450 ₽</li>
                                <li>С 15 по 31 марта</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div> <!-- End Col -->
        </div>
    </div>
</div>--%>
<%--<div class="footer-section">
    &lt;%&ndash;<div class="footer-top">
        <div class="container">
            <div class="row text-center">
                <div class="col-md-12">
                    <h2>Learn almost anything  </h2>
                </div>
                <div class="col-md-12">
                    Stay in the loop on all upcoming promotions, discounts and latest updates
                </div>
            </div>
        </div>
    </div>&ndash;%&gt;
    <div class="footer">
        <div class="container">
            <div class="col-md-3 footer-one">
                <h5>About Us </h5>
                <p>Cras sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.</p>
            </div>
            <div class="col-md-3 footer-two">
                &lt;%&ndash;<h5>Information </h5>
                <ul>
                    <li><a href="maintenance.html">Maintenance Tips</a></li>
                    <li><a href="contact.html">Locations</a></li>
                    <li><a href="about.html">Testimonials</a></li>
                    <li><a href="about.html">Careers</a></li>
                    <li><a href="about.html">Partners</a></li>
                </ul>&ndash;%&gt;
            </div>
            <div class="col-md-3 footer-three">
                <h5>Contacts </h5>
                <ul>
                    <li>Address: ------/-------/-----/----</li>
                    <li>Phone: ----/-----/-----/-----/-----</li>
                </ul>
                &lt;%&ndash;<ul>
                    <li><a href="maintenance.html">Maintenance Tips</a></li>
                    <li><a href="contact.html">Locations</a></li>
                    <li><a href="about.html">Testimonials</a></li>
                    <li><a href="about.html">Careers</a></li>
                    <li><a href="about.html">Partners</a></li>
                </ul>&ndash;%&gt;
            </div>
            &lt;%&ndash;<div class="col-md-3 footer-four">
                <h5>Information </h5>
                <ul>
                    <li><a href="maintenance.html">Maintenance Tips</a></li>
                    <li><a href="contact.html">Locations</a></li>
                    <li><a href="about.html">Testimonials</a></li>
                    <li><a href="about.html">Careers</a></li>
                    <li><a href="about.html">Partners</a></li>
                </ul>
            </div>&ndash;%&gt;
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="footer-bottom">
        <div class="container">
            <div class="row">
                <div class="col-sm-6 ">
                    <div class="copyright-text">
                        <p>CopyRight © 2017 Netcracker</p>
                    </div>
                </div> <!-- End Col -->
                &lt;%&ndash;<div class="col-sm-6 ">
                    <div class="bigpixi-footer-social pull-right">
                        <ul>
                            <li class="bigpixi-footer-social__list-item">
                                <a class="bigpixi-footer__icon-link" rel="nofollow" href="https://twitter.com/">
                                    <img class="bigpixi-footer-social__icon" alt="Twitter" title="Twitter" src="http://simpleicons.org/icons/twitter.svg">
                                </a>          </li>
                            <li class="bigpixi-footer-social__list-item">
                                <a class="bigpixi-footer__icon-link" rel="nofollow" href="https://www.facebook.com/">
                                    <img class="bigpixi-footer-social__icon" alt="Facebook" title="Facebook" src="http://simpleicons.org/icons/facebook.svg">
                                </a>          </li>
                            <li class="bigpixi-footer-social__list-item">
                                <a class="bigpixi-footer__icon-link" rel="nofollow" href="https://www.youtube.com/">
                                    <img class="bigpixi-footer-social__icon" alt="YouTube" title="YouTube" src="http://simpleicons.org/icons/youtube.svg">
                                </a>          </li>
                            <li class="bigpixi-footer-social__list-item">
                                <a class="bigpixi-footer__icon-link" rel="nofollow" href="https://www.instagram.com/">
                                    <img class="bigpixi-footer-social__icon" alt="Instagram" title="Instagram" src="http://simpleicons.org/icons/instagram.svg">
                                </a>          </li>
                            <li class="bigpixi-footer-social__list-item">
                                <a class="bigpixi-footer__icon-link" rel="nofollow" href="https://www.pinterest.com/">
                                    <img class="bigpixi-footer-social__icon" alt="Pinterest" title="Pinterest" src="http://simpleicons.org/icons/pinterest.svg">
                                </a>          </li>
                        </ul>
                    </div>

                </div>&ndash;%&gt; <!-- End Col -->
            </div>
        </div>
    </div>
</div>--%>
<jsp:include page="footer.jsp"/>
</body>
</html>
