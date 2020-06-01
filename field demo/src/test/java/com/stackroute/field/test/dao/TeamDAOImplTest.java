package com.stackroute.field.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.stackroute.field.config.ApplicationContextConfig;
import com.stackroute.field.dao.TeamDAO;
import com.stackroute.field.dao.TeamDAOImpl;
import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.model.Team;

@RunWith(SpringRunner.class)
@Transactional
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationContextConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class TeamDAOImplTest {

	@Autowired
	private SessionFactory sessionFactory;
	private TeamDAO teamDAO;
	private Team team;

	@Before
	public void setUp() throws Exception {
		teamDAO = new TeamDAOImpl(sessionFactory);
		team = new Team(1, "Testing", "All about testing spring application", null, "Jhon123", null);
	}

	@After
	public void tearDown() throws Exception {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE from Team");
		query.executeUpdate();
	}

	@Test
	@Rollback(true)
	public void testCreateCategorySuccess() throws TeamNotFoundException {
		teamDAO.createTeam(team);
		Team savedCategory = teamDAO.getTeamById(team.getTeamId());
		assertEquals(team, savedCategory);

	}

	@Test(expected= TeamNotFoundException.class)
	@Rollback(true)
	public void testCreateCategoryFailure() throws TeamNotFoundException {
		teamDAO.createTeam(team);
		Team savedCategory = teamDAO.getTeamById(2);
		assertNotEquals(team, savedCategory);

	}

	@Test
	public void testDeleteCategorySuccess() {
		teamDAO.createTeam(team);
		boolean status = teamDAO.deleteTeam(team.getTeamId());
		assertEquals(true, status);
	}

	@Test(expected= TeamNotFoundException.class)
	public void testDeleteCategoryFailure() throws TeamNotFoundException {
		teamDAO.createTeam(team);
		@SuppressWarnings("unused")
		Team savedCategory = teamDAO.getTeamById(2);
		boolean status = teamDAO.deleteTeam(2);
		assertEquals(false, status);
	}

	@Test
	public void testUpdateCategory() throws TeamNotFoundException {
		teamDAO.createTeam(team);
		Team savedCategory = teamDAO.getTeamById(team.getTeamId());
		savedCategory.setTeamDescription("Testing DAO layer in spring MVC");
		boolean status = teamDAO.updateTeam(savedCategory);
		assertEquals(true, status);
		savedCategory = teamDAO.getTeamById(savedCategory.getTeamId());
		assertEquals("Testing DAO layer in spring MVC", savedCategory.getTeamDescription());
	}

	@Test
	public void testGetCategoryById() throws TeamNotFoundException {
		teamDAO.createTeam(team);
		Team savedCategory = teamDAO.getTeamById(team.getTeamId());
		assertEquals(team, savedCategory);
	}

	@Test
	public void testGetAllCategoryByUserId() {
		teamDAO.createTeam(team);
		team = new Team(2, "Testing-2", "All about testing spring application", null, "Jhon123", null);
		teamDAO.createTeam(team);
		team = new Team(3, "Testing-3", "All about testing spring application", null, "Jhon123", null);
		teamDAO.createTeam(team);

		List<Team> allCategories = teamDAO.getAllTeamByUserId("Jhon123");
		assertEquals(3, allCategories.size());
	}

}
