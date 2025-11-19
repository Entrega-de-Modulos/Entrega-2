package com.fatec.repository;

import com.fatec.entities.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryInMemory implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        var userWithId = new User(
                users.size() + 1L,
                user.email(),
                user.password(),
                user.role()
        );
        users.put(user.email(), userWithId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }
}
