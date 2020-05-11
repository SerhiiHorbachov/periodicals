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
            <div id="periodical${periodical.id }" class="periodical">
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

                <div>
                    <a href="/admin/edit-periodical?id=<c:out value='${periodical.id}' />">Edit</a>
                </div>
            </div>
        </div>
        <hr>
    </c:forEach>
</div>