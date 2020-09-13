package ru.stepev.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.DailyScheduleRowMapper;
import ru.stepev.dao.rowmapper.LectureRowMapper;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;

@Component
public class DailyScheduleDao {

	private static final String CREATE_DAILYSCHEDUALE_QUERY = "INSERT INTO dailyschedule (dailyschedule_date) VALUES (?)";
	private static final String ASSIGN_LECTURE = "INSERT INTO dailyschedules_lectures (dailyschedule_id, lecture_id) VALUES (?, ?)";
	private static final String GET_ALL = "SELECT * FROM dailyschedule";
	private static final String FIND_BY_DATE = "SELECT * FROM dailyschedule WHERE dailyschedule_date = ?";
	private static final String FIND_DAILYSCHEDUALE_BY_ID = "SELECT * FROM dailyschedule WHERE dailyschedule_id = ?";
	private static final String DELETE_DAILYSCHEDUALE_BY_ID = "DELETE FROM dailyschedule WHERE dailyschedule_id = ?";
	private static final String UPDATE_BY_LECTURE_ID = "UPDATE dailyschedule SET dailyschedule_date = ? WHERE dailyschedule_id = ?";
	private static final String GET_LECTURE_GROUP_ID = "SELECT * FROM lectures WHERE local_date = ? and group_id = ?";
	private static final String GET_LECTURE_TEACHER_ID = "SELECT * FROM lectures WHERE local_date = ? and teacher_id = ?";
	private static final String GET_DAILYSCHEDUALE_ID_BY_DATE = "SELECT dailyschedule_id FROM dailyschedule WHERE dailyschedule_date = ?";

	@Autowired
	private DailyScheduleRowMapper dailyScheduleRowMapper;

	@Autowired
	private LectureRowMapper lectureRowMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public DailyScheduleDao(JdbcTemplate jdbcTemplate, DailyScheduleRowMapper dailyScheduleRowMapper, LectureRowMapper lectureRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.lectureRowMapper = lectureRowMapper;
		this.dailyScheduleRowMapper = dailyScheduleRowMapper;
	}

	public void create(DailySchedule dailySchedule) {
		jdbcTemplate.update(CREATE_DAILYSCHEDUALE_QUERY, dailySchedule.getDate());
		dailySchedule.setId(jdbcTemplate.queryForObject(GET_DAILYSCHEDUALE_ID_BY_DATE, Integer.class,
				dailySchedule.getDate().toString()));
	}

	public void update(DailySchedule dailySchedule, int dailyScheduleId) {
		jdbcTemplate.update(UPDATE_BY_LECTURE_ID, dailySchedule.getDate().toString(), dailyScheduleId);
	}

	public void delete(int dailyScheduleId) {
		jdbcTemplate.update(DELETE_DAILYSCHEDUALE_BY_ID, dailyScheduleId);
	}

	public DailySchedule findById(int dailyScheduleId) {
		return this.jdbcTemplate.queryForObject(FIND_DAILYSCHEDUALE_BY_ID, dailyScheduleRowMapper, dailyScheduleId);
	}

	public void assignLectures(DailySchedule dailySchedule) {
		for (Lecture lecture : dailySchedule.getLectures()) {
			jdbcTemplate.update(ASSIGN_LECTURE, dailySchedule.getId(), lecture.getId());
		}
	}

	public List<DailySchedule> findAllDailySchedules() {
		return this.jdbcTemplate.query(GET_ALL, dailyScheduleRowMapper);
	}

	public List<DailySchedule> findSchedualesForStudent(Group group, List<LocalDate> periodOfTime) {
		List<DailySchedule> dailySchedules = new ArrayList<>();
		for (LocalDate date : periodOfTime) {
			DailySchedule dailySchedule = findDailySchedualByDate(date);
			dailySchedule.setLectures(findLecturesByDateAndGroup(date, group));
			dailySchedules.add(dailySchedule);
		}
		return dailySchedules;
	}

	public DailySchedule findDailySchedualByDate(LocalDate date) {
		return this.jdbcTemplate.queryForObject(FIND_BY_DATE, dailyScheduleRowMapper, date.toString());
	}

	public List<Lecture> findLecturesByDateAndGroup(LocalDate date, Group group) {
		Object[] objects = new Object[] { date.toString(), group.getId() };
		return this.jdbcTemplate.query(GET_LECTURE_GROUP_ID, objects, lectureRowMapper);
	}

	public List<DailySchedule> findSchedualesForTeacher(int id, List<LocalDate> periodOfTime) {
		List<DailySchedule> dailySchedules = new ArrayList<>();
		for (LocalDate date : periodOfTime) {
			DailySchedule dailySchedule = findDailySchedualByDate(date);
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
