package com.stackroute.field.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.field.exception.PlayerNotFoundException;
import com.stackroute.field.model.Player;


@Repository
@Transactional
public class PlayerDAOImpl implements PlayerDAO {

	
	@Autowired
	private SessionFactory sessionFactory;

	public PlayerDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}

	

	public boolean createPlayer(Player note) {

		Integer id = (Integer) sessionFactory.getCurrentSession().save(note);
		if (null != id) {
			return true;
		} else {
			return false;
		}

	}



	public boolean deletePlayer(int playerId) {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE from Player pl where pl.playerId = :playerId");
		query.setParameter("playerId", playerId);
		int noofRows = query.executeUpdate();
		if (noofRows == 0) {
			return false;

		}
		return true;
	}

	

	public List<Player> getAllPlayersByUserId(String userId) {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("SELECT player from Player player where player.createdBy = :userId");
		query.setParameter("userId", userId);
		List<Player> list = query.list();
		return list;
	}

	

	public Player getPlayerById(int playerId) throws PlayerNotFoundException {
		Player player = sessionFactory.getCurrentSession().get(Player.class, playerId);
		if (null == player) {
			throw new PlayerNotFoundException("note not found");

		}

		return player;
	}

	

	public boolean UpdatePlayer(Player player) {
		sessionFactory.getCurrentSession().update(player);
		return true;
	}

}
