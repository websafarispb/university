package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.*;

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
import org.springframework.transaction.annotation.Transactional;

import ru.stepev.config.TestConfig;
import ru.stepev.dao.impl.LectureDaoImpl;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.Gender;
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
	private LectureDaoImpl lectureDao;

	@Test
	public void givenCreateLecture_whenCreateLecture_thenLectureCreated() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		
		int expectedRows = countRowsInTable(jdbcTemplate, "LECTURES") + 1;
		Course course = new Course(2, "Biology", "Bio");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE, "City17", courses);
		Lecture lecture = new Lecture(10, 1, LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);

		lectureDao.create(lecture);
		
		int actualRows = countRowsInTable(jdbcTemplate, "LECTURES");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void givenUpdateLectureById_whenUpdateLectureById_thenLectureUpdated() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		
		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(1, "a2a2");
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE, "City17", courses);
		Lecture lecture = new Lecture(1, 1,  LocalTime.of(10, 0, 0), course, classroom, group,
				teacher);
		
		lectureDao.update(lecture);
		
		int expectedRow = 1;
		int actualRow = countRowsInTableWhere(jdbcTemplate, "LECTURES",
				String.format("id = %d  AND "
						+ "local_time = '%s:00' AND course_id = %d AND classroom_id = %d AND group_id = %d AND teacher_id = %d",
						lecture.getId(), lecture.getTime(),
						lecture.getCourse().getId(), lecture.getClassRoom().getId(), lecture.getGroup().getId(),
						lecture.getTeacher().getId()));

		assertEquals(expectedRow, actualRow);
	}
	
	@Test
	public void givenFindLectureByIdNotExist_whenNotFindLectureById_thenGetEmptyOptional() throws Exception {
		Optional<Lecture> actual = lectureDao.findById(200);
		
			assertThat(actual).isEmpty();	
	}

	@Test
	public void givenFindLectureById_whenFindLectureById_thenLectureWasFound() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		
		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE, "City18", courses));
		
		Course course = new Course(1, "Mathematics", "Math");
		Classroom classroom = new Classroom(1, "101", 50);
		Group group = new Group(2, "b2b2",students);
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE, "City18", courses);
		Lecture expected = new Lecture(1, 1, LocalTime.of(9, 0, 0), course, classroom, group,
				teacher);
		
		Optional <Lecture> actual = lectureDao.findById(1);
		
		assertThat(actual).get().isEqualTo(expected);
	}

	@Test
	public void givenDeleteLecture_whenDeleteLectureById_thenLectureWasDeleted() {
		int expectedRows = countRowsInTable(jdbcTemplate, "LECTURES") - 1;
		
		lectureDao.delete(3);
		
		int actualRows = countRowsInTable(jdbcTemplate, "LECTURES");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void givenfindAllLectures_whenFindAllLectures_thenAllLecturesWasFound() {
		int expectedSize = 11;
		
		int actualSize = lectureDao.findAll().size();
		
		assertEquals(expectedSize, actualSize);
	}

	@Test
	public void givenFindLecturesByDailyScheduleId_whenFindLectureByDailyScheduleId_thenLecturesWasFound() {
		int expected = 2;
		
		int actual = lectureDao.findByDailyScheduleId(2).size();
		
		assertEquals(expected, actual);
	}
}
