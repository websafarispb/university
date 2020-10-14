package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.dao.LectureDao;
import ru.stepev.model.DailySchedule;

@Component
public class DailyScheduleRowMapper implements RowMapper<DailySchedule> {

	private LectureDao lectureDao;

	public DailyScheduleRowMapper(LectureDao lectureDao) {
		this.lectureDao = lectureDao;
	}

	@Override
	public DailySchedule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new DailySchedule(resultSet.getInt("id"), resultSet.getObject("dailyschedule_date", LocalDate.class),
				lectureDao.findByDailyScheduleId(resultSet.getInt("id")));
	}
}
