package ru.stepev.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.stepev.model.ClassRoom;
import ru.stepev.model.Course;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Teacher;

public class LectureDao {

	private static final String CREATE_LECTURE_QUERY = "INSERT INTO lectures (local_date, local_time, course_id, classroom_id, group_id, teacher_id) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL = "SELECT * FROM lectures";
	private static final String GET_BY_DATE = "SELECT * FROM lectures WHERE local_date = ?";
	private static final String FILL_LECTURE = "SELECT * FROM courses, classrooms, groups, teachers  WHERE courses.course_id = ? and classrooms.classroom_id = ? and groups.group_id = ? and teachers.teacher_id = ?";
	private static final String GET_LECTURE_ID = "SELECT * FROM lectures WHERE local_date = ? and group_id = ?";
	
	private JdbcTemplate jdbcTemplate;

	public LectureDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void create(Lecture lecture) {
		jdbcTemplate.update(CREATE_LECTURE_QUERY, lecture.getDate().toString(), lecture.getTime().toString(), lecture.getCourse().getId(),
				lecture.getClassRoom().getId(), lecture.getGroup().getId(), lecture.getTeacher().getId());
	}

	public void createLectures(List<Lecture> lectures) {
		for(Lecture lecture : lectures) {
			create(lecture);
		}
	}
	private final RowMapper<Lecture> lectureRowMapper = (resultSet, rowNum) -> {
		Object[] objects = new Object[] { resultSet.getInt("course_id"), resultSet.getInt("classroom_id"),
				resultSet.getInt("group_id"), resultSet.getInt("teacher_id") };
		Lecture lecture = null;
		lecture = jdbcTemplate.queryForObject(FILL_LECTURE, objects, new RowMapper<Lecture>() {
			public Lecture mapRow(ResultSet rs, int arg) throws SQLException {
				Course course = new Course(resultSet.getInt("course_id"), rs.getString("course_name"),
						rs.getString("course_description"));
				ClassRoom classroom = new ClassRoom(resultSet.getInt("classroom_id"), rs.getString("classroom_address"),
						rs.getInt("classroom_capacity"));
				Group group = new Group(resultSet.getInt("group_id"), rs.getString("group_name"));
				Teacher teacher = new Teacher(resultSet.getInt("teacher_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("birthday"), rs.getString("email"),
						rs.getString("gender"), rs.getString("address"));
				Lecture lecture = new Lecture(course, classroom, group, teacher);
				return lecture;
			}
		});
		lecture.setId(resultSet.getInt("lecture_id"));
		lecture.setDate(LocalDate.parse(resultSet.getString("local_date")));
		lecture.setTime(LocalTime.parse(resultSet.getString("local_time")));

		return lecture;
	};

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
