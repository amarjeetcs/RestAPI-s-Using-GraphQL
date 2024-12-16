package com.trh.crud.project.graphql.controller;

import com.trh.crud.project.graphql.model.User;
import com.trh.crud.project.graphql.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Query to fetch all users.
    @QueryMapping
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        logger.info("Retrieved {} users", users.size());
        return users;
    }

    // Query to fetch a single user by ID.
    @QueryMapping
    public User getUserById(@Argument int id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        if (user != null) {
            logger.info("Retrieved user: {}", user.getUserName());
        } else {
            logger.warn("User with ID {} not found", id);
        }
        return user;
    }

    // Mutation to create a new user.
    @MutationMapping
    public User createUser(@Argument String userName, @Argument String email, 
                           @Argument String phone, @Argument String password) {
        logger.info("Creating new user: {}", userName);
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setPassword(password);
        
        User createdUser = userService.createUser(newUser);
        logger.info("Created user with ID: {}", createdUser.getUserId());
        
        return createdUser;
    }

    // Mutation to update an existing user's information.
    @MutationMapping
    public User updateUser(@Argument int id, @Argument String userName, 
                           @Argument String email, @Argument String phone,
                           @Argument String password) {
        logger.info("Updating user with ID: {}", id);
        
        User updatedUser = new User();
        updatedUser.setUserId(id); // Set the ID for update
        updatedUser.setUserName(userName);
        updatedUser.setEmail(email);
        updatedUser.setPhone(phone);
        updatedUser.setPassword(password);

        User result = userService.updateUser(id, updatedUser);
        
        if (result != null) {
            logger.info("Updated user with ID: {}", result.getUserId());
        } else {
            logger.warn("Failed to update user with ID: {}", id);
        }
        
        return result;
    }

    // Mutation to delete a user by ID.
    @MutationMapping
    public boolean deleteUser(@Argument int id) {
        logger.info("Deleting user with ID: {}", id);
        
        boolean isDeleted = userService.deleteUser(id);
        
        if (isDeleted) {
            logger.info("Deleted user with ID: {}", id);
        } else {
            logger.warn("Failed to delete user with ID: {}", id);
        }
        
        return isDeleted;
    }
}