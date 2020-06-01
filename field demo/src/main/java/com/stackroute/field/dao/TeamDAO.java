package com.stackroute.field.dao;

import java.util.List;

import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.model.Team;

public interface TeamDAO {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	public boolean createTeam(Team team);

	public boolean deleteTeam(int teamId);

	public boolean updateTeam(Team team);

	public Team getTeamById(int teamId) throws TeamNotFoundException;

	public List<Team> getAllTeamByUserId(String userId);
}
