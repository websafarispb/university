package ru.stepev.dao;

import java.util.List;
import java.util.Optional;

import ru.stepev.model.Group;
import ru.stepev.model.Lecture;

public interface GroupDao {
	
	public void create(Group group);
	public void update(Group group);
	public void delete(int groupId);
	public Optional<Group> findById(int groupId);
	public List<Group> findAll();
	public Optional<Group> findByStudentId(int studentId);
	public Optional<Group> findByGroupIdAndCourseId(int groupId, int courseId);
	public Optional<Group> findByName(String nameOfGroup);
	public int findNumberOfItem();
	public List<Group> findAndSortByName(int numberOfItems, int offset);
	public List<Group> fineAndSortById(int numberOfItems, int offset);
}
