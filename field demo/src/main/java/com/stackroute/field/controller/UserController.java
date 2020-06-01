package com.stackroute.field.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.field.exception.UserAlreadyExistException;
import com.stackroute.field.exception.UserNotFoundException;
import com.stackroute.field.model.User;
import com.stackroute.field.service.UserService;


@RestController
public class UserController {

	
	@Autowired
	UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		try {
			userService.registerUser(user);

		} catch (UserAlreadyExistException e) {
			return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<String>("success", HttpStatus.CREATED);

	}

	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable String id, HttpSession session) {
		try {
			String userId = (String) session.getAttribute("loggedInUserId");
			if (null == userId) {
				return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

			}
			User usDb = userService.updateUser(user, id);
			if (null == usDb) {
				return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable("id") String id, HttpSession session) {

		String userId = (String) session.getAttribute("loggedInUserId");
		if (null == userId) {
			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

		}
		if (userService.deleteUser(id)) {
			return new ResponseEntity<String>("success", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("error", HttpStatus.NOT_FOUND);
		}
	}

	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserBySession(@PathVariable("id") String id, HttpSession session) {
		User user;
		try {
			String userId = (String) session.getAttribute("loggedInUserId");
			if (null == userId) {
				return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);

			}
			user = userService.getUserById(id);
			if (null == user) {
				return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

			}

		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}