package ru.stepev.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Gender;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

public interface DataTest {

	List<Classroom> expectedClassrooms = Arrays.asList(Classroom.builder().id(1).address("101").capacity(50).build(),
			Classroom.builder().id(2).address("102").capacity(40).build(),
			Classroom.builder().id(3).address("103").capacity(30).build(),
			Classroom.builder().id(4).address("104").capacity(20).build());

	Classroom classroomForCreate = Classroom.builder().id(0).address("105").capacity(10).build();
	Classroom classroomForTest = Classroom.builder().id(5).address("105").capacity(10).build();
	Classroom classroomForDelete = Classroom.builder().id(2).address("102").capacity(40).build();
	Classroom classroomForUpdate = Classroom.builder().id(3).address("303").capacity(400).build();
	Classroom classroomSmall = Classroom.builder().id(4).address("503").capacity(2).build();

	List<Course> expectedCourses = Arrays.asList(Course.builder().id(1).name("Mathematics").description("Math").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(3).name("Chemistry").description("Chem").build(),
			Course.builder().id(4).name("Physics").description("Phy").build());

	List<Course> coursesForTeacher = Arrays.asList(
			Course.builder().id(1).name("Mathematics").description("Math").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(3).name("Chemistry").description("Chem").build(),
			Course.builder().id(4).name("Physics").description("Phy").build(),
			Course.builder().id(5).name("Geography").description("Geo").build());

	Course courseForCreate = Course.builder().id(0).name("Geography").description("Geo").build();
	Course courseForTest = Course.builder().id(5).name("Geography").description("Geo").build();
	Course specialCourse = Course.builder().id(6).name("Informatica").description("Inf").build();

	List<Teacher> expectedTeachers = Arrays.asList(
			Teacher.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(2).personalNumber(124).firstName("Victoria").lastName("Semenova")
					.birthday(LocalDate.of(2020, 9, 1)).email("Semenova@mail.ru").gender(Gender.FEMALE)
					.address("City10").courses(coursesForTeacher).build(),
			Teacher.builder().id(3).personalNumber(125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("webPI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(5).personalNumber(227).firstName("Irina").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanova@mail.ru").gender(Gender.FEMALE)
					.address("City11").courses(coursesForTeacher).build(),
			Teacher.builder().id(6).personalNumber(228).firstName("Victoria").lastName("Semenova")
					.birthday(LocalDate.of(2020, 9, 1)).email("webPP@mail.ru").gender(Gender.FEMALE).address("City10")
					.courses(coursesForTeacher).build());
	Teacher teacherForTest = Teacher.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
			.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.FEMALE).address("City17")
			.courses(expectedCourses).build();
	Teacher specialTeacher = Teacher.builder().id(5).personalNumber(227).firstName("Irina").lastName("Stepanova")
			.birthday(LocalDate.of(2020, 9, 7)).email("Stepanova@mail.ru").gender(Gender.FEMALE).address("City11")
			.courses(expectedCourses.subList(0, 1)).build();

