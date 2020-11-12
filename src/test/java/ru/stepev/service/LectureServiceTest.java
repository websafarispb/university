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
import ru.stepev.exception.ClassroomHasNotQuiteCapacity;
import ru.stepev.exception.ClassroomIsNotFreeException;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.GroupCanNotStudyCourseException;
import ru.stepev.exception.GroupIsNotFreeException;
import ru.stepev.exception.TeacherIsNotFreeException;
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
	@Mock
	private DailyScheduleService dailyScheduleSerive;
	@Mock
	private CourseService courseSerice;
	@Mock
	private ClassroomService classroomService;
	@Mock
	private GroupService groupService;
	@Mock
	private TeacherService teacherService;

	@InjectMocks
	private LectureService lectureService;

	@Test
	public void givenLectureWithAvaliableClassroom_whenAdd_thenAddLecture() {
		Lecture lecture = lectureWithAvaliableClassroom;
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.of(lecture));

		ClassroomIsNotFreeException exception = assertThrows(ClassroomIsNotFreeException.class,
				() -> lectureService.add(lecture));

		assertThat(exception.getMessage()).isEqualTo("Classroom with address %s is not free",
				lecture.getClassRoom().getAddress());
		verify(lectureDao, never()).create(lecture);
	}

	@Test
	public void givenLectureWithAvaliableGroup_whenAdd_thenAddLecture() {
		Lecture lecture = lectureWithAvaliableGroup;
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId()))
						.thenReturn(Optional.of(lectureWithNotAvaliableClassroom));

		GroupIsNotFreeException exception = assertThrows(GroupIsNotFreeException.class,
				() -> lectureService.add(lecture));

		assertThat(exception.getMessage()).isEqualTo("Group name %s is not free", lecture.getGroup().getName());
		verify(lectureDao, never()).create(lecture);
	}

	@Test
	public void givenLectureWithAvaliableTeacher_whenAdd_thenAddLecture() {
		Lecture lecture = correstLectureForTest;
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.of(lecture));

		TeacherIsNotFreeException exception = assertThrows(TeacherIsNotFreeException.class,
				() -> lectureService.add(lecture));

		assertThat(exception.getMessage()).isEqualTo("Teacher name %s is not free", lecture.getTeacher().getLastName());
		verify(lectureDao, never()).create(lecture);
	}

	@Test
	public void givenExistingLecture_whenAdd_thenLectureNotAdd() {
		Lecture lecture = correstLectureForTest;
		when(lectureDao.findById(correstLectureForTest.getId())).thenReturn(Optional.of(correstLectureForTest));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> lectureService.add(lecture));

		assertThat(exception.getMessage()).isEqualTo("Lecture with ID %s already exist", lecture.getId());
		verify(lectureDao, never()).create(correstLectureForTest);
	}

	@Test
	public void givenLectureIsNotExist_whenAdd_thenAddLecture() {
		Lecture lecture = correstLectureForTest;
		when(lectureDao.findById(lecture.getId())).thenReturn(Optional.empty());
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
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.of(lecture));

		ClassroomIsNotFreeException exception = assertThrows(ClassroomIsNotFreeException.class,
				() -> lectureService.update(lecture));

		assertThat(exception.getMessage()).isEqualTo("Classroom with address %s is not free",
				lecture.getClassRoom().getAddress());
		verify(lectureDao, never()).update(lecture);
	}

	@Test
	public void givenLecture_whenUpdateLectureExistAndGroupIsBusy_thenLectureNotUpdate() {
		Lecture lecture = correstLectureForTest;
		when(lectureDao.findById(lecture.getId())).thenReturn(Optional.of(lecture));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.of(lecture));

		GroupIsNotFreeException exception = assertThrows(GroupIsNotFreeException.class,
				() -> lectureService.update(lecture));

		assertThat(exception.getMessage()).isEqualTo("Group name %s is not free", lecture.getGroup().getName());
		verify(lectureDao, never()).update(lecture);
	}

	@Test
	public void givenLecture_whenUpdateLectureExistAndTeacherIsBusy_thenLectureNotUpdate() {
		Lecture lecture = correstLectureForTest;
		when(lectureDao.findById(lecture.getId())).thenReturn(Optional.of(lecture));
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.of(lecture));

		TeacherIsNotFreeException exception = assertThrows(TeacherIsNotFreeException.class,
				() -> lectureService.update(lecture));

		assertThat(exception.getMessage()).isEqualTo("Teacher name %s is not free", lecture.getTeacher().getLastName());
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

	@Test
	public void givenClassroomWithNotQuiteCapacity_whenAddLecture_thenNotAddLecture() {
		Lecture lecture = lectureWithSmallClassroom;
		when(groupDao.findByGroupIdAndCourseId(lecture.getGroup().getId(), lecture.getCourse().getId()))
				.thenReturn(Optional.of(lecture.getGroup()));
		when(lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getGroup().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getClassRoom().getId())).thenReturn(Optional.empty());
		when(lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(0), lecture.getTeacher().getId())).thenReturn(Optional.empty());

		ClassroomHasNotQuiteCapacity exception = assertThrows(ClassroomHasNotQuiteCapacity.class,
				() -> lectureService.add(lecture));

		assertThat(exception.getMessage()).isEqualTo("Classroom with address %s doesn't have quite capacity",
				lecture.getClassRoom().getAddress());
		verify(lectureDao, never()).create(lecture);
	}

	@Test
	public void givenLectureWithNotExistFilds_whenAdd_thenNotAddLecture() {
		Lecture lecture = correstLectureForTest;

		GroupCanNotStudyCourseException exception = assertThrows(GroupCanNotStudyCourseException.class,
				() -> lectureService.add(lecture));

		assertThat(exception.getMessage()).isEqualTo("Group with name %s can't study course %s",
				lecture.getGroup().getName(), lecture.getCourse().getName());
		verify(lectureDao, never()).create(lecture);
	}
}
