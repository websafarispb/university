package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.GroupDao;
import ru.stepev.model.Group;

@Component
public class GroupService {
	
	public GroupDao groupDao;

	public GroupService(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	public void add(Group group) {
		groupDao.create(group);
	}
	public void update(Group group) {
		groupDao.update(group);
	}
	public void delete(int groupId) {
		groupDao.delete(groupId);
	}
	public Optional<Group> getById(int groupId){
		return groupDao.findById(groupId);
	}
	public List<Group> getAll(){
		return groupDao.findAll();
	}
	public Optional<Group> findByStudentId(int studentId){
		return groupDao.findByStudentId(studentId);
	}

}
