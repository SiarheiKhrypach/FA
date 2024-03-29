<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 20.11.2021
  Time: 1:32
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
<fmt:message bundle="${loc}" key="local.text.login" var="login"/>
<fmt:message bundle="${loc}" key="local.form.name" var="name"/>
<fmt:message bundle="${loc}" key="local.form.password" var="password"/>

<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_btn"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.be" var="be_btn"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_btn"/>
<fmt:message bundle="${loc}" key="local.button.submit" var="submit"/>
<fmt:message bundle="${loc}" key="local.button.back" var="back"/>
<fmt:message bundle="${loc}" key="local.message.fill_the_form" var="fill_the_form"/>
<fmt:message bundle="${loc}" key="local.message.invalid_credentials" var="invalid_credentials"/>

<head>
    <title>
        <c:out value="${login}"/>
    </title>
</head>

<body>

    <div>

        <form action="/login" method="post">
            <input type="hidden" name="command" value="form"/>
            <input type="hidden" name="locale_change" value="true"/>
            <input type="hidden" name="locale" value="en">
            <button type="submit">
                <c:out value="${en_btn}"/>
            </button>
        </form>

        <form action="/login" method="post">
            <input type="hidden" name="command" value="form"/>
            <input type="hidden" name="locale_change" value="true"/>
            <input type="hidden" name="locale" value="be">
            <button type="submit">
                <c:out value="${be_btn}"/>
            </button>
        </form>

        <form action="/login" method="post">
            <input type="hidden" name="command" value="form"/>
            <input type="hidden" name="locale_change" value="true"/>
            <input type="hidden" name="locale" value="ru">
            <button type="submit">
                <c:out value="${ru_btn}"/>
            </button>
        </form>


        <div>
            <h2>
                <c:out value="${login}"/>
            </h2>
        </div>

        <form method="post" action="/main">
<%--        <form method="post" action="<c:url value='/main'/>">--%>
            <input type="hidden" name="command" value="login"/>

        <label><c:out value="${name}"/>
                <input type="text" name="name" maxlength="30" required><br />
            </label>
            <label><c:out value="${password}"/>
                <input type="password" name="pass" maxlength="30" required><br />
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

<div>
<c:if test="${fn:contains(message, 'Please fill out the form')}">
    <c:out value="${fill_the_form}"/>
</c:if>

<c:if test="${fn:contains(message, 'User name or/and password are not valid' )}">
    <c:out value="${invalid_credentials}"/>
</c:if>
</div>

</html>
