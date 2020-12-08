<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">

<title>Schedule</title>
</head>
<body>
	<h1>Schedule</h1>
	<hr>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

	Welcome to "Schedule" page.
	<div id="container">
		<div th:if="${not #lists.isEmpty(schedules)}">
			<table class="table table-striped">
				<thead class="thead-dark">
					<tr>
						<th>Date</th>
						<th>Time</th>
						<th>Course</th>
						<th>Classroom</th>
						<th>Group</th>
						<th>Teacher</th>
					</tr>
				</thead>
				<tbody th:each="tempSchedule: ${schedules}">
					
						
	
					<tr th:each="lecture : ${tempSchedule.lectures}">
					<td th:text="${tempSchedule.date}" ></td>
						<td th:text="${lecture.time}"></td>
						<td th:text="${lecture.course.name}"></td>
						<td th:text="${lecture.classRoom.address}"></td>
						<td th:text="${lecture.group.name}"></td>
						<td th:text="${lecture.teacher.lastName}"></td>
					</tr>
				</tbody>
			</table>
			<a th:href="@{/}" class="btn btn-primary">Back to
				Menu</a>
		</div>
	</div>
</body>
</html>