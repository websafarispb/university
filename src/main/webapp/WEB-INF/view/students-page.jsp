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

<title>Students</title>
</head>
<body>
	<h1>Students</h1>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

	<div id="container">
		<div class="card">
			<div th:if="${not #lists.isEmpty(studentsForShow)}">

				<a th:href="@{/}" class="btn btn-info btn-sm mb-3">Back to
					Menu</a> 

				<table id="selectedColumn" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
					<thead class="thead-dark">
						<tr>
							<th><a class="btn btn-secondary" th:href="@{/students/showAllStudents/(sortedParam=${'Id'})}" >Id</a></th>
							<th><a class="btn btn-secondary" th:href="@{/students/showAllStudents/(sortedParam=${'First_name'})}" >First name</a></th>
							<th><a class="btn btn-secondary" th:href="@{/students/showAllStudents/(sortedParam=${'Last_name'})}" >Last name</a></th>
							<th><a class="btn btn-secondary" th:href="@{/students/showAllStudents/(sortedParam=${'Birthday'})}" >Birthday</a></th>
							<th><a class="btn btn-secondary" th:href="@{/students/showAllStudents/(sortedParam=${'Email'})}" >Email</a></th>
							<th><a class="btn btn-secondary" th:href="@{/students/showAllStudents/(sortedParam=${'Address'})}" >Address</a></th>
							<th>Action</th>
						</tr>
					</thead>
					<tr th:each="student: ${studentsForShow}">
						<td th:text="${student.id}" />
						<td th:text="${student.firstName}" />
						<td th:text="${student.lastName}" />
						<td th:text="${student.birthday}" />
						<td th:text="${student.email}" />
						<td th:text="${student.address}" />
						<td><a th:href="@{/students/showEntity/(studentId=${student.id})}"
							class="btn btn-info">Show</a>
					   </td>
					</tr>
				</table>
			</div>

			<nav aria-label="...">
				<ul class="pagination  justify-content-center">
					<li class="page-item" th:classappend="${(currentBeginPagination < currentNumberOfPagesForPagination ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/students/showAllStudents/(currentBeginPagination = ${currentBeginPagination - currentNumberOfPagesForPagination}, currentPage=${currentBeginPagination - currentNumberOfPagesForPagination + 1}, sortedParam=${sortedParam})}">Previous</a></li>
					<li class="page-item" th:each="i : ${currentPageNumbers}" th:classappend="${(currentPage == i ? 'active' : '' )}">
						<a class="page-link" th:text="${i}"  th:href="@{/students/showAllStudents/(currentBeginPagination = ${currentBeginPagination}, currentPage=${i}, sortedParam=${sortedParam})}" ></a></li>
					<li class="page-item"  th:classappend="${(currentBeginPagination >= numberOfPages-currentNumberOfPagesForPagination ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/students/showAllStudents/(currentBeginPagination = ${currentBeginPagination + currentNumberOfPagesForPagination}, currentPage=${currentBeginPagination + currentNumberOfPagesForPagination + 1}, sortedParam=${sortedParam})}">Next</a></li>
				</ul>
			</nav>
		</div>
	</div>
</body>
</html>