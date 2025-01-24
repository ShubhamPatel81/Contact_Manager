package com.example.Contact_manager_web.service;

import com.example.Contact_manager_web.entities.User;

import java.util.Optional;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(User userId);

    boolean isUserExist(String userId);

    boolean isUserExistByUserEmail(String emailId);

    List<User> getAllUsers(); // Corrected return type
}

