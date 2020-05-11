<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">


    <h1>Invoice</h1>

    <div class="row">
        <div class="col-sm-3">Invoice ID:</div>
        <div class="col-sm-9">${requestScope.invoice.id}</div>

    </div>

    <div class="row">
        <div class="col-sm-3">Status:</div>
        <div class="col-sm-9">${requestScope.invoice.status}</div>

    </div>

    <div class="row">
        <div class="col-sm-3">Created:</div>
        <div class="col-sm-9">
            <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium"
                            value="${requestScope.invoice.createdAt}"/>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3">Updated:</div>
        <div class="col-sm-9">
            <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium"
                            value="${requestScope.invoice.updatedAt}"/>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3">Total items:</div>
        <div class="col-sm-9">${requestScope.cart.totalCount}</div>
    </div>

    <div class="row">
        <div class="col-sm-3">Total price:</div>
        <div class="col-sm-9">
            <fmt:formatNumber value="${requestScope.cart.totalCost/100 }" type="currency"
                              currencyCode="USD"/>
        </div>
    </div>

    <h3>User info</h3>

    <div class="row">
        <div class="col-sm-3">User ID:</div>
        <div class="col-sm-9">${requestScope.user.id}</div>
    </div>

    <div class="row">
        <div class="col-sm-3">Name:</div>
        <div class="col-sm-9">${requestScope.user.firstName} ${requestScope.user.lastName}</div>
    </div>

    <div class="row">
        <div class="col-sm-3">Email:</div>
        <div class="col-sm-9">${requestScope.user.email} </div>
    </div>

    <table class="table table-striped">
        <thead class="thead-dark">
        <tr>
            <th>Periodical Id</th>
            <th>Periodical Name</th>
            <th>Price per month</th>
        </tr>
        </thead>

        <c:forEach var="periodical" items="${requestScope.cart.getCartItems()}">
            <tr>
                <td>${periodical.id}</td>
                <td>${periodical.name}</td>
                <td>
                    <fmt:formatNumber value="${periodical.monthlyPrice/100 }" type="currency"
                                      currencyCode="USD"/>
                </td>
            </tr>

        </c:forEach>
    </table>
    <c:if test="${requestScope.invoice.status.toString().equals('IN_PROGRESS')}">
        <div class="row">

            <form <c:url value="${pageContext.request.contextPath}/admin/invoices" var="url"/>

                    action="${url}" class="mr-auto" method="post">
                <input type="hidden" name="command" value="cancel">
                <input type="hidden" name="id" value="${requestScope.invoice.id}">
                <input type="submit" class="btn btn-outline-danger" value="Cancel">
            </form>

            <form
                    <c:url value="${pageContext.request.contextPath}/admin/invoices" var="url"/>

                    action="${url}" class="ml-auto" method="post">
                <input type="hidden" name="command" value="approve">
                <input type="hidden" name="id" value="${requestScope.invoice.id}">
                <input type="submit" class="btn btn-outline-info" value="Approve">
            </form>

        </div>
    </c:if>

</div>