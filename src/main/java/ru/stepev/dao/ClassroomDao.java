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
	public Optional<Classroom> findByAddress(String addressOfClassroom);
}
