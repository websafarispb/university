package ru.stepev.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Course;
import ru.stepev.model.Teacher;
import ru.stepev.service.CourseService;
import ru.stepev.service.TeacherService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
	
	private TeacherService teacherService;
	private CourseService courseService;

	public TeacherController(TeacherService teacherService, CourseService courseService) {
		this.teacherService = teacherService;
		this.courseService = courseService;
	}
	
	@GetMapping
	public String showAllTeachers(Model model, @Value("${itemsPerPage}") int itemsPerPage,
			@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(defaultValue = "Last_name") String sortBy) {
		List<Teacher> teachers = new ArrayList<>();
		Paginator paginator = new Paginator(teacherService.count(), currentPage, sortBy, itemsPerPage);
		teachers = teacherService.getAndSort(paginator);
		model.addAttribute("teachers", teachers);
		model.addAttribute("paginator", paginator);
		return "teachers-page";
	}
	
	@GetMapping("{id}")
	public String getTeacher(@PathVariable int id, Model model) {
		Teacher teacher = teacherService.getById(id).orElse(null);
		List<Course> allCourses = courseService.getAll();
		model.addAttribute("teacher", teacher);
		model.addAttribute("allCourses", allCourses);
		return "show-teacher";
	}
}
