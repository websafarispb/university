package ru.stepev.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ru.stepev.data.DataTest.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ru.stepev.config.TestConfig;
import ru.stepev.controller.DailyScheduleController;
import ru.stepev.utils.Paginator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class DailyScheduleControllerTest {

	@Autowired
	private DailyScheduleController dailyScheduleController;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void checkDailyScheduleControllerForNotnNull_whenCheckDailyScheduleController_thenItNotNull() {
		assertThat(dailyScheduleController).isNotNull();
	}

	@Test
	public void givenPathShowEntityWithParamDailyScheduleId_whenGetPathShowEntityViewWithParamDailyScheduleId_thenGetViewShowDailyScheduleWithParamCorrectDailySchedule()
			throws Exception {
		mvc.perform(get("/dailySchedules/1")).andExpect(status().isOk())
				.andExpect(model().attribute("dailySchedule", expectedDailySchedule))
				.andExpect(view().name("show-dailyschedule"));
	}

	@Test
	public void givenPathShowAllDailySchedulesWithDefaltParam_whenGetPathShowAllDailySchedulesWithDefaltParam_thenGetViewDailySchedulesPageWithCorrectDailySchedules()
			throws Exception {
		Paginator paginator = new Paginator(20, 1, "Date", 5);
		mvc.perform(get("/dailySchedules/")).andExpect(status().isOk())
				.andExpect(model().attribute("dailySchedules", expectedAllSchedules.subList(0, 5)))
				.andExpect(model().attribute("paginator", paginator)).andExpect(view().name("schedule-page"));
	}

	@Test
	public void givenPathShowAllDailySchedulesWithSortByParam_whenGetPathShowAllDailySchedulesWithSortByParam_thenGetViewDailySchedulesPageWithCorrectDailySchedules()
			throws Exception {
		Paginator paginator = new Paginator(20, 1, "Date", 5);
		mvc.perform(get("/dailySchedules/?currentPage=1&sortBy=Date")).andExpect(status().isOk())
				.andExpect(model().attribute("dailySchedules", expectedAllSchedules.subList(0, 5)))
				.andExpect(model().attribute("paginator", paginator)).andExpect(view().name("schedule-page"));
	}

	@Test
	public void givenTeachers_whentShowScheduleForTeacherForm_thenShowScheduleForTeacherForm() throws Exception {
		mvc.perform(get("/dailySchedules/showScheduleForTeacherForm")).andExpect(status().isOk())
				.andExpect(model().attribute("dailySchedules", expectedAllSchedules))
				.andExpect(model().attribute("allTeachers", expectedAllNotApdatedTeachers))
				.andExpect(view().name("scheduleForTeacherForm"));
	}

	@Test
	public void givenStudents_whenShowScheduleForStudentForm_thenShowScheduleForStudentForm() throws Exception {
		mvc.perform(get("/dailySchedules/showScheduleForStudentForm")).andExpect(status().isOk())
				.andExpect(model().attribute("dailySchedules", expectedAllSchedules))
				.andExpect(model().attribute("allStudents", expectedNotUpdatedStudents))
				.andExpect(view().name("scheduleForStudentForm"));
	}

	@Test
	public void givenTeacher_whenShowScheduleForTeacher_thenShowScheduleForTeacher() throws Exception {
		Paginator paginator = new Paginator(1, 1, "Date", 5);
		mvc.perform(get("/dailySchedules/showScheduleForTeacher?teacherId=1&firstDate=2020-09-07&lastDate=2020-09-09")).andExpect(status().isOk())
				.andExpect(model().attribute("dailySchedulesForShow", expectedDailyScheduleForTeacher))
				.andExpect(model().attribute("paginator", paginator)).andExpect(view().name("schedule-page"));
	}

	@Test
	public void givenStudent_whenShowScheduleForStudent_thenShowScheduleForStudent() throws Exception {
		Paginator paginator = new Paginator(2, 1, "Date", 5);
		mvc.perform(get("/dailySchedules/showScheduleForStudent?studentId=3&firstDate=2020-09-07&lastDate=2020-09-09")).andExpect(status().isOk())
				.andExpect(model().attribute("dailySchedulesForShow", expectedDailyScheduleForStudent))
				.andExpect(model().attribute("paginator", paginator)).andExpect(view().name("schedule-page"));
	}
}
