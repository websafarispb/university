package ru.stepev.controller;

import java.util.ArrayList;
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
	
	@Value("${itemsPerPage}")
	private int itemsPerPage;
	@Value("${currentNumberOfPagesForPagination}")
	private int currentNumberOfPagesForPagination;

	public ClassroomController(ClassroomService classroomService) {
		this.classroomService = classroomService;
	}
	
	@GetMapping("/showAllClassrooms")
	public String showAllClassrooms(Model model, @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "0") int currentBeginPagination, @RequestParam(defaultValue = "default") String sortedParam) {
		List<Classroom> classroomsForShow = new ArrayList<>();
		Paginator paginator = new Paginator(classroomService.getNumberOfItems(), currentPage, currentBeginPagination, itemsPerPage, currentNumberOfPagesForPagination);
		switch(sortedParam) {
			case ("Address") : classroomsForShow = classroomService.getAndSortByAddress(itemsPerPage, paginator.getOffset()); break;
			case ("Capacity")  : classroomsForShow = classroomService.getAndSortByCapacity(itemsPerPage, paginator.getOffset()); break;
			case ("Id")  : classroomsForShow = classroomService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
			default : classroomsForShow = classroomService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
		}
		model.addAttribute("classroomsForShow", classroomsForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBeginPagination", paginator.getCurrentBeginPagination());
		model.addAttribute("currentNumberOfPagesForPagination", currentNumberOfPagesForPagination);
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
