package com.example.jwtapp.service;

import com.example.jwtapp.model.User;

import java.util.List;

public interface UserService {
    User registration(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);
}
