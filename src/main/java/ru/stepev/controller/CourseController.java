package ru.stepev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Course;
import ru.stepev.service.CourseService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/courses")
public class CourseController {
	
	private CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	} 
	
	@GetMapping
	public String showAllCourses(Model model, @Value("${itemsPerPage}") int itemsPerPage,
			@RequestParam(defaultValue = "1") int currentPage,
			 @RequestParam(defaultValue = "Name")String sortBy) {
		Paginator paginator = new Paginator(courseService.count(), currentPage,  sortBy , itemsPerPage);
		List<Course>courses = courseService.getAndSortByName(paginator.getItemsPerPage(), paginator.getOffset());
		model.addAttribute("courses", courses);
		model.addAttribute("paginator", paginator);
		return "courses-page";
	}
	
	@GetMapping("/{id}")
	public String getCourse(@PathVariable int id, Model model) {
		Course course = courseService.getById(id).orElse(null);
		model.addAttribute("course", course);
		return "show-course";
	}
}
