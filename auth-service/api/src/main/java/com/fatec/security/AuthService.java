package com.fatec.security;

import com.fatec.entities.Role;
import com.fatec.entities.User;
import com.fatec.exceptions.DuplicateEmailException;
import com.fatec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.Date;

@Component
public class AuthService {
    @Value("jwt.secret")
    private String jwtSecret;

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    private String generateToken(String email) {
        var issuedAt = Instant.now();
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(issuedAt.plus(Duration.ofHours(1))))
                .compact();
    }

    public String register(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateEmailException("User already exists with email: " + email);
        }
        var user = new User(null, email, passwordEncoder.encode(password), Role.USER);
        userRepository.save(user);
        return generateToken(email);
    }

    public String login(String email, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authToken);
        return generateToken(email);
    }
}
