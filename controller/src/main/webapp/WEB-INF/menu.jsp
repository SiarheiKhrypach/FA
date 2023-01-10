<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 21.11.2022
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmy" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<fmt:message bundle="${loc}" key="local.button.delete" var="delete_btn"/>
<fmt:message bundle="${loc}" key="local.button.save" var="save_btn"/>
<fmt:message bundle="${loc}" key="local.button.back" var="back"/>

<fmt:message bundle="${loc}" key="local.text.proportion" var="proportion_line"/>
<fmt:message bundle="${loc}" key="local.text.optimum" var="optimum"/>

<fmt:message bundle="${loc}" key="local.form.productname" var="product_name"/>
<fmt:message bundle="${loc}" key="local.form.omega3" var="omega3"/>
<fmt:message bundle="${loc}" key="local.form.omega6" var="omega6"/>
<fmt:message bundle="${loc}" key="local.form.deleting" var="delete"/>
<fmt:message bundle="${loc}" key="local.form.portions" var="portions"/>

<fmt:message bundle="${loc}" key="local.text.menu" var="menu"/>

<fmt:message bundle="${loc}" key="local.message.invalid_data" var="invalid_data"/>

<head>
    <title>
        <c:out value="${menu}"/>
        <%--        text--%>
    </title>
</head>
<body>

<div class="content">

    <table class="w3-table w3-striped">
        <thread>
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
                    <c:out value="${delete}"/>
                </th>
                <th>
                    <c:out value="${portions}"/>
                </th>
            </tr>
        </thread>
        <tbody>
        <c:forEach var="menuItem" items="${menuItemsPagedAttribute.elements}">
            <tr>
                <td><c:out value="${menuItem.productName}"/></td>
                <td><c:out value="${menuItem.omegaThree}"/></td>
                <td><c:out value="${menuItem.omegaSix}"/></td>
                <td>
                    <form method="get" action="/delete">
                        <input type="hidden" name="productName" value="${menuItem.productName}"/>
                        <input type="hidden" name="command" value="deleteFromMenu"/>
                        <input type="submit" name="Delete" value="${delete_btn}">
                    </form>
                </td>

                <td>
                    <form name="portions"  class="w3-container w3-light-grey" method="post" action="/changePortion">
                        <input class="w3-input w3-border-0" type="number" class="register-input" form="portions"
                               name="${menuItem.productName}" value="${menuItem.portions}" min="0" step="1" required>
                        <input type="hidden" name="productId" value="${menuItem.productId}" form="portions" />
                    </form>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>

<form id="portions" method="post" action="/bulkChangePortion">
    <input type="hidden" name="command" value="bulkChangePortion"/>
    <input type="submit" name="BulkChangePortions" value="${save_btn}">
</form>


    <div>
        <c:forEach var="i" begin="1"
                   end="${Math.ceil(menuItemsPagedAttribute.totalElements / menuItemsPagedAttribute.limit)}">
            <c:if test="${i == menuItemsPagedAttribute.pageNumber}">
                <form form="show_menu_items" method="post" action="/menu">
                    <input type="hidden" name="currentPage" value="${i}">
                    <input type="hidden" name="command" value="menu">
                    <button style="color: red" type="submit" name="currentPage">
                            ${i}
                    </button>
                </form>
            </c:if>

            <c:if test="${i != menuItemsPagedAttribute.pageNumber}">
                <form form="show_menu_items" method="post" action="/menu">
                    <input type="hidden" name="currentPage" value="${i}">
                    <input type="hidden" name="command" value="menu">
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


    <c:if test="${requestScope.message == 'Please enter valid data'}">
        <p><c:out value="${invalid_data}"/></p>
    </c:if>

    <div>
        <button onclick="location.href='..'">
            <c:out value="${back}"/>
        </button>
    </div>


</div>


</body>
</html>
