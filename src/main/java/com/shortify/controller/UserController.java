package com.shortify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shortify.entity.User;
import com.shortify.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for user registration and management.
 */
@RestController
@RequestMapping("/shortify/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {

	private final UserService userService;

	/**
	 * Register a new user and generate an API key.
	 *
	 * @param request User registration request DTO
	 * @return User with generated API key
	 */
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User request) {
		User user = userService.registerUser(request.getName(), request.getEmail());
		return ResponseEntity.ok(user);
	}
}