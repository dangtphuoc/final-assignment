function CourseSearchView(parentView) {
	this.parentView = parentView;
	this.selectedCourses = new Object();
	this.$tag = $('<div>');
	var $form = $('<form class="form-horizontal">');
	var $content = $('<div class="edit_course_content_div">');
	$content.append($form);
	this.$tag.append($content);
	
	//course title
	var $controlGroup = $('<div class="control-group">');
	var $controls = $('<div class="controls">');
	var $label = $('<label class="control-label" for="courseEditTitle">Find:</label>');
	this.$find =  $('<input>').attr({"type":"text", "placeholder":"Course Title"});
	var $searchButton = $('<button>').addClass("btn").attr({"type" : "button"}).append($('<i>').addClass('icon-search')).append("Search");
	var self = this;
	$searchButton.click(function() {
		self.searchCourses();
	});
	$controls.append(this.$find);
	$controls.append($searchButton);
	$controlGroup.append($label);
	$controlGroup.append($controls);
	$form.append($controlGroup);
	
	this.$courseTable = $('<div>').addClass('course_table_div');
	this.$tag.append(this.$courseTable);
	
	this.searchCourses();
	
	return this;
}

CourseSearchView.prototype = new BaseView();

CourseSearchView.prototype.searchCourses = function() {
	var self = this;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'courses/search?key=' + this.$find.val(), 'GET', 'json', function(data){
		self.loadCourseTable(data);
	});
};

CourseSearchView.prototype.loadCourseTable = function(data) {
	var simpleTable = new SimpleTableView();
	var header = ['', 'Course Id', 'Title', 'Description'];
	simpleTable.setHeader(header);
	var model = [];
	for(var i in data) {
		var $checkBox = $('<input>').attr({"type" : "checkbox"});
		$checkBox.change({view: this, course: data[i]}, handleCheckboxChanged);
		var item = [$checkBox, data[i].id, data[i].title, data[i].description];
		model.push(item);
	}
	simpleTable.setModel(model);
	this.$courseTable.empty();
	this.$courseTable.append(simpleTable.getTag());
};

CourseSearchView.prototype.submitAddedCourses = function() {
	var addedCourses = new Array();
	for(var id in this.selectedCourses) {
		if(this.selectedCourses[id] != undefined) addedCourses.push(this.selectedCourses[id]);
	}
	
	this.parentView.handleAddedCourses(addedCourses);
};

function handleCheckboxChanged(event) {
	var course = event.data.course;
	var view = event.data.view;
	if($(this).is(':checked')) {
		view.selectedCourses[course.id] = course;
	} else {
		view.selectedCourses[course.id] = undefined;
	}
}