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

	public int findNumberOfItems();

	public List<Teacher> findAndSortByFirstName(int numberOfItems, int offset);

	public List<Teacher> findAndSortByLastName(int numberOfItems, int offset);

	public List<Teacher> findAndSortById(int numberOfItems, int offset);

	public List<Teacher> findAndSortByBirthday(int numberOfItems, int offset);

	public List<Teacher> findAndSortByEmail(int numberOfItems, int offset);

	public List<Teacher> findAndSortByAddress(int numberOfItems, int offset);
}
