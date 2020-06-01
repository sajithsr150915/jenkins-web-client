package com.stackroute.field.test.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.field.dao.TeamDAO;
import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.model.Team;
import com.stackroute.field.service.TeamServiceImpl;

public class TeamServiceImplTest {

	@Mock
	TeamDAO teamDAO;
	@InjectMocks
	TeamServiceImpl categoryServiceImpl;
	private Team team = null;
	private List<Team> allTeam = null;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		team = new Team(1, "Testing", "All about testing spring application", new Date(), "Jhon123", null);
		allTeam = new ArrayList<Team>();
	}

	@Test
	public void testCreateCategorySuccess() {

		when(teamDAO.createTeam(team)).thenReturn(true);
		boolean status = categoryServiceImpl.createTeam(team);
		assertEquals(true, status);
		verify(teamDAO, times(1)).createTeam(team);
	}

	@Test
	public void testCreateCategoryFailure() {
		when(teamDAO.createTeam(team)).thenReturn(false);
		boolean status = categoryServiceImpl.createTeam(team);
		assertEquals(false, status);
		verify(teamDAO, times(1)).createTeam(team);
	}

	@Test
	public void testDeleteCategorySuccess() {
		when(teamDAO.deleteTeam(1)).thenReturn(true);
		boolean status = categoryServiceImpl.deleteTeam(1);
		assertEquals(true, status);
		verify(teamDAO, times(1)).deleteTeam(1);
	}

	@Test
	public void testDeleteCategoryFailure() {
		when(teamDAO.deleteTeam(1)).thenReturn(false);
		boolean status = categoryServiceImpl.deleteTeam(1);
		assertEquals(false, status);
		verify(teamDAO, times(1)).deleteTeam(1);
	}

	@Test
	public void testUpdateCategorySuccess() throws TeamNotFoundException {
		when(teamDAO.getTeamById(1)).thenReturn(team);
		team.setTeamName("Testing Spring app");
		when(teamDAO.updateTeam(team)).thenReturn(true);
		Team updatedCategory = categoryServiceImpl.updateTeam(team, 1);
		assertEquals("Testing Spring app", updatedCategory.getTeamName());
		verify(teamDAO, times(1)).getTeamById(1);
		verify(teamDAO, times(1)).updateTeam(team);
	}

	@Test(expected = TeamNotFoundException.class)
	public void testUpdateCategoryFailure() throws TeamNotFoundException {
		when(teamDAO.getTeamById(1)).thenReturn(null);
		when(teamDAO.updateTeam(team)).thenReturn(true);
		@SuppressWarnings("unused")
		Team updatedCategory = categoryServiceImpl.updateTeam(team, 1);

	}

	@Test
	public void testGetCategoryByIdSuccess() throws TeamNotFoundException {

		when(teamDAO.getTeamById(1)).thenReturn(team);
		Team fetechedCategory = categoryServiceImpl.getTeamById(1);
		assertEquals(team, fetechedCategory);
		verify(teamDAO, times(1)).getTeamById(1);
	}

	@Test(expected = TeamNotFoundException.class)
	public void testGetCategoryByIdFailure() throws TeamNotFoundException {

		when(teamDAO.getTeamById(2)).thenReturn(null);
		@SuppressWarnings("unused")
		Team fetechedCategory = categoryServiceImpl.getTeamById(2);

	}

	@Test
	public void testGetAllCategoryByUserIdSuccess() {

		allTeam.add(team);
		team = new Team(2, "Testing-2", "All about testing spring application", new Date(), "Jhon123", null);
		allTeam.add(team);
		team = new Team(3, "Testing-3", "All about testing spring application", new Date(), "Jhon123", null);
		allTeam.add(team);

		when(teamDAO.getAllTeamByUserId("Jhon123")).thenReturn(allTeam);
		List<Team> categories = categoryServiceImpl.getAllTeamByUserId("Jhon123");
		assertEquals(3, categories.size());
		verify(teamDAO, times(1)).getAllTeamByUserId("Jhon123");
	}

}
