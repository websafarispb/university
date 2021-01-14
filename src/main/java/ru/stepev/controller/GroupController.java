package ru.stepev.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Group;
import ru.stepev.model.Student;
import ru.stepev.service.GroupService;
import ru.stepev.service.StudentService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/groups")
public class GroupController {

	private GroupService groupService;
	private StudentService studentService;

	public GroupController(GroupService groupService, StudentService studentService) {
		this.groupService = groupService;
		this.studentService = studentService;
	}

	@GetMapping
	public String showAllGroups(Model model, @Value("${itemsPerPage}") int itemsPerPage,
			@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "Name") String sortBy) {
		Paginator paginator = new Paginator(groupService.count(), currentPage, sortBy, itemsPerPage);
		List<Group> groups = groupService.getAndSortByName(paginator.getItemsPerPage(), paginator.getOffset());
		model.addAttribute("groups", groups);
		model.addAttribute("paginator", paginator);
		return "groups-page";
	}

	@GetMapping("{id}")
	public String getGroup(@PathVariable int id, Model model) {
		Group group = groupService.getById(id).orElse(new Group());
		model.addAttribute("group", group);
		return "show-group";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Group group = new Group();
		model.addAttribute("group", group);
		return "add-group";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, Model model) {
		Group group = groupService.getById(id).orElse(new Group());
		groupService.delete(group);
		return "redirect:/groups";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable int id, Model model) {
		Group group = groupService.getById(id).get();
		List<Student> students = studentService.getAll();
		model.addAttribute("group", group);
		model.addAttribute("allStudents", students);
		return "update-group";
	}

	@PostMapping("/create")
	public String createGroup(@ModelAttribute Group group, Model model) {
		group.setStudents(new ArrayList<>());
		groupService.add(group);
		return "redirect:/groups";
	}

	@PostMapping("/save")
	public String saveGroup(@ModelAttribute Group group, Model model) {
		List<Student> students = new ArrayList<>();
		for (Student student : group.getStudents()) {
			students.add(studentService.getById(student.getId()).orElse(new Student()));
		}
		group.setStudents(students);
		groupService.update(group);
		return "redirect:/groups";
	}

}
