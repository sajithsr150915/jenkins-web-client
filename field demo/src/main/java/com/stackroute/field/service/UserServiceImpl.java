package com.stackroute.field.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.field.dao.UserDAO;
import com.stackroute.field.exception.UserAlreadyExistException;
import com.stackroute.field.exception.UserNotFoundException;
import com.stackroute.field.model.User;

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
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the userDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */
	@Autowired
	UserDAO userDAO;
	/*
	 * This method should be used to save a new user.
	 */

	public boolean registerUser(User user) throws UserAlreadyExistException {
		if(null!=user) {
			user.setUserAddedDate(new Date());
		}
		if (null != userDAO.getUserById(user.getUserId())) {
			throw new UserAlreadyExistException("usr already exist");
		}
		return userDAO.registerUser(user);
	}

	/*
	 * This method should be used to update a existing user.
	 */

	public User updateUser(User user, String userId) throws Exception {
		User userDb = userDAO.getUserById(userId);
		if (null == userDb) {
			throw new Exception();
		} else {
			userDb.setUserMobile(user.getUserMobile());
			userDb.setUserName(user.getUserName());
			userDb.setUserPassword(user.getUserPassword());
			userDAO.updateUser(userDb);
			return userDb;
		}
	}

	/*
	 * This method should be used to get a user by userId.
	 */

	public User getUserById(String UserId) throws UserNotFoundException {
		User user = userDAO.getUserById(UserId);

		if (null == user) {
			throw new UserNotFoundException("");

		} else {
			return user;
		}

	}

	/*
	 * This method should be used to validate a user using userId and password.
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		try {

			if (userDAO.validateUser(userId, password)) {
				return true;
			} else {
				throw new UserNotFoundException("user not found");

			}
		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("user not found");

		}

	}

	/* This method should be used to delete an existing user. */
	public boolean deleteUser(String UserId) {
		return userDAO.deleteUser(UserId);

	}

}
