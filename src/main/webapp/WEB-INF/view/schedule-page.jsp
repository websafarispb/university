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

<title>Schedule</title>
</head>
<body>
	<h1>Schedule</h1>
	<hr>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
		crossorigin="anonymous"></script>

	Welcome to "Schedule" page.
	<div id="container">
		<div th:if="${not #lists.isEmpty(dailySchedulesForShow)}">
		<a th:href="@{/}" class="btn btn-info btn-sm mb-3">Back to
					Menu</a>
			<table class="table table-striped">
				<thead class="thead-dark">
					<tr>
						<th><a class="btn btn-secondary" th:href="@{/dailySchedules/showAllDailySchedules/(sortedParam=${'Id'})}" >Id</a></th>
						<th><a class="btn btn-secondary" th:href="@{/dailySchedules/showAllDailySchedules/(sortedParam=${'Date'})}" >Date</a></th>
						<th>Time</th>
						<th>Course</th>
						<th>Classroom</th>
						<th>Group</th>
						<th>Teacher</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody th:each="dailySchedule: ${dailySchedulesForShow}">
					
						
	
					<tr th:each="lecture : ${dailySchedule.lectures}">
						<td th:text="${dailySchedule.id}" ></td>
						<td th:text="${dailySchedule.date}" ></td>
						<td th:text="${lecture.time}"></td>
						<td th:text="${lecture.course.name}"></td>
						<td th:text="${lecture.classRoom.address}"></td>
						<td th:text="${lecture.group.name}"></td>
						<td th:text="${lecture.teacher.lastName}"></td>
						<td><a th:href="@{/dailySchedules/showEntity/(lectureId=${lecture.id})}"
							class="btn btn-info">Show</a>
					   </td>
					</tr>
				</tbody>
			</table>
			<nav aria-label="...">
				<ul class="pagination  justify-content-center">
					<li class="page-item" th:classappend="${(diapason < sizeOfDiapason ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/dailySchedules/showAllDailySchedules/(diapason = ${diapason - sizeOfDiapason}, currentPage=${diapason - sizeOfDiapason + 1}, sortedParam=${sortedParam})}">Previous</a></li>
					<li class="page-item" th:each="i : ${currentPageNumbers}" th:classappend="${(currentPage == i ? 'active' : '' )}">
						<a class="page-link" th:text="${i}"  th:href="@{/dailySchedules/showAllDailySchedules/(diapason = ${diapason}, currentPage=${i}, sortedParam=${sortedParam})}" ></a></li>
					<li class="page-item"  th:classappend="${(diapason >= numberOfPages-sizeOfDiapason ? 'disabled' : '' )}">
						<a class="page-link" th:href="@{/dailySchedules/showAllDailySchedules/(diapason = ${diapason + sizeOfDiapason}, currentPage=${diapason + sizeOfDiapason +  1}, sortedParam=${sortedParam})}">Next</a></li>
				</ul>
			</nav>
		</div>
	</div>
</body>
</html>