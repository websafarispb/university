package ru.stepev.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.stepev.model.Lecture;

@Component
public class LectureConverter implements Converter<String, Lecture>{
	
	@Override
	public Lecture convert(String id) {
		System.out.println("Trying to convert id-" + id + "to Lecture");	
		return new Lecture(Integer.parseInt(id));
	}
}
