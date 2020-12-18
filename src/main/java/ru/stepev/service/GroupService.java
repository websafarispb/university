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
import ru.stepev.exception.StudentsNotFoundException;
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
		verifyGroupIsUnique(group);
		verifyStudentsExist(group);
		groupDao.create(group);
		log.debug("Group with name {} was created ", group.getName());
	}

	public void update(Group group) {
		verifyGroupIsExist(group);
		verifyGroupIsUnique(group);
		verifyStudentsExist(group);
		groupDao.update(group);
		log.debug("Group with name {} was updated ", group.getName());

	}

	public void delete(Group group) {
		verifyGroupIsExist(group);
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
	
	public void verifyGroupIsUnique(Group group) {
		Optional<Group> existingGroup = groupDao.findByName(group.getName());
		if (existingGroup.isPresent() && (existingGroup.get().getId() != group.getId())) {	
				throw new EntityAlreadyExistException(
						String.format("Group with name %s already exist", group.getName()));
		} 
	}

	public void verifyGroupIsExist(Group group) {
		if (groupDao.findById(group.getId()).isEmpty()) {
			throw new EntityNotFoundException(
					String.format("Group with name %s doesn't exist", group.getName()));
		}
	}
	
	private void verifyStudentsExist(Group group) {
		List<Student> wrongStudents = group.getStudents().stream()
				.filter(s -> studentDao.findById(s.getId()).isEmpty()).collect(toList());
		if (wrongStudents.size() > 0) {
			throw new StudentsNotFoundException(String.format("Students %s don't exist", wrongStudents));
		}
	}

	public int getNumberOfItems() {
		return groupDao.findNumberOfItem();
	}

	public List<Group> getAndSortByName(int numberOfItems, int offset) {
		return groupDao.findAndSortByName( numberOfItems,  offset);
	}

	public List<Group> getAndSortById(int numberOfItems, int offset) {
		return groupDao.fineAndSortById( numberOfItems,  offset);
	}
}
