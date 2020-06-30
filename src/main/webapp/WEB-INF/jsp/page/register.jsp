<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="registration_messages"/>

<form action="${pageContext.request.contextPath}/register" method="post"
      class="main-form needs-validation" novalidate>

    <div class="alert alert-warning">
        ${emailAlreadyTakenMessage}
        ${invalidPasswordPatternMessage}
        ${invalidEmailPatternMessage}

    </div>

    <div class="form-group">
        <label for="fname"><fmt:message key="msg.first_name"/></label>
        <input type="text" id="fname" name="fname" class="form-control" required>
    </div>

    <div class="form-group">
        <label for="lname"><fmt:message key="msg.last_name"/></label>
        <input type="text" id="lname" name="lname" class="form-control" required>
    </div>

    <div class="form-group">
        <label for="email"><fmt:message key="msg.email"/></label>
        <input type="text" id="email" name="email" class="form-control" required>
    </div>

    <div class="form-group">
        <label for="pwd"><fmt:message key="msg.password"/></label>
        <input type="password" id="pwd" name="password" class="form-control" required>
        <small class="form-text text-muted">
            <fmt:message key="msg.pwd_hint"/>
        </small>
    </div>

    <button type="submit" class="btn btn-outline-success"><fmt:message key="msg.register"/></button>

</form>

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
