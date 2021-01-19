package ru.stepev.controller;

import java.util.ArrayList;
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
		Teacher teacher = teacherService.getById(id).orElseThrow();
		List<Course> allCourses = courseService.getAll();
		model.addAttribute("teacher", teacher);
		model.addAttribute("allCourses", allCourses);
		return "show-teacher";
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
	public String create(@ModelAttribute("teacher") Teacher teacher, Model model) {
		for (Course course : teacher.getCourses()) {
			Course tempCourse = courseService.getById(course.getId()).orElseThrow();
			course.setName(tempCourse.getName());
			course.setDescription(tempCourse.getDescription());
		}
		teacherService.add(teacher);
		return "redirect:/teachers";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, Model model) {
		Teacher teacher = teacherService.getById(id).orElseThrow();
		teacherService.delete(teacher);
		return "redirect:/teachers";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable int id, Model model) {
		Teacher teacher = teacherService.getById(id).orElseThrow();
		List<Course> allCourses = courseService.getAll();
		model.addAttribute("teacher", teacher);
		model.addAttribute("allCourses", allCourses);
		return "update-teacher";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("teacher") Teacher teacher, Model model) {
		for (Course course : teacher.getCourses()) {
			Course tempCourse = courseService.getById(course.getId()).orElseThrow();
			course.setName(tempCourse.getName());
			course.setDescription(tempCourse.getDescription());
		}
		teacherService.update(teacher);
		return "redirect:/teachers";
	}

}
