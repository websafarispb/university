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
import ru.stepev.controller.StudentController;
import ru.stepev.utils.Paginator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class StudentControllerTest {
	
	@Autowired
	private StudentController studentController;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void checkStudentControllerForNotnNull_whenCheckStudentController_thenItNotNull() {
		assertThat(studentController).isNotNull();
	}
	
	@Test
	public void givenPathShowEntityWithParamStudentId_whenGetPathShowEntityViewWithParamStudentId_thenGetViewShowStudentWithParamCorrectStudent() throws Exception {
		mvc.perform(get("/students/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("student", expectedStudents.get(0)))
			.andExpect(view().name("show-student"));
	}
	
	@Test
	public void givenPathShowAllStudentsWithDefaltParam_whenGetPathShowAllStudentsWithDefaltParam_thenGetViewStudentsPageWithCorrectStudents() throws Exception {
		Paginator paginator = new Paginator(28, 1, "Last_name", 5);
		mvc.perform(get("/students/"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("students", expectedAllStudentsSortedByLastName))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("students-page"));
	}
	
	@Test
	public void givenPathShowAllStudentsWithParams_whenGetPathShowAllStudentsWithWithParams_thenGetViewStudentsPageWithCorrectStudents() throws Exception {
		Paginator paginator = new Paginator(28, 1, "Last_name", 5);
		mvc.perform(get("/students/?currentPage=1&sortBy=Last_name"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("students", expectedAllStudentsSortedByLastName))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("students-page"));
	}
}
