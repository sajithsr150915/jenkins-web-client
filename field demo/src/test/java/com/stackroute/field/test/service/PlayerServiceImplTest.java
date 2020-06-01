package com.stackroute.field.test.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.field.dao.TeamDAO;
import com.stackroute.field.dao.PlayerDAO;
import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.exception.PlayerNotFoundException;
import com.stackroute.field.model.Team;
import com.stackroute.field.model.Player;
import com.stackroute.field.service.PlayerServiceImpl;

public class PlayerServiceImplTest {

	@Mock
	private PlayerDAO playerDao;
	@Mock
	private TeamDAO teamDao;

	@InjectMocks
	PlayerServiceImpl playerServiceImpl;

	private Player player;
	private Team team;
	List<Player> players = null;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		team = new Team(1, "Java", "Testing in java", new Date(), "Jhon123", null);
		player = new Player(1, "Testing", "Testing Service layer", "Active", new Date(), team,  "Jhon123");
		players = new ArrayList<Player>();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateNoteSuccess() throws  TeamNotFoundException {
		when(playerDao.createPlayer(player)).thenReturn(true);
		boolean status = playerServiceImpl.createPlayr(player);
		assertEquals(true, status);
		verify(playerDao, times(1)).createPlayer(player);
		verify(teamDao, times(1)).getTeamById(team.getTeamId());

	}

	@Test
	public void testCreateNoteSuccessWithoutCategoryAndReminder()
			throws TeamNotFoundException {
		player = new Player(1, "Testing", "Testing Service layer", "Active", new Date(), null,  "Jhon123");
		when(playerDao.createPlayer(player)).thenReturn(true);
		boolean status = playerServiceImpl.createPlayr(player);
		assertEquals(true, status);
		verify(playerDao, times(1)).createPlayer(player);

	}

	@Test
	public void testCreateNoteSuccessWithoutCategory() throws  TeamNotFoundException {
		player = new Player(1, "Testing", "Testing Service layer", "Active", new Date(), null, "Jhon123");
		when(playerDao.createPlayer(player)).thenReturn(true);
		boolean status = playerServiceImpl.createPlayr(player);
		assertEquals(true, status);
		verify(playerDao, times(1)).createPlayer(player);

	}

	@Test
	public void testCreateNoteSuccessWithoutReminder() throws  TeamNotFoundException {
		player = new Player(1, "Testing", "Testing Service layer", "Active", new Date(), team,  "Jhon123");
		when(playerDao.createPlayer(player)).thenReturn(true);
		boolean status = playerServiceImpl.createPlayr(player);
		assertEquals(true, status);
		verify(playerDao, times(1)).createPlayer(player);
		verify(teamDao, times(1)).getTeamById(team.getTeamId());

	}

	@Test()
	public void testCreateNoteFailure() throws  TeamNotFoundException {
		when(playerDao.createPlayer(player)).thenReturn(false);
		boolean status = playerServiceImpl.createPlayr(player);
		assertEquals(false, status);
		verify(playerDao, times(1)).createPlayer(player);
		verify(teamDao, times(1)).getTeamById(team.getTeamId());

	}


	@Test
	public void testDeleteNoteSuccess() throws PlayerNotFoundException {
		when(playerDao.deletePlayer(1)).thenReturn(true);
		boolean status = playerServiceImpl.deletePlayer(1);
		assertEquals(true, status);
		verify(playerDao, times(1)).deletePlayer(1);
	}

	@Test
	public void testDeleteNoteFailure() throws PlayerNotFoundException {
		when(playerDao.deletePlayer(1)).thenReturn(false);
		boolean status = playerServiceImpl.deletePlayer(1);
		assertEquals(false, status);
		verify(playerDao, times(1)).deletePlayer(1);
	}

	@Test
	public void testGetAllNotesByUserIdSucess() {

		players.add(player);
		player = new Player(2, "Testing-2", "Testing Service layer", "Active", new Date(), team,  "Jhon123");
		players.add(player);
		player = new Player(3, "Testing-3", "Testing Service layer", "Active", new Date(), team,  "Jhon123");
		players.add(player);
		when(playerDao.getAllPlayersByUserId("Jhon123")).thenReturn(players);
		List<Player> allNotes = playerServiceImpl.getAllPlayersByUserId("Jhon123");
		assertEquals(3, allNotes.size());
		assertEquals(players, allNotes);
		assertEquals("Testing-3", allNotes.get(2).getFirstName());
		verify(playerDao, times(1)).getAllPlayersByUserId("Jhon123");

	}

	@Test
	public void testGetNoteByIdSuccess() throws PlayerNotFoundException {
		when(playerDao.getPlayerById(player.getPlayerId())).thenReturn(player);
		Player fetchedNote = playerServiceImpl.getPlayerById(player.getPlayerId());
		assertEquals("Testing", fetchedNote.getFirstName());
		assertEquals(player, fetchedNote);
		verify(playerDao, times(1)).getPlayerById(player.getPlayerId());
	}

	@Test(expected = PlayerNotFoundException.class)
	public void testGetNoteByIdFailure() throws PlayerNotFoundException {
		when(playerDao.getPlayerById(2)).thenThrow(PlayerNotFoundException.class);
		@SuppressWarnings("unused")
		Player fetchedNote = playerServiceImpl.getPlayerById(2);

	}

	@Test
	public void testUpdateNoteSuccess()
			throws  PlayerNotFoundException, TeamNotFoundException {

		player.setLastName("Testing updateNote()");
		when(teamDao.getTeamById(1)).thenReturn(team);
		when(playerDao.getPlayerById(1)).thenReturn(player);
		when(playerDao.UpdatePlayer(player)).thenReturn(true);
		Player updatedNote = playerServiceImpl.updatePlayer(player, 1);
		assertEquals("Testing updateNote()", updatedNote.getLastName());
		verify(teamDao, times(1)).getTeamById(team.getTeamId());
		verify(playerDao, times(1)).getPlayerById(1);
		verify(playerDao, times(1)).UpdatePlayer(player);
	}

	

	@Test
	public void testUpdateNoteWithoutCategorySuccess()
			throws PlayerNotFoundException, TeamNotFoundException {

		player.setLastName("Testing updateNote()");
		player.setTeam(null);
		when(teamDao.getTeamById(1)).thenReturn(null);
		when(playerDao.getPlayerById(1)).thenReturn(player);
		when(playerDao.UpdatePlayer(player)).thenReturn(true);
		Player updatedNote = playerServiceImpl.updatePlayer(player, 1);
		assertEquals("Testing updateNote()", updatedNote.getLastName());
		verify(playerDao, times(1)).getPlayerById(1);
		verify(playerDao, times(1)).UpdatePlayer(player);
	}

 

	@Test(expected = PlayerNotFoundException.class)
	public void testUpdateNoteFailure()
			throws  PlayerNotFoundException, TeamNotFoundException {

		player.setLastName("Testing updateNote()");
		when(teamDao.getTeamById(1)).thenReturn(team);
		when(playerDao.getPlayerById(1)).thenThrow(PlayerNotFoundException.class);
		when(playerDao.UpdatePlayer(player)).thenReturn(true);
		@SuppressWarnings("unused")
		Player updatedNote = playerServiceImpl.updatePlayer(player, 1);

	}

}
