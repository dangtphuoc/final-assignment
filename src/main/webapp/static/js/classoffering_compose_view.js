function ClassOfferingComposeView() {
	this.model = new Array();
	var self = this;
	this.$tag = $('<div>').addClass('classoffering_compose_view_div');
	
	this.$contentTable = $('<div>').addClass('classoffering_compose_view_content_table');
	this.$tag.append(this.$contentTable);
	this.$title = $('<input>').attr({type : 'text', placeholder : 'Enter a class title'});
	this.startDate = new DateInput();
	this.endDate = new DateInput();
	this.$location = $('<select>');
	this.$instructor = $('<select>');
	this.$addButton = $('<button class="btn" type="button">Add</button>');
	
	this.$addButton.click(function(){
		self.addOffering();
	});
	
	this.initializeLocations();
	this.initializeInstructors();
	return this;
}

ClassOfferingComposeView.prototype = new BaseView();

ClassOfferingComposeView.prototype.initializeLocations = function() {
	var self = this;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + "locations", "GET", "json",
	function(data) {
		if(data != undefined) {
			for(var i in data) {
				self.$location.append($('<option>').attr({'value' : data[i].id}).text(data[i].title));
			}
		}
	});
};

ClassOfferingComposeView.prototype.initializeInstructors = function() {
	var self = this;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + "students?filterRole=3", "GET", "json",
	function(data) {
		if(data != undefined) {
			for(var i in data) {
				self.$instructor.append($('<option>').attr({'value' : data[i].id}).text(data[i].firstName + " " + data[i].lastName));
			}
		}
	});
};

ClassOfferingComposeView.prototype.repaint = function() {
	var simpleTable = new SimpleTableView();
	var header = ['Title', 'Start Date', 'End Date', 'Location', 'Instructor', ''];
	simpleTable.setHeader(header);
	var data = this.model;
	var model = [];
	for(var i in data) {
		var $removeIcon = $('<i>').addClass('icon-remove-sign');
		$removeIcon.click({view : this, pos : i}, removeOffering);
		var startDate = data[i].startTime == null ? "" : moment(data[i].startTime).format(JSConfig.SYSTEM_FORMAT_DATE);
		var endDate = data[i].endTime == null ? "" : moment(data[i].endTime).format(JSConfig.SYSTEM_FORMAT_DATE);
		var item = [data[i].title, startDate, endDate, data[i].location.title, data[i].instructor.firstName, $removeIcon];
		model.push(item);
	}
	var item = [this.$title, this.startDate.getTag(), this.endDate.getTag(), this.$location, this.$instructor, this.$addButton];
	model.push(item);
	simpleTable.setModel(model);
	this.$contentTable.empty();
	this.$contentTable.append(simpleTable.getTag());
};

ClassOfferingComposeView.prototype.addOffering = function() {
	if(!this.validateData()) {
		Contact.addErrorMessage("Your data is invalid. Please correct and retry.");
		return;
	}
	var offering = {};
	offering.title = this.$title.val();
	offering.startTime = this.startDate.getSelectedDate();
	offering.endTime = this.endDate.getSelectedDate();
	if(this.$location.val() != undefined && this.$location.val() != "") {
		offering.location = {id: this.$location.val(), title : this.$location.find(":selected").text()};
	}
	if(this.$instructor.val() != undefined && this.$instructor.val() != "") {
		offering.instructor = {id: this.$instructor.val(), firstName: this.$instructor.find(":selected").text()};
	}
	this.model.push(offering);
	
	this.repaint();
};
ClassOfferingComposeView.prototype.validateData = function() {
	if(this.$title.val() == "" || this.endDate.getSelectedDate() == "" || this.startDate.getSelectedDate() == "") return false;
	if(this.startDate > this.endDate) return false;
	for(var i in this.model) {
		if(this.model[i].title == this.$title.val()) return false;
	}
	return true;
};
ClassOfferingComposeView.prototype.removeOffering = function(pos) {
	this.model.splice(pos, 1);
	this.repaint();
};
function removeOffering(event) {
	event.data.view.removeOffering(event.data.pos);
};