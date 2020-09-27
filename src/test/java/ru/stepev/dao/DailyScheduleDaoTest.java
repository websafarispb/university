package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

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
	public void create_whenCreateOneDailySchedule_thenTableDailySchedulesMustHaveCorrectCountOfRows() throws Exception {

		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		List<Lecture> lectures = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2");
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18",
				courses);
		Lecture lecture = new Lecture(6, LocalDate.of(2020, 9, 10), LocalTime.of(13, 0, 0), course, classroom, group,
				teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(7, LocalDate.of(2020, 9, 10), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE") + 1;
		DailySchedule dailySchedule = new DailySchedule(LocalDate.of(2020, 9, 10), lectures);
		dailyScheduleDao.create(dailySchedule);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");
		assertEquals(expectedRows, actualRows);

		int expectedRow = 1;
		int actualRow = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "DAILYSCHEDULE", String.format(
				"id = %d AND dailyschedule_date = '%s'", dailySchedule.getId(), dailySchedule.getDate().toString()));
		assertEquals(expectedRow, actualRow);
	}

	@Test
	public void update_whenUpdateDailyScheduleById_thenTableMustHaveCorrectRow() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		List<Lecture> lectures = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2");
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18",
				courses);
		Lecture lecture = new Lecture(6, LocalDate.of(2020, 9, 10), LocalTime.of(13, 0, 0), course, classroom, group,
				teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2");
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(7, LocalDate.of(2020, 9, 10), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		DailySchedule dailySchedule = new DailySchedule(2, LocalDate.of(2020, 10, 10), lectures);

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
	public void delete_whenDeleteDailyScheduleById_thenCountOfRowsInTableMustDecrease() throws Exception {

		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE") - 1;
		DailySchedule dailySchedule = new DailySchedule(3, LocalDate.of(2020, 9, 9));
		dailyScheduleDao.delete(dailySchedule.getId());
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");
		assertEquals(expectedRows, actualRows);

		int expectedRow = 0;
		int actualRow = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "DAILYSCHEDULE", String.format(
				"id = %d AND dailyschedule_date = '%s'", dailySchedule.getId(), dailySchedule.getDate().toString()));
		assertEquals(expectedRow, actualRow);
	}

	@Test
	public void findById_shouldFindCorrectDailyScheduleById_thenTableHaveOne() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));

		List<Student> students = new ArrayList<>();
		students.add(new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17", courses));

		List<Lecture> lectures = new ArrayList<>();
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2", students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18",
				courses);
		Lecture lecture = new Lecture(6, LocalDate.of(2020, 9, 9), LocalTime.of(13, 0, 0), course, classroom, group,
				teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(7, LocalDate.of(2020, 9, 9), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		DailySchedule expected = new DailySchedule(3, LocalDate.of(2020, 9, 9), lectures);
		DailySchedule actual = dailyScheduleDao.findById(3);
		assertEquals(expected, actual);
	}

	@Test
	public void findAllDailySchedules_shouldFindAllDailySchedules_whenTableHaveMoreThenOne() {
		int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");
		int actual = dailyScheduleDao.findAll().size();
		assertEquals(expected, actual);
	}

	@Test
	public void findDailySchedualesForStudent_shouldFindDailySchedules_whenTableHaveMoreThenOne() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));

		List<Student> students = new ArrayList<>();
		students.add(
				new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17", courses));

		List<DailySchedule> expected = new ArrayList<>();
		List<Lecture> lectures = new ArrayList<>();

		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2", students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18",
				courses);
		Lecture lecture = new Lecture(4, LocalDate.of(2020, 9, 7), LocalTime.of(13, 0, 0), course, classroom, group,
				teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(5, LocalDate.of(2020, 9, 7), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		DailySchedule dailySchedule = new DailySchedule(1, LocalDate.of(2020, 9, 7));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);
		lectures = new ArrayList<>();

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(8, LocalDate.of(2020, 9, 11), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(9, LocalDate.of(2020, 9, 11), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		dailySchedule = new DailySchedule(4, LocalDate.of(2020, 9, 11));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);

		List<LocalDate> periodOfTime = new ArrayList<>();
		periodOfTime.add(LocalDate.of(2020, 9, 7));
		periodOfTime.add(LocalDate.of(2020, 9, 11));

		List<DailySchedule> actual = dailyScheduleDao.findForStudent(new Group(3, "c2c2"), periodOfTime);
		assertEquals(expected, actual);
	}

	@Test
	public void findDailySchedualByDate_shouldFindDailySchedualeByDate_whenTableHasOne() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));

		List<Student> students = new ArrayList<>();
		students.add(
				new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17", courses));

		List<Lecture> lectures = new ArrayList<>();

		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2", students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18",
				courses);
		Lecture lecture = new Lecture(8, LocalDate.of(2020, 9, 11), LocalTime.of(13, 0, 0), course, classroom, group,
				teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(9, LocalDate.of(2020, 9, 11), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		DailySchedule expected = new DailySchedule(4, LocalDate.of(2020, 9, 11), lectures);
		DailySchedule actual = dailyScheduleDao.findByDate(LocalDate.of(2020, 9, 11));
		assertEquals(expected, actual);
	}

	@Test
	public void findLecturesByDateAndGroup_shouldFindDailySchedualeByDateAndGroup_whenTableHasOne() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));

		List<Student> students = new ArrayList<>();
		students.add(
				new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17", courses));

		List<Lecture> expected = new ArrayList<>();

		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(2, "102", 40);
		Group group = new Group(3, "c2c2", students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18",
				courses);
		Lecture lecture = new Lecture(4, LocalDate.of(2020, 9, 7), LocalTime.of(13, 0, 0), course, classroom, group,
				teacher);
		expected.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(5, LocalDate.of(2020, 9, 7), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		List<LocalDate> periodOfTime = new ArrayList<>();
		periodOfTime.add(LocalDate.of(2020, 9, 7));
		periodOfTime.add(LocalDate.of(2020, 9, 11));

		List<Lecture> actual = dailyScheduleDao.findLecturesByDateAndGroup(LocalDate.of(2020, 9, 7), group);
		assertEquals(expected, actual);
	}

	@Test
	public void findDailySchedualesForTeacher_shouldFindCorrectDailyScheduleByTeacherAndPeriodOfDate_thenTableHave() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		List<DailySchedule> expected = new ArrayList<>();
		List<Lecture> lectures = new ArrayList<>();

		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses));

		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(2, "b2b2", students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18",
				courses);
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 9, 7), LocalTime.of(9, 0, 0), course, classroom, group,
				teacher);
		lectures.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(2, "b2b2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(2, LocalDate.of(2020, 9, 7), LocalTime.of(10, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		students = new ArrayList<>();
		students.add(
				new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17", courses));

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(4, LocalDate.of(2020, 9, 7), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(5, LocalDate.of(2020, 9, 7), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		DailySchedule dailySchedule = new DailySchedule(1, LocalDate.of(2020, 9, 7));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);
		lectures = new ArrayList<>();

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(8, LocalDate.of(2020, 9, 11), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(9, LocalDate.of(2020, 9, 11), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		lectures.add(lecture);
		dailySchedule = new DailySchedule(4, LocalDate.of(2020, 9, 11));
		dailySchedule.setLectures(lectures);
		expected.add(dailySchedule);

		List<LocalDate> periodOfTime = new ArrayList<>();
		periodOfTime.add(LocalDate.of(2020, 9, 7));
		periodOfTime.add(LocalDate.of(2020, 9, 11));

		List<DailySchedule> actual = dailyScheduleDao.findForTeacher(2, periodOfTime);
		assertThat(expected).isEqualTo(actual);

	}

	@Test
	public void findLecturesByDateAndTeacherId_shouldFindCorrectDailyScheduleByDateAndTeacherId_thenTableHave() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));

		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses));

		List<Lecture> expected = new ArrayList<>();
		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(2, "b2b2", students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18",
				courses);
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 9, 7), LocalTime.of(9, 0, 0), course, classroom, group,
				teacher);
		expected.add(lecture);

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(2, "b2b2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(2, LocalDate.of(2020, 9, 7), LocalTime.of(10, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		students = new ArrayList<>();
		students.add(
				new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17", courses));

		course = new Course(2, "Biology", "Bio");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(4, LocalDate.of(2020, 9, 7), LocalTime.of(13, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		course = new Course(4, "Physics", "Phy");
		classroom = new Classroom(2, "102", 40);
		group = new Group(3, "c2c2", students);
		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		lecture = new Lecture(5, LocalDate.of(2020, 9, 7), LocalTime.of(15, 0, 0), course, classroom, group, teacher);
		expected.add(lecture);

		List<Lecture> actual = dailyScheduleDao.findLecturesByDateAndTeacherId(LocalDate.of(2020, 9, 7), 2);
		assertEquals(expected, actual);
	}
}
