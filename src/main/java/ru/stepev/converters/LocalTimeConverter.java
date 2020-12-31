package ru.stepev.converters;

import java.time.LocalTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocalTimeConverter implements Converter <String, LocalTime>{

	@Override
	public LocalTime convert(String time) {
		return LocalTime.parse(time);
	}
}
