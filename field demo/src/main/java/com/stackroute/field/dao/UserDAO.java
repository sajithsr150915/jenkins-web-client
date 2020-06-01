package com.stackroute.field.dao;

import com.stackroute.field.exception.UserNotFoundException;
import com.stackroute.field.model.User;

public interface UserDAO {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	public boolean registerUser(User user);

	public boolean updateUser(User user);

	public User getUserById(String UserId);

	public boolean validateUser(String userName, String password) throws UserNotFoundException;

	public boolean deleteUser(String UserId);
}
