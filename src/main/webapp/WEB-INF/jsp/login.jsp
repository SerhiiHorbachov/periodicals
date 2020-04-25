<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 10.04.20
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div>
    <form name="loginForm" action="controller" method="POST">
        <input type="hidden" name="command" value="login">
        User email: <br>
        <input type="text" name="login" value=""/> <br>
        Enter password: <br>
        <input type="password" name="password" value=""/> <br>

        <br>
        ${errorLoginPassMessage}
        <br>
        ${wrongAction}
        <br>
        ${nullPage}

        <input type="submit" value="Log in"/>

    </form>

</div>
<br>

<a href="controller?command=go_to_register">Register</a>


</body>
</html>


