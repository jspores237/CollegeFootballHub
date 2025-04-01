package com.example.CollegeFootballHub.service;
import com.example.CollegeFootballHub.entity.Team;
import com.example.CollegeFootballHub.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;


@Service  // Marks this class as a Spring service component
public class TeamService {

    private final TeamRepository teamRepository;  // Repository for database operations on Team entities
    private final ApiService apiService;  // Service for API operations

    @Autowired
    public TeamService(TeamRepository teamRepository, ApiService apiService) {
        this.teamRepository = teamRepository;  // Constructor injection of repository
        this.apiService = apiService;  // Constructor injection of API service
    }

    // Get all teams, fetch from the API if not found in DB
    public Mono<List<?>> getAllTeams(String apiKey) {
        return teamRepository.findAll()  // Query the database for all teams
                .collectList()  // Convert Flux<Team> to Mono<List<Team>>
                .flatMap(savedTeams -> {
                    if (savedTeams.isEmpty()) {
                        // If database is empty, fetch teams from API
                        return apiService.fetchTeamsFromApi(apiKey);  // Gets data from API
                    }
                    // Return saved teams if they exist
                    return Mono.just(savedTeams);  // Returns data from database
                });
    }

    //Get team by ID
    public Mono<Team> getTeamById(Long id, String apiKey) {
        // Fetch team from repository
        return teamRepository.findById(id);  // Simple database lookup by ID
        // Note: No fallback to API if not found
    }

    //Get teams by conference
    public Mono<List<Team>> getTeamsByConference(String conference, String apiKey) {
        return teamRepository.findAll()  // Get all teams from database
                .filter(team -> conference.equalsIgnoreCase(team.getConference()))  // Filter by conference
                .collectList();  // Convert to List
    }

    public Mono<Void> refreshTeamsFromApi(String apiKey) {
        return teamRepository.deleteAll()  // Clear all teams from database
                .then(apiService.fetchTeamsFromApi(apiKey)
                        .then());  // Fetch from API then convert to Mono<Void>
        // Note: Teams are fetched but not saved to the database
    }
}