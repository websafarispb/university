package ru.stepev.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.stepev.model.Course;
import ru.stepev.model.Group;

@Component
public class GroupConverter implements Converter<String, Group>{
	
	@Override
	public Group convert(String id) {
		return new Group(Integer.parseInt(id));
	}
}
