package com.stackroute.field.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.field.exception.UserNotFoundException;
import com.stackroute.field.model.User;

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
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	private SessionFactory sessionFactory;

	//
	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {

		String id = (String) sessionFactory.getCurrentSession().save(user);
		if (null != id) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {

		sessionFactory.getCurrentSession().update(user);
		return true;

	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String UserId) {

		return sessionFactory.getCurrentSession().get(User.class, UserId);

	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		User user = sessionFactory.getCurrentSession().get(User.class, userId);

		if (null == user) {
			throw new UserNotFoundException("user not found");

		} else if (password.equals(user.getUserPassword())) {
			return true;
		}
		return false;
	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE from User where userId = :userId");
		query.setParameter("userId", userId);
		int noofRows = query.executeUpdate();
		if (noofRows == 0) {
			return false;

		}
		return true;

	}

}
