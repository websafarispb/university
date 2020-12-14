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
		<div th:if="${not #lists.isEmpty(teachersForShow)}">
			<a th:href="@{/}" class="btn btn-info btn-sm mb-3">Back to
				Menu</a>
			<table class="table table-striped">
				<thead class="thead-dark">
					<tr>
						<th><a class="btn btn-secondary" th:href="@{/teachers/showAllTeachers/(sortedParam=${'Id'})}">Id</a></th>
						<th><a class="btn btn-secondary" th:href="@{/teachers/showAllTeachers/(sortedParam=${'First_name'})}">First name</a></th>
						<th><a class="btn btn-secondary" th:href="@{/teachers/showAllTeachers/(sortedParam=${'Last_name'})}">Last name</a></th>
						<th><a class="btn btn-secondary" th:href="@{/teachers/showAllTeachers/(sortedParam=${'Birthday'})}">Birthday</a></th>
						<th><a class="btn btn-secondary" th:href="@{/teachers/showAllTeachers/(sortedParam=${'Email'})}">Email</a></th>
						<th><a class="btn btn-secondary" th:href="@{/teachers/showAllTeachers/(sortedParam=${'Address'})}">Address</a></th>
						<th>Action</th>
					</tr>
				</thead>
				<tr th:each="teacher: ${teachersForShow}">
					<td th:text="${teacher.id}" />
					<td th:text="${teacher.firstName}" />
					<td th:text="${teacher.lastName}" />
					<td th:text="${teacher.birthday}" />
					<td th:text="${teacher.email}" />
					<td th:text="${teacher.address}" />
					<td><a th:href="@{/teachers/showEntity/(teacherId=${teacher.id})}"
							class="btn btn-info">Show</a>
					</td>
				</tr>
			</table>
				<nav aria-label="...">
				<ul class="pagination  justify-content-center">
					<li class="page-item" th:classappend="${(diapason < sizeOfDiapason ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/teachers/showAllTeachers/(diapason = ${diapason - sizeOfDiapason}, currentPage=${diapason - sizeOfDiapason + 1}, sortedParam=${sortedParam})}">Previous</a></li>
					<li class="page-item" th:each="i : ${currentPageNumbers}" th:classappend="${(currentPage == i ? 'active' : '' )}">
						<a class="page-link" th:text="${i}"  th:href="@{/teachers/showAllTeachers/(diapason = ${diapason}, currentPage=${i}, sortedParam=${sortedParam})}" ></a></li>
					<li class="page-item"  th:classappend="${(diapason >= numberOfPages-sizeOfDiapason ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/teachers/showAllTeachers/(diapason = ${diapason + sizeOfDiapason}, currentPage=${diapason + sizeOfDiapason +  1}, sortedParam=${sortedParam})}">Next</a></li>
				</ul>
			</nav>
		</div>
	</div>
</body>
</html>