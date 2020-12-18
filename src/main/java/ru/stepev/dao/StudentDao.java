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

	public int findNumberOfItems();

	public List<Student> findAndSortByFirstName(int numberOfItems, int offset);

	public List<Student> findAndSortByLastName(int numberOfItems, int offset);

	public List<Student> findAndSortById(int numberOfItems, int offset);

	public List<Student> findAndSortByBirthday(int numberOfItems, int offset);

	public List<Student> findAndSortByEmail(int numberOfItems, int offset);

	public List<Student> findAndSortByAddress(int numberOfItems, int offset);
}
