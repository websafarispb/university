package ru.stepev.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

@Component
public class TeacherConverter implements Converter <String, Teacher>{

	@Override
	public Teacher convert(String id) {
		System.out.println("Trying to convert id-" + id + "to student");	
		return new Teacher(Integer.parseInt(id));
	}
}
