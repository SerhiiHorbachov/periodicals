<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="main_messages"/>

<div class="container align-content-center">
    <div class="row d-flex justify-content-center">
        <c:forEach var="periodical" items="${periodicals}">

            <div class="col-sm-6 text-center mb-5 mt-5">
                <div class="card bg-light">
                    <div class="card-body">
                        <h5 class="card-title"><h2>${fn:toUpperCase(periodical.name)}</h2></h5>
                        <p class="card-text">${periodical.description}</p>

                        <h6 class="card-price">

                            <fmt:formatNumber value="${periodical.monthlyPrice/100}"
                                              type="currency"
                                              currencyCode="USD"/>
                            <span class="term">/<fmt:message key="msg.month"/></span>
                        </h6>

                        <form action="/main" method="post">
                            <input type="hidden" name="periodicalId" value="${periodical.id}">
                            <input type="hidden" name="command" value="add-to-cart">
                            <input type="hidden" name="currentPage"
                                   value="${requestScope.activePage}">
                            <input type="submit" class="btn btn-success" value="<fmt:message
                            key="msg.subscribe"/>">
                        </form>

                    </div>
                    <c:if test="${requestScope.invalidId == periodical.id}">
                        <div><fmt:message key="msg.already_in_cart"/></div>
                    </c:if>

                </div>

            </div>

        </c:forEach>
    </div>

    <div class="text-center">
        <nav aria-label="Page navigation example">
            <ul class="pagination pg-blue justify-content-center">
                <c:forEach begin="1" end="${totalPages}" var="i">

                    <c:choose>
                        <c:when test="${activePage == i}">
                            <li class="page-item active">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/main?page=${i}">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/main?page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </ul>
        </nav>
    </div>

</div>
