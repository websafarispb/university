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

<title>Courses</title>
</head>
<body>
	<h1>Courses</h1>
	<hr>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

	Welcome to "Courses" page.
	<div id="container">
		<div th:if="${not #lists.isEmpty(courses)}">
		<a th:href="@{/}" class="btn btn-info btn-sm mb-3">Back to
					Menu</a>
			<table class="table table-striped">
			<thead class="thead-dark">
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Description</th>
					<th>Action</th>
				</tr>
				</thead>
				<tr th:each="course: ${courses}">
					<td th:text="${course.id}" />
					<td th:text="${course.name}" />
					<td th:text="${course.description}" />
					<td><a th:href="@{/courses/{courseId}/(courseId=${course.id})}"
							class="btn btn-info">Show</a>
					   </td>
				</tr>
			</table>
			<nav aria-label="..." th:object="${paginator}">
				<ul class="pagination  justify-content-center">
					<li class="page-item" th:classappend="*{(currentPage <=1 ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/courses/(currentPage=*{currentPage} - 1, sortBy=*{sortBy})}">&laquo;</a></li>
					<li class="page-item"  th:classappend="*{(currentPage == currentPage ? 'active' : '' )}">
						<a class="page-link" th:text="*{currentPage}"  th:href="@{/courses/(currentPage=*{currentPage}, sortBy=*{sortBy})}" ></a></li>
					<li class="page-item"  th:classappend="*{(currentPage == numberOfPages ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/courses/(currentPage=*{currentPage} + 1, sortBy=*{sortBy})}">&raquo;</a>
					</li>
					 <li class="page-item">
					 	<form action="#" method="get" th:action="@{/courses/}"
							th:object="${paginator}">
							<div class="input-group">
								<input class="w-25 p-1" type="text" th:field="*{currentPage}">
								<input type="hidden" th:field="*{sortBy}">
								<span class="input-group-text" th:utext="*{numberOfPages}" ></span>
							</div>
						</form>
					</li>		
				</ul>
			</nav>
		</div>
	</div>
</body>
</html>