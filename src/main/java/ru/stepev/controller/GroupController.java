package ru.stepev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Group;
import ru.stepev.service.GroupService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/groups")
public class GroupController {
	
	private GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@GetMapping
	public String showAllGroups(Model model, @Value("${itemsPerPage}") int itemsPerPage,
			@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(defaultValue = "default") String sortedParam) {
		Paginator paginator = new Paginator(groupService.count(), currentPage, sortedParam, itemsPerPage);
		List<Group> groupsForShow = groupService.getAndSortByName(paginator.getItemsPerPage(), paginator.getOffset());
		model.addAttribute("groupsForShow", groupsForShow);
		model.addAttribute("paginator", paginator);
		return "groups-page";
	}
	
	@GetMapping("{id}")
	public String showEntity(@PathVariable int id, Model model) {
		Group group = groupService.getById(id).get();
		model.addAttribute("group", group);
		return "show-group";
	}
}
