package com.stackroute.field.test.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.field.config.ApplicationContextConfig;
import com.stackroute.field.controller.TeamController;
import com.stackroute.field.model.Team;
import com.stackroute.field.model.Player;
import com.stackroute.field.model.User;
import com.stackroute.field.service.TeamService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ApplicationContextConfig.class })
@WebAppConfiguration
public class TeamControllerTest {

	
	private MockMvc mockMvc;
	@Mock
	private User user;
	@Mock
	private Player player;
	@Mock
	private Team team;
	@Autowired
	private MockHttpSession session;
	@Mock
	TeamService teamService;
	@InjectMocks
	TeamController teamController;
	private List<Team> teams = new ArrayList<Team>();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();

		// Creating User object
		user = new User("Jhon123", "Jhon Simon", "974324567", "123456", new Date());

		// creating Session object and setting session
		session.setAttribute("loggedInUserId", user.getUserId());

		// Creating Category Object
		team = new Team(1, "Testing", "All about testing spring application", new Date(), null, null);

	}

	@Test

	public void testCreateCategorySuccess() throws Exception {

		when(teamService.createTeam(any())).thenReturn(true);
		mockMvc.perform(post("/team").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.session(session).content(asJsonString(team))).andExpect(status().isCreated()).andDo(print());
		verify(teamService, times(1)).createTeam(Mockito.any(Team.class));
		verifyNoMoreInteractions(teamService);

	}

	@Test
	public void testCreateCategoryFailure() throws Exception {

		when(teamService.createTeam(team)).thenReturn(false);
		mockMvc.perform(post("/team").contentType(MediaType.APPLICATION_JSON).content(asJsonString(team))
				.session(session)).andExpect(status().isConflict()).andDo(print());

	}

	@Test
	public void testCreateCategoryWithoutSessionFailure() throws Exception {

		when(teamService.createTeam(team)).thenReturn(false);
		mockMvc.perform(post("/team").contentType(MediaType.APPLICATION_JSON).content(asJsonString(team)))
				.andExpect(status().isUnauthorized()).andDo(print());

	}

	@Test
	public void testDeleteCategorySuccess() throws Exception {

		when(teamService.deleteTeam(team.getTeamId())).thenReturn(true);
		mockMvc.perform(delete("/team/{id}", team.getTeamId()).contentType(MediaType.APPLICATION_JSON)
				.session(session)).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void testDeleteCategoryFailure() throws Exception {

		when(teamService.deleteTeam(team.getTeamId())).thenReturn(false);
		mockMvc.perform(delete("/team/{id}", team.getTeamId()).contentType(MediaType.APPLICATION_JSON)
				.session(session)).andExpect(status().isNotFound()).andDo(print());

	}

	@Test
	public void testDeleteCategoryWithoutSessionFailure() throws Exception {

		when(teamService.deleteTeam(team.getTeamId())).thenReturn(false);
		mockMvc.perform(delete("/team/{id}", team.getTeamId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized()).andDo(print());

	}

	@Test
	
	public void testUpdateCategorySuccess() throws Exception {
		when(teamService.updateTeam(any(), eq(team.getTeamId()))).thenReturn(team);
		mockMvc.perform(put("/team/{id}", team.getTeamId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(team)).session(session)).andExpect(status().isOk());
	}

	@Test
	public void testUpdateCategoryFailure() throws Exception {
		when(teamService.updateTeam(any(), eq(team.getTeamId()))).thenReturn(null);
		mockMvc.perform(put("/team/{id}", 2).contentType(MediaType.APPLICATION_JSON).content(asJsonString(team))
				.session(session)).andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateCategoryWithoutSessionFailure() throws Exception {
		when(teamService.updateTeam(any(), eq(team.getTeamId()))).thenReturn(team);
		mockMvc.perform(put("/team/{id}", team.getTeamId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(team))).andExpect(status().isUnauthorized());
	}

	@Test
	public void testGetAllCategoriesByUserIdSuccess() throws Exception {

		teams.add(team);
		team = new Team(2, "Testing-2", "All about testing spring application", new Date(), null, null);
		teams.add(team);
		team = new Team(3, "Testing-3", "All about testing spring application", new Date(), null, null);
		teams.add(team);

		when(teamService.getAllTeamByUserId("Jhon123")).thenReturn(teams);
		mockMvc.perform(get("/team").contentType(MediaType.APPLICATION_JSON).session(session))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3))).andDo(print());
	}

	@Test
	public void testGetAllCategoriesByUserIdWithoutSessionFailure() throws Exception {

		teams.add(team);
		team = new Team(2, "Testing-2", "All about testing spring application", new Date(), null, null);
		teams.add(team);
		team = new Team(3, "Testing-3", "All about testing spring application", new Date(), null, null);
		teams.add(team);

		when(teamService.getAllTeamByUserId("Jhon123")).thenReturn(teams);
		mockMvc.perform(get("/team").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
				.andDo(print());
	}

	public static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
