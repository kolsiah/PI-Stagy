package com.example.document.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.document.Entity.Document;
import com.example.document.Entity.Statut;
import com.example.document.Entity.Type;
import com.example.document.Repository.documentRepository;

import java.io.IOException;
import java.util.*;

@Service
public class documentServiceIMPL implements documentService {
    @Autowired
    public documentRepository dR;
    @Autowired
    public Cloudinary cloudinary;
  //  private static final String API_KEY = "0e30bfe8b05968422096a2621c6e1ba0"; // Replace with your actual API key
  //  private static final String API_URL = "https://api.api2convert.com/v2/jobs";
    @Override
    public List<Document> getAllDocuments() {
        return dR.findAll();
    }

    @Override
    public Document getDocumentById(int id) {
        return dR.findById(id).orElse(null);
    }

    @Override
    public Document createDocument(Document document) {
        return dR.save(document);
    }

    @Override
    public Document updateDocument(int id, Document document) {
        Optional<Document> existingDoc = dR.findById(id);
        if (existingDoc.isPresent()) {
            Document docToUpdate = existingDoc.get();
            docToUpdate.setTitreDocument(document.getTitreDocument());
            docToUpdate.setUrlDocument(document.getUrlDocument());
            docToUpdate.setTypeDocument(document.getTypeDocument());
            docToUpdate.setStatutDocument(document.getStatutDocument());
            docToUpdate.setDateUpload(document.getDateUpload());
            docToUpdate.setEtudiantId(document.getEtudiantId());
            docToUpdate.setStageId(document.getStageId());
            return dR.save(docToUpdate);
        }
        return null;
    }

    @Override
    public void deleteDocument(int id) {
        dR.deleteById(id);
    }
    /*public String uploadFile(MultipartFile file, String titreDocument, Type typeDocument, Statut statutDocument, int etudiantId, int stageId) throws IOException {
        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed!");
        }

        // Upload the file to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String url = uploadResult.get("url").toString();
        //return uploadResult.get("url").toString();
        // Create a Document object and set its fields
        Document document = new Document();
        document.setTitreDocument(titreDocument); // Get from Angular form
        document.setTypeDocument(typeDocument); // Get from Angular form
        document.setStatutDocument(statutDocument); // Get from Angular form
        document.setDateUpload(new Date()); // Set current date
        document.setUrlDocument(url); // Set Cloudinary file URL
        document.setEtudiantId(etudiantId); // Get from Angular form
        document.setStageId(stageId); // Get from Angular form

        // Save the Document to the database
        dR.save(document);

        return url;
    }*/

   /* public String uploadFile(MultipartFile file) throws IOException {
        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed!");
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();  // Retourne l'URL du fichier
    }*/
    @Override
    public String uploadFile(MultipartFile file, String titreDocument, Type typeDocument, Statut statutDocument, int etudiantId, int stageId) throws IOException {
        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed!");
        }

        // Upload the file to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String url = uploadResult.get("url").toString();
        //return uploadResult.get("url").toString();
        // Create a Document object and set its fields
        Document document = new Document();
        document.setTitreDocument(titreDocument); // Get from Angular form
        document.setTypeDocument(typeDocument); // Get from Angular form
        document.setStatutDocument(statutDocument); // Get from Angular form
        document.setDateUpload(new Date()); // Set current date
        document.setUrlDocument(url); // Set Cloudinary file URL
        document.setEtudiantId(etudiantId); // Get from Angular form
        document.setStageId(stageId); // Get from Angular form

        // Save the Document to the database
        dR.save(document);

        return url;
    }


    /*private String convertToPdf(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-oc-api-key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("input", Collections.singletonList(
                Map.of("type", "remote", "source", uploadTempFile(file))
        ));
        requestBody.put("conversion", Collections.singletonList(
                Map.of("category", "document", "target", "pdf")
        ));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map<String, Object>> output = (List<Map<String, Object>>) response.getBody().get("output");
            if (output != null && !output.isEmpty()) {
                return (String) output.get(0).get("uri");
            }
        }

        return null;
    }*/



    /*private String uploadTempFile(MultipartFile file) throws IOException {
        // Upload to Cloudinary temporarily
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    private MultipartFile downloadConvertedFile(String fileUrl) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(fileUrl, HttpMethod.GET, null, byte[].class);


        if (response.getStatusCode() == HttpStatus.OK) {
            // Convert the byte array response to a MultipartFile
            byte[] pdfContent = response.getBody(); // Assuming the response body contains the PDF byte array
            return new MockMultipartFile("converted.pdf", "converted.pdf", "application/pdf", pdfContent);
        }

        throw new RuntimeException("Failed to download converted PDF.");
    }
}*/
}
