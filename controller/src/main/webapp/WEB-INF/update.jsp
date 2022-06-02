<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 05.11.2021
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<html>

<link rel="stylesheet" href="w3.css"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.button.edit" var="edit_btn"/>
<fmt:message bundle="${loc}" key="local.text.enter_new_data" var="enter_new_data"/>
<fmt:message bundle="${loc}" key="local.form.editing" var="edit"/>
<fmt:message bundle="${loc}" key="local.form.productname" var="product_name"/>
<fmt:message bundle="${loc}" key="local.form.omega3" var="omega3"/>
<fmt:message bundle="${loc}" key="local.form.omega6" var="omega6"/>
<fmt:message bundle="${loc}" key="local.form.portions" var="portions"/>

<head>
    <title>
        <c:out value="${edit}"/>
    </title>
</head>
<body>

<br>
<div>
    <c:out value="${enter_new_data}"/>
</div>
<br>

<div>
<form class="w3-container w3-light-grey" method="post" action="/main">
<%--<form class="w3-container w3-light-grey" method="post" action="<c:url value='/main'/>">--%>
    <input type="hidden" name="command" value="edit"/>
    <input type="hidden" name="productId" value="${product.productId}"/>

    <label>
        <c:out value="${product_name}"/>
    </label>
    <input class="w3-input w3-border-0" type="text" class="register-input" name="productName"
           value="${product.productName}" placeholder="${product.productName}">

    <label>
        <c:out value="${omega3}"/>
    </label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaThree"
           value="${product.omegaThree}" placeholder="${product.omegaThree}" min="0" step="0.1">

    <label>
        <c:out value="${omega6}"/>
    </label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaSix" value="${product.omegaSix}"
           placeholder="${product.omegaSix}" min="0" step="0.1">

    <label>
        <c:out value="${portions}"/>
    </label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="portions" value="${product.portion}"
           placeholder="${product.portion}" min="1">

    <input type="submit" value="${edit_btn}" name="Edit">

</form>
</div>

</body>
</html>
