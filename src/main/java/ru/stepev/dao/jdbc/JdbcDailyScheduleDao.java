package ru.stepev.dao.jdbc;

import static java.util.stream.Collectors.toList;
import static java.util.function.Predicate.not;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.LectureDao;
import ru.stepev.dao.jdbc.rowmapper.DailyScheduleRowMapper;
import ru.stepev.exception.EntityCouldNotBeenCreatedException;
import ru.stepev.exception.EntityCouldNotBeenDeletedException;
import ru.stepev.exception.EntityCouldNotBeenUpdatedException;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.utils.Paginator;

@Component
@Slf4j
public class JdbcDailyScheduleDao implements DailyScheduleDao {

	private static final String CREATE_DAILYSCHEDUALE_QUERY = "INSERT INTO dailyschedule (dailyschedule_date) VALUES (?)";
	private static final String GET_ALL = "SELECT * FROM dailyschedule";
	private static final String GET_ALL_BY_TIME_PERIOD = "SELECT * FROM dailyschedule WHERE dailyschedule_date >= ? AND dailyschedule_date <= ? ";
	private static final String FIND_BY_DATE = "SELECT * FROM dailyschedule WHERE dailyschedule_date = ?";
	private static final String FIND_BY_SCHEDULE_ID = "SELECT * FROM dailyschedule WHERE id = ?";
	private static final String DELETE_DAILYSCHEDUALE_BY_ID = "DELETE FROM dailyschedule WHERE id = ?";
	private static final String UPDATE_BY_LECTURE_ID = "UPDATE dailyschedule SET dailyschedule_date = ? WHERE id = ?";
	private static final String FIND_NUMBER_OF_DAILYSCHEDULE = "SELECT COUNT(*) FROM dailyschedule";
	private static final String FIND_AND_SORT_BY_ID = "SELECT * FROM dailyschedule ORDER BY id ASC LIMIT ? OFFSET ?";
	private static final String FIND_AND_SORT_BY_DATE = "SELECT * FROM dailyschedule ORDER BY dailyschedule_date, id ASC LIMIT ? OFFSET ?";

	private LectureDao lectureDao;
	private DailyScheduleRowMapper dailyScheduleRowMapper;
	private JdbcTemplate jdbcTemplate;

	public JdbcDailyScheduleDao(JdbcTemplate jdbcTemplate, DailyScheduleRowMapper dailyScheduleRowMapper,
			LectureDao lectureDao) {
		this.jdbcTemplate = jdbcTemplate;
		this.dailyScheduleRowMapper = dailyScheduleRowMapper;
		this.lectureDao = lectureDao;
	}

