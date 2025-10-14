package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return  userRepository.findByUsername(name);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean verifyPassword(String username, String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            return false;
        }

        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return false;

        var user = userOpt.get();
        if (!user.getEnabled()) return false;

         return Objects.equals(rawPassword, user.getPassword());
    }

}
