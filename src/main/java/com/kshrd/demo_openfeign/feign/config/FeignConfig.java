package com.kshrd.demo_openfeign.feign.config;

import feign.Contract;
import feign.Logger;
import feign.Request;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Do NOT use @Configuration here (unless isolated)
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // Logs all requests/responses
    }

    @Bean
    public Contract feignContract() {
        return new SpringMvcContract(); // Allows use of @GetMapping, @RequestParam, etc.
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(
                5000,  // connect timeout = 5 seconds
                5000   // read timeout = 5 seconds
        );
    }
}