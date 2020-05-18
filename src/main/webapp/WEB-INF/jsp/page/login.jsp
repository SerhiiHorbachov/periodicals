<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="login_messages"/>

<div class="mt-5">
    <form action="/login" method="post" class="main-form needs-validation" novalidate>
        <c:if test="${authenticationErrorMessage != null}">
            <div class="alert alert-warning">
                    ${authenticationErrorMessage}
            </div>
        </c:if>

        <div class="form-group">
            <label for="email"><fmt:message key="msg.email"/></label>
            <input type="text" id="email" name="email" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="pwd"><fmt:message key="msg.password"/></label>
            <input type="password" id="pwd" name="password" class="form-control" required>
        </div>

        <div class="row">

            <a href="${pageContext.request.contextPath}/register" class="btn btn-light "
               role="button"><fmt:message
                    key="msg.register"/></a>
            <button type="submit" class="btn btn-success ml-auto"><fmt:message
                    key="msg.login"/></button>
        </div>

    </form>
</div>

<script>
    var form = document.querySelector('.needs-validation');
    form.addEventListener('submit', function (event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
        }
    })
</script>
