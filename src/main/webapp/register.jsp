<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ssmt
  Date: 20.11.2021
  Time: 1:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>

<div>
<div>
    <h2>Registration</h2>
</div>

    <form method="post">
        <label>Name:
            <input type="text" name="name"><br/>
        </label>
        <label>Password:
            <input type="password" name="pass"><br/>
        </label>
        <button type="submit">Sumbit</button>
    </form>

</div>

</body>

<div>
    <button onclick="location.href='/'">Back to main</button>
</div>

<p><c:out value="${message}" default="" /></p>

</html>
