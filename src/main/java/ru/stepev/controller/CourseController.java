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
	
	@Value("${itemsPerPage}")
	private int itemsPerPage;
	@Value("${currentNumberOfPagesForPagination}")
	private int currentNumberOfPagesForPagination;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	} 
	
	@GetMapping("/showAllCourses")
	public String showAllCourses(Model model, @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "0") int currentBeginPagination, @RequestParam(defaultValue = "default") String sortedParam) {
		List<Course> coursesForShow = new ArrayList<>();
		Paginator paginator = new Paginator(courseService.getNumberOfItems(), currentPage, currentBeginPagination, itemsPerPage, currentNumberOfPagesForPagination);
		switch(sortedParam) {
			case ("Name") : coursesForShow = courseService.getAndSortByName(itemsPerPage, paginator.getOffset()); break;
			case ("Id")  : coursesForShow = courseService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
			default :  coursesForShow = courseService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
		}

		model.addAttribute("coursesForShow", coursesForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBeginPagination", paginator.getCurrentBeginPagination());
		model.addAttribute("currentNumberOfPagesForPagination", currentNumberOfPagesForPagination);
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
