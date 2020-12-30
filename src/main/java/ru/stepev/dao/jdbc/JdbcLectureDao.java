package ru.stepev.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.LectureDao;
import ru.stepev.dao.jdbc.rowmapper.LectureRowMapper;
import ru.stepev.exception.EntityCouldNotBeenCreatedException;
import ru.stepev.exception.EntityCouldNotBeenDeletedException;
import ru.stepev.exception.EntityCouldNotBeenUpdatedException;
import ru.stepev.model.Lecture;

@Component
@Slf4j
public class JdbcLectureDao implements LectureDao {

	private static final String CREATE_LECTURE_QUERY = "INSERT INTO lectures (dailyschedule_id,  local_time, course_id, classroom_id, group_id, teacher_id) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_BY_LECTURE_ID = "UPDATE lectures SET dailyschedule_id = ?, local_time = ?, course_id = ?, classroom_id = ?, group_id = ?, teacher_id = ? WHERE id = ?";
	private static final String GET_ALL = "SELECT * FROM lectures";
	private static final String DELETE_LECTURE_BY_ID = "DELETE  FROM lectures WHERE id = ?";
	private static final String GET_BY_DAILY_SCHEDULE = "SELECT * FROM lectures WHERE dailyschedule_id = ?";
	private static final String FIND_LECTURE_BY_ID = "SELECT * FROM lectures WHERE id = ?";
	private static final String FIND_LECTURE_BY_DAILYSCHDULE_ID_AND_TIME_AND_GROUP_ID = "SELECT * FROM lectures  WHERE dailyschedule_id = ? AND local_time >=  ? AND local_time < ? AND group_id = ? ";
	private static final String FIND_LECTURE_BY_DAILYSCHDULE_ID_AND_TIME_AND_CLASSROOM_ID = "SELECT * FROM lectures  WHERE dailyschedule_id = ? AND local_time >=  ? AND local_time < ? AND classroom_id = ? ";
	private static final String FIND_LECTURE_BY_DAILYSCHDULE_ID_AND_TIME_AND_TEACHER_ID = "SELECT * FROM lectures  WHERE dailyschedule_id = ? AND local_time>=  ? AND local_time < ? AND teacher_id = ? ";
	private static final String FIND_NUMBER_OF_LECTURES = "SELECT COUNT(*) FROM lectures";
	private static final String FIND_AND_SORT_BY_TIME = "SELECT * FROM lectures ORDER BY local_time, id ASC LIMIT ? OFFSET ?";
	private static final String FIND_AND_SORT_BY_COURSE = "SELECT * FROM lectures INNER JOIN courses ON courses.id = lectures.course_id ORDER BY course_name, id ASC LIMIT ? OFFSET ?";
	private static final String FIND_AND_SORT_BY_CLASSROOM = "SELECT * FROM lectures INNER JOIN classrooms ON classrooms.id = lectures.classroom_id ORDER BY classroom_address, id  ASC LIMIT ? OFFSET ?";
	private static final String FIND_AND_SORT_BY_GROUP = "SELECT * FROM lectures INNER JOIN groups ON groups.id = lectures.group_id ORDER BY group_name, id ASC LIMIT ? OFFSET ?";
	private static final String FIND_AND_SORT_BY_TEACHER = "SELECT * FROM lectures INNER JOIN teachers ON teachers.id = lectures.teacher_id ORDER BY last_name, id ASC LIMIT ? OFFSET ?";
	private static final String FIND_AND_SORT_BY_ID = "SELECT * FROM lectures ORDER BY id ASC LIMIT ? OFFSET ?";

	private LectureRowMapper lectureRowMapper;
	private JdbcTemplate jdbcTemplate;

	public JdbcLectureDao(JdbcTemplate jdbcTemplate, LectureRowMapper lectureRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.lectureRowMapper = lectureRowMapper;
	}

