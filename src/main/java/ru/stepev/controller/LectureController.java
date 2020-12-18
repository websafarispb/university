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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@Value("${itemsPerPage}")
	private int itemsPerPage;
	@Value("${currentNumberOfPagesForPagination}")
	private int currentNumberOfPagesForPagination;

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

	@GetMapping("/showAllLectures")
	public String showAllLectures(Model model, @RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "0") int currentBeginPagination, @RequestParam(defaultValue = "default") String sortedParam) {
		List<Lecture> lecturesForShow = new ArrayList<>();
		Paginator paginator = new Paginator(lectureService.getNumberOfItem(), currentPage, currentBeginPagination, itemsPerPage, currentNumberOfPagesForPagination);
		switch(sortedParam) {
			case ("Time") : lecturesForShow = lectureService.getAndSortByTime(itemsPerPage, paginator.getOffset()); break;
			case ("Course")  :  lecturesForShow = lectureService.getAndSortByCourse(itemsPerPage, paginator.getOffset()); break;
			case ("Classroom")  :  lecturesForShow = lectureService.getAndSortByClassroom(itemsPerPage, paginator.getOffset()); break;
			case ("Group")  :  lecturesForShow = lectureService.getAndSortByGroup(itemsPerPage, paginator.getOffset()); break;
			case ("Teacher")  :  lecturesForShow = lectureService.getAndSortByTeacher(itemsPerPage, paginator.getOffset()); break;
			case ("Id")  :  lecturesForShow = lectureService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
			default : lecturesForShow = lectureService.getAndSortById(itemsPerPage, paginator.getOffset()); break;
		}
		
		model.addAttribute("lecturesForShow", lecturesForShow);
		model.addAttribute("currentPageNumbers",paginator.getCurrentPageNumbers());
		model.addAttribute("sortedParam", sortedParam);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("currentBeginPagination", paginator.getCurrentBeginPagination());
		model.addAttribute("currentNumberOfPagesForPagination", currentNumberOfPagesForPagination);
		model.addAttribute("numberOfPages", paginator.getNumberOfPages());
		return "lectures-page";
	}

	@GetMapping("/showEntity")
	public String showEntity(@RequestParam("lectureId") int id, Model model) {
		Lecture lecture = lectureService.getById(id).get();
		DailySchedule dailySchedule = dailyScheduleService.getById(lecture.getDailyScheduleId()).get();
		List<Course> allCourses = courseService.getAll();
		List<Classroom> allClassrooms = classroomService.getAll();
		List<Group> allGroups = groupService.getAll();
		List<Teacher> allTeachers = teacherService.getAll();
		model.addAttribute("lecture", lecture);
		model.addAttribute("dailySchedule", dailySchedule);
		model.addAttribute("allCourses", allCourses);
		model.addAttribute("allClassrooms", allClassrooms);
		model.addAttribute("allGroups", allGroups);
		model.addAttribute("allTeachers", allTeachers);
		return "show-lecture";
	}
}
