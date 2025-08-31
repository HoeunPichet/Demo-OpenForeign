package com.kshrd.demo_openfeign.client;

import com.kshrd.demo_openfeign.feign.config.FeignConfig;
import com.kshrd.demo_openfeign.model.request.ProductRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@FeignClient(
        name = "product-service",
        url = "https://dummyjson.com",
        path = "/products",
//        fallback = ProductClientFallback.class
//        fallbackFactory = ProductClientFallbackFactory.class,
//        configuration = FeignConfig .class
        dismiss404 = true
)
public interface ProductClient {
    @GetMapping("/{id}?select=title,price")
    Map<String, Object> getProductById(@PathVariable Long id);

    // Synchronous call
    @GetMapping("/{id}?select=title,price")
    CompletableFuture<Map<String, Object>> getProductByIdAsync(@PathVariable Long id);

    @PostMapping("/add")
    Map<String, Object> createProduct(@RequestBody ProductRequest product);
}
