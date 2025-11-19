package com.fatec.controller;

import com.fatec.dtos.LoginDto;
import com.fatec.dtos.TokenDto;
import com.fatec.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        var token = authService.login(loginDto.email(), loginDto.password());
        return ResponseEntity.ok(new TokenDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@RequestBody LoginDto registerDto) {
        return ResponseEntity.ok(new TokenDto(
            authService.register(registerDto.email(), registerDto.password())
        ));
    }
}
