package com.kshrd.demo_openfeign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://dummyjson.com")
                .defaultHeader("Accept", "application/json")
                .filter((request, next) -> { // Example logging filter
                    System.out.println("Calling: " + request.url());
                    return next.exchange(request);
                })
                .build();
    }
}

