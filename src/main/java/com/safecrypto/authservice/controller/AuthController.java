package com.safecrypto.authservice.controller;

import com.safecrypto.authservice.model.User;
import com.safecrypto.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    // ✅ Register User
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        String result = userService.registerUser(user);

        Map<String, Object> response = new HashMap<>();
        if (result.equals("EMAIL_EXISTS")) {
            response.put("message", "Email already exists!");
            return ResponseEntity.status(409).body(response);
        } else {
            response.put("token", result);
            response.put("message", "User registered successfully!");
            return ResponseEntity.ok(response);
        }
    }

    // ✅ Login User
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        String token = userService.loginUser(email, password);

        Map<String, Object> response = new HashMap<>();
        if (token.equals("INVALID")) {
            response.put("message", "Invalid credentials!");
            return ResponseEntity.status(401).body(response);
        } else {
            response.put("token", token);
            response.put("message", "Login successful!");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/test")
    public String testApi() {
        return "AuthController working ✅";
    }
}
