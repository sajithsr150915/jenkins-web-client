package com.stackroute.field.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.field.dao.TeamDAO;
import com.stackroute.field.exception.TeamNotFoundException;
import com.stackroute.field.model.Team;

import java.util.Date;

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
public class TeamServiceImpl implements TeamService {
	/*
	 * Autowiring should be implemented for the CategoryDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */
	@Autowired
	TeamDAO categoryDAO;

	/*
	 * This method should be used to save a new category.
	 */
	public boolean createTeam(Team category) {
		if(null!=category) {
			category.setTeamCreationDate(new Date());
		}
		return categoryDAO.createTeam(category);

	}

	/* This method should be used to delete an existing category. */
	public boolean deleteTeam(int categoryId) {
		return categoryDAO.deleteTeam(categoryId);

	}

	/*
	 * This method should be used to update a existing category.
	 */

	public Team updateTeam(Team category, int id) throws TeamNotFoundException {
		Team catg = null;
		try {

			catg = categoryDAO.getTeamById(id);
			if (null == catg)
				throw new TeamNotFoundException("");

		} catch (TeamNotFoundException ex) {
			throw new TeamNotFoundException("");
		}
		catg.setTeamDescription(category.getTeamDescription());
		catg.setTeamName(category.getTeamName());
		categoryDAO.updateTeam(catg);
		return category;

	}

	/*
	 * This method should be used to get a category by categoryId.
	 */
	public Team getTeamById(int categoryId) throws TeamNotFoundException {
		Team catg = null;
		try {

			catg = categoryDAO.getTeamById(categoryId);
			if (null == catg)
				throw new TeamNotFoundException("");

		} catch (TeamNotFoundException ex) {
			throw new TeamNotFoundException("");
		}
		return catg;

	}

	/*
	 * This method should be used to get a category by userId.
	 */

	public List<Team> getAllTeamByUserId(String userId) {
		return categoryDAO.getAllTeamByUserId(userId);

	}

}
