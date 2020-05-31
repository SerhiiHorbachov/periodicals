<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lang" value="${sessionScope.lang}"/>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="header_messages"/>


<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a href="/" class="navbar-brand">Subscriby</a>

        <div class="collapse navbar-collapse">
            <c:choose>
                <c:when test="${sessionScope.role != null}">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item"><a href="/my/subscriptions"
                                                class="nav-link"><fmt:message
                                key="msg.my_subscriptions"/></a></li>
                    </ul>
                </c:when>
            </c:choose>

            <ul class="navbar-nav ml-auto">
                <li class="nav-item dropdown">
                    <c:choose>
                        <c:when test="${sessionScope.lang == 'ukr'}">
                            <a class="nav-link dropdown-toggle" id="dropdown09"
                               data-toggle="dropdown"
                               aria-haspopup="true" aria-expanded="false"><span
                                    class="flag-icon flag-icon-ua"> </span> UA</a>
                        </c:when>
                        <c:otherwise>
                            <a class="nav-link dropdown-toggle" id="dropdown09"
                               data-toggle="dropdown"
                               aria-haspopup="true" aria-expanded="false"><span
                                    class="flag-icon flag-icon-gb"> </span> EN</a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sessionScope.lang == 'ukr'}">
                            <div class="dropdown-menu" aria-labelledby="dropdown09">
                                <a class="dropdown-item" href="?lang=en"><span
                                        class="flag-icon flag-icon-gb"> </span>EN</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="dropdown-menu" aria-labelledby="dropdown09">
                                <a class="dropdown-item" href="?lang=ukr"><span
                                        class="flag-icon flag-icon-ua"> </span>UA</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </li>

                <c:choose>
                    <c:when test="${sessionScope.role != null}">
                        <li class="nav-item"><a href="/my/cart" class="nav-link"><fmt:message
                                key="msg.cart"/></a></li>
                        <li class="nav-item"><a href="#"
                                                class="nav-link">${sessionScope.user.email}</a>
                        </li>
                        <li class="nav-item"><a href="/logout" class="nav-link"><fmt:message
                                key="msg.logout"/></a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item"><a href="/login" class="nav-link"><fmt:message
                                key="msg.login"/> </a></li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
    </div>
</nav>



