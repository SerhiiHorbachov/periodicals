<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
    <title>Title</title>
</head>
<body>
<header>
    <jsp:include page="fragment/header.jsp"/>
</header>

<main>
    <jsp:include page="${currentPage}"/>
</main>

<footer>
    <jsp:include page="fragment/footer.jsp"/>
</footer>
</body>
</html>
