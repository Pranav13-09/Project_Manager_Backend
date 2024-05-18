package com.org.projectmanager.service;

import com.org.projectmanager.model.User;
import com.org.projectmanager.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.org.projectmanager.config.JwtProvider jwtProvider;

    @Override
    public User findUserByJwt(String jwt) {
        try {
            String email = jwtProvider.getEmailFromToken(jwt);
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            // Log error or throw a custom exception
            throw new RuntimeException("Error processing JWT", e);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            // Log error or throw a custom exception
            throw new RuntimeException("Error finding user by email", e);
        }
    }

    @Override
    public User findUserById(Long id) {
        try {
            return userRepository.findById(id).orElse(null);
        } catch (Exception e) {
            // Log error or throw a custom exception
            throw new RuntimeException("Error finding user by ID", e);
        }
    }

    @Override
    public User updateUsersProjectSize(User user, int newSize) {
        try {
            user.setProjectSize(newSize);
            return userRepository.save(user);
        } catch (Exception e) {
            // Log error or throw a custom exception
            throw new RuntimeException("Error updating user's project size", e);
        }
    }
}
