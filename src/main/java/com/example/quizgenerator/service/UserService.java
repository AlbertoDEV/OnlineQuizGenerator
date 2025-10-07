package com.example.quizgenerator.service;

import com.example.quizgenerator.model.User;

import java.util.Set;

public interface UserService {
    void saveUser(User user);
    User findByUsername(String username);
    void saveUserWithRoles(User user, Set<String> roles);
}
