package ru.stepev.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import ru.stepev.service.GroupService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/groups")
public class GroupController {
	
	private GroupService groupService;
	
	@Value("${numberOfEntitiesForOnePage}")
	private int numberOfEntitiesForOnePage;
	@Value("${sizeOfDiapason}")
	private int sizeOfDiapason;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@GetMapping("/showAllGroups")
	public String showAllGroups(Model model, @RequestParam(defaultValue = "0") int diapason,
			@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(defaultValue = "default") String sortedParam) {
		List<Group> allGroups = groupService.getAll();
		switch(sortedParam) {
			case ("Name") : Collections.sort(allGroups, Comparator.comparing(Group::getName)); break;
			case ("Id")  : Collections.sort(allGroups, Comparator.comparing(Group::getId)); break;
			default : Collections.sort(allGroups, Comparator.comparing(Group::getId)); break;
		}
		Paginator paginator = new Paginator(allGroups.size(), currentPage, diapason, numberOfEntitiesForOnePage, sizeOfDiapason);
		List<Group> groupsForShow = allGroups.subList(paginator.getCurrentBeginOfEntities(), paginator.getCurrentEndOfEntities());
		model.addAttribute("groupsForShow", groupsForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("diapason", diapason);
		model.addAttribute("sizeOfDiapason", sizeOfDiapason);
		model.addAttribute("numberOfPages", paginator.getNumberOfPages());
		return "groups-page";
	}
	
	@GetMapping("/showEntity")
	public String showEntity(@RequestParam("groupId") int groupId, Model model) {
		Group group = groupService.getById(groupId).get();
		model.addAttribute("group", group);
		return "show-group";
	}
}
