package com.kshrd.demo_openfeign.client;

import com.kshrd.demo_openfeign.model.request.ProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {

    private static final Logger logger = LoggerFactory.getLogger(ProductClientFallbackFactory.class);

    @Override
    public ProductClient create(Throwable cause) {
        logger.warn("Fallback triggered for ProductClient with ID: " + cause.getMessage());

        return new ProductClient() {
            @Override
            public Map<String, Object> getProductById(Long id) {
                Map<String, Object> fallback = new HashMap<>();
                fallback.put("id", id);
                fallback.put("title", "Offline Product");
                fallback.put("description", "Service unreachable.");
                fallback.put("price", 0);
                fallback.put("rating", 0.0);
                fallback.put("stock", 0);
                fallback.put("category", "fallback");
                fallback.put("thumbnail", "https://via.placeholder.com/150");
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
        };
    }
}