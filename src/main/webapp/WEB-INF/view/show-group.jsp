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

<title>Classrooms</title>
</head>
<body>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

		<div id="container">
		<div class="card">
		<hr>
		<p class="h4 mb-4">Group</p>
		<a th:href="@{/groups/showAllGroups}" class="btn btn-info btn-sm mb-3">Back to
					groups list</a> 
	
		<form action="#" th:object="${group}" >
			<table id="selectedColumn" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
				<tr>
					<td>Id</td>
					<td  th:text="*{id}"></td>
				</tr>
				<tr>
					<td>Name</td>
					<td  th:text="*{name}"></td>
				</tr>
			</table>
			<p class="h4 mb-4" align="center">Students</p>
			<table id="selectedColumn" class="table table-striped table-bordered table-sm">
				<tr th:each="student: ${group.students}">
					<td th:text="${student.id}" />
					<td th:text="${student.firstName}" />
					<td th:text="${student.lastName}" />
					<td><a th:href="@{/students/showEntity/(studentId=${student.id})}"
							class="btn btn-info">Show</a>
					   </td>
				</tr>
			</table>					
		</form>
	</div>
	</div>

</body>
</html>