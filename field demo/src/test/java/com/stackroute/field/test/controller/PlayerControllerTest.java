package com.stackroute.field.test.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import com.stackroute.field.controller.PlayerController;
import com.stackroute.field.model.Player;
import com.stackroute.field.model.Team;
import com.stackroute.field.model.User;
import com.stackroute.field.service.PlayerService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ApplicationContextConfig.class })
@WebAppConfiguration
public class PlayerControllerTest {

	private MockMvc mockMvc;

	private User user;
	@Mock
	private Player note;

	private Team category;

	
	@Mock
	private PlayerService noteService;
	@Autowired
	private MockHttpSession session;
	@InjectMocks
	private PlayerController noteController;

	private List<Player> allNotesByUserId = new ArrayList<Player>();

	@Before

	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
		// session = new MockHttpSession();
		// Creating User Object
		user = new User("Jhon123", "Jhon Simon", "974324567", "123456", new Date());
		category = new Team();

		// Setting session attribute

		session.setAttribute("loggedInUserId", user.getUserId());

		// Creating Category object

		category.setTeamId(1);
		category.setTeamName("Testing Spring");
		category.setTeamDescription("All about testing spring application");
		category.setTeamCreatedBy("Jhon123");
		category.setTeamCreationDate(new Date());


		

		// creating Note Object
		note = new Player(1, "Testing for Step-3", "Complete testing for step-3", "Active", new Date(), category,
				 user.getUserId());

		allNotesByUserId.add(note);

		note = new Player(2, "Testing for Step-4", "Complete testing for step-4", "Active", new Date(), category,
				 user.getUserId());
		allNotesByUserId.add(note);

		note = new Player(3, "Testing for Step-5", "Complete testing for step-5", "Active", new Date(), category,
				 user.getUserId());
		allNotesByUserId.add(note);

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test

	public void testCreateNoteSuccess() throws Exception {

		when(noteService.createPlayr(any())).thenReturn(true);
		System.out.println(noteService.createPlayr(note));

		mockMvc.perform(
				post("/player").contentType(MediaType.APPLICATION_JSON).content(asJsonString(note)))
				.andExpect(status().isCreated()).andDo(print());

	}

	@Test
	public void testCreateNoteFailure() throws Exception {

		when(noteService.createPlayr(any())).thenReturn(false);

		mockMvc.perform(
				post("/player").contentType(MediaType.APPLICATION_JSON).content(asJsonString(note)))
				.andExpect(status().isConflict()).andDo(print());

	}

	

	@Test
	public void testDeleteNoteSuccess() throws Exception {

		when(noteService.deletePlayer(note.getPlayerId())).thenReturn(true);
		mockMvc.perform(delete("/player/{id}", note.getPlayerId()).session(session)).andExpect(status().isOk())
				.andDo(print());

	}

	@Test
	public void testDeleteNoteFailure() throws Exception {

		when(noteService.deletePlayer(1)).thenReturn(false);
		mockMvc.perform(delete("/player/{id}", 1).session(session)).andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	public void testDeleteNoteFailureWithoutSession() throws Exception {

		when(noteService.deletePlayer(1)).thenReturn(false);
		mockMvc.perform(delete("/player/{id}", 1)).andExpect(status().isUnauthorized()).andDo(print());
	}

	@Test

	public void testUpdateNoteSuccess() throws Exception {
		note = new Player(1, "Testing for Step-3", "Complete testing for step-3", "Active", new Date(), category, user.getUserId());
		note.setLastName("updating note card");
		when(noteService.updatePlayer(any(), eq(1))).thenReturn(note);
		mockMvc.perform(put("/player/{id}", note.getPlayerId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(note)).session(session)).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void testUpdateNoteFailure() throws  Exception {
		note.setLastName("updating note card");
		when(noteService.updatePlayer(note, note.getPlayerId())).thenReturn(null);
		mockMvc.perform(put("/player/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(note))
				.session(session)).andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	public void testUpdateNoteFailureWithoutSession() throws  Exception {
		// when(noteService.getNoteById(5)).thenReturn(null);
		note.setLastName("updating note card");
		when(noteService.updatePlayer(note, note.getPlayerId())).thenReturn(note);
		mockMvc.perform(put("/player/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(note)))
				.andExpect(status().isUnauthorized()).andDo(print());
	}

	@Test
	public void testGetAllNotesByUserIdSuccess() throws Exception {

		when(noteService.getAllPlayersByUserId("Jhon123")).thenReturn(allNotesByUserId);
		mockMvc.perform(get("/player").contentType(MediaType.APPLICATION_JSON).session(session))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3))).andDo(print());

	}

	@Test
	public void testGetAllNotesByUserIdFailureWithoutSession() throws Exception {

		when(noteService.getAllPlayersByUserId("Jhon123")).thenReturn(allNotesByUserId);
		mockMvc.perform(get("/player").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
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
