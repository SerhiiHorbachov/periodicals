<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11.04.20
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<br>
${errorRegisterPassMessage}
<br>
<div id="container">
    <h3>Register</h3>
    <form action="controller" method="POST">
        <input type="hidden" name="command" value="CREATE_USER"/>

        <table>
            <tbody>
            <tr>
                <td><label>FirstName:</label></td>
                <td><input type="text" name="firstName"></td>
            </tr>
            <tr>
                <td><label>LastName:</label></td>
                <td><input type="text" name="lastName"></td>
            </tr>
            <tr>
                <td><label>Email:</label></td>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <td><label>Password:</label></td>
                <td><input type="password" name="password"></td>
            </tr>
            <br>

            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Register"></td>
            </tr>
            </tbody>
        </table>

    </form>

    <p>
        <a href="controller?command=go_to_login">Back to List</a>
    </p>


</div>

</body>
</html>
