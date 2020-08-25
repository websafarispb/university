package ru.stepev.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class University {

	private List<DailySchedule> dailySchedules;
	private List<Group> groups;
	private List<Course> courses;
	private List<Teacher> teachers;
	private List<Student> students;
	private List<ClassRoom> classRooms;

	public List<DailySchedule> getTimeTableForStudent(String firstName, String lastName, List<LocalDate> periodOfTime) {
		List<DailySchedule> studentDailySchedules = new ArrayList<>();
		Group groupOfStudent = getGroupOfStudent(firstName, lastName);
		for (LocalDate day : periodOfTime) {
			List<Lecture> lecturesOfStudent = new ArrayList<>();
			for (DailySchedule schedule : dailySchedules) {

				if (schedule.getDate().equals(day)) {
					for (Lecture lecture : schedule.getLectures()) {
						if (lecture.getGroup().getName().equals(groupOfStudent.getName()))
							lecturesOfStudent.add(lecture);
					}
					studentDailySchedules.add(new DailySchedule(day, lecturesOfStudent));
				}
			}
		}
		return studentDailySchedules;
	}

	public Group getGroupOfStudent(String firstName, String lastName) {
		Group groupOfStudent = null;
		for (Group group : groups) {
			for (Student student : group.getStudents()) {
				if (student.firstName.equals(firstName) && student.lastName.equals(lastName)) {
					groupOfStudent = group;
					break;
				}
			}
		}
		return groupOfStudent;
	}

	public List<DailySchedule> getTimeTableForTeacher(String firstName, String lastName, List<LocalDate> periodOfTime) {
		List<DailySchedule> teacherDailySchedules = new ArrayList<>();
		for (LocalDate day : periodOfTime) {
			List<Lecture> lectureOfTeacher = new ArrayList<>();
			for (DailySchedule schedule : dailySchedules) {
				if (schedule.getDate().equals(day)) {
					for (Lecture lecture : schedule.getLectures()) {
						if (lecture.getTeacher().firstName.equals(firstName)
								&& lecture.getTeacher().lastName.equals(lastName))
							lectureOfTeacher.add(lecture);
					}
					teacherDailySchedules.add(new DailySchedule(day, lectureOfTeacher));
				}
			}
		}
		return teacherDailySchedules;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public void addTeacher(Teacher teacher) {
		teachers.add(teacher);
	}

	public void addGroup(Group group) {
		groups.add(group);
	}

	public void addCourse(Course course) {
		courses.add(course);
	}

	public void addDailySchedule(DailySchedule dailySchedule) {
		this.dailySchedules.add(dailySchedule);
	}

	public void assignStudentToGroup(Student student, Group group) {
		group.addStudent(student);

	}

	public List<DailySchedule> getDailySchedules() {
		return dailySchedules;
	}

	public void setDailySchedules(List<DailySchedule> dailySchedules) {
		this.dailySchedules = dailySchedules;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<ClassRoom> getClassRooms() {
		return classRooms;
	}

	public void setClassRooms(List<ClassRoom> classRooms) {
		this.classRooms = classRooms;
	}

}
