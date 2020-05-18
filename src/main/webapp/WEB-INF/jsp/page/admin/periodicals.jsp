<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
    <input type="button" value="Add Periodical"
           onclick="window.location.href='/admin/new-periodical'; return false;"
           class="btn btn-info"
    />
    <hr>

    <c:forEach var="periodical" items="${periodicals}">

        <div>
            <div id="periodical${periodical.id}" class="periodical">
                <h2>${fn:toUpperCase(periodical.name) }</h2>

                <div class="price">
                    Price:
                    <fmt:formatNumber value="${periodical.monthlyPrice/100 }" type="currency"
                                      currencyCode="USD"/>
                </div>
                <div class="description">
                    <p>
                            ${periodical.description}
                    </p>
                </div>

                <div class="row">
                    <div>
                        <button class="btn btn-link"><a
                                href="${pageContext.request.contextPath}/admin/edit-periodical?id=<c:out value='${periodical.id}'/>">Edit</a>
                        </button>
                    </div>
                    <span>   </span>
                    <div>
                        <form action="/admin/periodicals" method="post" class="d-inline">
                            <input type="hidden" name="command" value="delete">
                            <input type="hidden" name="periodicalId" value="${periodical.id}">
                            <button type="submit" class="btn btn-link"
                                    onclick="return confirm('Do you really want to delete this periodical?');">
                                Delete
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <hr>
    </c:forEach>

    <div class="text-center">
        <nav aria-label="Page navigation example">
            <ul class="pagination pg-blue justify-content-center">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${activePage == i}">
                            <li class="page-item active">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/admin/periodicals?page=${i}">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/admin/periodicals?page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>
