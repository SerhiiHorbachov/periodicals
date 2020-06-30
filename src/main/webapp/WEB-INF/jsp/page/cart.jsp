<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="cart_messages"/>

<div class="container">
    <h1><fmt:message key="msg.cart"/></h1>

    <c:if test="${sessionScope.cart != null}">

        <div>
            <fmt:message key="msg.total_items"/>: ${sessionScope.cart.totalCount}
        </div>
        <div>
            <fmt:message key="msg.total_cost"/>: <fmt:formatNumber
                value="${sessionScope.cart.totalCost/100}"
                type="currency" currencyCode="USD"/>
        </div>

        <c:forEach var="periodical" items="${cart.cartItems}">

            <div class="col-sm-6">
                <div class="card mt-5 mb-5">
                    <div class="card-body">
                        <h5 class="card-title"><h2>${fn:toUpperCase(periodical.name)}</h2></h5>
                        <div class="price">
                            <fmt:message key="msg.price"/>:
                            <fmt:formatNumber value="${periodical.monthlyPrice/100 }"
                                              type="currency"
                                              currencyCode="USD"/>
                        </div>
                        <p class="card-text">${periodical.description}</p>
                        <form action="${pageContext.request.contextPath}/my/cart" method="post">
                            <input type="hidden" name="periodicalId" value="${periodical.id}">
                            <input type="hidden" name="command" value="remove">
                            <div class="text-right">
                                <input type="submit" class="btn btn-outline-danger ml-auto"
                                       value="<fmt:message key="msg.remove"/>">
                            </div>
                        </form>

                    </div>

                </div>

            </div>

        </c:forEach>

        <div>
            <form action="${pageContext.request.contextPath}/my/cart" method="post">
                <input type="hidden" name="command" value="submit-invoice">
                <div class="text-center">
                    <input type="submit" class="btn btn-success ml-auto"
                           value="<fmt:message key="msg.pay_invoice"/>">
                </div>
            </form>
        </div>
    </c:if>

    <c:if test="${sessionScope.cart == null}">
        <div class="text-center">
            <iframe src="https://giphy.com/embed/JoJGxeheao5mQaSiBK" width="480"
                    height="233"
                    frameBorder="0" class="giphy-embed" allowFullScreen></iframe>
            <p><a href="https://giphy.com/gifs/bored-nothing-much-JoJGxeheao5mQaSiBK"></a>
            </p>
        </div>

    </c:if>

</div>