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
import org.springframework.transaction.annotation.Transactional;

import ru.stepev.config.TestConfig;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class LectureDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private LectureDao lectureDao;

	@Test
	public void create_whenCreateOneLecture_thenTableClassroomsMustHaveCorrectCountOfRows() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "LECTURES") + 1;
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17", courses);
		Lecture lecture = new Lecture(10, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);

		lectureDao.create(lecture);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "LECTURES");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void update_whenUpdateLectureById_thenTableMustHaveCorrectRow() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		
		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17", courses);
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 8, 19), LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);
		lectureDao.update(lecture);
		int expectedRow = 1;
		int actualRow = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "LECTURES",
				String.format("id = %d AND local_date = '%s' AND "
						+ "local_time = '%s' AND course_id = %d AND classroom_id = %d AND group_id = %d AND teacher_id = %d",
						lecture.getId(), lecture.getDate().toString(), lecture.getTime().toString(),
						lecture.getCourse().getId(), lecture.getClassRoom().getId(), lecture.getGroup().getId(),
						lecture.getTeacher().getId()));

		assertEquals(expectedRow, actualRow);
	}

	@Test
	public void findById_shouldFindCorrectLectureById_thenTableHaveOne() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		
		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses));
		
		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(2, "b2b2",students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		Lecture lecture = new Lecture(1, LocalDate.of(2020, 9, 7), LocalTime.of(9, 0, 0), course, classroom, group,
				teacher);
		Lecture expected = lecture;
		Lecture actual = lectureDao.findById(1);
		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void delete_whenDeleteLectureById_thenCountOfRowsInTableMustDecrease() throws Exception {
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "LECTURES") - 1;
		lectureDao.delete(3);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "LECTURES");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void findAll_shouldFindAllLectures_whenTableHaveMoreThenOne() {
		int expectedSize = 9;
		int actualSize = lectureDao.findAll().size();
		assertEquals(expectedSize, actualSize);
	}

	@Test
	public void findLecturesByDate_shouldFindLectureByDate_whenTableHasOne() {
		int expected = 1;
		int actual = lectureDao.findByDate(LocalDate.of(2020, 9, 8)).size();
		assertEquals(expected, actual);
	}

	@Test
	public void findLecturesByDateAndGroup_shouldReturnLectureByDateAndGroup_whenTableHas() {
		int expected = 2;
		int actual = lectureDao.findByDateAndGroup(LocalDate.of(2020, 9, 7), new Group(3, "c2c2")).size();
		assertEquals(expected, actual);
	}
}
