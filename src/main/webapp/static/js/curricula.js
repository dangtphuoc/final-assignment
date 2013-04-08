/**
 * Global variables
 */
var selectedCurriculumIds = undefined;

$(function() {
	
	initData();
	
	/**
	 * Binding event handler
	 */
	$('#btnAddCurriculum').click(function(){
		var curriculumView = new CurriculumComposeView(true);
		var curriculumModel = {};
		curriculumModel.courses = new Array();
		curriculumView.setModel(curriculumModel);
		var dialog = new ModalDialog('Curriculum View');
		dialog.setModel(curriculumView.getTag());
		dialog.setCallbackFunction(curriculumView, curriculumView.saveChanges);
		dialog.showDialog();
    });
	
	$('#btnSearch').click(function() {
		loadCurriculumData();
	});
	
	$('#btnSearchnEnroll').click(function() {
		var searchKey = $('#inSearchnEnrollKey').val();
		var withCourse = $('#inWithCourse').val();
		var url = JSConfig.getInstance().getRESTUrl() + 'curricula/search' + '?key=' + searchKey + '&withCourse=' + withCourse;
		makeAjaxRequest(url, 'GET', 'JSON', "jsonCBSearchnEnrollCurricula");
	});
	
	$('#btnEnroll').click(function() {
		if(selectedCurriculumIds.length == 0) {
			Contact.addErrorMessage('There is no curriculum selected.');
		} else {
			var studentSearchView = new StudentSearchView();
			studentSearchView.setCallbackFunction(enrollCurricula);
			studentSearchView.setModel({});
			var dialog = new ModalDialog('Student Search');
			dialog.setModel(studentSearchView.getTag());
			dialog.setCallbackFunction(studentSearchView, studentSearchView.handleSaveChanges);
			dialog.showDialog();
		}
	});
	
	/**
	 * Event registration
	 */
	EventManager.getInstance().registerHandler(EventManager.CURRICULUM_CREATED, loadCurriculumData);
});

function initData() {
	selectedCurriculumIds = new Array();
	loadCurriculumData();
	jsonCBSearchnEnrollCurricula([]);
	
}
function loadCurriculumData() {
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'curricula?key=' + $('#inSearchKey').val(), "GET", "json",
				"jsonCBCurricula", undefined, undefined);
}

function editCurriculum(event) {
	var id = event.data.id;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'curricula/' + id, "GET", "json",
			"jsobCBEditCurriculum");
}
function jsonCBCurricula(data) {
	var simpleTable = new SimpleTableView();
	simpleTable.setRowClickFunction(editCurriculum);
	var header = ['Id', 'Title', 'Description', ''];
	simpleTable.setHeader(header);
	var model = [];
	for(var i in data) {
		var $removeIcon = $('<i>').addClass('icon-remove-sign');
		$removeIcon.click(data[i], removeCurriculum);
		var item = [data[i].id, data[i].title, data[i].description, $removeIcon];
		item.rowClickData = data[i];
		model.push(item);
	}
	simpleTable.setModel(model);
	var $courseDiv = $('#course_content');
	$courseDiv.empty();
	$courseDiv.append(simpleTable.getTag());
}

function jsonCBSearchnEnrollCurricula(data) {
	var simpleTable = new SimpleTableView();
	var header = ['', 'Id', 'Title', 'Description'];
	simpleTable.setHeader(header);
	var model = [];
	for(var i in data) {
		var $checkbox = $('<input>').attr({type: "checkbox"});
		$checkbox.click(data[i], handleCurriculumCheckbox);
		var item = [$checkbox, data[i].id, data[i].title, data[i].description];
		model.push(item);
	}
	simpleTable.setModel(model);
	var $courseDiv = $('#searchnenroll_content');
	$courseDiv.empty();
	$courseDiv.append(simpleTable.getTag());
}

function jsobCBEditCurriculum(data) {
	var curriculumComposeView = new CurriculumComposeView();
	curriculumComposeView.setModel(data);
	var dialog = new ModalDialog("Curriculum View");
	dialog.setModel(curriculumComposeView.getTag());
	dialog.setCallbackFunction(curriculumComposeView, curriculumComposeView.saveChanges);
	dialog.showDialog();
}

function removeCurriculum(event) {
	var curriculum = event.data;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'curricula/' + curriculum.id, 'DELETE', 'json', function() {
		loadCurriculumData();
	});
}

function handleCurriculumCheckbox(event) {
	var curriculum = event.data;
	if($(this).is(":checked")) selectedCurriculumIds.push(curriculum.id);
}

function enrollCurricula(selectedStudentIds) {
	var enrollData = {};
	enrollData.studentIds = selectedStudentIds;
	enrollData.curriculumIds = selectedCurriculumIds;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'curricula/enroll', 'POST', 'json', function(data) {
		if(data.code == JSConfig.STATUS_SUCCESS) {
			Contact.addMessage("Enrolled successfully.");
		} else {
			Contact.addErrorMessage("Enrolled unsuccessfully.");
		}
		
		jsonCBSearchnEnrollCurricula([]);
	}, undefined, JSON.stringify(enrollData));
}
$(function() {
    $("#navi-curricula-link").addClass("active");
});
