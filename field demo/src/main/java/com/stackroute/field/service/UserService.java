package com.stackroute.field.service;



import com.stackroute.field.exception.UserAlreadyExistException;
import com.stackroute.field.exception.UserNotFoundException;
import com.stackroute.field.model.User;


public interface UserService {
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */
	public boolean registerUser(User user) throws UserAlreadyExistException;

	public User updateUser(User user, String id) throws Exception;

	public boolean deleteUser(String UserId);

	public boolean validateUser(String userName, String password) throws UserNotFoundException;

	public User getUserById(String userId) throws UserNotFoundException;

}
