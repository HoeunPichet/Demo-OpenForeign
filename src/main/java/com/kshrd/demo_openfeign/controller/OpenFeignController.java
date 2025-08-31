package com.kshrd.demo_openfeign.controller;

import com.kshrd.demo_openfeign.client.ProductClient;
import com.kshrd.demo_openfeign.model.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/open-feign/products")
@RequiredArgsConstructor
public class OpenFeignController {
    private final ProductClient productClient;

    @GetMapping("/{id}")
    public  Map<String, Object> getProductById(@PathVariable Long id) {
        return productClient.getProductById(id);
    }

    @GetMapping("/async/{id}")
    public CompletableFuture<Map<String, Object>> getProductByIdAsync(@PathVariable Long id) {
        return productClient.getProductByIdAsync(id)
                .exceptionally(ex -> Map.of(
                        "id", id,
                        "error", "Failed to fetch product: " + ex.getMessage()
                ));
    }

    @PostMapping
    public  Map<String, Object> createProduct(@RequestBody ProductRequest product) {
        return productClient.createProduct(product);
    }

}
