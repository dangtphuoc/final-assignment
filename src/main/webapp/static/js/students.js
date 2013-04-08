$(function() {
	
	initData();
	
	$('#btnAddStudent').click(function(){
		var studentView = new StudentComposeView();
		studentView.setModel({});
		var dialog = new ModalDialog('Student Compose View');
		dialog.setModel(studentView.getTag());
		dialog.setCallbackFunction(studentView, studentView.saveChanges);
		dialog.showDialog();
    });
	
	$('#btnSearch').click(function() {
		var studentId = $('#selStudent').val();
		var url = JSConfig.getInstance().getRESTUrl() + 'students/' + studentId + '/enrolledcontent';
		makeAjaxRequest(url, 'GET', 'JSON', "jsonCBEnrolledContent");
	});
	
	EventManager.getInstance().registerHandler(EventManager.STUDENT_CREATED, loadStudentData);
	EventManager.getInstance().registerHandler(EventManager.STUDENT_UPDATED, loadStudentData);
});

function cbEditStudent(data) {
	var courseEditView = new StudentComposeView();
	courseEditView.setModel(data);
	var dialog = new ModalDialog();
	dialog.setModel(courseEditView.getTag());
	dialog.setCallbackFunction(courseEditView, courseEditView.saveChanges);
	dialog.showDialog();
}
function initData() {
	loadStudentData();
	jsonCBEnrolledContent([]);
	
}
function loadStudentData() {
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'students', "GET", "json",
			"cbLoadStudentData", undefined, undefined);
}
function cbLoadStudentData(data) {
	var simpleTable = new SimpleTableView();
	simpleTable.setRowClickFunction(editStudent);
	var header = ['Id', 'First Name', 'Last Name'];
	simpleTable.setHeader(header);
	var model = [];
	for(var i in data) {
		var item = [data[i].id, data[i].firstName, data[i].lastName];
		item.rowClickData = data[i];
		model.push(item);
	}
	simpleTable.setModel(model);
	var $studentContentDiv = $('#student_content');
	$studentContentDiv.empty();
	$studentContentDiv.append(simpleTable.getTag());
	
	//load select
	var $selStudent = $('#selStudent');
	$selStudent.empty();
	for(var i in data) {
		$selStudent.append($('<option>').attr({"value" : data[i].id}).text(data[i].firstName + " " + data[i].lastName));
	}
}
function editStudent(event) {
	var student = event.data;
	makeAjaxRequest(JSConfig.getInstance().getRESTUrl() + 'students/' + student.id, "GET", "json",
			"cbEditStudent");
}

function jsonCBEnrolledContent(data) {
	var simpleTable = new SimpleTableView();
	var header = ['Id',  'Title', 'Description'];
	simpleTable.setHeader(header);
	var model = [];
	for(var i in data) {
		var item = [data[i].id, data[i].title, data[i].description];
		model.push(item);
	}
	simpleTable.setModel(model);
	var $enrollment = $('#enrollment_content');
	$enrollment.empty();
	$enrollment.append(simpleTable.getTag());
}

$(function() {
    $("#navi-students-link").addClass("active");
});
