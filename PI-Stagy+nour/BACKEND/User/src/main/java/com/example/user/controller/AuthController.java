package com.example.user.controller;
import com.example.user.DTOs.SignInRequest;
import com.example.user.DTOs.SignUpRequest;
import com.example.user.entity.User;
import com.example.user.security.JwtUtils;
import com.example.user.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})

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
    // unlocking account
    @GetMapping("/unlock")
    public ResponseEntity<?> unlockAccount(@RequestParam String email) {
        try {
            User user = authService.unlockUser(email);
            return ResponseEntity.ok(Map.of("message", "Account unlocked successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/userNom/{id}")
    public ResponseEntity<String> getNomUser(@PathVariable long id) {
        String nom = authService.getnNomByid(id);
        return ResponseEntity.ok("\"" + nom + "\"");
    }

}