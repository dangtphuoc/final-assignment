/**
 * Global variables
 */
var startDate = undefined;
var endDate = undefined;
var selectedClassOfferingIds = undefined;

$(function() {
	initData();
	$('#btnAddCourse').click(function(){
		var courseView = new CourseComposeView(true);
		var courseModel = {};
		courseModel.classOfferings = [];
		courseView.setModel(courseModel);
		var dialog = new ModalDialog('Course View');
		dialog.setModel(courseView.getTag());
		dialog.setCallbackFunction(courseView, courseView.saveChanges);
		dialog.showDialog();
    });
	$('#btnAddLocation').click(function(){
		var locationAddView = new LocationComposeView();
		locationAddView.setModel({});
		var dialog = new ModalDialog('Location View');
		dialog.setModel(locationAddView.getTag());
		dialog.setCallbackFunction(locationAddView, locationAddView.saveChanges);
		dialog.showDialog();
    });
	$('#btnSearch').click(function(){
		loadCourseData();
	});
	
	$('#btnSearchnEnroll').click(function() {
		var searchKey = $('#inSearchnEnrollKey').val();
		var url = JSConfig.getInstance().getRESTUrl() + 'classofferings/search' + '?key=' + searchKey;
		if(startDate.getSelectedDate() != "") url += "&startDate=" + moment(startDate.getSelectedDate()).format(JSConfig.SYSTEM_FORMAT_DATE);
		if(endDate.getSelectedDate() != "") url += "&endDate=" + moment(endDate.getSelectedDate()).format(JSConfig.SYSTEM_FORMAT_DATE);
		makeAjaxRequest(url, 'GET', 'JSON', "jsonCBClassOfferings");
	});
	
	$('#btnEnroll').click(function() {
		if(selectedClassOfferingIds.length == 0) {
			Contact.addErrorMessage('There is no class offering selected.');
		} else {
			var studentSearchView = new StudentSearchView();
			studentSearchView.setModel({});
			studentSearchView.setCallbackFunction(enrollClassOfferings);
			var dialog = new ModalDialog('Student Search');
			dialog.setModel(studentSearchView.getTag());
			dialog.setCallbackFunction(studentSearchView, studentSearchView.handleSaveChanges);
			dialog.showDialog();
		}
	});
	$('a[data-toggle="tab"]').on('shown', function (e) {
		  var target  = e.target; // activated tab
		  if(target.hash == "#home") {
			  loadCourseData();
		  } else if(target.hash == '#location') {
			  loadLocationData();
		  }
		});
	EventManager.getInstance().registerHandler(EventManager.COURSE_CREATED, loadCourseData);
	EventManager.getInstance().registerHandler(EventManager.COURSE_UPDATED, loadCourseData);
	EventManager.getInstance().registerHandler(EventManager.LOCATION_CREATED, loadLocationData);
	EventManager.getInstance().registerHandler(EventManager.LOCATION_UPDATED, loadLocationData);
});

function jsonCBLoadCourseData(data) {
	var simpleTable = new SimpleTableView();
	simpleTable.setRowClickFunction(editCourse);
	var header = ['Id', 'Title', 'Description', ''];
	simpleTable.setHeader(header);
	var model = [];
	for(var i in data) {
		var $removeIcon = $('<i>').addClass('icon-remove-sign');
		$removeIcon.click(data[i], removeCourse);
		var item = [data[i].id, data[i].title, data[i].description, $removeIcon];
		item.rowClickData = data[i];
		model.push(item);
	}
	simpleTable.setModel(model);
	var $courseDiv = $('#course_content');
	$courseDiv.empty();
	$courseDiv.append(simpleTable.getTag());
}
function editCourse(event) {
	var id = event.data.id;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'courses/' + id, "GET", "json",
			"jsonCBEditCourse");
}
function jsonCBEditCourse(data) {
	var courseEditView = new CourseComposeView();
	courseEditView.setModel(data);
	var dialog = new ModalDialog();
	dialog.setModel(courseEditView.getTag());
	dialog.setCallbackFunction(courseEditView, courseEditView.saveChanges);
	dialog.showDialog();
}
function initData() {
	selectedClassOfferingIds = new Array();
	startDate = new DateInput("startDate");
	endDate = new DateInput("endDate");
	$('#startDateDiv').append(startDate.getTag());
	$('#endDateDiv').append(endDate.getTag());
	jsonCBClassOfferings([]);
	
	loadCourseData();
	loadLocationData();
	
}
function loadCourseData() {
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'courses?key=' + $('#inSearchKey').val(), "GET", "json",
				"jsonCBLoadCourseData", undefined, undefined);
}
function loadLocationData() {
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'locations', "GET", "json",
			"jsonCBLoadLocationData", undefined, undefined);
}
function jsonCBLoadLocationData(data) {
	var simpleTable = new SimpleTableView();
	simpleTable.setRowClickFunction(editLocation);
	var header = ['Id', 'Title', 'Description'];
	simpleTable.setHeader(header);
	var model = [];
	for(var i in data) {
		var item = [data[i].id, data[i].title, data[i].description];
		item.rowClickData = data[i];
		model.push(item);
	}
	simpleTable.setModel(model);
	var $locationContentDiv = $('#location_content');
	$locationContentDiv.empty();
	$locationContentDiv.append(simpleTable.getTag());
}
function editLocation(event) {
	var id = event.data.id;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'locations/' + id, 'GET', 'json', 'jsonCBEditLocation');
}
function jsonCBEditLocation(data) {
	var locationAddView = new LocationComposeView();
	locationAddView.setModel(data);
	var dialog = new ModalDialog('Location View');
	dialog.setModel(locationAddView.getTag());
	dialog.setCallbackFunction(locationAddView, locationAddView.saveChanges);
	dialog.showDialog();
}

function removeCourse(event) {
	var course = event.data;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'courses/' + course.id, 'DELETE', 'json', function() {
		loadCourseData();
	});
}

function jsonCBClassOfferings(data) {
	selectedClassOfferingIds = new Array();
	var simpleTable = new SimpleTableView();
	var header = ['', 'Course Title', 'Class Title', 'Description'];
	simpleTable.setHeader(header);
	var model = [];
	for(var i in data) {
		var $checkbox = $('<input>').attr({type: "checkbox"});
		$checkbox.click(data[i], handleClassOfferingCheckbox);
		var item = [$checkbox, data[i].course.title, data[i].title, data[i].description];
		model.push(item);
	}
	simpleTable.setModel(model);
	var $searchNEnroll = $('#searchnenroll_content');
	$searchNEnroll.empty();
	$searchNEnroll.append(simpleTable.getTag());
}
function handleClassOfferingCheckbox(event) {
	var classOffering = event.data;
	if($(this).is(":checked")) {
		selectedClassOfferingIds.push(classOffering.id);
	} else {
		var pos = selectedClassOfferingIds.indexOf(classOffering.id);
		if(pos > -1) {
			selectedClassOfferingIds.splice(pos, 1);
		}
	}
}

function enrollClassOfferings(selectedStudentIds) {
	var enrollData = {};
	enrollData.studentIds = selectedStudentIds;
	enrollData.classOfferingIds = selectedClassOfferingIds;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'classofferings/enroll', 'POST', 'json', function(data) {
		if(data.code == 0) {
			Contact.addMessage("Enrolled successfully.");
		} else {
			Contact.addErrorMessage("Enrolled unsuccessfully.");
		}
		
		jsonCBClassOfferings([]);
	}, undefined, JSON.stringify(enrollData));
}
$(function() {
    $("#navi-courses-link").addClass("active");
});
