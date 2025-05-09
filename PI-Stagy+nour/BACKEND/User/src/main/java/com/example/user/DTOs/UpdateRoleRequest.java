package com.example.user.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateRoleRequest {
    @NotEmpty(message = "At least one role is required")
    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}