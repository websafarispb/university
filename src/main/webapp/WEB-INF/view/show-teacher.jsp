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
		<p class="h4 mb-4">Teacher</p>
		<a th:href="@{/teachers/showAllTeachers}" class="btn btn-info btn-sm mb-3">Back to
					teachers list</a> 
	
		<form action="#" th:object="${teacher}" >
			<table id="selectedColumn" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
				<tr>
					<td>Id</td>
					<td  th:text="*{id}"></td>
				</tr>
				<tr>
					<td>First name</td>
					<td th:text="*{firstName}"></td>
				</tr>
				<tr>
					<td>Last name</td>
					<td  th:text="*{lastName}"></td>
				</tr>
				<tr>
					<td>Date of birth</td>				
					<td  th:text="*{birthday}"></td>
				</tr>
				<tr>
					<td>Email</td>
					<td  th:text="*{email}"></td>
				</tr>
				<tr>
					<td>Gender</td>
					<td  th:text="*{gender}"></td>
				</tr>
				<tr>
					<td>Address</td>
					<td  th:text="*{address}"></td>
				</tr>
				
					<tr>
						<td>Courses</td>
						<td>
							<ul>
								<li th:each="course: *{courses}"><label
									th:text="${course.name}"></label></li>
							</ul>
						</td>
					</tr>
				</table>					
		</form>
		<hr>
	</div>
	</div>

</body>
</html>