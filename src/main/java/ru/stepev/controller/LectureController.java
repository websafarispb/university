package ru.stepev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Teacher;
import ru.stepev.service.ClassroomService;
import ru.stepev.service.CourseService;
import ru.stepev.service.DailyScheduleService;
import ru.stepev.service.GroupService;
import ru.stepev.service.LectureService;
import ru.stepev.service.TeacherService;
import ru.stepev.utils.Paginator;

@Controller
@RequestMapping("/lectures")
public class LectureController {

	private LectureService lectureService;
	private DailyScheduleService dailyScheduleService;
	private CourseService courseService;
	private ClassroomService classroomService;
	private GroupService groupService;
	private TeacherService teacherService;

	public LectureController(LectureService lectureService, DailyScheduleService dailyScheduleService,
			CourseService courseService, ClassroomService classroomService, GroupService groupService,
			TeacherService teacherService) {
		this.lectureService = lectureService;
		this.dailyScheduleService = dailyScheduleService;
		this.courseService = courseService;
		this.classroomService = classroomService;
		this.groupService = groupService;
		this.teacherService = teacherService;
	}

	@GetMapping
	public String showAllLectures(Model model, @Value("${itemsPerPage}") int itemsPerPage,
			@RequestParam(defaultValue = "1") int currentPage,
			 @RequestParam(defaultValue = "Time")String sortBy) {
		Paginator paginator = new Paginator(lectureService.count(), currentPage, sortBy, itemsPerPage);
		List<Lecture> lectures = lectureService.getAndSort(paginator);
		model.addAttribute("lectures", lectures);
		model.addAttribute("paginator", paginator);
		return "lectures-page";
	}

	@GetMapping("{id}")
	public String getLecture(@PathVariable int id, Model model) {
		Lecture lecture = lectureService.getById(id).get();
		model.addAttribute("lecture", lecture);
		return "show-lecture";
	}
}
