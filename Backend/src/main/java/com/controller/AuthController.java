package com.controller;

import com.model.User;
import java.util.HashMap;
import java.util.Map;
import com.dto.LoginRequest;
import com.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5174")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody LoginRequest loginRequest) {

        String token = userService.loginUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        User user = userService.findByUsername(loginRequest.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId());

        return response;
    }
    
}