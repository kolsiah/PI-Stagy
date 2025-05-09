package com.esprit.microservice.offrestage.Client ;
import com.esprit.microservice.offrestage.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")  // Nom du service tel qu'il est enregistré dans Eureka
public interface UserClient {

    @GetMapping("/user/api/admin/getUser/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}
