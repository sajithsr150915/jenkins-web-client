package com.stackroute.field.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.field.model.Player;
import com.stackroute.field.service.PlayerService;


@RestController
public class PlayerController {

	
	@Autowired
	PlayerService playerService;

	public PlayerController(PlayerService noteService) {

		this.playerService = noteService;

	}

	

	@RequestMapping(value = "/player", method = RequestMethod.POST)
	public ResponseEntity<String> createPlayer(@RequestBody Player player) {
		boolean create;
		try {
			
			create = playerService.createPlayr(player);
			if (!create) {
				return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);

			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);

		}

		return new ResponseEntity<String>("success", HttpStatus.CREATED);

	}

	

	@RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePlayer(@PathVariable("id") Integer id, HttpSession session) {

		String userId = (String) session.getAttribute("loggedInUserId");
		if (null == userId) {
			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

		}
		if (playerService.deletePlayer(id)) {
			return new ResponseEntity<String>("success", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("error", HttpStatus.NOT_FOUND);
		}
	}


	@RequestMapping(value = "/player/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updatePlayer(@RequestBody Player player, @PathVariable Integer id, HttpSession session) {
		try {

			String userId = (String) session.getAttribute("loggedInUserId");
			if (null == userId) {
				return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

			}
			Player noteDb = playerService.updatePlayer(player, id);
			if (null == noteDb) {
				return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}


	@RequestMapping(value = "/player", method = RequestMethod.GET)
	public ResponseEntity<List<Player>> getPlayers(HttpSession session) {

		List<Player> noteList;
		String userId = (String) session.getAttribute("loggedInUserId");
		if (null == userId) {
			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

		}
		noteList = playerService.getAllPlayersByUserId(userId);

		return new ResponseEntity<List<Player>>(noteList, HttpStatus.OK);
	}
	
	

	
	
	
	

}
