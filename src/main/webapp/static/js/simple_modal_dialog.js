function ModalDialog(title) {
	this.$tag = $('<div>').addClass('modal hide fade').attr({"tabindex":"-1", "role":"dialog", "aria-labelledby":"myModalLabel", "aria-hidden":"true"});
	this.$header = $('<div>').addClass('modal-header');
	this.$body = $('<div>').addClass('modal-body');
	this.$footer = $('<div>').addClass('modal-footer');
	this.$tag.append(this.$header).append(this.$body).append(this.$footer);
	
	var $headerCloseButton = $('<button>').addClass('close').attr({type: 'button', "data-dismiss" : 'modal', "aria-hidden" : 'true'}).text('x');
	this.$header.append($headerCloseButton);
	this.$header.append($('<h3>').append(title == undefined? "Modal Header" : title));
	this.$body.append('<p>One fine body…</p>');
	
	var $footerCloseButton = $('<button>').addClass('btn').attr({"data-dismiss" : 'modal', "aria-hidden" : 'true'}).text('Close');
	var $saveButton = $('<button>').addClass('btn btn-primary').text('Save Changes');
	var self = this;
	$saveButton.click(function(){
		self.handleSaveChanges();
	});
	this.$footer.append($saveButton);
	this.$footer.append($footerCloseButton);
}

ModalDialog.prototype = new BaseView();

ModalDialog.prototype.showDialog = function(width) {
	width = width == undefined ? '1000px' : width;
	this.$tag.modal('toggle').css({'width': width,'margin-left': function () {return -($(this).width() / 2);}});
};

ModalDialog.prototype.repaint = function() {
	this.$body.empty();
	this.$body.append(this.model);
};

ModalDialog.prototype.setCallbackFunction = function(context, cb, data) {
	this.cbFunction = cb;
	this.cbContext = context;
	this.data = data;
};

ModalDialog.prototype.handleSaveChanges = function() {
	var errorCode = JSConfig.STATUS_SUCCESS;
	if(this.cbFunction != undefined) {
		if(this.data != undefined) {
			errorCode = this.cbFunction.call(this.cbContext, this.data);
		} else {
			errorCode = this.cbFunction.call(this.cbContext);
		}
	}
	
	if(errorCode != JSConfig.STATUS_FAIL) {
		this.$tag.modal('hide');
	}
};