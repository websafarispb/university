package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.model.DailySchedule;

@Component
public class DailyScheduleRowMapper implements RowMapper<DailySchedule> {

	@Override
	public DailySchedule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		DailySchedule dailySchedule = new DailySchedule(resultSet.getInt("dailyschedule_id"),
				LocalDate.parse(resultSet.getString("dailyschedule_date")));
		return dailySchedule;
	}

}
