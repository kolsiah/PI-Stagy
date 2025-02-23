package com.example.document.Services;

import org.springframework.web.multipart.MultipartFile;
import com.example.document.Entity.Document;
import com.example.document.Entity.Statut;
import com.example.document.Entity.Type;

import java.io.IOException;
import java.util.List;

public interface documentService {
    List<Document> getAllDocuments();
    Document getDocumentById(int id);
    Document createDocument(Document document);
    Document updateDocument(int id, Document document);
    void deleteDocument(int id);
    String uploadFile(MultipartFile file, String titreDocument, Type typeDocument, Statut statutDocument, int etudiantId, int stageId)throws IOException;
}
