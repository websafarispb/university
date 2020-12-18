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
						<th><a class="btn btn-secondary" th:href="@{/lectures/showAllLectures/(sortedParam=${'Id'})}" >Id</a></th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/showAllLectures/(sortedParam=${'Time'})}" >Time</a></th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/showAllLectures/(sortedParam=${'Course'})}" >Course</a></th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/showAllLectures/(sortedParam=${'Classroom'})}" >Classroom</a></th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/showAllLectures/(sortedParam=${'Group'})}" >Group</a></th>
						<th><a class="btn btn-secondary" th:href="@{/lectures/showAllLectures/(sortedParam=${'Teacher'})}" >Teacher</a></th>
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
					<td><a th:href="@{/lectures/showEntity/(lectureId=${lecture.id})}"
							class="btn btn-info">Show</a>
					</td>
				</tr>
			</table>
				<nav aria-label="...">
				<ul class="pagination  justify-content-center">
					<li class="page-item" th:classappend="${(currentBeginPagination < currentNumberOfPagesForPagination ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/lectures/showAllLectures/(currentBeginPagination = ${currentBeginPagination - currentNumberOfPagesForPagination}, currentPage=${currentBeginPagination - currentNumberOfPagesForPagination + 1}, sortedParam=${sortedParam})}">Previous</a></li>
					<li class="page-item" th:each="i : ${currentPageNumbers}" th:classappend="${(currentPage == i ? 'active' : '' )}">
						<a class="page-link" th:text="${i}"  th:href="@{/lectures/showAllLectures/(currentBeginPagination = ${currentBeginPagination}, currentPage=${i}, sortedParam=${sortedParam})}" ></a></li>
					<li class="page-item"  th:classappend="${(currentBeginPagination >= numberOfPages-currentNumberOfPagesForPagination ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/lectures/showAllLectures/(currentBeginPagination = ${currentBeginPagination + currentNumberOfPagesForPagination}, currentPage=${currentBeginPagination + currentNumberOfPagesForPagination + 1}, sortedParam=${sortedParam})}">Next</a></li>
				</ul>
			</nav>
		</div>
	</div>
</body>
</html>