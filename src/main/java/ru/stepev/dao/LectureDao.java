package ru.stepev.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.LectureRowMapper;
import ru.stepev.exceptions.ObjectNotFoundException;
import ru.stepev.model.Lecture;

@Component
public class LectureDao {

	private static final String CREATE_LECTURE_QUERY = "INSERT INTO lectures (dailyschedule_id,  local_time, course_id, classroom_id, group_id, teacher_id) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_BY_LECTURE_ID = "UPDATE lectures SET dailyschedule_id = ?, local_time = ?, course_id = ?, classroom_id = ?, group_id = ?, teacher_id = ? WHERE id = ?";
	private static final String GET_ALL = "SELECT * FROM lectures";
	private static final String DELETE_LECTURE_BY_ID = "DELETE  FROM lectures WHERE id = ?";
	private static final String GET_BY_DAILY_SCHEDULE = "SELECT * FROM lectures WHERE dailyschedule_id = ?";
	private static final String FIND_LECTURE_BY_ID = "SELECT * FROM lectures WHERE id = ?";

	private LectureRowMapper lectureRowMapper;
	private JdbcTemplate jdbcTemplate;

	public LectureDao(JdbcTemplate jdbcTemplate, LectureRowMapper lectureRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.lectureRowMapper = lectureRowMapper;
	}

	public void create(Lecture lecture) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_LECTURE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, lecture.getDailyScheduleId());
			statement.setObject(2, lecture.getTime());
			statement.setInt(3, lecture.getCourse().getId());
			statement.setInt(4, lecture.getClassRoom().getId());
			statement.setInt(5, lecture.getGroup().getId());
			statement.setInt(6, lecture.getTeacher().getId());
			return statement;
		}, keyHolder);
		lecture.setId((int) keyHolder.getKeys().get("id"));
	}

	public void update(Lecture lecture) {
		if (jdbcTemplate.update(UPDATE_BY_LECTURE_ID, lecture.getDailyScheduleId(),
				lecture.getTime(), lecture.getCourse().getId(), lecture.getClassRoom().getId(),
				lecture.getGroup().getId(), lecture.getTeacher().getId(), lecture.getId()) == 0) {
			throw new ObjectNotFoundException(String.format("Lecture with Id = %d not found !!!", lecture.getId()));
		}
	}

	public void delete(int lectureId) {
		if (jdbcTemplate.update(DELETE_LECTURE_BY_ID, lectureId) == 0) {
			throw new ObjectNotFoundException(String.format("Lecture with Id = %d not found !!!", lectureId));
		}
	}

	public Lecture findById(int lectureId) {
		try {
			return jdbcTemplate.queryForObject(FIND_LECTURE_BY_ID, lectureRowMapper, lectureId);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(String.format("Lecture with Id = %d not found !!!", lectureId), e);
		}
	}

	public List<Lecture> findByDailyScheduleId(int dailyScheduleId) {
		Object[] objects = new Object[] { dailyScheduleId };
		return jdbcTemplate.query(GET_BY_DAILY_SCHEDULE, objects, lectureRowMapper);
	}

	public List<Lecture> findAll() {
		return jdbcTemplate.query(GET_ALL, lectureRowMapper);
	}
}
