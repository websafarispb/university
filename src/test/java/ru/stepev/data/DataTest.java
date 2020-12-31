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

	List<Integer> defaultCurrentPageNumbers = Arrays.asList(1, 2, 3);
	List<Integer> currentPageNumbers = Arrays.asList(4);

	List<Classroom> expectedClassrooms = Arrays.asList(Classroom.builder().id(1).address("101").capacity(50).build(),
			Classroom.builder().id(2).address("102").capacity(40).build(),
			Classroom.builder().id(4).address("104").capacity(20).build(),
			Classroom.builder().id(5).address("105").capacity(50).build(),
			Classroom.builder().id(6).address("106").capacity(40).build(),
			Classroom.builder().id(7).address("203").capacity(30).build(),
			Classroom.builder().id(8).address("204").capacity(23).build(),
			Classroom.builder().id(9).address("201").capacity(50).build(),
			Classroom.builder().id(10).address("202").capacity(40).build(),
			Classroom.builder().id(11).address("303").capacity(30).build(),
			Classroom.builder().id(12).address("304").capacity(20).build(),
			Classroom.builder().id(13).address("301").capacity(55).build(),
			Classroom.builder().id(14).address("402").capacity(40).build(),
			Classroom.builder().id(15).address("403").capacity(30).build(),
			Classroom.builder().id(16).address("504").capacity(20).build(),
			Classroom.builder().id(17).address("501").capacity(50).build(),
			Classroom.builder().id(18).address("602").capacity(40).build(),
			Classroom.builder().id(19).address("703").capacity(30).build(),
			Classroom.builder().id(20).address("704").capacity(60).build(),
			Classroom.builder().id(21).address("705").capacity(10).build());
	List<Classroom> expectedNotUpdatedClassrooms = Arrays.asList(
			Classroom.builder().id(1).address("101").capacity(50).build(),
			Classroom.builder().id(2).address("102").capacity(40).build(),
			Classroom.builder().id(3).address("103").capacity(30).build(),
			Classroom.builder().id(4).address("104").capacity(20).build(),
			Classroom.builder().id(5).address("105").capacity(50).build(),
			Classroom.builder().id(6).address("106").capacity(40).build(),
			Classroom.builder().id(7).address("203").capacity(30).build(),
			Classroom.builder().id(8).address("204").capacity(23).build(),
			Classroom.builder().id(9).address("201").capacity(50).build(),
			Classroom.builder().id(10).address("202").capacity(40).build(),
			Classroom.builder().id(11).address("303").capacity(30).build(),
			Classroom.builder().id(12).address("304").capacity(20).build(),
			Classroom.builder().id(13).address("301").capacity(55).build(),
			Classroom.builder().id(14).address("402").capacity(40).build(),
			Classroom.builder().id(15).address("403").capacity(30).build(),
			Classroom.builder().id(16).address("504").capacity(20).build(),
			Classroom.builder().id(17).address("501").capacity(50).build(),
			Classroom.builder().id(18).address("602").capacity(40).build(),
			Classroom.builder().id(19).address("703").capacity(30).build(),
			Classroom.builder().id(20).address("704").capacity(60).build());
	List<Classroom> expectedSortedClassroomsByCapacity = Arrays.asList(
			Classroom.builder().id(7).address("203").capacity(30).build(),
			Classroom.builder().id(11).address("303").capacity(30).build(),
			Classroom.builder().id(15).address("403").capacity(30).build(),
			Classroom.builder().id(19).address("703").capacity(30).build(),
			Classroom.builder().id(2).address("102").capacity(40).build());
	List<Classroom> expectedClassroomsSortedByAddress = Arrays.asList(
			Classroom.builder().id(1).address("101").capacity(50).build(),
			Classroom.builder().id(2).address("102").capacity(40).build(),
			Classroom.builder().id(3).address("103").capacity(30).build(),
			Classroom.builder().id(4).address("104").capacity(20).build(),
			Classroom.builder().id(5).address("105").capacity(50).build());

	Classroom classroomForCreate = Classroom.builder().id(0).address("105").capacity(10).build();
	Classroom classroomForTest = Classroom.builder().id(5).address("105").capacity(10).build();
	Classroom classroomForDelete = Classroom.builder().id(2).address("102").capacity(40).build();
	Classroom classroomForUpdate = Classroom.builder().id(3).address("303").capacity(400).build();
	Classroom classroomSortedTest = Classroom.builder().id(3).address("103").capacity(30).build();
	Classroom classroomSmall = Classroom.builder().id(4).address("503").capacity(2).build();
	Classroom expectedClassroom = Classroom.builder().id(1).address("101").capacity(50).build();

	List<Course> expectedSortedByNameCourses = Arrays.asList(
			Course.builder().id(12).name("Artificial intelligence").description("All about Artificial intelligence")
					.build(),
			Course.builder().id(7).name("Astronomy").description("All about astronomy").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(3).name("Chemistry").description("Chem").build(),
			Course.builder().id(13).name("Computer architecture and organisation")
					.description("All about computer architecture and organisation").build());

	List<Course> expectedSortedByIdCourses = Arrays.asList(
			Course.builder().id(1).name("Mathematics").description("Math").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(3).name("Chemistry").description("Chem").build(),
			Course.builder().id(4).name("History").description("History description").build(),
			Course.builder().id(5).name("Informatica").description("Info").build());

	List<Course> expectedCourses = Arrays.asList(Course.builder().id(1).name("Mathematics").description("Math").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(3).name("Chemistry").description("Chem").build(),
			Course.builder().id(4).name("Physics").description("Phy").build(),
			Course.builder().id(5).name("Informatica").description("Info").build(),
			Course.builder().id(6).name("Philosophy").description("All about philosophy").build(),
			Course.builder().id(7).name("Astronomy").description("All about astronomy").build(),
			Course.builder().id(8).name("Geography").description("All about Geography").build(),
			Course.builder().id(9).name("Physical education").description("All about physical education").build(),
			Course.builder().id(10).name("History").description("All about history").build(),
			Course.builder().id(11).name("Data structures and algorithms")
					.description("All about Data structures and algorithms").build(),
			Course.builder().id(12).name("Artificial intelligence").description("All about Artificial intelligence")
					.build(),
			Course.builder().id(13).name("Computer architecture and organisation")
					.description("All about computer architecture and organisation").build(),
			Course.builder().id(14).name("Computer networks")
					.description("This branch of computer science aims to manage networks between computers worldwide")
					.build(),
			Course.builder().id(15).name("Databases and data mining").description("All about Databases and data mining")
					.build(),
			Course.builder().id(16).name("Computer graphics and visualization").description(
					"Computer graphics is the study of digital visual contents and involves the synthesis and manipulation of image data. The study is connected to many other fields in computer science, including computer vision, image processing, and computational geometry, and is heavily applied in the fields of special effects and video games.")
					.build(),
			Course.builder().id(17).name("Geography").description("Geo").build());

	List<Course> expectedCoursesForStudents = Arrays.asList(
			Course.builder().id(1).name("Mathematics").description("Math").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(3).name("Chemistry").description("Chem").build(),
			Course.builder().id(4).name("Physics").description("Phy").build(),
			Course.builder().id(5).name("Informatica").description("Info").build());

	List<Course> expectedCoursesAfterDeleteOne = Arrays.asList(
			Course.builder().id(1).name("Mathematics").description("Math").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(4).name("History").description("History description").build(),
			Course.builder().id(5).name("Informatica").description("Info").build(),
			Course.builder().id(6).name("Philosophy").description("All about philosophy").build(),
			Course.builder().id(7).name("Astronomy").description("All about astronomy").build(),
			Course.builder().id(8).name("Geography").description("All about Geography").build(),
			Course.builder().id(9).name("Physical education").description("All about physical education").build(),
			Course.builder().id(10).name("History").description("All about history").build(),
			Course.builder().id(11).name("Data structures and algorithms")
					.description("All about Data structures and algorithms").build(),
			Course.builder().id(12).name("Artificial intelligence").description("All about Artificial intelligence")
					.build(),
			Course.builder().id(13).name("Computer architecture and organisation")
					.description("All about computer architecture and organisation").build(),
			Course.builder().id(14).name("Computer networks")
					.description("This branch of computer science aims to manage networks between computers worldwide")
					.build(),
			Course.builder().id(15).name("Databases and data mining").description("All about Databases and data mining")
					.build(),
			Course.builder().id(16).name("Computer graphics and visualization").description(
					"Computer graphics is the study of digital visual contents and involves the synthesis and manipulation of image data. The study is connected to many other fields in computer science, including computer vision, image processing, and computational geometry, and is heavily applied in the fields of special effects and video games.")
					.build(),
			Course.builder().id(17).name("Geography").description("Geo").build());

	List<Course> expectedSortedCoursesByName = Arrays.asList(
			Course.builder().id(12).name("Artificial intelligence").description("All about Artificial intelligence")
					.build(),
			Course.builder().id(7).name("Astronomy").description("All about astronomy").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(3).name("Chemistry").description("Chem").build(),
			Course.builder().id(13).name("Computer architecture and organisation")
					.description("All about computer architecture and organisation").build());

	List<Course> coursesForTeacher = Arrays.asList(
			Course.builder().id(1).name("Mathematics").description("Math").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(3).name("Chemistry").description("Chem").build(),
			Course.builder().id(4).name("Physics").description("Phy").build());

	List<Course> notExistedCourses = Arrays.asList(
			Course.builder().id(1).name("Mathematics").description("Math").build(),
			Course.builder().id(2).name("Biology").description("Bio").build(),
			Course.builder().id(3).name("Chemistry").description("Chem").build(),
			Course.builder().id(4).name("Physics").description("Phy").build(),
			Course.builder().id(400).name("Anatomy").description("All about Anatomy").build());

	Course courseForCreate = Course.builder().id(0).name("Geography").description("Geo").build();
	Course courseForTest = Course.builder().id(5).name("Geography").description("Geo").build();
	Course specialCourse = Course.builder().id(6).name("Informatica").description("Inf").build();
	Course expectedCourse = Course.builder().id(1).name("Mathematics").description("Math").build();

	List<Teacher> expectedTeachers = Arrays.asList(
			Teacher.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(2).personalNumber(124).firstName("Victoria").lastName("Semenova")
					.birthday(LocalDate.of(2020, 9, 1)).email("Semenova@mail.ru").gender(Gender.FEMALE)
					.address("City10").courses(expectedCourses.subList(2, 4)).build(),
			Teacher.builder().id(3).personalNumber(125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("webPI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(5).personalNumber(227).firstName("Irina").lastName("Antonova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Antonova@mail.ru").gender(Gender.FEMALE)
					.address("City11").courses(coursesForTeacher).build(),
			Teacher.builder().id(6).personalNumber(228).firstName("Olga").lastName("Voronina")
					.birthday(LocalDate.of(2019, 9, 7)).email("Voronina@mail.ru").gender(Gender.FEMALE).address("City1")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(7).personalNumber(229).firstName("Svetlana").lastName("Ulianova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ulianova@mail.ru").gender(Gender.FEMALE)
					.address("City112").courses(coursesForTeacher).build(),
			Teacher.builder().id(8).personalNumber(230).firstName("Ksenia").lastName("Gromova")
					.birthday(LocalDate.of(2018, 9, 7)).email("Gromova@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(9).personalNumber(231).firstName("Oksana").lastName("Petrova")
					.birthday(LocalDate.of(2020, 4, 7)).email("PetrovaI@mail.ru").gender(Gender.FEMALE)
					.address("City14").courses(coursesForTeacher).build(),
			Teacher.builder().id(10).personalNumber(232).firstName("Viktor").lastName("Yakovlev")
					.birthday(LocalDate.of(2001, 1, 1)).email("Yakovlev@mail.ru").gender(Gender.MALE).address("City11")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(11).personalNumber(233).firstName("Irina").lastName("Fedorova")
					.birthday(LocalDate.of(2012, 9, 7)).email("Fedorova@mail.ru").gender(Gender.FEMALE)
					.address("City15").courses(coursesForTeacher).build(),
			Teacher.builder().id(12).personalNumber(428).firstName("Victoria").lastName("Semenova")
					.birthday(LocalDate.of(2020, 9, 1)).email("Semenova@mail.ru").gender(Gender.FEMALE)
					.address("City10").courses(coursesForTeacher).build());
	List<Teacher> expectedAllNotApdatedTeachers = Arrays.asList(
			Teacher.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(3).personalNumber(125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("webPI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(4).personalNumber(126).firstName("Peter").lastName("Smirnov")
					.birthday(LocalDate.of(2020, 9, 6)).email("webPS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(5).personalNumber(227).firstName("Irina").lastName("Antonova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Antonova@mail.ru").gender(Gender.FEMALE)
					.address("City11").courses(coursesForTeacher).build(),
			Teacher.builder().id(6).personalNumber(228).firstName("Olga").lastName("Voronina")
					.birthday(LocalDate.of(2019, 9, 7)).email("Voronina@mail.ru").gender(Gender.FEMALE).address("City1")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(7).personalNumber(229).firstName("Svetlana").lastName("Ulianova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ulianova@mail.ru").gender(Gender.FEMALE)
					.address("City112").courses(coursesForTeacher).build(),
			Teacher.builder().id(8).personalNumber(230).firstName("Ksenia").lastName("Gromova")
					.birthday(LocalDate.of(2018, 9, 7)).email("Gromova@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(9).personalNumber(231).firstName("Oksana").lastName("Petrova")
					.birthday(LocalDate.of(2020, 4, 7)).email("PetrovaI@mail.ru").gender(Gender.FEMALE)
					.address("City14").courses(coursesForTeacher).build(),
			Teacher.builder().id(10).personalNumber(232).firstName("Viktor").lastName("Yakovlev")
					.birthday(LocalDate.of(2001, 1, 1)).email("Yakovlev@mail.ru").gender(Gender.MALE).address("City11")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(11).personalNumber(233).firstName("Irina").lastName("Fedorova")
					.birthday(LocalDate.of(2012, 9, 7)).email("Fedorova@mail.ru").gender(Gender.FEMALE)
					.address("City15").courses(coursesForTeacher).build());

	List<Teacher> expectedTeachersSortedByFirstName = Arrays.asList(
			Teacher.builder().id(11).personalNumber(233).firstName("Irina").lastName("Fedorova")
					.birthday(LocalDate.of(2012, 9, 7)).email("Fedorova@mail.ru").gender(Gender.FEMALE)
					.address("City15").courses(coursesForTeacher).build(),
			Teacher.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(coursesForTeacher).build());
	List<Teacher> expectedTeachersSortedByAddress = Arrays.asList(
			Teacher.builder().id(2).personalNumber(124).firstName("Victoria").lastName("Semenova")
					.birthday(LocalDate.of(2020, 9, 1)).email("Semenova@mail.ru").gender(Gender.FEMALE)
					.address("City10").courses(expectedCourses.subList(2, 4)).build(),
			Teacher.builder().id(12).personalNumber(428).firstName("Victoria").lastName("Semenova")
					.birthday(LocalDate.of(2020, 9, 1)).email("Semenova@mail.ru").gender(Gender.FEMALE)
					.address("City10").courses(coursesForTeacher).build());
	List<Teacher> expectedTeachersSortedByEmail = Arrays.asList(
			Teacher.builder().id(11).personalNumber(233).firstName("Irina").lastName("Fedorova")
					.birthday(LocalDate.of(2012, 9, 7)).email("Fedorova@mail.ru").gender(Gender.FEMALE)
					.address("City15").courses(coursesForTeacher).build(),
			Teacher.builder().id(8).personalNumber(230).firstName("Ksenia").lastName("Gromova")
					.birthday(LocalDate.of(2018, 9, 7)).email("Gromova@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(coursesForTeacher).build());
	List<Teacher> expectedTeachersSortedById = Arrays.asList(
			Teacher.builder().id(2).personalNumber(124).firstName("Victoria").lastName("Semenova")
					.birthday(LocalDate.of(2020, 9, 1)).email("Semenova@mail.ru").gender(Gender.FEMALE)
					.address("City10").courses(expectedCourses.subList(2, 4)).build(),
			Teacher.builder().id(3).personalNumber(125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("webPI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(coursesForTeacher).build());
	List<Teacher> expectedTeachersSortedByLastName = Arrays.asList(
			Teacher.builder().id(11).personalNumber(233).firstName("Irina").lastName("Fedorova")
					.birthday(LocalDate.of(2012, 9, 7)).email("Fedorova@mail.ru").gender(Gender.FEMALE)
					.address("City15").courses(coursesForTeacher).build(),
			Teacher.builder().id(8).personalNumber(230).firstName("Ksenia").lastName("Gromova")
					.birthday(LocalDate.of(2018, 9, 7)).email("Gromova@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(coursesForTeacher).build());
	List<Teacher> expectedAllTeachersSortedByLastName = Arrays.asList(
			Teacher.builder().id(5).personalNumber(227).firstName("Irina").lastName("Antonova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Antonova@mail.ru").gender(Gender.FEMALE)
					.address("City11").courses(coursesForTeacher).build(),
			Teacher.builder().id(11).personalNumber(233).firstName("Irina").lastName("Fedorova")
					.birthday(LocalDate.of(2012, 9, 7)).email("Fedorova@mail.ru").gender(Gender.FEMALE)
					.address("City15").courses(coursesForTeacher).build(),
			Teacher.builder().id(8).personalNumber(230).firstName("Ksenia").lastName("Gromova")
					.birthday(LocalDate.of(2018, 9, 7)).email("Gromova@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(3).personalNumber(125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("webPI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(coursesForTeacher).build(),
			Teacher.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(coursesForTeacher).build());
	List<Teacher> expectedTeachersSortedByBirthday = Arrays.asList(
			Teacher.builder().id(11).personalNumber(233).firstName("Irina").lastName("Fedorova")
					.birthday(LocalDate.of(2012, 9, 7)).email("Fedorova@mail.ru").gender(Gender.FEMALE)
					.address("City15").courses(coursesForTeacher).build(),
			Teacher.builder().id(8).personalNumber(230).firstName("Ksenia").lastName("Gromova")
					.birthday(LocalDate.of(2018, 9, 7)).email("Gromova@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(coursesForTeacher).build());
	Teacher teacherForTest = Teacher.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
			.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
			.courses(coursesForTeacher).build();
	Teacher specialTeacher = Teacher.builder().id(5).personalNumber(227).firstName("Irina").lastName("Stepanova")
			.birthday(LocalDate.of(2020, 9, 7)).email("Stepanova@mail.ru").gender(Gender.FEMALE).address("City11")
			.courses(expectedCourses.subList(0, 1)).build();
	List<Student> expectedStudentsSortedByAddress = Arrays.asList(
			Student.builder().id(3).personalNumber(125).firstName("Ivan").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 1)).email("Stepanov@mail.ru").gender(Gender.MALE).address("City11")
					.courses(coursesForTeacher).build(),
			Student.builder().id(5).personalNumber(227).firstName("Irina").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build());
	List<Student> expectedStudentsSortedByMail = Arrays.asList(
			Student.builder().id(12).personalNumber(2227).firstName("Yulia").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov2@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(19).personalNumber(3227).firstName("Irina").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov3@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build());
	List<Student> expectedStudentsSortedByBirthday = Arrays.asList(
			Student.builder().id(29).personalNumber(2280).firstName("Victoria").lastName("Semenova")
					.birthday(LocalDate.of(2020, 9, 1)).email("Semenova@mail.ru").gender(Gender.FEMALE)
					.address("City10").courses(coursesForTeacher).build(),
			Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build());
	List<Student> expectedStudentsSortedByLastName = Arrays.asList(
			Student.builder().id(17).personalNumber(3125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("web3PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(24).personalNumber(32125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("web23PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build());
	List<Student> expectedAllStudentsSortedByLastName = Arrays.asList(
			Student.builder().id(18).personalNumber(3126).firstName("Peter").lastName("Anisimov")
					.birthday(LocalDate.of(2020, 9, 6)).email("web3PS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(3).personalNumber(125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("webPI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(17).personalNumber(3125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("web3PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(24).personalNumber(32125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("web23PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(6).personalNumber(527).firstName("Daria").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova@mail.ru").gender(Gender.FEMALE).address("City20")
					.courses(expectedCoursesForStudents).build());
	List<Student> expectedStudentsSortedById = Arrays.asList(
			Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build());
	List<Student> expectedNotUpdatedStudents = Arrays.asList(
			Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(3).personalNumber(125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("webPI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(4).personalNumber(126).firstName("Peter").lastName("Smirnov")
					.birthday(LocalDate.of(2020, 9, 6)).email("webPS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(5).personalNumber(227).firstName("Irina").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(6).personalNumber(527).firstName("Daria").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova@mail.ru").gender(Gender.FEMALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(7).personalNumber(528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov@mail.ru").gender(Gender.MALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(8).personalNumber(2123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("web2PP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(9).personalNumber(2124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("web2IP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(10).personalNumber(2125).firstName("Peter").lastName("Zlobin")
					.birthday(LocalDate.of(2020, 9, 5)).email("web2PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(11).personalNumber(2126).firstName("Peter").lastName("Smirnov")
					.birthday(LocalDate.of(2020, 9, 6)).email("web2PS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(12).personalNumber(2227).firstName("Yulia").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov2@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(13).personalNumber(2527).firstName("Varia").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova2@mail.ru").gender(Gender.FEMALE)
					.address("City20").courses(expectedCoursesForStudents).build(),
			Student.builder().id(14).personalNumber(2528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov2@mail.ru").gender(Gender.MALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(15).personalNumber(3123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("web3PP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(16).personalNumber(3124).firstName("Alexander").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("web3IP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(17).personalNumber(3125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("web3PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(18).personalNumber(3126).firstName("Peter").lastName("Anisimov")
					.birthday(LocalDate.of(2020, 9, 6)).email("web3PS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(19).personalNumber(3227).firstName("Irina").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov3@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(20).personalNumber(3527).firstName("Varia").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova3@mail.ru").gender(Gender.FEMALE)
					.address("City20").courses(expectedCoursesForStudents).build(),
			Student.builder().id(21).personalNumber(3528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov3@mail.ru").gender(Gender.MALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(22).personalNumber(32123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("web23PP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(23).personalNumber(32124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("web23IP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(24).personalNumber(32125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("web23PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(25).personalNumber(32126).firstName("Peter").lastName("Smirnov")
					.birthday(LocalDate.of(2020, 9, 6)).email("web23PS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(26).personalNumber(32227).firstName("Irina").lastName("Yran")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov23@mail.ru").gender(Gender.FEMALE)
					.address("City11").courses(expectedCoursesForStudents).build(),
			Student.builder().id(27).personalNumber(32527).firstName("Zafira").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova23@mail.ru").gender(Gender.FEMALE)
					.address("City20").courses(expectedCoursesForStudents).build(),
			Student.builder().id(28).personalNumber(32528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov23@mail.ru").gender(Gender.MALE)
					.address("City20").courses(expectedCoursesForStudents).build());
	List<Student> expectedStudents = Arrays.asList(
			Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(3).personalNumber(125).firstName("Ivan").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 1)).email("Stepanov@mail.ru").gender(Gender.MALE).address("City11")
					.courses(coursesForTeacher).build(),
			Student.builder().id(4).personalNumber(126).firstName("Peter").lastName("Smirnov")
					.birthday(LocalDate.of(2020, 9, 6)).email("webPS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(5).personalNumber(227).firstName("Irina").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(6).personalNumber(527).firstName("Daria").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova@mail.ru").gender(Gender.FEMALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(7).personalNumber(528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov@mail.ru").gender(Gender.MALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(8).personalNumber(2123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("web2PP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(9).personalNumber(2124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("web2IP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(10).personalNumber(2125).firstName("Peter").lastName("Zlobin")
					.birthday(LocalDate.of(2020, 9, 5)).email("web2PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(11).personalNumber(2126).firstName("Peter").lastName("Smirnov")
					.birthday(LocalDate.of(2020, 9, 6)).email("web2PS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(12).personalNumber(2227).firstName("Yulia").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov2@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(13).personalNumber(2527).firstName("Varia").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova2@mail.ru").gender(Gender.FEMALE)
					.address("City20").courses(expectedCoursesForStudents).build(),
			Student.builder().id(14).personalNumber(2528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov2@mail.ru").gender(Gender.MALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(15).personalNumber(3123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("web3PP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(16).personalNumber(3124).firstName("Alexander").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("web3IP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(17).personalNumber(3125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("web3PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(18).personalNumber(3126).firstName("Peter").lastName("Anisimov")
					.birthday(LocalDate.of(2020, 9, 6)).email("web3PS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(19).personalNumber(3227).firstName("Irina").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov3@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(20).personalNumber(3527).firstName("Varia").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova3@mail.ru").gender(Gender.FEMALE)
					.address("City20").courses(expectedCoursesForStudents).build(),
			Student.builder().id(21).personalNumber(3528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov3@mail.ru").gender(Gender.MALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(22).personalNumber(32123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("web23PP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(23).personalNumber(32124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("web23IP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(24).personalNumber(32125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("web23PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(25).personalNumber(32126).firstName("Peter").lastName("Smirnov")
					.birthday(LocalDate.of(2020, 9, 6)).email("web23PS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(26).personalNumber(32227).firstName("Irina").lastName("Yran")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov23@mail.ru").gender(Gender.FEMALE)
					.address("City11").courses(expectedCoursesForStudents).build(),
			Student.builder().id(27).personalNumber(32527).firstName("Zafira").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova23@mail.ru").gender(Gender.FEMALE)
					.address("City20").courses(expectedCoursesForStudents).build(),
			Student.builder().id(28).personalNumber(32528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov23@mail.ru").gender(Gender.MALE)
					.address("City20").courses(expectedCoursesForStudents).build());
	List<Student> sillyStudents = Arrays.asList(
			Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCourses.subList(0, 1)).build(),
			Student.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCourses.subList(0, 1)).build());

	List<Student> studentsSortedByFirstNAme = Arrays.asList(
			Student.builder().id(6).personalNumber(527).firstName("Daria").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova@mail.ru").gender(Gender.FEMALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(7).personalNumber(528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov@mail.ru").gender(Gender.MALE).address("City20")
					.courses(expectedCoursesForStudents).build());
	List<Student> expectedStudentsForGroupA2A2Name = Arrays.asList(
			Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build());

	List<Student> expectedStudentsForGroupB2B2Name = Arrays.asList(
			Student.builder().id(3).personalNumber(125).firstName("Peter").lastName("Ivanov")
					.birthday(LocalDate.of(2020, 9, 5)).email("webPI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(4).personalNumber(126).firstName("Peter").lastName("Smirnov")
					.birthday(LocalDate.of(2020, 9, 6)).email("webPS@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build());

	List<Student> expectedStudentsForGroupC2C2Name = Arrays.asList(
			Student.builder().id(5).personalNumber(227).firstName("Irina").lastName("Stepanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanov@mail.ru").gender(Gender.FEMALE).address("City11")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(6).personalNumber(527).firstName("Daria").lastName("Ivanova")
					.birthday(LocalDate.of(2020, 9, 7)).email("Ivanova@mail.ru").gender(Gender.FEMALE).address("City20")
					.courses(expectedCoursesForStudents).build());

	List<Student> expectedStudentsForGroupD2D2Name = Arrays.asList(
			Student.builder().id(7).personalNumber(528).firstName("Igor").lastName("Stepanov")
					.birthday(LocalDate.of(2020, 9, 7)).email("Stepanov@mail.ru").gender(Gender.MALE).address("City20")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(8).personalNumber(2123).firstName("Peter").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 3)).email("web2PP@mail.ru").gender(Gender.MALE).address("City17")
					.courses(expectedCoursesForStudents).build());

	List<Student> expectedStudentsForGroupE2E2Name = Arrays.asList(
			Student.builder().id(9).personalNumber(2124).firstName("Ivan").lastName("Petrov")
					.birthday(LocalDate.of(2020, 9, 4)).email("web2IP@mail.ru").gender(Gender.MALE).address("City18")
					.courses(expectedCoursesForStudents).build(),
			Student.builder().id(10).personalNumber(2125).firstName("Peter").lastName("Zlobin")
					.birthday(LocalDate.of(2020, 9, 5)).email("web2PI@mail.ru").gender(Gender.MALE).address("City19")
					.courses(expectedCoursesForStudents).build());

	Student studentForTest = Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
			.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
			.courses(expectedCourses).build();
	Student smartStudent = Student.builder().id(1).personalNumber(123).firstName("Peter").lastName("Petrov")
			.birthday(LocalDate.of(2020, 9, 3)).email("webPP@mail.ru").gender(Gender.MALE).address("City17")
			.courses(notExistedCourses).build();

	List<Group> expectedGroups = Arrays.asList(
			Group.builder().id(1).name("a2a2").students(expectedStudentsForGroupA2A2Name).build(),
			Group.builder().id(2).name("b2b2").students(expectedStudentsForGroupB2B2Name).build(),
			Group.builder().id(3).name("c2c2").students(expectedStudentsForGroupC2C2Name).build(),
			Group.builder().id(4).name("d2d2").students(expectedStudentsForGroupD2D2Name).build());

	List<Group> expectedGroupsForDefaultPage = Arrays.asList(
			Group.builder().id(1).name("a2a2").students(expectedStudentsForGroupA2A2Name).build(),
			Group.builder().id(2).name("b2b2").students(expectedStudentsForGroupB2B2Name).build(),
			Group.builder().id(3).name("c2c2").students(expectedStudentsForGroupC2C2Name).build(),
			Group.builder().id(4).name("d2d2").students(expectedStudentsForGroupD2D2Name).build(),
			Group.builder().id(5).name("e2e2").students(expectedStudentsForGroupE2E2Name).build());

	List<Group> expectedSortedGroupsForShowByName = Arrays.asList(
			Group.builder().id(1).name("a2a2").students(expectedStudentsForGroupA2A2Name).build(),
			Group.builder().id(2).name("b2b2").students(expectedStudentsForGroupB2B2Name).build(),
			Group.builder().id(3).name("c2c2").students(expectedStudentsForGroupC2C2Name).build(),
			Group.builder().id(4).name("d2d2").students(expectedStudentsForGroupD2D2Name).build(),
			Group.builder().id(5).name("e2e2").students(expectedStudentsForGroupE2E2Name).build());
	List<Group> expectedAllGroups = Arrays.asList(
			Group.builder().id(1).name("a2a2").students(expectedStudentsForGroupA2A2Name).build(),
			Group.builder().id(2).name("b2b2").students(expectedStudentsForGroupB2B2Name).build(),
			Group.builder().id(3).name("c2c2").students(expectedStudentsForGroupC2C2Name).build(),
			Group.builder().id(4).name("d2d2").students(expectedStudentsForGroupD2D2Name).build(),
			Group.builder().id(5).name("e2e2").students(expectedStudentsForGroupE2E2Name).build(),
			Group.builder().id(6).name("f2f2").students(expectedStudentsForGroupA2A2Name).build(),
			Group.builder().id(7).name("g2g2").students(expectedStudentsForGroupB2B2Name).build(),
			Group.builder().id(8).name("k2k2").students(expectedStudentsForGroupC2C2Name).build(),
			Group.builder().id(9).name("l2l2").students(expectedStudentsForGroupD2D2Name).build(),
			Group.builder().id(10).name("m2m2").students(expectedStudentsForGroupE2E2Name).build(),
			Group.builder().id(11).name("n2n2").students(expectedStudentsForGroupA2A2Name).build(),
			Group.builder().id(12).name("o2o2").students(expectedStudentsForGroupB2B2Name).build(),
			Group.builder().id(13).name("p2p2").students(expectedStudentsForGroupC2C2Name).build(),
			Group.builder().id(14).name("q2q2").students(expectedStudentsForGroupD2D2Name).build(),
			Group.builder().id(15).name("r2r2").students(expectedStudentsForGroupE2E2Name).build(),
			Group.builder().id(16).name("s2s2").students(expectedStudentsForGroupA2A2Name).build(),
			Group.builder().id(17).name("t2t2").students(expectedStudentsForGroupB2B2Name).build(),
			Group.builder().id(18).name("u2u2").students(expectedStudentsForGroupC2C2Name).build(),
			Group.builder().id(19).name("v2v2").students(expectedStudentsForGroupD2D2Name).build(),
			Group.builder().id(20).name("x2x2").students(expectedStudentsForGroupE2E2Name).build(),
			Group.builder().id(21).name("z2z2").students(expectedStudentsForGroupA2A2Name).build(),
			Group.builder().id(22).name("w2w2").students(expectedStudentsForGroupB2B2Name).build(),
			Group.builder().id(23).name("i2i2").students(expectedStudentsForGroupC2C2Name).build(),
			Group.builder().id(24).name("j2j2").students(expectedStudentsForGroupE2E2Name).build());

	List<Group> expectedSortedGroupsForByName = Arrays.asList(
			Group.builder().id(2).name("b2b2").students(expectedStudentsForGroupB2B2Name).build(),
			Group.builder().id(4).name("d2d2").students(expectedStudentsForGroupD2D2Name).build());

	Group bigGroup = Group.builder().id(5).name("c2c2").students(expectedStudents).build();
	Group sillyGroup = Group.builder().id(1).name("a2a2").students(sillyStudents).build();
	Group groupForTest = Group.builder().id(1).name("a2a2").students(expectedStudents.subList(0, 3)).build();
	Group groupExpected = Group.builder().id(1).name("a2a2").students(expectedStudentsForGroupA2A2Name).build();
	Group groupExpectedForSortedTest = Group.builder().id(4).name("d2d2").students(expectedStudentsForGroupD2D2Name)
			.build();
	Group groupForCreate = Group.builder().id(0).name("a2a2").students(expectedStudents.subList(0, 3)).build();

	List<Lecture> expectedLectures = Arrays.asList(
			Lecture.builder().id(1).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(expectedTeachers.get(1))
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
					.build(),
			Lecture.builder().id(10).dailyScheduleId(4).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(11).dailyScheduleId(4).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build());

	List<Lecture> expectedLecturesForGroup = Arrays.asList(
			Lecture.builder().id(4).dailyScheduleId(1).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(5).dailyScheduleId(1).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(7).dailyScheduleId(2).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(2))
					.build(),
			Lecture.builder().id(8).dailyScheduleId(3).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(9).dailyScheduleId(3).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build());

	List<Lecture> expectedLecturesForTeacherSchedule = Arrays.asList(
			Lecture.builder().id(34).dailyScheduleId(1).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(35).dailyScheduleId(1).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(8).dailyScheduleId(3).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(9).dailyScheduleId(3).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build());

	List<Lecture> lecturesForSortedSchedule = Arrays.asList(
			Lecture.builder().id(31).dailyScheduleId(19).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(32).dailyScheduleId(20).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(33).dailyScheduleId(20).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build());

	List<Lecture> lecturesForSortedScheduleTimePeriod = Arrays.asList(
			Lecture.builder().id(6).dailyScheduleId(2).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(2))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(8).dailyScheduleId(3).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(9).dailyScheduleId(3).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build());

	List<Lecture> lecturesForSortedScheduleByDateAndGroup = Arrays.asList(
			Lecture.builder().id(13).dailyScheduleId(5).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(24).dailyScheduleId(14).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build());

	List<Lecture> lecturesSortedByGroup = Arrays.asList(
			Lecture.builder().id(3).dailyScheduleId(1).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(6).dailyScheduleId(2).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(2))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(expectedTeachers.get(1))
					.build());

	List<Lecture> lecturesSortByCourse = Arrays.asList(
			Lecture.builder().id(4).dailyScheduleId(1).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(7).dailyScheduleId(2).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(2))
					.build());

	List<Lecture> lecturesSortById = Arrays.asList(
			Lecture.builder().id(2).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(3).dailyScheduleId(1).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(expectedTeachers.get(1))
					.build());

	List<Lecture> lecturesSortByClassroom = Arrays.asList(
			Lecture.builder().id(7).dailyScheduleId(2).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(2))
					.build(),
			Lecture.builder().id(12).dailyScheduleId(5).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(expectedTeachers.get(1))
					.build());

	List<Lecture> lecturesSortByTeacher = Arrays.asList(
			Lecture.builder().id(18).dailyScheduleId(8).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(2))
					.build(),
			Lecture.builder().id(29).dailyScheduleId(18).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(2))
					.build());

	List<Lecture> lecturesSortByTime = Arrays.asList(
			Lecture.builder().id(2).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(12).dailyScheduleId(5).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(expectedTeachers.get(1))
					.build());

	List<Lecture> expectedLecturesForDailySchedule = Arrays.asList(
			Lecture.builder().id(8).dailyScheduleId(3).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
					.build(),
			Lecture.builder().id(9).dailyScheduleId(3).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(1))
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
			.course(expectedCourses.get(3)).classRoom(classroomSmall).group(bigGroup).teacher(expectedTeachers.get(3))
			.build();

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
	Teacher forTestTeacher = Teacher.builder().id(2).personalNumber(124).firstName("Ivan").lastName("Petrov")
			.birthday(LocalDate.of(2020, 9, 4)).email("webIP@mail.ru").gender(Gender.MALE).address("City18")
			.courses(expectedCourses.subList(0, 4)).build();
	List<Lecture> expectedLecturesForSchedule = Arrays.asList(
			Lecture.builder().id(1).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(2).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(3).dailyScheduleId(1).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(4).dailyScheduleId(1).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(5).dailyScheduleId(1).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build());

	List<Lecture> expectedAllLectures = Arrays.asList(
			Lecture.builder().id(1).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(2).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(3).dailyScheduleId(1).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(4).dailyScheduleId(1).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(5).dailyScheduleId(1).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(6).dailyScheduleId(2).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(2))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(7).dailyScheduleId(2).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(2))
					.build(),
			Lecture.builder().id(8).dailyScheduleId(3).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(9).dailyScheduleId(3).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),

			Lecture.builder().id(10).dailyScheduleId(4).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(11).dailyScheduleId(4).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),

			Lecture.builder().id(12).dailyScheduleId(5).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(13).dailyScheduleId(5).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(14).dailyScheduleId(6).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(15).dailyScheduleId(6).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(16).dailyScheduleId(7).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(17).dailyScheduleId(7).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(2))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(18).dailyScheduleId(8).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(2))
					.build(),
			Lecture.builder().id(19).dailyScheduleId(9).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(20).dailyScheduleId(10).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(21).dailyScheduleId(11).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(22).dailyScheduleId(12).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(23).dailyScheduleId(13).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(24).dailyScheduleId(14).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(25).dailyScheduleId(15).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(26).dailyScheduleId(15).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(27).dailyScheduleId(16).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(28).dailyScheduleId(17).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(2))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(29).dailyScheduleId(18).time(LocalTime.of(11, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(2)).teacher(expectedTeachers.get(2))
					.build(),
			Lecture.builder().id(30).dailyScheduleId(18).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2))
					.teacher(expectedAllNotApdatedTeachers.get(1)).build(),
			Lecture.builder().id(31).dailyScheduleId(19).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(32).dailyScheduleId(20).time(LocalTime.of(13, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build(),
			Lecture.builder().id(33).dailyScheduleId(20).time(LocalTime.of(15, 0, 0)).course(expectedCourses.get(3))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(2)).teacher(forTestTeacher).build());

	List<Lecture> expectedLecturesSortedByTime = Arrays.asList(
			Lecture.builder().id(1).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(2).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(12).dailyScheduleId(5).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(13).dailyScheduleId(5).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(classroomSortedTest).group(expectedGroups.get(3)).teacher(expectedTeachers.get(0))
					.build(),
			Lecture.builder().id(23).dailyScheduleId(13).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(forTestTeacher).build());

	DailySchedule expectedDailySchedule = DailySchedule.builder().id(1).date(LocalDate.of(2020, 9, 7))
			.lectures(expectedLecturesForSchedule).build();

	List<DailySchedule> expectedAllSchedules = Arrays.asList(
			DailySchedule.builder().id(1).date(LocalDate.of(2020, 9, 7)).lectures(expectedAllLectures.subList(0, 5))
					.build(),
			DailySchedule.builder().id(2).date(LocalDate.of(2020, 9, 8)).lectures(expectedAllLectures.subList(5, 7))
					.build(),
			DailySchedule.builder().id(3).date(LocalDate.of(2020, 9, 9)).lectures(expectedAllLectures.subList(7, 9))
					.build(),
			DailySchedule.builder().id(4).date(LocalDate.of(2020, 9, 11)).lectures(expectedAllLectures.subList(9, 11))
					.build(),
			DailySchedule.builder().id(5).date(LocalDate.of(2020, 10, 7)).lectures(expectedAllLectures.subList(11, 13))
					.build(),
			DailySchedule.builder().id(6).date(LocalDate.of(2020, 10, 8)).lectures(expectedAllLectures.subList(13, 15))
					.build(),
			DailySchedule.builder().id(7).date(LocalDate.of(2020, 10, 9)).lectures(expectedAllLectures.subList(15, 17))
					.build(),
			DailySchedule.builder().id(8).date(LocalDate.of(2020, 10, 11)).lectures(expectedAllLectures.subList(17, 18))
					.build(),
			DailySchedule.builder().id(9).date(LocalDate.of(2020, 11, 7)).lectures(expectedAllLectures.subList(18, 19))
					.build(),
			DailySchedule.builder().id(10).date(LocalDate.of(2020, 11, 8)).lectures(expectedAllLectures.subList(19, 20))
					.build(),
			DailySchedule.builder().id(11).date(LocalDate.of(2020, 11, 9)).lectures(expectedAllLectures.subList(20, 21))
					.build(),
			DailySchedule.builder().id(12).date(LocalDate.of(2020, 11, 11))
					.lectures(expectedAllLectures.subList(21, 22)).build(),
			DailySchedule.builder().id(13).date(LocalDate.of(2020, 12, 7)).lectures(expectedAllLectures.subList(22, 23))
					.build(),
			DailySchedule.builder().id(14).date(LocalDate.of(2020, 12, 8)).lectures(expectedAllLectures.subList(23, 24))
					.build(),
			DailySchedule.builder().id(15).date(LocalDate.of(2020, 12, 9)).lectures(expectedAllLectures.subList(24, 26))
					.build(),
			DailySchedule.builder().id(16).date(LocalDate.of(2020, 12, 11))
					.lectures(expectedAllLectures.subList(26, 27)).build(),
			DailySchedule.builder().id(17).date(LocalDate.of(2021, 1, 7)).lectures(expectedAllLectures.subList(27, 28))
					.build(),
			DailySchedule.builder().id(18).date(LocalDate.of(2021, 1, 8)).lectures(expectedAllLectures.subList(28, 30))
					.build(),
			DailySchedule.builder().id(19).date(LocalDate.of(2021, 1, 9)).lectures(expectedAllLectures.subList(30, 31))
					.build(),
			DailySchedule.builder().id(20).date(LocalDate.of(2021, 1, 11)).lectures(expectedAllLectures.subList(31, 33))
					.build());

	List<DailySchedule> expectedScheduleForGroup = Arrays.asList(
			DailySchedule.builder().id(1).date(LocalDate.of(2020, 9, 7))
					.lectures(expectedLecturesForGroup.subList(0, 2)).build(),
			DailySchedule.builder().id(2).date(LocalDate.of(2020, 9, 8))
					.lectures(expectedLecturesForGroup.subList(2, 3)).build(),
			DailySchedule.builder().id(3).date(LocalDate.of(2020, 9, 9))
					.lectures(expectedLecturesForGroup.subList(3, 5)).build());

	List<DailySchedule> expectedScheduleForTeacher = Arrays.asList(
			DailySchedule.builder().id(1).date(LocalDate.of(2020, 9, 7))
					.lectures(expectedLecturesForTeacherSchedule.subList(0, 2)).build(),
			DailySchedule.builder().id(3).date(LocalDate.of(2020, 9, 9))
					.lectures(expectedLecturesForTeacherSchedule.subList(2, 4)).build());
	List<DailySchedule> expectedDailyScheduleForTeacher = Arrays.asList(DailySchedule.builder().id(1)
			.date(LocalDate.of(2020, 9, 7)).lectures(expectedLecturesSortedByTime.subList(1, 2)).build());

	List<Lecture> lectureForStudent = Arrays.asList(
			Lecture.builder().id(1).dailyScheduleId(1).time(LocalTime.of(9, 0, 0)).course(expectedCourses.get(0))
					.classRoom(expectedClassrooms.get(0)).group(expectedGroups.get(1)).teacher(forTestTeacher).build(),
			Lecture.builder().id(3).dailyScheduleId(1).time(LocalTime.of(10, 0, 0)).course(expectedCourses.get(1))
					.classRoom(expectedClassrooms.get(1)).group(expectedGroups.get(1)).teacher(forTestTeacher).build());
	List<DailySchedule> expectedDailyScheduleForStudent = Arrays.asList(
			DailySchedule.builder().id(1).date(LocalDate.of(2020, 9, 7)).lectures(lectureForStudent).build(),
			DailySchedule.builder().id(2).date(LocalDate.of(2020, 9, 8)).lectures(expectedAllLectures.subList(5, 6))
					.build());

	List<DailySchedule> expectedScheduleSortedByDate = Arrays.asList(
			DailySchedule.builder().id(19).date(LocalDate.of(2021, 1, 9))
					.lectures(lecturesForSortedSchedule.subList(0, 1)).build(),
			DailySchedule.builder().id(20).date(LocalDate.of(2021, 1, 11))
					.lectures(lecturesForSortedSchedule.subList(1, 3)).build());

	List<DailySchedule> expectedScheduleSortedByDateTimePeriod = Arrays.asList(
			DailySchedule.builder().id(2).date(LocalDate.of(2020, 9, 8))
					.lectures(lecturesForSortedScheduleTimePeriod.subList(0, 1)).build(),
			DailySchedule.builder().id(3).date(LocalDate.of(2020, 9, 9))
					.lectures(lecturesForSortedScheduleTimePeriod.subList(1, 3)).build());

	List<DailySchedule> expectedScheduleSortedByDateAndGroup = Arrays.asList(
			DailySchedule.builder().id(5).date(LocalDate.of(2020, 10, 7))
					.lectures(lecturesForSortedScheduleByDateAndGroup.subList(0, 1)).build(),
			DailySchedule.builder().id(14).date(LocalDate.of(2020, 12, 8))
					.lectures(lecturesForSortedScheduleByDateAndGroup.subList(1, 2)).build());

	DailySchedule dailyScheduleForCreate = DailySchedule.builder().id(4).date(LocalDate.of(2020, 9, 11))
			.lectures(expectedLectures.subList(10, 12)).build();

	DailySchedule dailyScheduleForFindById = DailySchedule.builder().id(3).date(LocalDate.of(2020, 9, 9))
			.lectures(expectedLecturesForDailySchedule).build();
}
