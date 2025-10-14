package com.example.demo.service;

import com.example.demo.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUser(Long id);

    Optional<User> getUserByName(String name);

    List<User> getAllUsers();
    void deleteUser(Long id);
    boolean verifyPassword(String username, String rawPassword);
}
