package com.fatec;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedController {
    @GetMapping("/")
    public String adminEndpoint() {
        return "This is a protected endpoint.";
    }
}
