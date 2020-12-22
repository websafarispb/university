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

<title>Lectures</title>
</head>
<body>
	<h1>Lectures</h1>
	<hr>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

	Welcome to "Lecture" page.
	<div id="container">
		<div th:if="${not #lists.isEmpty(lecturesForShow)}">
		<a th:href="@{/}" class="btn btn-info btn-sm mb-3">Back to
				menu</a>
			<table class="table table-striped">
				<thead class="thead-dark">
					<tr>
						<th>Id</th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/(sortedParam=${'Time'})}" >Time</a></th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/(sortedParam=${'Course'})}" >Course</a></th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/(sortedParam=${'Classroom'})}" >Classroom</a></th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/(sortedParam=${'Group'})}" >Group</a></th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/(sortedParam=${'Teacher'})}" >Teacher</a></th>
						<th>Actions</th>
					</tr>
				</thead>
				<tr th:each="lecture : ${lecturesForShow}">
					<td th:text="${lecture.id}"></td>
					<td th:text="${lecture.time}"></td>
					<td th:text="${lecture.course.name}"></td>
					<td th:text="${lecture.classRoom.address}"></td>
					<td th:text="${lecture.group.name}"></td>
					<td th:text="${lecture.teacher.lastName}"></td>
					<td><a th:href="@{/lectures/{lectureId}/(lectureId=${lecture.id})}"
							class="btn btn-info">Show</a>
					</td>
				</tr>
			</table>
					<nav aria-label="..." th:object="${paginator}">
				<ul class="pagination  justify-content-center">
					<li class="page-item" th:classappend="*{(currentPage <=1 ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/lectures/(currentPage=*{currentPage} - 1, sortedParam=*{sortedParam})}">&laquo;</a></li>
					<li class="page-item"  th:classappend="*{(currentPage == currentPage ? 'active' : '' )}">
						<a class="page-link" th:text="*{currentPage}"  th:href="@{/lectures/(currentPage=*{currentPage}, sortedParam=*{sortedParam})}" ></a></li>
					<li class="page-item"  th:classappend="*{(currentPage == numberOfPages ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/lectures/(currentPage=*{currentPage} + 1, sortedParam=*{sortedParam})}">&raquo;</a>
					</li>
					 <li class="page-item">
					 	<form action="#" method="get" th:action="@{/lectures/}"
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