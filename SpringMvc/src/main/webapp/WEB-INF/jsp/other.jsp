<%@ page import="com.netcracker.config.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>

</head>
<body>
    <c:forEach items="${users}" var="user">
        <p>${user.getParameterById(Constant.FULL_NAME_ATTR_ID)}</p>
        <p>${user.getParameterById(Constant.USERNAME_ATTR_ID)}</p>
        <p>${user.getParameterById(Constant.PHONE_NUMBER_ATTR_ID)}</p>
        <p>${user.getParameterById(Constant.EMAIL_ATTR_ID)}</p>
        <c:forEach items="${user.getListParametersById(Constant.ADDRESS_ATTR_ID)}" var="address">
            <p>${address.latitude}  ${address.longitude} </p>
        </c:forEach>
        <p>Next user</p>
    </c:forEach>
</body>
</html>
