package ru.stepev.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.service.CourseService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/courses")
public class CourseController {
	
	private CourseService courseService;
	
	@Value("${numberOfEntitiesForOnePage}")
	private int numberOfEntitiesForOnePage;
	@Value("${sizeOfDiapason}")
	private int sizeOfDiapason;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	} 
	
	@GetMapping("/showAllCourses")
	public String showAllCourses(Model model, @RequestParam(defaultValue = "0") int diapason,
			@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(defaultValue = "default") String sortedParam) {
		List<Course> allCourses = courseService.getAll();
		switch(sortedParam) {
			case ("Name") : Collections.sort(allCourses, Comparator.comparing(Course::getName)); break;
			case ("Id")  : Collections.sort(allCourses, Comparator.comparing(Course::getId)); break;
			default : Collections.sort(allCourses, Comparator.comparing(Course::getId)); break;
		}
		Paginator paginator = new Paginator(allCourses.size(), currentPage, diapason, numberOfEntitiesForOnePage, sizeOfDiapason);
		List<Course> coursesForShow = allCourses.subList(paginator.getCurrentBeginOfEntities(), paginator.getCurrentEndOfEntities());
		model.addAttribute("coursesForShow", coursesForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("diapason", diapason);
		model.addAttribute("sizeOfDiapason", sizeOfDiapason);
		model.addAttribute("numberOfPages", paginator.getNumberOfPages());
		return "courses-page";
	}
	
	@GetMapping("/showEntity")
	public String showEntity(@RequestParam("courseId") int courseId, Model model) {
		Course course = courseService.getById(courseId).get();
		model.addAttribute("course", course);
		return "show-course";
	}
}
