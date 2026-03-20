package com.service;

import com.jwt.JwtUtil;
import com.model.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @Autowired
    private JwtUtil jwtUtil;

    public String registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Error: Username is already taken!";
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Email is already in use!";
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign default role
        HashSet<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!";
    }
    public String loginUser(String username, String password) {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid password";
        }

        String token = jwtUtil.generateToken(username);
        return token;
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }
   
  }
