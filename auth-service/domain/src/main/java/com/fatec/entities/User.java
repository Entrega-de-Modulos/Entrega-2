package com.fatec.entities;

public record User(
    Long id,
    String email,
    String password,
    Role role
) {
}
