<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 20.11.2021
  Time: 1:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<html>
<link rel="stylesheet" href="w3.css"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.text.registration" var="registration"/>
<fmt:message bundle="${loc}" key="local.form.name" var="name"/>
<fmt:message bundle="${loc}" key="local.form.password" var="password"/>
<fmt:message bundle="${loc}" key="local.button.submit" var="submit"/>
<fmt:message bundle="${loc}" key="local.button.back" var="back"/>
<fmt:message bundle="${loc}" key="local.message.user_exist" var="user_exist"/>
<fmt:message bundle="${loc}" key="local.message.fill_the_form" var="fill_the_form"/>

<head>
    <title>
        <c:out value="${registration}"/>
    </title>
</head>
<body>

<div>
<div>
    <h2>
        <c:out value="${registration}"/>
    </h2>
</div>

    <form method="post" action="<c:url value='/'/>">
        <input type="hidden" name="command" value="register"/>
        <label><c:out value="${name}"/>
            <input type="text" name="name" maxlength="30" required><br/>
        </label>
        <label><c:out value="${password}"/>
            <input type="password" name="pass" maxlength="30" required><br/>
        </label>
        <button type="submit">
            <c:out value="${submit}"/>
        </button>
    </form>

</div>

</body>

<div>
    <button onclick="location.href='..'">
        <c:out value="${back}"/>
    </button>
</div>


<c:if test="${fn:contains(message, 'Please fill out the form' )}">
    <p><c:out value="${fill_the_form}"/></p>
</c:if>

<c:if test="${fn:contains(message, 'User name or/and password are already in use, try one more time' )}">
    <p><c:out value="${user_exist}"/></p>
</c:if>

</html>
