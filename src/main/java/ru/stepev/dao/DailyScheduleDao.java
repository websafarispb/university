package ru.stepev.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.stepev.dao.rowmapper.DailyScheduleRowMapper;
import ru.stepev.dao.rowmapper.LectureRowMapper;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;

@Component
public class DailyScheduleDao {

	private static final String CREATE_DAILYSCHEDUALE_QUERY = "INSERT INTO dailyschedule (dailyschedule_date) VALUES (?)";
	private static final String ASSIGN_LECTURE = "INSERT INTO dailyschedules_lectures (dailyschedule_id, lecture_id) VALUES (?, ?)";
	private static final String REMOVE_LECTURE = "DELETE * FROM dailyschedules_lectures WHERE dailyschedule_id = ? AND lecture_id = ?";
	private static final String GET_ALL = "SELECT * FROM dailyschedule";
	private static final String FIND_BY_DATE = "SELECT * FROM dailyschedule WHERE dailyschedule_date = ?";
	private static final String FIND_BY_SCHEDULE_ID = "SELECT * FROM dailyschedule WHERE id = ?";
	private static final String FIND_DAILYSCHEDUALE_BY_ID = "SELECT * FROM dailyschedule WHERE id = ?";
	private static final String DELETE_DAILYSCHEDUALE_BY_ID = "DELETE FROM dailyschedule WHERE id = ?";
	private static final String UPDATE_BY_LECTURE_ID = "UPDATE dailyschedule SET dailyschedule_date = ? WHERE id = ?";
	private static final String GET_LECTURE_GROUP_ID = "SELECT * FROM lectures WHERE local_date = ? and group_id = ?";
	private static final String GET_LECTURE_TEACHER_ID = "SELECT * FROM lectures WHERE local_date = ? and teacher_id = ?";

	private LectureDao lectureDao;
	private DailyScheduleRowMapper dailyScheduleRowMapper;
	private LectureRowMapper lectureRowMapper;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public DailyScheduleDao(JdbcTemplate jdbcTemplate, DailyScheduleRowMapper dailyScheduleRowMapper,
			LectureRowMapper lectureRowMapper, LectureDao lectureDao) {
		this.jdbcTemplate = jdbcTemplate;
		this.lectureRowMapper = lectureRowMapper;
		this.dailyScheduleRowMapper = dailyScheduleRowMapper;
		this.lectureDao = lectureDao;
	}

	@Transactional
	public void create(DailySchedule dailySchedule) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_DAILYSCHEDUALE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, dailySchedule.getDate().toString());
			return statement;
		}, keyHolder);
		dailySchedule.setId((int) keyHolder.getKeys().get("id"));
		dailySchedule.getLectures().forEach(l -> {
			lectureDao.create(l);
			jdbcTemplate.update(ASSIGN_LECTURE, dailySchedule.getId(), l.getId());
			});
	}

	@Transactional
	public void update(DailySchedule dailySchedule) {
		jdbcTemplate.update(UPDATE_BY_LECTURE_ID, dailySchedule.getDate().toString(), dailySchedule.getId());		
		findById(dailySchedule.getId()).getLectures().stream()
																  .filter(l->!dailySchedule.getLectures().contains(l))
																  .forEach(l -> jdbcTemplate.update(REMOVE_LECTURE, dailySchedule.getId(), l.getId()));
		dailySchedule.getLectures().stream()
								   .filter(l->findById(dailySchedule.getId()).getLectures().contains(l))
								   .forEach(l -> jdbcTemplate.update(ASSIGN_LECTURE, dailySchedule.getId(), l.getId()));
	}

	public DailySchedule findById(int scheduleId) {
		return this.jdbcTemplate.queryForObject(FIND_BY_SCHEDULE_ID, dailyScheduleRowMapper, scheduleId);
	}

	public void delete(int dailyScheduleId) {
		jdbcTemplate.update(DELETE_DAILYSCHEDUALE_BY_ID, dailyScheduleId);
	}

	public List<DailySchedule> findAll() {
		return this.jdbcTemplate.query(GET_ALL, dailyScheduleRowMapper);
	}

	public List<DailySchedule> findForStudent(Group group, List<LocalDate> periodOfTime) {
		List<DailySchedule> dailySchedules = new ArrayList<>();
		for (LocalDate date : periodOfTime) {
			DailySchedule dailySchedule = findByDate(date);
			dailySchedule.setLectures(findLecturesByDateAndGroup(date, group));
			dailySchedules.add(dailySchedule);
		}
		return dailySchedules;
	}

	public DailySchedule findByDate(LocalDate date) {
		return this.jdbcTemplate.queryForObject(FIND_BY_DATE, dailyScheduleRowMapper, date.toString());
	}

	public List<Lecture> findLecturesByDateAndGroup(LocalDate date, Group group) {
		Object[] objects = new Object[] { date.toString(), group.getId() };
		return this.jdbcTemplate.query(GET_LECTURE_GROUP_ID, objects, lectureRowMapper);
	}

	public List<DailySchedule> findForTeacher(int id, List<LocalDate> periodOfTime) {
		List<DailySchedule> dailySchedules = new ArrayList<>();
		for (LocalDate date : periodOfTime) {
			DailySchedule dailySchedule = findByDate(date);
			dailySchedule.setLectures(findLecturesByDateAndTeacherId(date, id));
			dailySchedules.add(dailySchedule);
		}
		return dailySchedules;
	}

	public List<Lecture> findLecturesByDateAndTeacherId(LocalDate date, int id) {
		Object[] objects = new Object[] { date.toString(), id };
		return this.jdbcTemplate.query(GET_LECTURE_TEACHER_ID, objects, lectureRowMapper);
	}
}
