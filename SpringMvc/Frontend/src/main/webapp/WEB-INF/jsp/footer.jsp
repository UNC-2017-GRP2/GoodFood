<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/footer-style.css">
</head>
<body>
<div class="footer-section">
    <%--<div class="footer-top">
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
    </div>--%>
    <div class="footer">
        <div class="container">
            <div class="col-md-3 footer-one">
                <h5><spring:message code="general.aboutUs"/></h5>
                <p><spring:message code="general.aboutUsText"/></p>
            </div>
            <div class="col-md-3 footer-two">
                <%--<h5>Information </h5>
                <ul>
                    <li><a href="maintenance.html">Maintenance Tips</a></li>
                    <li><a href="contact.html">Locations</a></li>
                    <li><a href="about.html">Testimonials</a></li>
                    <li><a href="about.html">Careers</a></li>
                    <li><a href="about.html">Partners</a></li>
                </ul>--%>
            </div>
            <div class="col-md-3 footer-three">
                <h5><spring:message code="general.contacts"/></h5>
                <ul>
                    <li><spring:message code="general.address"/>: ------/------/------/------/</li>
                    <li><spring:message code="general.phone"/>: ------/------/------/------/</li>
                </ul>
                <%--<ul>
                    <li><a href="maintenance.html">Maintenance Tips</a></li>
                    <li><a href="contact.html">Locations</a></li>
                    <li><a href="about.html">Testimonials</a></li>
                    <li><a href="about.html">Careers</a></li>
                    <li><a href="about.html">Partners</a></li>
                </ul>--%>
            </div>
            <%--<div class="col-md-3 footer-four">
                <h5>Information </h5>
                <ul>
                    <li><a href="maintenance.html">Maintenance Tips</a></li>
                    <li><a href="contact.html">Locations</a></li>
                    <li><a href="about.html">Testimonials</a></li>
                    <li><a href="about.html">Careers</a></li>
                    <li><a href="about.html">Partners</a></li>
                </ul>
            </div>--%>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="footer-bottom">
        <div class="container">
            <div class="row">
                <div class="col-sm-6 ">
                    <div class="copyright-text">
                        <p>© 2018 <spring:message code="general.goodFood"/></p>
                    </div>
                </div> <!-- End Col -->
                <%--<div class="col-sm-6 ">
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

                </div>--%> <!-- End Col -->
            </div>
        </div>
    </div>
</div>
</body>
</html>
