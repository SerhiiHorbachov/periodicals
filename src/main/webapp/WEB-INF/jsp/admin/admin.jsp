<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 13.04.20
  Time: 00:38
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
<header>
    admin page <br>
    <h3>Welcome</h3>
    ${sessionScope.user.firstName}, hello!<br>
    Your role is: ${sessionScope.user.role}
    <br>
    <a href="controller?command=logout">Logout</a>
    <hr>
</header>

<div id="content">

    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Monthly price</th>
            <th>Action</th>
        </tr>

        <c:forEach var="periodical" items="${periodical_list}">

            <tr>
                <td>${ periodical.id}</td>
                <td>${ periodical.name}</td>
                <td>$${ periodical.monthlyPrice / 100}</td>
                <td><a href="">Update</a>
                    |
                    <a href=""
                       onclick="if(!(confirm('Are you sure you want to delete the periodical?'))) return false">Delete</a>
                </td>

            </tr>

        </c:forEach>

    </table>
</div>

</body>
</html>
