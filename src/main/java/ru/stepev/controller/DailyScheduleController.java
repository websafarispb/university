package ru.stepev.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.DailySchedule;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;
import ru.stepev.service.DailyScheduleService;
import ru.stepev.service.StudentService;
import ru.stepev.service.TeacherService;

@Controller
public class DailyScheduleController {
	
	private DailyScheduleService dailyScheduleService;
	private TeacherService teacherService;
	private StudentService studentService;

	public DailyScheduleController(DailyScheduleService dailyScheduleService, TeacherService teacherService, StudentService studentService) {
		this.dailyScheduleService = dailyScheduleService;
		this.teacherService = teacherService;
		this.studentService = studentService;
	}
	
	@GetMapping("/showSchedule")
	public String showSchedule(Model model) {
		List<DailySchedule> dailySchedules = dailyScheduleService.getAll();
		model.addAttribute("schedules", dailySchedules);
		return "schedule-page";
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
}
