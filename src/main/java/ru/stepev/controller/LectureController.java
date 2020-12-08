package ru.stepev.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Teacher;
import ru.stepev.service.ClassroomService;
import ru.stepev.service.CourseService;
import ru.stepev.service.DailyScheduleService;
import ru.stepev.service.GroupService;
import ru.stepev.service.LectureService;
import ru.stepev.service.TeacherService;

@Controller
@RequestMapping("/lectures")
public class LectureController {

	private LectureService lectureService;
	private DailyScheduleService dailyScheduleService;
	private CourseService courseService;
	private ClassroomService classroomService;
	private GroupService groupService;
	private TeacherService teacherService;

	public LectureController(LectureService lectureService, DailyScheduleService dailyScheduleService,
			CourseService courseService, ClassroomService classroomService, GroupService groupService,
			TeacherService teacherService) {
		this.lectureService = lectureService;
		this.dailyScheduleService = dailyScheduleService;
		this.courseService = courseService;
		this.classroomService = classroomService;
		this.groupService = groupService;
		this.teacherService = teacherService;
	}

	@GetMapping("/showAllLectures")
	public String showAllLectures(Model model) {
		List<Lecture> lectures = lectureService.getAll();
		model.addAttribute("lectures", lectures);
		return "lectures-page";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Lecture lecture = new Lecture();
		List<Course> allCourses = courseService.getAll();
		List<Classroom> allClassrooms = classroomService.getAll();
		List<Group> allGroups = groupService.getAll();
		List<Teacher> allTeachers = teacherService.getAll();
		model.addAttribute("lecture", lecture);
		model.addAttribute("allCourses", allCourses);
		model.addAttribute("allClassrooms", allClassrooms);
		model.addAttribute("allGroups", allGroups);
		model.addAttribute("allTeachers", allTeachers);
		return "add-lecture";
	}

	@GetMapping("/update")
	public String update(@RequestParam("lectureId") int id, Model model) {
		Lecture lecture = lectureService.getById(id).get();
		DailySchedule dailySchedule = dailyScheduleService.getById(lecture.getDailyScheduleId()).get();
		List<Course> allCourses = courseService.getAll();
		List<Classroom> allClassrooms = classroomService.getAll();
		List<Group> allGroups = groupService.getAll();
		List<Teacher> allTeachers = teacherService.getAll();
		model.addAttribute("lecture", lecture);
		model.addAttribute("dailySchedule", dailySchedule);
		model.addAttribute("allCourses", allCourses);
		model.addAttribute("allClassrooms", allClassrooms);
		model.addAttribute("allGroups", allGroups);
		model.addAttribute("allTeachers", allTeachers);
		return "update-lecture";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("lectureId") int id, Model model) {
		Lecture lecture = lectureService.getById(id).get();
		lectureService.delete(lecture);
		return "redirect:/lectures/showAllLectures";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute("lecture") Lecture lecture, @RequestParam("date") String date,
			@RequestParam("courseId") int courseId, @RequestParam("classroomId") int classroomId,
			@RequestParam("groupId") int groupId, @RequestParam("teacherId") int teacherId) {
		LocalDate lectureDate = LocalDate.parse(date);
		DailySchedule dailySchedule = dailyScheduleService.getByDate(lectureDate).orElse(null);
		Course course = courseService.getById(courseId).get();
		Classroom classroom = classroomService.getById(classroomId).get();
		Group group = groupService.getById(groupId).get();
		Teacher teacher = teacherService.getById(teacherId).get();
		if (dailySchedule != null) {
			System.out.println("make update dailyschedule");
			 lecture.setDailyScheduleId(dailySchedule.getId());
			 lecture.setCourse(course);
			 lecture.setClassRoom(classroom);
			 lecture.setGroup(group);
			 lecture.setTeacher(teacher);
			 lectureService.add(lecture);
		} else {		 
			 dailyScheduleService.add(new DailySchedule(lectureDate));
			 lecture.setDailyScheduleId(dailyScheduleService.getByDate(lectureDate).get().getId());
			 lecture.setCourse(course);
			 lecture.setClassRoom(classroom);
			 lecture.setGroup(group);
			 lecture.setTeacher(teacher);
			 lectureService.add(lecture);
		}
		return "redirect:/lectures/showAllLectures";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("lecture") Lecture lecture, @RequestParam("date") LocalDate date,
			@RequestParam("courseId") int courseId, @RequestParam("classroomId") int classroomId,
			@RequestParam("groupId") int groupId, @RequestParam("teacherId") int teacherId) {
	//	LocalDate lectureDate = LocalDate.parse(date);
		DailySchedule dailySchedule = dailyScheduleService.getByDate(date).orElse(null);
		Course course = courseService.getById(courseId).get();
		Classroom classroom = classroomService.getById(classroomId).get();
		Group group = groupService.getById(groupId).get();
		Teacher teacher = teacherService.getById(teacherId).get();
		if (dailySchedule != null) {
			 lecture.setDailyScheduleId(dailySchedule.getId());
			 lecture.setCourse(course);
			 lecture.setClassRoom(classroom);
			 lecture.setGroup(group);
			 lecture.setTeacher(teacher);
			 lectureService.update(lecture);
		} else {		 
			 dailyScheduleService.add(new DailySchedule(date));
			 lecture.setDailyScheduleId(dailyScheduleService.getByDate(date).get().getId());
			 lecture.setCourse(course);
			 lecture.setClassRoom(classroom);
			 lecture.setGroup(group);
			 lecture.setTeacher(teacher);
			 lectureService.update(lecture);
		}
		return "redirect:/lectures/showAllLectures";
	}
}
