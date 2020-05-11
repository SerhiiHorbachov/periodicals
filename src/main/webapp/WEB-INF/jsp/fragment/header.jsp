<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<h2>Header</h2>--%>
<%--<c:if test="${sessionScope.role != null}">--%>
<%--    Role: ${sessionScope.role}--%>
<%--</c:if>--%>

<%--<c:if test="${sessionScope.role != null}">--%>
<%--    <a href="/logout">Log out</a>--%>
<%--</c:if>--%>

<%--<hr>--%>
<%--<hr>--%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a href="/" class="navbar-brand">Subscriby</a>

    <div class="collapse navbar-collapse">
        <c:if test="${sessionScope.role.equals('ADMIN') }">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item"><a href="/admin/periodicals" class="nav-link">Periodicals</a></li>
                <li class="nav-item"><a href="#" class="nav-link">Invoices</a></li>
                <li class="nav-item"><a href="#" class="nav-link">Users</a></li>
            </ul>
        </c:if>

        <ul class="navbar-nav ml-auto">
            <c:choose>
                <c:when test="${sessionScope.role != null}">
                    <li class="nav-item"><a href="/my/cart" class="nav-link">Cart</a></li>
                    <li class="nav-item"><a href="#" class="nav-link">${sessionScope.user.email}</a></li>
                    <li class="nav-item"><a href="/logout" class="nav-link">Log out</a></li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item"><a href="/login" class="nav-link">Login</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>

</nav>