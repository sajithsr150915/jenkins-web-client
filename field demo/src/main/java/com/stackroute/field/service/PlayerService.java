package com.stackroute.field.service;

import java.util.List;

import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.exception.PlayerNotFoundException;
import com.stackroute.field.model.Player;

public interface PlayerService {
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */
	public boolean createPlayr(Player player) throws TeamNotFoundException;

	public boolean deletePlayer(int playerId);

	public List<Player> getAllPlayersByUserId(String userId);

	public Player getPlayerById(int playerId) throws PlayerNotFoundException;

	public Player updatePlayer(Player note, int id)
			throws  PlayerNotFoundException, TeamNotFoundException;
}
