package ru.stepev.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.stepev.model.Classroom;

@Component
public class ClassroomConverter implements Converter<String, Classroom>{
	
	@Override
	public Classroom convert(String id) {
		System.out.println("Trying to convert id-" + id + "to Course");	
		return new Classroom(Integer.parseInt(id));
	}
}
