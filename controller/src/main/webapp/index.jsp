<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 29.10.2021
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<html>
<link rel="stylesheet" href="w3.css"/>
<style type="text/css">
    div form {
        display: inline-block
    }
</style>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_btn"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.be" var="be_btn"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_btn"/>
<fmt:message bundle="${loc}" key="local.button.add" var="add_btn"/>
<fmt:message bundle="${loc}" key="local.button.login" var="login_btn"/>
<fmt:message bundle="${loc}" key="local.button.register" var="register_btn"/>

<fmt:message bundle="${loc}" key="local.text.welcome" var="welcome"/>
<fmt:message bundle="${loc}" key="local.text.fill_form" var="form"/>
<fmt:message bundle="${loc}" key="local.text.proportion" var="proportion_line"/>
<fmt:message bundle="${loc}" key="local.text.optimum" var="optimum"/>

<fmt:message bundle="${loc}" key="local.form.productname" var="product_name"/>
<fmt:message bundle="${loc}" key="local.form.omega3" var="omega3"/>
<fmt:message bundle="${loc}" key="local.form.omega6" var="omega6"/>
<fmt:message bundle="${loc}" key="local.form.portions" var="portions"/>
<fmt:message bundle="${loc}" key="local.form.deleting" var="delete"/>
<fmt:message bundle="${loc}" key="local.form.editing" var="edit"/>

<fmt:message bundle="${loc}" key="local.button.delete" var="delete_btn"/>
<fmt:message bundle="${loc}" key="local.button.edit" var="edit_btn"/>
<fmt:message bundle="${loc}" key="local.message.already" var="already_present"/>
<fmt:message bundle="${loc}" key="local.message.invalid" var="invalid_data"/>
<fmt:message bundle="${loc}" key="local.message.hello" var="hello"/>

<head>
    <title>
        <c:out value="${welcome}"/>
    </title>
</head>
<body>

<div class="content">
<div>

    <form action="/main" method="post">
        <input type="hidden" name="command" value="locale"/>
        <input type="hidden" name="locale" value="en">
        <button type="submit">
            <c:out value="${en_btn}"/>
        </button>
    </form>

    <form id="show_products" action="/main" method="post">
        <input type="hidden" name="command" value="locale"/>
        <input type="hidden" name="locale" value="be">
        <button type="submit">
            <c:out value="${be_btn}"/>
        </button>
    </form>

    <form action="/main" method="post">
        <input type="hidden" name="command" value="locale"/>
        <input type="hidden" name="locale" value="ru">
        <button type="submit">
            <c:out value="${ru_btn}"/>
        </button>
    </form>

</div>

<div>
    <div>
        <form id="login" method="get" action="/login">
            <input type="hidden" name="command" value="form"/>
            <button form="login" type="submit">
                <c:out value="${login_btn}"/>
            </button>
        </form>
    </div>

    <div>
        <form id="register" method="get" action="/register">
            <input type="hidden" name="command" value="form"/>
            <button form="register" type="submit">
                <c:out value="${register_btn}"/>
            </button>
        </form>
    </div>

</div>

<p>
    <c:out value="${form}"/>
</p>


<form class="w3-container w3-light-grey" method="post" action="/main">

    <label>
        <c:out value="${product_name}"/>
    </label>
    <input class="w3-input w3-border-0" type="text" class="register-input" name="productName" required>

    <label>
        <c:out value="${omega3}"/>
    </label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaThree" min="0" step="0.1"
           required>

    <label>
        <c:out value="${omega6}"/>
    </label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaSix" min="0" step="0.1"
           required>

    <label>
        <c:out value="${portions}"/>
    </label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="portions" min="1" required>

    <input type="hidden" name="command" value="add"/>
    <input type="submit" name="Add" value="${add_btn}">

</form>


<table class="w3-table w3-striped">
    <thead>
    <tr>
        <th>
            <c:out value="${product_name}"/>
        </th>
        <th>
            <c:out value="${omega3}"/>
        </th>
        <th>
            <c:out value="${omega6}"/>
        </th>
        <th>
            <c:out value="${portions}"/>
        </th>
        <th>
            <c:out value="${delete}"/>
        </th>
        <th>
            <c:out value="${edit}"/>
        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productsPagedAttribute.elements}">

        <tr>
            <td><c:out value="${product.productName}"/></td>
            <td><c:out value="${product.omegaThree}"/></td>
            <td><c:out value="${product.omegaSix}"/></td>
            <td><c:out value="${product.portion}"/></td>

            <td>
                <form method="get" action="/main">
                    <input type="hidden" name="productName" value="${product.productName}"/>
                    <input type="hidden" name="command" value="delete"/>
                    <input type="submit" name="Delete" value="${delete_btn}"/>
                </form>
            </td>

            <td>
                <form method="get" action="/update">
                    <input type="hidden" name="productId" value="${product.productId}"/>
                    <input type="hidden" name="command" value="editform"/>
                    <input type="submit" name="Edit" value="${edit_btn}"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div>
    <c:forEach var="i" begin="1"
               end="${Math.ceil(productsPagedAttribute.totalElements / productsPagedAttribute.limit)}">
        <c:if test="${i == productsPagedAttribute.pageNumber}">
            <form form="show_products">
                <input type="hidden" name="currentPage" value="${i}">
                <button style="color:red" type="submit" name="currentPage">
                        ${i}
                </button>
            </form>
        </c:if>
        <c:if test="${i != productsPagedAttribute.pageNumber}">
            <form form="show_products">
                <input type="hidden" name="currentPage" value="${i}">
                <button type="submit" name="currentPage">
                        ${i}
                </button>
            </form>
        </c:if>
    </c:forEach>
</div>
</div>

<div class="footer">
<p><c:out value="${proportion_line} ${proportion}" default=""/></p>
<c:out value="${optimum}"/>

<c:if test="${fn:contains(message, 'The list already has product with such name' )}">
    <p><c:out value="${already_present}"/></p>
</c:if>


<c:if test="${fn:contains(message, 'Please enter valid data' )}">
    <p><c:out value="${invalid_data}"/></p>
</c:if>

<c:if test="${fn:contains(message, 'Welcome, ' )}">
    <p><c:out value="${hello}${name}"/></p>
</c:if>

<p><%=request.getServletContext().getAttribute("productsAttribute")%>
</p>
</div>

</body>
</html>