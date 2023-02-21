<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 16.01.2023
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<html>
<link rel="stylesheet" href="w3.css"/>
<style type="text/css">
    div form {
        display: inline-block;
    }
</style>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.local" var="loc"/>

<fmt:message bundle="${loc}" key="local.button.delete" var="delete_btn"/>
<fmt:message bundle="${loc}" key="local.button.back" var="back"/>
<fmt:message bundle="${loc}" key="local.button.search" var="search_btn"/>
<fmt:message bundle="${loc}" key="local.button.sort" var="sort_btn"/>
<fmt:message bundle="${loc}" key="local.button.sort_by_name_asc" var="sort_by_name_asc_btn"/>
<fmt:message bundle="${loc}" key="local.button.sort_by_name_des" var="sort_by_name_des_btn"/>


<fmt:message bundle="${loc}" key="local.text.user_list" var="user_list"/>

<fmt:message bundle="${loc}" key="local.form.deleting" var="delete"/>

<fmt:message bundle="${loc}" key="local.message.operation_failed" var="operation_failed"/>
<fmt:message bundle="${loc}" key="local.message.successful_operation" var="successful_operation"/>
<fmt:message bundle="${loc}" key="local.message.you_are_in_the_user_list" var="you_are_in_the_user_list"/>

<head>
    <title>
        <c:out value="${user_list}"/>
    </title>
</head>
<body>

<div class="content">

    <div class="w3-dropdown-hover">
        <button class="w3 button w3-light-grey">
            <c:out value="${sort_btn}"></c:out>
        </button>
        <div class="w3-dropdown-content w3-bar-block w3-border">

            <form id="sortByNameAsc" method="get" action="/userList">
                <input type="hidden" name="command" value="user_List">
                <input type="hidden" name="orderBy" value="users.user_name ASC">
                <button form="sortByNameAsc" type="submit">
                    <c:out value="${sort_by_name_asc_btn}"></c:out>
                </button>
            </form>

            <form id="sortByNameDsc" method="get" action="/userList">
                <input type="hidden" name="command" value="user_List">
                <input type="hidden" name="orderBy" value="users.user_name DESC">\
                <button form="sortByNameDsc" type="submit">
                    <c:out value="${sort_by_name_des_btn}"></c:out>
                </button>
            </form>

        </div>

    </div>

    <div>
        <form class="w3-container w3-light-grey" method="post" action="/userList">
            <input class="w3-input w3-border-0" type="text" class="register-input" name="filter">
            <input type="hidden" name="command" value="user_list">
            <input type="submit" name="Search" value="${search_btn}">
        </form>
    </div>

    <table class="w3-table w3-striped">
        <thread>
            <tr>
                <th>
                    <c:out value="${user_list}"/>
                </th>
                <th>
                    <c:out value="${delete}"/>
                </th>
            </tr>
        </thread>
        <tbody>
        <c:forEach var="user" items="${usersPagedAttribute.elements}">
            <tr>
                <td><c:out value="${user}"/></td>
                    <%--                <td><c:out value="${user.userName}"/></td>--%>
                <td>
                    <form method="get" action="/deleteUser">
                        <input type="hidden" name="userName" value="${user}">
                            <%--                        <input type="hidden" name="userName" value="${user.userName}">--%>
                        <input type="hidden" name="command" value="delete_user">
                        <input type="submit" name="Delete" value="${delete_btn}">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div>
        <c:forEach var="i" begin="1"
                   end="${Math.ceil(usersPagedAttribute.totalElements / usersPagedAttribute.limit)}">
            <c:if test="${i == usersPagedAttribute.pageNumber}">
                <form form="show_users" method="post" action="/userList">
                    <input type="hidden" name="currentPage" value="${i}">
                    <input type="hidden" name="command" value="user_list">
                    <button style="color: red" type="submit" name="currentPage">
                            ${i}
                    </button>
                </form>
            </c:if>

            <c:if test="${i != usersPagedAttribute.pageNumber}">
                <form form="show_users" method="post" action="/userList">
                    <input type="hidden" name="currentPage" value="${i}">
                    <input type="hidden" name="command" value="user_list">
                    <button type="submit" name="currentPage">
                            ${i}
                    </button>
                </form>
            </c:if>

        </c:forEach>
    </div>
</div>

<div class="footer">

    <c:if test="${sessionScope.message == 'Successful operation'}">
        <p><c:out value="${successful_operation}"/></p>
    </c:if>

    <c:if test="${sessionScope.message == 'Operation failed'}">
        <p><c:out value="${operation_failed}"/></p>
    </c:if>

    <c:if test="${sessionScope.message == 'You are in the user list now'}">
        <p><c:out value="${you_are_in_the_user_list}"/></p>
    </c:if>

    <div>
        <button onclick="location.href='..'">
            <c:out value="${back}"/>
        </button>
    </div>

</div>

</body>
</html>
