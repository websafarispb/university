package ru.stepev.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Classroom;
import ru.stepev.service.ClassroomService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {
	
	private ClassroomService classroomService;
	
	@Value("${numberOfEntitiesForOnePage}")
	private int numberOfEntitiesForOnePage;
	@Value("${sizeOfDiapason}")
	private int sizeOfDiapason;

	public ClassroomController(ClassroomService classroomService) {
		this.classroomService = classroomService;
	}
	
	@GetMapping("/showAllClassrooms")
	public String showAllClassrooms(Model model, @RequestParam(defaultValue = "0") int diapason,  @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "default") String sortedParam) {
		List<Classroom> allClassrooms = classroomService.getAll();
		switch(sortedParam) {
			case ("Address") : Collections.sort(allClassrooms, Comparator.comparing(Classroom::getAddress)); break;
			case ("Capacity")  : Collections.sort(allClassrooms, Comparator.comparing(Classroom::getCapacity)); break;
			case ("Id")  : Collections.sort(allClassrooms, Comparator.comparing(Classroom::getId)); break;
			default : Collections.sort(allClassrooms, Comparator.comparing(Classroom::getId)); break;
		}
		Paginator paginator = new Paginator(allClassrooms.size(), currentPage, diapason, numberOfEntitiesForOnePage, sizeOfDiapason);
		List<Classroom> classroomsForShow = allClassrooms.subList(paginator.getCurrentBeginOfEntities(), paginator.getCurrentEndOfEntities());
		model.addAttribute("classroomsForShow", classroomsForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("diapason", diapason);
		model.addAttribute("sizeOfDiapason", sizeOfDiapason);
		model.addAttribute("numberOfPages", paginator.getNumberOfPages());
		return "classrooms-page";
	}
	
	@GetMapping("/showEntity")
	public String showEntity(@RequestParam("classroomId") int classroomId, Model model) {	
		Classroom classroom = classroomService.getById(classroomId).get();
		model.addAttribute("classroom", classroom);
		return "show-classroom";
	}
}
