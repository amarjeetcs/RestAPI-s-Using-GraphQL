package com.trh.crud.project.graphql.service;

import com.trh.crud.project.graphql.model.User;

import java.util.List;

public interface UserService {
    
    User createUser(User user);
    
    User getUserById(int userId);
    
    List<User> getAllUsers();
    
    User updateUser(int userId, User user);
    
    boolean deleteUser(int userId);
}