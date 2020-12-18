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
	
	@Value("${itemsPerPage}")
	private int itemsPerPage;
	@Value("${currentNumberOfPagesForPagination}")
	private int currentNumberOfPagesForPagination;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@GetMapping("/showAllGroups")
	public String showAllGroups(Model model, @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "0") int currentBeginPagination, @RequestParam(defaultValue = "default") String sortedParam) {
		List<Group> groupsForShow = new ArrayList<>();
		Paginator paginator = new Paginator(groupService.getNumberOfItems(), currentPage, currentBeginPagination, itemsPerPage, currentNumberOfPagesForPagination);
		switch(sortedParam) {
			case ("Name") : groupsForShow = groupService.getAndSortByName(itemsPerPage, paginator.getOffset()); break;
			case ("Id")  : groupsForShow = groupService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
			default : groupsForShow = groupService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
		}
		model.addAttribute("groupsForShow", groupsForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBeginPagination", paginator.getCurrentBeginPagination());
		model.addAttribute("currentNumberOfPagesForPagination", currentNumberOfPagesForPagination);
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
