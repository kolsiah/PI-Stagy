package org.example.apigateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class GatewayConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("Candidat", r -> r.path("/candidats/**")
                        .uri("lb://MICROSERVICE-CANDIDATS")) // URL fixe du microservice candidat
                .route("Job", r -> r.path("/jobs/**")
                        .uri("lb://JOB")) // URL fixe du microservice job

                .route("EntrepriseWeb", r -> r.path("/Entreprise/**")
                        .uri("http://localhost:8052/Entreprise")) // URL fixe du microservice candidat
                .route("Entreprise", r -> r.path("/api/entreprises/**")
                        .uri("http://localhost:8088/api/entreprises")) // URL fixe du microservice candidat
                .route("EvaluationCondidat", r -> r.path("/EvaluationCondidat/**")
                        .uri("http://localhost:8090/EvaluationCondidat")) // URL fixe du microservice job
                // ✅ 🔥 Nouvelle route pour les ratings
                .route("Ratings", r -> r.path("/api/ratings/**")
                        .uri("http://localhost:8088"))

                .build();
    }
}
