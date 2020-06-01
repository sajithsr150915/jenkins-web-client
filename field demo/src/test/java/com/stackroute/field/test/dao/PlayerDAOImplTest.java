package com.stackroute.field.test.dao;

import static org.junit.Assert.*;
import java.util.Date;
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
import com.stackroute.field.dao.PlayerDAO;
import com.stackroute.field.dao.PlayerDAOImpl;
import com.stackroute.field.exception.PlayerNotFoundException;
import com.stackroute.field.model.Player;

@RunWith(SpringRunner.class)
@Transactional
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationContextConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class PlayerDAOImplTest {

	@Autowired
	private SessionFactory sessionFactory;
	private PlayerDAO playerDao;
	private Player player;

	@Before
	public void setUp() {
		playerDao = new PlayerDAOImpl(sessionFactory);
		player = new Player(1, "Testing-1", "Testing Service layer", "Active", new Date(), null, "Jhon123");

	}

	@After
	public void tearDown() throws Exception {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE from Player");
		query.executeUpdate();
	}

	@Test
	@Rollback(true)
	public void testCreateNoteSuccess() throws PlayerNotFoundException {

		playerDao.createPlayer(player);
		List<Player> notes = playerDao.getAllPlayersByUserId("Jhon123");
		assertEquals("Testing-1", notes.get(0).getFirstName());
		playerDao.deletePlayer(player.getPlayerId());
	}

	@Test
	@Rollback(true)
	public void testCreateNoteFailure() throws PlayerNotFoundException {

		playerDao.createPlayer(player);
		List<Player> notes = playerDao.getAllPlayersByUserId("Jhon123");
		assertNotEquals("Testing-2", notes.get(0).getFirstName());
		playerDao.deletePlayer(player.getPlayerId());

	}

	@Test
	@Rollback(true)
	public void testDeleteNoteSuccess() throws PlayerNotFoundException {

		playerDao.createPlayer(player);
		Player noteData = playerDao.getPlayerById(player.getPlayerId());
		boolean status = playerDao.deletePlayer(noteData.getPlayerId());
		assertEquals(true, status);

	}

	@Test
	public void testGetAllNotesByUserId() throws PlayerNotFoundException {
		Player note2 = new Player(2, "Testing-2", "Testing Service layer", "Active", new Date(), null,  "Jhon123");
		Player note3 = new Player(3, "Testing-3", "Testing Service layer", "Active", new Date(), null,  "Jhon123");
		playerDao.createPlayer(player);
		playerDao.createPlayer(note2);
		playerDao.createPlayer(note3);
		List<Player> notes = playerDao.getAllPlayersByUserId("Jhon123");
		assertEquals(3, notes.size());
		playerDao.deletePlayer(player.getPlayerId());
		playerDao.deletePlayer(note2.getPlayerId());
		playerDao.deletePlayer(note3.getPlayerId());
	}

	@Test
	@Rollback(true)
	public void testGetNoteById() throws PlayerNotFoundException {

		playerDao.createPlayer(player);
		Player noteData = playerDao.getPlayerById(player.getPlayerId());
		assertEquals(player, noteData);
		playerDao.deletePlayer(player.getPlayerId());

	}

	@Test(expected = PlayerNotFoundException.class)
	@Rollback(true)
	public void testGetNoteByIdFailure() throws PlayerNotFoundException {

		playerDao.createPlayer(player);
		Player noteData = playerDao.getPlayerById(2);
		assertEquals(player, noteData);
		playerDao.deletePlayer(player.getPlayerId());

	}

	@Test
	@Rollback(true)
	public void testUpdateNote() throws PlayerNotFoundException {
		playerDao.createPlayer(player);
		Player noteData = playerDao.getPlayerById(player.getPlayerId());
		noteData.setLastName("Unit testing for DAO layer");
		noteData.setCreatedAt(new Date());
		;
		boolean status = playerDao.UpdatePlayer(noteData);
		Player updatedNote = playerDao.getPlayerById(noteData.getPlayerId());
		assertEquals("Unit testing for DAO layer", updatedNote.getLastName());
		assertEquals(true, status);
		playerDao.deletePlayer(updatedNote.getPlayerId());

	}

}
