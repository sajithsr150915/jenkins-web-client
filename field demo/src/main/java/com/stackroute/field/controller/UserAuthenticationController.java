package com.stackroute.field.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.field.exception.UserNotFoundException;
import com.stackroute.field.model.User;
import com.stackroute.field.service.UserService;


@RestController
public class UserAuthenticationController {

	
	@Autowired
	UserService userService;

	public UserAuthenticationController(UserService userService) {
		this.userService = userService;
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody User user, HttpSession session) {
		try {
			user = userService.getUserById(user.getUserId());
			if (null != user) {
				if (!userService.validateUser(user.getUserId(), user.getUserPassword())) {
					return new ResponseEntity<String>("aunauthorized", HttpStatus.UNAUTHORIZED);

				}
				session.setAttribute("loggedInUserId", user.getUserId());

			} else {
				return new ResponseEntity<String>("aunauthorized", HttpStatus.UNAUTHORIZED);

			}

		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("aunauthorized", HttpStatus.UNAUTHORIZED);

		}

		return new ResponseEntity<String>("success", HttpStatus.OK);

	}

	

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logout(HttpSession session) {

		String userId = (String) session.getAttribute("loggedInUserId");
		User user = null;
		try {
			user = userService.getUserById(userId);
			if (null != user) {
				if (!userService.validateUser(user.getUserId(), user.getUserPassword())) {
					return new ResponseEntity<String>("aunauthorized", HttpStatus.BAD_REQUEST);

				}
				session.setAttribute("loggedInUserId", "");

			} else {
				return new ResponseEntity<String>("aunauthorized", HttpStatus.BAD_REQUEST);

			}

		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("aunauthorized", HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<String>("success", HttpStatus.OK);

	}

}
