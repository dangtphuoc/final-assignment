function CurriculumComposeView() {
	
	this.model = {};
	this.model.courses = new Array();
	this.$tag = $('<div>');
	var $form = $('<form class="form-horizontal">');
	var $content = $('<div class="edit_course_content_div">');
	$content.append($form);
	this.$tag.append($content);
	
	//title
	var $controlGroup = $('<div class="control-group">');
	var $controls = $('<div class="controls">');
	var $label = $('<label class="control-label" for="courseEditTitle">Title</label>');
	this.$title =  $('<input>').attr({"type":"text", "placeholder":"Title"});
	$controls.append(this.$title);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	//description
	$controls = $('<div class="controls">');
	$controlGroup = $('<div class="control-group">');
	$label = $('<label class="control-label" for="courseEditDescription">Description</label>');
	this.$description =  $('<input>').attr({"type":"text", "placeholder":"Description"});
	$controls.append(this.$description);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	//buttons
	var $buttonPanel = $('<div class="button_panel_div">');
	var $addCourse =  $('<button>').addClass('btn').attr({"type":"button"}).append($('<i>').addClass('icon-plus')).append(' Add Course');
	var self = this;
	$addCourse.click(function() {
		var modalDialog = new ModalDialog('Course Search View');
		var courseSearchView = new CourseSearchView(self);
		modalDialog.setModel(courseSearchView.getTag());
		modalDialog.setCallbackFunction(courseSearchView, courseSearchView.submitAddedCourses);
		modalDialog.showDialog();
		
	});
	$buttonPanel.append($addCourse);
	this.$tag.append($buttonPanel);
	
	this.$courseTable = $('<div>').addClass('content_table_div');
	this.$tag.append(this.$courseTable);
	
	return this;
}

CurriculumComposeView.prototype = new BaseView();

CurriculumComposeView.prototype.repaint = function() {
	this.$title.val(this.model.title);
	this.$description.val(this.model.description);
	this.loadCourseTable();
};

CurriculumComposeView.prototype.loadCourseTable = function() {
	var simpleTable = new SimpleTableView();
	var header = ['Course Id', 'Title', 'Description', ''];
	simpleTable.setHeader(header);
	var data = this.model.courses;
	var model = [];
	for(var i in data) {
		var $removeIcon = $('<i>').addClass('icon-remove-sign');
		$removeIcon.click({view : this, course : data[i]}, removeCourseFromCurriculum);
		var item = [data[i].id, data[i].title, data[i].description, $removeIcon];
		model.push(item);
	}
	simpleTable.setModel(model);
	this.$courseTable.empty();
	this.$courseTable.append(simpleTable.getTag());
};

CurriculumComposeView.prototype.handleAddedCourses = function(addedCourses) {
	if(addedCourses != undefined) {
		if(this.model.courses == undefined) this.model.courses = new Array();
		this.model.courses = this.model.courses.concat(addedCourses);
		this.loadCourseTable();
	}
};
CurriculumComposeView.prototype.saveChanges = function() {
	var errorCode = this.validateData();
	if(errorCode == JSConfig.STATUS_SUCCESS) {
		this.model.title = this.$title.val();
		this.model.description = this.$description.val();
		var url = JSConfig.getInstance().getRESTUrl() + 'curricula';
		if(this.model.id != undefined) url = JSConfig.getInstance().getRESTUrl() + 'curricula/update';
		makeAjaxRequest(url, "POST", "json",
		function (){
			EventManager.getInstance().notifyEvent(EventManager.CURRICULUM_CREATED);
		}, 
		undefined, JSON.stringify(this.model));
	} else {
		Contact.addErrorMessage("Curriculum title and description must be not empty. Curriculum's content must be at least one course.");
	}
	return errorCode;
};

CurriculumComposeView.prototype.validateData = function() {
	if(this.$title.val() == "" || this.$description.val() == "") return JSConfig.STATUS_FAIL;
	if(this.model.courses.length == 0) return JSConfig.STATUS_FAIL;
	return JSConfig.STATUS_SUCCESS;
};
CurriculumComposeView.prototype.removeCourseFromCurriculum = function(course) {
	for(var i in this.model.courses) {
		if(this.model.courses[i].id == course.id) this.model.courses.splice(i, 1);
	}
	this.loadCourseTable();
};
function removeCourseFromCurriculum(event) {
	var course = event.data.course;
	var view = event.data.view;
	view.removeCourseFromCurriculum(course);
}