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
		if (!isGroupExist(group) && areStudentsExist(group)) {
			groupDao.create(group);
			log.debug("Group with name {} was created ", group.getName());

		} else {
			log.warn("Group with name {} is already exist", group.getName());
			throw new EntityAlreadyExistException(String.format(
					"Can not create group with name %s group already exist", group.getName()));
		}
	}

	public void update(Group group) {
		if (isGroupExist(group) && areStudentsExist(group)) {
			groupDao.update(group);
			log.debug("Group with name {} was updated ", group.getName());
		} else {
			log.warn("Group with name {} doesn't exist", group.getName());
			throw new EntityNotFoundException(String.format(
					"Can not update group with name %s group doesn't exist", group.getName()));
		}
	}

	public void delete(Group group) {
		if (isGroupExist(group)) {
			groupDao.delete(group.getId());
			log.debug("Group with name {} was deleted ", group.getName());
		}  else {
			log.warn("Group with address {} doesn't exist",  group.getName());
			throw new EntityNotFoundException(String.format(
					"Can not delete group with name %s group doesn't exist",  group.getName()));
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
		log.debug("Are group with name {} exist?", group.getName());
		return groupDao.findById(group.getId()).isPresent();
	}

	private boolean areStudentsExist(Group group) {
		log.debug("Are students exist in group with name {} ?", group.getName());
		List<Student> correctStudents = group.getStudents().stream()
				.filter(s -> studentDao.findById(s.getId()).isPresent()).collect(toList());
		return correctStudents.equals(group.getStudents());
	}
}
