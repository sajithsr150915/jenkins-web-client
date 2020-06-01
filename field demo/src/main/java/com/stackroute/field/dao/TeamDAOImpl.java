package com.stackroute.field.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.model.Team;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class TeamDAOImpl implements TeamDAO {

	
	@Autowired
	private SessionFactory sessionFactory;

	public TeamDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}

	
	public boolean createTeam(Team team) {

		Integer id = (Integer) sessionFactory.getCurrentSession().save(team);
		if (null != id) {
			return true;
		} else {
			return false;
		}

	}

	
	public boolean deleteTeam(int teamId) {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("DELETE from Team where teamId = :teamId");
		query.setParameter("teamId", teamId);
		int noofRows = query.executeUpdate();
		if (noofRows == 0) {
			return false;

		}
		return true;
	}
	

	public boolean updateTeam(Team team) {
		sessionFactory.getCurrentSession().update(team);
		return true;

	}
	
	public Team getTeamById(int teamId) throws TeamNotFoundException {
		Team team = sessionFactory.getCurrentSession().get(Team.class, teamId);

		if (null == team) {
			throw new TeamNotFoundException(" not found");

		}

		return team;
	}

	
	public List<Team> getAllTeamByUserId(String userId) {

		Query query = sessionFactory.getCurrentSession()
				.createQuery("SELECT team from Team team where team.teamCreatedBy = :userId");
		query.setParameter("userId", userId);
		List<Team> list = query.list();
		return list;

	}

}
