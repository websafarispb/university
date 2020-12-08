package ru.stepev.controller;

import java.util.ArrayList;
import java.util.List;

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

@Controller
@RequestMapping("/groups")
public class GroupController {
	
	private GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@GetMapping("/showAllGroups")
	public String showAllGroups(Model model) {
		List<Group> groups = groupService.getAll();
		model.addAttribute("groups", groups);
		return "groups-page";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		Group group = new Group();
		model.addAttribute("group", group);
		return "add-group";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("groupId") int groupId) {
		Group group = groupService.getById(groupId).get();
		groupService.delete(group);
		return "redirect:/groups/showAllGroups";
	}
	
	@GetMapping("/update")
	public String update(@RequestParam("groupId") int groupId, Model model) {
		Group group = groupService.getById(groupId).get();
		model.addAttribute("group", group);
		return "update-group";
	}
	
	@PostMapping("/create")
	public String createGroup(@ModelAttribute Group group) {
		group.setStudents(new ArrayList<>());
		groupService.add(group);
		return "redirect:/groups/showAllGroups";
	}
	
	@PostMapping("/save")
	public String saveGroup(@ModelAttribute Group group) {
		group.setStudents(groupService.getById(group.getId()).get().getStudents());
		groupService.update(group);
		return "redirect:/groups/showAllGroups";
	}
}
