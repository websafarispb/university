package ru.stepev.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.stepev.model.Student;

@Component
public class StudentConverter implements Converter <String, Student>{

	@Override
	public Student convert(String id) {
		return new Student(Integer.parseInt(id));
	}
}
