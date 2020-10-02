package ru.stepev.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.stepev.dao.rowmapper.DailyScheduleRowMapper;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;

@Component
public class DailyScheduleDao {

	private static final String CREATE_DAILYSCHEDUALE_QUERY = "INSERT INTO dailyschedule (dailyschedule_date) VALUES (?)";
	private static final String GET_ALL = "SELECT * FROM dailyschedule";
	private static final String FIND_BY_DATE = "SELECT * FROM dailyschedule WHERE dailyschedule_date = ?";
	private static final String FIND_BY_SCHEDULE_ID = "SELECT * FROM dailyschedule WHERE id = ?";
	private static final String DELETE_DAILYSCHEDUALE_BY_ID = "DELETE FROM dailyschedule WHERE id = ?";
	private static final String UPDATE_BY_LECTURE_ID = "UPDATE dailyschedule SET dailyschedule_date = ? WHERE id = ?";

	private LectureDao lectureDao;
	private DailyScheduleRowMapper dailyScheduleRowMapper;
	private JdbcTemplate jdbcTemplate;

	public static <T> Predicate<T> not(Predicate<T> t) {
		return t.negate();
	}

	public DailyScheduleDao(JdbcTemplate jdbcTemplate, DailyScheduleRowMapper dailyScheduleRowMapper,
			LectureDao lectureDao) {
		this.jdbcTemplate = jdbcTemplate;
		this.dailyScheduleRowMapper = dailyScheduleRowMapper;
		this.lectureDao = lectureDao;
	}

	@Transactional
	public void create(DailySchedule dailySchedule) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_DAILYSCHEDUALE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setObject(1, dailySchedule.getDate());
			return statement;
		}, keyHolder);
		dailySchedule.setId((int) keyHolder.getKeys().get("id"));
		dailySchedule.getLectures().forEach(lectureDao::create);
	}

	@Transactional
	public void update(DailySchedule dailySchedule) {
		jdbcTemplate.update(UPDATE_BY_LECTURE_ID, dailySchedule.getDate(), dailySchedule.getId());
		findById(dailySchedule.getId()).getLectures().stream().filter(not(dailySchedule.getLectures()::contains))
				.forEach(l -> lectureDao.delete(l.getId()));
		dailySchedule.getLectures().stream().filter(not(findById(dailySchedule.getId()).getLectures()::contains))
				.forEach(l -> lectureDao.create(l));
	}

	public void delete(int dailyScheduleId) {
		jdbcTemplate.update(DELETE_DAILYSCHEDUALE_BY_ID, dailyScheduleId);
	}

	public DailySchedule findById(int scheduleId) {
		try {
			return this.jdbcTemplate.queryForObject(FIND_BY_SCHEDULE_ID, dailyScheduleRowMapper, scheduleId);
		} catch (EmptyResultDataAccessException e) {
			return new DailySchedule(0, LocalDate.of(0, 1, 1));
		}
	}

	public DailySchedule findByDate(LocalDate date) {
		try {
			return this.jdbcTemplate.queryForObject(FIND_BY_DATE, dailyScheduleRowMapper, date);
		} catch (EmptyResultDataAccessException e) {
			return new DailySchedule(0, date);
		}
	}

	public List<DailySchedule> findAll() {
		try {
			return this.jdbcTemplate.query(GET_ALL, dailyScheduleRowMapper);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<>();
		}
	}

	public List<DailySchedule> findBiTeacherIdAndPeriodOfTime(int teacherId, List<LocalDate> periodOfTime) {
		List<DailySchedule> dailySchedules = new ArrayList<>();
		for (LocalDate date : periodOfTime) {
			DailySchedule dailySchedule = new DailySchedule(0, date);
			dailySchedule.setLectures(lectureDao.findLecturesByDateAndTeacherId(date, teacherId));
			if (dailySchedule.getLectures().size() > 0)
				dailySchedules.add(dailySchedule);
		}
		return dailySchedules;
	}

	public List<DailySchedule> findByGroupAndPeriodOfTime(Group group, List<LocalDate> periodOfTime) {
		List<DailySchedule> dailySchedules = new ArrayList<>();
		for (LocalDate date : periodOfTime) {
			DailySchedule dailySchedule = new DailySchedule(0, date);
			dailySchedule.setLectures(lectureDao.findByDateAndGroup(date, group));
			if (dailySchedule.getLectures().size() > 0)
				dailySchedules.add(dailySchedule);
		}
		return dailySchedules;
	}
}
