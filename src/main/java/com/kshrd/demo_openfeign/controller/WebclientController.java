package com.kshrd.demo_openfeign.controller;

import com.kshrd.demo_openfeign.exception.NotFoundException;
import com.kshrd.demo_openfeign.model.request.ProductRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/web-client/products")
@RequiredArgsConstructor
public class WebclientController {
    // WebClient.create
    private final WebClient webClient = WebClient.create("https://dummyjson.com");
    // WebClient Builder in WebClientConfig
    private final WebClient client;

//    private final WebClient webClient;
//    public ProductService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder
//                .baseUrl("https://dummyjson.com")
//                .build();
//    }

    @GetMapping("/{id}")
    public Mono<Object> getProductById(@PathVariable Long id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new NotFoundException("A Product not found with ID: " + id))
                )
                .bodyToMono(Object.class); // Mono = async single value
//                .block();   // blocks the thread until response
    }

    @GetMapping("/flux")
    public Flux<Map> getDataFlux() {
        return webClient.get()
                .uri("/products?limit=5&skip=10&select=title,price")
                .retrieve()
                .bodyToFlux(Map.class);
    }

    @GetMapping("/flux/subscribe")
    public Flux<Map> getDataFluxSubscribe() {
        Flux<Map> response = webClient.get()
                .uri("/products?limit=5&skip=10&select=title,price")
                .retrieve()
                .bodyToFlux(Map.class);
        response.subscribe(product -> {
            System.out.println("Got product: " + product);
        });

        return response;
    }

    @PostMapping
    public Mono<ProductRequest> addProduct(ProductRequest product) {
        return webClient.post()
                .uri("/products/add")
//                .header("Content-Type", "application/json")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(ProductRequest.class);
    }

//    @CircuitBreaker(name = "productService", fallbackMethod = "getDefaultProduct")
    @GetMapping("/builder/{id}")
    public Mono<Map> getDataClientBuilder(@PathVariable Long id) {
        return client.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Map.class);
    }

    public Map<String, Object> getDefaultProduct(Long id, Throwable throwable) {
        System.out.println("Circuit breaker triggered: " + throwable.getMessage());

        Map<String, Object> fallback = new HashMap<>();
        fallback.put("id", id);
        fallback.put("title", "Offline Product");
        fallback.put("price", 0);
        return fallback;
    }
}
