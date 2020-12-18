package ru.stepev.controller;

import static java.util.stream.Collectors.toList;

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

import ru.stepev.model.Course;
import ru.stepev.model.Group;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;
import ru.stepev.service.CourseService;
import ru.stepev.service.TeacherService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
	
	private TeacherService teacherService;
	private CourseService courseService;
	
	@Value("${itemsPerPage}")
	private int itemsPerPage;
	@Value("${currentNumberOfPagesForPagination}")
	private int currentNumberOfPagesForPagination;

	public TeacherController(TeacherService teacherService, CourseService courseService) {
		this.teacherService = teacherService;
		this.courseService = courseService;
	}
	
	@GetMapping("/showAllTeachers")
	public String showAllTeachers(Model model, @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "0") int currentBeginPagination, @RequestParam(defaultValue = "default") String sortedParam){
		List<Teacher> teachersForShow = new ArrayList<>();
		Paginator paginator = new Paginator(teacherService.getNumberOfItems(), currentPage, currentBeginPagination, itemsPerPage, currentNumberOfPagesForPagination);
		switch(sortedParam) {
			case ("First_name") : teachersForShow = teacherService.getAndSortByFirstName(itemsPerPage, paginator.getOffset()); break;
			case ("Last_name")  : teachersForShow = teacherService.getAndSortByLastName(itemsPerPage, paginator.getOffset()); break;
			case ("Id")  : teachersForShow = teacherService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
			case ("Birthday")  : teachersForShow = teacherService.getAndSortByBirthday(itemsPerPage, paginator.getOffset()); break;
			case ("Email")  : teachersForShow = teacherService.getAndSortByEmail(itemsPerPage, paginator.getOffset()); break;
			case ("Address")  : teachersForShow = teacherService.getAndSortByAddress(itemsPerPage, paginator.getOffset()); break;
			default : teachersForShow = teacherService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
		}
		
		model.addAttribute("teachersForShow", teachersForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBeginPagination", paginator.getCurrentBeginPagination());
		model.addAttribute("currentNumberOfPagesForPagination", currentNumberOfPagesForPagination);
		model.addAttribute("numberOfPages", paginator.getNumberOfPages());
		return "teachers-page";
	}
	
	@GetMapping("/showEntity")
	public String showEntity(@RequestParam("teacherId") int id, Model model) {
		Teacher teacher = teacherService.getById(id).get();
		List<Course> allCourses = courseService.getAll();
		model.addAttribute("teacher", teacher);
		model.addAttribute("allCourses", allCourses);
		return "show-teacher";
	}
}
