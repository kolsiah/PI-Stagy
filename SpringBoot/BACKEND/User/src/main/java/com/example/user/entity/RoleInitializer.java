package com.example.user.entity;

import com.example.user.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create roles if they don't exist
        createRoleIfNotFound("ROLE_ADMIN", List.of("CREATE_USER", "EDIT_USER", "DELETE_USER"));
        createRoleIfNotFound("ROLE_ETUDIANT", List.of("VIEW_PROFILE", "APPLY_INTERNSHIP"));
        createRoleIfNotFound("ROLE_ENTREPRISE", List.of("POST_INTERNSHIP", "MANAGE_INTERNSHIP"));
        createRoleIfNotFound("ROLE_ENSEIGNANT", List.of("VIEW_STUDENT_PROGRESS", "SUBMIT_GRADES"));
    }

    private void createRoleIfNotFound(String name, List<String> permissions) {
        if (roleRepository.findByName(name).isEmpty()) {
            Role role = new Role();
            role.setName(name);
            role.setPermissions(permissions);
            roleRepository.save(role);
        }
    }
}