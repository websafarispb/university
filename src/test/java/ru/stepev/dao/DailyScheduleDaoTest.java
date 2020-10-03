package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import ru.stepev.config.TestConfig;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Gender;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class DailyScheduleDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DailyScheduleDao dailyScheduleDao;

	@Test
	public void givenCreateDailySchedual_whenCreateDailySchedule_thenDailySchedualeCreated() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		List<Lecture> lectures = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2");
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		Lecture lecture = new Lecture(12, 5, LocalTime.of(16, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(13, 5, LocalTime.of(17, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE") + 1;
		DailySchedule dailySchedule = new DailySchedule(LocalDate.of(2020, 9, 12), lectures);

		dailyScheduleDao.create(dailySchedule);

		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");
		assertEquals(expectedRows, actualRows);
		int expectedRow = 1;
		int actualRow = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "DAILYSCHEDULE", String.format(
				"id = %d AND dailyschedule_date = '%s'", dailySchedule.getId(), dailySchedule.getDate().toString()));
		assertEquals(expectedRow, actualRow);
	}

	@Test
	public void givenUpdateDailySchedual_whenUpdateDailyScheduleById_thenDailySchedualUpdated() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		List<Lecture> lectures = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2");
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		Lecture lecture = new Lecture(6, 1, LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(7, 1, LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		DailySchedule dailySchedule = new DailySchedule(1, LocalDate.of(2020, 9, 7), lectures);
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");

		dailyScheduleDao.update(dailySchedule);

		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");
		assertEquals(expectedRows, actualRows);
		int expectedRow = 1;
		int actualRow = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "DAILYSCHEDULE", String.format(
				"id = %d AND dailyschedule_date = '%s'", dailySchedule.getId(), dailySchedule.getDate().toString()));
		assertEquals(expectedRow, actualRow);
	}

	@Test
	public void givenUpdateDailySchedualNotExist_whenUpdateDailyScheduleByIdNotExist_thenGetObjectNotFoundException()
			throws Exception {
		try {
			dailyScheduleDao.update(new DailySchedule(200, LocalDate.of(2021, 9, 8)));
			fail("Expected Exception");
		} catch (Exception e) {
			assertThat("DailySchedule with Id = 200 not found !!!").isEqualTo(e.getMessage());
		}
	}

	@Test
	public void givenDeleteDailySchedualNotExist_whenDeleteDailyScheduleByIdNotExist_thenGetObjectNotFoundException()
			throws Exception {
		try {
			dailyScheduleDao.delete(200);
			fail("Expected Exception");
		} catch (Exception e) {
			assertThat("DailySchedule with Id = 200 not found !!!").isEqualTo(e.getMessage());
		}
	}

	@Test
	public void givenDeleteDailySchedual_whenDeleteDailyScheduleById_thenDailySchedualDeleted() throws Exception {
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE") - 1;
		DailySchedule dailySchedule = new DailySchedule(2, LocalDate.of(2020, 9, 8));

		dailyScheduleDao.delete(dailySchedule.getId());

		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");
		assertEquals(expectedRows, actualRows);
		int expectedRow = 0;
		int actualRow = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "DAILYSCHEDULE", String.format(
				"id = %d AND dailyschedule_date = '%s'", dailySchedule.getId(), dailySchedule.getDate().toString()));
		assertEquals(expectedRow, actualRow);
	}

	@Test
	public void givenFindDailySchedualById_whenDailySchedualFoundById_thenDailySchedualFound() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		List<Student> students = new ArrayList<>();
		students.add(new Student(3, 125, "Peter", "Ivanov", LocalDate.of(2020, 9, 5), "webPI@mail.ru", Gender.MALE,
				"City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", LocalDate.of(2020, 9, 6), "webPS@mail.ru", Gender.MALE,
				"City17", courses));
		List<Lecture> lectures = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2", students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		Lecture lecture = new Lecture(8, 3, LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(9, 3, LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		DailySchedule expected = new DailySchedule(3, LocalDate.of(2020, 9, 9), lectures);

		DailySchedule actual = dailyScheduleDao.findById(3);

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void givenFindDailySchedualByIdNotExist_whenDailySchedualFoundByIdNotExist_thenGetObjectNotFoundException() {
		try {
			dailyScheduleDao.findById(200);
			fail("Expected Exception");
		} catch (Exception e) {
			assertThat("DailySchedule with Id = 200 not found !!!").isEqualTo(e.getMessage());
		}
	}

	@Test
	public void givenFindAllDailySchedules_whenFindAllDailySchedules_thenAllDailySchedualFound() {
		int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");

		int actual = dailyScheduleDao.findAll().size();

		assertEquals(expected, actual);
	}

	@Test
	public void givenFindDailySchedualesForStudent_whenFindDailySchedules_thenDailySchedulesFound() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		List<Student> students = new ArrayList<>();
		students.add(new Student(3, 125, "Peter", "Ivanov", LocalDate.of(2020, 9, 5), "webPI@mail.ru", Gender.MALE,
				"City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", LocalDate.of(2020, 9, 6), "webPS@mail.ru", Gender.MALE,
				"City17", courses));
		List<DailySchedule> expected = new ArrayList<>();
		List<Lecture> lectures = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2", students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		Lecture lecture = new Lecture(4, 1, LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(5, 1, LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		DailySchedule dailySchedule = new DailySchedule(1, LocalDate.of(2020, 9, 7));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);

		lectures = new ArrayList<>();
		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(1, "101", 50);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(3, 125, "Peter", "Ivanov", LocalDate.of(2020, 9, 5), "webPI@mail.ru", Gender.FEMALE,
				"City19", courses);

		lecture = new Lecture(7, 2, LocalTime.of(11, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		dailySchedule = new DailySchedule(2, LocalDate.of(2020, 9, 8));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);

		lectures = new ArrayList<>();
		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(8, 4, LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(9, 4, LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		dailySchedule = new DailySchedule(3, LocalDate.of(2020, 9, 9));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);

		lectures = new ArrayList<>();
		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(10, 4, LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(11, 4, LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		dailySchedule = new DailySchedule(4, LocalDate.of(2020, 9, 11));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);
		List<LocalDate> periodOfTime = new ArrayList<>();
		periodOfTime.add(LocalDate.of(2020, 9, 7));
		periodOfTime.add(LocalDate.of(2020, 9, 11));

		List<DailySchedule> actual = dailyScheduleDao.findByGroupAndPeriodOfTime(new Group(3, "c2c2"), periodOfTime);

		assertEquals(expected, actual);
	}

	@Test
	public void givenFindDailySchedualByDateNotExist_whenDailySchedualFoundByDateNotExist_thenGetObjectNotFoundException() {
		try {
			dailyScheduleDao.findByDate(LocalDate.of(2021, 9, 11));
			fail("Expected Exception");
		} catch (Exception e) {
			assertThat("DailySchedule with Date = '2021-09-11' not found !!!").isEqualTo(e.getMessage());
		}
	}

	@Test
	public void givenFindByDate_whenFindDailySchedualeByDate_thenDailyScheduleFound() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		List<Student> students = new ArrayList<>();
		students.add(new Student(3, 125, "Peter", "Ivanov", LocalDate.of(2020, 9, 5), "webPI@mail.ru", Gender.MALE,
				"City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", LocalDate.of(2020, 9, 6), "webPS@mail.ru", Gender.MALE,
				"City17", courses));
		List<Lecture> lectures = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2", students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		Lecture lecture = new Lecture(10, 4, LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(11, 4, LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		DailySchedule expected = new DailySchedule(4, LocalDate.of(2020, 9, 11), lectures);

		DailySchedule actual = dailyScheduleDao.findByDate(LocalDate.of(2020, 9, 11));

		assertEquals(expected, actual);
	}

	@Test
	public void givenFindDailySchedualesForTeacher_whenFindCorrectDailyScheduleByTeacherAndPeriodOfDate_thenDailyScheduleFound() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		List<DailySchedule> expected = new ArrayList<>();
		List<Lecture> lectures = new ArrayList<>();
		List<Student> students = new ArrayList<>();
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		students = new ArrayList<>();
		students.add(new Student(3, 125, "Peter", "Ivanov", LocalDate.of(2020, 9, 5), "webPI@mail.ru", Gender.MALE,
				"City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", LocalDate.of(2020, 9, 6), "webPS@mail.ru", Gender.MALE,
				"City17", courses));
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		Lecture lecture = new Lecture(14, 1, LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(15, 1, LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		DailySchedule dailySchedule = new DailySchedule(1, LocalDate.of(2020, 9, 7));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);
		lectures = new ArrayList<>();
		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(8, 4, LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(9, 4, LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		dailySchedule = new DailySchedule(3, LocalDate.of(2020, 9, 9));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);
		lectures = new ArrayList<>();
		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(10, 4, LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses);
		lecture = new Lecture(11, 4, LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		dailySchedule = new DailySchedule(4, LocalDate.of(2020, 9, 11));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);
		List<LocalDate> periodOfTime = new ArrayList<>();
		periodOfTime.add(LocalDate.of(2020, 9, 7));
		periodOfTime.add(LocalDate.of(2020, 9, 11));

		List<DailySchedule> actual = dailyScheduleDao.findByTeacherIdAndPeriodOfTime(2, periodOfTime);

		assertThat(expected).isEqualTo(actual);
	}
}
