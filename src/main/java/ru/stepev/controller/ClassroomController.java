package ru.stepev.controller;

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

import ru.stepev.model.Classroom;
import ru.stepev.service.ClassroomService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

	private ClassroomService classroomService;

	public ClassroomController(ClassroomService classroomService) {
		this.classroomService = classroomService;
	}

	@GetMapping
	public String showAllClassrooms(Model model, @Value("${itemsPerPage}") int itemsPerPage,
			@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "Address") String sortBy) {
		Paginator paginator = new Paginator(classroomService.count(), currentPage, sortBy, itemsPerPage);
		List<Classroom> classrooms = classroomService.getAndSort(paginator);
		model.addAttribute("classrooms", classrooms);
		model.addAttribute("paginator", paginator);
		return "classrooms-page";
	}

	@GetMapping("/{id}")
	public String getClassroom(@PathVariable int id, Model model) {
		Classroom classroom = classroomService.getById(id).orElse(new Classroom());
		model.addAttribute("classroom", classroom);
		return "show-classroom";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, Model model) {
		Classroom classroom = classroomService.getById(id).orElse(new Classroom());
		classroomService.delete(classroom);
		return "redirect:/classrooms";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable int id, Model model) {
		Classroom classroom = classroomService.getById(id).orElse(new Classroom());
		model.addAttribute("classroom", classroom);
		return "update-classroom";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("classroom", new Classroom());
		return "add-classroom";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute Classroom classroom, Model model) {
		classroomService.update(classroom);
		return "redirect:/classrooms";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute Classroom classroom, Model model) {
		classroomService.add(classroom);
		return "redirect:/classrooms";
	}
}
