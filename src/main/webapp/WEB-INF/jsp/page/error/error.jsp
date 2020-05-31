<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="alert alert-danger" role="alert">
    <h1>Code: ${statusCode}</h1>
    <c:choose>
        <c:when test="${statusCode ==404}">
            Requested resource not found
        </c:when>
        <c:otherwise>
            Try again alter
        </c:otherwise>
    </c:choose>
    Message: ${errorMessage}
</div>