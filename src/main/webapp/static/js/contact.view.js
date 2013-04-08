$(function() {

    $(".well").on("click", "#delete-contact-link", function(e) {
        e.preventDefault();

        var contactDeleteDialogTemplate = Handlebars.compile($("#template-delete-contact-confirmation-dialog").html());

        $("#view-holder").append(contactDeleteDialogTemplate());
        $("#delete-contact-confirmation-dialog").modal();
    })

    $("#view-holder").on("click", "#cancel-contact-button", function(e) {
        e.preventDefault();

        var deleteConfirmationDialog = $("#delete-contact-confirmation-dialog")
        deleteConfirmationDialog.modal('hide');
        deleteConfirmationDialog.remove();
    });

    $("#view-holder").on("click", "#delete-contact-button", function(e) {
        e.preventDefault();

        $.ajax({
            type: "DELETE",
            url: "/contact/" + $("#contact-id").text(),
            success: function(result) {
                Contact.storeMessageToCache(result);
                window.location.href = "/";
            }
        });
    });
});