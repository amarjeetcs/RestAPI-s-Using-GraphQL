package com.trh.crud.project.graphql.service.impl;

import com.trh.crud.project.graphql.model.User;
import com.trh.crud.project.graphql.repository.UserRepository;
import com.trh.crud.project.graphql.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Mark this class as a service component.
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired // Automatically inject the UserRepository.
	private UserRepository userRepository;

	@Override // Create a new user.
	public User createUser(User user) {
		logger.info("Creating new user: {}", user.getUserName());
		User createdUser = userRepository.save(user);
		logger.info("Created user with ID: {}", createdUser.getUserId());
		return createdUser;
	}

	@Override // Get a user by ID.
	public User getUserById(int userId) {
		logger.info("Fetching user with ID: {}", userId);
		return userRepository.findById(userId).orElse(null);
	}

	@Override // Get all users.
	public List<User> getAllUsers() {
		logger.info("Fetching all users");
		List<User> users = userRepository.findAll();
		logger.info("Retrieved {} users", users.size());
		return users;
	}

	@Override // Update an existing user's information.
	public User updateUser(int userId, User user) {
		logger.info("Updating user with ID: {}", userId);
		if (userRepository.existsById(userId)) {
			user.setUserId(userId); // Set the ID to ensure it updates the correct record.
			User updatedUser = userRepository.save(user);
			logger.info("Updated user with ID: {}", updatedUser.getUserId());
			return updatedUser;
		} else {
			logger.warn("No user found with ID: {}", userId);
			return null; // Or throw an exception if you prefer.
		}
	}

	@Override // Delete a user by ID.
	public boolean deleteUser(int userId) {
		logger.info("Deleting user with ID: {}", userId);
		if (userRepository.existsById(userId)) {
			userRepository.deleteById(userId);
			logger.info("Deleted user with ID: {}", userId);
			return true;
		} else {
			logger.warn("No user found with ID: {}", userId);
			return false; // Or throw an exception if you prefer.
		}
	}
}