package ru.stepev.dao;

import java.util.List;
import java.util.Optional;

import ru.stepev.model.Course;

public interface CourseDao {
	
	public void create(Course course);
	public void update(Course course);
	public void delete(int courseId);
	public Optional<Course> findById(int courseId);
	public List<Course> findAll();
	public List<Course> findByTeacherId(int teacherId);
	public List<Course> findByStudentId(int studentId);
}
