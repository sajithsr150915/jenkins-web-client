package com.stackroute.field.service;

import java.util.List;

import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.model.Team;

public interface TeamService {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */
	public boolean createTeam(Team team);

	public boolean deleteTeam(int teamId);

	public Team updateTeam(Team team, int id) throws TeamNotFoundException;

	public Team getTeamById(int teamId) throws TeamNotFoundException;

	public List<Team> getAllTeamByUserId(String userId);

}
