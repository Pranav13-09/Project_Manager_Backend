package com.org.projectmanager.service;

import com.org.projectmanager.model.User;

public interface UserService {

    // Method to find user by JWT token
    User findUserByJwt(String jwt);

    // Method to find user by email
    User findUserByEmail(String email);

    // Method to find user by user ID
    User findUserById(Long id);

    // Method to update the user's project size
    User updateUsersProjectSize(User user, int newSize);
}
