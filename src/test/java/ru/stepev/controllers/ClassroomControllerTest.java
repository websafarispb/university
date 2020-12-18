package ru.stepev.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import static ru.stepev.data.DataTest.expectedClassrooms;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import static ru.stepev.data.DataTest.*;

import ru.stepev.config.TestConfig;
import ru.stepev.controller.ClassroomController;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class ClassroomControllerTest {

	@Autowired
	private ClassroomController classroomController;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void checkClassroomControllerForNotnNull_whenCheckClassroomController_thenItNotNull() {
		assertThat(classroomController).isNotNull();
	}
	
	@Test
	public void givenPathShowEntityWithParamClassroomID_whenGetPathShowEntityViewWithParamClassroomId_thenGetViewShowClassroomWithParamCorrectClassroom() throws Exception {
		mvc.perform(get("/classrooms/showEntity/?classroomId=1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("classroom", expectedClassroom))
			.andExpect(view().name("show-classroom"));
	}
	
	@Test
	public void givenPathShowAllClassroomsWithDefaltParam_whenGetPathShowAllClassroomsWithDefaltParam_thenGetViewClassroomsPageWithCorrectClassrooms() throws Exception {
		mvc.perform(get("/classrooms/showAllClassrooms/"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("classroomsForShow", expectedClassrooms))
		.andExpect(model().attribute("currentPageNumbers", defaultCurrentPageNumbers))
		.andExpect(model().attribute("sortedParam", "default"))
		.andExpect(model().attribute("currentPage", 1))
		.andExpect(model().attribute("currentBeginPagination", 0))
		.andExpect(model().attribute("currentNumberOfPagesForPagination", 3))
		.andExpect(model().attribute("numberOfPages", 4))
		.andExpect(view().name("classrooms-page"));
	}
	
	@Test
	public void givenPathShowAllClassroomsWithParams_whenGetPathShowAllClassroomsWithWithParams_thenGetViewClassroomsPageWithCorrectClassrooms() throws Exception {
		mvc.perform(get("/classrooms/showAllClassrooms/?currentBeginPagination=3&currentPage=4&sortedParam=Capacity"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("classroomsForShow", expectedSortedClassroomsByCapacity))
		.andExpect(model().attribute("currentPageNumbers", currentPageNumbers))
		.andExpect(model().attribute("sortedParam", "Capacity"))
		.andExpect(model().attribute("currentPage", 1))
		.andExpect(model().attribute("currentBeginPagination", 3))
		.andExpect(model().attribute("currentNumberOfPagesForPagination", 3))
		.andExpect(model().attribute("numberOfPages", 4))
		.andExpect(view().name("classrooms-page"));
	}
}
