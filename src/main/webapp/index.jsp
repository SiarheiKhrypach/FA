<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 29.10.2021
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="w3.css">

<head>
    <title>Title</title>
</head>
<body>
Welcome to the Fatty Acids! <br>

<div>
<button onclick="location.href='/login'">Log in</button>
<button onclick="location.href='/register'">Register</button>
</div>

<p>
    Please fill out the form below to add your product: <br>
</p>


<form class="w3-container w3-light-grey" method="post" action="<c:url value='/add'/>">

    <label>Product Name:</label>
    <input class="w3-input w3-border-0" type="text" class="register-input" name="productName">

    <label>Omega-3 content, mg:</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaThree" min="0" step="0.1">

    <label>Omega-6 content, mg:</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaSix" min="0" step="0.1">

    <label>Number of portions (1pt = 28g):</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="portions" min="1">

    <input type="submit" value="Add" name="Add">

</form>


<table class="w3-table w3-striped">
    <thead>
    <tr>
        <th>Product name</th>
        <th>Omega-3 content, mg</th>
        <th>Omega-6 content, mg</th>
        <th>Number of portions (1pt = 28g)</th>
        <th>Delete</th>
        <th>Edit</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productsAttribute}">

    <tr>
        <td><c:out value="${product.productName}"/></td>
        <td><c:out value="${product.omegaThree}"/></td>
        <td><c:out value="${product.omegaSix}"/></td>
        <td><c:out value="${product.portion}"/></td>

                    <td>
                        <form method="post" action="<c:url value='/delete'/>">
                            <input type="number" hidden name="id" value="${product.id}" />
                            <input type="submit" name="Delete" value="Delete"/>
                        </form>
                    </td>

                    <td>
                        <form method="get" action="<c:url value='/update'/>">
                            <input type="number" hidden name="id" value="${product.id}" />
                            <input type="submit" name="Edit" value="Edit"/>
                        </form>
                    </td>

    </tr>
    </c:forEach>
    </tbody>
</table>

<p><c:out value="Your proportion: ${proportion}" default=""/></p>
Optimum range is 2-5.
<p><c:out value="${message}${name}" default="" /></p>

</body>
</html>
