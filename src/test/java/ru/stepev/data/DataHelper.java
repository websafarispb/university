package ru.stepev.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Gender;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

@Data
@AllArgsConstructor
@Component
public class DataHelper {

	private List<Classroom> classrooms;
	private Classroom classroomForCreate;
	private Classroom classroomForDelete;
	private Classroom classroomForUpdate;
	private List<Course> courses;
	private List<Student> students;
	private List<Teacher> teachers;
	private Teacher teacherForTest;
	private List<Group> groups;
	private Group groupForTest;
	private List<Lecture> lectures;
	private List<DailySchedule> dailySchedules;
	private DailySchedule dailyScheduleForCreate;
	private Student studentForTest;
	private Course courseForTest;
	private Lecture correstLectureForTest;
	private Lecture wrongLectureForTest;
	private Lecture lectureWithNotAvaliableGroup;
	private Lecture lectureWithAvaliableGroup;
	private Lecture lectureWithAvaliableClassroom;
	private Lecture lectureWithNotAvaliableClassroom;
	private Teacher specialTeacher;
	private Group sillyGroup;
	private List<Student> sillyStudents;
	private Classroom classroomSmall;
	private ArrayList<Course> coursesForTeacher;

	public DataHelper() {
		classrooms = new ArrayList<>();
		classrooms.add(new Classroom(1, "101", 50));
		classrooms.add(new Classroom(2, "102", 40));
		classrooms.add(new Classroom(3, "103", 30));
		classrooms.add(new Classroom(4, "104", 20));

		classroomForCreate = new Classroom(5, "105", 10);
		classroomForDelete = new Classroom(2, "102", 40);
		classroomForUpdate = new Classroom(3, "303", 400);
		classroomSmall = new Classroom(4, "503", 1);

		courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));

		courseForTest = new Course(5, "Geography", "Geo");
		
		coursesForTeacher = new ArrayList<>();
		coursesForTeacher.add(new Course(1, "Mathematics", "Math"));
		coursesForTeacher.add(new Course(2, "Biology", "Bio"));
		coursesForTeacher.add(new Course(3, "Chemistry", "Chem"));
		coursesForTeacher.add(new Course(4, "Physics", "Phy"));
		coursesForTeacher.add( new Course(5, "Geography", "Geo"));

		students = new ArrayList<>();
		students.add(new Student(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE,
				"City17", courses));
		students.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses));
		students.add(new Student(3, 125, "Ivan", "Stepanov", LocalDate.of(2020, 9, 1), "Stepanov@mail.ru", Gender.MALE,
				"City11", courses));
		students.add(new Student(4, 126, "Peter", "Smirnov", LocalDate.of(2020, 9, 6), "webPS@mail.ru", Gender.MALE,
				"City17", courses));
		students.add(new Student(5, 227, "Irina", "Stepanova", LocalDate.of(2020, 9, 7), "Ivanov@mail.ru",
				Gender.FEMALE, "City11", courses));
		students.add(new Student(6, 527, "Daria", "Ivanova", LocalDate.of(2020, 9, 7), "Ivanova@mail.ru", Gender.FEMALE,
				"City20", courses));
		students.add(new Student(7, 528, "Igor", "Stepanov", LocalDate.of(2020, 9, 7), "Stepanov@mail.ru", Gender.MALE,
				"City20", courses));
		studentForTest = new Student(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE,
				"City17", courses);

		teachers = new ArrayList<>();
		teachers.add(new Teacher(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE,
				"City17", coursesForTeacher));
		teachers.add(new Teacher(2, 124, "Victoria", "Semenova", LocalDate.of(2020, 9, 1), "Semenova@mail.ru",
				Gender.MALE, "City10", coursesForTeacher));
		teachers.add(new Teacher(3, 125, "Peter", "Ivanov", LocalDate.of(2020, 9, 5), "webPI@mail.ru", Gender.FEMALE,
				"City19", coursesForTeacher));
		teachers.add(new Teacher(5, 227, "Irina", "Stepanova", LocalDate.of(2020, 9, 7), "Stepanova@mail.ru",
				Gender.FEMALE, "City11", coursesForTeacher));
		teachers.add(new Teacher(6, 228, "Victoria", "Semenova", LocalDate.of(2020, 9, 1), "Semenova@mail.ru",
				Gender.MALE, "City10", coursesForTeacher));

		teacherForTest = new Teacher(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE,
				"City17", courses);
		
		specialTeacher = new Teacher(5, 227, "Irina", "Stepanova", LocalDate.of(2020, 9, 7), "Stepanova@mail.ru",
				Gender.FEMALE, "City11", courses.subList(0, 1));

		groups = new ArrayList<>();
		groups.add(new Group(1, "a2a2", students.subList(0, 1)));
		groups.add(new Group(2, "b2b2", students.subList(2, 3)));
		groups.add(new Group(3, "c2c2", students.subList(4, 5)));
		groups.add(new Group(4, "d2d2", students.subList(6, 6)));
		
		sillyStudents = new ArrayList<>();
		sillyStudents.add(new Student(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE,
				"City17", courses.subList(0, 1)));
		sillyStudents.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses.subList(0, 1)));
		
		sillyGroup = new Group(1, "a2a2", sillyStudents);
		
		groupForTest = new Group(1, "a2a2", students.subList(0, 3));
		

		lectures = new ArrayList<>();
		lectures.add(new Lecture(1, 1, LocalTime.of(9, 0, 0), courses.get(0), classrooms.get(0), groups.get(0),
				teachers.get(0)));
		lectures.add(new Lecture(2, 1, LocalTime.of(10, 0, 0), courses.get(1), classrooms.get(0), groups.get(0),
				teachers.get(1)));
		lectures.add(new Lecture(3, 2, LocalTime.of(9, 0, 0), courses.get(2), classrooms.get(0), groups.get(0),
				teachers.get(2)));
		lectures.add(new Lecture(4, 2, LocalTime.of(10, 0, 0), courses.get(3), classrooms.get(0), groups.get(0),
				teachers.get(0)));
		lectures.add(new Lecture(5, 3, LocalTime.of(9, 0, 0), courses.get(0), classrooms.get(0), groups.get(1),
				teachers.get(1)));
		lectures.add(new Lecture(6, 3, LocalTime.of(10, 0, 0), courses.get(1), classrooms.get(0), groups.get(1),
				teachers.get(2)));
		lectures.add(new Lecture(7, 4, LocalTime.of(9, 0, 0), courses.get(2), classrooms.get(0), groups.get(2),
				teachers.get(0)));
		lectures.add(new Lecture(8, 4, LocalTime.of(10, 0, 0), courses.get(3), classrooms.get(0), groups.get(3),
				teachers.get(1)));
		lectures.add(new Lecture(8, 5, LocalTime.of(9, 0, 0), courses.get(2), classrooms.get(0), groups.get(2),
				teachers.get(0)));
		lectures.add(new Lecture(9, 5, LocalTime.of(10, 0, 0), courses.get(3), classrooms.get(0), groups.get(3),
				teachers.get(1)));

		correstLectureForTest = new Lecture(9, 5, LocalTime.of(10, 0, 0), courses.get(3), classrooms.get(1),
				groups.get(1), teachers.get(0));
		wrongLectureForTest = new Lecture(9, 5, LocalTime.of(9, 59, 59), courses.get(3), classrooms.get(0),
				groups.get(3), teachers.get(1));
		
		lectureWithAvaliableGroup = new Lecture(9, 5, LocalTime.of(10, 0, 0), courses.get(3), classrooms.get(2),
				groups.get(1), teachers.get(3));
		lectureWithNotAvaliableGroup = new Lecture(9, 5, LocalTime.of(9, 59, 59), courses.get(3), classrooms.get(0),
				groups.get(3), teachers.get(3));
		
		lectureWithAvaliableClassroom = new Lecture(9, 5, LocalTime.of(10, 0, 0), courses.get(3), classrooms.get(1),
				groups.get(1), teachers.get(3));
		lectureWithNotAvaliableClassroom = new Lecture(9, 5, LocalTime.of(9, 59, 59), courses.get(3), classrooms.get(0),
				groups.get(1), teachers.get(3));

		dailySchedules = new ArrayList<>();
		dailySchedules.add(new DailySchedule(1, LocalDate.of(2020, 9, 7), lectures.subList(0, 1)));
		dailySchedules.add(new DailySchedule(2, LocalDate.of(2020, 9, 8), lectures.subList(2, 3)));
		dailySchedules.add(new DailySchedule(3, LocalDate.of(2020, 9, 9), lectures.subList(4, 5)));
		dailySchedules.add(new DailySchedule(4, LocalDate.of(2020, 9, 10), lectures.subList(6, 7)));
		dailySchedules.add(new DailySchedule(5, LocalDate.of(2020, 9, 11), lectures.subList(8, 9)));

		dailyScheduleForCreate = new DailySchedule(5, LocalDate.of(2020, 9, 11), lectures.subList(8, 9));
	}
}
