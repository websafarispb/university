package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.dao.ClassroomDao;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.model.Lecture;

@Component
public class LectureRowMapper implements RowMapper<Lecture> {

	@Autowired
	private CourseDao courseDao;
	@Autowired
	private ClassroomDao classroomDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private TeacherDao teacherDao;

	@Override
	public Lecture mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Lecture lecture = new Lecture();
		lecture.setId(resultSet.getInt("id"));
		lecture.setDate(LocalDate.parse(resultSet.getString("local_date")));
		lecture.setTime(LocalTime.parse(resultSet.getString("local_time")));
		lecture.setCourse(courseDao.findById( resultSet.getInt("course_id")));
		lecture.setClassRoom(classroomDao.findById( resultSet.getInt("classroom_id")));
		lecture.setGroup(groupDao.findById(resultSet.getInt("group_id")));
		lecture.setTeacher(teacherDao.findById(resultSet.getInt("teacher_id")));
		return lecture;
	}
}
