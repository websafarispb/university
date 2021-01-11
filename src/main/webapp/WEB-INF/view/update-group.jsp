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

<title>Update course</title>
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
		<form th:action="@{/groups/save}" th:object="${group}" method="post">
			<h1>Update group</h1>
			<div class="form-row">
				<div class="col-md-6 form-group">
					<input type="hidden" th:field="*{id}" /> <label>Group name</label>
					<input type="text" class="form-control" th:field="*{name}">
					<p class="h4 mb-4" align="center">Students</p>
					<ul>
						<li th:each="student: *{students}">
							<div class="ul chekbox">
								<input type="checkbox" name="student" th:field="*{students}"
									th:value="${student.id}" th:checked="${true}" > <label
									th:text="${student.firstName} +' ' + ${student.lastName}"></label>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div style="margin-top: 10px" class="form-group">
				<div class="col-sm-6 controls">
					<button type="submit" class="btn btn-success">Update</button>
				</div>
			</div>
		</form>
		<hr>
		<a th:href="@{/groups}">Back to groups list</a>
	</div>

</body>
</html>
