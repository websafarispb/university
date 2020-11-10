package ru.stepev.service;

import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.GroupDao;
import ru.stepev.dao.StudentDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.exception.StudentdsNotFoundException;
import ru.stepev.model.Group;
import ru.stepev.model.Student;

@Component
@Slf4j
public class GroupService {

	public GroupDao groupDao;
	public StudentDao studentDao;

	public GroupService(GroupDao groupDao, StudentDao studentDao) {
		this.groupDao = groupDao;
		this.studentDao = studentDao;
	}

	public void add(Group group) {
		try {
			isGroupNotExist(group);
			areStudentsExist(group);
			groupDao.create(group);
			log.debug("Group with name {} was created ", group.getName());
		} catch (EntityAlreadyExistException e) {
			log.warn("Group with name {} is already exist", group.getName());
		}
	}

	public void update(Group group) {
		isGroupExist(group);
		areStudentsExist(group);
		groupDao.update(group);
		log.debug("Group with name {} was updated ", group.getName());

	}

	public void delete(Group group) {
		isGroupExist(group);
		groupDao.delete(group.getId());
		log.debug("Group with name {} was deleted ", group.getName());
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

	private void isGroupNotExist(Group group) {
		if (groupDao.findById(group.getId()).isPresent()) {
			throw new EntityAlreadyExistException(String.format("Group with name %s already exist", group.getName()));
		}
	}

	private void isGroupExist(Group group) {
		if (groupDao.findById(group.getId()).isEmpty()) {
			throw new EntityNotFoundException(String.format("Group with name %s doesn't exist", group.getName()));
		}
	}

	private void areStudentsExist(Group group) {
		List<Student> correctStudents = group.getStudents().stream()
				.filter(s -> studentDao.findById(s.getId()).isPresent()).collect(toList());
		if (!correctStudents.equals(group.getStudents())) {
			throw new StudentdsNotFoundException(String.format("Students don't exist"));
		}
	}
}
