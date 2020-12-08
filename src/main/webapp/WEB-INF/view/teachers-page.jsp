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

<title>Teachers</title>
</head>
<body>
	<h1>Teachers</h1>
	<hr>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

	Welcome to "Teachers" page.
	<div id="container">
		<div th:if="${not #lists.isEmpty(teachers)}">
			<a th:href="@{/}" class="btn btn-primary btn-sm mb-3">Back to
				Menu</a> <a th:href="@{add}" class="btn btn-primary btn-sm mb-3">
				Add Teacher </a>
			<table class="table table-striped">
				<thead class="thead-dark">
					<tr>
						<th>Id</th>
						<th>First name</th>
						<th>Last name</th>
						<th>Birthday</th>
						<th>Email</th>
						<th>Address</th>
						<th>Action</th>
					</tr>
				</thead>
				<tr th:each="teacher: ${teachers}">
					<td th:text="${teacher.id}" />
					<td th:text="${teacher.firstName}" />
					<td th:text="${teacher.lastName}" />
					<td th:text="${teacher.birthday}" />
					<td th:text="${teacher.email}" />
					<td th:text="${teacher.address}" />
					<td><a th:href="@{update/(teacherId=${teacher.id})}"
							class="btn btn-primary">Edit</a> <a
							th:href="@{delete/(teacherId=${teacher.id})}"
							class="btn btn-danger" onclick="if (!(confirm('Are you sure you want to delete this teacher?'))) return false">Delete</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>