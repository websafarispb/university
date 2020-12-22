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
							<th>Id</th>
							<th><a class="btn btn-secondary" th:href="@{/students/(sortedParam=${'First_name'})}" >First name</a></th>
							<th><a class="btn btn-secondary" th:href="@{/students/(sortedParam=${'Last_name'})}" >Last name</a></th>
							<th><a class="btn btn-secondary" th:href="@{/students/(sortedParam=${'Birthday'})}" >Birthday</a></th>
							<th><a class="btn btn-secondary" th:href="@{/students/(sortedParam=${'Email'})}" >Email</a></th>
							<th><a class="btn btn-secondary" th:href="@{/students/(sortedParam=${'Address'})}" >Address</a></th>
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
						<td><a th:href="@{/students/{studentId}/(studentId=${student.id})}"
							class="btn btn-info">Show</a>
					   </td>
					</tr>
				</table>
			</div>
			<nav aria-label="..." th:object="${paginator}">
				<ul class="pagination  justify-content-center">
					<li class="page-item" th:classappend="*{(currentPage <=1 ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/students/(currentPage=*{currentPage} - 1, sortedParam=*{sortedParam})}">&laquo;</a></li>
					<li class="page-item"  th:classappend="*{(currentPage == currentPage ? 'active' : '' )}">
						<a class="page-link" th:text="*{currentPage}"  th:href="@{/students/(currentPage=*{currentPage}, sortedParam=*{sortedParam})}" ></a></li>
					<li class="page-item"  th:classappend="*{(currentPage == numberOfPages ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/students/(currentPage=*{currentPage} + 1, sortedParam=*{sortedParam})}">&raquo;</a>
					</li>
					 <li class="page-item">
					 	<form action="#" method="get" th:action="@{/students/}"
							th:object="${paginator}">
							<div class="input-group">
								<input class="w-25 p-1" type="text" th:field="*{currentPage}">
								<input type="hidden" th:field="*{sortedParam}">
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