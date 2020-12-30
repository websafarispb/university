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
import ru.stepev.controller.LectureController;
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

}
