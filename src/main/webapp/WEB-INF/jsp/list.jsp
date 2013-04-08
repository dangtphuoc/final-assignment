<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <script type="text/javascript" src="<c:url value="/static/js/contact.home.js" />" ></script>
    <title></title>
</head>
<body>
<h1><spring:message code="contact.list.title"/></h1>
<div>
    <a href="/contact/add" class="btn btn-primary"><spring:message code="contact.label.add.link"/></a>
    <div id="contact-list">
        <c:choose>
            <c:when test="${empty contacts}">
                <p><spring:message code="contact.list.label.no.contacts"/></p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${contacts}" var="contact">
                    <div class="well contact-list-item">
                        <a href="/contact/${contact.id}"><c:out value="${contact.lastName}"/> <c:out value="${contact.firstName}"/></a>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>