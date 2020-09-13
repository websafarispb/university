package ru.stepev.dao;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import ru.stepev.dao.rowmapper.LectureRowMapper;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Teacher;

public class LectureDaoTest {

	private static IDatabaseTester databaseTester;
	private static JdbcTemplate jdbcTemplate;
	private static LectureDao lectureDao;

	@BeforeAll
	public static void init() throws Exception {
		DataSource dataSourse = new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8").ignoreFailedDrops(true).addScript("schema.sql").addScript("data.sql")
				.build();
		jdbcTemplate = new JdbcTemplate(dataSourse);
		DataSourceDatabaseTester dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSourse);
		databaseTester = dataSourceDatabaseTester; // (IDatabaseTester)

		LectureRowMapper lectureRowMapper = new LectureRowMapper(jdbcTemplate);
		lectureDao = new LectureDao(jdbcTemplate, lectureRowMapper);
	}

	@Test
	public void createOneLectureTest() throws Exception {

		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17");
		Lecture lecture = new Lecture(4, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);
		List<Object> expected = new ArrayList<>();

		expected.add(1);
		expected.add(LocalDate.of(2020, 8, 19).toString());
		expected.add(LocalTime.of(10, 0, 0).toString());
		expected.add(1);
		expected.add(1);
		expected.add(1);
		expected.add(1);

		expected.add(2);
		expected.add(LocalDate.of(2020, 9, 7).toString());
		expected.add(LocalTime.of(10, 0, 0).toString());
		expected.add(2);
		expected.add(2);
		expected.add(2);
		expected.add(2);

		expected.add(3);
		expected.add(LocalDate.of(2020, 9, 8).toString());
		expected.add(LocalTime.of(11, 0, 0).toString());
		expected.add(3);
		expected.add(2);
		expected.add(2);
		expected.add(2);

		expected.add(4);
		expected.add(LocalDate.of(2020, 9, 7).toString());
		expected.add(LocalTime.of(13, 0, 0).toString());
		expected.add(2);
		expected.add(2);
		expected.add(3);
		expected.add(2);

		expected.add(5);
		expected.add(LocalDate.of(2020, 9, 7).toString());
		expected.add(LocalTime.of(15, 0, 0).toString());
		expected.add(4);
		expected.add(2);
		expected.add(3);
		expected.add(2);

		expected.add(6);
		expected.add(LocalDate.of(2020, 9, 9).toString());
		expected.add(LocalTime.of(13, 0, 0).toString());
		expected.add(2);
		expected.add(2);
		expected.add(3);
		expected.add(2);

		expected.add(7);
		expected.add(LocalDate.of(2020, 9, 9).toString());
		expected.add(LocalTime.of(15, 0, 0).toString());
		expected.add(4);
		expected.add(2);
		expected.add(3);
		expected.add(2);

		expected.add(8);
		expected.add(LocalDate.of(2020, 9, 11).toString());
		expected.add(LocalTime.of(13, 0, 0).toString());
		expected.add(2);
		expected.add(2);
		expected.add(3);
		expected.add(2);

		expected.add(9);
		expected.add(LocalDate.of(2020, 9, 11).toString());
		expected.add(LocalTime.of(15, 0, 0).toString());
		expected.add(4);
		expected.add(2);
		expected.add(3);
		expected.add(2);

		expected.add(10);
		expected.add(LocalDate.of(2020, 8, 19).toString());
		expected.add(LocalTime.of(10, 0, 0).toString());
		expected.add(course.getId());
		expected.add(classroom.getId());
		expected.add(group.getId());
		expected.add(teacher.getId());

		lectureDao.create(lecture);

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("LECTURES");

		List<Object> actual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			actual.add(Integer.parseInt(actualTable.getValue(i, "lecture_id").toString()));
			actual.add(actualTable.getValue(i, "local_date").toString());
			actual.add(actualTable.getValue(i, "local_time").toString());
			actual.add(Integer.parseInt(actualTable.getValue(i, "course_id").toString()));
			actual.add(Integer.parseInt(actualTable.getValue(i, "classroom_id").toString()));
			actual.add(Integer.parseInt(actualTable.getValue(i, "group_id").toString()));
			actual.add(Integer.parseInt(actualTable.getValue(i, "teacher_id").toString()));
		}

		assertEquals(expected, actual);
	}

	@Test
	public void updateLectureByIdTest() throws Exception {

		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17");
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);
		lectureDao.update(lecture, 1);
		Lecture expected = lecture;
		Lecture actual = lectureDao.findById(1);
		assertEquals(expected, actual);
	}

	@Test
	public void findLectureByIdTest() throws Exception {

		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17");
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);
		Lecture expected = lecture;
		Lecture actual = lectureDao.findById(1);
		assertEquals(expected, actual);
	}

	@Test
	public void deleteLectureByIdTest() throws Exception {

		List<Lecture> expected = new ArrayList<>();
		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17");
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(2, "b2b2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(2, LocalDate.of(2020, 9, 7), LocalTime.of(10, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(4, LocalDate.of(2020, 9, 7), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(5, LocalDate.of(2020, 9, 7), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(6, LocalDate.of(2020, 9, 9), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(7, LocalDate.of(2020, 9, 9), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(8, LocalDate.of(2020, 9, 11), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(9, LocalDate.of(2020, 9, 11), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(1, "101", 50);
		group = new Group(1, "a2a2");
		teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17");
		lecture = new Lecture(10, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		lectureDao.delete(3);
		List<Lecture> actual = lectureDao.findAllLectures();
		assertEquals(expected, actual);
	}

	@Test
	public void findAllLecturesTest() {
		List<Lecture> expected = new ArrayList<>();
		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17");
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(2, "b2b2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(2, LocalDate.of(2020, 9, 7), LocalTime.of(10, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(4, LocalDate.of(2020, 9, 7), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(5, LocalDate.of(2020, 9, 7), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(6, LocalDate.of(2020, 9, 9), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(7, LocalDate.of(2020, 9, 9), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(8, LocalDate.of(2020, 9, 11), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(9, LocalDate.of(2020, 9, 11), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(1, "101", 50);
		group = new Group(1, "a2a2");
		teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17");
		lecture = new Lecture(10, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		List<Lecture> actual = lectureDao.findAllLectures();
		assertEquals(expected, actual);
	}

	@Test
	public void findLecturesByDateTest() {
		List<Lecture> expected = new ArrayList<>();
		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17");
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);
		expected.add(lecture);
		List<Lecture> actual = lectureDao.findLecturesByDate(LocalDate.of(2020, 8, 19));
		assertEquals(expected, actual);
	}

	@Test
	public void findLecturesByDateAndGroupTest() {
		List<Lecture> expected = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2");
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		Lecture lecture = new Lecture(4, LocalDate.of(2020, 9, 7), LocalTime.of(13, 0, 0), course, classroom, group,
				teacher);
		expected.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(5, LocalDate.of(2020, 9, 7), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		List<Lecture> actual = lectureDao.findLecturesByDateAndGroup(LocalDate.of(2020, 9, 7), new Group(3, "c2c2"));
		assertEquals(expected, actual);
	}

}
