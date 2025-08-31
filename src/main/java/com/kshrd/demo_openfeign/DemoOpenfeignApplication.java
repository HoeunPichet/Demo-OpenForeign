package com.kshrd.demo_openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@EnableHystrix // enable Hystrix globally
public class DemoOpenfeignApplication {

//	@Bean
//	public WebClient.Builder webClientBuilder() {
//		return WebClient.builder();
//	}


	public static void main(String[] args) {
		SpringApplication.run(DemoOpenfeignApplication.class, args);
	}

}
