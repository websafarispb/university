package ru.stepev.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
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
		Paginator paginator = new Paginator(17, 1, "Name", 5);
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
	
	@Test
	public void givenPathDeleteCourseWithCourseId_whenDeleteCourseById_thenDeleteCourse() throws Exception {
		mvc.perform(get("/courses/delete/2"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/courses"));
	}
	
	@Test
	public void givenPathUpdateCourseWithCourseId_whenGetPathUpdateWithCourseId_thenGetViewUpdateCourse() throws Exception {	
		mvc.perform(get("/courses/update/3"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("course", notExistedCourses.get(2)))
		.andExpect(view().name("update-course"));
	}
	
	@Test
	public void givenPathAddCourse_whenGetPathAddCourse_thenGetViewAddCourseWithNewCourse() throws Exception {	
		mvc.perform(get("/courses/add"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("course", new Course()))
		.andExpect(view().name("add-course"));
	}
	
	@Test
	public void givenPathSaveAndCourseModelAttribute_whenSaveCourse_thenUpdateCourseAndRedirect() throws Exception {	
		mvc.perform(post("/courses/save").flashAttr("course", expectedCourse))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/courses"));
	}
	
	@Test
	public void givenPathCreateAndPostMethod_whenCreateNewCourse_thenCourseWasCreatedAndRedirect() throws Exception {	
		mvc.perform(post("/courses/create").flashAttr("course", new Course("New course","All about new course")))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/courses"));
	}
	
	@Test
	public void givenPathCreateAndPostMethod_whenCreateCourseWichExist_thenCourseWasNotCreatedAndGetMessage() throws Exception {	
		mvc.perform(post("/courses/create").flashAttr("course", new Course("Mathematics","All about new course")))
		.andExpect(status().isOk())
		.andExpect(model().attribute("message", "Course with name Mathematics already exist"))
		.andExpect(view().name("courses-page"));
	}
}
