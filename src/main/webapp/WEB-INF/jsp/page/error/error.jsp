<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="alert alert-danger" role="alert">
    <h1>Code: ${statusCode}</h1>
    <c:choose>
        <c:when test="${statusCode ==404}">

        </c:when>
        <c:otherwise>
            Try again alter
        </c:otherwise>
    </c:choose>

</div>

<c:choose>
    <c:when test="${statusCode ==404}">
        <div class="text-center">
            <img src="https://media.giphy.com/media/VwoJkTfZAUBSU/giphy.gif" alt="not found">
        </div>
    </c:when>
    <c:otherwise>
        Try again alter
    </c:otherwise>
</c:choose>