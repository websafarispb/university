package ru.stepev.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.LectureRowMapper;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;

@Component
public class LectureDao {

	private static final String CREATE_LECTURE_QUERY = "INSERT INTO lectures (local_date, local_time, course_id, classroom_id, group_id, teacher_id) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_BY_LECTURE_ID = "UPDATE lectures SET local_date = ?, local_time = ?, course_id = ?, classroom_id = ?, group_id = ?, teacher_id = ? WHERE lecture_id = ?";
	private static final String GET_ALL = "SELECT * FROM lectures";
	private static final String DELETE_LECTURE_BY_ID = "DELETE  FROM lectures WHERE lecture_id = ?";
	private static final String GET_BY_DATE = "SELECT * FROM lectures WHERE local_date = ?";
	private static final String FIND_LECTURE_BY_ID = "SELECT * FROM lectures WHERE lecture_id = ?";
	private static final String GET_LECTURE_ID = "SELECT * FROM lectures WHERE local_date = ? and group_id = ?";
	private static final String GET_LECTURE_ID_BY_DATE_TIME_COURSE_ID_AND_GROUP_ID = "SELECT lecture_id FROM lectures WHERE local_date = ? AND local_time = ? AND course_id = ? AND group_id = ?";

	@Autowired
	private LectureRowMapper lectureRowMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public LectureDao(JdbcTemplate jdbcTemplate, LectureRowMapper lectureRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.lectureRowMapper = lectureRowMapper;
	}

	public void create(Lecture lecture) {
		jdbcTemplate.update(CREATE_LECTURE_QUERY, lecture.getDate().toString(), lecture.getTime().toString(),
				lecture.getCourse().getId(), lecture.getClassRoom().getId(), lecture.getGroup().getId(),
				lecture.getTeacher().getId());
		lecture.setId(jdbcTemplate.queryForObject(GET_LECTURE_ID_BY_DATE_TIME_COURSE_ID_AND_GROUP_ID, Integer.class,
				lecture.getDate().toString(), lecture.getTime().toString(), lecture.getCourse().getId(),
				lecture.getGroup().getId()));
	}

	public void update(Lecture lecture, int lectureId) {
		jdbcTemplate.update(UPDATE_BY_LECTURE_ID, lecture.getDate().toString(), lecture.getTime().toString(),
				lecture.getCourse().getId(), lecture.getClassRoom().getId(), lecture.getGroup().getId(),
				lecture.getTeacher().getId(), lectureId);
	}

	public void delete(int lectureId) {
		jdbcTemplate.update(DELETE_LECTURE_BY_ID, lectureId);
	}

	public Lecture findById(int lectureId) {
		return this.jdbcTemplate.queryForObject(FIND_LECTURE_BY_ID, lectureRowMapper, lectureId);
	}

	public List<Lecture> findAllLectures() {
		return this.jdbcTemplate.query(GET_ALL, lectureRowMapper);
	}

	public List<Lecture> findLecturesByDate(LocalDate date) {
		Object[] objects = new Object[] { date.toString() };
		return this.jdbcTemplate.query(GET_BY_DATE, objects, lectureRowMapper);
	}

	public List<Lecture> findLecturesByDateAndGroup(LocalDate date, Group group) {
		Object[] objects = new Object[] { date.toString(), group.getId() };
		return this.jdbcTemplate.query(GET_LECTURE_ID, objects, lectureRowMapper);
	}
}
