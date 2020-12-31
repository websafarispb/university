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
import ru.stepev.utils.Paginator;

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
		mvc.perform(get("/classrooms/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("classroom", expectedClassroom))
			.andExpect(view().name("show-classroom"));
	}
	
	@Test
	public void givenPathShowAllClassroomsWithDefaltParam_whenGetPathShowAllClassroomsWithDefaltParam_thenGetViewClassroomsPageWithCorrectClassrooms() throws Exception {
		Paginator paginator = new Paginator(20, 1, "Address", 5);
		mvc.perform(get("/classrooms/"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("classrooms", expectedClassroomsSortedByAddress))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("classrooms-page"));
	}
	
	@Test
	public void givenPathShowAllClassroomsWithParams_whenGetPathShowAllClassroomsWithWithParams_thenGetViewClassroomsPageWithCorrectClassrooms() throws Exception {
		Paginator paginator = new Paginator(20, 2, "Capacity", 5);
		mvc.perform(get("/classrooms/?currentPage=2&sortBy=Capacity"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("classrooms", expectedSortedClassroomsByCapacity))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("classrooms-page"));
	}
}
