package com.example.document.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.document.Entity.Document;
import com.example.document.Entity.Type;


import javax.print.Doc;
import java.util.List;

    @Repository
    public interface documentRepository extends JpaRepository<Document, Integer> {
        List<Document> findByEtudiantId(Integer etudiantId);
        @Query("SELECT d FROM Document d WHERE LOWER(d.typeDocument) <> 'cv'")
        List<Document> findAllExceptCV();

    }
