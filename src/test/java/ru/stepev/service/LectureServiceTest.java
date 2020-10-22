package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.stepev.dao.ClassroomDao;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.dao.LectureDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Lecture;

public class LectureServiceTest {

	@Mock
	private LectureDao lectureDao;
	@Mock
	private DailyScheduleDao dailyScheduleDao;
	@Mock
	private CourseDao courseDao;
	@Mock
	private ClassroomDao classroomDao;
	@Mock
	private GroupDao groupDao;
	@Mock
	private TeacherDao teacherDao;

	@InjectMocks
	private LectureService lectureService;

	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		dataHelper = new DataHelper();
	}
	
	@Test
	public void givenCheckClassroomCanContainGroup_whenCheckClassroomCanContainGroup_thenReturnTrue() {
		boolean possibilityExpected = true;

		boolean possibilityActual = lectureService.isClassroomHasQuiteCapacity(dataHelper.getClassroomForCreate(),
				dataHelper.getCorrestLectureForTest().getGroup());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenCheckClassroomCanNotContainGroup_whenCheckClassroomCanNotContainGroup_thenReturnFalse() {
		boolean possibilityExpected = false;

		boolean possibilityActual = lectureService.isClassroomHasQuiteCapacity(dataHelper.getClassroomSmall(),
				dataHelper.getGroupForTest());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenCheckGroupCanDoCourse_whenCheckGroupCanDoCourse_thenReturnTrue() {
		boolean possibilityExpected = true;

		boolean possibilityActual = lectureService.isGroupCanDoCourse(dataHelper.getGroupForTest(),
				dataHelper.getCorrestLectureForTest().getCourse());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenCheckGroupCanNotDoCourse_whenCheckGroupCanNotDoCourse_thenReturnFalse() {
		boolean possibilityExpected = false;

		boolean possibilityActual = lectureService.isGroupCanDoCourse(dataHelper.getSillyGroup(),
				dataHelper.getCorrestLectureForTest().getCourse());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenCheckTeacherCanDoCourse_whenCheckTeacherCanDoCourse_thenReturnTrue() {
		boolean possibilityExpected = true;

		boolean possibilityActual = lectureService.isTeacherCanDoCourse(
				dataHelper.getCorrestLectureForTest().getTeacher(), dataHelper.getCorrestLectureForTest().getCourse());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenCheckTeacherCanNotDoCourse_whenCheckTeacherCanNotDoCourse_thenReturnFalse() {
		boolean possibilityExpected = false;

		boolean possibilityActual = lectureService.isTeacherCanDoCourse(dataHelper.getSpecialTeacher(),
				dataHelper.getCorrestLectureForTest().getCourse());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenAddLectureWithAvaliableClassroom_whenAddExistingLectureWithAvaliableClassroom_thenAddLecture() {
		when(lectureDao.findByDailyScheduleId(dataHelper.getLectureWithAvaliableClassroom().getDailyScheduleId()))
				.thenReturn(dataHelper.getLectures().subList(8, 10));
		when(dailyScheduleDao.findById(dataHelper.getLectureWithAvaliableClassroom().getDailyScheduleId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));
		when(courseDao.findById(dataHelper.getLectureWithAvaliableClassroom().getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(classroomDao.findById(dataHelper.getLectureWithAvaliableClassroom().getClassRoom().getId()))
				.thenReturn(Optional.of(dataHelper.getClassroomForCreate()));
		when(groupDao.findById(dataHelper.getLectureWithAvaliableClassroom().getGroup().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(teacherDao.findById(dataHelper.getLectureWithAvaliableClassroom().getTeacher().getId()))
				.thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		lectureService.add(dataHelper.getLectureWithAvaliableClassroom());

		verify(lectureDao, times(1))
				.findByDailyScheduleId(dataHelper.getLectureWithAvaliableClassroom().getDailyScheduleId());
		verify(dailyScheduleDao, times(1)).findById(dataHelper.getLectureWithAvaliableClassroom().getDailyScheduleId());
		verify(courseDao, times(1)).findById(dataHelper.getLectureWithAvaliableClassroom().getCourse().getId());
		verify(classroomDao, times(1)).findById(dataHelper.getLectureWithAvaliableClassroom().getClassRoom().getId());
		verify(groupDao, times(1)).findById(dataHelper.getLectureWithAvaliableClassroom().getGroup().getId());
		verify(teacherDao, times(1)).findById(dataHelper.getLectureWithAvaliableClassroom().getTeacher().getId());
		verify(lectureDao, times(1)).create(dataHelper.getLectureWithAvaliableClassroom());
	}

	@Test
	public void givenAddLectureWithNotAvaliableClassroom_whenAddExistingLectureWithNotAvaliableClassroom_thenLectureNotAdd() {
		when(lectureDao.findByDailyScheduleId(dataHelper.getLectureWithNotAvaliableClassroom().getDailyScheduleId()))
				.thenReturn(dataHelper.getLectures().subList(8, 10));
		when(dailyScheduleDao.findById(dataHelper.getLectureWithNotAvaliableClassroom().getDailyScheduleId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));
		when(courseDao.findById(dataHelper.getLectureWithNotAvaliableClassroom().getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(classroomDao.findById(dataHelper.getLectureWithNotAvaliableClassroom().getClassRoom().getId()))
				.thenReturn(Optional.of(dataHelper.getClassroomForCreate()));
		when(groupDao.findById(dataHelper.getLectureWithNotAvaliableClassroom().getGroup().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(teacherDao.findById(dataHelper.getLectureWithNotAvaliableClassroom().getTeacher().getId()))
				.thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		lectureService.add(dataHelper.getLectureWithNotAvaliableClassroom());

		verify(lectureDao, times(1))
				.findByDailyScheduleId(dataHelper.getLectureWithNotAvaliableClassroom().getDailyScheduleId());
		verify(dailyScheduleDao, times(1))
				.findById(dataHelper.getLectureWithNotAvaliableClassroom().getDailyScheduleId());
		verify(courseDao, times(1)).findById(dataHelper.getLectureWithNotAvaliableClassroom().getCourse().getId());
		verify(classroomDao, times(1))
				.findById(dataHelper.getLectureWithNotAvaliableClassroom().getClassRoom().getId());
		verify(groupDao, times(1)).findById(dataHelper.getLectureWithNotAvaliableClassroom().getGroup().getId());
		verify(teacherDao, times(1)).findById(dataHelper.getLectureWithNotAvaliableClassroom().getTeacher().getId());
		verify(lectureDao, times(0)).create(dataHelper.getLectureWithNotAvaliableClassroom());
	}

	@Test
	public void givenAddLectureWithAvaliableGroup_whenAddExistingLectureWithAvaliableGroup_thenAddLecture() {
		when(lectureDao.findByDailyScheduleId(dataHelper.getLectureWithAvaliableGroup().getDailyScheduleId()))
				.thenReturn(dataHelper.getLectures().subList(8, 10));
		when(dailyScheduleDao.findById(dataHelper.getLectureWithAvaliableGroup().getDailyScheduleId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));
		when(courseDao.findById(dataHelper.getLectureWithAvaliableGroup().getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(classroomDao.findById(dataHelper.getLectureWithAvaliableGroup().getClassRoom().getId()))
				.thenReturn(Optional.of(dataHelper.getClassroomForCreate()));
		when(groupDao.findById(dataHelper.getLectureWithAvaliableGroup().getGroup().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(teacherDao.findById(dataHelper.getLectureWithAvaliableGroup().getTeacher().getId()))
				.thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		lectureService.add(dataHelper.getLectureWithAvaliableGroup());

		verify(lectureDao, times(1))
				.findByDailyScheduleId(dataHelper.getLectureWithAvaliableGroup().getDailyScheduleId());
		verify(lectureDao, times(1)).create(dataHelper.getLectureWithAvaliableGroup());
	}

	@Test
	public void givenAddLectureWithNotAvaliableGroup_whenAddExistingLectureWithNotAvaliableGroup_thenLectureNotAdd() {
		when(lectureDao.findByDailyScheduleId(dataHelper.getLectureWithNotAvaliableGroup().getDailyScheduleId()))
				.thenReturn(dataHelper.getLectures().subList(8, 10));
		when(dailyScheduleDao.findById(dataHelper.getLectureWithNotAvaliableGroup().getDailyScheduleId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));
		when(courseDao.findById(dataHelper.getLectureWithNotAvaliableGroup().getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(classroomDao.findById(dataHelper.getLectureWithNotAvaliableGroup().getClassRoom().getId()))
				.thenReturn(Optional.of(dataHelper.getClassroomForCreate()));
		when(groupDao.findById(dataHelper.getLectureWithNotAvaliableGroup().getGroup().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(teacherDao.findById(dataHelper.getLectureWithNotAvaliableGroup().getTeacher().getId()))
				.thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		lectureService.add(dataHelper.getLectureWithNotAvaliableGroup());

		verify(lectureDao, times(1))
				.findByDailyScheduleId(dataHelper.getLectureWithNotAvaliableGroup().getDailyScheduleId());
		verify(lectureDao, times(0)).create(dataHelper.getLectureWithNotAvaliableGroup());
	}

	@Test
	public void givenAddLectureWithAvaliableTeacher_whenAddExistingLectureWithAvaliableTeacher_thenAddLecture() {
		when(lectureDao.findByDailyScheduleId(dataHelper.getCorrestLectureForTest().getDailyScheduleId()))
				.thenReturn(dataHelper.getLectures().subList(8, 10));
		when(dailyScheduleDao.findById(dataHelper.getCorrestLectureForTest().getDailyScheduleId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));
		when(courseDao.findById(dataHelper.getCorrestLectureForTest().getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(classroomDao.findById(dataHelper.getCorrestLectureForTest().getClassRoom().getId()))
				.thenReturn(Optional.of(dataHelper.getClassroomForCreate()));
		when(groupDao.findById(dataHelper.getCorrestLectureForTest().getGroup().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(teacherDao.findById(dataHelper.getCorrestLectureForTest().getTeacher().getId()))
				.thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		lectureService.add(dataHelper.getCorrestLectureForTest());

		verify(lectureDao, times(1)).findByDailyScheduleId(dataHelper.getCorrestLectureForTest().getDailyScheduleId());
		verify(dailyScheduleDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getDailyScheduleId());
		verify(courseDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getCourse().getId());
		verify(classroomDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getClassRoom().getId());
		verify(groupDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getGroup().getId());
		verify(teacherDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getTeacher().getId());
		verify(lectureDao, times(1)).create(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenAddLectureWithNotAvaliableTeacher_whenAddExistingLectureWithNotAvaliableTeacher_thenLectureNotAdd() {
		when(lectureDao.findByDailyScheduleId(dataHelper.getWrongLectureForTest().getDailyScheduleId()))
				.thenReturn(dataHelper.getLectures().subList(8, 10));
		when(dailyScheduleDao.findById(dataHelper.getWrongLectureForTest().getDailyScheduleId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));
		when(courseDao.findById(dataHelper.getWrongLectureForTest().getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(classroomDao.findById(dataHelper.getWrongLectureForTest().getClassRoom().getId()))
				.thenReturn(Optional.of(dataHelper.getClassroomForCreate()));
		when(groupDao.findById(dataHelper.getWrongLectureForTest().getGroup().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(teacherDao.findById(dataHelper.getWrongLectureForTest().getTeacher().getId()))
				.thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		lectureService.add(dataHelper.getWrongLectureForTest());

		verify(lectureDao, times(1)).findByDailyScheduleId(dataHelper.getWrongLectureForTest().getDailyScheduleId());
		verify(dailyScheduleDao, times(1)).findById(dataHelper.getWrongLectureForTest().getDailyScheduleId());
		verify(courseDao, times(1)).findById(dataHelper.getWrongLectureForTest().getCourse().getId());
		verify(classroomDao, times(1)).findById(dataHelper.getWrongLectureForTest().getClassRoom().getId());
		verify(groupDao, times(1)).findById(dataHelper.getWrongLectureForTest().getGroup().getId());
		verify(teacherDao, times(1)).findById(dataHelper.getWrongLectureForTest().getTeacher().getId());
		verify(lectureDao, times(0)).create(dataHelper.getWrongLectureForTest());
	}

	@Test
	public void givenAddExistingLecture_whenAddExistingLecture_thenLectureNotAdd() {
		when(lectureDao.findById(dataHelper.getCorrestLectureForTest().getId()))
				.thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));
		when(dailyScheduleDao.findById(dataHelper.getCorrestLectureForTest().getDailyScheduleId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));
		when(courseDao.findById(dataHelper.getCorrestLectureForTest().getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(classroomDao.findById(dataHelper.getCorrestLectureForTest().getClassRoom().getId()))
				.thenReturn(Optional.of(dataHelper.getClassroomForCreate()));
		when(groupDao.findById(dataHelper.getCorrestLectureForTest().getGroup().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(teacherDao.findById(dataHelper.getCorrestLectureForTest().getTeacher().getId()))
				.thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		lectureService.add(dataHelper.getCorrestLectureForTest());

		verify(lectureDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getId());
		verify(lectureDao, times(0)).create(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenAddLectureIsNotExist_whenAddLectureIsNotExist_thenAddLecture() {
		when(lectureDao.findById(dataHelper.getCorrestLectureForTest().getId())).thenReturn(Optional.empty());
		when(dailyScheduleDao.findById(dataHelper.getCorrestLectureForTest().getDailyScheduleId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));
		when(courseDao.findById(dataHelper.getCorrestLectureForTest().getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(classroomDao.findById(dataHelper.getCorrestLectureForTest().getClassRoom().getId()))
				.thenReturn(Optional.of(dataHelper.getClassroomForCreate()));
		when(groupDao.findById(dataHelper.getCorrestLectureForTest().getGroup().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(teacherDao.findById(dataHelper.getCorrestLectureForTest().getTeacher().getId()))
				.thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		lectureService.add(dataHelper.getCorrestLectureForTest());

		verify(lectureDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getId());
		verify(lectureDao, times(1)).create(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenUpdateLecture_whenUpdateLecture_thenUpdateLecture() {
		when(lectureDao.findById(dataHelper.getCorrestLectureForTest().getId())).thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));
		when(dailyScheduleDao.findById(dataHelper.getCorrestLectureForTest().getDailyScheduleId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));
		when(courseDao.findById(dataHelper.getCorrestLectureForTest().getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(classroomDao.findById(dataHelper.getCorrestLectureForTest().getClassRoom().getId()))
				.thenReturn(Optional.of(dataHelper.getClassroomForCreate()));
		when(groupDao.findById(dataHelper.getCorrestLectureForTest().getGroup().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(teacherDao.findById(dataHelper.getCorrestLectureForTest().getTeacher().getId()))
				.thenReturn(Optional.of(dataHelper.getTeacherForTest()));


		lectureService.update(dataHelper.getCorrestLectureForTest());

		verify(lectureDao, times(1)).findByDailyScheduleId(dataHelper.getCorrestLectureForTest().getDailyScheduleId());
		verify(dailyScheduleDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getDailyScheduleId());
		verify(courseDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getCourse().getId());
		verify(classroomDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getClassRoom().getId());
		verify(groupDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getGroup().getId());
		verify(teacherDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getTeacher().getId());
		verify(lectureDao, times(1)).update(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenDeleteLecture_whenDeleteLecture_thenDeleteLecture() {
		when(lectureDao.findById(dataHelper.getCorrestLectureForTest().getId())).thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));

		lectureService.delete(dataHelper.getCorrestLectureForTest());

		verify(lectureDao, times(1)).delete(dataHelper.getCorrestLectureForTest().getId());
		verify(lectureDao, times(1)).findById(dataHelper.getCorrestLectureForTest().getId());
	}

	@Test
	public void givenGetAllLectures_whenGetAllLectures_thenGetAllLectures() {
		List<Lecture> expected = dataHelper.getLectures();
		when(lectureDao.findAll()).thenReturn(dataHelper.getLectures());

		List<Lecture> actual = lectureService.getAll();

		assertThat(actual).isEqualTo(expected);
		verify(lectureDao, times(1)).findAll();
	}

	@Test
	public void givenGetLectureById_whenGetLectureById_thenGetLectureById() {
		Optional<Lecture> expected = Optional.of(dataHelper.getCorrestLectureForTest());
		when(lectureDao.findById(9)).thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));

		Optional<Lecture> actual = lectureService.getById(9);

		assertThat(actual).isEqualTo(expected);
		verify(lectureDao, times(1)).findById(9);
	}

	@Test
	public void givenGetLecturesByDailyScheduleId_whenGetLecturesByDailyScheduleId_thenGetLecturesByDailyScheduleId() {
		List<Lecture> expected = dataHelper.getLectures().subList(0, 1);
		when(lectureDao.findByDailyScheduleId(1)).thenReturn(dataHelper.getLectures().subList(0, 1));

		List<Lecture> actual = lectureService.getByDailyScheduleId(1);

		assertThat(actual).isEqualTo(expected);
		verify(lectureDao, times(1)).findByDailyScheduleId(1);
	}
}
