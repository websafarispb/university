package ru.stepev.controller;

import java.util.List;
import static java.util.stream.Collectors.*;

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
import ru.stepev.service.CourseService;
import ru.stepev.service.GroupService;
import ru.stepev.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	private StudentService studentService;
	private CourseService courseService;
	private GroupService groupService;

	public StudentController(StudentService studentService, CourseService courseService, GroupService groupService) {
		this.studentService = studentService;
		this.courseService = courseService;
		this.groupService = groupService;
	}

	@GetMapping("/showAllStudents")
	public String showAllStudents(Model model) {
		List<Student> students = studentService.getAll();
		model.addAttribute("students", students);
		return "students-page";
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
	public String create(@ModelAttribute("student") Student student, @RequestParam("groupId") String groupId) {
		
		System.out.println(student);
		System.out.println(groupId);
		Group chosenGroup = groupService.getById(Integer.parseInt(groupId)).get();
		List<Student>students = chosenGroup.getStudents().stream().filter(s->s.getId()!=student.getId()).collect(toList());
		students.add(student);
		chosenGroup.setStudents(students);
		
		
		for(Course course : student.getCourses()) {
			Course tempCourse = courseService.getById(course.getId()).get();
			course.setName(tempCourse.getName());
			course.setDescription(tempCourse.getDescription());
		}
		studentService.add(student);
		groupService.update(chosenGroup);
		return "redirect:/students/showAllStudents";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("student") Student student, @RequestParam("groupId") String groupId) {
		
		Group currentGroup = groupService.findByStudentId(student.getId()).get();
		List<Student> students = currentGroup.getStudents().stream().filter(s->s.getId()!=student.getId()).collect(toList());
		currentGroup.setStudents(students);
		groupService.update(currentGroup);
		Group chosenGroup = groupService.getById(Integer.parseInt(groupId)).get();
		students = chosenGroup.getStudents().stream().filter(s->s.getId()!=student.getId()).collect(toList());
		students.add(student);
		chosenGroup.setStudents(students);
		groupService.update(chosenGroup);
		
		for(Course course : student.getCourses()) {
			Course tempCourse = courseService.getById(course.getId()).get();
			course.setName(tempCourse.getName());
			course.setDescription(tempCourse.getDescription());
		}
		studentService.update(student);
		return "redirect:/students/showAllStudents";
	}
	
	@GetMapping("/update")
	public String updateStudent(@RequestParam("studentId") int id, Model model) {
		Student student = studentService.getById(id).get();
		List<Course> allCourses = courseService.getAll();
		List<Group> allGroups = groupService.getAll();
		model.addAttribute("student", student);
		model.addAttribute("allCourses", allCourses);
		model.addAttribute("allGroups", allGroups);
		return "update-student";
	}

	@GetMapping("/delete")
	public String deleteStudent(@RequestParam("studentId") int studentId, Model model) {
		Student student = studentService.getById(studentId).get();
		studentService.delete(student);
		return "redirect:/students/showAllStudents";
	}
}
