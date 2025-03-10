package com.example.CollegeFootballHub.service;

import com.example.CollegeFootballHub.entity.Team;
import com.example.CollegeFootballHub.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ApiService apiService;

    @Autowired
    public TeamService(TeamRepository teamRepository, ApiService apiService) {
        this.teamRepository = teamRepository;
        this.apiService = apiService;
    }

    // Get all teams, fetch from the API if not found in DB
    public Mono<List<?>> getAllTeams(String apiKey) {
        return teamRepository.findAll()
                .collectList()
                .flatMap(savedTeams -> {
                    if (savedTeams.isEmpty()) {
                        // If database is empty, fetch teams from API
                        return apiService.fetchTeamsFromApi(apiKey);
                    }
                    // Return saved teams if they exist
                    return Mono.just(savedTeams);
                });
    }

    // Get team by ID
    public Mono<Team> getTeamById(Long id, String apiKey) {
        // Fetch team from repository
        return teamRepository.findById(id);
    }

    // Get teams by conference
    public Mono<List<Team>> getTeamsByConference(String conference, String apiKey) {
        return teamRepository.findAll()
                .filter(team -> conference.equalsIgnoreCase(team.getConference()))
                .collectList();
    }

    public Mono<Void> refreshTeamsFromApi(String apiKey) {
        return teamRepository.deleteAll()
                .then(apiService.fetchTeamsFromApi(apiKey)
                        .then()); // Convert to Mono<Void>
    }
}