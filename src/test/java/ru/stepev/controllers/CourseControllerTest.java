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
import ru.stepev.controller.CourseController;
import ru.stepev.utils.Paginator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class CourseControllerTest {

	@Autowired
	private CourseController courseController;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void checkCourseControllerForNotnNull_whenCheckCourseController_thenItNotNull() {
		assertThat(courseController).isNotNull();
	}
	
	@Test
	public void givenPathShowEntityWithParamCourseId_whenGetPathShowEntityViewWithParamCourseId_thenGetViewShowCourseWithParamCorrectCourse() throws Exception {
		mvc.perform(get("/courses/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("course", expectedCourse))
			.andExpect(view().name("show-course"));
	}
	
	@Test
	public void givenPathShowAllCoursesWithDefaltParam_whenGetPathShowAllCoursesWithDefaltParam_thenGetViewCoursesPageWithCorrectCourses() throws Exception {
		Paginator paginator = new Paginator(16, 1, "Name", 5);
		mvc.perform(get("/courses/"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("courses", expectedSortedCoursesByName))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("courses-page"));
	}
	
	@Test
	public void givenPathShowAllCoursesWithParams_whenGetPathShowAllCoursesWithWithParams_thenGetViewCoursesPageWithCorrectCourses() throws Exception {
		Paginator paginator = new Paginator(16, 1, "Name", 5);
		mvc.perform(get("/courses/?currentPage=1&sortBy=Name"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("courses", expectedSortedCoursesByName))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("courses-page"));
	}
}
