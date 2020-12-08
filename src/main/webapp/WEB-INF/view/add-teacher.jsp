<!DOCTYPE HTML>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

	<title>Save Employee</title>
</head>

<body>

	<div class="container">
	
		<h3>Teachers directory</h3>
		<hr>
		
		<p class="h4 mb-4">Create Teacher!</p>
	
		<form action="#" th:action="@{/teachers/create}"
						 th:object="${teacher}" method="POST">
		
			<input type="hidden" th:field="*{id}" />
			<input type="hidden" th:field="*{personalNumber}" />
					
			<input type="text" th:field="*{firstName}"
					class="form-control mb-4 col-4" placeholder="First name">

			<input type="text" th:field="*{lastName}"
					class="form-control mb-4 col-4" placeholder="Last name">

			<input type="text" th:field="*{birthday}"
					class="form-control mb-4 col-4" placeholder="Birthday">
					
			<input type="text" th:field="*{email}"
					class="form-control mb-4 col-4" placeholder="Email">
					
		 	<input type="text" th:field="*{gender}"
					class="form-control mb-4 col-4" placeholder="Gender">
					
			<input type="text" th:field="*{address}"
					class="form-control mb-4 col-4" placeholder="Address">
			<ul>
				<li th:each="course: ${allCourses}">
					<div class="ul chekbox">
						<input type="checkbox" name="course" th:field="*{courses}" th:value="${course.id}">
						<label th:text="${course.name}"></label>
					</div>
				</li>
			</ul>
				
			<button type="submit" class="btn btn-info col-2">Save</button>
						
		</form>
	
		<hr>
		<a th:href="@{/teachers/showAllTeachers}">Back to teachers List</a>
		
	</div>
</body>

</html>