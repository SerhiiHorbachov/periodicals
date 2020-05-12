<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>User Main Page</h2>

<div class="container">
    <c:forEach var="periodical" items="${periodicals}">

        <div class="col-sm-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title"><h2>${fn:toUpperCase(periodical.name)}</h2></h5>
                    <p class="card-text">${periodical.description}</p>

                    <h6 class="card-price">

                        <fmt:formatNumber value="${periodical.monthlyPrice/100 }"
                                          type="currency"
                                          currencyCode="USD"/>
                        <span class="term">/month</span>
                    </h6>

                    <form action="/main" method="post">
                        <input type="hidden" name="periodicalId" value="${periodical.id}">
                        <input type="hidden" name="command" value="add-to-cart">
                        <input type="submit" class="btn btn-success" value="Add">
                    </form>

                </div>
                <c:if test="${invalidId == periodical.id}">
                    <div>${alreadyInCart}</div>
                </c:if>
            </div>

        </div>

    </c:forEach>

    <div class="text-center">
        <nav aria-label="Page navigation example">
            <ul class="pagination pg-blue justify-content-center">
                <c:forEach begin="1" end="${totalPages}" var="i">

                    <%--                <li class="page-item disabled">--%>
                    <%--                    <a class="page-link" tabindex="-1">Previous</a>--%>
                    <%--                </li>--%>

                    <%--                <li class="page-item"><a class="page-link">1</a></li>--%>

                    <%--                <li class="page-item active">--%>
                    <%--                    <a class="page-link">2 <span class="sr-only">(current)</span></a>--%>
                    <%--                </li>--%>


                    <%--                    <li class="page-item"><a--%>
                    <%--                            href="${pageContext.request.contextPath}/main?page=${i}"--%>
                    <%--                            class="page-link">${i}</a>--%>
                    <%--                    </li>--%>


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


                    <%--                <li class="page-item">--%>
                    <%--                    <a class="page-link">Next</a>--%>
                    <%--                </li>--%>

                </c:forEach>
            </ul>
        </nav>
    </div>


</div>



