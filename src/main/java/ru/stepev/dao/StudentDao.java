package ru.stepev.dao;

import java.util.List;
import java.util.Optional;

import ru.stepev.model.Student;

public interface StudentDao {

	public void create(Student student);

	public void update(Student student);

	public void delete(int studentId);

	public Optional<Student> findById(int studentId);

	public List<Student> findAll();

	public List<Student> findByFirstAndLastNames(String firstName, String lastName);

	public List<Student> findByGroupId(int groupId);
}
