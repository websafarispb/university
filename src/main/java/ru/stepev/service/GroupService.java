package ru.stepev.service;

import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

import org.springframework.stereotype.Component;

import ru.stepev.dao.GroupDao;
import ru.stepev.dao.StudentDao;
import ru.stepev.model.Group;
import ru.stepev.model.Student;

@Component
public class GroupService {

	public GroupDao groupDao;
	public StudentDao studentDao;

	public GroupService(GroupDao groupDao, StudentDao studentDao) {
		this.groupDao = groupDao;
		this.studentDao = studentDao;
	}

	public void add(Group group) {
		if (!isGroupExist(group) && areStudentsExist(group)) {
			groupDao.create(group);
		}
	}

	public void update(Group group) {
		if (isGroupExist(group) && areStudentsExist(group)) {
			groupDao.update(group);
		}
	}

	public void delete(Group group) {
		if (isGroupExist(group)) {
			groupDao.delete(group.getId());
		}
	}

	public Optional<Group> getById(int groupId) {
		return groupDao.findById(groupId);
	}

	public List<Group> getAll() {
		return groupDao.findAll();
	}

	public Optional<Group> findByStudentId(int studentId) {
		return groupDao.findByStudentId(studentId);
	}

	private boolean isGroupExist(Group group) {
		return groupDao.findById(group.getId()).isPresent();
	}

	private boolean areStudentsExist(Group group) {
		List<Student> correctStudents = group.getStudents().stream()
				.filter(s -> studentDao.findById(s.getId()).isPresent()).collect(toList());
		return correctStudents.equals(group.getStudents());
	}
}
