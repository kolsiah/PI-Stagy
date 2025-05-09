package com.example.user.controller;

import com.example.user.DTOs.UpdateRoleRequest;
import com.example.user.entity.User;
import com.example.user.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }
    @PutMapping("/users/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')") // Only admins can update roles
    public User updateUserRoles(@PathVariable Long userId, @RequestBody UpdateRoleRequest request) {
        return authService.updateUserRoles(userId, request);
    }
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            User user = authService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}