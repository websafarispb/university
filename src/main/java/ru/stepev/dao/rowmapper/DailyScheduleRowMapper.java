package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.dao.LectureDao;
import ru.stepev.model.DailySchedule;

@Component
public class DailyScheduleRowMapper implements RowMapper<DailySchedule> {

	@Autowired
	public LectureDao lectureDao;

	@Override
	public DailySchedule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new DailySchedule(resultSet.getInt("id"),
				LocalDate.parse(resultSet.getString("dailyschedule_date")),
				lectureDao.findLecturesByDate(LocalDate.parse(resultSet.getString("dailyschedule_date"))));
	}

}
