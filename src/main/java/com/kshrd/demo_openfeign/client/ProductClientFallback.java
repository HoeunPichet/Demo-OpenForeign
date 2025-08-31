package com.kshrd.demo_openfeign.client;

import com.kshrd.demo_openfeign.model.request.ProductRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public Map<String, Object> getProductById(Long id) {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("id", id);
        fallback.put("title", "Offline Product");
        fallback.put("price", 0);
        return fallback;
    }

    @Override
    public CompletableFuture<Map<String, Object>> getProductByIdAsync(Long id) {
        return null;
    }

    @Override
    public Map<String, Object> createProduct(ProductRequest product) {
        return Map.of();
    }
}