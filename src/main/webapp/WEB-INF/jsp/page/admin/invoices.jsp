<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
    <h1>List of invoices</h1>

    <c:if test="${requestScope.inProgressInvoices != null }">

        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>InvoiceId</th>
                <th>UserId</th>
                <th>Status</th>
                <th>CreatedAt</th>
                <th>Action</th>
            </tr>
            </thead>

            <c:forEach var="invoice" items="${inProgressInvoices}">
                <tr>
                    <td>${invoice.id}</td>
                    <td>${invoice.userId}</td>
                    <td>${invoice.status}</td>
                    <td>${invoice.createdAt}</td>
                    <td>
                        <c:url value="/admin/invoices/view" var="url">
                            <c:param name="id" value="${invoice.id}"/>
                        </c:url>
                        <a href="${url}" class="btn btn-outline-info">Process</a>
                            <%--                        <a href="<c:url value="/admin/invoices/process?id=${invoice.id}"/>"--%>
                            <%--                           class="btn btn-outline-info">Process</a>--%>
                    </td>

                </tr>

            </c:forEach>
        </table>
    </c:if>

</div>

