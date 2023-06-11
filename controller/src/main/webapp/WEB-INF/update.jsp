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

<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_btn"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.be" var="be_btn"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_btn"/>
<fmt:message bundle="${loc}" key="local.button.edit" var="edit_btn"/>
<fmt:message bundle="${loc}" key="local.button.back" var="back"/>
<fmt:message bundle="${loc}" key="local.button.logout" var="logout_btn"/>

<fmt:message bundle="${loc}" key="local.text.enter_new_data" var="enter_new_data"/>

<fmt:message bundle="${loc}" key="local.form.editing" var="edit"/>
<fmt:message bundle="${loc}" key="local.form.productname" var="product_name"/>
<fmt:message bundle="${loc}" key="local.form.omega3" var="omega3"/>
<fmt:message bundle="${loc}" key="local.form.omega6" var="omega6"/>
<fmt:message bundle="${loc}" key="local.form.portions" var="portions"/>

<fmt:message bundle="${loc}" key="local.message.you_are_in_the_edit_form" var="you_are_in_the_edit_form"/>

<head>
    <title>
        <c:out value="${edit}"/>
    </title>
</head>
<body>

<div class="content">
<div class="btn-group">

    <form action="/update" method="post">
        <input type="hidden" name="productId" value="${product.productId}"/>
        <input type="hidden" name="command" value="edit_form"/>
        <input type="hidden" name="locale_change" value="true"/>
        <input type="hidden" name="locale" value="en">
        <button type="submit">
            <c:out value="${en_btn}"/>
        </button>
    </form>

    <form action="/update" method="post">
        <input type="hidden" name="productId" value="${product.productId}"/>
        <input type="hidden" name="command" value="edit_form"/>
        <input type="hidden" name="locale_change" value="true"/>
        <input type="hidden" name="locale" value="be">
        <button type="submit">
            <c:out value="${be_btn}"/>
        </button>
    </form>

    <form action="/update" method="post">
        <input type="hidden" name="productId" value="${product.productId}"/>
        <input type="hidden" name="command" value="edit_form"/>
        <input type="hidden" name="locale_change" value="true"/>
        <input type="hidden" name="locale" value="ru">
        <button type="submit">
            <c:out value="${ru_btn}"/>
        </button>
    </form>

</div>

<br>
<div>
    <c:out value="${enter_new_data}"/>
</div>
<br>

<div>
    <form class="w3-container w3-light-grey" method="post" action="/main">
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
        <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaSix"
               value="${product.omegaSix}"
               placeholder="${product.omegaSix}" min="0" step="0.1">

        <%--    <label>--%>
        <%--        <c:out value="${portions}"/>--%>
        <%--    </label>--%>
        <%--    <input class="w3-input w3-border-0" type="number" class="register-input" name="portions" value="${product.portions}"--%>
        <%--           placeholder="${product.portions}" min="1">--%>

        <input type="submit" value="${edit_btn}" name="Edit">

    </form>
</div>

<div class="footer">

    <c:if test="${sessionScope.message == 'You are in the edit form now'}">
        <p><c:out value="${you_are_in_the_edit_form}"></c:out></p>
    </c:if>

    <button onclick="location.href= '..'">
        <c:out value="${back}"/>
    </button>

    <div>
        <form id="logout" method="get" action="/main">
            <input type="hidden" name="command" value="logout"/>
            <button form="logout" type="submit">
                <c:out value="${logout_btn}"/>
            </button>
        </form>
    </div>


</div>
</div>




</body>
</html>
