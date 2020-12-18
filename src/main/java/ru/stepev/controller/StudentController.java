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
	
	@Value("${itemsPerPage}")
	private int itemsPerPage;
	@Value("${currentNumberOfPagesForPagination}")
	private int currentNumberOfPagesForPagination;


	public StudentController(StudentService studentService, CourseService courseService, GroupService groupService) {
		this.studentService = studentService;
		this.courseService = courseService;
		this.groupService = groupService;
	}

	@GetMapping("/showAllStudents")
	public String showAllStudents(Model model, @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "0") int currentBeginPagination, @RequestParam(defaultValue = "default") String sortedParam) {
		List<Student> studentsForShow = new ArrayList<>();
		Paginator paginator = new Paginator(studentService.getNumberOfItems(), currentPage, currentBeginPagination, itemsPerPage, currentNumberOfPagesForPagination);
		switch(sortedParam) {
			case ("First_name") : studentsForShow = studentService.getAndSortByFirstName(itemsPerPage, paginator.getOffset()); break;
			case ("Last_name")  : studentsForShow = studentService.getAndSortByLastName(itemsPerPage, paginator.getOffset()); break;
			case ("Id")  : studentsForShow = studentService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
			case ("Birthday")  : studentsForShow = studentService.getAndSortByBirthday(itemsPerPage, paginator.getOffset()); break;
			case ("Email")  : studentsForShow = studentService.getAndSortByEmail(itemsPerPage, paginator.getOffset()); break;
			case ("Address")  : studentsForShow = studentService.getAndSortByAddress(itemsPerPage, paginator.getOffset()); break;
			default : studentsForShow = studentService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
		}
		
		model.addAttribute("studentsForShow", studentsForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBeginPagination", paginator.getCurrentBeginPagination());
		model.addAttribute("currentNumberOfPagesForPagination", currentNumberOfPagesForPagination);
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
