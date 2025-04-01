package com.example.CollegeFootballHub.client;
import com.example.CollegeFootballHub.entity.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.util.List;

@Component  // Marks this class as a Spring component for dependency injection
public class ApiClient {

    private final WebClient webClient;

    @Autowired
    public ApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.collegefootballdata.com")  // Sets the base URL for all requests
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(5 * 1024 * 1024))  // Increases the buffer size to 5MB to handle large responses
                .build();
    }

    // Method to fetch teams from the API
    public Mono<List<TeamDto>> getTeams(String apiKey) {
        return webClient.get()  // Creates a GET request
                .uri("/teams")  // Appends this path to the base URL
                .header("Authorization", "Bearer " + apiKey)  // Adds Bearer token authentication
                .retrieve()  // Initiates the request and retrieves the response
                .bodyToMono(new ParameterizedTypeReference<List<TeamDto>>() {})  // Deserializes the JSON response into a List of TeamDto objects
                .onErrorResume(WebClientResponseException.class, e -> {  // Handles HTTP errors (4xx, 5xx)
                    System.err.println("Response body: " + e.getResponseBodyAsString());  // Logs the error response body
                    return Mono.empty();  // Returns an empty Mono instead of failing
                })
                .onErrorResume(e -> {  // Handles all other errors (network issues, etc.)
                    System.err.println("Request failed: " + e.getMessage());  // Logs the error message
                    return Mono.empty();  // Returns an empty Mono instead of failing
                });
    }


    // Method to fetch a single team by ID from the API
    public Mono<TeamDto> getTeamById(Long id, String apiKey) {
        return webClient.get()  // Creates a GET request
                .uri("/teams/{id}", id)  // Uses path variable for the team ID
                .header("Authorization", "Bearer " + apiKey)  // Adds Bearer token authentication
                .retrieve()  // Initiates the request and retrieves the response
                .bodyToMono(TeamDto.class)  // Deserializes the JSON response into a single TeamDto object
                .onErrorResume(WebClientResponseException.class, e -> {  // Handles HTTP errors (4xx, 5xx)
                    System.err.println("Response body for team ID " + id + ": " + e.getResponseBodyAsString());  // Logs the error response body
                    return Mono.empty();  // Returns an empty Mono instead of failing
                })
                .onErrorResume(e -> {  // Handles all other errors (network issues, etc.)
                    System.err.println("Request failed for team ID " + id + ": " + e.getMessage());  // Logs the error message
                    return Mono.empty();  // Returns an empty Mono instead of failing
                });
    }

}