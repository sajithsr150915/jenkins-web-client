package com.stackroute.field.dao;

import java.util.List;

import com.stackroute.field.exception.PlayerNotFoundException;
import com.stackroute.field.model.Player;

public interface PlayerDAO {

	

	public boolean createPlayer(Player player);

	public boolean deletePlayer(int playerId);

	public List<Player> getAllPlayersByUserId(String userId);

	public Player getPlayerById(int playerId) throws PlayerNotFoundException;

	public boolean UpdatePlayer(Player player);

}
