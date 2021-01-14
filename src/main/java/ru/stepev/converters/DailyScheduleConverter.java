package ru.stepev.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.stepev.model.DailySchedule;

@Component
public class DailyScheduleConverter implements Converter<String, DailySchedule>{
	
	@Override
	public DailySchedule convert(String id) {
		return new DailySchedule(Integer.parseInt(id));
	}
}
