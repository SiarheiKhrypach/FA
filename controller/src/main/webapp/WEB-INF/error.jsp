<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 2/2/2022
  Time: 8:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="content-type" content="text/html charset=utf-8">
<html>
<link rel="stylesheet" href="ws.css"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.text.error" var="error"/>
<fmt:message bundle="${loc}" key="local.text.injection_error" var="injection_error"/>
<fmt:message bundle="${loc}" key="local.button.back" var="back"/>



<head>
    <title>Error</title>
</head>
<body>
<h3>
<c:out value="${error}"/>
</h3>

<div>
<c:if test="${fn:contains(message, 'Attempt of injection!' )}">
    <p><c:out value="${injection_error}"/></p>
</c:if>
</div>

<div>
    <button onclick="location.href='..'">
        <c:out value="${back}"/>
    </button>
</div>


</body>
</html>