	public void create(Lecture lecture) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_LECTURE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, lecture.getDailyScheduleId());
			statement.setObject(2, lecture.getTime());
			statement.setInt(3, lecture.getCourse().getId());
			statement.setInt(4, lecture.getClassRoom().getId());
			statement.setInt(5, lecture.getGroup().getId());
			statement.setInt(6, lecture.getTeacher().getId());
			return statement;
		}, keyHolder) == 0) {
			throw new EntityCouldNotBeenCreatedException(
					String.format("Lecture with time %s could not been created", lecture.getTime()));
		}
		lecture.setId((int) keyHolder.getKeys().get("id"));
	}

	public void update(Lecture lecture) {
		if (jdbcTemplate.update(UPDATE_BY_LECTURE_ID, lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getCourse().getId(), lecture.getClassRoom().getId(), lecture.getGroup().getId(),
				lecture.getTeacher().getId(), lecture.getId()) == 0) {
			throw new EntityCouldNotBeenUpdatedException(
					String.format("Lecture with time %s could not been updated", lecture.getTime()));
		}
	}

	public void delete(int lectureId) {
		if (jdbcTemplate.update(DELETE_LECTURE_BY_ID, lectureId) == 0) {
			throw new EntityCouldNotBeenDeletedException(
					String.format("Lecture with time %s could not been deleted", lectureId));
		}
	}

	public Optional<Lecture> findById(int lectureId) {
		try {
			return Optional
					.of(jdbcTemplate.queryForObject(FIND_LECTURE_BY_ID, lectureRowMapper, lectureId));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Lecture with id {} was not found", lectureId);
			return Optional.empty();
		}
	}

	public List<Lecture> findByDailyScheduleId(int dailyScheduleId) {
		log.debug("Finding by dailyscheduleId - {}", dailyScheduleId);
		Object[] objects = new Object[] { dailyScheduleId };
		return jdbcTemplate.query(GET_BY_DAILY_SCHEDULE, objects, lectureRowMapper);
	}

	public List<Lecture> findAll() {
		log.debug("Finding all lecture");
		return jdbcTemplate.query(GET_ALL, lectureRowMapper);
	}

	public Optional<Lecture> findByDailyScheduleIdAndTimeAndGroupId(int dailyScheduleId, LocalTime startTime,
			LocalTime finishTime, int groupId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_LECTURE_BY_DAILYSCHDULE_ID_AND_TIME_AND_GROUP_ID,
					lectureRowMapper, dailyScheduleId, startTime, finishTime, groupId));
		} catch (EmptyResultDataAccessException e) {
			log.warn(String.format("Lecture with starttime %s was not found", startTime));
			return Optional.empty();
		}
	}

	@Override
	public Optional<Lecture> findByDailyScheduleIdAndTimeAndClassroomId(int dailyScheduleId, LocalTime startTime,
			LocalTime finishTime, int classroomId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_LECTURE_BY_DAILYSCHDULE_ID_AND_TIME_AND_CLASSROOM_ID,
					lectureRowMapper, dailyScheduleId, startTime, finishTime, classroomId));
		} catch (EmptyResultDataAccessException e) {
			log.warn(String.format("Lecture with starttime %s was not found", startTime));
			return Optional.empty();
		}
	}

	@Override
	public Optional<Lecture> findByDailyScheduleIdAndTimeAndTeacherId(int dailyScheduleId, LocalTime startTime,
			LocalTime finishTime, int teacherId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_LECTURE_BY_DAILYSCHDULE_ID_AND_TIME_AND_TEACHER_ID,
					lectureRowMapper, dailyScheduleId, startTime, finishTime, teacherId));
		} catch (EmptyResultDataAccessException e) {
			log.warn(String.format("Lecture with starttime %s was not found", startTime));
			return Optional.empty();
		}
	}

	@Override
	public int findNumberOfItem() {
		log.debug("Counting number of lectures ... ");
		return this.jdbcTemplate.queryForObject(FIND_NUMBER_OF_LECTURES, Integer.class);
	}

	@Override
	public List<Lecture> findAndSortByTime(int numberOfItems, int offset) {
		log.debug("Finding and sorting lectures by time ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_TIME, objects, lectureRowMapper);
	}

	@Override
	public List<Lecture> findAndSortByCourse(int numberOfItems, int offset) {
		log.debug("Finding and sorting lectures by course ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_COURSE, objects, lectureRowMapper);
	}

	@Override
	public List<Lecture> findAndSortByClassroom(int numberOfItems, int offset) {
		log.debug("Finding and sorting lectures by classroom ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_CLASSROOM, objects, lectureRowMapper);
	}

	@Override
	public List<Lecture> findAndSortByGroup(int numberOfItems, int offset) {
		log.debug("Finding and sorting lectures by group ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_GROUP, objects, lectureRowMapper);
	}

	@Override
	public List<Lecture> findAndSortByTeacher(int numberOfItems, int offset) {
		log.debug("Finding and sorting lectures by teacher ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_TEACHER, objects, lectureRowMapper);
	}

	@Override
	public List<Lecture> findAndSortById(int numberOfItems, int offset) {
		log.debug("Finding and sorting lectures by Id ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_ID, objects, lectureRowMapper);
	}
}
