package ru.stepev.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ru.stepev.data.DataTest.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
import ru.stepev.controller.LectureController;
import ru.stepev.model.Course;
import ru.stepev.model.Lecture;
import ru.stepev.utils.Paginator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class LectureControllerTest {
	
	@Autowired
	private LectureController lectureController;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	
	@Test
	public void checkLectureControllerForNotnNull_whenCheckLectureController_thenItNotNull() {
		assertThat(lectureController).isNotNull();
	}
	
	@Test
	public void givenPathShowEntityWithParamLectureId_whenGetPathShowEntityViewWithParamLectured_thenGetViewShowLectureWithParamCorrectLecture() throws Exception {
		mvc.perform(get("/lectures/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("lecture", expectedAllLectures.get(0)))
			.andExpect(view().name("show-lecture"));
	}
	
	@Test
	public void givenPathShowAllLecturesWithDefaltParam_whenGetPathShowAllLecturesWithDefaltParam_thenGetViewLecturesPageWithCorrectLectures() throws Exception {
		Paginator paginator = new Paginator(33, 1, "Time", 5);
		mvc.perform(get("/lectures/"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("lectures", expectedLecturesSortedByTime))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("lectures-page"));
	}
	
	@Test
	public void givenPathShowAllLecturesWithParams_whenGetPathShowAllLecturesWithWithParams_thenGetViewLecturesPageWithCorrectLectures() throws Exception {
		Paginator paginator = new Paginator(33, 1, "Time", 5);
		mvc.perform(get("/lectures/?currentPage=1&sortBy=Time"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("lectures", expectedLecturesSortedByTime))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("lectures-page"));
	}

	@Test
	public void givenPathDeleteLectureWithCourseId_whenDeleteLectureById_thenDeleteLecture() throws Exception {
		mvc.perform(get("/lectures/delete/2"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/lectures"));
	}
	
	@Test
	public void givenPathUpdateLectureWithCourseId_whenGetPathUpdateWithLectureId_thenGetViewUpdateLecture() throws Exception {	
		mvc.perform(get("/lectures/update/7"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("lecture", expectedLecturesForGroup.get(2)))
		.andExpect(view().name("update-lecture"));
	}
	
	@Test
	public void givenPathAddLecture_whenGetPathAddLecture_thenGetViewAddLectureWithNewLecture() throws Exception {	
		mvc.perform(get("/lectures/add"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("lecture", new Lecture()))
		.andExpect(view().name("add-lecture"));
	}
	
	@Test
	public void givenPathSaveAndLectureModelAttribute_whenSaveLecture_thenUpdateLectureAndRedirect() throws Exception {
		Map<String, Object> flashAttributes = new HashMap<>();
		flashAttributes.put("lecture",  expectedLecturesForGroup.get(2));
		flashAttributes.put("date",  LocalDate.of(2020, 9, 8));
		mvc.perform(post("/lectures/save?courseId=2&classroomId=1&groupId=3&teacherId=3").flashAttrs(flashAttributes))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/lectures?date=2020-09-08"));
	}
	
	@Test
	public void givenPathCreateAndPostMethod_whenCreateNewLecture_thenLectureWasCreatedAndRedirect() throws Exception {
		Map<String, Object> flashAttributes = new HashMap<>();
		flashAttributes.put("lecture",  lectureForCreateClassroom);
		flashAttributes.put("date",  LocalDate.of(2040, 9, 8));
		mvc.perform(post("/lectures/create?courseId=2&classroomId=1&groupId=3&teacherId=3").flashAttrs(flashAttributes))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/lectures?date=2040-09-08"));
	}
	
	@Test
	public void givenPathCreateAndPostMethod_whenCreateLectureWichExist_thenLectureWasNotCreatedAndGetMessage() throws Exception {	
		Map<String, Object> flashAttributes = new HashMap<>();
		flashAttributes.put("lecture",  expectedLecturesForGroup.get(2));
		flashAttributes.put("date",  LocalDate.of(2040, 9, 8));
		mvc.perform(post("/lectures/create?courseId=2&classroomId=1&groupId=3&teacherId=3").flashAttrs(flashAttributes))
		.andExpect(status().isOk())
		.andExpect(model().attribute("message", "Lecture with ID 7 already exist"))
		.andExpect(view().name("error/general"));
	}
}
