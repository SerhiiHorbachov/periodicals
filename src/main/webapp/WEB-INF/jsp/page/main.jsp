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
</div>



