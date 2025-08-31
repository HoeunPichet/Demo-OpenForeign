package com.kshrd.demo_openfeign.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RestTemplateController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final RestClient restClient = RestClient.create();

    @GetMapping("/rest-template/products/{id}")
    public ResponseEntity<Map> getProductById(@PathVariable Long id) {
        String url = "https://dummyjson.com/products/" + id;
        // Synchronous call → waits for response
        return restTemplate.getForEntity(url, Map.class);
    }

    @GetMapping("/rest-template/products")
    public Map getAllProduct() {
        String url = "https://dummyjson.com/products?limit=5&skip=10&select=title,price";
        // Synchronous call → waits for response
        return restTemplate.getForObject(url, Map.class);
    }

    @GetMapping("/rest-client/products/{id}")
    public Map getProductByIdRestClient(@PathVariable Long id) {
        String url = "https://dummyjson.com/products/" + id;
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(Map.class);
//                .toEntity(Map.class);
    }
}

