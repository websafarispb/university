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
import ru.stepev.controller.TeacherController;
import ru.stepev.utils.Paginator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class TeacherControllerTest {
	
	@Autowired
	private TeacherController teacherController;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void checkTeacherControllerForNotnNull_whenCheckTeacherController_thenItNotNull() {
		assertThat(teacherController).isNotNull();
	}
	
	@Test
	public void givenPathShowEntityWithParamTeacherId_whenGetPathShowEntityViewWithParamTeacherId_thenGetViewShowTeacherWithParamCorrectTeacher() throws Exception {
		mvc.perform(get("/teachers/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("teacher", teacherForTest))
			.andExpect(view().name("show-teacher"));
	}
	
	@Test
	public void givenPathShowAllTeachersWithDefaltParam_whenGetPathShowAllTeachersWithDefaltParam_thenGetViewTeachersPageWithCorrectTeachers() throws Exception {
		Paginator paginator = new Paginator(11, 1, "Last_name", 5);
		mvc.perform(get("/teachers/"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("teachers", expectedAllTeachersSortedByLastName))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("teachers-page"));
	}
	
	@Test
	public void givenPathShowAllTeachersWithParams_whenGetPathShowAllTeachersWithWithParams_thenGetViewTeachersPageWithCorrectTeachers() throws Exception {
		Paginator paginator = new Paginator(11, 1, "Last_name", 5);
		mvc.perform(get("/teachers/?currentPage=1&sortBy=Last_name"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("teachers", expectedAllTeachersSortedByLastName))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("teachers-page"));
	}
}