	List<Student> expectedStudents = Arrays.asList(
			Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCourses).build(),
			Student.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCourses).build(),
			Student.builder().id(3).personalNumber(125).firstName("Ivan").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 1)).email("Stepanov@mail.ru").gender(Gender.MALE).address("City11")
					.courses(expectedCourses).build(),
			Student.builder().id(4).personalNumber(126).firstName("Peter").lastName("Smirnov")
					.birthday(LocalDate.of(2020, 9, 6)).email("webPS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCourses).build(),
			Student.builder().id(5).personalNumber(227).firstName("Irina").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCourses).build(),
			Student.builder().id(6).personalNumber(527).firstName("Daria").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova@mail.ru").gender(Gender.FEMALE).address("City20")
					.courses(expectedCourses).build(),
			Student.builder().id(7).personalNumber(528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov@mail.ru").gender(Gender.MALE).address("City20")
					.courses(expectedCourses).build());
	List<Student> sillyStudents = Arrays.asList(
			Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCourses.subList(0, 1)).build(),
			Student.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCourses.subList(0, 1)).build());

	Student studentForTest = Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
			.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
			.courses(expectedCourses).build();
	Student smartStudent = Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
			.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
			.courses(coursesForTeacher).build();

	List<Group> expectedGroups = Arrays.asList(
			Group.builder().id(1).name("a2a2").students(expectedStudents.subList(0, 1)).build(),
			Group.builder().id(2).name("b2b2").students(expectedStudents.subList(2, 3)).build(),
			Group.builder().id(3).name("c2c2").students(expectedStudents.subList(4, 5)).build(),
			Group.builder().id(4).name("d2d2").students(expectedStudents.subList(6, 6)).build());
	
	Group bigGroup = Group.builder().id(5).name("c2c2").students(expectedStudents).build();
	Group sillyGroup = Group.builder().id(1).name("a2a2").students(sillyStudents).build();
	Group groupForTest = Group.builder().id(1).name("a2a2").students(expectedStudents.subList(0, 3)).build();
	Group groupForCreate = Group.builder().id(0).name("a2a2").students(expectedStudents.subList(0, 3)).build();

	List<Lecture> expectedLectures = Arrays.asList(
			Lecture.builder().id(1).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(0)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(2).dailyScheduleId(1).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(0)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(3).dailyScheduleId(2).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(2))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(0)).teacher(expectedTeachers.get(2))
					.build(),
			Lecture.builder().id(4).dailyScheduleId(2).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(0)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(5).dailyScheduleId(3).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(6).dailyScheduleId(3).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(expectedTeachers.get(2))
					.build(),
			Lecture.builder().id(7).dailyScheduleId(4).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(2))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(8).dailyScheduleId(4).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(3)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(8).dailyScheduleId(5).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(2))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(9).dailyScheduleId(5).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(3)).teacher(expectedTeachers.get(1))
					.build());

	Lecture correstLectureForTest = Lecture.builder().id(9).dailyScheduleId(5).time(LocalTime.of(10, 0, 0))
			.course(expectedCourses.get(3)).classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1))
			.teacher(expectedTeachers.get(0)).build();
	Lecture wrongLectureForTest = Lecture.builder().id(9).dailyScheduleId(5).time(LocalTime.of(9, 59, 59))
			.course(expectedCourses.get(3)).classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(3))
			.teacher(expectedTeachers.get(1)).build();
	Lecture lectureWithAvaliableGroup = Lecture.builder().id(9).dailyScheduleId(5).time(LocalTime.of(10, 0, 0))
			.course(expectedCourses.get(3)).classRoom(expectedClassrooms.get(2)).group(expectedGroups.get(1))
			.teacher(expectedTeachers.get(3)).build();
	Lecture lectureWithNotAvaliableGroup = Lecture.builder().id(9).dailyScheduleId(5).time(LocalTime.of(9, 59, 59))
			.course(expectedCourses.get(3)).classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(3))
			.teacher(expectedTeachers.get(3)).build();
	Lecture lectureWithAvaliableClassroom = Lecture.builder().id(9).dailyScheduleId(5).time(LocalTime.of(10, 0, 0))
			.course(expectedCourses.get(3)).classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1))
			.teacher(expectedTeachers.get(3)).build();
	Lecture lectureWithNotAvaliableClassroom = Lecture.builder().id(9).dailyScheduleId(5).time(LocalTime.of(9, 59, 59))
			.course(expectedCourses.get(3)).classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1))
			.teacher(expectedTeachers.get(3)).build();
	Lecture lectureWithSmallClassroom = Lecture.builder().id(9).dailyScheduleId(5).time(LocalTime.of(9, 59, 59))
			.course(expectedCourses.get(3)).classRoom(classroomSmall).group(bigGroup)
			.teacher(expectedTeachers.get(3)).build();

	List<DailySchedule> expectedDailySchedules = Arrays.asList(
			DailySchedule.builder().id(1).date(LocalDate.of(2020, 9, 7)).lectures(expectedLectures.subList(0, 1))
					.build(),
			DailySchedule.builder().id(2).date(LocalDate.of(2020, 9, 8)).lectures(expectedLectures.subList(2, 3))
					.build(),
			DailySchedule.builder().id(3).date(LocalDate.of(2020, 9, 9)).lectures(expectedLectures.subList(4, 5))
					.build(),
			DailySchedule.builder().id(4).date(LocalDate.of(2020, 9, 10)).lectures(expectedLectures.subList(6, 7))
					.build(),
			DailySchedule.builder().id(5).date(LocalDate.of(2020, 9, 11)).lectures(expectedLectures.subList(8, 9))
					.build());

	DailySchedule dailyScheduleForCreate = DailySchedule.builder().id(5).date(LocalDate.of(2020, 9, 11))
			.lectures(expectedLectures.subList(8, 9)).build();
}
