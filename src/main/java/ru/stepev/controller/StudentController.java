package ru.stepev.controller;

import java.util.List;
import static java.util.stream.Collectors.*;

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
import ru.stepev.model.Group;
import ru.stepev.model.Student;
import ru.stepev.service.CourseService;
import ru.stepev.service.GroupService;
import ru.stepev.service.StudentService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/students")
public class StudentController {

	private StudentService studentService;
	private GroupService groupService;
	private CourseService courseService;

	public StudentController(StudentService studentService, GroupService groupService, CourseService courseService) {
		this.studentService = studentService;
		this.groupService = groupService;
		this.courseService = courseService;
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
		Student student = studentService.getById(id).orElse(new Student());
		Group group = groupService.findByStudentId(id).orElse(new Group());
		model.addAttribute("student", student);
		model.addAttribute("group", group);
		return "show-student";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Student student = new Student();
		List<Course> allCourses = courseService.getAll();
		List<Group> allGroups = groupService.getAll();
		model.addAttribute("student", student);
		model.addAttribute("allCourses", allCourses);
		model.addAttribute("allGroups", allGroups);
		return "add-student";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute("student") Student student, @RequestParam("groupId") String groupId,
			Model model) {
		Group chosenGroup = groupService.getById(Integer.parseInt(groupId)).orElse(new Group());
		List<Student> students = chosenGroup.getStudents().stream().filter(s -> s.getId() != student.getId())
				.collect(toList());
		students.add(student);
		chosenGroup.setStudents(students);

		for (Course course : student.getCourses()) {
			Course tempCourse = courseService.getById(course.getId()).orElse(new Course());
			course.setName(tempCourse.getName());
			course.setDescription(tempCourse.getDescription());
		}

		studentService.add(student);
		groupService.update(chosenGroup);
		return "redirect:/students";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("student") Student student, @RequestParam("groupId") String groupId,
			Model model) {
		Group currentGroup = groupService.findByStudentId(student.getId()).orElse(new Group());
		List<Student> students = currentGroup.getStudents().stream().filter(s -> s.getId() != student.getId())
				.collect(toList());
		currentGroup.setStudents(students);
		groupService.update(currentGroup);
		Group chosenGroup = groupService.getById(Integer.parseInt(groupId)).orElse(new Group());
		students = chosenGroup.getStudents().stream().filter(s -> s.getId() != student.getId()).collect(toList());
		students.add(student);
		chosenGroup.setStudents(students);
		groupService.update(chosenGroup);

		for (Course course : student.getCourses()) {
			Course tempCourse = courseService.getById(course.getId()).orElse(new Course());
			course.setName(tempCourse.getName());
			course.setDescription(tempCourse.getDescription());
		}
		studentService.update(student);
		return "redirect:/students";
	}

	@GetMapping("/update/{id}")
	public String updateStudent(@PathVariable int id, Model model) {
		Student student = studentService.getById(id).orElse(new Student());
		List<Course> allCourses = courseService.getAll();
		List<Group> allGroups = groupService.getAll();
		model.addAttribute("student", student);
		model.addAttribute("allCourses", allCourses);
		model.addAttribute("allGroups", allGroups);
		return "update-student";
	}

	@GetMapping("/delete/{id}")
	public String deleteStudent(@PathVariable int id, Model model) {
		Student student = studentService.getById(id).orElse(new Student());
		studentService.delete(student);
		return "redirect:/students";
	}

}
