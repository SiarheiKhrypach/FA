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
<fmt:message bundle="${loc}" key="local.button.back" var="back"/>
<fmt:message bundle="${loc}" key="local.button.delete" var="delete_btn"/>
<fmt:message bundle="${loc}" key="local.button.logout" var="logout_btn"/>
<fmt:message bundle="${loc}" key="local.button.save" var="save_btn"/>
<fmt:message bundle="${loc}" key="local.button.search" var="search_btn"/>
<fmt:message bundle="${loc}" key="local.button.sort" var="sort_btn"/>
<fmt:message bundle="${loc}" key="local.button.sort_by_name_asc" var="sort_by_name_asc_btn"/>
<fmt:message bundle="${loc}" key="local.button.sort_by_name_des" var="sort_by_name_des_btn"/>
<fmt:message bundle="${loc}" key="local.button.sort_by_portions_asc" var="sort_by_portions_asc"/>
<fmt:message bundle="${loc}" key="local.button.sort_by_portions_des" var="sort_by_portions_des"/>


<fmt:message bundle="${loc}" key="local.text.menu" var="menu"/>
<fmt:message bundle="${loc}" key="local.text.proportion" var="proportion_line"/>
<fmt:message bundle="${loc}" key="local.text.optimum" var="optimum"/>

<fmt:message bundle="${loc}" key="local.form.productname" var="product_name"/>
<fmt:message bundle="${loc}" key="local.form.omega3" var="omega3"/>
<fmt:message bundle="${loc}" key="local.form.omega6" var="omega6"/>
<fmt:message bundle="${loc}" key="local.form.deleting" var="delete"/>
<fmt:message bundle="${loc}" key="local.form.portions" var="portions"/>


<fmt:message bundle="${loc}" key="local.message.invalid_data" var="invalid_data"/>
<fmt:message bundle="${loc}" key="local.message.operation_failed" var="operation_failed"/>
<fmt:message bundle="${loc}" key="local.message.successful_operation" var="successful_operation"/>
<fmt:message bundle="${loc}" key="local.message.you_are_in_the_menu" var="you_are_in_the_menu"/>


<head>
    <title>
        <c:out value="${menu}"/>
        <%--        text--%>
    </title>
</head>
<body>

<div class="content">

    <form action="/menu" method="post">
        <input type="hidden" name="command" value="menu"/>
        <input type="hidden" name="locale_change" value="true"/>
        <input type="hidden" name="locale" value="en">
        <button type="submit">
            <c:out value="${en_btn}"/>
        </button>
    </form>

    <form action="/menu" method="post">
        <input type="hidden" name="command" value="menu"/>
        <input type="hidden" name="locale_change" value="true"/>
        <input type="hidden" name="locale" value="be">
        <button type="submit">
            <c:out value="${be_btn}"/>
        </button>
    </form>

    <form action="/menu" method="post">
        <input type="hidden" name="command" value="menu"/>
        <input type="hidden" name="locale_change" value="true"/>
        <input type="hidden" name="locale" value="ru">
        <button type="submit">
            <c:out value="${ru_btn}"/>
        </button>
    </form>

    <div class="w3-dropdown-hover">
        <button class="w3 button w3-light-grey">
            <c:out value="${sort_btn}"></c:out>
        </button>
        <div class="w3-dropdown-content w3-bar-block w3-border">

            <form id="sortByNameAsc" method="get" action="/menu">
                <input type="hidden" name="command" value="menu">
                <input type="hidden" name="orderBy" value="products.product_name ASC">
                <button form="sortByNameAsc" type="submit">
                    <c:out value="${sort_by_name_asc_btn}"></c:out>
                </button>
            </form>

            <form id="sortByNameDesc" method="get" action="/menu">
                <input type="hidden" name="command" value="menu">
                <input type="hidden" name="orderBy" value="products.product_name DESC">
                <button form="sortByNameDesc" type="submit">
                    <c:out value="${sort_by_name_des_btn}"></c:out>
                </button>
            </form>

            <form id="sortByPortionsAsc" method="get" action="/menu">
                <input type="hidden" name="command" value="menu">
                <input type="hidden" name="orderBy" value="menu.portions ASC">
                <button form="sortByPortionsAsc" type="submit">
                    <c:out value="${sort_by_portions_asc}"></c:out>
                </button>
            </form>

            <form id="sortByPortionsDesc" method="get" action="/menu">
                <input type="hidden" name="command" value="menu">
                <input type="hidden" name="orderBy" value="menu.portions DESC">
                <button form="sortByPortionsDesc" type="submit">
                    <c:out value="${sort_by_portions_des}"></c:out>
                </button>
            </form>

        </div>
    </div>

    <div>
        <form class="w3-container w3-light-grey" method="post" action="/menu">
            <input class="w3-input w3-border-0" type="text" class="register-input" name="filter">
            <input type="hidden" name="command" value="menu">
            <input type="submit" name="Search" value="${search_btn}">
    </div>


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
                    <%--Block below prevents bug in the following block which didn't allow to delete first line of forEach sequence--%>
                <form>
                </form>

                <td>
                    <form method="get" action="/delete">
                        <input type="hidden" name="productName" value="${menuItem.productName}"/>
                        <input type="hidden" name="command" value="delete_from_menu"/>
                        <input type="submit" name="Delete" value="${delete_btn}">
                    </form>
                </td>

                <td>
                    <form name="portions" class="w3-container w3-light-grey" method="post" action="/changePortion">
                        <input class="w3-input w3-border-0" type="number" class="register-input" form="portions"
                               name="${menuItem.productName}" value="${menuItem.portions}" min="0" step="1" required>
                        <input type="hidden" name="productId" value="${menuItem.productId}" form="portions"/>
                    </form>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form id="portions" method="post" action="/bulkChangePortion">
        <input type="hidden" name="command" value="bulk_change_portion"/>
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

    <c:if test="${sessionScope.message == 'Delete from menu error'}">
        <p><c:out value="${error_while_deleting}"/></p>
    </c:if>

    <c:if test="${sessionScope.message == 'Successful operation'}">
        <p><c:out value="${successful_operation}"/></p>
    </c:if>

    <c:if test="${sessionScope.message == 'Operation failed'}">
        <p><c:out value="${operation_failed}"/></p>
    </c:if>

    <c:if test="${sessionScope.message == 'You are in the menu now'}">
        <p><c:out value="${you_are_in_the_menu}"/></p>
    </c:if>


    <div>
        <button onclick="location.href='..'">
            <c:out value="${back}"/>
        </button>
    </div>

    <div>
        <form id="logout" method="get" action="/main">
            <input type="hidden" name="command" value="logout"/>
            <button form="logout" type="submit">
                <c:out value="${logout_btn}"/>
            </button>
        </form>
    </div>



</div>


</body>
</html>
