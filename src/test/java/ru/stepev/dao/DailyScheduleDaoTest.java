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

import ru.stepev.dao.rowmapper.DailyScheduleRowMapper;
import ru.stepev.dao.rowmapper.LectureRowMapper;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Teacher;

public class DailyScheduleDaoTest {

	private static IDatabaseTester databaseTester;
	private static JdbcTemplate jdbcTemplate;
	private static DailyScheduleDao dailyScheduleDao;

	@BeforeAll
	public static void init() throws Exception {
		DataSource dataSourse = new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8").ignoreFailedDrops(true).addScript("schema.sql").addScript("data.sql")
				.build();
		jdbcTemplate = new JdbcTemplate(dataSourse);
		DataSourceDatabaseTester dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSourse);
		databaseTester = dataSourceDatabaseTester; // (IDatabaseTester)

		LectureRowMapper lectureRowMapper = new LectureRowMapper(jdbcTemplate);
		DailyScheduleRowMapper dailyScheduleRowMapper = new DailyScheduleRowMapper();
		dailyScheduleDao = new DailyScheduleDao(jdbcTemplate, dailyScheduleRowMapper, lectureRowMapper);
	}

	@Test
	public void createOneDailyScheduleTest() throws Exception {

		List<DailySchedule> expected = new ArrayList<>();
		expected.add(new DailySchedule(1, LocalDate.of(2020, 9, 7)));
		expected.add(new DailySchedule(2, LocalDate.of(2020, 9, 8)));
		expected.add(new DailySchedule(3, LocalDate.of(2020, 9, 9)));
		expected.add(new DailySchedule(4, LocalDate.of(2020, 9, 11)));
		expected.add(new DailySchedule(5, LocalDate.of(2020, 9, 10)));

		DailySchedule dailySchedule = new DailySchedule(4, LocalDate.of(2020, 9, 10));

		dailyScheduleDao.create(dailySchedule);

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("DAILYSCHEDULE");

		List<DailySchedule> actual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			actual.add(new DailySchedule(Integer.parseInt(actualTable.getValue(i, "dailyschedule_id").toString()),
					LocalDate.parse(actualTable.getValue(i, "dailyschedule_date").toString())));
		}
		assertEquals(expected, actual);
	}

	@Test
	public void updateDailyScheduleByIdTest() throws Exception {

		List<DailySchedule> expected = new ArrayList<>();
		expected.add(new DailySchedule(1, LocalDate.of(2020, 9, 7)));
		expected.add(new DailySchedule(2, LocalDate.of(2020, 9, 8)));
		expected.add(new DailySchedule(3, LocalDate.of(2021, 10, 19)));
		expected.add(new DailySchedule(4, LocalDate.of(2020, 9, 11)));
		expected.add(new DailySchedule(5, LocalDate.of(2020, 9, 10)));

		DailySchedule dailySchedule = new DailySchedule(3, LocalDate.of(2021, 10, 19));
		dailyScheduleDao.update(dailySchedule, 3);

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("DAILYSCHEDULE");

		List<DailySchedule> actual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			actual.add(new DailySchedule(Integer.parseInt(actualTable.getValue(i, "dailyschedule_id").toString()),
					LocalDate.parse(actualTable.getValue(i, "dailyschedule_date").toString())));
		}
		assertEquals(expected, actual);
	}

	@Test
	public void deleteDailyScheduleByIdTest() throws Exception {

		List<DailySchedule> expected = new ArrayList<>();
		expected.add(new DailySchedule(1, LocalDate.of(2020, 9, 7)));
		expected.add(new DailySchedule(2, LocalDate.of(2020, 9, 8)));
		expected.add(new DailySchedule(4, LocalDate.of(2020, 9, 11)));
		expected.add(new DailySchedule(5, LocalDate.of(2020, 9, 10)));

		dailyScheduleDao.delete(3);

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("DAILYSCHEDULE");

		List<DailySchedule> actual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			actual.add(new DailySchedule(Integer.parseInt(actualTable.getValue(i, "dailyschedule_id").toString()),
					LocalDate.parse(actualTable.getValue(i, "dailyschedule_date").toString())));
		}
		assertEquals(expected, actual);
	}

	@Test
	public void findById() {
		DailySchedule expected = new DailySchedule(3, LocalDate.of(2020, 9, 9));
		DailySchedule actual = dailyScheduleDao.findById(3);
		assertEquals(expected, actual);
	}

	@Test
	public void assignLecturesTest() {
		List<Lecture> expected = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2");
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		Lecture lecture = new Lecture(6, LocalDate.of(2020, 9, 9), LocalTime.of(13, 0, 0), course, classroom, group,
				teacher);
		expected.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(7, LocalDate.of(2020, 9, 9), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		DailySchedule dailySchedule = new DailySchedule(3, LocalDate.of(2020, 9, 9));
		dailySchedule.setLectures(expected);
		dailyScheduleDao.assignLectures(dailySchedule);
		List<Lecture> actual = dailyScheduleDao.findLecturesByDateAndGroup(LocalDate.of(2020, 9, 9),
				new Group(3, "c2c2"));
		assertEquals(expected, actual);
	}

	@Test
	public void findAllDailySchedulesTest() {
		List<DailySchedule> expected = new ArrayList<>();
		expected.add(new DailySchedule(1, LocalDate.of(2020, 9, 7)));
		expected.add(new DailySchedule(2, LocalDate.of(2020, 9, 8)));
		expected.add(new DailySchedule(3, LocalDate.of(2020, 9, 9)));
		expected.add(new DailySchedule(4, LocalDate.of(2020, 9, 11)));
		expected.add(new DailySchedule(5, LocalDate.of(2020, 9, 10)));
		List<DailySchedule> actual = dailyScheduleDao.findAllDailySchedules();

		assertEquals(expected, actual);
	}

	@Test
	public void findSchedualesForStudent() {

		List<DailySchedule> expected = new ArrayList<>();
		List<Lecture> lectures = new ArrayList<>();

		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2");
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		Lecture lecture = new Lecture(4, LocalDate.of(2020, 9, 7), LocalTime.of(13, 0, 0), course, classroom, group,
				teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(5, LocalDate.of(2020, 9, 7), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		DailySchedule dailySchedule = new DailySchedule(1, LocalDate.of(2020, 9, 7));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);
		lectures = new ArrayList<>();

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(8, LocalDate.of(2020, 9, 11), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(9, LocalDate.of(2020, 9, 11), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		dailySchedule = new DailySchedule(4, LocalDate.of(2020, 9, 11));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);

		List<LocalDate> periodOfTime = new ArrayList<>();
		periodOfTime.add(LocalDate.of(2020, 9, 7));
		periodOfTime.add(LocalDate.of(2020, 9, 11));

		List<DailySchedule> actual = dailyScheduleDao.findSchedualesForStudent(new Group(3, "c2c2"), periodOfTime);
		assertEquals(expected, actual);
	}

	@Test
	public void findDailySchedualByDateTest() {
		DailySchedule expected = new DailySchedule(1, LocalDate.of(2020, 9, 7));
		DailySchedule actual = dailyScheduleDao.findDailySchedualByDate(LocalDate.of(2020, 9, 7));
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

		List<LocalDate> periodOfTime = new ArrayList<>();
		periodOfTime.add(LocalDate.of(2020, 9, 7));
		periodOfTime.add(LocalDate.of(2020, 9, 11));

		List<Lecture> actual = dailyScheduleDao.findLecturesByDateAndGroup(LocalDate.of(2020, 9, 7), group);
		assertEquals(expected, actual);
	}

	@Test
	public void findSchedualesForTeacher() {
		List<DailySchedule> expected = new ArrayList<>();
		List<Lecture> lectures = new ArrayList<>();

		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(2, "b2b2");
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 9, 7), LocalTime.of(9, 0, 0), course, classroom, group,
				teacher);
		lectures.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(2, "b2b2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(2, LocalDate.of(2020, 9, 7), LocalTime.of(10, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(4, LocalDate.of(2020, 9, 7), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(5, LocalDate.of(2020, 9, 7), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		DailySchedule dailySchedule = new DailySchedule(1, LocalDate.of(2020, 9, 7));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);
		lectures = new ArrayList<>();

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(8, LocalDate.of(2020, 9, 11), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		lecture = new Lecture(9, LocalDate.of(2020, 9, 11), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		dailySchedule = new DailySchedule(4, LocalDate.of(2020, 9, 11));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);

		List<LocalDate> periodOfTime = new ArrayList<>();
		periodOfTime.add(LocalDate.of(2020, 9, 7));
		periodOfTime.add(LocalDate.of(2020, 9, 11));

		List<DailySchedule> actual = dailyScheduleDao.findSchedualesForTeacher(2, periodOfTime);
		assertEquals(expected, actual);
	}

	@Test
	public void findLecturesByDateAndTeacherIdTest() {
		List<Lecture> expected = new ArrayList<>();

		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(2, "b2b2");
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 9, 7), LocalTime.of(9, 0, 0), course, classroom, group,
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

		List<Lecture> actual = dailyScheduleDao.findLecturesByDateAndTeacherId(LocalDate.of(2020, 9, 7), 2);
		assertEquals(expected, actual);
	}
}
