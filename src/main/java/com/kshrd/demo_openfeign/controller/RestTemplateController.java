package com.kshrd.demo_openfeign.controller;

import com.kshrd.demo_openfeign.model.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rest-template")
public class RestTemplateController {
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/products/{id}")
    public ResponseEntity<Map> getProductById(@PathVariable Long id) {
        String url = "https://dummyjson.com/products/" + id;
        // Synchronous call → waits for response
        return restTemplate.getForEntity(url, Map.class);
    }

    @GetMapping("/products")
    public Map getAllProduct() {
        String url = "https://dummyjson.com/products?limit=5&skip=10&select=title,price";
        // Synchronous call → waits for response
        return restTemplate.getForObject(url, Map.class);
    }

    @PostMapping("/products")
    public Map createProduct(ProductRequest product) {
        String url = "https://dummyjson.com/products/add";
        // Synchronous call → waits for response
        return restTemplate.postForObject(url, product, Map.class);
    }

    @PostMapping("/products/location")
    public URI createProductLocation(ProductRequest product) {
        String url = "https://dummyjson.com/products/add";
        // Synchronous call → waits for response
        return restTemplate.postForLocation(url, product);
    }

    @GetMapping("/products/exchange")
    public Map getAllProductsExchange() {
        String url = "https://dummyjson.com/products?limit=5&skip=10&select=title,price";

        // Add headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer mytoken");

//        Product newProduct = new Product("Laptop", 1200);
//        HttpEntity<Product> entity = new HttpEntity<>(newProduct, headers); // POST

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }
}

