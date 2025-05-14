package com.example.validationdocument.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.validationdocument.Entity.StatutV;

@FeignClient(name = "DOCUMENT")
public interface DocumentClient {
    @PutMapping("/documents/{id}/status")
    void updateDocumentStatus(@PathVariable("id") int id, @RequestParam("status") StatutV status);

}
