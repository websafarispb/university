package ru.stepev.dao;

import java.util.List;
import java.util.Optional;

import ru.stepev.model.Teacher;

public interface TeacherDao {

	public void create(Teacher teacher);

	public void update(Teacher teacher);

	public void delete(int teacherId);

	public Optional<Teacher> findById(int teacherId);

	public List<Teacher> findAll();
}
