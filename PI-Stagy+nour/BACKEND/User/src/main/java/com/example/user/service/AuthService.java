package com.example.user.service;

import com.example.user.DTOs.SignUpRequest;
import com.example.user.DTOs.UpdateRoleRequest;
import com.example.user.entity.Role;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    public User signup(SignUpRequest request) {
        // Validate the request
        if (request.getNom() == null || request.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (request.getPrenom() == null || request.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom est obligatoire");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("L'email est obligatoire");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire");

        }


        // Create the user
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatut("actif");

        // Assign roles
        Set<String> roles = request.getRoles();
        if (roles == null || roles.isEmpty()) {
            // Assign default role (ROLE_ETUDIANT)
            Role defaultRole = roleRepository.findByName("ROLE_ETUDIANT")
                    .orElseThrow(() -> new RuntimeException("Rôle par défaut non trouvé"));
            user.getRoles().add(defaultRole);
        } else {
            // Assign provided roles
            for (String roleName : roles) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Rôle non trouvé: " + roleName));
                user.getRoles().add(role);
            }
        }

        return userRepository.save(user);
    }
    public String getnNomByid(Long id) {
        return userRepository.findById(id)
                .map(User::getNom) // ✅ récupère le nom réel
                .orElse("Utilisateur inconnu"); // 🔒 fallback clair
    }
    public User authenticate(String email, String password) {
        // Validate inputs
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("L'email est requis");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Le mot de passe est requis");
        }

        // Find the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si l'utilisateur est verrouillé
        if (user.isLocked()) {
            throw new RuntimeException("Votre compte est verrouillé. Vérifiez votre email pour le déverrouiller.");
        }

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // Incrémenter le nombre d'échecs
            user.setFailedAttempts(user.getFailedAttempts() + 1);

            // Verrouiller après 4 tentatives
            if (user.getFailedAttempts() >= 3) {
                user.setLocked(true);
                emailService.sendUnlockEmail(user); // Envoyer un e-mail pour déverrouiller le compte
            }

            // Sauvegarder l'utilisateur avec le nouveau nombre d'échecs
            userRepository.save(user);
            throw new RuntimeException("Mot de passe incorrect. Tentative " + user.getFailedAttempts() + "/3.");
        }

        // Réinitialiser le compteur d'échecs en cas de succès
        user.setFailedAttempts(0);
        user.setDernierLogin(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User updateUserRoles(Long userId, UpdateRoleRequest request) {
        // Find the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Clear existing roles
        user.getRoles().clear();

        // Assign new roles
        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            user.getRoles().add(role);
        }

        // Save the updated user
        return userRepository.save(user);
    }

    //unlock accounts
    public User unlockUser(String email) {
        // Find the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Unlock the account
        user.setFailedAttempts(0); // Reset failed attempts
        user.setLocked(false); // Unlock the account

        // Save the updated user
        return userRepository.save(user);
    }
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));
    }

}