package ru.stepev.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Course;
import ru.stepev.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {
	
	private CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	} 
	
	@GetMapping("/showAllCourses")
	public String showAllCourses(Model model) {
		List<Course> courses = courseService.getAll();
		model.addAttribute("courses", courses);
		return "courses-page";
	}
	
	@GetMapping("/addCourse")
	public String addCourse(Model model) {
		model.addAttribute("course", new Course());
		return "add-course";
	}
	
	@GetMapping("/delete")
	public String deleteCourse(@RequestParam("courseId") int courseId, Model model) {
		Course course = courseService.getById(courseId).get();
		courseService.delete(course);
		return "redirect:/courses/showAllCourses";
	}
	
	@GetMapping("/update")
	public String updateCourse(@RequestParam("courseId") int courseId, Model model) {
		Course course = courseService.getById(courseId).get();
		model.addAttribute("course", course);
		return "update-course";
	}
	
	@PostMapping("/create")
	public String createCourse(@ModelAttribute Course course) {
		courseService.add(course);
		return "redirect:/courses/showAllCourses";
	}
	
	@PostMapping("/save")
	public String saveCourse(@ModelAttribute Course course) {
		courseService.update(course);
		return "redirect:/courses/showAllCourses";
	}
}
