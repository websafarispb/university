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
		mvc.perform(get("/courses/showEntity/?courseId=1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("course", expectedCourse))
			.andExpect(view().name("show-course"));
	}
	
	@Test
	public void givenPathShowAllCoursesWithDefaltParam_whenGetPathShowAllCoursesWithDefaltParam_thenGetViewCoursesPageWithCorrectCourses() throws Exception {
		mvc.perform(get("/courses/showAllCourses/"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("coursesForShow", expectedCourses))
		.andExpect(model().attribute("currentPageNumbers", defaultCurrentPageNumbers))
		.andExpect(model().attribute("sortedParam", "default"))
		.andExpect(model().attribute("currentPage", 1))
		.andExpect(model().attribute("diapason", 0))
		.andExpect(model().attribute("sizeOfDiapason", 3))
		.andExpect(model().attribute("numberOfPages", 4))
		.andExpect(view().name("courses-page"));
	}
	
	@Test
	public void givenPathShowAllCoursesWithParams_whenGetPathShowAllCoursesWithWithParams_thenGetViewCoursesPageWithCorrectCourses() throws Exception {
		mvc.perform(get("/courses/showAllCourses/?diapason=3&currentPage=1&sortedParam=Name"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("coursesForShow", expectedSortedCoursesByName))
		.andExpect(model().attribute("currentPageNumbers", currentPageNumbers))
		.andExpect(model().attribute("sortedParam", "Name"))
		.andExpect(model().attribute("currentPage", 1))
		.andExpect(model().attribute("diapason", 3))
		.andExpect(model().attribute("sizeOfDiapason", 3))
		.andExpect(model().attribute("numberOfPages", 4))
		.andExpect(view().name("courses-page"));
	}
}
