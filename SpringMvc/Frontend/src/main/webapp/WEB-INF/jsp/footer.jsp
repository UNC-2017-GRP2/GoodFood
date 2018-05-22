<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/footer-style.css">
</head>
<body>
<div class="footer-section">
    <div class="footer">
        <div class="container">
            <div class="col-md-3 footer-one">
                <h5><spring:message code="general.aboutUs"/></h5>
                <p><spring:message code="general.aboutUsText"/></p>
            </div>
            <div class="col-md-3 footer-two">
            </div>
            <div class="col-md-3 footer-three">
                <h5><spring:message code="general.contacts"/></h5>
                <ul>
                    <li><spring:message code="general.address"/>: ------/------/------/------/</li>
                    <li><spring:message code="general.phone"/>: ------/------/------/------/</li>
                </ul>
            </div>
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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
