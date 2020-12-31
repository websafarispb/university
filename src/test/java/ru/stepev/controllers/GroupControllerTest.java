package ru.stepev.controllers;

import static org.assertj.core.api.Assertions.assertThat;

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
import ru.stepev.controller.GroupController;
import ru.stepev.utils.Paginator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class GroupControllerTest {

	@Autowired
	private GroupController groupController;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void checkGroupControllerForNotnNull_whenCheckGroupController_thenItNotNull() {
		assertThat(groupController).isNotNull();
	}
	
	@Test
	public void givenPathShowEntityWithParamGroupId_whenGetPathShowEntityViewWithParamGroupId_thenGetViewShowGroupWithParamCorrectGroup() throws Exception {
		mvc.perform(get("/groups/1"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("group", groupExpected))
			.andExpect(view().name("show-group"));
	}
	
	@Test
	public void givenPathShowAllGroupsWithDefaltParam_whenGetPathShowAllGroupsWithDefaltParam_thenGetViewGroupsPageWithCorrectGroups() throws Exception {
		Paginator paginator = new Paginator(24, 1, "Name", 5);
		mvc.perform(get("/groups/"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("groups", expectedGroupsForDefaultPage))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("groups-page"));
	}
	
	@Test
	public void givenPathShowAllGroupsWithParams_whenGetPathShowAllGroupsWithWithParams_thenGetViewGroupsPageWithCorrectGroups() throws Exception {
		Paginator paginator = new Paginator(24, 1, "Name", 5);
		mvc.perform(get("/groups/?currentPage=1&sortBy=Name"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("groups", expectedSortedGroupsForShowByName))
		.andExpect(model().attribute("paginator", paginator))
		.andExpect(view().name("groups-page"));
	}
}
