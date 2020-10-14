package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.stepev.config.TestConfig;
import ru.stepev.dao.LectureDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Lecture;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class LectureServiceTest {

	@Mock
	private LectureDao lectureDao;

	@InjectMocks
	private LectureService lectureService;

	@Autowired
	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenAddLecture_whenAddLecture_thenAddLecture() {

		lectureService.add(dataHelper.getLectureForTest());

		verify(lectureDao, times(1)).create(dataHelper.getLectureForTest());
	}

	@Test
	public void givenUpdateLecture_whenUpdateLecture_thenUpdateLecture() {

		lectureService.update(dataHelper.getLectureForTest());

		verify(lectureDao, times(1)).update(dataHelper.getLectureForTest());
	}

	@Test
	public void givenDeleteLecture_whenDeleteLecture_thenDeleteLecture() {

		lectureService.delete(dataHelper.getLectureForTest().getId());

		verify(lectureDao, times(1)).delete(dataHelper.getLectureForTest().getId());
		;
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
		Optional<Lecture> expected = Optional.of(dataHelper.getLectureForTest());
		when(lectureDao.findById(9)).thenReturn(Optional.of(dataHelper.getLectureForTest()));

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
