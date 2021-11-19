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
<%--<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">--%>

<head>
    <title>Title</title>
</head>
<body>
Welcome to the Fatty Acids! <br>
<p>
    Please fill out the form below to add your product: <br>
</p>


<form class="w3-container w3-light-grey" method="post" action="<c:url value='/add'/>">

    <label>Product Name:</label>
    <input class="w3-input w3-border-0" type="text" class="register-input" name="productName">

    <label>Omega-3 content, mg:</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaThree">

    <label>Omega-6 content, mg:</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="omegaSix">

    <label>Number of portions (1pt = 28g):</label>
    <input class="w3-input w3-border-0" type="number" class="register-input" name="portions">

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
    <c:forEach var="product" items="${productsInContext.values()}">

    <tr>
        <td><c:out value="${product.productName}"/></td
<%--            Unsolved bug - 2nd line after product.productName always go above the table--%>
        <td><c:out value=""/></td>
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



<%--<td><c:out value="${proportion}"/></td>--%>
<%--    <br>--%>
<%--<td><c:out value="${noValidName}"/></td>--%>

<%--<%--%>
<%--  if (request.getAttribute("proportion") != null)  out.println("Your proportion: " + request.getAttribute("proportion"));--%>
<%--  if (request.getAttribute("noValidName") != null) out.println("" + request.getAttribute("noValidName"));--%>
<%--%>--%>

<p><c:out value="Your proportion: ${proportion}" default="" /></p>
<p><c:out value="${noValidName}" default="" /></p>

</body>
</html>
