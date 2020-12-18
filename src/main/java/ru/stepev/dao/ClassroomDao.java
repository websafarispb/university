package ru.stepev.dao;

import java.util.List;
import java.util.Optional;

import ru.stepev.model.Classroom;

public interface ClassroomDao {
	
	public void create(Classroom classroom);
	public void update(Classroom classroom);
	public void delete(int classroomId);
	public Optional<Classroom> findById(int classroomId);
	public List<Classroom> findAll();
	public List<Classroom> findAndSortByCapacity(int numberOfItems, int offset);
	public List<Classroom> findAndSortById(int numberOfItems, int offset);
	public List<Classroom> findAndSortByAddress(int numberOfItems, int offset);
	public int findNumberOfItems();
	public Optional<Classroom> findByAddress(String addressOfClassroom);
}
