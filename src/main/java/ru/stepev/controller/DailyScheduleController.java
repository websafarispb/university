package ru.stepev.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	public DailyScheduleController(DailyScheduleService dailyScheduleService, TeacherService teacherService, StudentService studentService) {
		this.dailyScheduleService = dailyScheduleService;
		this.teacherService = teacherService;
		this.studentService = studentService;
	}
	
	@GetMapping
	public String showAllDailySchedules(Model model, @Value("${itemsPerPage}") int itemsPerPage,
			@RequestParam(defaultValue = "1") int currentPage,
			 @RequestParam(defaultValue = "Date") String sortBy) {
		List<DailySchedule> dailySchedules = new ArrayList<>();
		Paginator paginator = new Paginator(dailyScheduleService.count(), currentPage, sortBy , itemsPerPage);
		dailySchedules = dailyScheduleService.getAndSortByDate(paginator.getItemsPerPage(),paginator.getOffset());
		model.addAttribute("dailySchedules", dailySchedules);
		model.addAttribute("paginator", paginator);
		return "schedule-page";
	}
	
	@GetMapping("/{id}")
	public String getSchedule(@PathVariable int id, Model model) {
		DailySchedule dailySchedule = dailyScheduleService.getById(id).orElse(null);
		model.addAttribute("dailySchedule", dailySchedule);
		return "show-dailyschedule";
	}
	
	@GetMapping("/showScheduleForTeacherForm")
	public String showScheduleForTeacherForm(Model model) {
		List<DailySchedule> dailySchedules = dailyScheduleService.getAll();
		List<Teacher> allTeachers = teacherService.getAll();
		model.addAttribute("dailySchedules", dailySchedules);
		model.addAttribute("allTeachers", allTeachers);
		return "scheduleForTeacherForm";
	}
	
	@GetMapping("/showScheduleForStudentForm")
	public String showScheduleForStudentForm(Model model) {
		List<DailySchedule> dailySchedules = dailyScheduleService.getAll();
		List<Student> allStudents = studentService.getAll();
		model.addAttribute("dailySchedules", dailySchedules);
		model.addAttribute("allStudents", allStudents);
		return "scheduleForStudentForm";
	}
	
	@GetMapping("/showScheduleForTeacher")
	public String showScheduleForTeacher(@RequestParam("teacherId") String teacherId,
			@RequestParam("firstDate") String firstDate, @RequestParam("lastDate") String lastDate, Model model,
			@Value("${itemsPerPage}") int itemsPerPage, @RequestParam(defaultValue = "1") int currentPage,
			 @RequestParam(defaultValue = "Date") String sortBy) {
		LocalDate firstDay = LocalDate.parse(firstDate);	
		LocalDate lastDay  = LocalDate.parse(lastDate);
		Paginator paginator = new Paginator(dailyScheduleService.count(), currentPage, sortBy , itemsPerPage);
		List<DailySchedule> dailySchedules = dailyScheduleService.getSortedScheduleForTeacher(Integer.parseInt(teacherId), firstDay, lastDay, paginator);
		paginator = new Paginator(dailySchedules.size(), currentPage, sortBy , itemsPerPage);
		model.addAttribute("dailySchedulesForShow", dailySchedules);
		model.addAttribute("paginator", paginator);
		return "schedule-page";
	}
	
	@GetMapping("/showScheduleForStudent")
	public String showScheduleForStudent(@RequestParam("studentId") String studentId,
			@RequestParam("firstDate") String firstDate, @RequestParam("lastDate") String lastDate, Model model,
			@Value("${itemsPerPage}") int itemsPerPage, @RequestParam(defaultValue = "1") int currentPage,
			 @RequestParam(defaultValue = "Date")String sortBy) {
		LocalDate firstDay = LocalDate.parse(firstDate);	
		LocalDate lastDay  = LocalDate.parse(lastDate);
		Paginator paginator = new Paginator(dailyScheduleService.count(), currentPage, sortBy , itemsPerPage);
		List<DailySchedule> dailySchedules = dailyScheduleService.getSortedScheduleForStudent(Integer.parseInt(studentId), firstDay, lastDay, paginator);
		paginator = new Paginator(dailySchedules.size(), currentPage, sortBy , itemsPerPage);
		model.addAttribute("dailySchedulesForShow", dailySchedules);
		model.addAttribute("paginator", paginator);
		return "schedule-page";
	}
}
