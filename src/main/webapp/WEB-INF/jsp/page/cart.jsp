<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1>My Cart</h1>
<div class="container">

    <c:if test="${sessionScope.cart != null}">

        <div>
            Total items: ${sessionScope.cart.totalCount}
        </div>
        <div>
            Total price: <fmt:formatNumber value="${sessionScope.cart.totalCost/100}"
                                           type="currency" currencyCode="USD"/>
        </div>

        <c:forEach var="periodical" items="${cart.cartItems}">

            <div class="col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><h2>${fn:toUpperCase(periodical.name)}</h2></h5>
                        <div class="price">
                            Price:
                            <fmt:formatNumber value="${periodical.monthlyPrice/100 }"
                                              type="currency"
                                              currencyCode="USD"/>
                        </div>
                        <p class="card-text">${periodical.description}</p>
                        <form action="/my/cart" method="post">
                            <input type="hidden" name="periodicalId" value="${periodical.id}">
                            <input type="hidden" name="command" value="remove">
                            <div class="text-right">
                                <input type="submit" class="btn btn-outline-danger ml-auto"
                                       value="Remove">
                            </div>
                        </form>

                    </div>

                </div>

            </div>

        </c:forEach>

        <div>
            <form action="/my/cart" method="post">
                <input type="hidden" name="command" value="submit-invoice">
                <div class="text-center">
                    <input type="submit" class="btn btn-success ml-auto"
                           value="Pay invoice">
                </div>
            </form>
        </div>
    </c:if>

    <c:if test="${sessionScope.cart == null}">
        <div class="text-center">
            <iframe src="https://giphy.com/embed/JoJGxeheao5mQaSiBK" width="480" height="233"
                    frameBorder="0" class="giphy-embed" allowFullScreen></iframe>
            <p><a href="https://giphy.com/gifs/bored-nothing-much-JoJGxeheao5mQaSiBK"></a></p>
        </div>

    </c:if>

<%--    <c:if test="${requestScope.submit_success != null}">--%>
<%--        <div class="text-center">--%>
<%--                ${submit_success}--%>
<%--        </div>--%>
<%--    </c:if>--%>
</div>