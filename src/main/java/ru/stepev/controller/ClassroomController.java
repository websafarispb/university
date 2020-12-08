package ru.stepev.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.service.ClassroomService;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {
	
	private ClassroomService classroomService;

	public ClassroomController(ClassroomService classroomService) {
		this.classroomService = classroomService;
	}
	
	@GetMapping("/showAllClassrooms")
	public String showAllClassrooms(Model model) {

		List<Classroom> classrooms = classroomService.getAll();
		model.addAttribute("classrooms", classrooms);
		return "classrooms-page";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("classroomId") int classroomId, Model model) {	
		Classroom classroom = classroomService.getById(classroomId).get();
		classroomService.delete(classroom);
		return "redirect:/classrooms/showAllClassrooms";
	}
	
	@GetMapping("/update")
	public String update(@RequestParam("classroomId") int classroomId, Model model) {	
		Classroom classroom = classroomService.getById(classroomId).get();
		model.addAttribute("classroom", classroom);
		return "update-classroom";
	}
	
	@GetMapping("/add")
	public String add( Model model) {	
		model.addAttribute("classroom", new Classroom());
		return "add-classroom";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute Classroom classroom) {	
		classroomService.update(classroom);
		return "redirect:/classrooms/showAllClassrooms";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute Classroom classroom) {	
		classroomService.add(classroom);
		return "redirect:/classrooms/showAllClassrooms";
	}
}
