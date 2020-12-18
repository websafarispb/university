package ru.stepev.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.DailySchedule;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;
import ru.stepev.service.DailyScheduleService;
import ru.stepev.service.StudentService;
import ru.stepev.service.TeacherService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/dailySchedules")
public class DailyScheduleController {
	
	private DailyScheduleService dailyScheduleService;
	private TeacherService teacherService;
	private StudentService studentService;
	
	@Value("${itemsPerPage}")
	private int itemsPerPage;
	@Value("${currentNumberOfPagesForPagination}")
	private int currentNumberOfPagesForPagination;

	public DailyScheduleController(DailyScheduleService dailyScheduleService, TeacherService teacherService, StudentService studentService) {
		this.dailyScheduleService = dailyScheduleService;
		this.teacherService = teacherService;
		this.studentService = studentService;
	}
	
	@GetMapping("/showScheduleForTeacherForm")
	public String showScheduleForTeacherForm(Model model) {
		List<DailySchedule> dailySchedules = dailyScheduleService.getAll();
		List<Teacher> allTeachers = teacherService.getAll();
		model.addAttribute("dailySchedules", dailySchedules);
		model.addAttribute("allTeachers", allTeachers);
		return "scheduleForTeacherForm";
	}
	
	@GetMapping("/showEntity")
	public String showEntity(@RequestParam("dailyScheduleId") int dailyScheduleId, Model model) {
		DailySchedule dailySchedule = dailyScheduleService.getById(dailyScheduleId).get();
		model.addAttribute("dailySchedule", dailySchedule);
		return "show-dailyschedule";
	}
	
	@GetMapping("/showScheduleForStudentForm")
	public String showScheduleForStudentForm(Model model) {
		List<DailySchedule> dailySchedules = dailyScheduleService.getAll();
		List<Student> allStudents = studentService.getAll();
		model.addAttribute("dailySchedules", dailySchedules);
		model.addAttribute("allStudents", allStudents);
		return "scheduleForStudentForm";
	}
	
	@PostMapping("showScheduleForTeacher")
	public String showScheduleForTeacher(@RequestParam("teacherId") String teacherId, @RequestParam("firstDate") String firstDate,  @RequestParam("lastDate") String lastDate, Model model) {
		LocalDate firstDay = LocalDate.parse(firstDate);	
		LocalDate lastDay  = LocalDate.parse(lastDate);
		List<DailySchedule> dailySchedules = dailyScheduleService.getScheduleForTeacher(Integer.parseInt(teacherId), firstDay, lastDay);
		model.addAttribute("schedules", dailySchedules);
		return "schedule-page";
	}
	
	@PostMapping("/showScheduleForStudent")
	public String showScheduleForStudent(@RequestParam("studentId") String studentId, @RequestParam("firstDate") String firstDate,  @RequestParam("lastDate") String lastDate, Model model) {
		LocalDate firstDay = LocalDate.parse(firstDate);	
		LocalDate lastDay  = LocalDate.parse(lastDate);
		List<DailySchedule> dailySchedules = dailyScheduleService.getScheduleForStudent(Integer.parseInt(studentId), firstDay, lastDay);
		System.out.println("dailySchedules" +dailySchedules);
		model.addAttribute("schedules", dailySchedules);
		return "schedule-page";
	}
	
	@GetMapping("/showAllDailySchedules")
	public String showAllDailySchedules(Model model, @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "0") int currentBeginPagination, @RequestParam(defaultValue = "default") String sortedParam) {
		List<DailySchedule> dailySchedulesForShow = new ArrayList<>();
		Paginator paginator = new Paginator(dailyScheduleService.getNumberOfItems(), currentPage, currentBeginPagination, itemsPerPage, currentNumberOfPagesForPagination);
		switch(sortedParam) {
			case ("Date") : dailySchedulesForShow = dailyScheduleService.getAndSortByDate(itemsPerPage, paginator.getOffset()); break;
			case ("Id")  : dailySchedulesForShow = dailyScheduleService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
			default : dailySchedulesForShow = dailyScheduleService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
		}	
		model.addAttribute("dailySchedulesForShow", dailySchedulesForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBeginPagination", paginator.getCurrentBeginPagination());
		model.addAttribute("currentNumberOfPagesForPagination", currentNumberOfPagesForPagination);
		model.addAttribute("numberOfPages", paginator.getNumberOfPages());
		return "schedule-page";
	}
}
