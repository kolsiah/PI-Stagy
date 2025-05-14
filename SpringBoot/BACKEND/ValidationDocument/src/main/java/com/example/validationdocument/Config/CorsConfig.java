package com.example.validationdocument.Config;// adapte selon ton package

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Autoriser les cookies
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Frontend Angular
        config.setAllowedHeaders(Arrays.asList("*")); // Autoriser tous les headers
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Autoriser méthodes HTTP

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Appliquer à toutes les routes

        return new CorsFilter(source);
    }
}
