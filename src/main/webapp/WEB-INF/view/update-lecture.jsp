<!DOCTYPE HTML>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

	<title>Save lecture</title>
</head>

<body>

	<div class="container">


		<h3>Lecture directory</h3>
		<hr>

		<p class="h4 mb-4">Update lecture</p>

		<form action="#" th:action="@{/lectures/save}"
			th:object="${lecture}" method="POST">


			<input type="hidden" th:field="*{id}" />
			<input type="hidden" th:field="*{dailyScheduleId}" />
			
			<input type="text" th:name="date" th:field="${dailySchedule.date}"
				class="form-control mb-4 col-4" placeholder="Date YYYY-MM-DD">
				
			<input type="text" th:field="*{time}" class="form-control mb-4 col-4"
				placeholder="Time - HH:MM">
				
			<label>Select course</label>
			<select th:name="courseId">
				<option th:each="course : ${allCourses}" th:value="${course.id}"
					th:utext="${course.name}" th:selected="${course.name} == *{course.name}"/>
			</select>
			
			<label>Select classroom</label>
			<select th:name="classroomId">
				<option th:each="classroom : ${allClassrooms}"
					th:value="${classroom.id}" th:utext="${classroom.address}"  th:selected="${classroom.address} == *{classRoom.address}"/>
			</select>
			<br>
			<label>Select group</label>
			<select th:name="groupId">
				<option  th:each="group : ${allGroups}" th:value="${group.id}"
					th:utext="${group.name}" th:selected="${group.id} == *{group.id}" />
			</select>
			
			<label>Select teacher</label>
			<select th:name="teacherId">
				<option th:each="teacher : ${allTeachers}" th:value="${teacher.id}"
					th:utext="${teacher.firstName + ' ' + teacher.lastName}"  th:selected="${teacher.id} == *{teacher.id}"/>
			</select>
			<br>
			<button type="submit" class="btn btn-info col-2">Save</button>

			<hr>
			<a th:href="@{/lectures}">Back to lectures list</a>

		</form>
	</div>
</body>

</html>
