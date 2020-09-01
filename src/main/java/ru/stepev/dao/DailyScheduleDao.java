package ru.stepev.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.stepev.model.ClassRoom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Teacher;

public class DailyScheduleDao {
	
	private static final String CREATE_DAILYSCHEDUALE_QUERY = "INSERT INTO dailyschedule (dailyschedule_date) VALUES (?)";
	private static final String ASSIGN_LECTURE = "INSERT INTO dailyschedules_lectures (dailyschedule_id, lecture_id) VALUES (?, ?)";
	private static final String GET_ALL = "SELECT * FROM dailyschedule";
	private static final String GET_LECTURE_GROUP_ID = "SELECT * FROM lectures WHERE local_date = ? and group_id = ?";
	private static final String GET_LECTURE_TEACHER_ID = "SELECT * FROM lectures WHERE local_date = ? and teacher_id = ?";
	private static final String GET_LECTURES_BY_DATE_AND_GROUP = "SELECT * FROM lectures INNER JOIN dailyschedules_lectures "
															+  " ON dailyschedules_lectures.lecture_id = lectures.lecture_id "
															+ " WHERE lectures.group_id = ? and lectures.local_date = ? ";
	private static final String FILL_LECTURE = "SELECT * FROM courses, classrooms, groups, teachers  WHERE courses.course_id = ? "
			 								 + "AND classrooms.classroom_id = ? AND groups.group_id = ? AND teachers.teacher_id = ?";

	
	private JdbcTemplate jdbcTemplate;

	public DailyScheduleDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void create(DailySchedule dailySchedule) {
		jdbcTemplate.update(CREATE_DAILYSCHEDUALE_QUERY, dailySchedule.getDate());
	}

	public void assignLectures(DailySchedule dailySchedule) {
		for (Lecture lecture : dailySchedule.getLectures()) {
			jdbcTemplate.update(ASSIGN_LECTURE,  dailySchedule.getId(), lecture.getId());
		}
	}
	
	private final RowMapper<DailySchedule> dailySchedualeRowMapper = (resultSet, rowNum) -> {
		DailySchedule dailySchedule = new DailySchedule(resultSet.getInt("dailyschedule_id"), LocalDate.parse(resultSet.getString("dailyschedule_date")));
        return dailySchedule;
    };

    public List<DailySchedule> findAllDailySchedules() {
        return this.jdbcTemplate.query( GET_ALL, dailySchedualeRowMapper);
    }

	public List<DailySchedule> findSchedualesForStudent(Group group, List<LocalDate> periodOfTime) {
		List <DailySchedule> dailySchedules = new ArrayList<>();
		for(LocalDate date : periodOfTime) {
				dailySchedules.add(new DailySchedule(date, findLecturesByDateAndGroup(date, group)));
		}
		return dailySchedules;
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
	
	public List<Lecture> findLecturesByDateAndGroup(LocalDate date, Group group) {
		Object[] objects = new Object[] { date.toString(), group.getId() };
		return this.jdbcTemplate.query(GET_LECTURE_GROUP_ID, objects, lectureRowMapper);
	}

	public List<DailySchedule> findSchedualesForTeacher(int id, List<LocalDate> periodOfTime) {
		List <DailySchedule> dailySchedules = new ArrayList<>();
		for(LocalDate date : periodOfTime) {
			System.out.println("Searching date" + date.toString() );
				dailySchedules.add(new DailySchedule(date, findLecturesByDateAndTeacherId(date, id)));
		}
		return dailySchedules;
	}

	private List<Lecture> findLecturesByDateAndTeacherId(LocalDate date, int id) {
		Object[] objects = new Object[] { date.toString(), id };
		return this.jdbcTemplate.query(GET_LECTURE_TEACHER_ID, objects, lectureRowMapper);
	}
    

}
