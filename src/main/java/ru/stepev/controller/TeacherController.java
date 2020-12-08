package ru.stepev.controller;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Course;
import ru.stepev.model.Group;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;
import ru.stepev.service.CourseService;
import ru.stepev.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
	
	private TeacherService teacherService;
	private CourseService courseService;

	public TeacherController(TeacherService teacherService, CourseService courseService) {
		this.teacherService = teacherService;
		this.courseService = courseService;
	}
	
	@GetMapping("/showAllTeachers")
	public String showAllTeachers(Model model){
		List<Teacher> teachers = teacherService.getAll();
		model.addAttribute("teachers", teachers);
		return "teachers-page";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		Teacher teacher = new Teacher();
		List<Course> allCourses = courseService.getAll();
		model.addAttribute("teacher", teacher);
		model.addAttribute("allCourses", allCourses);
		return "add-teacher";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute("teacher") Teacher teacher) {
		for(Course course : teacher.getCourses()) {
			Course tempCourse = courseService.getById(course.getId()).get();
			course.setName(tempCourse.getName());
			course.setDescription(tempCourse.getDescription());
		}
		teacherService.add(teacher);
		return "redirect:/teachers/showAllTeachers";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("teacherId") int id) {
		Teacher teacher = teacherService.getById(id).get();
		teacherService.delete(teacher);
		return "redirect:/teachers/showAllTeachers";
	}
	
	@GetMapping("/update")
	public String update(@RequestParam("teacherId") int id, Model model) {
		Teacher teacher = teacherService.getById(id).get();
		List<Course> allCourses = courseService.getAll();
		model.addAttribute("teacher", teacher);
		model.addAttribute("allCourses", allCourses);
		return "update-teacher";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("teacher") Teacher teacher) {
		for(Course course : teacher.getCourses()) {
			Course tempCourse = courseService.getById(course.getId()).get();
			course.setName(tempCourse.getName());
			course.setDescription(tempCourse.getDescription());
		}
		teacherService.update(teacher);
		return "redirect:/teachers/showAllTeachers";
	}
}
