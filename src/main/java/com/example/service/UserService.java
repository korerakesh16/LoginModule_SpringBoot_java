package com.example.springbootlogin.service;

import com.example.springbootlogin.dto.UserRegistrationDto;
import com.example.springbootlogin.model.User;
import com.example.springbootlogin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(UserRegistrationDto dto) throws IllegalArgumentException {
        String username = dto.getUsername().trim().toLowerCase();
        if (userRepo.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() == null ? "USER" : dto.getRole());
        user.setEnabled(true);
        return userRepo.save(user);
    }
}
