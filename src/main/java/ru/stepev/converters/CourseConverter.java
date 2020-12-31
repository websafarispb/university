package ru.stepev.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.stepev.model.Course;

@Component
public class CourseConverter implements Converter<String, Course>{
	
	@Override
	public Course convert(String id) {
		System.out.println("Trying to convert id-" + id + "to Course");	
		return new Course(Integer.parseInt(id));
	}
}
