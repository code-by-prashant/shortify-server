package com.shortify.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shortify.entity.User;
import com.shortify.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service for User operations (API key validation).
 */
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	/**
	 * Registers a new user and generates a unique API key.
	 *
	 * @param name  User name
	 * @param email User email
	 * @return User entity with API key
	 */
	public User registerUser(String name, String email) {
		User user = User.builder().name(name).email(email).apiKey(UUID.randomUUID().toString()).build();
		return userRepository.save(user);
	}

	/**
	 * Validate API key.
	 *
	 * @param apiKey API key header
	 * @return User entity
	 */
	public User validateApiKey(final String apiKey) {
		return userRepository.findByApiKey(apiKey).orElseThrow(() -> new RuntimeException("Invalid API Key"));
	}
}
