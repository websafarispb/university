package ru.stepev.controller;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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
import ru.stepev.service.GroupService;
import ru.stepev.service.StudentService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/students")
public class StudentController {

	private StudentService studentService;
	private CourseService courseService;
	private GroupService groupService;
	
	@Value("${numberOfEntitiesForOnePage}")
	private int numberOfEntitiesForOnePage;
	@Value("${sizeOfDiapason}")
	private int sizeOfDiapason;


	public StudentController(StudentService studentService, CourseService courseService, GroupService groupService) {
		this.studentService = studentService;
		this.courseService = courseService;
		this.groupService = groupService;
	}

	@GetMapping("/showAllStudents")
	public String showAllStudents(Model model, @RequestParam(defaultValue = "0") int diapason,  @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "default") String sortedParam) {
		List<Student> allStudents = studentService.getAll();
		switch(sortedParam) {
			case ("First_name") : Collections.sort(allStudents, Comparator.comparing(Student::getFirstName)); break;
			case ("Last_name")  : Collections.sort(allStudents, Comparator.comparing(Student::getLastName)); break;
			case ("Id")  : Collections.sort(allStudents, Comparator.comparing(Student::getId)); break;
			case ("Birthday")  : Collections.sort(allStudents, Comparator.comparing(Student::getBirthday)); break;
			case ("Email")  : Collections.sort(allStudents, Comparator.comparing(Student::getEmail)); break;
			case ("Address")  : Collections.sort(allStudents, Comparator.comparing(Student::getAddress)); break;
			default : Collections.sort(allStudents, Comparator.comparing(Student::getId)); break;
		}
		Paginator paginator = new Paginator(allStudents.size(), currentPage, diapason, numberOfEntitiesForOnePage, sizeOfDiapason);
		List<Student> studentsForShow = allStudents.subList(paginator.getCurrentBeginOfEntities(), paginator.getCurrentEndOfEntities());
		model.addAttribute("studentsForShow", studentsForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("diapason", diapason);
		model.addAttribute("sizeOfDiapason", sizeOfDiapason);
		model.addAttribute("numberOfPages", paginator.getNumberOfPages());
		return "students-page";
	}

	@GetMapping("/showEntity")
	public String showEntity(Model model, @RequestParam("studentId") int studentId) {
		Student student = studentService.getById(studentId).get();
		Group group = groupService.findByStudentId(studentId).get();
		model.addAttribute("student", student);
		model.addAttribute("group", group);
		return "show-student";
	}
}
