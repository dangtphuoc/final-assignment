function LocationComposeView() {
	this.$tag = $('<div>');
	var $form = $('<form class="form-horizontal">');
	var $content = $('<div class="content_table_div">');
	$content.append($form);
	this.$tag.append($content);
	
	//title
	var $controlGroup = $('<div class="control-group">');
	var $controls = $('<div class="controls">');
	var $label = $('<label class="control-label">Location Title</label>');
	this.$title =  $('<input>').attr({"type":"text", "placeholder":"Title"});
	$controls.append(this.$title);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	//description
	$controls = $('<div class="controls">');
	$controlGroup = $('<div class="control-group">');
	$label = $('<label class="control-label">Location Description</label>');
	this.$description =  $('<input>').attr({"type":"text", "placeholder":"Description"});
	$controls.append(this.$description);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	//contact
	$controls = $('<div class="controls">');
	$controlGroup = $('<div class="control-group">');
	$label = $('<label class="control-label">Contact Person</label>');
	this.$contact =  $('<input>').attr({"type":"text", "placeholder":"Contact Person"});
	$controls.append(this.$contact);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	//telephone
	$controls = $('<div class="controls">');
	$controlGroup = $('<div class="control-group">');
	$label = $('<label class="control-label">Telephone</label>');
	this.$telephone =  $('<input>').attr({"type":"text", "placeholder":"Telephone"});
	$controls.append(this.$telephone);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
}

LocationComposeView.prototype = new BaseView();

LocationComposeView.prototype.repaint = function() {
	this.$title.val(this.model.title);
	this.$description.val(this.model.description);
	this.$contact.val(this.model.contactPerson);
	this.$telephone.val(this.model.telephone);
};

LocationComposeView.prototype.saveChanges = function() {
	var errorCode = this.validateData();
	if(errorCode == JSConfig.STATUS_SUCCESS) {
		this.model.title = this.$title.val();
		this.model.description = this.$description.val();
		this.model.contactPerson = this.$contact.val();
		this.model.telephone = this.$telephone.val();
		var url = JSConfig.getInstance().getRESTUrl() + 'locations';
		if(this.model.id != undefined && this.model.id != "") url += '/update';
		makeAjaxRequest(url, "POST", "json",
		function (data){
			if(data.code == JSConfig.STATUS_SUCCESS) {
				Contact.addMessage("Location has created/updated successfully.");
			} else {
				Contact.addErrorMessage("Failed to create/update location.");
			}
			EventManager.getInstance().notifyEvent(EventManager.LOCATION_CREATED);
		}, 
		undefined, JSON.stringify(this.model));
	} else {
		Contact.addErrorMessage("Location title or description is empty.");
	}
	
	return errorCode;
};

LocationComposeView.prototype.validateData = function() {
	if(this.$title.val() == "" || this.$description.val() == "") return JSConfig.STATUS_FAIL;
	return JSConfig.STATUS_SUCCESS;
};