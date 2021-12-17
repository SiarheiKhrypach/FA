<%@ page import="by.tut.ssmt.repository.entities.Product" %><%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 05.11.2021
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
</head>
<body>

<br>
<div> Enter new data: </div>
<br>
<form class="w3-container w3-light-grey" method="post" action="<c:url value='/update'/>">

    <input type="number" hidden name="id" value="${product.id}" />

    <label>Product Name:</label>
    <input class="w3-input w3-border-0" type="text" class="register-input" name="productName" value="${product.productName}" placeholder="${product.productName}">

    <label>Omega-3 content, mg:</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaThree" value="${product.omegaThree}" placeholder="${product.omegaThree}" min="0">

    <label>Omega-6 content, mg:</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaSix" value="${product.omegaSix}" placeholder="${product.omegaSix}" min="0">

    <label>Number of portions (1pt = 28g):</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="portions" value="${product.portion}" placeholder="${product.portion}" min="1">

    <input type="submit" value="Edit" name="Edit">

</form>

</body>
</html>
