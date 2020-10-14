package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.ClassroomDao;
import ru.stepev.dao.impl.ClassroomDaoImpl;
import ru.stepev.model.Classroom;

@Component
public class ClassroomService {
	
	private ClassroomDao classroomDao;
	
	public ClassroomService (ClassroomDao classroomDao) {
		this.classroomDao =  classroomDao;
	}
	
	public void add(Classroom classroom) {
		classroomDao.create(classroom);
	}
	
	public void update(Classroom classroom) {
		classroomDao.update(classroom);
	}
	
	public void delete(int classroomId) {
		classroomDao.delete(classroomId);
	}
	
	public Optional<Classroom> findById (int classroomId) {
		return classroomDao.findById(classroomId);
	}

	public List<Classroom> findAll(){
		return classroomDao.findAll();
	}
}
