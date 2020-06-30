<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="login_messages"/>

<div class="container">
    <h3>Edit Periodical</h3>
    <hr>
    <a href="${pageContext.request.contextPath}/admin/periodicals">Back to List</a>

    <form action="${pageContext.request.contextPath}/admin/edit-periodical" method="POST" class="needs-validation" novalidate>
        <c:if test="${validationMsg != null}">
            <div class="alert alert-warning">
                    ${validationMsg}
            </div>
        </c:if>

        <input type="hidden" name="command" value="edit">
        <input type="hidden" name="periodicalId" value="${periodical.id}"/>

        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" class="form-control" value="${periodical.name}"
                   required>
        </div>

        <div class="form-group">
            <label for="price">Price, USD:</label>
            <input type="number" min="0.00" step="0.01" id="price" name="price"
                   placeholder="9.99" class="form-control" value="${periodical.monthlyPrice / 100}"
                   required>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <input type="text" id="description" name="description" class="form-control"
                   value="${periodical.description}">
        </div>

        <div class="text-right">
            <button type="submit" class="btn btn-primary">Save</button>
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
