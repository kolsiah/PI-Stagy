package com.example.document.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.document.DTO.ValidationEvent;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.kafka.annotation.KafkaListener;
import com.example.document.Entity.Document;
import com.example.document.Entity.Statut;
import com.example.document.Entity.Type;
import com.example.document.Repository.documentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.lucene.*;
import org.apache.tika.Tika;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import okhttp3.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class documentServiceIMPL implements documentService {
    @Autowired
    public documentRepository dR;
    @Autowired
    public Cloudinary cloudinary;
    @Value("${huggingface.api.key}")
    private String huggingFaceApiKey;

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
    public List<Document> getDocumentsByEtudiantId(int etudiantId) {
        return dR.findByEtudiantId(etudiantId);
    }
    public List<Document> getAllExceptCV() {
        return dR.findAllExceptCV();
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
            docToUpdate.setDateUpload(new Date());
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
    public boolean updateDocumentStatus(int id, String status) {
        Optional<Document> optionalDoc = dR.findById(id);
        if (optionalDoc.isPresent()) {
            Document doc = optionalDoc.get();
            doc.setStatutDocument(Statut.valueOf(status));
            dR.save(doc);
            return true;
        }
        return false;
    }
   /* @KafkaListener(topics = "validation-events", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeValidationEvent(ValidationEvent event) {
        int documentId = event.getDocumentId();
        Statut statut = Statut.valueOf(event.getStatus());
        Document document = dR.findById(documentId).orElseThrow(() -> new IllegalArgumentException("Document not found"));

        // Update the document status based on the validation event
        document.setStatutDocument(statut);
        dR.save(document);

        // Log the update or perform additional operations if needed
        System.out.println("Document " + documentId + " updated with status " + statut);
    }*/


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
    // ✅ Méthode principale
    public Map<String, Object> analyzeReport(int documentId) throws Exception {
        // 1. Récupérer le document
        Document document = dR.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document introuvable avec ID : " + documentId));

        // 2. Télécharger le PDF depuis Cloudinary
        URL pdfUrl = new URL(document.getUrlDocument());
        InputStream inputStream = pdfUrl.openStream();

        // 3. Extraire le texte avec Tika
        Tika tika = new Tika();
        String content = tika.parseToString(inputStream);

        // 4. Générer résumé via HuggingFace
        String resume = summarizeWithHuggingFace(content);

        // 5. Extraire les technologies détectées
        List<String> technologies = detectTechnologies(content);

        // 6. Retourner le résultat
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("resume", resume);
        result.put("technologies", technologies);

        return result;
    }

    // ✅ Appel à HuggingFace pour générer le résumé
    private String summarizeWithHuggingFace(String inputText) throws Exception {
        OkHttpClient client = new OkHttpClient();

        // Raccourcir si le texte est trop long
        if (inputText.length() > 1000) {
            inputText = inputText.substring(0, 1000);
        }

        JSONObject json = new JSONObject();
        json.put("inputs", inputText);

        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json")
        );
        String modelUrl = "https://api-inference.huggingface.co/models/plguillou/t5-base-fr-sum-cnndm";

        Request request = new Request.Builder()
                .url(modelUrl)
                .addHeader("Authorization", "Bearer " + huggingFaceApiKey)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();

        // Log pour debug si besoin
        System.out.println("Réponse HuggingFace : " + jsonResponse);
// Avant de parser comme JSONArray
        if (jsonResponse.startsWith("{") && jsonResponse.contains("error")) {
            throw new RuntimeException("HuggingFace Error: " + jsonResponse);
        }

        // Lire la réponse
        JSONArray arr = new JSONArray(jsonResponse);
        if (arr.length() > 0) {
            JSONObject obj = arr.getJSONObject(0);
            return obj.getString("summary_text");
        } else {
            throw new RuntimeException("Résumé non généré");
        }
    }

    // ✅ Analyse simple des technologies utilisées
    private List<String> detectTechnologies(String content) {
        List<String> techList = List.of(
                "Java", "Spring Boot", "Angular", "MySQL", "MongoDB",
                "Docker", "Kubernetes", "React", "Node.js", "PHP", "Laravel",
                "Python", "Flask", "Vue.js", "TypeScript"
        );

        String lowerContent = content.toLowerCase();

        return techList.stream()
                .filter(tech -> lowerContent.contains(tech.toLowerCase()))
                .collect(Collectors.toList());
    }
    private String extractTextFromUrl(String url) throws Exception {
        InputStream stream = new URL(url).openStream();
        Tika tika = new Tika();
        return tika.parseToString(stream);
    }
    public float compareTextWithCommons(String text1, String text2) {
        CosineSimilarity cosine = new CosineSimilarity();

        Map<CharSequence, Integer> vec1 = getTermFrequencyMap(text1);
        Map<CharSequence, Integer> vec2 = getTermFrequencyMap(text2);

        Double result = cosine.cosineSimilarity(vec1, vec2);
        return result != null ? result.floatValue() : 0f;
    }

    private Map<CharSequence, Integer> getTermFrequencyMap(String text) {
        Map<CharSequence, Integer> tf = new HashMap<>();
        for (String word : text.toLowerCase().split("\\W+")) {
            if (word.isEmpty()) continue;
            tf.put(word, tf.getOrDefault(word, 0) + 1);
        }
        return tf;
    }
    public float compareDocumentsById(int id1, int id2) throws Exception {
        Document doc1 = dR.findById(id1)
                .orElseThrow(() -> new RuntimeException("Document 1 introuvable"));
        Document doc2 = dR.findById(id2)
                .orElseThrow(() -> new RuntimeException("Document 2 introuvable"));

        String text1 = extractTextFromUrl(doc1.getUrlDocument());
        String text2 = extractTextFromUrl(doc2.getUrlDocument());

        return compareTextWithCommons(text1, text2);
    }




}
