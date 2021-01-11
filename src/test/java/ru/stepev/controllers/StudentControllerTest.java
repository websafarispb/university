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
import ru.stepev.controller.StudentController;
import ru.stepev.model.Course;
import ru.stepev.model.Student;
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

	@Test
	public void givenPathDeleteStudentWithStudentId_whenDeleteStudentById_thenDeleteStudent() throws Exception {
		mvc.perform(get("/students/delete/2"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/students"));
	}
	
	@Test
	public void givenPathUpdateStudentWithStudentId_whenGetPathUpdateWithStudentId_thenGetViewUpdateStudent() throws Exception {	
		mvc.perform(get("/students/update/3"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("student", expectedNotUpdatedStudents.get(2)))
		.andExpect(view().name("update-student"));
	}
	
	@Test
	public void givenPathAddStudent_whenGetPathAddStudent_thenGetViewAddStudentWithNewStudent() throws Exception {
		Student student = new Student();
		student.setGender("MALE");
		mvc.perform(get("/students/add"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("student",  student))
		.andExpect(view().name("add-student"));
	}
	
	@Test
	public void givenPathSaveAndStudentModelAttribute_whenSaveStudent_thenUpdateStudentAndRedirect() throws Exception {	
		mvc.perform(post("/students/save?groupId=1").flashAttr("student", expectedNotUpdatedStudents.get(2)))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/students"));
	}
	
	@Test
	public void givenPathCreateAndPostMethod_whenCreateNewStudent_thenStudentWasCreatedAndRedirect() throws Exception {	
		mvc.perform(post("/students/create?groupId=1").flashAttr("student", studentForCreate))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/students"));
	}
	
	@Test
	public void givenPathCreateAndPostMethod_whenCreateStudentWichExist_thenStudentWasNotCreatedAndGetMessage() throws Exception {	
		mvc.perform(post("/students/create?groupId=1").flashAttr("student", expectedStudents.get(0)))
		.andExpect(status().isOk())
		.andExpect(model().attribute("message", "Student with ID 1 already exist"))
		.andExpect(view().name("students-page"));
	}
}
