package com.example.CollegeFootballHub;

import com.example.CollegeFootballHub.entity.Team;
import com.example.CollegeFootballHub.entity.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class ApiClient {

    private final WebClient webClient;

    @Autowired
    public ApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.collegefootballdata.com")
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(5 * 1024 * 1024))
                .build();
    }

    // Calling an API endpoint using WebClient
    public Mono<List<TeamDto>> getTeams(String apiKey) {
        return webClient.get()
                .uri("/teams")
                .header("Authorization", "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})
                .doOnSuccess(teams -> System.out.println("Successfully fetched " + (teams != null ? teams.size() : 0) + " teams"))
                .onErrorResume(WebClientResponseException.class, e -> {
                    System.err.println("Response body: " + e.getResponseBodyAsString());
                    return Mono.empty();
                })
                .onErrorResume(e -> {
                    System.err.println("Request failed: " + e.getMessage());
                    return Mono.empty();
                });
    }
}