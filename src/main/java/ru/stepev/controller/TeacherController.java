package ru.stepev.controller;

import static java.util.stream.Collectors.toList;

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
	
	@Value("${numberOfEntitiesForOnePage}")
	private int numberOfEntitiesForOnePage;
	@Value("${sizeOfDiapason}")
	private int sizeOfDiapason;

	public TeacherController(TeacherService teacherService, CourseService courseService) {
		this.teacherService = teacherService;
		this.courseService = courseService;
	}
	
	@GetMapping("/showAllTeachers")
	public String showAllTeachers(Model model, @RequestParam(defaultValue = "0") int diapason,  @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "default") String sortedParam){
		List<Teacher> allTeachers = teacherService.getAll();
		switch(sortedParam) {
			case ("First_name") : Collections.sort(allTeachers, Comparator.comparing(Teacher::getFirstName)); break;
			case ("Last_name")  : Collections.sort(allTeachers, Comparator.comparing(Teacher::getLastName)); break;
			case ("Id")  : Collections.sort(allTeachers, Comparator.comparing(Teacher::getId)); break;
			case ("Birthday")  : Collections.sort(allTeachers, Comparator.comparing(Teacher::getBirthday)); break;
			case ("Email")  : Collections.sort(allTeachers, Comparator.comparing(Teacher::getEmail)); break;
			case ("Address")  : Collections.sort(allTeachers, Comparator.comparing(Teacher::getAddress)); break;
			default : Collections.sort(allTeachers, Comparator.comparing(Teacher::getId)); break;
		}
		
		
		Paginator paginator = new Paginator(allTeachers.size(), currentPage, diapason, numberOfEntitiesForOnePage, sizeOfDiapason);
		List<Teacher> teachersForShow = allTeachers.subList(paginator.getCurrentBeginOfEntities(), paginator.getCurrentEndOfEntities());
		model.addAttribute("teachersForShow", teachersForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("diapason", diapason);
		model.addAttribute("sizeOfDiapason", sizeOfDiapason);
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
