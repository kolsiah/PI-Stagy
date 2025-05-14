package com.example.user.repository;

import com.example.user.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long>  {
    Optional<Role> findByName(String nomRole);

}