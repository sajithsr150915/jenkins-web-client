package com.stackroute.field.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.field.dao.PlayerDAO;
import com.stackroute.field.dao.TeamDAO;
import com.stackroute.field.exception.PlayerNotFoundException;
import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.model.Player;
import com.stackroute.field.model.Team;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class PlayerServiceImpl implements PlayerService {

	/*
	 * Autowiring should be implemented for the NoteDAO,CategoryDAO,ReminderDAO.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	PlayerDAO playerDao;
	@Autowired
	TeamDAO teamDAO;


	/*
	 * This method should be used to save a new note.
	 */

	public boolean createPlayr(Player note) throws  TeamNotFoundException {
		try {

			Team catg = null;
			if (null != note.getTeam() && null != note.getTeam().getTeamId()) {
				catg = teamDAO.getTeamById(note.getTeam().getTeamId());
			}

            if(null!=note) {
            	note.setCreatedAt(new Date());
            }
			
			note.setTeam(catg);

		}  catch (TeamNotFoundException ex) {
			throw new TeamNotFoundException("");

		}

		return playerDao.createPlayer(note);

	}

	/* This method should be used to delete an existing note. */

	public boolean deletePlayer(int noteId) {
		return playerDao.deletePlayer(noteId);

	}
	/*
	 * This method should be used to get a note by userId.
	 */

	public List<Player> getAllPlayersByUserId(String userId) {
		return playerDao.getAllPlayersByUserId(userId);

	}

	/*
	 * This method should be used to get a note by noteId.
	 */
	public Player getPlayerById(int noteId) throws PlayerNotFoundException {
		return playerDao.getPlayerById(noteId);

	}

	/*
	 * This method should be used to update a existing note.
	 */

	public Player updatePlayer(Player note, int id)
			throws  PlayerNotFoundException, TeamNotFoundException {

		if (null == playerDao.getPlayerById(id)) {
			throw new PlayerNotFoundException("note not found");
		}

		if (null != note.getTeam() && null == teamDAO.getTeamById(note.getTeam().getTeamId())) {
			throw new TeamNotFoundException("category not found");
		}
		

		playerDao.UpdatePlayer(note);
		return note;

	}

}
