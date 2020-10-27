package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import ru.stepev.dao.ClassroomDao;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.dao.LectureDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Lecture;

@ExtendWith(MockitoExtension.class)
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

	@Mock
	private Environment environment;

	@InjectMocks
	private LectureService lectureService;

	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		dataHelper = new DataHelper();
	}

	@Test
	public void givenClassroomAndGroup_whenClassroomCanContainGroup_thenReturnTrue() {
		boolean possibilityExpected = true;

		boolean possibilityActual = lectureService.isClassroomHasQuiteCapacity(dataHelper.getClassroomForCreate(),
				dataHelper.getCorrestLectureForTest().getGroup());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenClassroomAndGroup_whenClassroomCanNotContainGroup_thenReturnFalse() {
		boolean possibilityExpected = false;

		boolean possibilityActual = lectureService.isClassroomHasQuiteCapacity(dataHelper.getClassroomSmall(),
				dataHelper.getGroupForTest());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenGroupAndCourse_whenGroupCanStudyCourse_thenReturnTrue() {
		Lecture lecture = dataHelper.getCorrestLectureForTest();
		boolean possibilityExpected = true;
		when(groupDao.findByGroupIdAndCourseId(dataHelper.getGroupForTest().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));

		boolean possibilityActual = lectureService.isGroupCanStudyCourse(dataHelper.getGroupForTest(),
				dataHelper.getCorrestLectureForTest().getCourse());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenGroupAndCourse_whenGroupCanNotStudyCourse_thenReturnFalse() {
		boolean possibilityExpected = false;

		boolean possibilityActual = lectureService.isGroupCanStudyCourse(dataHelper.getSillyGroup(),
				dataHelper.getCorrestLectureForTest().getCourse());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenTeacherAndCourse_whenTeacherCanTeachCourse_thenReturnTrue() {
		boolean possibilityExpected = true;

		boolean possibilityActual = lectureService.isTeacherCanTeachCourse(
				dataHelper.getCorrestLectureForTest().getTeacher(), dataHelper.getCorrestLectureForTest().getCourse());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenTeacherAndCourse_whenTeacherCanNotTeachCourse_thenReturnFalse() {
		boolean possibilityExpected = false;

		boolean possibilityActual = lectureService.isTeacherCanTeachCourse(dataHelper.getSpecialTeacher(),
				dataHelper.getCorrestLectureForTest().getCourse());

		assertThat(possibilityActual).isEqualTo(possibilityExpected);
	}

	@Test
	public void givenLectureWithAvaliableClassroom_whenClassroomIsAvaliable_thenAddLecture() {
		Lecture lecture = dataHelper.getLectureWithAvaliableClassroom();
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
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getTeacher().getId())).thenReturn(Optional.empty());

		lectureService.add(dataHelper.getLectureWithAvaliableClassroom());

		verify(lectureDao).create(dataHelper.getLectureWithAvaliableClassroom());
	}

	@Test
	public void givenLectureWithNotAvaliableClassroom_whenClassroomIsNotAvaliable_thenLectureNotAdd() {
		Lecture lecture = dataHelper.getLectureWithNotAvaliableClassroom();
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId()))
						.thenReturn(Optional.of(dataHelper.getLectureWithNotAvaliableClassroom()));

		lectureService.add(dataHelper.getLectureWithNotAvaliableClassroom());

		verify(lectureDao, times(0)).create(dataHelper.getLectureWithNotAvaliableClassroom());
	}

	@Test
	public void givenLectureWithAvaliableGroup_whenGroupIsAvaliable_thenAddLecture() {
		Lecture lecture = dataHelper.getLectureWithAvaliableGroup();
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
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getTeacher().getId())).thenReturn(Optional.empty());

		lectureService.add(dataHelper.getLectureWithAvaliableGroup());

		verify(lectureDao).create(dataHelper.getLectureWithAvaliableGroup());
	}

	@Test
	public void givenLectureWithNotAvaliableGroup_whenGroupIsNotAvaliable_thenLectureNotAdd() {
		Lecture lecture = dataHelper.getLectureWithNotAvaliableGroup();
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getGroup().getId()))
						.thenReturn(Optional.of(dataHelper.getLectureWithNotAvaliableClassroom()));

		lectureService.add(dataHelper.getLectureWithNotAvaliableGroup());

		verify(lectureDao, times(0)).create(dataHelper.getLectureWithNotAvaliableGroup());
	}

	@Test
	public void givenLectureWithAvaliableTeacher_whenTeacherIsAvaliable_thenAddLecture() {
		Lecture lecture = dataHelper.getCorrestLectureForTest();
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
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getTeacher().getId())).thenReturn(Optional.empty());

		lectureService.add(dataHelper.getCorrestLectureForTest());

		verify(lectureDao).create(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenLectureWithNotAvaliableTeacher_whenTeacherIsNotAvaliable_thenLectureNotAdd() {
		Lecture lecture = dataHelper.getWrongLectureForTest();
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getTeacher().getId()))
						.thenReturn(Optional.of(dataHelper.getWrongLectureForTest()));

		lectureService.add(dataHelper.getWrongLectureForTest());

		verify(lectureDao, times(0)).create(dataHelper.getWrongLectureForTest());
	}

	@Test
	public void givenExistingLecture_whenExistingLecture_thenLectureNotAdd() {
		when(lectureDao.findById(dataHelper.getCorrestLectureForTest().getId()))
				.thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));

		lectureService.add(dataHelper.getCorrestLectureForTest());

		verify(lectureDao, times(0)).create(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenLectureIsNotExist_whenLectureDoesNotExist_thenAddLecture() {
		Lecture lecture = dataHelper.getCorrestLectureForTest();
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
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getTeacher().getId())).thenReturn(Optional.empty());

		lectureService.add(dataHelper.getCorrestLectureForTest());

		verify(lectureDao).create(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenLecture_whenLectureExist_thenUpdateLecture() {
		Lecture lecture = dataHelper.getCorrestLectureForTest();
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getTeacher().getId())).thenReturn(Optional.empty());
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(environment.getProperty("durationOfLecture")).thenReturn(dataHelper.getDurationOfLecture());

		lectureService.update(dataHelper.getCorrestLectureForTest());

		verify(lectureDao).update(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenLecture_whenLectureExistAndClassroomIsBusy_thenLectureNotUpdate() {
		Lecture lecture = dataHelper.getCorrestLectureForTest();
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId()))
						.thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));

		lectureService.update(dataHelper.getCorrestLectureForTest());

		verify(lectureDao, times(0)).update(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenLecture_whenLectureExistAndGroupIsBusy_thenLectureNotUpdate() {
		Lecture lecture = dataHelper.getCorrestLectureForTest();
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getGroup().getId()))
						.thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));

		lectureService.update(dataHelper.getCorrestLectureForTest());

		verify(lectureDao, times(0)).update(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenLecture_whenLectureExistAndTeacherIsBusy_thenLectureNotUpdate() {
		Lecture lecture = dataHelper.getCorrestLectureForTest();
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusHours(1), lecture.getTeacher().getId()))
						.thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));

		lectureService.update(dataHelper.getCorrestLectureForTest());

		verify(lectureDao, times(0)).update(dataHelper.getCorrestLectureForTest());
	}

	@Test
	public void givenLecture_whenLectureExist_thenDeleteLecture() {
		when(lectureDao.findById(dataHelper.getCorrestLectureForTest().getId()))
				.thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));

		lectureService.delete(dataHelper.getCorrestLectureForTest());

		verify(lectureDao).delete(dataHelper.getCorrestLectureForTest().getId());
	}

	@Test
	public void findAllLectures_whenFindAllLectures_thenGetAllLectures() {
		List<Lecture> expected = dataHelper.getLectures();
		when(lectureDao.findAll()).thenReturn(dataHelper.getLectures());

		List<Lecture> actual = lectureService.getAll();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void givenLectureId_whenFindLectureById_thenGetLectureById() {
		Optional<Lecture> expected = Optional.of(dataHelper.getCorrestLectureForTest());
		when(lectureDao.findById(9)).thenReturn(Optional.of(dataHelper.getCorrestLectureForTest()));

		Optional<Lecture> actual = lectureService.getById(9);

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void givenDailyScheduleId_whenFindLecturesByDailyScheduleId_thenGetLecturesByDailyScheduleId() {
		List<Lecture> expected = dataHelper.getLectures().subList(0, 1);
		when(lectureDao.findByDailyScheduleId(1)).thenReturn(dataHelper.getLectures().subList(0, 1));

		List<Lecture> actual = lectureService.getByDailyScheduleId(1);

		assertThat(actual).isEqualTo(expected);
	}
}
