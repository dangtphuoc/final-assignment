function CourseComposeView() {
	this.$tag = $('<div>');
	this.$title =  $('<input>').attr({"type":"text", "placeholder":"Title"});
	this.$description =  $('<textarea>').attr({"row":"3", "placeholder":"Description"});
	var $form = $('<form class="form-horizontal">');
	var $controlGroup1 = $('<div class="control-group">');
	var $label1 = $('<label class="control-label">Course Title</label>');
	var $controls1 = $('<div class="controls">');
	var $controlGroup2 = $('<div class="control-group">');
	var $label2 = $('<label class="control-label">Course Description</label>');
	var $controls2 = $('<div class="controls">');
	var $content = $('<div class="content_table_div">');
	$content.append($form);
	$form.append($controlGroup1);
	$form.append($controlGroup2);
	$controlGroup1.append($label1);
	$controlGroup1.append($controls1);
	$controlGroup2.append($label2);
	$controlGroup2.append($controls2);
	$controls1.append(this.$title);
	$controls2.append(this.$description);
	this.$tag.append($content);
	
	this.$tag.append($('<h4>').text("Class Offerings"));
	this.classOfferingView = new ClassOfferingComposeView();
	this.$tag.append(this.classOfferingView.getTag());
	
	return this;
}

CourseComposeView.prototype = new BaseView();

CourseComposeView.prototype.repaint = function() {
	this.$title.val(this.model.title);
	this.$description.val(this.model.description);
	this.classOfferingView.setModel(this.model.classOfferings);
};

CourseComposeView.prototype.saveChanges = function() {
	var errorCode = this.validateData();
	if(errorCode == JSConfig.STATUS_SUCCESS) {
		var title = this.$title.val();
		var description = this.$description.val();
		this.model.title = title;
		this.model.description = description;
		this.model.classOfferings = this.classOfferingView.model;
		var url = JSConfig.getInstance().getRESTUrl() + 'courses';
		if(this.model.id != undefined && this.model.id != "") {
			url = JSConfig.getInstance().getRESTUrl() + 'courses/update';
		}
		makeAjaxRequest(url, "POST", "json",
				"cbSaveChanges", undefined, JSON.stringify(this.model));
		
		return JSConfig.STATUS_SUCCESS;
	} else {
		Contact.addErrorMessage("Course title or description is empty.");
	}
	
	return errorCode;
};
CourseComposeView.prototype.validateData = function() {
	var title = this.$title.val();
	var description = this.$description.val();
	if(title == "" || description == "") return JSConfig.STATUS_FAIL;
	return JSConfig.STATUS_SUCCESS;
};
function cbSaveChanges(data) {
	if(data.code == JSConfig.STATUS_SUCCESS) {
		Contact.addMessage("Course has created/updated successfully.");
	} else {
		Contact.addErrorMessage("Failed to create/update course.");
	}
	EventManager.getInstance().notifyEvent(EventManager.COURSE_CREATED);
}