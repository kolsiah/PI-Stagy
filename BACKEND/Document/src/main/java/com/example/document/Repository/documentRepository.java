package com.example.document.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.document.Entity.Document;

@Repository
public interface documentRepository extends JpaRepository<Document, Integer> {
}
