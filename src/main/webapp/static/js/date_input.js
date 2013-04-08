function DateInput(id) {
	id = id == undefined ? "" : id;
	this.$tag = $('<input>').addClass('input-small').attr({id : id, type: "text", placeholder : "Select a date..."});
	var self = this;
	this.$tag.datepicker().on('changeDate', function(ev) {
		self.$tag.datepicker('hide');
		
	});
	
	return this;
}

DateInput.prototype = new BaseView();

DateInput.prototype.getSelectedDate = function() {
	if(this.$tag.val() == "") return "";
	return new Date(this.$tag.val());
};
