<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a href="/admin/periodicals" class="navbar-brand">Admin</a>

    <div class="collapse navbar-collapse">

        <ul class="navbar-nav mr-auto">
            <li class="nav-item"><a href="/admin/periodicals" class="nav-link">Periodicals</a></li>
            <li class="nav-item"><a href="/admin/invoices/in_progress" class="nav-link">Invoices</a></li>
<%--            <li class="nav-item"><a href="#" class="nav-link">Users</a></li>--%>
        </ul>

        <ul class="navbar-nav ml-auto">

            <li class="nav-item"><a href="#" class="nav-link">${sessionScope.user.email}</a></li>
            <li class="nav-item"><a href="/logout" class="nav-link">Log out</a></li>

        </ul>
    </div>

</nav>