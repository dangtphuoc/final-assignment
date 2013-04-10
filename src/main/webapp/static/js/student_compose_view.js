function StudentComposeView() {
	this.$tag = $('<div>');
	var $form = $('<form class="form-horizontal">');
	var $content = $('<div class="content_table_div">');
	$content.append($form);
	this.$tag.append($content);
	
	//first name
	var $controlGroup = $('<div class="control-group">');
	var $controls = $('<div class="controls">');
	var $label = $('<label class="control-label" for="courseEditTitle">First Name</label>');
	this.$firstName =  $('<input>').attr({"type":"text", "placeholder":"First Name"});
	$controls.append(this.$firstName);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	//last name
	$controls = $('<div class="controls">');
	$controlGroup = $('<div class="control-group">');
	$label = $('<label class="control-label" for="courseEditDescription">Last Name</label>');
	this.$lastName =  $('<input>').attr({"type":"text", "placeholder":"Last Name"});
	$controls.append(this.$lastName);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	//roles
	$controls = $('<div class="controls">');
	$controlGroup = $('<div class="control-group">');
	$label = $('<label class="control-label" for="courseEditDescription">Roles</label>');
	this.$roles =  $('<select multiple="multiple">');
	$controls.append(this.$roles);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	//manager
	$controls = $('<div class="controls">');
	$controlGroup = $('<div class="control-group">');
	$label = $('<label class="control-label" for="courseEditDescription">Manager</label>');
	this.$manager =  $('<select>');
	$controls.append(this.$manager);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	this.initializeRoles();
	this.initializeManagers();
}

StudentComposeView.prototype = new BaseView();

StudentComposeView.prototype.initializeRoles = function() {
	var self = this;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + "roles", "GET", "json",
	function(data) {
		if(data != undefined) {
			for(var i in data) {
				var $option = $('<option>').attr({'value' : data[i].id}).text(data[i].title);
				for(var j in self.model.roles) {
					if(self.model.roles[j].id == data[i].id) {
						$option.attr({"selected" : "selected"});
						break;
					}
				}
				self.$roles.append($option);
			}
		}
	});
};
StudentComposeView.prototype.initializeManagers = function() {
	var self = this;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + "students?filterRole=2", "GET", "json",
	function(data) {
		var $option = $('<option>').attr({'value' : ''}).text('None');
		self.$manager.append($option);
		if(data != undefined) {
			for(var i in data) {
				var $option = $('<option>').attr({'value' : data[i].id}).text(data[i].firstName + " " + data[i].lastName);
				if(self.model.manager != undefined && self.model.manager.id == data[i].id) {
					$option.attr({"selected" : "selected"});
				}
				self.$manager.append($option);
			}
		}
	});
};

StudentComposeView.prototype.repaint = function() {
	this.$firstName.val(this.model.firstName);
	this.$lastName.val(this.model.lastName);
	if(this.model.manager != undefined) {
		this.$manager.val(this.model.manager.id);
	}
};

StudentComposeView.prototype.saveChanges = function() {
	var errorCode = this.validateData();
	if(errorCode == JSConfig.STATUS_SUCCESS) {
		var title = this.$firstName.val();
		var description = this.$lastName.val();
		this.model.firstName = title;
		this.model.lastName = description;
		this.model.roles = [];
		var selectedRoles = this.$roles.val();
		if(selectedRoles != undefined) {
			for(var i in selectedRoles) {
				this.model.roles.push({id : selectedRoles[i]});
			}
		}
		
		//manager
		if(this.$manager.val() != undefined && this.$manager.val() != '') {
			this.model.manager = {id : this.$manager.val()};
		}
		var url = JSConfig.getInstance().getRESTUrl() + 'students';
		if(this.model.id != undefined && this.model.id != "") {
			url = JSConfig.getInstance().getRESTUrl() + 'students/update';
		}
		makeAjaxRequest(url, "POST", "json",
		function (data){
			if(data.code == JSConfig.STATUS_SUCCESS) {
				Contact.addMessage("Student has created/updated successfully.");
			} else {
				Contact.addErrorMessage("Failed to create/update student.");
			}
			EventManager.getInstance().notifyEvent(EventManager.STUDENT_CREATED);
		}, 
		undefined, JSON.stringify(this.model));
	} else {
		Contact.addErrorMessage("Invalid Data.");
	}
	return errorCode;
};
StudentComposeView.prototype.validateData = function() {
	if(this.$firstName.val() == "" || this.$lastName.val() == ""
		|| this.$roles.val() == undefined || this.$roles.val().length == 0
		|| this.$manager.val() == "") return JSConfig.STATUS_FAIL;
	return JSConfig.STATUS_SUCCESS;
};