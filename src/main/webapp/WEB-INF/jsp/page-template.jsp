<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/styles.css">
    <title>Title</title>
</head>
<body>
<header>
    <c:choose>
        <c:when test="${sessionScope.role.equals('ADMIN')}">
            <jsp:include page="fragment/admin/header.jsp"/>
        </c:when>

        <c:otherwise>
            <jsp:include page="fragment/header.jsp"/>
        </c:otherwise>
    </c:choose>
</header>

<main>
    <jsp:include page="${currentPage}"/>
</main>

<footer>
    <jsp:include page="fragment/footer.jsp"/>
</footer>
<script src="/static/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
</body>
</html>