	@Transactional
	public void create(DailySchedule dailySchedule) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_DAILYSCHEDUALE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setObject(1, dailySchedule.getDate());
			return statement;
		}, keyHolder) == 0) {
			throw new EntityCouldNotBeenCreatedException(
					String.format("DailySchedule with getAddress %s could not been created", dailySchedule.getDate()));
		}
		dailySchedule.setId((int) keyHolder.getKeys().get("id"));
	//	dailySchedule.getLectures().forEach(lectureDao::create);
	}

	@Transactional
	public void update(DailySchedule dailySchedule) {
		if (jdbcTemplate.update(UPDATE_BY_LECTURE_ID, dailySchedule.getDate(), dailySchedule.getId()) != 0) {
			Optional<DailySchedule> dailyScheduleUpdated = findById(dailySchedule.getId());
			dailyScheduleUpdated.get().getLectures().stream().filter(not(dailySchedule.getLectures()::contains))
					.forEach(l -> lectureDao.delete(l.getId()));
			dailySchedule.getLectures().stream().filter(not(dailyScheduleUpdated.get().getLectures()::contains))
					.forEach(l -> lectureDao.create(l));
		} else {
			throw new EntityCouldNotBeenUpdatedException(
					String.format("DailySchedule with date %s could not been updated", dailySchedule.getDate()));
		}
	}

	public void delete(int dailyScheduleId) {
		if (jdbcTemplate.update(DELETE_DAILYSCHEDUALE_BY_ID, dailyScheduleId) == 0) {
			throw new EntityCouldNotBeenDeletedException(
					String.format("DailySchedule with date %s could not been deleted", dailyScheduleId));
		}

	}

	public Optional<DailySchedule> findById(int scheduleId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_SCHEDULE_ID, dailyScheduleRowMapper, scheduleId));
		} catch (EmptyResultDataAccessException e) {
			log.warn("DailySchedule with ID {} was not found", scheduleId);
			return Optional.empty();
		}
	}

	public Optional<DailySchedule> findByDate(LocalDate date) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_DATE, dailyScheduleRowMapper, date));
		} catch (EmptyResultDataAccessException e) {
			log.warn("DailySchedule with date {} was not found", date);
			return Optional.empty();
		}
	}

	public List<DailySchedule> findAll() {
		log.debug("Finding all DailySchedules...");
		return jdbcTemplate.query(GET_ALL, dailyScheduleRowMapper);
	}

	public List<DailySchedule> findAllByDatePeriod(LocalDate firstDate, LocalDate lastDate) {
		log.debug("Finding all DailySchedules by Date period ...");
		Object[] objects = new Object[] { firstDate, lastDate };
		return jdbcTemplate.query(GET_ALL_BY_TIME_PERIOD, objects, dailyScheduleRowMapper);
	}

	public List<DailySchedule> findByTeacherIdAndPeriodOfTime(int teacherId, LocalDate firstDate, LocalDate lastDate) {
		log.debug("Finding all DailySchedules by teacher and period of time ...");
		List<DailySchedule> allDailySchedules = findAllByDatePeriod(firstDate, lastDate);
		List<DailySchedule> dailySchedules = new ArrayList<>();
		for (DailySchedule dailySchedule : allDailySchedules) {
			dailySchedule.setLectures(dailySchedule.getLectures().stream()
					.filter(l -> l.getTeacher().getId() == teacherId).collect(toList()));
			if (!dailySchedule.getLectures().isEmpty())
				dailySchedules.add(dailySchedule);
		}
		return dailySchedules;
	}

	public List<DailySchedule> findByGroupAndPeriodOfTime(Group group, LocalDate firstDate, LocalDate lastDate) {
		log.debug("Finding all DailySchedules by group and period of time ...");
		List<DailySchedule> allDailySchedules = findAllByDatePeriod(firstDate, lastDate);
		List<DailySchedule> dailySchedules = new ArrayList<>();
		for (DailySchedule dailySchedule : allDailySchedules) {
			dailySchedule.setLectures(dailySchedule.getLectures().stream()
					.filter(l -> l.getGroup().getId() == group.getId()).collect(toList()));
			if (!dailySchedule.getLectures().isEmpty())
				dailySchedules.add(dailySchedule);
		}
		return dailySchedules;
	}

	@Override
	public int findNumberOfItems() {
		log.debug("Counting number of dailyschedule ... ");
		return this.jdbcTemplate.queryForObject(FIND_NUMBER_OF_DAILYSCHEDULE, Integer.class);
	}

	@Override
	public List<DailySchedule> findAndSortByDate(int numberOfItems, int offset) {
		log.debug("Finding and sorting dailyschedule by date ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_DATE, objects, dailyScheduleRowMapper);
	}

	@Override
	public List<DailySchedule> findAndSortById(int numberOfItems, int offset) {
		log.debug("Finding and sorting dailyschedule by Id ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_ID, objects, dailyScheduleRowMapper);
	}

	@Override
	public List<DailySchedule> findAndSortedByTeacherIdAndPeriodOfDate(int teacherId, LocalDate firstDay,
			LocalDate lastDay, Paginator paginator) {
		List<DailySchedule> dailySchedules = findByTeacherIdAndPeriodOfTime(teacherId, firstDay, lastDay).stream()
		.skip(paginator.getOffset()).limit(paginator.getItemsPerPage()).collect(toList());
		paginator.setNumberOfEntities(dailySchedules.size());
		return dailySchedules;
	}

	@Override
	public List<DailySchedule> findAndSortedByGroupAndPeriodOfDate(Group group, LocalDate firstDay, LocalDate lastDay,
			Paginator paginator) {
		List<DailySchedule> dailySchedules = findByGroupAndPeriodOfTime(group, firstDay, lastDay).stream().skip(paginator.getOffset())
				.limit(paginator.getItemsPerPage()).collect(toList());
		paginator.setNumberOfEntities(dailySchedules.size());
		return dailySchedules;
	}
}
