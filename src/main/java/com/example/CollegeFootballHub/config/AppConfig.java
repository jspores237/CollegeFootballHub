package com.example.CollegeFootballHub.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${college-football-hub.api.base-url}")
    private String baseUrl;

    @Value("${college-football-hub.api.key}")
    private String apiKey;

    @Bean
    public WebClientCustomizer webClientCustomizer() {
        return webClientBuilder -> webClientBuilder
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(5 * 1024 * 1024));
    }

    @Bean
    public String apiKey() {
        return apiKey;
    }
}




