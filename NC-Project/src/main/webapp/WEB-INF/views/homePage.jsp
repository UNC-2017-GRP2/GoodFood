<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contact Manager Home</title>
    </head>
    <body>
        <div align="center">
            <h1>Objects</h1>
            <table border="1">
                <th>ID</th>
                <th>Username</th>
                <th>Password hash</th>
                <th>Role</th>

                <c:forEach var="object" items="${objectList}">
                <tr>
                    <td>${object.id}</td>
                    <td>${object.username}</td>
                    <td>${object.passHash}</td>
                    <td>${object.role}</td>
                </tr>
                </c:forEach>
            </table>
            <c:url value="/logout" var="logoutUrl" />
            <form id="logout" action="${logoutUrl}" method="post" >
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
            	<a href="javascript:document.getElementById('logout').submit()">Logout</a>
            </c:if>
        </div>
    </body>
</html>