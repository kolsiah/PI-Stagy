package com.example.document.Services;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize( DataSize.ofMegabytes(10));  // 10MB per file
        factory.setMaxRequestSize(DataSize.ofMegabytes(20));  // 20MB total request
        return factory.createMultipartConfig();
    }
}
