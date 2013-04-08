<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <script type="text/javascript" src="/static/js/contact.form.js"></script>
    <script type="text/javascript" src="/static/js/contact.update.js"></script>
    <title></title>
</head>
<body>
<div id="contact-id" class="hidden">${contact.id}</div>
<h1><spring:message code="update.contact.title"/></h1>
<form:errors path="contact" cssClass="errorBlock" element="div"/>
<form:form action="/contact/update" cssClass="well" commandName="contact" method="POST">
    <form:hidden path="id"/>
    <div id="control-group-firstName" class="control-group">
        <label for="contact-firstName"><spring:message code="contact.label.firstName"/>:</label>

        <div class="controls">
            <form:input id="contact-firstName" path="firstName"/>
            <form:errors id="error-firstName" path="firstName" cssClass="help-inline"/>
        </div>
    </div>
    <div id="control-group-lastName" class="control-group">
        <label for="contact-lastName"><spring:message code="contact.label.lastName"/>:</label>

        <div class="controls">
            <form:input id="contact-lastName" path="lastName"/>
            <form:errors id="error-lastName" path="lastName" cssClass="help-inline"/>
        </div>
    </div>
    <div id="control-group-emailAddress" class="control-group">
        <label for="contact-emailAddress"><spring:message code="contact.label.emailAddress"/>:</label>

        <div class="controls">
            <form:input id="contact-emailAddress" path="emailAddress"/>
            <form:errors id="error-emailAddress" path="emailAddress" cssClass="help-inline"/>
        </div>
    </div>
    <div id="control-group-phoneNumber" class="control-group">
        <label for="contact-phoneNumber"><spring:message code="contact.label.phoneNumber"/>:</label>

        <div class="controls">
            <form:input id="contact-phoneNumber" path="phoneNumber"/>
            <form:errors id="error-phoneNumber" path="phoneNumber" cssClass="help-inline"/>
        </div>
    </div>
    <div id="control-group-streetAddress" class="control-group">
        <label for="contact-streetAddress"><spring:message code="contact.label.streetAddress"/>:</label>

        <div class="controls">
            <form:input id="contact-streetAddress" path="streetAddress"/>
            <form:errors id="error-streetAddress" path="streetAddress" cssClass="help-inline"/>
        </div>
    </div>
    <div id="control-group-postCode" class="control-group">
        <label for="contact-postCode"><spring:message code="contact.label.postCode"/>:</label>

        <div class="controls">
            <form:input id="contact-postCode" path="postCode"/>
            <form:errors id="error-postCode" path="postCode" cssClass="help-inline"/>
        </div>
    </div>
    <div id="control-group-postOffice" class="control-group">
        <label for="contact-postOffice"><spring:message code="contact.label.postOffice"/>:</label>

        <div class="controls">
            <form:input id="contact-postOffice" path="postOffice"/>
            <form:errors id="error-postOffice" path="postOffice" cssClass="help-inline"/>
        </div>
    </div>
    <div id="control-group-state" class="control-group">
        <label for="contact-streetAddress"><spring:message code="contact.label.state"/>:</label>

        <div class="controls">
            <form:input id="contact-state" path="state"/>
            <form:errors id="error-state" path="state" cssClass="help-inline"/>
        </div>
    </div>
    <div id="control-group-country" class="control-group">
        <label for="contact-country"><spring:message code="contact.label.country"/>:</label>

        <div class="controls">
            <form:input id="contact-country" path="country"/>
            <form:errors id="error-country" path="country" cssClass="help-inline"/>
        </div>
    </div>
    <div class="form-buttons">
        <button id="cancel-contact-button" class="btn"><spring:message code="cancel.label"/></button>
        <button id="update-contact-button" type="submit" class="btn btn-primary"><spring:message
                code="update.contact.button.label"/></button>
    </div>
</form:form>
</body>
</html>