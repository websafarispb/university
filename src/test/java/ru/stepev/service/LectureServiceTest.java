package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.ClassroomDao;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.dao.LectureDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Lecture;

import static ru.stepev.data.DataTest.*;

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

	@InjectMocks
	private LectureService lectureService;

	@Test
	public void givenLectureWithAvaliableClassroom_whenAdd_thenAddLecture() {
		Lecture lecture = lectureWithAvaliableClassroom;
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.empty());

		lectureService.add(lecture);

		verify(lectureDao).create(lecture);
	}

	@Test
	public void givenLectureWithNotAvaliableClassroom_whenAdd_thenLectureNotAdd() {
		Lecture lecture = lectureWithNotAvaliableClassroom;
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.of(lecture));
		
		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> lectureService.add(lecture));

		assertThat(exception.getMessage()).isEqualTo("Can not create lecture with time %s lecture already exist",
				lecture.getTime());
		verify(lectureDao, never()).create(lecture);
	}

	@Test
	public void givenLectureWithAvaliableGroup_whenAdd_thenAddLecture() {
		Lecture lecture = lectureWithAvaliableGroup;
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.empty());

		lectureService.add(lecture);

		verify(lectureDao).create(lecture);
	}

	@Test
	public void givenLectureWithNotAvaliableGroup_whenAdd_thenLectureNotAdd() {
		Lecture lecture = lectureWithNotAvaliableGroup;
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId()))
						.thenReturn(Optional.of(lectureWithNotAvaliableClassroom));
		
		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> lectureService.add(lecture));

		assertThat(exception.getMessage()).isEqualTo("Can not create lecture with time %s lecture already exist",
				lecture.getTime());
		verify(lectureDao, never()).create(lecture);
	}

	@Test
	public void givenLectureWithAvaliableTeacher_whenAdd_thenAddLecture() {
		Lecture lecture = correstLectureForTest;
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(correstLectureForTest.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.empty());

		lectureService.add(lecture);

		verify(lectureDao).create(lecture);
	}

	@Test
	public void givenLectureWithNotAvaliableTeacher_whenAdd_thenLectureNotAdd() {
		Lecture lecture = wrongLectureForTest;
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.of(lecture));
		
		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> lectureService.add(lecture));

		assertThat(exception.getMessage()).isEqualTo("Can not create lecture with time %s lecture already exist",
				lecture.getTime());
		verify(lectureDao, never()).create(lecture);
	}

	@Test
	public void givenExistingLecture_whenAdd_thenLectureNotAdd() {
		when(lectureDao.findById(correstLectureForTest.getId())).thenReturn(Optional.of(correstLectureForTest));
		
		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> lectureService.add(correstLectureForTest));

		assertThat(exception.getMessage()).isEqualTo("Can not create lecture with time %s lecture already exist",
				correstLectureForTest.getTime());
		verify(lectureDao, never()).create(correstLectureForTest);
	}

	@Test
	public void givenLectureIsNotExist_whenAdd_thenAddLecture() {
		Lecture lecture = correstLectureForTest;
		when(lectureDao.findById(lecture.getId())).thenReturn(Optional.empty());
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.empty());

		lectureService.add(lecture);

		verify(lectureDao).create(lecture);
	}

	@Test
	public void givenExistingLecture_whenUpdate_thenUpdateLecture() {
		Lecture lecture = correstLectureForTest;
		when(lectureDao.findById(lecture.getId())).thenReturn(Optional.of(lecture));
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.empty());
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));

		lectureService.update(lecture);

		verify(lectureDao).update(lecture);
	}

	@Test
	public void givenLecture_whenUpdateLectureExistAndClassroomIsBusy_thenLectureNotUpdate() {
		Lecture lecture = correstLectureForTest;
		when(lectureDao.findById(lecture.getId())).thenReturn(Optional.of(lecture));
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.of(lecture));
	
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> lectureService.update(lecture));

		assertThat(exception.getMessage()).isEqualTo("Can not update lecture with time %s lecture doesn't exist",
				lecture.getTime());
		verify(lectureDao, never()).update(lecture);
	}

	@Test
	public void givenLecture_whenUpdateLectureExistAndGroupIsBusy_thenLectureNotUpdate() {
		Lecture lecture = correstLectureForTest;
		when(lectureDao.findById(lecture.getId())).thenReturn(Optional.of(lecture));
		when(dailyScheduleDao.findById(lecture.getDailyScheduleId())).thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(correstLectureForTest.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(correstLectureForTest.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.of(lecture));
		
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> lectureService.update(lecture));

		assertThat(exception.getMessage()).isEqualTo("Can not update lecture with time %s lecture doesn't exist",
				lecture.getTime());
		verify(lectureDao, never()).update(lecture);
	}

	@Test
	public void givenLecture_whenUpdateLectureExistAndTeacherIsBusy_thenLectureNotUpdate() {
		Lecture lecture = correstLectureForTest;
		when(lectureDao.findById(lecture.getId())).thenReturn(Optional.of(lecture));
		when(dailyScheduleDao.findById(correstLectureForTest.getDailyScheduleId()))
				.thenReturn(Optional.of(dailyScheduleForCreate));
		when(courseDao.findById(lecture.getCourse().getId())).thenReturn(Optional.of(courseForTest));
		when(classroomDao.findById(lecture.getClassRoom().getId())).thenReturn(Optional.of(classroomForCreate));
		when(groupDao.findById(lecture.getGroup().getId())).thenReturn(Optional.of(groupForTest));
		when(teacherDao.findById(lecture.getTeacher().getId())).thenReturn(Optional.of(teacherForTest));
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.of(lecture));
		
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> lectureService.update(lecture));

		assertThat(exception.getMessage()).isEqualTo("Can not update lecture with time %s lecture doesn't exist",
				lecture.getTime());
		verify(lectureDao, never()).update(lecture);
	}

	@Test
	public void givenLecture_whenDeleteLectureExist_thenDeleteLecture() {
		when(lectureDao.findById(correstLectureForTest.getId())).thenReturn(Optional.of(correstLectureForTest));

		lectureService.delete(correstLectureForTest);

		verify(lectureDao).delete(correstLectureForTest.getId());
	}

	@Test
	public void findAllLectures_whenGetAll_thenGetAllLectures() {
		when(lectureDao.findAll()).thenReturn(expectedLectures);

		List<Lecture> actualLectures = lectureService.getAll();

		assertThat(actualLectures).isEqualTo(expectedLectures);
	}

	@Test
	public void givenLectureId_whenGetById_thenGetLectureById() {
		Optional<Lecture> expected = Optional.of(correstLectureForTest);
		when(lectureDao.findById(9)).thenReturn(Optional.of(correstLectureForTest));

		Optional<Lecture> actual = lectureService.getById(9);

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void givenDailyScheduleId_whenGetByDailyScheduleId_thenGetLecturesByDailyScheduleId() {
		List<Lecture> expected = expectedLectures.subList(0, 1);
		when(lectureDao.findByDailyScheduleId(1)).thenReturn(expectedLectures.subList(0, 1));

		List<Lecture> actual = lectureService.getByDailyScheduleId(1);

		assertThat(actual).isEqualTo(expected);
	}
}
