package com.example.CollegeFootballHub.service;
import com.example.CollegeFootballHub.client.ApiClient;
import com.example.CollegeFootballHub.entity.TeamDto;
import com.example.CollegeFootballHub.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;

@Service  // Marks this class as a Spring service component
public class ApiService {

    public final TeamRepository teamRepository;  // Repository for database operations
    private final ApiClient apiClient;  // Client for external API calls

    @Autowired
    public ApiService(TeamRepository teamRepository, ApiClient apiClient) {
        this.teamRepository = teamRepository;  // Constructor injection of repository
        this.apiClient = apiClient;  // Constructor injection of API client
    }

    public Mono<List<TeamDto>> fetchTeamsFromApi(String apiKey) {
        // This method retrieves teams directly from the external API without saving to the database
        return apiClient.getTeams(apiKey)  // Call the API client to get teams
                .map(teams -> {
                    System.out.println("Successfully fetched " + teams.size() + " teams");  // Log success
                    return teams;  // Return the teams unchanged
                })
                .onErrorResume(e -> {
                    System.err.println("API fetch failed: " + e.getMessage());  // Log error
                    return Mono.empty();  // Return empty result on error instead of propagating
                });
    }

    public Mono<TeamDto> fetchTeamByIdFromApi(Long id, String apiKey) {
        // This method retrieves a single team by ID from the external API
        return apiClient.getTeamById(id, apiKey)  // Call the API client to get a specific team
                .map(team -> {
                    System.out.println("Successfully fetched team: " + team.getSchool());  // Log success
                    return team;  // Return the team
                })
                .onErrorResume(e -> {
                    System.err.println("API fetch failed for team ID " + id + ": " + e.getMessage());  // Log error
                    return Mono.empty();  // Return empty result on error instead of propagating
                });
    }
}