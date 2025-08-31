package com.kshrd.demo_openfeign.controller;

import com.kshrd.demo_openfeign.model.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/rest-client")
public class RestClientController {
    private final RestClient restClient = RestClient.create();

    @GetMapping("/products/{id}")
    public Map getProductByIdRestClient(@PathVariable Long id) {
        String url = "https://dummyjson.com/products/" + id;
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(Map.class);
//                .toEntity(Map.class);
    }

    @PutMapping("/product/{id}")
    public String updateProduct(@PathVariable Long id, @RequestBody ProductRequest product) {
        restClient.put()
                .uri("https://dummyjson.com/products/{id}", id)
                .body(product)
                .retrieve()
                .toBodilessEntity();

        return "Updated successfully";
    }
}
