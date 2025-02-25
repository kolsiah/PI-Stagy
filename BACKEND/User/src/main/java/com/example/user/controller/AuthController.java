package com.example.user.controller;
import com.example.user.DTOs.SignInRequest;
import com.example.user.DTOs.SignUpRequest;
import com.example.user.entity.User;
import com.example.user.security.JwtUtils;
import com.example.user.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")  //  Définit un préfixe clair

public class AuthController {

    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest request) {
        User user = authService.signup(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignInRequest request) {
        User user = authService.authenticate(request.getEmail(), request.getPassword());
        String token = JwtUtils.generateToken(user);
        return ResponseEntity.ok(Map.of("token", token));
    }
}