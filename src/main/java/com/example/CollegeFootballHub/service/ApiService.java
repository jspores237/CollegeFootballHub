package com.example.CollegeFootballHub.service;
import com.example.CollegeFootballHub.ApiClient;
import com.example.CollegeFootballHub.entity.TeamDto;
import com.example.CollegeFootballHub.entity.Team;
import com.example.CollegeFootballHub.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class ApiService {

    private final TeamRepository teamRepository;
    private final ApiClient apiClient;

    @Autowired
    public ApiService(TeamRepository teamRepository, ApiClient apiClient) {
        this.teamRepository = teamRepository;
        this.apiClient = apiClient;
    }

    public Mono<List<TeamDto>> fetchTeamsFromApi(String apiKey) {
        // Just fetch teams from the API using the ApiClient and return DTOs
        return apiClient.getTeams(apiKey)
                .map(teams -> {
                    System.out.println("Successfully fetched " + teams.size() + " teams");
                    return teams;
                })
                .onErrorResume(e -> {
                    System.err.println("API fetch failed: " + e.getMessage());
                    return Mono.empty();
                });
    }
}