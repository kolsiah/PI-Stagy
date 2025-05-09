package com.example.validationdocument.Services;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {
    public Map<String, Long> analyserCommentaires(List<String> commentaires) {
        try {
            URL url = new URL("http://127.0.0.1:5000/analyze-multiple");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("comments", commentaires);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                JSONObject result = new JSONObject(response.toString());
                Map<String, Long> stats = new HashMap<>();
                stats.put("positif", result.getLong("positif"));
                stats.put("negatif", result.getLong("negatif"));
                return stats;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", -1L);
        }
    }

    public String analyserCommentaire(String commentaire) {
        try {
            URL url = new URL("http://127.0.0.1:5000/analyze");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


            String jsonInput = String.format("{\"comment\": \"%s\"}", commentaire.replace("\"", "\\\""));

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() == 200) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    if (response.toString().contains("positif")) return "positif";
                    else return "négatif";
                }
            } else {
                System.out.println("❌ Erreur HTTP : " + conn.getResponseCode());
                return "erreur";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "erreur";
        }
    }

}
