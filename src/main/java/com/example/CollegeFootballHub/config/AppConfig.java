package com.example.CollegeFootballHub.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Marks this class as a Spring configuration class that provides beans to the application context
public class AppConfig {

    @Value("${college-football-hub.api.base-url}")  // Injects the API base URL from application.properties
    private String baseUrl;

    @Value("${college-football-hub.api.key}")  // Injects the API key from application.properties
    private String apiKey;

    @Bean
    public String apiKey() {
        return apiKey;  // Exposes the API key as a bean so it can be injected elsewhere. This allows controllers and services to access the key without direct property injection
    }
}