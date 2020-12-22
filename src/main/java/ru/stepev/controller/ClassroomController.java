package ru.stepev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(defaultValue = "default") String sortedParam) {
		Paginator paginator = new Paginator(classroomService.count(), currentPage, sortedParam, itemsPerPage);
		List<Classroom> classroomsForShow = classroomService.getAndSort(paginator);
		model.addAttribute("classroomsForShow", classroomsForShow);
		model.addAttribute("paginator", paginator);
		return "classrooms-page";
	}

	@GetMapping("/{id}")
	public String showEntity(@PathVariable int id, Model model) {
		Classroom classroom = classroomService.getById(id).get();
		model.addAttribute("classroom", classroom);
		return "show-classroom";
	}
}
