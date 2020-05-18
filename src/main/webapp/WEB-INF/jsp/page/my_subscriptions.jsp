<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="subscriptions_messages"/>

<div class="container align-content-center">
    <div class="text-center">
        <h1 class="text-primary"><fmt:message key="msg.my_subscription"/></h1>
    </div>
    <div class="row d-flex justify-content-center">

        <c:forEach var="periodical" items="${subscriptions}">

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

                        <form action="/my/subscriptions" method="post">
                            <input type="hidden" name="periodicalId" value="${periodical.id}">
                            <input type="hidden" name="command" value="unsubscribe">
                            <input type="hidden" name="currentPage"
                                   value="${requestScope.activePage}">
                            <input type="submit" class="btn btn-outline-danger" value="<fmt:message
                            key="msg.unsubscribe"/>">
                        </form>

                    </div>
                </div>

            </div>

        </c:forEach>
    </div>

</div>