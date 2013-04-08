<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script type="text/javascript" src="<c:url value="/static/js/course_edit_view.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/static/js/classoffering_compose_view.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/static/js/date_input.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/static/js/location_add_view.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/static/js/student_compose_view.js"/>" ></script>
    <script type="text/javascript" src="<c:url value="/static/js/students.js" />" ></script>
    <script type="text/javascript" src="<c:url value="/static/js/student_search_view.js" />" ></script>
    <title></title>
</head>
<body>
<div class="bs-docs-example">
		<ul id="myTab" class="nav nav-tabs">
			<li class="active"><a href="#students" data-toggle="tab">Students</a></li>
			<li class=""><a href="#enrollment" data-toggle="tab">Enrollment</a></li>
		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade active in" id="students">
				<div class="pull-right">
					<button type="button" class="btn" id="btnAddStudent"><i class="icon-plus"></i> Add</button>
				</div>
				<div id='student_content' class='content_table_div'></div>
			</div>
			
			<div class="tab-pane fade" id="enrollment">
				<form class="form-horizontal">
					<div class="control-group">
						<label class="control-label" for="selStudent">Student: </label>
						<div class="controls">
							<select id="selStudent">
							</select>
							<button type="button" class="btn" id="btnSearch"><i class="icon-search"></i> Search</button>
						</div>
					</div>
				</form>
				<div id='enrollment_content' class='content_table_div'></div>
			</div>
			
		</div>
	</div>
</body>
</html>