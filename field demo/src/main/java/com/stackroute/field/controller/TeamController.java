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

import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.model.Team;
import com.stackroute.field.service.TeamService;


@RestController
public class TeamController {

	
	@Autowired
	TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;

	}

	
	@RequestMapping(value = "/team", method = RequestMethod.POST)
	public ResponseEntity<String> createTeam(@RequestBody Team team, HttpSession session) {
		boolean create;
		try {
			String userId = (String) session.getAttribute("loggedInUserId");
			if (null == userId) {
				return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

			}
			create = teamService.createTeam(team);
			if (!create) {
				return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);

			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);

		}

		return new ResponseEntity<String>("success", HttpStatus.CREATED);

	}

	

	@RequestMapping(value = "/team/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTeam(@PathVariable("id") Integer id, HttpSession session) {

		String userId = (String) session.getAttribute("loggedInUserId");
		if (null == userId) {
			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

		}
		if (teamService.deleteTeam(id)) {
			return new ResponseEntity<String>("success", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("error", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/playerCount/{id}", method = RequestMethod.GET)
	public ResponseEntity<Integer> playerCount(@PathVariable("id") Integer id, HttpSession session) {

		String userId = (String) session.getAttribute("loggedInUserId");
		if (null == userId) {
			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

		}
		
		Team team;
		try {
			team = teamService.getTeamById(id);
		} catch (TeamNotFoundException e) {
			return new ResponseEntity<Integer>(0, HttpStatus.NOT_FOUND);
		}
		
		if (null!=team && null!=team.getPlayers()) {
			return new ResponseEntity<Integer>(team.getPlayers().size(), HttpStatus.OK);

		} else {
			return new ResponseEntity<Integer>(0, HttpStatus.OK);
		}
	}

	

	@RequestMapping(value = "/team/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateTeam(@RequestBody Team team, @PathVariable Integer id,
			HttpSession session) {
		try {

			String userId = (String) session.getAttribute("loggedInUserId");
			if (null == userId) {
				return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

			}
			Team categoryDb = teamService.updateTeam(team, id);
			if (null == categoryDb) {
				return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	
	@RequestMapping(value = "/team", method = RequestMethod.GET)
	public ResponseEntity<List<Team>> getAllTeamsByUserId(HttpSession session) {

		List<Team> list;
		String userId = (String) session.getAttribute("loggedInUserId");
		if (null == userId) {
			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

		}
		
		list = teamService.getAllTeamByUserId(userId);

		return new ResponseEntity<List<Team>>(list, HttpStatus.OK);
	}
	
	
	

}