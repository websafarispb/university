package ru.stepev.dao;

import java.util.List;
import java.util.Optional;

import ru.stepev.model.Group;

public interface GroupDao {
	
	public void create(Group group);
	public void update(Group group);
	public void delete(int groupId);
	public Optional<Group> findById(int groupId);
	public List<Group> findAll();
	public Optional<Group> findByStudentId(int studentId);
}
