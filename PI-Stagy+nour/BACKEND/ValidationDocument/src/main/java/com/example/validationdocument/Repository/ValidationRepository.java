package com.example.validationdocument.Repository;

import com.example.validationdocument.Entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Integer> {
    List<Validation> findValidationByIdEncadrant(int idEncadrant);
    List<Validation> findValidationByEtudiantId(int validationId);
}
