package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.*;
import static ru.stepev.data.DataTest.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import ru.stepev.config.TestConfig;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Gender;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;
import ru.stepev.utils.Paginator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class JdbcDailyScheduleDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DailyScheduleDao dailyScheduleDao;

	@Test
	public void givenCreateDailySchedual_whenCreateDailySchedule_thenDailySchedualeCreated() {
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
		int expectedRows = countRowsInTable(jdbcTemplate, "DAILYSCHEDULE") + 1;
		DailySchedule dailySchedule = new DailySchedule(LocalDate.of(2020, 9, 12), lectures);

		dailyScheduleDao.create(dailySchedule);

		int actualRows = countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");
		assertEquals(expectedRows, actualRows);
		int expectedRow = 1;
		int actualRow = countRowsInTableWhere(jdbcTemplate, "DAILYSCHEDULE", String.format(
				"id = %d AND dailyschedule_date = '%s'", dailySchedule.getId(), dailySchedule.getDate().toString()));
		assertEquals(expectedRow, actualRow);
	}

	@Test
	public void givenUpdateDailySchedual_whenUpdateDailyScheduleById_thenDailySchedualUpdated() {
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
		int expectedRows = countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");

		dailyScheduleDao.update(dailySchedule);

		int actualRows = countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");
		assertEquals(expectedRows, actualRows);
		int expectedRow = 1;
		int actualRow = countRowsInTableWhere(jdbcTemplate, "DAILYSCHEDULE", String.format(
				"id = %d AND dailyschedule_date = '%s'", dailySchedule.getId(), dailySchedule.getDate().toString()));
		assertEquals(expectedRow, actualRow);
	}

	@Test
	public void givenDeleteDailySchedual_whenDeleteDailyScheduleById_thenDailySchedualDeleted() {
		int expectedRows = countRowsInTable(jdbcTemplate, "DAILYSCHEDULE") - 1;

		dailyScheduleDao.delete(2);

		int actualRows = countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");
		assertEquals(expectedRows, actualRows);
		int expectedRow = 0;
		int actualRow = countRowsInTableWhere(jdbcTemplate, "DAILYSCHEDULE",
				String.format("id = %d AND dailyschedule_date = '%s'", 2, LocalDate.of(2020, 9, 8).toString()));
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

		Optional<DailySchedule> actual = dailyScheduleDao.findById(3);

		assertThat(actual).get().isEqualTo(dailyScheduleForFindById);
	}

	@Test
	public void givenFindDailySchedualByIdNotExist_whenDailySchedualNotFoundByIdNotExist_thenGetEmptyOptional() {
		Optional<DailySchedule> actual = dailyScheduleDao.findById(300);

		assertThat(actual).isEmpty();
	}

	@Test
	public void givenFindAllDailySchedules_whenFindAllDailySchedules_thenAllDailySchedualFound() {
		int expected = countRowsInTable(jdbcTemplate, "DAILYSCHEDULE");

		int actual = dailyScheduleDao.findAll().size();

		assertEquals(expected, actual);
	}

	@Test
	public void givenFindDailySchedualesForStudent_whenFindDailySchedules_thenDailySchedulesFound() {

		List<DailySchedule> actual = dailyScheduleDao.findByGroupAndPeriodOfTime(new Group(3, "c2c2"),
				LocalDate.of(2020, 9, 7), LocalDate.of(2020, 9, 9));

		assertThat(actual).isEqualTo(expectedScheduleForGroup);
	}

	@Test
	public void givenFindDailySchedualByDateNotExist_whenDailySchedualNotFoundByDateNotExist_thenGetEmptyOptional() {
		Optional<DailySchedule> actual = dailyScheduleDao.findByDate(LocalDate.of(2021, 9, 11));

		assertThat(actual).isEmpty();
	}

	@Test
	public void givenFindByDate_whenFindDailySchedualeByDate_thenDailyScheduleFound() {

		Optional<DailySchedule> actual = dailyScheduleDao.findByDate(LocalDate.of(2020, 9, 11));
		

		assertThat(actual).get().isEqualTo(dailyScheduleForCreate);
	}

	@Test
	public void givenFindDailySchedualesForTeacher_whenFindCorrectDailyScheduleByTeacherAndPeriodOfDate_thenDailyScheduleFound() {		

		List<DailySchedule> actual = dailyScheduleDao.findByTeacherIdAndPeriodOfTime(2, LocalDate.of(2020, 9, 7),
				LocalDate.of(2020, 9, 9));
		
		assertThat(actual).isEqualTo(expectedScheduleForTeacher);
	}
	
	@Test
	public void findNumberOfItems_whenFindNumberOfItem_thenGetCorrestNumberOFItems() {
		int expected = 21;
		
		int actual = dailyScheduleDao.findNumberOfItems();
		
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void findAndSortByDate_whenFindAndSortByDate_thenGetCorrectSortedListOfDailySchedule() {
		
		List<DailySchedule> actualDailySchedules = dailyScheduleDao.findAndSortByDate(2, 19);
		
		assertThat(actualDailySchedules).isEqualTo(expectedScheduleSortedByDate);
	}

	@Test
	public void findAndSortById_whenFindAndSortByID_thenGetCorrectSortedListOfDailySchedule() {

		List<DailySchedule> actualDailySchedules = dailyScheduleDao.findAndSortById(2, 17);

		assertThat(actualDailySchedules).isEqualTo(expectedScheduleSortedByDate);
	}

	@Test
	public void findAndSortedByTeacherIdAndPeriodOfTime_whenFindAndSortedByTeacherIdAndPeriodOfTime_thenGetCorrestListOfDailySchedule() {
		Paginator paginator = new Paginator(21, 1, "Time" , 2);
		
		List<DailySchedule> actualDailySchedules = dailyScheduleDao.findAndSortedByTeacherIdAndPeriodOfDate(2, LocalDate.of(2020, 9, 8), LocalDate.of(2020, 9, 9), paginator);

		assertThat(actualDailySchedules).isEqualTo(expectedScheduleSortedByDateTimePeriod);
	}
	
	@Test
	public void findAndSortedByGroupAndPeriodOfTime_whenFindAndSortedByGroupAndPeriodOfTime_thenGetCorrectListDailyScheduleSortedByGroupAndPeriodOfTime() {
		Paginator paginator = new Paginator(21, 1, "Time" , 2);
		
		List<DailySchedule> actualDailySchedules = dailyScheduleDao.findAndSortedByGroupAndPeriodOfDate(groupExpectedForSortedTest, LocalDate.of(2020, 9, 7), LocalDate.of(2021, 12, 31), paginator);
		
		assertThat(actualDailySchedules).isEqualTo(expectedScheduleSortedByDateAndGroup);
	}
}
