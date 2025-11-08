package com.safecrypto.authservice.service;

import com.safecrypto.authservice.model.User;
import com.safecrypto.authservice.repository.UserRepository;
import com.safecrypto.authservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ Register user
    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "EMAIL_EXISTS";
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        return jwtUtil.generateToken(user.getEmail());
    }

    // ✅ Login user
    public String loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return "INVALID";

        User user = userOpt.get();
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(email);
        } else {
            return "INVALID";
        }
    }
}
