<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <script type="text/javascript" src="/static/js/contact.form.js"></script>
    <script type="text/javascript" src="/static/js/contact.view.js"></script>
    <title></title>
</head>
<body>
<div id="contact-id" class="hidden">${contact.id}</div>
<h1><c:out value="${contact.firstName}"/> <c:out value="${contact.lastName}"/></h1>

<div class="well">
    <address>
        <c:if test="${not empty contact.address}">
            <c:if test="${not empty contact.address.streetAddress}">
                <c:out value="${contact.address.streetAddress}"/><br/>
            </c:if>
            <c:if test="${not empty contact.address.postCode}">
                <c:out value="${contact.address.postCode}"/>
            </c:if>
            <c:if test="${not empty contact.address.postOffice}">
                <c:out value="${contact.address.postOffice}"/>
            </c:if>
            <c:if test="${(not empty contact.address.postCode) || (not empty contact.address.postOffice)}">
                <br/>
            </c:if>
            <c:if test="${not empty contact.address.state}">
                <c:out value="${contact.address.state}"/><br/>
            </c:if>
            <c:if test="${not empty contact.address.country}">
                <c:out value="${contact.address.country}"/><br/>
            </c:if>
        </c:if>
        <c:if test="${not empty contact.phoneNumber}">
            <abbr title="<spring:message code="view.contact.phoneNumber.abbr.title"/>">
                <spring:message code="view.contact.phoneNumber.abbr.label"/>
            </abbr><c:out value="${contact.phoneNumber}"/><br/>
        </c:if>
        <c:if test="${not empty contact.emailAddress}">
            <abbr title="<spring:message code="view.contact.emailAddress.abbr.title"/>">
                <spring:message code="view.contact.emailAddress.abbr.label"/>
            </abbr><c:out value="${contact.emailAddress}"/><br/>
        </c:if>
    </address>
    <div>
        <a href="/contact/update/${contact.id}" class="btn btn-primary"><spring:message
                code="update.contact.button.label"/></a>
        <a id="delete-contact-link" class="btn btn-primary"><spring:message code="delete.contact.button.label"/></a>
    </div>
</div>
<script id="template-delete-contact-confirmation-dialog" type="text/x-handlebars-template">
    <div id="delete-contact-confirmation-dialog" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="delete.contact.dialog.title"/></h3>
        </div>
        <div class="modal-body">
            <p><spring:message code="delete.contact.dialog.message"/></p>
        </div>
        <div class="modal-footer">
            <a id="cancel-contact-button" href="#" class="btn"><spring:message code="cancel.label"/></a>
            <a id="delete-contact-button" href="#" class="btn btn-primary"><spring:message code="delete.contact.button.label"/></a>
        </div>
    </div>
</script>
</body>
</html>