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

<title>Schedule for student</title>
</head>
<body>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

	<div class="container">
		<h3>Schedule for student</h3>
		<form th:action="@{/showScheduleForStudent}" method="post">
			<div class="form-group">
				<label>Select student</label>
				<div>
				<select th:name="studentId">
					<option  th:each="student : ${allStudents}"
						th:value="${student.id}" th:utext="${student.firstName} + ' ' +  ${student.lastName}"  />
				</select>
				</div>
			</div>
			<div class="form-group">
				<label>Select first date</label>
			
				<div>
					<select th:name="firstDate">
						<option  th:each="dailySchedule : ${dailySchedules}"
							th:value="${dailySchedule.date}" th:utext="${dailySchedule.date}" />
					</select>
				</div>
			</div>
			<div class="form-group">
			<label>Select last date</label>
					<div>
				<select th:name="lastDate">
					<option  th:each="dailySchedule : ${dailySchedules}"
						th:value="${dailySchedule.date}"  th:utext="${dailySchedule.date}" />
				</select>
				</div>
			</div>
			<a th:href="@{/}" class="btn btn-primary">Back to
				Menu</a>
			<button type="submit" class="btn btn-primary">Show</button>
		</form>
	</div>

</body>
</html>