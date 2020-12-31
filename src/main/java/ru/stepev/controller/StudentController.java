package ru.stepev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Group;
import ru.stepev.model.Student;
import ru.stepev.service.GroupService;
import ru.stepev.service.StudentService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/students")
public class StudentController {

	private StudentService studentService;
	private GroupService groupService;

	public StudentController(StudentService studentService,  GroupService groupService) {
		this.studentService = studentService;
		this.groupService = groupService;
	}

	@GetMapping
	public String showAllStudents(Model model, @Value("${itemsPerPage}") int itemsPerPage,
			@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(defaultValue = "Last_name") String sortBy) {
		Paginator paginator = new Paginator(studentService.count(), currentPage, sortBy, itemsPerPage);
		List<Student> students = studentService.getAndSort(paginator);
		model.addAttribute("students", students);
		model.addAttribute("paginator", paginator);
		return "students-page";
	}

	@GetMapping("{id}")
	public String getStudent(@PathVariable int id, Model model) {
		Student student = studentService.getById(id).orElse(null);
		Group group = groupService.findByStudentId(id).orElse(null);
		model.addAttribute("student", student);
		model.addAttribute("group", group);
		return "show-student";
	}
}
