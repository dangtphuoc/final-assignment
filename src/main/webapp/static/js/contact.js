var messageCacheOptions = {
    expires: 30000
};

var Contact = {
    addErrorMessage:function (message) {
        var alertTemplate = Handlebars.compile($("#template-alert-message-error").html());
        $("#message-holder").html(alertTemplate({message:message}));
        $("#alert-message-error").alert().delay(3000).fadeOut("fast", function() { $(this).remove(); });
    },

    addMessage:function (message) {
        var alertTemplate = Handlebars.compile($("#template-alert-message").html());
        $("#message-holder").html(alertTemplate({message:message}));
        $("#alert-message").alert().delay(3000).fadeOut("fast", function() { $(this).remove(); });
    },
    getErrorMessageCacheKey: function() {
        return "contacts.errorMessage";
    },
    getErrorMessageFromCache: function() {
        var errorMessage = amplify.store(Contact.getErrorMessageCacheKey());
        amplify.store(Contact.getErrorMessageCacheKey(), null);
        return errorMessage;
    },
    getMessageCacheKey: function() {
        return "contacts.message";
    },
    getMessageFromCache: function() {
        var message = amplify.store(Contact.getMessageCacheKey());
        amplify.store(Contact.getMessageCacheKey(), null);
        return message;
    },
    storeErrorMessageToCache: function(message) {
        amplify.store(Contact.getErrorMessageCacheKey(), message, messageCacheOptions);
    },
    storeMessageToCache: function(message) {
        amplify.store(Contact.getMessageCacheKey(), message, messageCacheOptions);
    }

};

$(document).ready(function () {

    var errorMessage = $(".errroblock");
    if (errorMessage.length > 0) {
        Contact.addErrorMessage(errorMessage.text());
    }
    else {
        errorMessage = Contact.getErrorMessageFromCache();
        if (errorMessage) {
            Contact.addErrorMessage(errorMessage);
        }
    }

    var feedbackMessage = $(".messageblock");
    if (feedbackMessage.length > 0) {
        Contact.addMessage(feedbackMessage.text());
    }
    else {
        feedbackMessage = Contact.getMessageFromCache();
        if (feedbackMessage) {
            Contact.addMessage(feedbackMessage);
        }
    }
    
});

$(document).bind('ajaxError', function(error, response) {
    if (response.status == "404") {
        window.location.href = JSConfig.getInstance().getRESTUrl() + "error/404";
    }
    else if(response.status == "500"){
        window.location.href = JSConfig.getInstance().getRESTUrl() + "error/error";
    }
});