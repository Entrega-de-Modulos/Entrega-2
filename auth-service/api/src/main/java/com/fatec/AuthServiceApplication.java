package com.fatec;

import com.fatec.entities.Role;
import com.fatec.entities.User;
import com.fatec.repository.UserRepositoryInMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner {
    private final UserRepositoryInMemory userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String ADMIN_EMAIL;

    @Value("${admin.password}")
    private String ADMIN_PASSWORD;

    public AuthServiceApplication(UserRepositoryInMemory userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var adminUser = new User(
            1L,
            ADMIN_EMAIL,
            passwordEncoder.encode(ADMIN_PASSWORD),
            Role.ADMIN
        );
        userRepository.save(adminUser);
    }
}
