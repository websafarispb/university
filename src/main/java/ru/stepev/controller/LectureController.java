package ru.stepev.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import ru.stepev.utils.Paginator;

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

	@GetMapping
	public String showAllLectures(Model model, @Value("${itemsPerPage}") int itemsPerPage,
			@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "Time") String sortBy) {
		Paginator paginator = new Paginator(lectureService.count(), currentPage, sortBy, itemsPerPage);
		List<Lecture> lectures = lectureService.getAndSort(paginator);
		model.addAttribute("lectures", lectures);
		model.addAttribute("paginator", paginator);
		return "lectures-page";
	}

	@GetMapping("{id}")
	public String getLecture(@PathVariable int id, Model model) {
		Lecture lecture = lectureService.getById(id).get();
		model.addAttribute("lecture", lecture);
		return "show-lecture";
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

	@GetMapping("/update/{id}")
	public String update(@PathVariable int id, Model model) {
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

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, Model model) {
		Lecture lecture = lectureService.getById(id).get();
		try {
			lectureService.delete(lecture);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "lectures-page";
		}
		return "redirect:/lectures";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute("lecture") Lecture lecture, @ModelAttribute("date") LocalDate date,
			@RequestParam("courseId") int courseId, @RequestParam("classroomId") int classroomId,
			@RequestParam("groupId") int groupId, @RequestParam("teacherId") int teacherId, Model model) {
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
		} else {
			dailyScheduleService.add(new DailySchedule(date));
			lecture.setDailyScheduleId(dailyScheduleService.getByDate(date).get().getId());
			lecture.setCourse(course);
			lecture.setClassRoom(classroom);
			lecture.setGroup(group);
			lecture.setTeacher(teacher);
		}
		try {
			lectureService.add(lecture);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "lectures-page";
		}
		return "redirect:/lectures";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("lecture") Lecture lecture, @ModelAttribute("date") LocalDate date,
			@RequestParam("courseId") int courseId, @RequestParam("classroomId") int classroomId,
			@RequestParam("groupId") int groupId, @RequestParam("teacherId") int teacherId, Model model) {
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
		} else {
			dailyScheduleService.add(new DailySchedule(date));
			lecture.setDailyScheduleId(dailyScheduleService.getByDate(date).get().getId());
			lecture.setCourse(course);
			lecture.setClassRoom(classroom);
			lecture.setGroup(group);
			lecture.setTeacher(teacher);
		}
		try {
			lectureService.update(lecture);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "lectures-page";
		}
		return "redirect:/lectures";
	}

}
